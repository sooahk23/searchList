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
import com.example.daumsearch.viewmodel.WebmediaResponseToWebmediaMapper.ImgMap
import com.example.daumsearch.viewmodel.WebmediaResponseToWebmediaMapper.docMap
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
    val webMedia: LiveData<List<WebMedium>> = _webMedia // 정렬된 최종 웹문서와 이미지

    var queryBefore = "" // 이전 검색어
    var docsPage = 1 // 웹문서 불러온 페이지 수
    var imgsPage = 1 // 이미지 불러온 페이지 수
    lateinit  var docsRemain : List<ResponseWebMedium> // 남겨진 정제되지 않은 웹문서 리스트
    lateinit var imgsRemain : List<ResponseWebMedium> // 남겨진 정제되지 않은 이미지 리스트
    var docsIsLast = false // 마지막 웹문서 페이지인지
    var imgsIsLast = false // 마지막 이미지 페이지인지

    private val db = DaumSearchApp.getDatabase(application)
    private val bookmarkDao: BookmarkDao = db.bookmarkDao()

    fun fetchAndCombineResults(query: String?) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                try {
//                    if (query != null && query != "") { // 처음 불러올 때
//                        Log.d(TAG, "query is not null")
                        val docsDeferred = async { RetrofitClient.daumApiService.getDocuments(query ?: queryBefore, "recency", docsPage) }
                        val imgsDeferred = async { RetrofitClient.daumApiService.getImages(query?: queryBefore, "recency", imgsPage) }

                        val docsRes = docsDeferred.await()
                        val imgsRes = imgsDeferred.await()

                        if (docsRes.isSuccessful && imgsRes.isSuccessful) {
                            var docs:List<ResponseWebMedium> = docsRes.body()?.documents ?: emptyList()
                            var imgs:List<ResponseWebMedium> = imgsRes.body()?.documents ?: emptyList()

                            docsIsLast = docsRes.body()?.meta?.is_end ?: false
                            imgsIsLast = imgsRes.body()?.meta?.is_end ?: false

//                            // TODO: !! 연산자를 사용하지 않도록 수정
//                            val lastDateTime = if (docs.last().datetime!! > imgs.last().datetime!!) docs.last().datetime else imgs.last().datetime
//
//                            docsRemain = docs.filter { item -> item.datetime!! < lastDateTime }
//                            docs = docs.filter { item -> item.datetime!! >= lastDateTime }
//
//                            imgsRemain = imgs.filter { item -> item.datetime!! < lastDateTime }
//                            imgs = imgs.filter { item -> item.datetime!! >= lastDateTime }


                            var combinedList: List<ResponseWebMedium> = docs + imgs
                            combinedList = combinedList.sortedByDescending { item -> item.datetime }

                            val combinedWebMedia: List<WebMedium> = combinedList.map{item->
                                when(item){
                                    is ResponseDocument -> {
                                        val existingBookmark = bookmarkDao.findBookmarkByContent(
                                            Gson().toJson(docMap(true, item)))
                                        WebmediaResponseToWebmediaMapper.docMap(
                                            existingBookmark != null, item)
                                    }
                                    is ResponseImage -> {
                                        val existingBookmark = bookmarkDao.findBookmarkByContent(
                                            Gson().toJson(ImgMap(true, item)))
                                        WebmediaResponseToWebmediaMapper.ImgMap(
                                            existingBookmark != null, item)
                                    }
                                    else -> throw IllegalArgumentException("Invalid Response type")
                                }
                            }
                            _webMedia.postValue(webMedia.value?.plus(combinedWebMedia))
                            if(query!=null) queryBefore = query
                            docsPage++
                            imgsPage++
                        } else {
                            Log.d(TAG, "response accepted but failed")
                        }

//                    }
//                    else if (query == null) { // 더 불러올 때
//                         Log.d(TAG, "query is null")
//                        // 이미 불러온 적이 있는 경우
//                        if (docsRemain.isEmpty()) {
//                            if (!docsIsLast){
//                                // docs 불러오기
//                                Log.d(TAG, "docs 불러오기 1")
////                                val docsDeferred = async { RetrofitClient.daumApiService.getDocuments(queryBefore, "recency", docsPage) }
////                                val docsRes = docsDeferred.await()
////
////                                if (docsRes.isSuccessful){
////                                    var docs:List<ResponseWebMedium> = docsRes.body()?.documents ?: emptyList()
////                                    docsIsLast = docsRes.body()?.meta?.is_end ?: false
////                                }
//                            }else if (!imgsIsLast){
//                                // imgs 불러오기
//                                Log.d(TAG, "imgs 불러오기 1")
//                            }else{
//                                Log.d(TAG, "All data is loaded")
//                            }
//                        }else if (imgsRemain.isEmpty()) {
//                            if (!imgsIsLast){
//                                // imgs 불러오기
//                                Log.d(TAG, "imgs 불러오기 2")
//                            }else if (!docsIsLast){
//                                // docs 불러오기
//                                Log.d(TAG, "docs 불러오기 2")
//                            }else{
//                                Log.d(TAG, "All data is loaded")
//                            }
//                        } else {
//                            Log.d(TAG, "Something went wrong")
//                        }
//
//                        // docsRemain, imgsRemain에 남아있는 데이터 합치기 진행
//
//                    }
                } catch (e: Exception) {
                    // 예외 처리, 예를 들면 네트워크 오류 등
                    Log.d(TAG, "response failed in fetchDocs")
                    Log.d(TAG, e.toString())
                }
            }
        }
    }

    fun addOrDeleteBookmark(webMedium: WebMedium) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val webMedia: List<WebMedium>? = _webMedia.value
                webMedium.bookmarked = !webMedium.bookmarked // 원래 있는지 확인하기 위해 Flag를 뒤집음
                if (webMedia != null && webMedia.contains(webMedium)) {
                    val index = webMedia.indexOf(webMedium)
                    webMedia[index].bookmarked = !webMedium.bookmarked // 다시 Flag를 원래대로 적용
                    _webMedia.postValue(webMedia?: emptyList())
                }
            }
        }
    }
}
