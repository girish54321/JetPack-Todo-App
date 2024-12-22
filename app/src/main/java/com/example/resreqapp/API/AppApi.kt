package com.example.resreqapp.API


import com.example.resreqapp.DataType.RemortData.CreateTodoRequestBody
import com.example.resreqapp.DataType.RemortData.LoginPostBody
import com.example.resreqapp.DataType.RemortData.LoginResRemote
import com.example.resreqapp.DataType.RemortData.SuccessResponse
import com.example.resreqapp.DataType.RemortData.ToDoInfo
import com.example.resreqapp.DataType.RemortData.ToDoResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AppApi {
    @POST("auth/login")
    fun userLogin(
        @Body body: LoginPostBody
    ): Call<LoginResRemote>

    @GET("/api/v1/todo/getalltodos")
    fun getUserToDoApi(): Call<ToDoResponse>

    @POST("/api/v1/todo/addtodo")
    fun createToDo(
        @Body body: CreateTodoRequestBody
    ): Call<SuccessResponse>

    @POST("/api/v1/todo/updatetodo")
    fun updateTodo(
        @Body body: CreateTodoRequestBody
    ): Call<SuccessResponse>

    @GET("/api/v1/todo/gettodo/{id}")
    fun getTodoInfo(@Path("id") id: String?):Call<ToDoInfo>
}

