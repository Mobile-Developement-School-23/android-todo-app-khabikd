package com.example.todoappyandex.di

import com.example.todoappyandex.data.DataStorage
import com.example.todoappyandex.data.TodoRepository
import com.example.todoappyandex.data.local.LocalDataSource
import com.example.todoappyandex.data.local.dao.TodoItemDao
import com.example.todoappyandex.data.remote.RemoteDataSource
import com.example.todoappyandex.data.remote.api.TodoApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideTodoItemsRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): TodoRepository {
        return TodoRepository(localDataSource, remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(todoItemDao: TodoItemDao): LocalDataSource {
        return LocalDataSource(todoItemDao)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: TodoApiService, dataStorage: DataStorage): RemoteDataSource {
        return RemoteDataSource(apiService, dataStorage)
    }

}