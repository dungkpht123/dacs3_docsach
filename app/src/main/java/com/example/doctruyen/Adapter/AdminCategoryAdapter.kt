package com.example.doctruyen.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.doctruyen.FilterCategory
import com.example.doctruyen.PdfListAdminActivity
import com.example.doctruyen.R
import com.example.doctruyen.databinding.AdminRowCategoryItemBinding
import com.example.doctruyen.firebase.Test
import com.google.firebase.database.FirebaseDatabase

class AdminCategoryAdapter(val context: Context, var categoryArrayList: ArrayList<Test>):
    RecyclerView.Adapter<AdminCategoryAdapter.HolderCategory>() {

    var onItemClick:((Test)->Unit)?=null

    inner class HolderCategory(binding:AdminRowCategoryItemBinding): RecyclerView.ViewHolder(binding.root){
        var categoryTV = binding.categoryTV
        var deleteBtn = binding.deleteBtn
        init {
            binding.deleteBtn.setOnClickListener {
                onItemClick?.invoke(categoryArrayList[adapterPosition])
            }
            binding.categoryTV.setOnClickListener {
                val intent = Intent(context,PdfListAdminActivity::class.java)
                var model =categoryArrayList.get(position)

                Log.i("text :", "${model.id}")
                var id = model.id
                var category= model.category
                var uid = model.uid
                var timestamp = model.timestamp
                intent.putExtra("categoryid",id)
                intent.putExtra("category",category)
                context.startActivity(intent)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderCategory {
        val binding =
            AdminRowCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HolderCategory(binding)
    }

    override fun onBindViewHolder(holder: HolderCategory, position: Int) {
        holder.categoryTV.text = categoryArrayList[position].category

    }

    override fun getItemCount(): Int {
       return categoryArrayList.size
    }

}