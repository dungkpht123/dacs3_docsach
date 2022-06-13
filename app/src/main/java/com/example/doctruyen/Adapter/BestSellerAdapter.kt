package com.example.doctruyen.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.doctruyen.Model.BestSellerData
import com.example.doctruyen.Model.BookDataTest
import com.example.doctruyen.R
import com.example.doctruyen.databinding.BestSellerItemBinding

class BestSellerAdapter(val context: Context, val bookData: List<BookDataTest>) : RecyclerView.Adapter<BestSellerAdapter.ViewHolder>(){

    var onItemClick:((BookDataTest)->Unit)?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestSellerAdapter.ViewHolder {
        val binding =
            BestSellerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BestSellerAdapter.ViewHolder, position: Int) {
        holder.bookName.text = bookData[position].book
        holder.author.text = bookData[position].author
        holder.desc.text = bookData[position].desc
        val uri = Uri.parse(bookData[position].img.toString())
        holder.bind(uri)
    }

    override fun getItemCount(): Int {
        return bookData.size
    }

    inner class ViewHolder(binding:BestSellerItemBinding): RecyclerView.ViewHolder(binding.root){
        val bookImg =binding.imgAuthorLib
        val bookName = binding.nameAuthorLib
        val author = binding.bestAuditorName
        val desc = binding.descAuthorLib

        fun bind(uri: Uri) {
            Glide.with(context).load(uri).into(bookImg)
        }
        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(bookData[adapterPosition])
            }
        }
    }

}

class FavouricAdapter(val context: Context?, val theLatestrData: List<BestSellerData>) : RecyclerView.Adapter<FavouricAdapter.ViewHolder>(){

    private lateinit var adapter: Adapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouricAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.best_seller_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouricAdapter.ViewHolder, position: Int) {
        holder.bestSellerImg.setImageResource(theLatestrData[position].imgCategory)
        holder.bestSellerName.text = theLatestrData[position].nameCategory
        holder.best_auditor_name.text = theLatestrData[position].nameAuditor
        holder.bestSellerViews.text = "( ${theLatestrData[position].views.toString() +')'}"
        holder.bestSellerDesc.text = "${theLatestrData[position].descBestSeller }" + "..."

    }

    override fun getItemCount(): Int {
        return theLatestrData.size
    }

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val bestSellerImg = view.findViewById<ImageView>(R.id.img_author_lib)
        val bestSellerName = view.findViewById<TextView>(R.id.name_author_lib)
        val best_auditor_name = view.findViewById<TextView>(R.id.best_auditor_name)
        val bestSellerViews = view.findViewById<TextView>(R.id.views)
        val bestSellerDesc = view.findViewById<TextView>(R.id.descAuthor_lib)

    }
}

class ComingSoonAdapter(val context: Context?, val theLatestrData: List<BestSellerData>) : RecyclerView.Adapter<ComingSoonAdapter.ViewHolder>(){

    private lateinit var adapter: Adapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComingSoonAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.best_seller_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComingSoonAdapter.ViewHolder, position: Int) {
        holder.bestSellerImg.setImageResource(theLatestrData[position].imgCategory)
        holder.bestSellerName.text = theLatestrData[position].nameCategory
        holder.best_auditor_name.text = theLatestrData[position].nameAuditor
        holder.bestSellerViews.text = "( ${theLatestrData[position].views.toString() +')'}"
        holder.bestSellerDesc.text = "${theLatestrData[position].descBestSeller }" + "..."

    }

    override fun getItemCount(): Int {
        return theLatestrData.size
    }

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val bestSellerImg = view.findViewById<ImageView>(R.id.img_author_lib)
        val bestSellerName = view.findViewById<TextView>(R.id.name_author_lib)
        val best_auditor_name = view.findViewById<TextView>(R.id.best_auditor_name)
        val bestSellerViews = view.findViewById<TextView>(R.id.views)
        val bestSellerDesc = view.findViewById<TextView>(R.id.descAuthor_lib)

    }
}

