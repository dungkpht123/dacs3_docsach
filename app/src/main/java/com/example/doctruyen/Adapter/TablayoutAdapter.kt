package com.example.doctruyen.Adapter

import android.content.res.Resources
import android.util.Log
import androidx.fragment.app.*
import com.example.doctruyen.UserInterface.HomePage
import com.example.doctruyen.fragments.BestSellerFragment
import com.example.doctruyen.fragments.ComingSoonFragment
import com.example.doctruyen.fragments.FavouricFragment
import com.example.doctruyen.fragments.TheLatestFragment

class TablayoutAdapter(fm: FragmentManager, behavior: Int):
    FragmentStatePagerAdapter(fm, behavior) {

    var fragmentList: ArrayList<Fragment> = ArrayList()
    var fragmentTitle: ArrayList<String> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {

        return fragmentTitle[position]
    }

    fun addFragment(fragment: Fragment,title: String){
        fragmentList.add(fragment)
        fragmentTitle.add(title)
    }
}

