package com.example.doctruyen.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.doctruyen.FilterPdfAdmin
import com.example.doctruyen.Model.ModelPdfAdmin
import com.example.doctruyen.PdfDetailActivity
import com.example.doctruyen.PdfEditActivity
import com.example.doctruyen.databinding.RowPdfAdminBinding
import java.util.ArrayList

class AdapterPdfAdmin :RecyclerView.Adapter<AdapterPdfAdmin.HolderPdfAdmin>,Filterable{
    private lateinit var binding:RowPdfAdminBinding
    private var context :Context
    public var pdfArrayList : ArrayList<ModelPdfAdmin>
    private val filterList:ArrayList<ModelPdfAdmin>
  private var filter :FilterPdfAdmin?= null
    constructor(

        context: Context,
        pdfArrayList: ArrayList<ModelPdfAdmin>
    ) : super() {

        this.context = context
        this.pdfArrayList = pdfArrayList
        this.filterList = pdfArrayList
    }

    inner class HolderPdfAdmin(itemView:View):RecyclerView.ViewHolder(itemView){
        val pdfView = binding.pdfView
        val processBar = binding.progressBar
        val titleTv = binding.titleTv
        val description = binding.desciptionTv
        val categoryTv = binding.categoryTV
        val sizeTv = binding.sizeTv
        val dateTv = binding.dateTv
        val modeBtn = binding.moreBtn
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderPdfAdmin {
        binding = RowPdfAdminBinding.inflate(LayoutInflater.from(context),parent,false)
        return HolderPdfAdmin(binding.root)
    }

    override fun onBindViewHolder(holder: HolderPdfAdmin, position: Int) {
//            get data set dâta
        val model = pdfArrayList[position]
        val pdfId = model.id
        val categoryId = model.categoryId
        val title = model.title
        val description = model.description
        val pdfUrl = model.url
        val timestamp = model.timestamp
        val formattedDate = MyApplication.formatTimeStamp(timestamp)
        holder.titleTv.text =title
        holder.description.text = description
        holder.dateTv.text= formattedDate
        MyApplication.loadCategory(categoryId,holder.categoryTv)
        MyApplication.loadPdfFromUrlSinglePage(pdfUrl,title,holder.pdfView,holder.processBar,null)
        MyApplication.loadPdfSize(pdfUrl,title,holder.sizeTv)
        holder.modeBtn.setOnClickListener {
            moreOptionsDialog(model,holder)
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context,PdfDetailActivity::class.java)
            intent.putExtra("bookId",pdfId)
            context.startActivity(intent)
        }
    }

    private fun moreOptionsDialog(model: ModelPdfAdmin, holder: AdapterPdfAdmin.HolderPdfAdmin) {
//
        val bookId = model.id
        val bookUrl = model.url
        val bookTitlle = model.title

        val options = arrayOf("Edit","Delete")
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Choose Option")
            .setItems(options){dialog,position->
                if(position==0){
                    val intent = Intent(context,PdfEditActivity::class.java)
                    intent.putExtra("bookId",bookId)
                    context.startActivity(intent)

                }else if(position==1){
                    MyApplication.deleteBook(context,bookId,bookUrl,bookTitlle)
                }
            }.show()
    }

    override fun getItemCount(): Int {
      return pdfArrayList.size
    }

    override fun getFilter(): Filter {
        if(filter== null){
            filter = FilterPdfAdmin(filterList,this)
            filter = FilterPdfAdmin(filterList,this)

        }
        return filter as FilterPdfAdmin
    }
}