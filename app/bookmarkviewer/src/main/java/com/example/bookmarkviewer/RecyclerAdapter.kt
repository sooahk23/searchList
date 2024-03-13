package com.example.bookmarkviewer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookmarkviewer.data.Document
import com.example.bookmarkviewer.data.Image
import com.example.bookmarkviewer.data.ViewType
import com.example.bookmarkviewer.data.WebMedium
import com.example.bookmarkviewer.databinding.RecyclerDocumentBinding
import com.example.bookmarkviewer.databinding.RecyclerImageBinding

class RecyclerAdapter(private val clickListener: (item: WebMedium) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<WebMedium> = emptyList()

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
                val item = items[position] as Document
                holder.binding.document = item
                holder.binding.docBookmark.setOnClickListener{clickListener(item)}
            }
            is RecyclerImgHolder -> {
                val item = items[position] as Image
                holder.binding.image = item
                holder.binding.imgbookmark.setOnClickListener{clickListener(item)}
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
        notifyDataSetChanged()
    }

}