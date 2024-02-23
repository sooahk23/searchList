package com.example.daumsearch.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.daumsearch.api.RetrofitClient
import com.example.daumsearch.data.Document
import com.example.daumsearch.data.Image
import com.example.daumsearch.data.ResponseDocument
import com.example.daumsearch.data.ResponseDocuments
import com.example.daumsearch.data.ResponseImage
import com.example.daumsearch.data.ResponseImages
import com.example.daumsearch.data.ResponseWebMedium
import com.example.daumsearch.data.WebMedium
import okhttp3.internal.ignoreIoExceptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WebViewModel : ViewModel(){
    private val TAG = "WebViewModel"

    private val _docs = MutableLiveData<List<ResponseDocument>>(emptyList())
    val docs: LiveData<List<ResponseDocument>> = _docs

    private val _imgs = MutableLiveData<List<ResponseImage>>(emptyList())
    val imgs: LiveData<List<ResponseImage>> = _imgs

    private val _webMedia = MutableLiveData<List<WebMedium>>(emptyList())
    val webMedia: LiveData<List<WebMedium>> = _webMedia

    fun fetchDocs(query: String) {
        RetrofitClient.daumApiService.getDocuments(query, "recency").enqueue(object : Callback<ResponseDocuments> {
            override fun onResponse(call: Call<ResponseDocuments>, response: Response<ResponseDocuments>) {
                if (response.isSuccessful) {
                    _docs.postValue(response.body()?.documents)
                    combineAll()
                    Log.d(TAG, docs.toString())
                } else {
                    // 처리: 실패 응답
                    Log.d(TAG, "response accepted but failed in fetchDocs")
                }
            }

            override fun onFailure(call: Call<ResponseDocuments>, t: Throwable) {
                // 처리: 네트워크 오류
                Log.d(TAG, "response failed in fetchDocs")
                Log.d(TAG, t.toString())
            }
        })
    }

    fun fetchImages(query: String) {
        RetrofitClient.daumApiService.getImages(query, "recency").enqueue(object : Callback<ResponseImages> {
            override fun onResponse(call: Call<ResponseImages>, response: Response<ResponseImages>) {
                if (response.isSuccessful) {
                    _imgs.postValue(response.body()?.documents)
                    combineAll()
                    Log.d(TAG, _imgs.toString())
                } else {
                    // 처리: 실패 응답
                    Log.d(TAG, "response accepted but failed in fetchImages")
                }
            }

            override fun onFailure(call: Call<ResponseImages>, t: Throwable) {
                // 처리: 네트워크 오류
                Log.d(TAG, "response failed in fetchImages")
                Log.d(TAG, t.toString())
            }
        })
    }

    fun combineAll() {
        val docsList: List<ResponseWebMedium> = docs.value?: emptyList()
        val imgsList: List<ResponseWebMedium> = imgs.value ?: emptyList()
        var combinedList: List<ResponseWebMedium> = docsList + imgsList
        combinedList = combinedList.sortedByDescending { item -> item.datetime }
        val combinedWebMedia: List<WebMedium> = combinedList.map{item->
            when(item){
                is ResponseDocument -> WebmediaResponseToWebmediaMapper.docMap(item)
                is ResponseImage -> WebmediaResponseToWebmediaMapper.ImgMap(item)
                else -> throw IllegalArgumentException("Invalid Response type")
            }
        }
        _webMedia.postValue(combinedWebMedia)
    }
}
