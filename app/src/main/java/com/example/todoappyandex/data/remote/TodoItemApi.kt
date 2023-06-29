package com.example.todoappyandex.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

private const val BASEURL =
    "https://beta.mrdekk.ru/todobackend/"

interface TodoItemApi {
    @GET("list")
    suspend fun getTaskList(): TodoListResponse

    @PATCH("list")
    suspend fun updateTaskList(@Header("X-Last-Known-Revision") revision: Int, @Body request: TodoListRequest): TodoListResponse

    @GET("list/{id}")
    suspend fun getTaskById(@Path("id") id: String): TodoResponse

    @POST("list")
    suspend fun addTask(@Header("X-Last-Known-Revision") revision: Int, @Body request: TodoRequest): TodoResponse

    @PUT("list/{id}")
    suspend fun updateTask(@Path("id") id: String, @Header("X-Last-Known-Revision") revision: Int, @Body request: TodoRequest): TodoResponse

    @DELETE("list/{id}")
    suspend fun deleteTask(@Path("id") id: String, @Header("X-Last-Known-Revision") revision: Int): TodoResponse
}

fun createTodoItemApi(): TodoItemApi {
    val authToken = "pippiest"
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(authToken))
        .build()
    val retrofit = Retrofit.Builder()
        .baseUrl(BASEURL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(TodoItemApi::class.java)
}