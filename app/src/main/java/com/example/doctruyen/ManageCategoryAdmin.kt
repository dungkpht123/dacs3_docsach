package com.example.doctruyen

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.doctruyen.Adapter.AdminCategoryAdapter
import com.example.doctruyen.Adapter.CategoryAdapter
import com.example.doctruyen.databinding.ActivityManageCategoryAdminBinding
import com.example.doctruyen.firebase.Test
import com.example.doctruyen.firebase.fireBase_Category
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ManageCategoryAdmin : AppCompatActivity() {
    private lateinit var binding: ActivityManageCategoryAdminBinding
    private lateinit var categoryArrayList: ArrayList<fireBase_Category>
    private lateinit var adapterCategory: AdminCategoryAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var ref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_category_admin)
        binding = ActivityManageCategoryAdminBinding.inflate(layoutInflater)


        loadCategories()
//        search()

        val addCategoryBtn = findViewById<Button>(R.id.addCategoryBtn)
        addCategoryBtn.setOnClickListener{
            startActivity(Intent(this,CategoryAddActivity::class.java))
        }

        var addPdfFab = findViewById<FloatingActionButton>(R.id.addPdfFab)
        addPdfFab.setOnClickListener{
            startActivity(Intent(this,PdfAddActivity::class.java))
        }
//        binding.addPdfFab.setOnClickListener {
//            startActivity(Intent(this,PdfAddActivity::class.java))
//        }

    }

//    private fun search() {
//        binding.searchEt.addTextChangedListener(object: TextWatcher {
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                try {
//                    adapterCategory.filter.filter(s)
//                }
//                catch (e: Exception){
//
//                }
//            }
//            override fun afterTextChanged(s: Editable?) {
//            }
//        })
//    }

    private fun loadCategories() {
        categoryArrayList = ArrayList()

        val database = Firebase.database
        val myRef = database.getReference("Categories")
        var x = 0

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val arrayData = ArrayList<Test>()
                for (postSnapshot in dataSnapshot.children) {
                    val filterCategory = postSnapshot.getValue(Test::class.java)
                    arrayData.add(filterCategory!!)
                }
                recyclerView = findViewById(R.id.recyclerView_Cate_admin)
                recyclerView.layoutManager = LinearLayoutManager(this@ManageCategoryAdmin)
                adapterCategory = AdminCategoryAdapter(this@ManageCategoryAdmin,arrayData)
                recyclerView.adapter = adapterCategory

                adapterCategory.onItemClick={
                    myRef.child("${it.id}").removeValue()
                }

//                adapterCategory = AdminCategoryAdapter(this@ManageCategoryAdmin,arrayData)
//                binding.recyclerViewCateAdmin.layoutManager = LinearLayoutManager(this@ManageCategoryAdmin,RecyclerView.VERTICAL,false)
//                binding.recyclerViewCateAdmin.adapter = adapterCategory

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
    }
}

/*        ref = FirebaseDatabase.getInstance().getReference("Categories")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })*/
/*                // Clear list
                categoryArrayList.clear()
                for (ds in snapshot.children){
                    val model = ds.getValue(fireBase_Category::class.java)
                    categoryArrayList.add(model!!)
                }
                adapterCategory = AdminCategoryAdapter(this@ManageCategoryAdmin,categoryArrayList)
                binding.recyclerViewCateAdmin.layoutManager = LinearLayoutManager(this@ManageCategoryAdmin,RecyclerView.VERTICAL,false)
                binding.recyclerViewCateAdmin.adapter = adapterCategory*/