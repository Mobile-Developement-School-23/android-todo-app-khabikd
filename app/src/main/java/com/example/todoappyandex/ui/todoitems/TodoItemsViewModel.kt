package com.example.todoappyandex.ui.todoitems

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoappyandex.data.TodoRepository
import com.example.todoappyandex.data.model.TodoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

class TodoItemsViewModel @Inject constructor(private val repository: TodoRepository) : ViewModel() {
    private val _completedTasksCount: MutableStateFlow<Int> = MutableStateFlow(0)
    val completedTasksCount: StateFlow<Int>
        get() = _completedTasksCount.asStateFlow()

    val todoItems = repository.todoItems

    init {
        loadTodoItems()

        viewModelScope.launch {
            todoItems.collectLatest { items ->
                _completedTasksCount.value = items.count { it.isCompleted }
            }
        }
    }

    private fun loadTodoItems() {
        viewModelScope.launch {
            repository.loadTodoItems()
        }
    }

    private fun updateTodoItem(todoItem: TodoItem) {
        viewModelScope.launch {
            repository.updateTodoItem(todoItem)
        }
    }

    fun updateChecked(todoItem: TodoItem, isChecked: Boolean) {
        val modifiedItem = todoItem.copy(isCompleted = isChecked, modifiedAt = Date().time)
        updateTodoItem(modifiedItem)
    }

}