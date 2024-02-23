package com.example.daumsearch.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daumsearch.data.Document
import com.example.daumsearch.data.Image
import com.example.daumsearch.data.ViewType
import com.example.daumsearch.data.WebMedium
import com.example.daumsearch.databinding.RecyclerDocumentBinding
import com.example.daumsearch.databinding.RecyclerImageBinding

class RecyclerAdapter(private var items: List<WebMedium>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class RecyclerDocHolder(binding: RecyclerDocumentBinding) : RecyclerView.ViewHolder(binding.root) {
        val binding:RecyclerDocumentBinding = binding
    }

    class RecyclerImgHolder(binding: RecyclerImageBinding) : RecyclerView.ViewHolder(binding.root) {
        val binding:RecyclerImageBinding = binding
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            ViewType.Document.value ->  {
                val binding = RecyclerDocumentBinding.inflate(inflater, parent, false)
                RecyclerDocHolder(binding)
            }
            ViewType.Image.value -> {
                val binding = RecyclerImageBinding.inflate(inflater, parent, false)
                RecyclerImgHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RecyclerDocHolder -> {
                val item = items[position] as Document // 데이터 타입 캐스팅
                holder.binding.document = item // 데이터 바인딩
                holder.binding.executePendingBindings() // 즉시 바인딩 실행
            }
            is RecyclerImgHolder -> {
                val item = items[position] as Image // 데이터 타입 캐스팅
                holder.binding.image = item // 데이터 바인딩
                holder.binding.executePendingBindings() // 즉시 바인딩 실행
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item : WebMedium = items[position]
        return when (item) {
            is Document -> ViewType.Document.value
            is Image -> ViewType.Image.value
            else -> ViewType.Document.value
        }
    }

    fun setData(newItems: List<WebMedium>) {
        this.items = newItems
        notifyDataSetChanged()  // 데이터가 변경되었음을 어댑터에 알립니다.
    }

}