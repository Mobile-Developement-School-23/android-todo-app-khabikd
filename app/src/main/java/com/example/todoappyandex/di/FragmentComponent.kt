package com.example.todoappyandex.di

import com.example.todoappyandex.ui.edititem.EditItemFragment
import com.example.todoappyandex.ui.todoitems.TodoItemsFragment
import dagger.Component
import javax.inject.Scope

@FragmentScope
@Component(modules = [ViewModelModule::class, RepositoryModule::class, NetworkModule::class])
internal interface FragmentComponent {

    fun inject(taskListFragment: TodoItemsFragment)
    fun inject(taskFragment: EditItemFragment)

}

@Scope
annotation class FragmentScope