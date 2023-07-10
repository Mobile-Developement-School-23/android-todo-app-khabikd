package com.example.todoappyandex.di

import com.example.todoappyandex.ui.edititem.EditItemFragment
import com.example.todoappyandex.ui.todoitems.TodoItemsFragment
import dagger.Subcomponent

@Subcomponent(modules = [ViewModelModule::class])
interface ViewModelFactoryComponent {
    fun inject(usernameFragment: TodoItemsFragment)
    fun inject(passwordFragment: EditItemFragment)
}