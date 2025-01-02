package com.example.resreqapp.DataType.RemortData

import android.net.Uri


data class ToDoResponse (
    val totalPages: Int? = null,
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
    var state: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val userid: String? = null,
    val filePath: String? = null,
    val files: List<File>? = null
)

data class File (
    val fileID: String? = null,
    val fileName: String? = null,
    val fileSize: String? = null,
    val type: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val toDoID: String? = null,
    val userID: String? = null
)

data class SuccessResponse (
   val success: Boolean? = null,
)

data class DeleteResponse (
    val deleted: Boolean? = null,
)