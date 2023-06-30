package com.example.todoappyandex.presentation.ui

import androidx.lifecycle.*
import com.example.todoappyandex.data.TodoItemsRepository
import com.example.todoappyandex.domain.model.TodoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TodoListViewModel(private val repository: TodoItemsRepository): ViewModel() {

    private val _todoListState = MutableStateFlow<List<TodoItem>>(emptyList())
    val todoListState: StateFlow<List<TodoItem>> = _todoListState

    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    init {
        fetchTodoList()
    }

    private fun fetchTodoList() {
        viewModelScope.launch {
            _loadingState.value = true
            try {
                val todoList = repository.getTodoList()
                _todoListState.value = todoList
            } catch (e: Exception) {
                _errorState.value = "Failed to fetch todo list: ${e.message}"
            } finally {
                _loadingState.value = false
            }
        }
    }

    fun refreshTodoList() {
        fetchTodoList()
    }

    fun saveTodoItem(todoItem: TodoItem) {
        viewModelScope.launch {
            try {
                repository.saveTodoItem(todoItem)
            } catch (e: Exception) {
                _errorState.value = "Failed to save todo item: ${e.message}"
            }
        }
    }

    fun editTodoItem(todoItem: TodoItem) {
        viewModelScope.launch {
            try {
                repository.editTodoItem(todoItem)
            } catch (e: Exception) {
                _errorState.value = "Failed to edit todo item: ${e.message}"
            }
        }
    }

    fun deleteTodoItem(todoItem: TodoItem) {
        viewModelScope.launch {
            try {
                repository.deleteTodoItem(todoItem)
            } catch (e: Exception) {
                _errorState.value = "Failed to delete todo item: ${e.message}"
            }
        }
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

