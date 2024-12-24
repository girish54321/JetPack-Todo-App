package com.example.resreqapp.DataType.RemortData


import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import java.io.IOException

fun getErrorResponse(response:String):ErrorBody {
    return Gson().fromJson(response, ErrorBody::class.java)
}

//@Serializable
//data class ErrorBody(
//    @SerializedName("error")
//    val error: String = "",
//)

data class ErrorMainBody (

    @SerializedName("error" ) var error : ErrorBody? = ErrorBody()

)


@Serializable
data class ErrorBody (
    @SerialName("status"  ) var status  : Int?    = null,
    @SerialName("message" ) var message : String? = null

)

//@Serializable
//data class ErrorBody(
//    @SerialName("error")
//    val error: String = ""
//)

fun parseError(response: Response<*>): ErrorMainBody? {
    return try {
        response.errorBody()?.string()?.let {
            Gson().fromJson(it, ErrorMainBody::class.java)
        }
    } catch (e: IOException) {
        null
    }
}

fun createThrowableError(error:Throwable): ErrorMainBody {
    return ErrorMainBody(error = ErrorBody(status = 999, message = error.message))
}