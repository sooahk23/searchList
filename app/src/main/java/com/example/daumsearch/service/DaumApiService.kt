package com.example.daumsearch.service

import com.example.daumsearch.data.Document
import retrofit2.Call
import retrofit2.http.GET

interface DaumApiService {
    @GET("https://dapi.kakao.com/v2/search/web")
    fun getDocuments(): Call<List<Document>> // Item은 받아올 데이터의 구조를 나타내는 모델 클래스입니다.
}
