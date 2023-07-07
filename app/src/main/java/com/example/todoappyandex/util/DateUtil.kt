package com.example.todoappyandex.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long?.timestampToFormattedDate(): String {
    val format = SimpleDateFormat("d MMM yyyy", Locale("ru"))
    return format.format(Date(this ?: 0))
}