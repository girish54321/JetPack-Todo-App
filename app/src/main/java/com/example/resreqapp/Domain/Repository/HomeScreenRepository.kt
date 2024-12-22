package com.example.resreqapp.Domain.Repository

import com.example.mytodoandroid.helper.Resource
import com.example.resreqapp.DataType.RemortData.DeleteResponse
import com.example.resreqapp.DataType.RemortData.LoginResRemote
import com.example.resreqapp.DataType.RemortData.SuccessResponse
import com.example.resreqapp.DataType.RemortData.ToDoInfo
import com.example.resreqapp.DataType.RemortData.ToDoResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import java.io.File


interface HomeScreenRepository {
    suspend fun getUserToDos(): Flow<Resource<Call<ToDoResponse>>>
    suspend fun createToDo(title: String, body: String,state: String): Flow<Resource<Call<SuccessResponse>>>
    suspend fun updateTodo(todoID: String, title: String, body: String,state: String): Flow<Resource<Call<SuccessResponse>>>
    suspend fun getTodoInfo(todoID: String): Flow<Resource<Call<ToDoInfo>>>
    suspend fun deleteTodo(todoID: String): Flow<Resource<Call<DeleteResponse>>>
}