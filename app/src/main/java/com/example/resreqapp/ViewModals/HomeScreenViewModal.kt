package com.example.resreqapp.ViewModals

import AuthDefaultState
import HomeScreenDefaultState
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mytodoandroid.helper.Resource
import com.example.resreqapp.DataType.RemortData.ToDoResponse
import com.example.resreqapp.DataType.RemortData.parseError
import com.example.resreqapp.Domain.Repository.HomeScreenRepository
import com.example.resreqapp.Graph
import hoods.com.quotesyt.utils.PerferenceDatastore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeScreenViewModal (
    private val perf: PerferenceDatastore = Graph.dataSource,
    private val authRepository: HomeScreenRepository = Graph.HomeScreenRepository,
) : ViewModel() {
    private val _appViewModal = MutableStateFlow(HomeScreenDefaultState())
    val homeScreenState = _appViewModal.asStateFlow()

    fun toTestAuth (){
        CoroutineScope(Dispatchers.IO).launch {
            authRepository.getUserToDos(
            ).collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        _appViewModal.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        it.data?.enqueue(object : Callback<ToDoResponse> {
                            override fun onResponse(call: Call<ToDoResponse>, response: Response<ToDoResponse>) {
                                if (response.isSuccessful) {
                                    _appViewModal.update {
                                        it.copy(
                                            isLoading = false,
                                            toDoList = it.toDoList?: emptyList()
                                        )
                                    }
                                } else {
                                    if (response.code() == 401){
                                        _appViewModal.update {
                                            it.copy(
                                                isLoading = false,
                                                errorMessage = parseError(response)
                                            )
                                        }
                                    }
//                                    doTheLog("AuthViewModal", "Error: ${response.code()} - ${response.message()}")
                                }
                            }
                            override fun onFailure(call: Call<ToDoResponse>, t: Throwable) {
                              _appViewModal.update {
                                  it.copy(
                                      isLoading = false,
//                                      errorMessage = parseError(t.message)
                                  )

                              }
                            }
                        })
                    }

                    is Resource.Error -> {

                    }
                }
            }
        }
    }
    private fun removeToken(){
        CoroutineScope(Dispatchers.IO).launch {
            perf.removeToken()
//            appViewModal.update {
//                it.copy(
//                    isLoggedIn = false
//                )
//            }
        }
    }
}