package com.example.todoappyandex.data.repository

import com.example.todoappyandex.domain.model.TodoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*

class TodoItemsRepository {
    private val _todoList = MutableStateFlow<List<TodoItem>>(mutableListOf(
        TodoItem("1", "Купить что-то", TodoItem.Priority.DEFAULT, null, false, Date(), null),
        TodoItem("2", "Купить что-то", TodoItem.Priority.LOW, null, false, Date(), null),
        TodoItem("3", "Купить что-то", TodoItem.Priority.HIGH, null, false, Date(), null),
        TodoItem("4", "Купить что-то", TodoItem.Priority.DEFAULT, null, true, Date(), null),
        TodoItem("5", "Купить что-то", TodoItem.Priority.DEFAULT, null, true, Date(), null),
        TodoItem("6", "Купить что-то", TodoItem.Priority.DEFAULT, null, true, Date(), null),
        TodoItem("7", "Купить что-то", TodoItem.Priority.DEFAULT, null, true, Date(), null),
        TodoItem("8", "Купить что-то", TodoItem.Priority.DEFAULT, null, true, Date(), null),
        TodoItem("9", "Купить что-то", TodoItem.Priority.DEFAULT, Date(), true, Date(), null),
        TodoItem("10", "Купить что-то, где-то, зачем-то, но зачем не очень понятно", TodoItem.Priority.DEFAULT, null, false, Date(), null),
        TodoItem("11", "Купить что-то, где-то, зачем-то, но зачем не очень понятно, но точно чтобы показать как обрезается", TodoItem.Priority.DEFAULT, null, false, Date(), null),
    ))
    val todoList: StateFlow<List<TodoItem>> = _todoList

    fun getTodoList(): List<TodoItem> {
        return _todoList.value
    }

    fun addTodoItem(todo: TodoItem) {
        val updatedList = _todoList.value.toMutableList()
        updatedList.add(todo)
        _todoList.value = updatedList
    }
}

