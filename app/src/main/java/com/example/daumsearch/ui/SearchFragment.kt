package com.example.daumsearch.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.daumsearch.R
import com.example.daumsearch.databinding.SearchFragmentBinding
import com.example.daumsearch.ui.adapter.RecyclerAdapter
import com.example.daumsearch.viewmodel.BookmarkViewModel
import com.example.daumsearch.viewmodel.WebViewModel

class SearchFragment: Fragment() {
    private val TAG = "SearchFragment"

    private var _binding: SearchFragmentBinding ? = null
    private val binding get() = _binding!!
    private val viewModel: WebViewModel by activityViewModels()
    private val bookmarkViewModel: BookmarkViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.search_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager : LayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapter = RecyclerAdapter(clickListener = {item -> bookmarkViewModel.addOrDeleteBookmark(item)})

        binding.lifecycleOwner = viewLifecycleOwner
        binding.webViewModel = viewModel

        binding.recyclerViewAll.layoutManager = linearLayoutManager
        binding.recyclerViewAll.adapter = adapter

        viewModel.webMedia.observe(viewLifecycleOwner, Observer { webMedia ->
            Log.d(TAG, webMedia.toString())
            adapter.setData(webMedia)
        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 메모리 누수 방지
    }
}