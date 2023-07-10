package com.example.todoappyandex.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todoappyandex.data.model.Importance

@Entity(tableName = "todoItem")
data class TodoItemEntity(
    @PrimaryKey val id: String,
    @ColumnInfo("text") val text: String,
    @ColumnInfo("importance") val importance: Importance,
    @ColumnInfo("deadline") val deadline: Long? = null,
    @ColumnInfo("is_completed") val isCompleted: Boolean,
    @ColumnInfo("created_at") val createdAt: Long,
    @ColumnInfo("modified_at") var modifiedAt: Long? = null,
    @ColumnInfo(name = "is_deleted") val isDeleted: Boolean = false
)