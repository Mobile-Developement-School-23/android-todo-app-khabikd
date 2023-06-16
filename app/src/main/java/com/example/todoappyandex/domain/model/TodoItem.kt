package com.example.todoappyandex.domain.model

import java.io.Serializable
import java.util.*

data class TodoItem(
    val id: String,
    val text: String,
    val priority: Priority,
    val deadline: Date? = null,
    var isDone: Boolean,
    val createdDate: Date,
    val changedDate: Date? = null,

): Serializable {
    enum class Priority {
        LOW,
        DEFAULT,
        HIGH
    }
}