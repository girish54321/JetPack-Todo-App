package com.example.resreqapp.API


import com.example.resreqapp.DataType.RemortData.CreateTodoRequestBody
import com.example.resreqapp.DataType.RemortData.DeleteResponse
import com.example.resreqapp.DataType.RemortData.LoginPostBody
import com.example.resreqapp.DataType.RemortData.LoginResRemote
import com.example.resreqapp.DataType.RemortData.SignUpPostBody
import com.example.resreqapp.DataType.RemortData.SuccessResponse
import com.example.resreqapp.DataType.RemortData.ToDoInfo
import com.example.resreqapp.DataType.RemortData.ToDoResponse
import com.example.resreqapp.DataType.RemortData.UserProfileDatan
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface AppApi {
    @POST("auth/login")
    fun userLogin(
        @Body body: LoginPostBody
    ): Call<LoginResRemote>

    @POST("auth/signup")
    fun userSignup(
        @Body body: SignUpPostBody
    ): Call<LoginResRemote>

    @GET("/api/v1/profile/user-profile")
    fun getUserProfile(): Call<UserProfileDatan>

    @GET("/api/v1/todo/getalltodos")
    fun getUserToDoApi(): Call<ToDoResponse>

    @GET("/api/v1/todo/getalltodos")
    fun getToDo(
        @Query("page") page: Int,
        @Query("size") perPage: Int = 10,
    ): Call<ToDoResponse>

    @Multipart
    @POST("/api/v1/todo/addtodo")
     fun createToDo(
    @Part file: MultipartBody.Part?,
    @Part("title") title: RequestBody,
    @Part("body") body: RequestBody,
    @Part("state") state: RequestBody,
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

