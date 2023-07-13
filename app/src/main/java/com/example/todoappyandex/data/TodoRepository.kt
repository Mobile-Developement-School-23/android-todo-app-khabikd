package com.example.todoappyandex.data

import com.example.todoappyandex.data.local.LocalDataSource
import com.example.todoappyandex.data.mappers.asExternalModel
import com.example.todoappyandex.data.model.TodoItem
import com.example.todoappyandex.data.remote.RemoteDataSource
import com.example.todoappyandex.util.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TodoRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    private val _todoItems = localDataSource.getTodoItemsFlow()
    val todoItems: Flow<List<TodoItem>>
        get() = _todoItems

    suspend fun syncTodoItems() = withContext(Dispatchers.IO) { // repeating withContext..try..catch
        try {
            val remoteItems = remoteDataSource.getTodoItems()
            localDataSource.addTodoItems(remoteItems)
            val localItems = localDataSource.getTodoItems()
            remoteDataSource.updateTodoItems(localItems)
            State.Success(Unit)
        } catch (e: Exception) {
            State.Failure(e)
        }
    }

    suspend fun loadTodoItems() = withContext(Dispatchers.IO){
        try {
            val items = remoteDataSource.getTodoItems()
            localDataSource.addTodoItems(items)
            State.Success(Unit)
        } catch (e: Exception) {
            State.Failure(e)
        }
    }

    suspend fun getTodoItem(itemId: String): TodoItem = withContext(Dispatchers.IO) {
        return@withContext localDataSource.getTodoItem(itemId).asExternalModel()
    }

    suspend fun addTodoItem(todoItem: TodoItem) = withContext(Dispatchers.IO) {
        try {
            localDataSource.addTodoItem(todoItem)
            remoteDataSource.addTodoItem(todoItem)
            State.Success(Unit)
        }
        catch (e: Exception) {
            State.Failure(e)
        }
    }

    suspend fun updateTodoItem(todoItem: TodoItem) = withContext(Dispatchers.IO) {
        try {
            localDataSource.updateTodoItem(todoItem)
            remoteDataSource.updateTodoItem(todoItem)
            State.Success(Unit)
        } catch (e: Exception) {
            State.Failure(e)
        }
    }

    suspend fun deleteTodoItem(itemId: String) = withContext(Dispatchers.IO) {
        try {
            localDataSource.deleteTodoItem(itemId)
            remoteDataSource.deleteTodoItem(itemId)
            State.Success(Unit)
        } catch (e: Exception) {
            State.Failure(e)
        }
    }
}