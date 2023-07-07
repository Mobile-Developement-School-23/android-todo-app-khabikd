package com.example.todoappyandex.worker

import android.content.Context
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.properties.Delegates

class WorkerProvider @Inject constructor(
    updateDataWorkerFactory: CreateWorkerFactory,
    private val context: Context
) {

    private var workManager: WorkManager by Delegates.notNull()

    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.UNMETERED)
        .build()


    private val syncWork = PeriodicWorkRequestBuilder<SyncWorker>(
        8, TimeUnit.HOURS,
        7, TimeUnit.HOURS
    )
        .setConstraints(constraints)
        .build()


    private val workManagerConfig = Configuration.Builder()
        .setWorkerFactory(updateDataWorkerFactory)
        .build()

    init {
        WorkManager.initialize(context, workManagerConfig)
        workManager = WorkManager.getInstance(context)
    }

    fun startPeriodicUpdateData(){
        workManager.enqueue(syncWork)
    }
}