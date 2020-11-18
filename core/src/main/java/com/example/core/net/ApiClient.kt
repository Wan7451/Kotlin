package com.example.core.net

import android.util.Log
import com.example.core.BuildConfig
import com.example.core.Config.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.Proxy
import java.util.concurrent.TimeUnit

object ApiClient {
    private var retrofit: Retrofit

    init {
        val client = OkHttpClient.Builder()
            .addInterceptor(SafeGuardInterceptor())
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .proxy(Proxy.NO_PROXY)

        if (BuildConfig.DEBUG) {
            val logger = HttpLoggingInterceptor { message -> Log.i("========", message) }
            logger.level = HttpLoggingInterceptor.Level.BASIC
            client.addNetworkInterceptor(logger)
        }

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
    }

    fun <T> getApi(c: Class<T>): T {
        return retrofit.create(c)
    }
}