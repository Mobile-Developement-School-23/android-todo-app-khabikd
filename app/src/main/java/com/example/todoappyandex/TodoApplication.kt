package com.example.todoappyandex

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.todoappyandex.data.TodoItemsRepository
import com.example.todoappyandex.data.local.TodoDatabase
import com.example.todoappyandex.data.remote.TodoService
import com.example.todoappyandex.data.remote.createTodoItemApi

class TodoApplication : Application() {

    val context: Context by lazy { applicationContext }
    private val sharedPreferences: SharedPreferences by lazy { applicationContext.getSharedPreferences("my_preferences", Context.MODE_PRIVATE) }
    private val todoApi: TodoService by lazy { createTodoItemApi() }
    private val todoDatabase: TodoDatabase by lazy { TodoDatabase.getDatabase(this) }
    val todoRepository: TodoItemsRepository by lazy { TodoItemsRepository(todoApi, todoDatabase.todoItemDao(), sharedPreferences, context) }

}

