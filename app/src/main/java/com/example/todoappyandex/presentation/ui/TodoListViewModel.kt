package com.example.todoappyandex.presentation.ui

import androidx.lifecycle.*
import com.example.todoappyandex.data.TodoItemsRepository
import com.example.todoappyandex.domain.model.TodoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoListViewModel(private val repository: TodoItemsRepository): ViewModel() {

//    private val _todoList = MutableStateFlow<List<TodoItem>>(emptyList())
//    val todoList: StateFlow<List<TodoItem>> = _todoList
//
//    private val _completedTodoCount = MutableStateFlow<Int>(0)
//    val completedTodoCount: StateFlow<Int> = _completedTodoCount
//
//    init {
//        fetchTodoList()
//    }
//
//    private fun fetchTodoList() {
////        _todoList.value = repository.getTodoList()
////        viewModelScope.launch {
////            repository.todoList.collect { items ->
////                _todoList.value = items.toMutableList()
////                val completedCount = items.count { it.done }
////                _completedTodoCount.value = completedCount
////            }
////        }
//        _todoList.value = repository.getTodoList()
//        viewModelScope.launch {
//            repository.todoItemsFlow.collect { items ->
//                _todoList.value = items.toMutableList()
//            }
//        }
//    }

    val todoList = repository.getTodoList()
    fun getTodoItem(itemId: String) = repository.getTodoItem(itemId)

    fun addTodoItem(todoItem: TodoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTodoItem(todoItem)
        }
    }

    fun updateTodoItem(todoItem: TodoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTodoItem(todoItem)
        }
    }

    fun deleteTodoItem(todoItem: TodoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTodoItem(todoItem)
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

