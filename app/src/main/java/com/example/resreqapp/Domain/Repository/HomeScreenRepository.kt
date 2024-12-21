package com.example.resreqapp.Domain.Repository

import com.example.mytodoandroid.helper.Resource
import com.example.resreqapp.DataType.RemortData.LoginResRemote
import com.example.resreqapp.DataType.RemortData.ToDoResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import java.io.File


interface HomeScreenRepository {
    suspend fun getUserToDos(): Flow<Resource<Call<ToDoResponse>>>
}