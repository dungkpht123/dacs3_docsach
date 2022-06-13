package com.example.doctruyen

import android.app.ProgressDialog
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.doctruyen.databinding.ActivityDocBinding
import com.example.doctruyen.service.FirebaseService
import com.github.barteksc.pdfviewer.PDFView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class Doc : AppCompatActivity() {
    private lateinit var PDFlink: String
    private var bookId = ""

    private lateinit var pdfView: PDFView
    private lateinit var binding: ActivityDocBinding

    private companion object {
        const val TAG = "PDF_VIEW_TAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadbookDeatails()

        val btnBack = findViewById<ImageButton>(R.id.backBtn)
        btnBack.setOnClickListener {
            onBackPressed()
        }

//        PDFlink = intent.getStringExtra("PDF_mybook").toString()
//        pdfView = findViewById(R.id.pdfView)
//        val uri = Uri.parse(PDFlink)
//
//        Log.d("AAA", "URI: $uri")
//
//        pdfView.fromAsset("harrypotter_test.pdf")
//            .password(null)
//            .defaultPage(0)
//            .enableSwipe(true)
//            .swipeHorizontal(false)
//            .enableDoubletap(true)
//            .onPageError{ page, t ->
//                Toast.makeText(this,"Error" +page,  Toast.LENGTH_SHORT).show()
//                Log.d("AAA", t.localizedMessage)
//            }
//            .onTap { false }
//            .enableAnnotationRendering(true)
//            .load()
    }

    private fun loadbookDeatails() {
        Log.d(TAG, "loadbookdeatail get pdf url from db")
        val ref = FirebaseDatabase.getInstance().getReference("Books")
        ref.child(bookId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val pdfUrl = snapshot.child("url").value
                    Log.d(TAG, "ondatachange:pdf_url $pdfUrl")
                    loadBookFromUrl("$pdfUrl")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }

    private fun loadBookFromUrl(pdfUrl: String) {
        Log.d(TAG, "Loadbookfrom: get pdf from firebase storage using url")
        val reference = FirebaseStorage.getInstance().getReferenceFromUrl(pdfUrl)
        reference.getBytes(Constants.MAX_BYTE_PDF)
            .addOnSuccessListener { bytes ->
                Log.d(TAG, "Loading :pdf to got from url")
                binding.pdfView.fromBytes(bytes)
                    .swipeHorizontal(false)
                    .onPageChange { page, pageCount ->
                        val currentPage = page + 1
                        binding.toolbarSubtitleTv.text = "$currentPage/$pageCount"
                        Log.d(TAG, "loadbookfromurl $currentPage/$pageCount")
                    }
                    .onError { t ->
                        Log.d(TAG, "loadbookfromurl ${t.message}")

                    }
                    .onPageError { page, t ->
                        Log.d(TAG, "loadbookfromurl ${t.message}")

                    }.load()
                binding.progressBar.visibility = View.GONE
            }
            .addOnFailureListener { e ->
                Log.d(TAG, "Loading : failed to get ${e.message}")
                binding.progressBar.visibility = View.GONE

            }
//    private fun loadBookDetail() {
//        Log.d(TAG,"loadBook tu firebase")
//        val ref = FirebaseService()
//        Log.d(TAG,"REF: $ref")
//        ref.getBook {
//            Log.d(TAG,"List: $it")
//        }
//
//    }
    }
}