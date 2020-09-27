package com.example.kotlin.net

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Net {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().apply {
                val logger = HttpLoggingInterceptor {
                    Log.d(">>>>>>", it)
                }
                logger.setLevel(HttpLoggingInterceptor.Level.BODY)
                addNetworkInterceptor(logger)
            }.build())
            .build()
    }

    val api: API by lazy {
        retrofit.create(API::class.java)
    }
}

fun api(): API {
    return Net.api
}