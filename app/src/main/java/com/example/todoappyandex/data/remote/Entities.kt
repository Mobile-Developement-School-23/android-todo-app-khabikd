package com.example.todoappyandex.data.remote

import androidx.room.PrimaryKey
import com.example.todoappyandex.domain.model.TodoItem
import com.google.gson.annotations.SerializedName
import java.util.UUID


data class TodoListResponse(
    val status: String,
    val list: List<TodoItem>,
    val revision: Int
)

data class TodoListRequest(
    val list: List<TodoItem>
)

data class TodoResponse(
    val status: String,
    val element: TodoItem,
    val revision: Int
)

data class TodoRequest(
    @SerializedName("id") val id: String = UUID.randomUUID().toString(),
    @SerializedName("text") val text: String,
    @SerializedName("importance")val importance: TodoItem.Importance,
    @SerializedName("deadline") val deadline: Long? = null,
    @SerializedName("done") var done: Boolean,
    @SerializedName("color") val color: String? = "#FFFFFF",
    @SerializedName("created_at") val created_at: Long = System.currentTimeMillis(),
    @SerializedName("changed_at") val changed_at: Long? = null,
    @SerializedName("last_updated_by") val last_updated_by: String
)