package com.example.doctruyen.Model

data class BookData(
    val imgBook: Int,
    val nameBook: String,
    val nameAuditorofBook: String,
    val viewsBook: Int
) {
}

data class BookDataTest(
    val author: String? = null,
    val book: String? = null,
    val desc: String? = null,
    val nd: String? = null,
    val img:String?=null
) {
}