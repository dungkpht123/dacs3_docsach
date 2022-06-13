package com.example.doctruyen.Adapter

import com.example.doctruyen.R

class ReadingAdapter {
}

//package com.example.doctruyen
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Adapter
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import java.security.AccessControlContext
//
//class ReadingAdapter(val cateData: List<ReadingModel>) :
//    RecyclerView.Adapter<ReadingAdapter.ViewHolder>() {
//
//    private lateinit var adapter: Adapter
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReadingAdapter.ViewHolder {
//        val view =
//            LayoutInflater.from(parent.context).inflate(R.layout.profile_image, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ReadingAdapter.ViewHolder, position: Int) {
//        holder.imgseller.setImageResource(cateData[position].imgseller)
//        holder.nameseller.text = cateData[position].nameseller
//        holder.auditorName.text = cateData[position].nameAuditor
//        holder.views.text = cateData[position].views
//        holder.descbook.text = cateData[position].views
//    }
//
//    override fun getItemCount(): Int {
//        return cateData.size
//    }
//
//    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val imgseller = view.findViewById<ImageView>(R.id.bestSeller_img)
//        val nameseller = view.findViewById<TextView>(R.id.bestSeller_name)
//        val auditorName = view.findViewById<TextView>(R.id.best_auditor_name)
//        val views = view.findViewById<TextView>(R.id.views)
//        val descbook = view.findViewById<TextView>(R.id.descBook)
////        val rating =  view.fi
//
//    }
//}