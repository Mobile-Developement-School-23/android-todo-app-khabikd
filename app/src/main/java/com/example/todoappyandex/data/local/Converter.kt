package com.example.todoappyandex.data.local

import androidx.room.TypeConverter
import com.example.todoappyandex.domain.model.TodoItem

class ImportanceConverter {
    @TypeConverter
    fun fromString(value: String): TodoItem.Importance {
        return when (value) {
            "LOW" -> TodoItem.Importance.LOW
            "DEFAULT" -> TodoItem.Importance.DEFAULT
            "HIGH" -> TodoItem.Importance.HIGH
            else -> throw IllegalArgumentException("Unknown Importance value: $value")
        }
    }

    @TypeConverter
    fun toString(importance: TodoItem.Importance): String {
        return importance.name
    }
}