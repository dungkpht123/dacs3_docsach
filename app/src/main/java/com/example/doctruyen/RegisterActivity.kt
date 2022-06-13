package com.example.doctruyen

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.doctruyen.databinding.ActivityRegisterBinding
import com.example.doctruyen.fragments.HomePageFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    //    view binding
    private lateinit var  binding:ActivityRegisterBinding
    //    firebase auth
    private  lateinit var firebaseAuth:FirebaseAuth
    //    progress dialog
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        init firebase
        firebaseAuth = FirebaseAuth.getInstance()
//        init progress dialog will show while creating account register user
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("please wait")
        progressDialog.setCanceledOnTouchOutside(false)

//        handle back button click goto previous
        binding.backBtn.setOnClickListener {
            onBackPressed() // goto previous screen
        }
        binding.registerBtn.setOnClickListener {
//            1 input data 2 validate data 3 create account -firebase auth 4 save user info - firebase realtime database
            validateData()

        }
    }
    private var name = ""
    private var  email = ""
    private var  password = ""

    private fun validateData() {
//        1 input data
        name = binding.nameEt.text.toString().trim()
        email = binding.emailEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()
        val cPassword = binding.cppasswordEt.text.toString().trim()
//        2 validate data
        if(name.isEmpty()){
//            empty name
            Toast.makeText(this,"Enter your name ...",Toast.LENGTH_SHORT).show()
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            invalid email pattern
            Toast.makeText(
                this,
                "Invalid email pattern ",
                Toast.LENGTH_SHORT
            ).show()
        }
        else if(password.isEmpty()){
//                empty password
            Toast.makeText(this,
                "Enter password ...",Toast.LENGTH_SHORT).show()
        }
        else if(cPassword.isEmpty()){
//                empty password
            Toast.makeText(this,
                "Confirm password ...",Toast.LENGTH_SHORT).show()
        }
        else if(password != cPassword){
//                empty password
            Toast.makeText(this, "Password doesn't match ...",Toast.LENGTH_SHORT).show()
        }
        else {
            createUserAccount()
        }

    }

    private fun createUserAccount() {
//        3 create account -firebase auth
//        show progress
        progressDialog.setMessage("Creating Account...")
        progressDialog.show()
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
//                account created ,now add user info in db
                updateUserInfo()

            }
            .addOnFailureListener { e->
//                failed creating account
                progressDialog.dismiss()
                Toast.makeText(this,"Failed creating account due to ${e.message}",Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateUserInfo() {
//        4 save user info - firebase realtime database
        progressDialog.setMessage("Saving user info")
        val timestamp = System.currentTimeMillis()
        val uid = firebaseAuth.uid
        val hashMap: HashMap<String,Any?> = HashMap()
        hashMap["uid"] = uid
        hashMap["email"] = email
        hashMap["name"] = name
        hashMap["profileImage"] = "" //add empty will do in profile edit
        hashMap["userType"] = ""     // prossible are user/admin ,will change value to admin manually on firebase db
        hashMap["timestamp"] = timestamp

//        set data to db
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(uid!!)
            .setValue(hashMap)
            .addOnSuccessListener {
// user info saved open user dashboard
                progressDialog.dismiss()
                Toast.makeText(this,"Account created... ", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@RegisterActivity,HomePageFragment::class.java))
            }
            .addOnFailureListener {e->
//                failed adding data to db
                progressDialog.dismiss()
                Toast.makeText(this,"Failed creating account due to ${e.message}",Toast.LENGTH_SHORT).show()
            }
    }
}
