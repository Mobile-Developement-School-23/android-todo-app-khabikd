package com.example.todoappyandex.data

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.todoappyandex.data.local.TodoItemDao
import com.example.todoappyandex.data.remote.TodoBody
import com.example.todoappyandex.data.remote.TodoItemApi
import com.example.todoappyandex.data.remote.TodoService
import com.example.todoappyandex.domain.model.TodoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class TodoItemsRepository(
    private val todoService: TodoService,
    private val todoItemDao: TodoItemDao,
    private val sharedPreferences: SharedPreferences,
    private val context: Context
) {
    private var currentRevision: Int
        get() = sharedPreferences.getInt(REVISION_KEY, -1)
        set(value) = sharedPreferences.edit().putInt(REVISION_KEY, value).apply()

    private suspend fun isInternetAvailable(): Boolean = withContext(Dispatchers.IO) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCapabilities = connectivityManager.activeNetwork ?: return@withContext false
        val activeNetwork =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return@withContext false

        return@withContext when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    suspend fun getTodoList(): List<TodoItem> = withContext(Dispatchers.IO) {
        val isInternetAvailable = isInternetAvailable()
        return@withContext try {
            if (isInternetAvailable) {
                val response = todoService.getTodoList()
                if (response.status == "ok") {
                    val todoItems = response.list.map { it.toTodoItem() }
                    currentRevision = response.revision
                    todoItemDao.insertAll(todoItems)
                    todoItems
                } else {
                    todoItemDao.getAllTodoItems()
                }
            } else {
                todoItemDao.getAllTodoItems()
            }
        } catch (e: Exception) {
            todoItemDao.getAllTodoItems()
        }
    }

    suspend fun saveTodoItem(todoItem: TodoItem) = withContext(Dispatchers.IO) {
        val isInternetAvailable = isInternetAvailable()
        try {
            if (isInternetAvailable) {
                val todoBody = TodoBody(todoItem.toTodoItemApi())
                val response = todoService.saveTodoItem(todoBody, currentRevision)
                if (response.status == "ok") {
                    currentRevision = response.revision
                    todoItemDao.insert(todoItem)
                } else {
                    throw Exception("Failed to save todo item.")
                }
            } else {
                throw Exception("Internet connection is not available.")
            }
        } catch (e: Exception) {
            throw Exception("Failed to save todo item.")
        }
    }

    suspend fun editTodoItem(todoItem: TodoItem) = withContext(Dispatchers.IO) {
        val isInternetAvailable = isInternetAvailable()
        try {
            if (isInternetAvailable) {
                val todoBody = TodoBody(todoItem.toTodoItemApi())
                val response = todoService.editTodoItem(todoItem.id, todoBody, currentRevision)
                if (response.status == "ok") {
                    currentRevision = response.revision
                    todoItemDao.insert(todoItem)
                } else {
                    throw Exception("Failed to edit todo item.")
                }
            } else {
                throw Exception("Internet connection is not available.")
            }
        } catch (e: Exception) {
            throw Exception("Failed to edit todo item.")
        }
    }

    suspend fun deleteTodoItem(todoItem: TodoItem) = withContext(Dispatchers.IO) {
        val isInternetAvailable = isInternetAvailable()
        try {
            if (isInternetAvailable) {
                val response = todoService.deleteTodoItem(todoItem.id, currentRevision)
                if (response.status == "ok") {
                    currentRevision = response.revision
                    todoItemDao.delete(todoItem)
                } else {
                    throw Exception("Failed to delete todo item.")
                }
            } else {
                throw Exception("Internet connection is not available.")
            }
        } catch (e: Exception) {
            throw Exception("Failed to delete todo item.")
        }
    }

    companion object {
        private const val REVISION_KEY = "revision"
    }
}

