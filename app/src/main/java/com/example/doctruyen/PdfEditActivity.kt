package com.example.doctruyen

import android.app.AlertDialog
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.doctruyen.databinding.ActivityPdfEditBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PdfEditActivity : AppCompatActivity() {
    private lateinit var binding:ActivityPdfEditBinding
    private var bookId =""
    private companion object{
        private const val TAG = "PDF_EDIT_TAG"
    }
    private lateinit var progressDialog: ProgressDialog
    private lateinit var categoryTitleArrayList : ArrayList<String>
    private lateinit var categoryIdArrayList : ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bookId = intent.getStringExtra("bookId")!!
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)
        loadCategories()
        loadBookInfo()
        binding.backBtn.setOnClickListener{
            onBackPressed()
        }
        binding.categoryTV.setOnClickListener {
            categoryDialog()
        }
        binding.submitBtn.setOnClickListener {
            validateData()
        }
    }

    private fun loadBookInfo() {
        Log.d(TAG,"LoadBookInfo:Loading book info")
        val ref = FirebaseDatabase.getInstance().getReference("Books")
        ref.child(bookId)
            .addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    selectedCategoryId = snapshot.child("categoryId").value.toString()
                    val description  = snapshot.child("description").value.toString()
                    Log.d("dien","${description}")
                    val title  = snapshot.child("title").value.toString()
                    binding.titleEt.setText(title)
                    binding.descriptionEt.setText(description)

                    Log.d(TAG,"onDatachange:Loading book category info")
                    val refBookCategory = FirebaseDatabase.getInstance().getReference("Categories")
                    refBookCategory.child(selectedCategoryId)
                        .addListenerForSingleValueEvent(object :ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val    category = snapshot.child("category").value
                                 binding.categoryTV.text = category.toString()

                            }

                            override fun onCancelled(error: DatabaseError) {

                            }

                        })

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }
    private var  title =""
    private var description = ""

    private fun validateData() {
        title = binding.titleEt.text.toString().trim()
        description= binding.descriptionEt.text.toString().trim()
        if(title.isEmpty()){
            Toast.makeText(this,"Enter title",Toast.LENGTH_SHORT).show()
        }else if (description.isEmpty()){
            Toast.makeText(this,"Enter Description",Toast.LENGTH_SHORT).show()

        }else if (selectedCategoryId.isEmpty()){
            Toast.makeText(this,"Pick Description",Toast.LENGTH_SHORT).show()

        }else{
            updatePdf()
        }
    }

    private fun updatePdf() {
        Log.d(TAG,"updatePdf :Starting updating pdf into...")
        progressDialog.setMessage("Updating book info")
        progressDialog.show()
        val hashMap = HashMap<String,Any>()
        hashMap["title"] = "$title"
        hashMap["description"]="$description"
        hashMap["categoryId"] = "$selectedCategoryId"
        val ref = FirebaseDatabase.getInstance().getReference("Books")
        ref.child(bookId)
            .updateChildren(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Log.d(TAG,"UpdatePdf: Updated successfully...")
                Toast.makeText(this," Updated successfully",Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener { e->
                Log.d(TAG,"UpdatePdf:Failed to update to ${e.message}")
                progressDialog.dismiss()
                Toast.makeText(this,"Failed to update to ${e.message}",Toast.LENGTH_SHORT).show()

            }
    }

    private fun categoryDialog() {
        val categoriesArray =  arrayOfNulls<String>(categoryTitleArrayList.size)
        for (i in categoryTitleArrayList.indices){
            categoriesArray[i] = categoryTitleArrayList[i]

        }
        val builder =  AlertDialog.Builder(this)
        builder.setTitle("Choose Category")
            .setItems(categoriesArray){dialog,position->
                selectedCategoryId = categoryIdArrayList[position]
                selectedCategoryTitle = categoryTitleArrayList[position]
                binding.categoryTV.text = selectedCategoryTitle

            }.show()
    }
    private var selectedCategoryId = ""
    private var selectedCategoryTitle = ""
    private fun loadCategories(){
        Log.d(TAG,"loadCategories:loading categories...")
        categoryTitleArrayList = ArrayList()
        categoryIdArrayList = ArrayList()
        val ref = FirebaseDatabase.getInstance().getReference("Categories")
        ref.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                categoryIdArrayList.clear()
                categoryTitleArrayList.clear()
                for (ds in snapshot.children){
                    val id = "${ds.child("id").value}"
                    val category ="${ ds.child("category").value}"
                    categoryIdArrayList.add(id)
                    categoryTitleArrayList.add(category)
                    Log.d(TAG,"onDataChange:Category ID $id")
                    Log.d(TAG,"onDataChange:Category  $category")

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}