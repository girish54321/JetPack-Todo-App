package com.example.resreqapp.DataType.RemortData


data class ToDoResponse (
    val totalPages: Int? = null,
    val total: Long? = null,
    val perPage: Long? = null,
    val page: Int? = null,
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
    val userid: String? = null,
    val currentPage: Int
)

fun Todo.toPageingTodo(currentPage: Int): Todo {
    return Todo(toDoId, title,body, state,createdAt,updatedAt,userid,currentPage)
}

data class SuccessResponse (
   val success: Boolean? = null,
)

data class DeleteResponse (
    val deleted: Boolean? = null,
)