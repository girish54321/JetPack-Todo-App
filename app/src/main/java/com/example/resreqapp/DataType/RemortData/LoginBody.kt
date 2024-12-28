package com.example.resreqapp.DataType.RemortData

import com.google.gson.annotations.SerializedName

data class LoginPostBody(
    val email: String = "",
    val password: String = ""
)

data class SignUpPostBody(
    val email: String = "",
    val password: String = "",
    val firstName: String = "",
    val lastName: String = ""
)

//if (!toDoRequest?.title) {
//    throw createError.BadRequest("Title is required")
//}
//if (!toDoRequest?.body) {
//    throw createError.BadRequest("Body is required")
//}
//if (!toDoRequest?.state) {
//    throw createError.BadRequest("State is required")
//}

data class CreateTodoRequestBody(
    val title: String = "",
    val body: String = "",
    val state: String = "",
    val toDoId: String = "",
)

data class LoginResRemote (
    @SerializedName("user")
    val user: LogedInUser? = null,
    val accessToken: String? = null,
    val refreshToken: String? = null
)

data class LogedInUser (
    val userid: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val deletedAt: Any? = null
)
