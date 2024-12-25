package com.example.resreqapp.API


import com.example.resreqapp.DataType.RemortData.CreateTodoRequestBody
import com.example.resreqapp.DataType.RemortData.DeleteResponse
import com.example.resreqapp.DataType.RemortData.LoginPostBody
import com.example.resreqapp.DataType.RemortData.LoginResRemote
import com.example.resreqapp.DataType.RemortData.SuccessResponse
import com.example.resreqapp.DataType.RemortData.ToDoInfo
import com.example.resreqapp.DataType.RemortData.ToDoResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AppApi {
    @POST("auth/login")
    fun userLogin(
        @Body body: LoginPostBody
    ): Call<LoginResRemote>

    @GET("/api/v1/todo/getalltodos")
    suspend fun getUserToDoApi(
        @Query("page") page: Int,
        @Query("size") perPage: Int = 10,
    ): ToDoResponse

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

    @DELETE("/api/v1/todo/deletetodo/{id}")
    fun deleteTodo(@Path("id") id: String?):Call<DeleteResponse>
}

