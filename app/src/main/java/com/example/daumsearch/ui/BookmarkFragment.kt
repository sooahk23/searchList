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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.daumsearch.R
import com.example.daumsearch.data.TabName
import com.example.daumsearch.databinding.BookmarkFragmentBinding
import com.example.daumsearch.ui.adapter.RecyclerAdapter
import com.example.daumsearch.viewmodel.WebViewModel

class BookmarkFragment: Fragment() {
    private val TAG = "BookmarkFragment"

    private var _binding: BookmarkFragmentBinding  ? = null
    private val binding get() = _binding!!
    private val viewModel: WebViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.bookmark_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView : RecyclerView = binding.recyclerViewBookmark
        val linearLayoutManager : RecyclerView.LayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapter : RecyclerAdapter = RecyclerAdapter(emptyList())

        binding.lifecycleOwner = viewLifecycleOwner  // 중요: LiveData를 사용할 때
        binding.webViewModel = viewModel

        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter

        viewModel.webMedia.observe(viewLifecycleOwner, Observer { webMedia ->
            // UI 업데이트: 아이템 목록 표시
            adapter.setData(webMedia)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 메모리 누수 방지
    }

}