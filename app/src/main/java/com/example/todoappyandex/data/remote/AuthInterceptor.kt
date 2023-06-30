package com.example.todoappyandex.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val authToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header("Authorization", "Bearer $authToken")
            .build()
        return chain.proceed(request)
    }
}

class LastKnownRevisionInterceptor(private val revision: Int) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val modifiedRequest = originalRequest.newBuilder()
            .header("X-Last-Known-Revision", revision.toString())
            .build()
        return chain.proceed(modifiedRequest)
    }
}