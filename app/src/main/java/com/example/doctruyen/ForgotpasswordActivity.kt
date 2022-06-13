package com.example.doctruyen

import android.app.ProgressDialog
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.doctruyen.databinding.ActivityForgotpasswordBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class ForgotpasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotpasswordBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog:ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotpasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        binding.submitBtn.setOnClickListener {
            validateData()
        }
    }
    private var email =""
    private fun validateData() {
        email = binding.emailEt.text.toString().trim()
        if(email.isEmpty()){
            Toast.makeText(this,"Enter email ...",Toast.LENGTH_SHORT).show()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this,"Invalid email patter ...",Toast.LENGTH_SHORT).show()

        } else{
            recoverPassword()
        }
    }

    private fun recoverPassword() {
//        show progress
        progressDialog.setMessage("sending password  reset instructions to $email")
        progressDialog.show()
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnFailureListener { e->

                progressDialog.dismiss()
                Toast.makeText(this,"Failed to add due to ${e.message}",Toast.LENGTH_SHORT).show()

            }
            .addOnSuccessListener {
//                send
                progressDialog.dismiss()
                Toast.makeText(this,"Instruction send to \n $email ...",Toast.LENGTH_SHORT).show()

            }
    }
}
