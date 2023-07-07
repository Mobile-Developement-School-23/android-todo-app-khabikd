package com.example.todoappyandex.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.todoappyandex.data.TodoRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SyncWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val params: WorkerParameters,
    private val repository: TodoRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            repository.syncTodoItems()
            Result.success()
        } catch (e: Throwable) {
            Result.retry()
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(context: Context, workerParameters: WorkerParameters): SyncWorker
    }
}

class CreateWorkerFactory @Inject constructor(
    private val syncWorker: SyncWorker.Factory
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        return syncWorker.create(appContext, workerParameters)
    }
}