package com.example.doctruyen

import android.app.AlertDialog
import android.app.Application
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.example.doctruyen.databinding.ActivityPdfAddBinding
import com.example.doctruyen.firebase.fireBase_Category
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage


class PdfAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPdfAddBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog
    private lateinit var categoryArrayList: ArrayList<fireBase_Category>
    private var pdfUri: Uri? =  null

    private val TAG = "PDF_ADD_TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_add)
        binding = ActivityPdfAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        loadPDFCategory()
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        binding.categoryTv.setOnClickListener {
            categoryPickDialog()
        }

        binding.attachPdf.setOnClickListener {
            pdfPickIntent()
        }

//        Start uploading pdf
        binding.submitBtn.setOnClickListener {
/*
* chon data -> upload data len firebase storage -> lay dia chi url -> uploadd pdf len firebase db
* */
            validateData()

        }
    }

    private var title = ""
    private var description = ""
    private var category = ""

    private fun validateData() {
        Log.d(TAG,"xac nhan data: Dang Xac nhan data")

        title = binding.titleEt.text.toString().trim()
        description = binding.descriptionEt.text.toString().trim()
        category = binding.categoryTv.text.toString().trim()

            if(title.isEmpty()) {
                Toast.makeText(this,"Enter Title....",Toast.LENGTH_LONG).show()
            }else if(description.isEmpty()) {
                Toast.makeText(this, "Enter Description....", Toast.LENGTH_LONG).show()

            }else if(category.isEmpty()) {
                Toast.makeText(this, "Pick category....", Toast.LENGTH_LONG).show()
            }else if(pdfUri == null) {
                Toast.makeText(this, "Pick PDF....", Toast.LENGTH_LONG).show()
            }
            else {
                uploadPdfToStorage()
            }
        }
//Update ti storage
    private fun uploadPdfToStorage() {
        Log.d(TAG,"Upload pdf to storage: Upload to stores")

        progressDialog.setMessage("Uploading PDF")
        progressDialog.show()

        val timestamp = System.currentTimeMillis()

        val filePathAndName = "Books/$timestamp"
        val storageRefecence = FirebaseStorage.getInstance().getReference(filePathAndName)
        storageRefecence.putFile(pdfUri!!)
            .addOnSuccessListener {task ->
                Log.d(TAG,"Upload to Storage: PDF uploading now geting url")
                val uriTask:Task<Uri> = task.storage.downloadUrl
                while (!uriTask.isSuccessful);
                val uploadPdfUri = "${uriTask.result}"

                uploadPdfIntotoDB(uploadPdfUri,timestamp)
            }
            .addOnFailureListener { e ->
                Log.d(TAG,"Upload PDf to Storage: Fail ${e.message}")
                progressDialog.dismiss()
                Toast.makeText(this, "Failed to update due to ${e.message}", Toast.LENGTH_SHORT).show()

            }
    }

    private fun uploadPdfIntotoDB(uploadPdfUri: String, timestamp: Long) {
        Log.d(TAG,"uploadPdfIntoDb: upload to db")

        progressDialog.setMessage("Uploading pdf info....")

        val uid = firebaseAuth.uid

        val hashMap: HashMap<String,Any> = HashMap()
        hashMap["uid"] = "$uid"
        hashMap["id"] = "$timestamp"
        hashMap["title"] = "$title"
        hashMap["description"] = "$description"
        hashMap["categoryId"] = "$selectedCategoryId"
        hashMap["url"] = "$uploadPdfUri"
        hashMap["timestamp"] = timestamp
        hashMap["viewCount"] = 0
        hashMap["downloadsCount"] = 0

        val ref = FirebaseDatabase.getInstance().getReference("Books")
        ref.child("$timestamp")
            .setValue(hashMap)
            .addOnSuccessListener {
                Log.d(TAG,"uploadPdftoDb: uploaded to db")
                progressDialog.dismiss()
                Toast.makeText(this,"Uploaded.....",Toast.LENGTH_SHORT).show()
                pdfUri = null

            }
            .addOnFailureListener {e->
                Log.d(TAG,"ERROR: ${e.message}")
                progressDialog.dismiss()
                Toast.makeText(this,"Failed to upload due to ${e.message}",Toast.LENGTH_SHORT).show()

            }



    }

    private fun loadPDFCategory() {
        Log.d(TAG,"LoadPDFCategory : Loading PDF categories")
        categoryArrayList = ArrayList()

        val ref = FirebaseDatabase.getInstance().getReference("Categories")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                categoryArrayList.clear()
                for (ds in snapshot.children){
                    val model = ds.getValue(fireBase_Category::class.java)
//                    add to arraylist
                    categoryArrayList.add(model!!)
                    Log.d(TAG,"onDataChange: ${model.category}")
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    private var selectedCategoryId = ""
    private var selectedCategoryTitle = ""
    private fun categoryPickDialog(){
        Log.d(TAG,"Category PickDialog: show pdf cateogory pick dialog")

        val categoriesArray = arrayOfNulls<String>(categoryArrayList.size)
        for (i in categoryArrayList.indices){
            categoriesArray[i] = categoryArrayList[i].category
        }

//        alert
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Pick Category")
            .setItems(categoriesArray){ dialog, which ->
                selectedCategoryTitle = categoryArrayList[which].category
                selectedCategoryId = categoryArrayList[which].id

                binding.categoryTv.text = selectedCategoryTitle

                Log.d(TAG,"Category Pick Dialog: Selected category ID: $selectedCategoryId")
                Log.d(TAG,"Category Pick Dialog: Selected category Title: $selectedCategoryTitle")
            }
            .show()
    }

    private fun pdfPickIntent(){
        Log.d(TAG,"pdfPickIntent: Start PDF pick intent")
        val intent = Intent()
        intent.type= "application/pdf"
        intent.action = Intent.ACTION_GET_CONTENT
        pdfActivityResultLaucher.launch(intent)

    }
    val pdfActivityResultLaucher  = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult>{ result ->
            if (result.resultCode == RESULT_OK)
            {
                Log.d(TAG,"PDF Picked: ")
                pdfUri = result.data!!.data
            }else{
                Log.d(TAG,"PDF Picked cancel")
                Toast.makeText(this,"Cancelled",Toast.LENGTH_LONG).show()
            }
        }
        )
    }