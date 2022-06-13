package com.example.doctruyen

import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.doctruyen.Adapter.MyApplication
import com.example.doctruyen.databinding.ActivityPdfDetailBinding
import com.example.doctruyen.databinding.ActivityPdfEditBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.io.FileOutputStream
import java.util.jar.Manifest

class PdfDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPdfDetailBinding
    private companion object{
        const val TAG= "BOOK_DETAIL_TAG"
    }
    private lateinit var progressDialog :ProgressDialog
    private var bookId =""
    private var bookTitle = ""
    private var bookUrl = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPdfDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bookId = intent.getStringExtra("bookId")!!
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)
        MyApplication.incrementBookViewCount(bookId)
        loadBookDetails()
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        binding.readBookBtn.setOnClickListener {
            val intent = Intent(this,PdfViewActivity::class.java)
            intent.putExtra("bookId",bookId)
            startActivity(intent)
        }
        binding.DownloadBookBtn.setOnClickListener {
            if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                Log.d(TAG,"ONCREATE :stoge premission is granted ")
                downloadbook()
            } else{
                Log.d(TAG,"ONCREATE :stoge premission  was not granted ")
                repuestStogePermissionLauchaer.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

            }
        }
    }
private val repuestStogePermissionLauchaer = registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted:Boolean->
    if (isGranted){
        Log.d(TAG,"ONCREATE :stoge premission is granted ")
        downloadbook()
    }else{
        Log.d(TAG,"ONCREATE :stoge premission is not granted ")
Toast.makeText(this,"Permission denied",Toast.LENGTH_SHORT).show()

    }


}
    private fun downloadbook(){
        Log.d(TAG,"Downloading booking")
        progressDialog.setMessage("Downloading booking")
        progressDialog.show()
        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(bookUrl)
        storageReference.getBytes(Constants.MAX_BYTE_PDF)
            .addOnSuccessListener {bytes->
                Log.d(TAG,"dowloadbook")
               saveToDownloadsFolder(bytes)
            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Log.d(TAG,"downloadbook : failed to get ${e.message}")
                Toast.makeText(this," failed download to get ${e.message}",Toast.LENGTH_SHORT).show()

                binding.progressBar.visibility = View.GONE

            }
    }

    private fun saveToDownloadsFolder(bytes: ByteArray?) {
        Log.d(TAG,"saveDownloadsfolder booking")
        val nameWithExtention = "$bookTitle.pdf"
        try {
            val downloadFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            downloadFolder.mkdirs()
            val  filePath = downloadFolder.path +"/"+ nameWithExtention
            val  out = FileOutputStream(filePath)
            out.write(bytes)
            out.close()
            Toast.makeText(this," save ti downloads folders",Toast.LENGTH_SHORT).show()
            Log.d(TAG,"saveDownloadsfolder booking")

            progressDialog.dismiss()
            incrementDownloadCount()
        }catch (e:Exception){
            Log.d(TAG,"saveDownloadsfolder booking: ${e.message}")
            Toast.makeText(this,"saveDownloadsfolder booking: ${e.message}",Toast.LENGTH_SHORT).show()

        }
    }

    private fun incrementDownloadCount() {
        Log.d(TAG,"saveDownloadscount booking:")
        val ref = FirebaseDatabase.getInstance().getReference("Books")
        ref.child(bookId)
            .addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    var dowloadCount = "${snapshot.child("downloadCount").value}"
                    if (dowloadCount==""||dowloadCount=="null"){
                        dowloadCount="0"

                    }
                    val newDownloadCount:Long = dowloadCount.toLong()+1
                    val hashMap:HashMap<String,Any> = HashMap()
                        hashMap["downloadsCount"] = newDownloadCount
                    val dbRef = FirebaseDatabase.getInstance().getReference("Books")
                    dbRef.child(bookId)
                        .updateChildren(hashMap)
                        .addOnSuccessListener {
                            Log.d(TAG,"COUNT DOWNLOAD")
                        }
                        .addOnFailureListener { e->
                            Log.d(TAG," FAILDER COUNT DOWNLOAD ${e.message}")

                        }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }

    private fun loadBookDetails() {
    val ref = FirebaseDatabase.getInstance().getReference("Books")
        ref.child(bookId)
            .addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    val categoryId = "${snapshot.child("categoryId").value}"
                    val description = "${snapshot.child("description").value}"
                    val downloadcount = "${snapshot.child("downloadsCount").value}"
                    val timestamp = "${snapshot.child("timestamp").value}"
                    bookTitle = "${snapshot.child("title").value}"
                    val viewscount = "${snapshot.child("viewCount").value}"
                    val uid = "${snapshot.child("uid").value}"
                    bookUrl = "${snapshot.child("url").value}"
                    val date = MyApplication.formatTimeStamp(timestamp.toLong())
                    MyApplication.loadCategory(categoryId,binding.categoryTV)
                    MyApplication.loadPdfFromUrlSinglePage("$bookUrl","$bookTitle",binding.pdfView,binding.progressBar,binding.pagesTV)
                    MyApplication.loadPdfSize("$bookUrl","$bookTitle",binding.sizeTV)
                    binding.titleTv.text = bookTitle
                    binding.desciptionTv.text = description
                    binding.viewsTV.text = viewscount
                    binding.downloadsTV.text = downloadcount
                    binding.dateTV.text = date

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }
}