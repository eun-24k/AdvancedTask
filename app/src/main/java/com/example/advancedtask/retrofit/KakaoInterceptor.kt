package com.example.advancedtask.retrofit

import com.example.advancedtask.utils.ApiKey
import com.example.advancedtask.utils.ApiKey.Companion.REST_API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class KakaoInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val auth = "KakaoAK $REST_API_KEY"

        builder.addHeader("Authorization", auth)

        return chain.proceed(builder.build())
    }
}