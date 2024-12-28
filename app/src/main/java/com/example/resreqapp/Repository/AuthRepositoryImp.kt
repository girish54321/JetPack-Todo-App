package com.example.resreqapp.Repository

import com.example.mytodoandroid.helper.Resource
import com.example.resreqapp.API.AppApi
import com.example.resreqapp.DataType.RemortData.LoginPostBody
import com.example.resreqapp.DataType.RemortData.LoginResRemote
import com.example.resreqapp.DataType.RemortData.SignUpPostBody
import com.example.resreqapp.Domain.Repository.AuthRepository
import com.example.resreqapp.Helper.errorHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.HttpException
import java.io.IOException


class AuthRepositoryImp(
    private val api: AppApi
) : AuthRepository {
    override suspend fun login(
        email: String,
        password: String
    ): Flow<Resource<Call<LoginResRemote>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val postData = LoginPostBody(email, password)
                val response = api.userLogin(postData)
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

    override suspend fun singUp(
        email: String,
        password: String,
        lastName: String,
        firstName: String
    ): Flow<Resource<Call<LoginResRemote>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val postData = SignUpPostBody(email, password,firstName, lastName)
                val response = api.userSignup(postData)
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