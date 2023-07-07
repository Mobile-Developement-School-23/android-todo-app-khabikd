package com.example.todoappyandex.di

import com.example.todoappyandex.data.remote.RetrofitRepository
import com.example.todoappyandex.data.remote.api.TodoApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitRepository(): RetrofitRepository {
        return RetrofitRepository()
    }

    @Singleton
    @Provides
    fun provideTodoApiService(): TodoApiService = RetrofitRepository().api
}