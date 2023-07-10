package com.example.todoappyandex

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.todoappyandex.data.ConnectionManager
import com.example.todoappyandex.data.TodoRepository
import com.example.todoappyandex.data.local.AppDatabase
import com.example.todoappyandex.data.remote.api.TodoApiService
import com.example.todoappyandex.di.AppComponent
import com.example.todoappyandex.di.DaggerAppComponent
import com.example.todoappyandex.worker.CreateWorkerFactory
import com.example.todoappyandex.worker.WorkerProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class TodoApp: Application() {
    private var _appComponent: AppComponent? = null
    val appComponent: AppComponent
        get() = requireNotNull(_appComponent) {
            "AppComponent must be not null"
        }

    @Inject
    lateinit var context: Context
    @Inject
    lateinit var sharedPreferences: SharedPreferences
    @Inject
    lateinit var todoApi: TodoApiService
    @Inject
    lateinit var todoDatabase: AppDatabase
    @Inject
    lateinit var todoRepository: TodoRepository
    @Inject
    lateinit var syncDataWorkerFactory: CreateWorkerFactory
    @Inject
    lateinit var syncDataWorkerProvider: WorkerProvider

    override fun onCreate() {
        super.onCreate()
        _appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()
        appComponent.inject(this)
        setupConnectivityMonitoring(todoRepository)
        syncDataWorkerProvider.startPeriodicUpdateData()
    }

    private fun setupConnectivityMonitoring(todoItemRepository: TodoRepository) {
        val connectionManager = ConnectionManager()
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        connectionManager.setupNetworkListener(context, todoItemRepository, coroutineScope)
    }
}