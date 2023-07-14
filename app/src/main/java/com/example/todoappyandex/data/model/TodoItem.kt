package com.example.todoappyandex.data.model

import java.io.Serializable

enum class Importance {
    LOW,
    DEFAULT,
    HIGH
}

data class TodoItem(
    val id: String,
    var text: String,
    var importance: Importance,
    var deadline: Long? = null,
    var isCompleted: Boolean,
    var color: String? = null,
    var createdAt: Long,
    var modifiedAt: Long? = null,
    var isDeleted: Boolean = false
): Serializable