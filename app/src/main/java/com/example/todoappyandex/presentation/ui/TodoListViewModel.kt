package com.example.todoappyandex.presentation.ui

import androidx.lifecycle.*
import com.example.todoappyandex.data.repository.TodoItemsRepository
import com.example.todoappyandex.domain.model.TodoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TodoListViewModel(private val repository: TodoItemsRepository): ViewModel() {

    private val _todoList = MutableStateFlow<List<TodoItem>>(emptyList())
    val todoList: StateFlow<List<TodoItem>> = _todoList

    private val _completedTodoCount = MutableStateFlow<Int>(0)
    val completedTodoCount: StateFlow<Int> = _completedTodoCount

    init {
        fetchTodoList()
    }

    private fun fetchTodoList() {
        _todoList.value = repository.getTodoList()
        viewModelScope.launch {
            repository.todoList.collect { items ->
                _todoList.value = items.toMutableList()
                val completedCount = items.count { it.isDone }
                _completedTodoCount.value = completedCount
            }
        }
    }

    fun addTodo(todo: TodoItem) {
        repository.addTodoItem(todo)
    }

    fun deleteTodoItem(todoItem: TodoItem) {
        repository.deleteTodoItem(todoItem)
    }

    fun updateTodoItem(todo: TodoItem) {
        repository.updateTodoItem(todo)
    }
}


class TodoListViewModelFactory(private val repository: TodoItemsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TodoListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

