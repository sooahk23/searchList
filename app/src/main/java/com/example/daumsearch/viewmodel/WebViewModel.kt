package com.example.daumsearch.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.daumsearch.api.RetrofitClient
import com.example.daumsearch.data.Document
import com.example.daumsearch.data.Image
import com.example.daumsearch.data.ResponseDocument
import com.example.daumsearch.data.ResponseDocuments
import com.example.daumsearch.data.ResponseImage
import com.example.daumsearch.data.ResponseImages
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WebViewModel : ViewModel(){
    private val TAG = "WebViewModel"
    private val _docs = MutableLiveData<List<Document>>(emptyList())
//    val _docs = MutableLiveData<List<Document>>()
    val docs: LiveData<List<Document>> = _docs

    private val _imgs = MutableLiveData<List<Image>>(emptyList())
    val imgs: LiveData<List<Image>> = _imgs

    fun fetchDocs(query: String) {
        RetrofitClient.daumApiService.getDocuments(query, "recency").enqueue(object : Callback<ResponseDocuments> {
            override fun onResponse(call: Call<ResponseDocuments>, response: Response<ResponseDocuments>) {
                if (response.isSuccessful) {
                    val resBody : List<ResponseDocument>? = response.body()?.documents
                    val toDoc : List<Document>? = resBody?.map{item -> WebmediaResponseToWebmediaMapper.docMap(item)}
                    _docs.postValue(toDoc)
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
                    val resBody : List<ResponseImage>? = response.body()?.documents
                    val toImg : List<Image>? = resBody?.map{item -> WebmediaResponseToWebmediaMapper.ImgMap(item)}
                    _imgs.postValue(toImg)
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
}
