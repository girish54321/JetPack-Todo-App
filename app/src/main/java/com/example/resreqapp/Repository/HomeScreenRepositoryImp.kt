package com.example.resreqapp.Repository

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.net.toFile
import com.example.mytodoandroid.helper.Resource
import com.example.resreqapp.API.AppApi
import com.example.resreqapp.DataType.RemortData.CreateTodoRequestBody
import com.example.resreqapp.DataType.RemortData.DeleteResponse
import com.example.resreqapp.DataType.RemortData.SuccessResponse
import com.example.resreqapp.DataType.RemortData.ToDoInfo
import com.example.resreqapp.DataType.RemortData.ToDoResponse
import com.example.resreqapp.DataType.RemortData.Todo
import com.example.resreqapp.Domain.Repository.HomeScreenRepository
import com.example.resreqapp.Helper.errorHelper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.HttpException
import java.io.File
import java.io.IOException


class HomeScreenRepositoryImp(
    private val api: AppApi
) : HomeScreenRepository {
//    override suspend fun getUserToDos(): Flow<Resource<Call<ToDoResponse>>> {
//        return flow {
//            emit(Resource.Loading())
//            try {
//                val response = api.getUserToDoApi()
//                emit(Resource.Success(response))
//            } catch (e: IOException) {
//                e.printStackTrace()
//                emit(Resource.Error(errorObj = errorHelper(message = e.toString())))
//                return@flow
//            } catch (e: HttpException) {
//                e.printStackTrace()
//                emit(Resource.Error(errorObj = errorHelper(message = e.toString())))
//                return@flow
//            } catch (e: Exception) {
//                e.printStackTrace()
//                emit(Resource.Error(errorObj = errorHelper(message = e.toString())))
//                return@flow
//            }
//        }
//    }

    override suspend fun getToDo(page:Int): Flow<Resource<Call<ToDoResponse>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = api.getToDo(page,5)
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
        item:Todo,
        imageUrl:String?,
    ): Flow<Resource<Call<SuccessResponse>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val file = imageUrl?.let { File(it) }
                val titlePart = RequestBody.create("text/plain".toMediaTypeOrNull(), item.title ?: "")
                val bodyPart = RequestBody.create("text/plain".toMediaTypeOrNull(), item.body ?: "")
                val statePart = RequestBody.create("text/plain".toMediaTypeOrNull(), item.state ?: "")
                var filePart:MultipartBody.Part? = null
                if(file != null){
                    filePart = MultipartBody.Part.createFormData("file", file.name, file.asRequestBody())
                }
//                filePart = MultipartBody.Part.createFormData("file", file.name, file.asRequestBody())
                val response = api.createToDo(filePart, titlePart, bodyPart, statePart)
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
        updateObj: Todo
    ): Flow<Resource<Call<SuccessResponse>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val postData = CreateTodoRequestBody(
                    toDoId = updateObj.toDoId ?: "1",
                    title = updateObj.title ?: "",
                    body = updateObj.body ?: "",
                    state = updateObj.state ?: "",
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

    override suspend fun deleteTodo(todoID: String): Flow<Resource<Call<DeleteResponse>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = api.deleteTodo(todoID)
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