package com.example.daumsearch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.daumsearch.data.TabName
import com.example.daumsearch.R

class TabFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val category = arguments?.getString("category", "")
//        val view: View
        return when (category) {
            TabName.Search.value -> inflater.inflate(R.layout.search_fragment, container, false)
            TabName.Bookmark.value -> inflater.inflate(R.layout.bookmark_fragment, container, false)
            else -> inflater.inflate(R.layout.search_fragment, container, false)
        }
    }

    companion object {
        private const val TAG = "TabFragment"

        fun newInstance(category: String?): TabFragment {
            val fragment = TabFragment()
            val args = Bundle()
            args.putString("category", category) // 카테고리를 변수로 보내주기
            fragment.arguments = args
            return fragment
        }
    }
}