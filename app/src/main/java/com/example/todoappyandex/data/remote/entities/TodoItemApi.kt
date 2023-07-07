package com.example.todoappyandex.data.remote.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TodoItemApi(
    @SerialName("id") var id: String,
    @SerialName("text") var text: String,
    @SerialName("importance") var importance: String,
    @SerialName("deadline") var deadline: Long?,
    @SerialName("done") var done: Boolean,
    @SerialName("color") var color: String?,
    @SerialName("created_at") var createdAt: Long,
    @SerialName("changed_at") var changedAt: Long?,
    @SerialName("last_updated_by") var lastUpdatedBy: String
)

@Serializable
data class TodoItemResponse(
    @SerialName("status") val status: String,
    @SerialName("element") val todoItem: TodoItemApi,
    @SerialName("revision") var revision: Int
)

@Serializable
data class TodoItemsResponse(
    @SerialName("status") val status: String,
    @SerialName("list") val list: List<TodoItemApi>,
    @SerialName("revision") val revision: Int
)

@Serializable
data class TodoItemRequest(
    @SerialName("element") val todoItem: TodoItemApi,
)

@Serializable
data class TodoItemsRequest(
    @SerialName("list") val todoListItem: List<TodoItemApi>,
)