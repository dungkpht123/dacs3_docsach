package com.example.doctruyen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.doctruyen.Adapter.AdminCategoryAdapter
import com.example.doctruyen.databinding.ActivityDashboardAdminBinding
import com.example.doctruyen.firebase.fireBase_Category
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class DashboardAdminActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityDashboardAdminBinding
//    firebase auth
    private  lateinit var firebaseAuth: FirebaseAuth

//    Array list to hold categories
    private lateinit var categoryArrayList: ArrayList<fireBase_Category>
    private lateinit var adapterCategory: AdminCategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

//        Chuyen lan
        binding.categoryCard.setOnClickListener{
            startActivity(Intent(this,ManageCategoryAdmin::class.java))
        }

        binding.bookCard.setOnClickListener {
            startActivity(Intent(this,ManageBook::class.java))
        }

//        handle click logout
        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }
//        handle click pdf
        binding.profileBtn.setOnClickListener {
            startActivity(Intent(this,ProfileAdminActivity::class.java))

        }


    }


    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser == null){
//            not loggin in goto main screen
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        else{
            // logged in, get and show user info
            val email = firebaseUser.email
//            set to textview of toolbar
            binding.subTitleTv.text= email
        }
    }
}