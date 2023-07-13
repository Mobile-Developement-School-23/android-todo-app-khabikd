package com.example.todoappyandex.data

import android.content.SharedPreferences
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

private enum class KEYS {
    DEVICE,
    REVISION
}

@Singleton
class DataStorage @Inject constructor ( // scope
    private val sharedPref: SharedPreferences,
) {
    private val mutex = Mutex()
    private var lastRevision: Int? = null

    suspend fun saveRevision(revision: Int) {
        mutex.withLock {
            lastRevision = revision
            saveToPreferences(revision, KEYS.REVISION)
        }
    }

    suspend fun getRevision(): Int {
        return mutex.withLock {
            lastRevision ?: sharedPref.getInt(KEYS.REVISION.name, 0).also { lastRevision = it }
        }
    }

    val device: String = sharedPref.getString(KEYS.DEVICE.name, null) ?: run {
        val id = UUID.randomUUID().toString()
        saveToPreferences(id, KEYS.DEVICE)
        id
    }

    private fun <T> saveToPreferences(value: T?, key: KEYS) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        when (value) {
            is Int -> editor.putInt(key.name, value)
            is String -> editor.putString(key.name, value)
        }
        editor.apply()
    }
}