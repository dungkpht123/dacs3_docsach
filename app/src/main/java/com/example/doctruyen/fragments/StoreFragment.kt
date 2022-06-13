package com.example.doctruyen.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.doctruyen.Adapter.CategoryAdapter
import com.example.doctruyen.Adapter.TopPickAdapter
import com.example.doctruyen.Model.CategoryData
import com.example.doctruyen.R

class StoreFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_store, container, false)
        storePage(view)
        allCate(view)
        return view
    }

    private fun allCate(view: View) {
        val allCateRW = view.findViewById<RecyclerView>(R.id.allCategoryRecycler)

        val listTopPick = listOf(
            CategoryData(R.drawable.harry_potter,"Action","J.Docweck",1850,"#F86C6D"),
            CategoryData(R.drawable.harry_potter,"Action","J.Docweck",1850,"#51AFEB"),
            CategoryData(R.drawable.harry_potter,"Action","J.Docweck",1850,"#752294"),
            CategoryData(R.drawable.riseoffirstworld,"Action","J.Docweck",1850,"#767575"),
            CategoryData(R.drawable.harry_potter2,"Action","J.Docweck",1850,"#752294"),
            CategoryData(R.drawable.harry_potter2,"Action","J.Docweck",1850,"#5FB25F")
        )

        val allCateAdapter = TopPickAdapter(context, listTopPick)
        allCateRW.layoutManager = GridLayoutManager(context,2,RecyclerView.VERTICAL,false)
        allCateRW.adapter = allCateAdapter
    }

    private fun storePage(view: View) {
        val topPickRW = view.findViewById<RecyclerView>(R.id.top_pick_recycler)

        val listTopPick = listOf(
            CategoryData(R.drawable.harry_potter,"Am Lake","J.Docweck",1850,"#F86C6D"),
            CategoryData(R.drawable.harry_potter,"Am Lake","J.Docweck",1850,"#51AFEB"),
            CategoryData(R.drawable.harry_potter2,"Am Lake","J.Docweck",1850,"#752294"),
            CategoryData(R.drawable.harry_potter,"Am Lake","J.Docweck",1850,"#DB6969"),
        )

        val topPickAdapter = TopPickAdapter(context, listTopPick)
        topPickRW.layoutManager = GridLayoutManager(context,2,RecyclerView.VERTICAL,false)
        topPickRW.adapter = topPickAdapter

    }

}