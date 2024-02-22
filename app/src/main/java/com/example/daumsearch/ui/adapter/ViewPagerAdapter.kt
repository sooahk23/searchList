package com.example.daumsearch.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.daumsearch.ui.BookmarkFragment
import com.example.daumsearch.ui.SearchFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> SearchFragment()
            1 -> BookmarkFragment()
            else -> SearchFragment()
        }
    }

    override fun getItemCount(): Int {
        return 2 // Number of tabs
    }
}