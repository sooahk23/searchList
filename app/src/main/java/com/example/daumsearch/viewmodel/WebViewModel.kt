package com.example.daumsearch.viewmodel


import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.daumsearch.DaumSearchApp
import com.example.daumsearch.api.RetrofitClient
import com.example.daumsearch.data.Document
import com.example.daumsearch.data.Image
import com.example.daumsearch.data.ResponseDocument
import com.example.daumsearch.data.ResponseDocuments
import com.example.daumsearch.data.ResponseImage
import com.example.daumsearch.data.ResponseImages
import com.example.daumsearch.data.ResponseWebMedium
import com.example.daumsearch.data.WebMedium
import com.example.daumsearch.db.BookmarkDao
import com.example.daumsearch.util.DateTimeUtils
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.ignoreIoExceptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse

class WebViewModel (application: Application) : AndroidViewModel(application){
    private val TAG = "WebViewModel"

    private val _webMedia = MutableLiveData<List<WebMedium>>(emptyList())
    val webMedia: LiveData<List<WebMedium>> = _webMedia

    private val db = DaumSearchApp.getDatabase(application)
    private val bookmarkDao: BookmarkDao = db. bookmarkDao()

    fun fetchAndCombineResults(query: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                try {
                    val docsDeferred = async { RetrofitClient.daumApiService.getDocuments(query, "recency") }
                    val imgsDeferred = async { RetrofitClient.daumApiService.getImages(query, "recency") }

                    val docsRes = docsDeferred.await()
                    val imgsRes = imgsDeferred.await()

                    if (docsRes.isSuccessful && imgsRes.isSuccessful) {
                        val docs:List<ResponseWebMedium> = docsRes.body()?.documents ?: emptyList()
                        val imgs:List<ResponseWebMedium> = imgsRes.body()?.documents ?: emptyList()

                        var combinedList: List<ResponseWebMedium> = docs + imgs
                        combinedList = combinedList.sortedByDescending { item -> item.datetime }
                        val combinedWebMedia: List<WebMedium> = combinedList.map{item->
                            when(item){
                                is ResponseDocument -> {
                                    val existingBookmark = bookmarkDao.findBookmarkByContent(
                                        Gson().toJson(Document(
                                        item.title ?: "", item.contents ?: "", item.url ?: "", item.datetime.toString(), true
                                    )))
                                    WebmediaResponseToWebmediaMapper.docMap(existingBookmark!=null, item)
                                }
                                is ResponseImage -> {
                                    val existingBookmark = bookmarkDao.findBookmarkByContent(
                                        Gson().toJson(
                                            Image(
                                                item.collection ?: "",
                                                item.thumbnail_url ?: "",
                                                item.image_url ?: "",
                                                item.width ?: 0,
                                                item.height ?: 0,
                                                item.display_sitename ?: "",
                                                item.doc_url ?: "",
                                                datetime = DateTimeUtils.formatIsoDateTime(item.datetime.toString()),
                                                true
                                            )
                                        )
                                    )
                                    WebmediaResponseToWebmediaMapper.ImgMap(
                                        existingBookmark != null,
                                        item
                                    )
                                }
                                else -> throw IllegalArgumentException("Invalid Response type")
                            }
                        }
                        _webMedia.postValue(combinedWebMedia)
                    } else {
                        // 오류 처리
                        Log.d(TAG, "response accepted but failed")
                    }
                } catch (e: Exception) {
                    // 예외 처리, 예를 들면 네트워크 오류 등
                    Log.d(TAG, "response failed in fetchDocs")
                    Log.d(TAG, e.toString())
                }
            }
        }
    }
}
