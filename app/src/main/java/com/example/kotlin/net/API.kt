package com.example.kotlin.net

import com.example.kotlin.module.Article
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface API {
    /**
     * 文章列表
     */
    @GET("article/list/{page}/json")
    fun articleList(@Path("page") page: Int): Observable<BaseData<BaseList<Article>>>
}