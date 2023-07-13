package com.example.todoappyandex.ui.edititem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoappyandex.data.TodoRepository
import com.example.todoappyandex.data.model.Importance
import com.example.todoappyandex.data.model.TodoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID
import javax.inject.Inject

class EditItemViewModel @Inject constructor(private val repository: TodoRepository) : ViewModel() {

    private val _todoItem = MutableStateFlow(getDefaultTodoItem())
    val todoItem: StateFlow<TodoItem> = _todoItem.asStateFlow()

    private var _isExisting: Boolean? = null

    fun updateDescription(description: String) {
        val currentTodoItem = _todoItem.value
        val updatedTodoItem = currentTodoItem.copy(text = description)
        _todoItem.value = updatedTodoItem
    }

    fun updatePriority(importance: Importance) {
        val currentTodoItem = _todoItem.value
        val updatedTodoItem = currentTodoItem.copy(importance = importance)
        _todoItem.value = updatedTodoItem
    }

    fun updateDeadline(deadline: Long?) {
        val currentTodoItem = _todoItem.value
        val updatedTodoItem = currentTodoItem.copy(deadline = deadline) // repeated code
        _todoItem.value = updatedTodoItem
    }

    fun saveTodoItem() {
        val modifiedAt = Date().time
        val currentTodoItem = _todoItem.value.copy(modifiedAt = modifiedAt)

        if (_isExisting == true) {
            viewModelScope.launch {
                repository.updateTodoItem(currentTodoItem)
            }
        } else {
            viewModelScope.launch {
                repository.addTodoItem(currentTodoItem.copy(createdAt = modifiedAt))
            }
        }
    }

    fun removeTodoItem() {
        val currentTodoItem = _todoItem.value
        viewModelScope.launch {
            repository.deleteTodoItem(currentTodoItem.id)
        }
    }

    fun createOrFind(id: String?) {
        if (id == null) {
            _isExisting = false
            _todoItem.value = getDefaultTodoItem()
        } else {
            _isExisting = true
            viewModelScope.launch {
                val todoItem = repository.getTodoItem(id)
                _todoItem.value = todoItem
            }
        }
    }

    private fun getDefaultTodoItem(): TodoItem {
        return TodoItem(id = UUID.randomUUID().toString(), text = "", Importance.DEFAULT, isCompleted = false, createdAt = Date().time)
    }
}
