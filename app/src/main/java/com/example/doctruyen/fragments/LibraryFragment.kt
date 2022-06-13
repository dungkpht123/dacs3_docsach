package com.example.doctruyen.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
//import com.example.doctruyen.Adapter.AuthorLibAdapter
import com.example.doctruyen.Adapter.BestSellerAdapter
import com.example.doctruyen.Adapter.MyBookAdapter
import com.example.doctruyen.Adapter.TablayoutAdapter
import com.example.doctruyen.Model.AuthorData
import com.example.doctruyen.Model.BestSellerData
import com.example.doctruyen.R
import com.example.doctruyen.ReadBook
import com.example.doctruyen.service.FirebaseService
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson

class LibraryFragment : Fragment() {
    private lateinit var mviewpager: ViewPager
    private lateinit var mtablayout: TabLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_library, container, false)
        tabLayoutLib(view)
        return view
    }



    private fun tabLayoutLib(view: View) {
        mviewpager = view.findViewById<ViewPager>(R.id.viewpager)
        mtablayout = view.findViewById<TabLayout>(R.id.tab_layout_lib)

        val viewPagerAdapter = TablayoutAdapter(parentFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        viewPagerAdapter.addFragment(BookFragment(),"Book")
        viewPagerAdapter.addFragment(AuthorFragment(),"Author")
        viewPagerAdapter.addFragment(BookFragment(),"List")

        mviewpager.adapter = viewPagerAdapter
        mtablayout.setupWithViewPager(mviewpager)
    }
}
class BookFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_best_seller, container, false)

        val bookRW = view.findViewById<RecyclerView>(R.id.bestseller_recycler)

        val firebaseService = FirebaseService()
        firebaseService.getBook {listBook->
            val allBook = BestSellerAdapter(requireContext(), listBook)
            bookRW.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
            bookRW.adapter = allBook
            allBook.onItemClick={
//                val intent = Intent(requireContext(), ReadBook::class.java)
//                val gson = Gson()
//                val json = gson.toJson(it)
//                intent.putExtra("myBooks",json)
//                startActivity(intent)
            }
        }
        return view
    }

    private fun insertDataBook(view: View) {
//        val bookRW = view.findViewById<RecyclerView>(R.id.bestseller_recycler)
//
//        val listBook = listOf(
//            BestSellerData(R.drawable.recentimage1,"Trên đường băng","Lê Văn Tấn",14809,"Đây là một cuốn sách rất hay Đây là một cuốn sách rất hayĐây là một cuốn sách rất hay..."),
//            BestSellerData(R.drawable.recentimage2,"Giao tiếp không khó","Horron",14809,"Đây là một cuốn sách rất hay Đây là một cuốn sách rất hay ..."),
//            BestSellerData(R.drawable.recentimage2,"One Piece","Horron",14809,"Đây là một cuốn sách rất hay..."),
//            BestSellerData(R.drawable.recentimage1,"Naruto","Horron",14809,"Đây là một cuốn sách rất hay..."),
//            BestSellerData(R.drawable.recentimage1,"Trên đường băng","Lê Văn Tấn",14809,"Đây là một cuốn sách rất hay Đây là một cuốn sách rất hayĐây là một cuốn sách rất hay..."),
//            BestSellerData(R.drawable.recentimage2,"Giao tiếp không khó","Horron",14809,"Đây là một cuốn sách rất hay Đây là một cuốn sách rất hay ..."),
//            BestSellerData(R.drawable.recentimage2,"One Piece","Horron",14809,"Đây là một cuốn sách rất hay..."),
//            BestSellerData(R.drawable.recentimage1,"Naruto","Horron",14809,"Đây là một cuốn sách rất hay...")
//        )
//        val bestSellerAdapter = BestSellerAdapter(context,listBook)
//
//        bookRW.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
//        bookRW.adapter = bestSellerAdapter
    }

}

class AuthorFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_best_seller, container, false)
        insertDataAuthor(view)
        return view
    }

    private fun insertDataAuthor(view: View) {
//        val authorRW = view.findViewById<RecyclerView>(R.id.bestseller_recycler)
//
//        val listAuthor = listOf(
//            AuthorData("J. K. Rowling",R.drawable.mail,30,10000,"Bộ truyện viết về những cuộc phiêu lưu phù thủy của cậu bé Harry Potter cùng hai người bạn thân là Ronald Weasley và Hermione Granger, lấy bối cảnh tại Trường Phù thủy và Pháp sư Hogwarts nước Anh. "),
//            AuthorData("Aoyama Gōshō",R.drawable.mail,25,800,"Aoyama Gōshō từ lúc còn trẻ đã là một người vẽ rất giỏi. Khi còn học ở trường cấp 2, Aoyama đã từng đoạt giải nhất một cuộc thi vẽ tranh với bức vẽ mang tên Cuộc chiến Yukiai,"),
//            AuthorData("Oda Eiichiro",R.drawable.mail,12,1060,"Oda Eiichirō là một họa sĩ truyện tranh người Nhật Bản, hiện đang sáng tác cho nhà xuất bản Shūeisha. Ông là tác giả của bộ truyện nổi tiếng thế giới One Piece."),
//            AuthorData("J.DocWeck",R.drawable.mail,8,100,"Oda Eiichirō là một họa sĩ truyện tranh người Nhật Bản, hiện đang sáng tác cho nhà xuất bản Shūeisha. Ông là tác giả của bộ truyện nổi tiếng thế giới One Piece.")
//        )
//        val authoradapter = AuthorLibAdapter(context,listAuthor)
//
//        authorRW.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
//        authorRW.adapter = authoradapter

    }

}

class ListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_library, container, false)
        insertItemtoRecyclerView(view)
        insertDatatoLib(view)
        return view
    }

    private fun insertDatatoLib(view: View) {
//        val recyclerView_lib = view.findViewById<RecyclerView>(R.id.recyclerView_lib)
//
//        val listAuthor = listOf(
//            AuthorData("J. K. Rowling",R.drawable.mail,30,10000,"Bộ truyện viết về những cuộc phiêu lưu phù thủy của cậu bé Harry Potter cùng hai người bạn thân là Ronald Weasley và Hermione Granger, lấy bối cảnh tại Trường Phù thủy và Pháp sư Hogwarts nước Anh. "),
//            AuthorData("Aoyama Gōshō",R.drawable.mail,25,800,"Aoyama Gōshō từ lúc còn trẻ đã là một người vẽ rất giỏi. Khi còn học ở trường cấp 2, Aoyama đã từng đoạt giải nhất một cuộc thi vẽ tranh với bức vẽ mang tên Cuộc chiến Yukiai,"),
//            AuthorData("Oda Eiichiro",R.drawable.mail,12,1060,"Oda Eiichirō là một họa sĩ truyện tranh người Nhật Bản, hiện đang sáng tác cho nhà xuất bản Shūeisha. Ông là tác giả của bộ truyện nổi tiếng thế giới One Piece."),
//            AuthorData("J.DocWeck",R.drawable.mail,8,100,"Oda Eiichirō là một họa sĩ truyện tranh người Nhật Bản, hiện đang sáng tác cho nhà xuất bản Shūeisha. Ông là tác giả của bộ truyện nổi tiếng thế giới One Piece.")
//        )
//        val authoradapter = AuthorLibAdapter(context,listAuthor)
//
//        recyclerView_lib.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
//        recyclerView_lib.adapter = authoradapter

    }

    private fun insertItemtoRecyclerView(view: View) {
//        val recycler_view_lib = view.findViewById<RecyclerView>(R.id.recycler_view_lib)
//
//        val listItemList = listOf(
//            AuthorData("J. K. Rowling",R.drawable.mail,30,10000,"Bộ truyện viết về những cuộc phiêu lưu phù thủy của cậu bé Harry Potter cùng hai người bạn thân là Ronald Weasley và Hermione Granger, lấy bối cảnh tại Trường Phù thủy và Pháp sư Hogwarts nước Anh. "),
//            AuthorData("Aoyama Gōshō",R.drawable.mail,25,800,"Aoyama Gōshō từ lúc còn trẻ đã là một người vẽ rất giỏi. Khi còn học ở trường cấp 2, Aoyama đã từng đoạt giải nhất một cuộc thi vẽ tranh với bức vẽ mang tên Cuộc chiến Yukiai,"),
//            AuthorData("Oda Eiichiro",R.drawable.mail,12,1060,"Oda Eiichirō là một họa sĩ truyện tranh người Nhật Bản, hiện đang sáng tác cho nhà xuất bản Shūeisha. Ông là tác giả của bộ truyện nổi tiếng thế giới One Piece."),
//            AuthorData("J.DocWeck",R.drawable.mail,8,100,"Oda Eiichirō là một họa sĩ truyện tranh người Nhật Bản, hiện đang sáng tác cho nhà xuất bản Shūeisha. Ông là tác giả của bộ truyện nổi tiếng thế giới One Piece.")
//        )
//        val authoradapter = AuthorLibAdapter(context,listItemList)
//
//        recycler_view_lib.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
//        recycler_view_lib.adapter = authoradapter

    }
}
