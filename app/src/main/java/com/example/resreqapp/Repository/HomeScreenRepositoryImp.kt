package com.example.resreqapp.Repository

import com.example.mytodoandroid.helper.Resource
import com.example.resreqapp.API.AppApi
import com.example.resreqapp.DataType.RemortData.CreateTodoRequestBody
import com.example.resreqapp.DataType.RemortData.LoginPostBody
import com.example.resreqapp.DataType.RemortData.SuccessResponse
import com.example.resreqapp.DataType.RemortData.ToDoInfo
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

    override suspend fun createToDo(
        title: String,
        body: String,
        state: String
    ): Flow<Resource<Call<SuccessResponse>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val postData = CreateTodoRequestBody(title = title, body = body, state = state)
                val response = api.createToDo(postData)
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

    override suspend fun updateTodo(
        todoID: String,
        title: String,
        body: String,
        state: String
    ): Flow<Resource<Call<SuccessResponse>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val postData = CreateTodoRequestBody(
                    toDoId = todoID,
                    title = title,
                    body = body,
                    state = state
                )
                val response = api.updateTodo(postData)
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

    override suspend fun getTodoInfo(todoID: String): Flow<Resource<Call<ToDoInfo>>> {
      return flow {
            emit(Resource.Loading())
            try {
                val response = api.getTodoInfo(todoID)
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