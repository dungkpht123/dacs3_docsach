package com.example.doctruyen.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.doctruyen.Adapter.BestSellerAdapter
import com.example.doctruyen.Adapter.TablayoutAdapter
import com.example.doctruyen.Adapter.TopTrendAdapter
import com.example.doctruyen.Model.BestSellerData
import com.example.doctruyen.Model.BookDataTest
import com.example.doctruyen.R
import com.example.doctruyen.ReadBook
import com.example.doctruyen.service.FirebaseService
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson


class BestSellerFragment : Fragment() {
    private lateinit var bestSellerRW: RecyclerView
    private lateinit var bestSellerAdapter: BestSellerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_best_seller, container, false)
        initRecyclerViewBestSeller(view)
        return view
    }

    private fun initRecyclerViewBestSeller(view: View) {
        bestSellerRW = view.findViewById<RecyclerView>(R.id.bestseller_recycler)

        val firebaseService = FirebaseService()
        firebaseService.getBookSeller {listBook->
            bestSellerAdapter = BestSellerAdapter(requireContext(), listBook)
            bestSellerRW.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
            bestSellerRW.adapter = bestSellerAdapter
            bestSellerAdapter.onItemClick={
                val intent = Intent(requireContext(), ReadBook::class.java)
                val gson = Gson()
                val json = gson.toJson(it)
                val PDF = it.nd
                intent.putExtra("myBooks",json)
                intent.putExtra("PDF",PDF)
                startActivity(intent)
            }
        }
    }
}