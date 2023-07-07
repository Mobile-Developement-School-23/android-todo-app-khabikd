package com.example.todoappyandex.util

sealed class State<out T> {
    data class Success<out T>(val result: T) : State<T>()
    data class Failure(val exception: Exception) : State<Nothing>()
}