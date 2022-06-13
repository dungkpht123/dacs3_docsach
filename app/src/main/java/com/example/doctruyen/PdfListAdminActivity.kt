package com.example.doctruyen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.TextView
import com.example.doctruyen.Adapter.AdapterPdfAdmin
import com.example.doctruyen.Model.BookDataTest
import com.example.doctruyen.Model.ModelPdfAdmin
import com.example.doctruyen.databinding.ActivityPdfListAdminBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson

class PdfListAdminActivity : AppCompatActivity() {
    private var categoryid =""
    private var category =""
    private companion object{
        const val TAG="PDF_LIST_ADMIN_TAG"
    }
    private lateinit var pdfArrayList: ArrayList<ModelPdfAdmin>
    private lateinit var adapterPdfAdmin: AdapterPdfAdmin
    private lateinit var binding:ActivityPdfListAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfListAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var intent = intent
        categoryid = intent.getStringExtra("categoryid")!!
        category = intent.getStringExtra("category")!!

//        val id = arrcategor
        Log.i("thu","$categoryid")
//        textView.text =arrcategor.toString()
        binding.subTitleTv.text= category
        loadPdfList()
        binding.searchEt.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
        try {
            adapterPdfAdmin.filter!!.filter(s)
        }catch (e:Exception){
            Log.d(TAG,"MESS: ${e.message}")
                }
        }
            override fun afterTextChanged(p0: Editable?) {
                TODO("Not yet implemented")
            }

        })
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }

    private fun loadPdfList() {
        pdfArrayList = ArrayList()
        val ref = FirebaseDatabase.getInstance().getReference("Books")
        ref.orderByChild("categoryId").equalTo(categoryid)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                   pdfArrayList.clear()
                   for (ds in snapshot.children){
                       val model = ds.getValue(ModelPdfAdmin::class.java)
                       if (model != null) {
                           pdfArrayList.add(model)
                           Log.d(TAG,"ondatachage ${model.title} ${model.categoryId}")

                       }
                   }
                    Log.d("Test","${pdfArrayList}")
                    adapterPdfAdmin = AdapterPdfAdmin(this@PdfListAdminActivity,pdfArrayList)
                    binding.booksRv.adapter = adapterPdfAdmin
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }


}