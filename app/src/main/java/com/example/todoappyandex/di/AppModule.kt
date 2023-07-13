package com.example.todoappyandex.di

import android.content.Context
import android.content.SharedPreferences
import com.example.todoappyandex.data.DataStorage
import com.example.todoappyandex.data.local.AppDatabase
import com.example.todoappyandex.data.local.dao.TodoItemDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule() {

    @Singleton
    @Provides
    fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideTodoItemDao(appDatabase: AppDatabase): TodoItemDao {
        return appDatabase.todoItemDao()
    }


    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
    }

}