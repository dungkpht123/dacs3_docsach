package com.example.doctruyen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.doctruyen.databinding.ActivityPdfViewBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class PdfViewActivity : AppCompatActivity() {
    private companion object{
        const val TAG = "PDF_VIEW_TAG"
    }
    private var  bookId = ""


    private lateinit var binding: ActivityPdfViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bookId = intent.getStringExtra("bookId")!!
        loadbookDeatails()
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }

    private fun loadbookDeatails() {
        Log.d(TAG,"loadbookdeatail get pdf url from db")
        val ref = FirebaseDatabase.getInstance().getReference("Books")
        ref.child(bookId)
            .addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val pdfUrl = snapshot.child("url").value
                    Log.d(TAG,"ondatachange:pdf_url $pdfUrl")
                    loadBookFromUrl("$pdfUrl")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }

    private fun loadBookFromUrl(pdfUrl:String) {
        Log.d(TAG,"Loadbookfrom: get pdf from firebase storage using url")
        val reference = FirebaseStorage.getInstance().getReferenceFromUrl(pdfUrl)
        reference.getBytes(Constants.MAX_BYTE_PDF)
            .addOnSuccessListener {bytes->
                Log.d(TAG,"Loading :pdf to got from url")
                binding.pdfView.fromBytes(bytes)
                    .swipeHorizontal(false)
                    .onPageChange{ page, pageCount ->
                        val currentPage = page+1
                        binding.toolbarSubtitleTv.text="$currentPage/$pageCount"
                        Log.d(TAG,"loadbookfromurl $currentPage/$pageCount")
                    }
                    .onError {t->
                        Log.d(TAG,"loadbookfromurl ${t.message}")

                    }
                    .onPageError { page, t ->
                        Log.d(TAG,"loadbookfromurl ${t.message}")

                    }.load()
                    binding.progressBar.visibility = View.GONE
            }
            .addOnFailureListener { e->
                Log.d(TAG,"Loading : failed to get ${e.message}")
                binding.progressBar.visibility = View.GONE

            }
    }
}