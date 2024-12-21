package com.example.resreqapp.Repository

import com.example.mytodoandroid.helper.Resource
import com.example.resreqapp.API.AppApi
import com.example.resreqapp.DataType.RemortData.LoginPostBody
import com.example.resreqapp.DataType.RemortData.ToDoResponse
import com.example.resreqapp.Domain.Repository.HomeScreenRepository
import com.example.resreqapp.Helper.errorHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.HttpException
import java.io.IOException


class HomeScreenRepositoryImp(
    private val api: AppApi
) : HomeScreenRepository {
    override suspend fun getUserToDos(): Flow<Resource<Call<ToDoResponse>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = api.getUserToDoApi()
                emit(Resource.Success(response))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(errorObj = errorHelper(message = e.toString())))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(errorObj = errorHelper(message = e.toString())))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(errorObj = errorHelper(message = e.toString())))
                return@flow
            }
        }
    }
}