package com.example.todoappyandex.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import androidx.room.Update
import com.example.todoappyandex.domain.model.TodoItem

@Dao
@TypeConverters(ImportanceConverter::class)
interface TodoItemDao {
    @Query("SELECT * FROM todo_item_table")
    fun getAllTodoItems(): List<TodoItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(todoItems: List<TodoItem>)

    @Query("SELECT * FROM todo_item_table WHERE id = :todoId")
    fun getTodoItemById(todoId: String): TodoItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(todoItem: TodoItem)

    @Delete
    fun delete(todoItem: TodoItem)
}