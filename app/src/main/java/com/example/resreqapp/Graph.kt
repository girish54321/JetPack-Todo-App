package com.example.resreqapp

import android.annotation.SuppressLint
import android.content.Context
import com.example.jetpackunsplash.Const.Constants.BASE_URL
import com.example.resreqapp.API.AppApi
import com.example.resreqapp.Domain.Repository.AuthRepository
import com.example.resreqapp.Domain.Repository.HomeScreenRepository
import com.example.resreqapp.Helper.ErrorChecker
import com.example.resreqapp.Helper.OAuthInterceptor
import com.example.resreqapp.Repository.AuthRepositoryImp
import com.example.resreqapp.Repository.HomeScreenRepositoryImp
import hoods.com.quotesyt.utils.PerferenceDatastore
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


@SuppressLint("StaticFieldLeak")
object Graph {
    private var intercptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    lateinit var dataSource: PerferenceDatastore

    lateinit var api: AppApi

    val authRepository: AuthRepository by lazy {
        AuthRepositoryImp(api)
    }
    val HomeScreenRepository: HomeScreenRepository by lazy {
        HomeScreenRepositoryImp(api)
    }



    fun createErrorChecker(){
//        val okHttpClient: OkHttpClient = HexFormat.Builder()
////            .addInterceptor(object : Interceptor() {
////                @Throws(IOException::class)
////                fun intercept(chain: Chain): Response {
////                    val request: Request = chain.request()
////                    val response: Response = chain.proceed(request)
////
////                    // todo deal with the issues the way you need to
////                    if (response.code() == SomeCode) {
////                        //do something
////                        return response
////                    }
////
////                    return response
////                }
////            })
////            .build()
    }

//    fun apiWithAuthRepository () {
//        val authInterceptor = Interceptor { chain ->
//            val token = dataSource.getToken().toString()// Fetch the token from your datastore
//            val request = chain.request().newBuilder().apply {
//                if (token != "") {
//                    addHeader("Authorization", "Bearer $token")
//                }
//            }.build()
//            chain.proceed(request)
//        }
//        val client: OkHttpClient = OkHttpClient.Builder()
//            .addInterceptor(intercptor)
//            .addInterceptor(
//                OAuthInterceptor()
//            )
//            .build()
//        api = Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(client)
//            .build()
//            .create(AppApi::class.java)
//    }


    fun provide(context1: Context) {
        dataSource = PerferenceDatastore(context1)
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(intercptor)
            .addInterceptor(
                OAuthInterceptor()
            )
            .addInterceptor(
                ErrorChecker()
            )
            .build()
        api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(AppApi::class.java)
    }
}