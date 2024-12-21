package com.example.mytodoandroid.helper

import com.example.resreqapp.DataType.RemortData.ErrorMainBody


sealed class Resource<T>(
    val data: T? = null,
    val errorObj: ErrorMainBody? = null,
) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(errorObj: ErrorMainBody, data: T? = null) : Resource<T>(data, errorObj)
    class Loading<T>(val isLoading: Boolean = true) : Resource<T>(null)
}