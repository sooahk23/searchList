package com.example.bookmarkviewer

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookmarkviewer.data.Document
import com.example.bookmarkviewer.data.Image
import com.example.bookmarkviewer.data.WebMedium
import com.google.gson.Gson

import com.example.bookmarkviewer.databinding.ActivityMainBinding
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = RecyclerAdapter(clickListener = {
            Log.d("MainActivity", "item clicked")
        })

        binding.recyclerViewAll.layoutManager = linearLayoutManager
        binding.recyclerViewAll.adapter = adapter

        binding.loadBtn.setOnClickListener(View.OnClickListener {


            try {
                val cursor: Cursor? = this.contentResolver.query(Uri.parse("content://com.example.daumsearch.bookmarkprovider/bookmark"),
                    null, null, null, null)
                val webMediaList = mutableListOf<WebMedium>()

                cursor?.let {
                    Log.d("Cursor", "cursor: $cursor")
                    while (it.moveToNext()) {
                        val content = it.getString(it.getColumnIndex("content"))
                        val jsonContent = JSONObject(content)
                        val type = jsonContent.getString("type")
                        Log.d("MainActivity", "jsonContent type: $type" )
                        when (jsonContent.getString("type")) {
                            "Document" -> {
                                val document: Document = Gson().fromJson(jsonContent.toString(), Document::class.java)
                                webMediaList.add(document)
                                Log.d("MainActivity", "document: $document")
                            }
                            "Image" -> {
                                val image: Image = Gson().fromJson(jsonContent.toString(), Image::class.java)
                                webMediaList.add(image)
                                Log.d("MainActivity", "image: $image")
                            }
                        }
                    }
                    it.close()
                    adapter.setData(webMediaList)
                    adapter.notifyDataSetChanged()
                }

            } catch (e: Exception) {
                Log.d("MainActivity", "error: $e")
            }
        })
    }


}