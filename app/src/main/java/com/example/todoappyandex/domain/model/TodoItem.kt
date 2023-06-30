package com.example.todoappyandex.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todoappyandex.data.remote.TodoItemApi
import java.io.Serializable
import java.util.*

@Entity(tableName = "todo_item_table")
@kotlinx.serialization.Serializable
data class TodoItem(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val text: String,
    val importance: Importance,
    val deadline: Long? = null,
    var done: Boolean,
    val color: String? = "#FFFFFF",
    val created_at: Long = System.currentTimeMillis(),
    val changed_at: Long? = null,
    val last_updated_by: String
    ) : Serializable {
    enum class Importance {
        LOW,
        DEFAULT,
        HIGH
    }

    fun toTodoItemApi(): TodoItemApi {
        return TodoItemApi(
            id = id,
            text = text,
            importance = importance.name,
            deadline = deadline,
            done = done,
            color = color,
            createdAt = created_at,
            changedAt = changed_at,
            lastUpdateBy = last_updated_by
        )
    }
}

