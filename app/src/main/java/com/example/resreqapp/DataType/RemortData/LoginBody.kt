package com.example.resreqapp.DataType.RemortData

import com.google.gson.annotations.SerializedName

data class LoginPostBody(
    val email: String = "",
    val password: String = ""
)

//data class LoginResRemote(
//    @SerializedName("token")
//    val token: String = ""
//)

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
