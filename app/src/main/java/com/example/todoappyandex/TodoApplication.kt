package com.example.todoappyandex

import android.app.Application
import com.example.todoappyandex.data.TodoItemsRepository
import com.example.todoappyandex.data.local.TodoDatabase
import com.example.todoappyandex.data.remote.TodoItemApi
import com.example.todoappyandex.data.remote.createTodoItemApi

class TodoApplication : Application() {

    private val todoApi: TodoItemApi by lazy { createTodoItemApi() }
    private val todoDatabase: TodoDatabase by lazy { TodoDatabase.getDatabase(this) }
    val todoRepository: TodoItemsRepository by lazy { TodoItemsRepository(todoApi, todoDatabase.todoItemsDao()) }

}

