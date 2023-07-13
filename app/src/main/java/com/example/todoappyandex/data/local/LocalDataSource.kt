package com.example.todoappyandex.data.local

import com.example.todoappyandex.data.local.dao.TodoItemDao
import com.example.todoappyandex.data.local.entities.TodoItemEntity
import com.example.todoappyandex.data.mappers.asEntity
import com.example.todoappyandex.data.mappers.asExternalModel
import com.example.todoappyandex.data.model.TodoItem
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val todoItemDao: TodoItemDao) { // no scope

    fun getTodoItemsFlow() = todoItemDao.getTodoItemsFlow().map { it.toExternalModel() }

    suspend fun getTodoItems() = todoItemDao.getTodoItems().toExternalModel()

    suspend fun getTodoItem(todoId: String) = todoItemDao.getTodoItem(todoId)

    suspend fun addTodoItem(todoItem: TodoItem) = todoItemDao.insertTodoItem(todoItem.asEntity())

    suspend fun addTodoItems(todoItems: List<TodoItem>) = todoItemDao.insertTodoItems(todoItems.map { it.asEntity() })

    suspend fun deleteTodoItem(itemId: String) = todoItemDao.deleteTodoItem(itemId)

    suspend fun updateTodoItem(todoItem: TodoItem) = todoItemDao.updateTodoItem(todoItem.asEntity())

    private fun List<TodoItemEntity>.toExternalModel(): List<TodoItem> {
        return this
            .map { it.asExternalModel() }
    }
}