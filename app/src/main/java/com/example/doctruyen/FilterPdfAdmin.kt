package com.example.doctruyen

import com.example.doctruyen.Adapter.AdapterPdfAdmin
import com.example.doctruyen.Model.ModelPdfAdmin

class FilterPdfAdmin :android.widget.Filter{
    lateinit var filterList : ArrayList<ModelPdfAdmin>
    lateinit var adapterPdfAdmin :AdapterPdfAdmin

    constructor(filterList: ArrayList<ModelPdfAdmin>, adapterPdfAdmin: AdapterPdfAdmin) {
        this.filterList = filterList
        this.adapterPdfAdmin = adapterPdfAdmin
    }

    constructor() : super()

    override fun performFiltering(constraint: CharSequence?): FilterResults {

        var constraint:CharSequence? = constraint
        val results = FilterResults()
        if(constraint != null && constraint.isNotEmpty()){
            constraint = constraint.toString().lowercase()
            val filterModels = ArrayList<ModelPdfAdmin>()
            for (i in filterList.indices){
                if(filterList[i].title.lowercase().contains(constraint))
                {
                    filterModels.add(filterList[i])
                }
            }
            results.count= filterModels.size
            results.values= filterModels
        }else{
            results.count= filterList.size
            results.values= filterList
        }
        return  results

    }

    override fun publishResults(constraint: CharSequence, results: FilterResults) {
   adapterPdfAdmin.pdfArrayList = results.values as ArrayList<ModelPdfAdmin>
        adapterPdfAdmin.notifyDataSetChanged()
    }
}