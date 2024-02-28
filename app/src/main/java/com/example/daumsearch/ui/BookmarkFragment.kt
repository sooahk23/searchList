package com.example.daumsearch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.daumsearch.R
import com.example.daumsearch.databinding.BookmarkFragmentBinding
import com.example.daumsearch.ui.adapter.RecyclerAdapter
import com.example.daumsearch.viewmodel.BookmarkViewModel
import com.example.daumsearch.viewmodel.WebViewModel

class BookmarkFragment: Fragment() {
    private val TAG = "BookmarkFragment"

    private var _binding: BookmarkFragmentBinding  ? = null
    private val binding get() = _binding!!
    private val viewModel: WebViewModel by activityViewModels()
    private val bookmarkViewModel: BookmarkViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.bookmark_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager : RecyclerView.LayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapter = RecyclerAdapter(clickListener = {item ->
            bookmarkViewModel.addOrDeleteBookmark(item)
            // code to apply bookmarked status
        })
        //

        binding.lifecycleOwner = viewLifecycleOwner
        binding.webViewModel = viewModel

        binding.recyclerViewBookmark.layoutManager = linearLayoutManager
        binding.recyclerViewBookmark.adapter = adapter

        viewModel.webMedia.observe(viewLifecycleOwner, Observer { webMedia ->
            adapter.setData(webMedia.filter { item -> item.bookmarked } )
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 메모리 누수 방지
    }

}