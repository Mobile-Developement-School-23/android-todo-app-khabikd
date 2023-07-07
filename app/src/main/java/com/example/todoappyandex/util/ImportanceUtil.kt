package com.example.todoappyandex.util

import com.example.todoappyandex.data.model.Importance

fun importanceFromString(value: String): Importance {
    return when (value) {
        "LOW" -> Importance.LOW
        "DEFAULT" -> Importance.DEFAULT
        "HIGH" -> Importance.HIGH
        else -> throw IllegalArgumentException("Unknown Importance value: $value")
    }
}

fun importanceToString(importance: Importance): String {
    return importance.name
}