package com.example.todoappyandex.di

import com.example.todoappyandex.ui.edititem.EditItemFragment
import com.example.todoappyandex.ui.todoitems.TodoItemsFragment
import dagger.Subcomponent

// No scope?
@Subcomponent(modules = [ViewModelModule::class])
interface ViewModelFactoryComponent { // what is the reason for this class?
    fun inject(usernameFragment: TodoItemsFragment)
    fun inject(passwordFragment: EditItemFragment)
}