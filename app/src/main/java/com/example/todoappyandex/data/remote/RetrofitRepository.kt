package com.example.todoappyandex.data.remote

import com.example.todoappyandex.data.remote.api.TodoApiService
import com.example.todoappyandex.data.remote.interceptors.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitRepository {
    private val okHttpClient = OkHttpClient.Builder() // should be DI module
        .addInterceptor(AuthInterceptor("pippiest"))
        .addInterceptor(makeLoggingInterceptor())
        .connectTimeout(1000L, TimeUnit.SECONDS) // 1000s = 16min
        .readTimeout(1000L, TimeUnit.SECONDS)
        .writeTimeout(1000L, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://beta.mrdekk.ru/todobackend/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: TodoApiService = retrofit.create(TodoApiService::class.java)
}

private fun makeLoggingInterceptor(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    return logging
}

