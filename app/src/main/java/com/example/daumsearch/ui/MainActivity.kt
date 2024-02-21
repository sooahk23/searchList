package com.example.daumsearch.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.example.daumsearch.R
import com.example.daumsearch.data.TabName
import com.example.daumsearch.databinding.ActivityMainBinding
import com.example.daumsearch.ui.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: ViewPagerAdapter

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // For Fragment
        tabLayout = binding.tabLayout
        viewPager = binding.viewPager

        adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter


        TabLayoutMediator(tabLayout, viewPager) { tab: TabLayout.Tab, position: Int ->
                tab.text = ArrayList(
                    mutableListOf(
                        TabName.Search.value,
                        TabName.Bookmark.value
                    )
                )[position]
            }
        .attach()
    }

}