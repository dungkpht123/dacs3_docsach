package com.example.doctruyen.Adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.doctruyen.Model.BookDataTest
import com.example.doctruyen.Model.CategoryData
import com.example.doctruyen.R

class CategoryAdapter(val context: Context?, val cateData: ArrayList<CategoryData>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>(){
    var onItemClick:((BookDataTest)->Unit)?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.category_row_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        holder.cateImg.setImageResource(cateData[position].imgCategory)
        holder.categoryName.text = cateData[position].nameCategory
        holder.auditorName.text = cateData[position].nameAuditor
    }

    override fun getItemCount(): Int {
        return cateData.size
    }

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val cateImg = view.findViewById<ImageView>(R.id.img_author_lib)
        val categoryName = view.findViewById<TextView>(R.id.name_author_lib)
        val auditorName = view.findViewById<TextView>(R.id.best_auditor_name)
        val views = view.findViewById<TextView>(R.id.views)
        val color = view.findViewById<ConstraintLayout>(R.id.store_bg)

    }
}

class TopPickAdapter(val context: Context?, val cateData: List<CategoryData>) : RecyclerView.Adapter<TopPickAdapter.ViewHolder>(){

    private lateinit var adapter: Adapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopPickAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.store_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopPickAdapter.ViewHolder, position: Int) {
        holder.storeImg.setImageResource(cateData[position].imgCategory)
        holder.storeTitle.text = cateData[position].nameCategory
        holder.color.setBackgroundColor(Color.parseColor(cateData[position].Color))
    }

    override fun getItemCount(): Int {
        return cateData.size
    }

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val storeImg = view.findViewById<ImageView>(R.id.storeImg)
        val storeTitle = view.findViewById<TextView>(R.id.storeTitle)
        val color = view.findViewById<ConstraintLayout>(R.id.store_bg)

    }
}


