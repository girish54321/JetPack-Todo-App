package com.example.resreqapp.Helper

import android.util.Log
import com.example.resreqapp.Graph
import hoods.com.quotesyt.utils.PerferenceDatastore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response


class OAuthInterceptor(
    private val perf: PerferenceDatastore = Graph.dataSource
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val token = runBlocking {
            perf.getToken().firstOrNull()  
        }

        if (token != null) {
            val request = originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
            return chain.proceed(request)
        } else {
            Log.e("OAuthInterceptor", "Token not found in preferences")
            return chain.proceed(originalRequest)
        }
    }
}

class ErrorChecker(
    private val perf: PerferenceDatastore = Graph.dataSource
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val response = chain.proceed(originalRequest)
        // todo deal with the issues the way you need to
        if (response.code == 401) {
            //do something
            runBlocking {
                perf.removeToken()
            }
            Log.e("Erro for 401","Kill the app")
            return response
        }
        return response
    }
}
