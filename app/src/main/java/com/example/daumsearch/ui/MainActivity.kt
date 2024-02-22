package com.example.daumsearch.ui

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.daumsearch.R
import com.example.daumsearch.data.TabName
import com.example.daumsearch.databinding.ActivityMainBinding
import com.example.daumsearch.ui.adapter.ViewPagerAdapter
import com.example.daumsearch.viewmodel.WebViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var editTextSearch: EditText
    private lateinit var buttonSearch: Button
    private lateinit var adapter: ViewPagerAdapter

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: WebViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // For Fragment
        tabLayout = binding.tabLayout
        viewPager = binding.viewPager
        editTextSearch = binding.editTextSearch
        buttonSearch = binding.buttonSearch

        adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

        viewModel = ViewModelProvider(this)[WebViewModel::class.java]

        TabLayoutMediator(tabLayout, viewPager) { tab: TabLayout.Tab, position: Int ->
                tab.text = ArrayList(
                    mutableListOf(
                        TabName.Search.value,
                        TabName.Bookmark.value
                    )
                )[position]
            }
        .attach()

        buttonSearch.setOnClickListener{
            Log.d(TAG, "hello you searched for " + editTextSearch.text.toString())
            viewModel.fetchDocs(editTextSearch.text.toString())
            viewModel.fetchImages(editTextSearch.text.toString())
        }


    }

}
