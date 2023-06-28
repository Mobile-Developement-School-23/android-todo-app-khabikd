package com.example.todoappyandex.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "todo_item_table")

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

    ): Serializable {
    enum class Importance {
        LOW,
        DEFAULT,
        HIGH
    }
}

