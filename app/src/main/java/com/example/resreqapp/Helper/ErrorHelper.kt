package com.example.resreqapp.Helper

import com.example.resreqapp.DataType.RemortData.ErrorBody
import com.example.resreqapp.DataType.RemortData.ErrorMainBody
import kotlinx.serialization.json.Json
import org.json.JSONObject

fun errorHelper(message: String, errorCode: Int? = 999): ErrorMainBody {
    val errorBody =
        ErrorBody(
            status = errorCode,
            message = message
        )

    val errorMainBody =
        ErrorMainBody(
            error = errorBody
        )
    return  errorMainBody
}

fun createApiErrorBody(body: String): ErrorBody {
    val jObjError = JSONObject(
        body
    )
    val errorBody: ErrorBody =
        Json.decodeFromString<ErrorBody>(jObjError.toString())
    return errorBody
}