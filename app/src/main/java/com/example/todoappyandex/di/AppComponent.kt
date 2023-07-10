package com.example.todoappyandex.di

import android.content.Context
import com.example.todoappyandex.MainActivity
import com.example.todoappyandex.TodoApp
import com.example.todoappyandex.ui.edititem.EditItemFragment
import com.example.todoappyandex.ui.todoitems.TodoItemsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RepositoryModule::class, NetworkModule::class])
interface AppComponent {
    fun viewModelFactoryComponent(): ViewModelFactoryComponent

    fun inject(activity: MainActivity)
    fun inject(usernameFragment: TodoItemsFragment)
    fun inject(passwordFragment: EditItemFragment)
    fun inject(application: TodoApp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}