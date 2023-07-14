package com.example.todoappyandex.notifications

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.todoappyandex.R
import com.example.todoappyandex.data.local.dao.TodoItemDao
import com.example.todoappyandex.data.mappers.asExternalModel
import com.example.todoappyandex.data.model.Importance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

class AlarmReceiver: BroadcastReceiver() {
    @Inject lateinit var todoDao: TodoItemDao

    override fun onReceive(context: Context?, intent: Intent?) {
        CoroutineScope(Dispatchers.IO).launch {
            val notificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val todoItems = todoDao.getTodoItems()
                .filter { it.deadline != null }
                .map { it.asExternalModel() }

            todoItems.forEach { todo ->
                val notification = NotificationCompat.Builder(context, "main_channel")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(context.getString(R.string.notification))
                    .setContentText(todo.text)
                    .build()
                notificationManager.notify(todo.id.hashCode(), notification)
            }
        }
    }
}