package com.example.todoappyandex.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoappyandex.ui.TodoViewModelFactory
import com.example.todoappyandex.ui.edititem.EditItemViewModel
import com.example.todoappyandex.ui.todoitems.TodoItemsViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: TodoViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(TodoItemsViewModel::class)
    fun todoItemsViewModel(viewModel: TodoItemsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditItemViewModel::class)
    fun editItemViewModel(viewModel: EditItemViewModel): ViewModel
}