package com.example.daumsearch.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.daumsearch.api.RetrofitClient
import com.example.daumsearch.data.Document
import com.example.daumsearch.data.Image
import com.example.daumsearch.data.ResponseDocuments
import com.example.daumsearch.data.ResponseImages
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WebViewModel : ViewModel(){
    private val TAG = "WebViewModel"
    private val _docs = MutableLiveData<ResponseDocuments>()
    val docs: LiveData<ResponseDocuments> = _docs

    private val _imgs = MutableLiveData<ResponseImages>()
    val imgs: LiveData<ResponseImages> = _imgs

    fun fetchDocs(query: String) {
        RetrofitClient.daumApiService.getDocuments(query, "recency").enqueue(object : Callback<ResponseDocuments> {
            override fun onResponse(call: Call<ResponseDocuments>, response: Response<ResponseDocuments>) {
                if (response.isSuccessful) {
                    _docs.postValue(response.body())
                } else {
                    // 처리: 실패 응답
                    Log.d(TAG, "response failed1")
                }
            }

            override fun onFailure(call: Call<ResponseDocuments>, t: Throwable) {
                // 처리: 네트워크 오류
                Log.d(TAG, "response failed2")
                Log.d(TAG, t.toString())
            }
        })
    }

    fun fetchImages(query: String) {
        RetrofitClient.daumApiService.getImages(query, "recency").enqueue(object : Callback<ResponseImages> {
            override fun onResponse(call: Call<ResponseImages>, response: Response<ResponseImages>) {
                if (response.isSuccessful) {
                    _imgs.postValue(response.body())
                } else {
                    // 처리: 실패 응답
                    Log.d(TAG, "response failed3")
                }
            }

            override fun onFailure(call: Call<ResponseImages>, t: Throwable) {
                // 처리: 네트워크 오류
                Log.d(TAG, "response failed4")
                Log.d(TAG, t.toString())
            }
        })
    }
}
