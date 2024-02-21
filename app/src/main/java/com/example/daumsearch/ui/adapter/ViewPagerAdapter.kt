package com.example.daumsearch.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.daumsearch.data.TabName
import com.example.daumsearch.ui.TabFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)
        when(position){
            0 -> return TabFragment.newInstance(TabName.Search.value)
            1 -> return TabFragment.newInstance(TabName.Bookmark.value)
        }
        return TabFragment.newInstance(TabName.Search.value)
    }

    override fun getItemCount(): Int {
        return 2 // Number of tabs
    }
}