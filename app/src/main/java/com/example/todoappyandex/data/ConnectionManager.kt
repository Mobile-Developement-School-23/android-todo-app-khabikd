package com.example.todoappyandex.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ConnectionManager {
    fun setupNetworkListener(
        context: Context,
        todoItemRepository: TodoRepository,
        coroutineScope: CoroutineScope
    ) {
        val networkCallback: ConnectivityManager.NetworkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)

                coroutineScope.launch {
                    todoItemRepository.syncTodoItems()
                }
            }
        }

        val connectivityManager: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }
}