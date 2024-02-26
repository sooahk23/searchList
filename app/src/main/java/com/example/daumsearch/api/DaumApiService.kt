package com.example.daumsearch.api

import com.example.daumsearch.data.ResponseDocuments
import com.example.daumsearch.data.ResponseImages
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface DaumApiService {
    @Headers("Authorization: KakaoAK 206547b0f9d8a16a8775ee3a6cfd9cd0")
    @GET("web")
    suspend fun getDocuments(@Query("query") query: String,
                     @Query("sort") sort: String
    ): Response<ResponseDocuments>

    @Headers("Authorization: KakaoAK 206547b0f9d8a16a8775ee3a6cfd9cd0")
    @GET("image")
    suspend fun getImages(@Query("query") query: String,
                     @Query("sort") sort: String
    ): Response<ResponseImages>
}
