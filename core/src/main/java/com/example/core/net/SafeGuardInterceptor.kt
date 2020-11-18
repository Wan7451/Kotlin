package com.example.core.net

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class SafeGuardInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            return chain.proceed(chain.request())
        } catch (t: Throwable) {
            throw IOException("SafeGuarded when requesting ${chain.request().url}", t)
        }
    }
}