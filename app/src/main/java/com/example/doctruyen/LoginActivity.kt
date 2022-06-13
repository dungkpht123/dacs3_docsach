package com.example.doctruyen

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.doctruyen.UserInterface.HomePage
import com.example.doctruyen.databinding.ActivityLoginBinding
import com.example.doctruyen.databinding.ActivityRegisterBinding
import com.example.doctruyen.fragments.HomePageFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {
//    viewbinding
    private lateinit var  binding: ActivityLoginBinding
    //    firebase auth
    private  lateinit var firebaseAuth: FirebaseAuth
    //    progress dialog
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        init firebase
        firebaseAuth = FirebaseAuth.getInstance()
//        init progress dialog will show while login user
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        // handle click not have account goto register screen
        binding.noAccountTv.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }
        binding.loginBtn.setOnClickListener {
//            step 1 input data 2 validate data 3 login firebase auth 4 check user -firebase auth
//            if user move to user dashboard
//            if admin move  to admin dashboard
            validateData()
        }

        binding.forgotTV.setOnClickListener {
            startActivity(Intent(this,ForgotpasswordActivity::class.java))
        }

    }
    private var password = ""
    private var email = ""
    private fun validateData() {
//        input data
        email = binding.emailEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()

//        validate data
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this,"Invalid email format ... ", Toast.LENGTH_SHORT).show()

        } else if(password.isEmpty()){
            Toast.makeText(this,"Enter password... ", Toast.LENGTH_SHORT).show()
        }
        else{
            loginUser()
        }
    }

    private fun loginUser() {
//        3 login firebase auth

//        show progress
        progressDialog.setMessage("Loggin in")
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                checkUser()
            }
            .addOnFailureListener { e->
//                failed login
                progressDialog.dismiss()
                Toast.makeText(this,"login Failed due to ${e.message}",Toast.LENGTH_SHORT).show()

            }
    }
//    4 check user -firebase auth
    //            if user move to user dashboard
//            if admin move  to admin dashboard
    private fun checkUser() {
        progressDialog.setMessage("checking user ...")
    val  firebaseUser = firebaseAuth.currentUser!!
    val ref =FirebaseDatabase.getInstance().getReference("Users")
    ref.child(firebaseUser.uid)
        .addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                progressDialog.dismiss()
//                get user type user/admin
                var userType = snapshot.child("userType").value
                if (userType == "user"){
                //its simple user open user dashboard
                    startActivity(Intent(this@LoginActivity,HomePage::class.java))
                    finish()
                } else if ( userType == "admin" ){
    //                its admin open admin dashboard
                    startActivity(Intent(this@LoginActivity,DashboardAdminActivity::class.java))
                    finish()

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}