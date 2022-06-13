package com.example.doctruyen.service

import android.util.Log
import com.example.doctruyen.Model.AuthorData
import com.example.doctruyen.Model.BookDataTest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseService {
    private val db = Firebase.firestore
    private val tableBooks = db.collection("Books")
    private val BestSeller = db.collection("BookSeller")
    private val BookLatest = db.collection("BookLatest")
    private val AuthorData = db.collection("Author")


    fun getBook(callback: (data: List<BookDataTest>) -> Unit){
        tableBooks.get().addOnSuccessListener { result ->
            var arrayBookDataTest = ArrayList<BookDataTest>()
            for (document in result) {
                var bookTest = document.toObject(BookDataTest::class.java)
                arrayBookDataTest.add(bookTest)
            }
            callback.invoke(arrayBookDataTest)
//            callback.invoke(arrayListStopPointInfo)
        }.addOnFailureListener {
            Log.i("test","$it")
        }
    }
    fun getBookSeller(callback: (data: List<BookDataTest>) -> Unit){
        BestSeller.get().addOnSuccessListener { result ->
            var arrayBookSellerData= ArrayList<BookDataTest>()
            for (document in result) {
                var bookTest = document.toObject(BookDataTest::class.java)
                arrayBookSellerData.add(bookTest)
            }
            callback.invoke(arrayBookSellerData)
        }.addOnFailureListener {
            Log.i("test","$it")
        }
    }
    fun getBookLatest(callback: (data: List<BookDataTest>) -> Unit){
        BookLatest.get().addOnSuccessListener { result ->
            var arrayBookLatestData= ArrayList<BookDataTest>()
            for (document in result) {
                var bookTest = document.toObject(BookDataTest::class.java)
                arrayBookLatestData.add(bookTest)
            }
            callback.invoke(arrayBookLatestData)
//            callback.invoke(arrayListStopPointInfo)
        }.addOnFailureListener {
            Log.i("test","$it")
        }
    }
    fun getAuthorData(callback: (data: List<AuthorData>) -> Unit){
        AuthorData.get().addOnSuccessListener { result ->
            var arrayAuthorData= ArrayList<AuthorData>()
            for (document in result) {
                var bookTest = document.toObject(com.example.doctruyen.Model.AuthorData::class.java)
                arrayAuthorData.add(bookTest)
            }
            callback.invoke(arrayAuthorData)
//            callback.invoke(arrayListStopPointInfo)
        }.addOnFailureListener {
            Log.i("test","$it")
        }
    }
}