package com.example.todoappyandex.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

private const val BASEURL =
    "https://beta.mrdekk.ru/todobackend/"

interface TodoService {

    @GET("list")
    suspend fun getTodoList(): TodoListResponse

    @GET("list/{id}")
    suspend fun getTodoItem(
        @Path("id") todoId: String
    ): TodoResponse

    @POST("list")
    suspend fun saveTodoItem(
        @Body todoBody: TodoBody,
        @Header("X-Last-Known-Revision") revision: Int
    ): TodoResponse

    @PUT("list/{id}")
    suspend fun editTodoItem(
        @Path("id") todoId: String,
        @Body todoBody: TodoBody,
        @Header("X-Last-Known-Revision") revision: Int
    ): TodoResponse

    @DELETE("list/{id}")
    suspend fun deleteTodoItem(
        @Path("id") todoId: String,
        @Header("X-Last-Known-Revision") revision: Int
    ): TodoResponse

}

fun createTodoItemApi(): TodoService {
    val authToken = "pippiest"
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(authToken))
        .build()
    val retrofit = Retrofit.Builder()
        .baseUrl(BASEURL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(TodoService::class.java)
}