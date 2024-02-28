package com.example.daumsearch.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daumsearch.DaumSearchApp

import com.example.daumsearch.data.WebMedium
import com.example.daumsearch.db.Bookmark
import com.example.daumsearch.db.BookmarkDao
import com.example.daumsearch.db.Converters
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookmarkViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = "BookmarkViewModel"

    private val _bookmarks = MutableLiveData<List<Bookmark>>(emptyList())
    val bookmarks: LiveData<List<Bookmark>> =_bookmarks

    private val db = DaumSearchApp.getDatabase(application)
    private val bookmarkDao: BookmarkDao = db. bookmarkDao()

//    fun addBookmark(webMedium: WebMedium) {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO){
//                val bookmark = Bookmark(content = webMedium)
//                bookmarkDao.insertBookmark(bookmark)
//            }
//        }
//    }
//
//    fun removeBookmark(bookmark: Bookmark) {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO){
//                bookmarkDao.deleteBookmark(bookmark)
//            }
//        }
//    }

    fun fetchBookmarks() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _bookmarks.postValue(bookmarkDao.getAllBookmarks())
            }
        }
    }

    fun addOrDeleteBookmark(webMedium: WebMedium) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val existingBookmark = bookmarkDao.findBookmarkByContent(Gson().toJson(webMedium))
                if (existingBookmark != null) {
                    bookmarkDao.deleteBookmark(existingBookmark)
                } else {
                    bookmarkDao.insertBookmark(Bookmark(content = webMedium))
                }
                // 이걸 항상 부르는 게 맞나?
                _bookmarks.postValue(bookmarkDao.getAllBookmarks())
            }
        }
    }

}