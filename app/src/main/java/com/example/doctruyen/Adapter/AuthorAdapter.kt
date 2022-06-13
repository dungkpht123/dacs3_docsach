package com.example.doctruyen.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.doctruyen.Model.AuthorData
import com.example.doctruyen.Model.BookDataTest
import com.example.doctruyen.databinding.TopAuthorItemBinding

class AuthorAdapter(val context: Context, val authorData: List<AuthorData>) : RecyclerView.Adapter<AuthorAdapter.ViewHolder>() {
    var onItemClick: ((AuthorData) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            TopAuthorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AuthorAdapter.ViewHolder, position: Int) {
//        holder.bookName.text = authorData[position].book
//        holder.author.text = authorData[position].author
        val uri = Uri.parse(authorData[position].imgAuthor.toString())
        holder.bind(uri)
        holder.authorName.text = authorData[position].nameAuthor
        holder.authortotalBook.text = authorData[position].totalBook
        holder.storeTitle.text = authorData[position].views
    }

    override fun getItemCount(): Int {
        return authorData.size
    }

    inner class ViewHolder(binding: TopAuthorItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val authorImg = binding.authorImg
        val authorName = binding.authorName
        val authortotalBook = binding.authortotalBook
        val storeTitle = binding.storeTitle

        fun bind(uri: Uri) {
            Glide.with(context).load(uri).into(authorImg)
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(authorData[adapterPosition])

            }
        }
    }
}

//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
////        holder.authorImg.setImageResource(authorData[position].imgAuthor)
////        val uri = Uri.parse(authorData[position].imgAuthor.toString())
////        holder.bind(uri)
////        holder.authorName.text = authorData[position].nameAuthor
////        holder.authortotalBook.text = "${authorData[position].totalBook.toString() + 'M'} books"
////        holder.totalViews.text = "${authorData[position].views.toString()} views"
//    }
//
//    override fun getItemCount(): Int {
//        return authorData.size
//    }
//
//    inner class ViewHolder(view: TopAuthorItemBinding): RecyclerView.ViewHolder(view){
////        val authorImg = view.findViewById<ImageView>(R.id.authorImg)
////        val authorName = view.findViewById<TextView>(R.id.authorName)
////        val authortotalBook = view.findViewById<TextView>(R.id.authortotalBook)
////        val totalViews = view.findViewById<TextView>(R.id.storeTitle)
//    }
//}
//
//class AuthorLibAdapter(val context: Context?, val authorData: List<AuthorData>) : RecyclerView.Adapter<AuthorLibAdapter.ViewHolder>() {
//    private lateinit var adapter: Adapter
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(context).inflate(R.layout.author_item_lib,parent,false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
////        holder.img_author_lib.setImageResource(authorData[position].imgAuthor)
////        holder.name_author_lib.text = authorData[position].nameAuthor
////        holder.total_book_lib.text = "${authorData[position].totalBook.toString() + 'M'} books"
////        holder.total_view_lib.text = "${authorData[position].views.toString()} views"
////        holder.descAuthor_lib.text = authorData[position].descAuthor
//    }
//
//    override fun getItemCount(): Int {
//        return authorData.size
//    }
//
//    inner class ViewHolder(binding: CategoryRowItemBinding): RecyclerView.ViewHolder(binding.root)){
////        val img_author_lib = view.findViewById<ImageView>(R.id.img_author_lib)
////        val name_author_lib = view.findViewById<TextView>(R.id.name_author_lib)
////        val total_book_lib = view.findViewById<TextView>(R.id.total_book_lib)
////        val total_view_lib = view.findViewById<TextView>(R.id.total_view_lib)
////        val descAuthor_lib = view.findViewById<TextView>(R.id.descAuthor_lib)
//
//        fun bind(uri: Uri) {
//            Glide.with(context).load(uri).into(img_author_lib)
//        }
//        init {
//            binding.root.setOnClickListener {
//                onItemClick?.invoke(bookData[adapterPosition])
//
//            }
//        }
//
//    }
//}

