package com.example.todoappyandex.data

import com.example.todoappyandex.data.local.TodoItemsDao
import com.example.todoappyandex.data.remote.TodoListRequest
import com.example.todoappyandex.data.remote.TodoRequest
import com.example.todoappyandex.data.remote.TodoItemApi
import com.example.todoappyandex.domain.model.TodoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.withContext

class TodoItemsRepository(
    private val todoApi: TodoItemApi,
    private val todoDao: TodoItemsDao,
) {

    fun getTodoList(): Flow<List<TodoItem>> = todoDao.getTodoList()

    fun getTodoItem(itemId: String): Flow<TodoItem> = todoDao.getTodoItem(itemId)

    suspend fun syncTodoList() {
        withContext(Dispatchers.IO) {
            val localList = todoDao.getTodoList()

            val response = todoApi.updateTaskList(revision, TodoListRequest(revision, localList))

            if (response.status == "ok") {
                todoDao.updateTodoList(response.list)
            }
        }
    }

    suspend fun addTodoItem(todoItem: TodoItem) {
        withContext(Dispatchers.IO) {
            val response = todoApi.addTask(revision, TodoRequest(todoItem.id, todoItem.text, todoItem.importance, todoItem.deadline, todoItem.done, todoItem.color))

            if (response.status == "ok") {
                todoDao.addTodoItem(todoItem)
            }
        }
    }

    suspend fun updateTodoItem(todoItem: TodoItem) {
        withContext(Dispatchers.IO) {
            val response = todoApi.updateTask(todoItem.id, todoItem.revision, TodoRequest(todoItem.id, todoItem.text, todoItem.importance, todoItem.deadline, todoItem.done, todoItem.color))

            if (response.status == "ok") {
                todoDao.updateTodoItem(todoItem)
            }
        }
    }

    suspend fun deleteTodoItem(todoItem: TodoItem) {
        withContext(Dispatchers.IO) {
            val response = todoApi.deleteTask(todoItem.id, todoItem.revision)

            if (response.status == "ok") {
                todoDao.deleteTodoItem(todoItem.id)
            }
        }
    }

}

