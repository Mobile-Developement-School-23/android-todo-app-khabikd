package com.example.todoappyandex.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todoappyandex.domain.model.TodoItem
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoItemsDao {

    @Query("SELECT * FROM todo_item_table")
    fun getTodoList(): Flow<List<TodoItem>>

    @Query("SELECT * FROM todo_item_table WHERE id = :itemId")
    fun getTodoItem(itemId: String): Flow<TodoItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTodoItem(todoItem: TodoItem)

    @Update
    suspend fun updateTodoItem(todoItem: TodoItem)

    @Update
    suspend fun updateTodoList(todoItems: List<TodoItem>)

    @Query("DELETE FROM todo_item_table WHERE id = :itemId")
    suspend fun deleteTodoItem(itemId: String)
}