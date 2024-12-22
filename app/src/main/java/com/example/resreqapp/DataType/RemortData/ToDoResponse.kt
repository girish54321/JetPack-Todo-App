package com.example.resreqapp.DataType.RemortData


data class ToDoResponse (
    val totalPages: Long? = null,
    val total: Long? = null,
    val perPage: Long? = null,
    val page: Long? = null,
    val todo: List<Todo>? = null
)

data class ToDoInfo (
    val todo: Todo? = null,
)

data class Todo (
    val toDoId: String? = null,
    val title: String? = null,
    val body: String? = null,
    val state: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val userid: String? = null
)

data class SuccessResponse (
   val success: Boolean? = null,
)

data class DeleteResponse (
    val deleted: Boolean? = null,
)