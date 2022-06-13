package com.example.doctruyen.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.doctruyen.Adapter.ComingSoonAdapter
import com.example.doctruyen.Adapter.FavouricAdapter
import com.example.doctruyen.Model.BestSellerData
import com.example.doctruyen.R


class ComingSoonFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_coming_soon, container, false)
        initRecyclerViewComingSoon(view)
        return view
    }
    private fun initRecyclerViewComingSoon(view: View) {
        val comingSoonRW = view.findViewById<RecyclerView>(R.id.comingsoon_recycler)

        val listFavouric = listOf(
            BestSellerData(R.drawable.recentimage1,"Trên đường băng","Lê Văn Tấn",14809,"Đây là một cuốn sách rất hay Đây là một cuốn sách rất hayĐây là một cuốn sách rất hay..."),
            BestSellerData(R.drawable.recentimage2,"Giao tiếp không khó","Horron",14809,"Đây là một cuốn sách rất hay Đây là một cuốn sách rất hay ..."),
            BestSellerData(R.drawable.recentimage2,"One Piece","Horron",14809,"Đây là một cuốn sách rất hay..."),
            BestSellerData(R.drawable.recentimage1,"Naruto","Horron",14809,"Đây là một cuốn sách rất hay..."),
        )
        val bestSellerAdapter = ComingSoonAdapter(context,listFavouric)

        comingSoonRW.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
        comingSoonRW.adapter = bestSellerAdapter
    }

}