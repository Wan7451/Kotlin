package com.example.myapplication.api

import com.example.core.net.ApiClient
import com.example.myapplication.entity.Image
import com.example.myapplication.entity.ImageList
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @GET("j")
    fun loadImage(
        @Query("q") query: String,
        @Query("sn") page: Int,
        @Query("pn") pageSize: Int = 50
    ): Observable<ImageList>
}


private var api: API? = null

fun getApi(): API {
    return api ?: ApiClient.getApi(API::class.java)
}