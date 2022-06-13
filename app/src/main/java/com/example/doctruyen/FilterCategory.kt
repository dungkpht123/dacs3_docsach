package com.example.doctruyen

import android.widget.Filter
import com.example.doctruyen.Adapter.AdminCategoryAdapter
import com.example.doctruyen.firebase.fireBase_Category

class FilterCategory: Filter {
    private var filterList: ArrayList<fireBase_Category>
//
    private var adapterCategory: AdminCategoryAdapter
//
    constructor(
        filterList: ArrayList<fireBase_Category>,
        adapterCategory: AdminCategoryAdapter
    ) : super() {
        this.filterList = filterList
        this.adapterCategory = adapterCategory
    }
//
    override fun performFiltering(constraint: CharSequence?): FilterResults {
//        var constraint = constraint
        val results = FilterResults()
//
//        if(constraint != null && constraint.isNotEmpty())
//        {
//            constraint = constraint.toString().uppercase()
//            val filterModel: ArrayList<fireBase_Category> = ArrayList()
//            for(i in 0 until filterList.size){
//                if(filterList[i].category.uppercase().contains(constraint)){
//                    filterModel.add(filterList[i])
//                }
//            }
//            results.count = filterModel.size
//            results.values = filterModel
//        }else{
//            results.count = filterList.size
//            results.values = filterList
//        }
//
        return results
    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults) {
//        adapterCategory.categoryArrayList = results.values as ArrayList<fireBase_Category>
//
//        adapterCategory.notifyDataSetChanged()


    }
}