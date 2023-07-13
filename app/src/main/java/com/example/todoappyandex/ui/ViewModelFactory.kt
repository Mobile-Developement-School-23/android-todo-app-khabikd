package com.example.todoappyandex.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.todoappyandex.TodoApp
import com.example.todoappyandex.di.FragmentScope
import com.example.todoappyandex.ui.edititem.EditItemViewModel
import com.example.todoappyandex.ui.todoitems.TodoItemsViewModel
import javax.inject.Inject

@FragmentScope // wtf?
class TodoViewModelFactory @Inject constructor() : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {
        val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
        val repository = (application as TodoApp).todoRepository
        if (modelClass.isAssignableFrom(TodoItemsViewModel::class.java)) { // where ViewModelKey?
            return TodoItemsViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(EditItemViewModel::class.java)) {
            return EditItemViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.simpleName}")
    }
}