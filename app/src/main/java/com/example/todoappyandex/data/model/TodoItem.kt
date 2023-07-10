package com.example.todoappyandex.data.model

import java.io.Serializable

enum class Importance {
    LOW,
    DEFAULT,
    HIGH
}

data class TodoItem(
    val id: String,
    val text: String,
    val importance: Importance,
    var deadline: Long? = null,
    var isCompleted: Boolean,
    var color: String? = null,
    val createdAt: Long,
    val modifiedAt: Long? = null,
    val isDeleted: Boolean = false
): Serializable