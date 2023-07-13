package com.example.todoappyandex.data.remote

import com.example.todoappyandex.data.DataStorage
import com.example.todoappyandex.data.mappers.asApiModel
import com.example.todoappyandex.data.mappers.asExternalModel
import com.example.todoappyandex.data.model.TodoItem
import com.example.todoappyandex.data.remote.api.TodoApiService
import com.example.todoappyandex.data.remote.entities.TodoItemRequest
import com.example.todoappyandex.data.remote.entities.TodoItemResponse
import com.example.todoappyandex.data.remote.entities.TodoItemsRequest
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: TodoApiService, private val dataStorage: DataStorage) {  // no scope

    suspend fun getTodoItems(): List<TodoItem> {
        val response = apiService.getTodoList()
        val body = response.body()
        if (body != null){
            dataStorage.saveRevision(body.revision)
            return body.list.map { it.asExternalModel() }
        }

        return emptyList()
    }

    suspend fun updateTodoItems(todoItems: List<TodoItem>) {
        apiService.updateTodoItems(TodoItemsRequest(todoItems.map { it.asApiModel(dataStorage.device) }), dataStorage.getRevision())
    }

    suspend fun addTodoItem(todoItem: TodoItem): Response<TodoItemResponse> {
        return apiService.addTodoItem(TodoItemRequest(todoItem.asApiModel(dataStorage.device)), dataStorage.getRevision())
    }

    suspend fun updateTodoItem(todoItem: TodoItem): Response<TodoItemResponse> {
        return apiService.updateTodoItem(todoItem.id, TodoItemRequest(todoItem.asApiModel(dataStorage.device)), dataStorage.getRevision())
    }

    suspend fun deleteTodoItem(itemId: String) {
        apiService.deleteTodoItem(itemId, dataStorage.getRevision())
    }
}

