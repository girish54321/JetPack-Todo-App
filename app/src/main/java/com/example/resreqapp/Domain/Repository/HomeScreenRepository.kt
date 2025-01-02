package com.example.resreqapp.Domain.Repository

import com.example.mytodoandroid.helper.Resource
import com.example.resreqapp.DataType.RemortData.DeleteResponse
import com.example.resreqapp.DataType.RemortData.SuccessResponse
import com.example.resreqapp.DataType.RemortData.ToDoInfo
import com.example.resreqapp.DataType.RemortData.ToDoResponse
import com.example.resreqapp.DataType.RemortData.Todo
import kotlinx.coroutines.flow.Flow
import retrofit2.Call


interface HomeScreenRepository {
    suspend fun getToDo(page:Int): Flow<Resource<Call<ToDoResponse>>>
    suspend fun createToDo(item: Todo,imageUri:String?): Flow<Resource<Call<SuccessResponse>>>
    suspend fun updateTodo(todoItem: Todo): Flow<Resource<Call<SuccessResponse>>>
    suspend fun getTodoInfo(todoID: String): Flow<Resource<Call<ToDoInfo>>>
    suspend fun deleteTodo(todoID: String): Flow<Resource<Call<DeleteResponse>>>
}