package com.example.doctruyen.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.doctruyen.Adapter.BestSellerAdapter
import com.example.doctruyen.Model.BestSellerData
import com.example.doctruyen.R
import com.example.doctruyen.ReadBook
import com.example.doctruyen.service.FirebaseService
import com.google.gson.Gson

class TheLatestFragment : Fragment() {
    private lateinit var theLatestRW: RecyclerView
    private lateinit var theLatestAdapter: BestSellerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_the_latest, container, false)
        initRecyclerViewTheLatest(view)
        return view
    }

    private fun initRecyclerViewTheLatest(view: View) {
        theLatestRW = view.findViewById<RecyclerView>(R.id.thelatestrecycle)

        val firebaseService = FirebaseService()
        firebaseService.getBookLatest { listBook ->
            theLatestAdapter = BestSellerAdapter(requireContext(), listBook)
            theLatestRW.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            theLatestRW.adapter = theLatestAdapter
            theLatestAdapter.onItemClick = {
                val intent = Intent(requireContext(), ReadBook::class.java)
                val gson = Gson()
                val json = gson.toJson(it)
                val PDF = it.nd
                intent.putExtra("myBooks", json)
                intent.putExtra("PDF", PDF)
                startActivity(intent)
            }

//        val theLatestRW = view.findViewById<RecyclerView>(R.id.thelatestrecycle)
//
//        val listTheLatest = listOf(
//            BestSellerData(R.drawable.recentimage1,"Trên đường băng","Lê Văn Tấn",14809,"Đây là một cuốn sách rất hay Đây là một cuốn sách rất hayĐây là một cuốn sách rất hay..."),
//            BestSellerData(R.drawable.recentimage2,"Giao tiếp không khó","Horron",14809,"Đây là một cuốn sách rất hay Đây là một cuốn sách rất hay ..."),
//            BestSellerData(R.drawable.recentimage2,"One Piece","Horron",14809,"Đây là một cuốn sách rất hay..."),
//            BestSellerData(R.drawable.recentimage1,"Naruto","Horron",14809,"Đây là một cuốn sách rất hay..."),
//        )
//        val bestSellerAdapter = TheLatestAdapter(context,listTheLatest)
//
//        theLatestRW.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
//        theLatestRW.adapter = bestSellerAdapter
        }

    }
}