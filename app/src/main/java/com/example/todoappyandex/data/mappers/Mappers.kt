package com.example.todoappyandex.data.mappers

import com.example.todoappyandex.data.local.entities.TodoItemEntity
import com.example.todoappyandex.data.model.TodoItem
import com.example.todoappyandex.data.remote.entities.TodoItemApi
import com.example.todoappyandex.util.importanceFromString
import com.example.todoappyandex.util.importanceToString

fun TodoItem.asEntity() = TodoItemEntity(
    id = id,
    text = text,
    importance = importance,
    deadline = deadline,
    isCompleted = isCompleted,
    createdAt = createdAt,
    modifiedAt = modifiedAt,
    isDeleted = isDeleted
)

fun TodoItem.asApiModel(device: String) = TodoItemApi(
    id = id,
    text = text,
    importance = importanceToString(importance),
    deadline = deadline,
    done = isCompleted,
    createdAt = createdAt,
    changedAt = modifiedAt,
    color = color,
    lastUpdatedBy = device
)

fun TodoItemEntity.asExternalModel() = TodoItem(
    id = id,
    text = text,
    importance = importance,
    deadline = deadline,
    isCompleted = isCompleted,
    createdAt = createdAt,
    modifiedAt = modifiedAt,
    isDeleted = isDeleted
)

fun TodoItemApi.asExternalModel() = TodoItem(
    id = id,
    text = text,
    importance = importanceFromString(importance),
    deadline = deadline,
    isCompleted = done,
    color = color,
    createdAt = createdAt,
    modifiedAt = changedAt
)