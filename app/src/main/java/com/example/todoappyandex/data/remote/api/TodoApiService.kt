package com.example.todoappyandex.data.remote.api

import com.example.todoappyandex.data.remote.entities.TodoItemRequest
import com.example.todoappyandex.data.remote.entities.TodoItemResponse
import com.example.todoappyandex.data.remote.entities.TodoItemsRequest
import com.example.todoappyandex.data.remote.entities.TodoItemsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TodoApiService {

    @GET("list")
    suspend fun getTodoList(): Response<TodoItemsResponse>

    @GET("list/{id}")
    suspend fun getTodoItem(
        @Path("id") todoId: String
    ): Response<TodoItemResponse>

    @PATCH("list")
    suspend fun updateTodoItems(
        @Body todoListRequest: TodoItemsRequest,
        @Header("X-Last-Known-Revision") revision: Int,
    ): Response<TodoItemsResponse>

    @POST("list")
    suspend fun addTodoItem(
        @Body todoBody: TodoItemRequest,
        @Header("X-Last-Known-Revision") revision: Int
    ): Response<TodoItemResponse>


    @PUT("list/{id}")
    suspend fun updateTodoItem(
        @Path("id") todoId: String,
        @Body todoBody: TodoItemRequest,
        @Header("X-Last-Known-Revision") revision: Int
    ): Response<TodoItemResponse>

    @DELETE("list/{id}")
    suspend fun deleteTodoItem(
        @Path("id") todoId: String,
        @Header("X-Last-Known-Revision") revision: Int
    ): Response<TodoItemResponse>

}