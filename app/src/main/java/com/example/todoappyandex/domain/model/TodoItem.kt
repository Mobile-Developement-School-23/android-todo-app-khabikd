package com.example.todoappyandex.domain.model

import java.util.*

data class TodoItem(
    val id: String,
    val text: String,
    val priority: Priority,
    val deadline: Date? = null,
    var isDone: Boolean,
    val createdDate: Date,
    val changedDate: Date? = null,

) {
    enum class Priority {
        LOW,
        DEFAULT,
        HIGH
    }
}