package com.example.doctruyen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.example.doctruyen.Adapter.MyApplication
import com.example.doctruyen.databinding.ActivityProfileAdminBinding
import com.example.doctruyen.databinding.RowPdfAdminBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileAdminActivity : AppCompatActivity() {
    private lateinit var binding:ActivityProfileAdminBinding
    private lateinit var firebaseAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        loadUserInfo()
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        binding.profileeditBtn.setOnClickListener {
            startActivity(Intent(this,ProfileEditActivity::class.java))
        }

    }

    private fun loadUserInfo() {
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseAuth.uid!!)
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val email = "${snapshot.child("email").value}"
                    val name = "${snapshot.child("name").value}"
                    val profileImage = "${snapshot.child("profileImage").value}"
                    val timestamp = "${snapshot.child("timestamp").value}"
                    val uid = "${snapshot.child("uid").value}"
                    val userType = "${snapshot.child("userType").value}"



                    val formatteDate = MyApplication.formatTimeStamp(timestamp.toLong())
                    binding.nameTv.text =name
                    binding.emailTv.text=email
                    binding.memberTypeTv.text= formatteDate
                    binding.accountTypeTv.text=userType
                    try{
                        Glide.with(this@ProfileAdminActivity)
                            .load(profileImage)
                            .placeholder(R.drawable.ic_person_gray)
                            .into(binding.profileTv)
                    }catch (e:Exception){

                    }


                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }
}