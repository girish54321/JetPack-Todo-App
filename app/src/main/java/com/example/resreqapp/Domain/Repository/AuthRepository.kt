package com.example.resreqapp.Domain.Repository

import com.example.mytodoandroid.helper.Resource
import com.example.resreqapp.DataType.RemortData.LoginResRemote
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import java.io.File


interface AuthRepository {
    suspend fun login(email: String, password: String): Flow<Resource<Call<LoginResRemote>>>
}