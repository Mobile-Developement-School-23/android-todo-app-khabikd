package com.example.todoappyandex.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.todoappyandex.data.local.entities.TodoItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoItemDao {

    @Query("select * FROM todoItem ")
    fun getTodoItemsFlow(): Flow<List<TodoItemEntity>>

    @Query("select * FROM todoItem")
    suspend fun getTodoItems(): List<TodoItemEntity>

    @Query("SELECT * FROM todoItem where id = :todoId")
    suspend fun getTodoItem(todoId: String): TodoItemEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodoItem(todoItemEntity: TodoItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodoItems(list: List<TodoItemEntity>)

    @Query("DELETE FROM todoItem where id = :todoId")
    suspend fun deleteTodoItem(todoId: String)

    @Upsert
    suspend fun updateTodoItem(todoItemEntity: TodoItemEntity)
}