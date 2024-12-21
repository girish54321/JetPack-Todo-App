package com.example.resreqapp.API


import com.example.resreqapp.DataType.RemortData.LoginPostBody
import com.example.resreqapp.DataType.RemortData.LoginResRemote
import com.example.resreqapp.DataType.RemortData.ToDoResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AppApi {
    @POST("auth/login")
    fun userLogin(
        @Body body: LoginPostBody
    ): Call<LoginResRemote>

    @GET("/api/v1/todo/getalltodos")
    fun getUserToDoApi(): Call<ToDoResponse>
}

