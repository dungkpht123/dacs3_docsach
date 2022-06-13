package com.example.doctruyen

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.doctruyen.Model.BookDataTest
import com.example.doctruyen.databinding.ActivityReadBookBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson

class ReadBook : AppCompatActivity() {
    private val gson = Gson()
    private lateinit var PDFLink: String
    private var bookID = ""
    private lateinit var json: String
    private lateinit var binding : ActivityReadBookBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private companion object{
        const val TAG = "READ_BOOK_TAG"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        bookID = intent.getStringExtra("booksID")!!
//        topTrend()
        myBooks()

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        binding.btndownload.setOnClickListener {
            checkUser()
        }
    }

    private fun checkUser() {
//        val  firebaseUser = firebaseAuth.uid
//        Log.d(TAG,"firebase: $firebaseUser")
    }

    private fun myBooks() {
        val json = intent.getStringExtra("myBooks")
        PDFLink = intent.getStringExtra("PDF_mybook").toString()
        val mybooks = gson.fromJson(json,BookDataTest::class.java)
        val img = findViewById<ImageView>(R.id.avatar)
        val img_bg = findViewById<ImageView>(R.id.avatar_bg)
        val nameBook = findViewById<TextView>(R.id.nameTP)
        val author = findViewById<TextView>(R.id.nameActor)
        val descriptor = findViewById<TextView>(R.id.textnd)
        val uri = Uri.parse(mybooks.img)

        Glide.with(this).load(uri).into(img)
        Glide.with(this).load(uri).into(img_bg)

        nameBook.text = mybooks.book
        author.text = mybooks.author
        descriptor.text = mybooks.desc

        binding.reading.setOnClickListener{
            val intent = Intent(this,Doc::class.java)
            intent.putExtra("PDF_mybook",PDFLink)
            startActivity(intent)
        }
    }

    private fun topTrend() {
        val json = intent.getStringExtra("bookDataTest")
        PDFLink = intent.getStringExtra("PDF_top").toString()
        val bookDataTest = gson.fromJson(json,BookDataTest::class.java)

        val img = findViewById<ImageView>(R.id.avatar)
        val img_bg = findViewById<ImageView>(R.id.avatar_bg)
        val nameBook = findViewById<TextView>(R.id.nameTP)
        val author = findViewById<TextView>(R.id.nameActor)
        val descriptor = findViewById<TextView>(R.id.textnd)

        val uri = Uri.parse(bookDataTest.img)
        Glide.with(this).load(uri).into(img)
        Glide.with(this).load(uri).into(img_bg)
        nameBook.text = bookDataTest.book
        author.text = bookDataTest.author
        descriptor.text = bookDataTest.desc

        binding.reading.setOnClickListener {
            intent.putExtra("PDF_top",PDFLink)
            startActivity(Intent(this,Doc::class.java))
        }
    }
}
