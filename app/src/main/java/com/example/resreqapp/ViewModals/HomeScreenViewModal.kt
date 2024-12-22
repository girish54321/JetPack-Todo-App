package com.example.resreqapp.ViewModals

import AuthDefaultState
import HomeScreenDefaultState
import android.util.Log
import androidx.compose.ui.text.font.createFontFamilyResolver
import androidx.lifecycle.ViewModel
import com.example.mytodoandroid.helper.Resource
import com.example.resreqapp.DataType.RemortData.DeleteResponse
import com.example.resreqapp.DataType.RemortData.SuccessResponse
import com.example.resreqapp.DataType.RemortData.ToDoInfo
import com.example.resreqapp.DataType.RemortData.ToDoResponse
import com.example.resreqapp.DataType.RemortData.Todo
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

class HomeScreenViewModal(
    private val perf: PerferenceDatastore = Graph.dataSource,
    private val authRepository: HomeScreenRepository = Graph.HomeScreenRepository,
) : ViewModel() {
    private val _appViewModal = MutableStateFlow(HomeScreenDefaultState())
    val homeScreenState = _appViewModal.asStateFlow()

    init {
        getUserToDo()
    }

    //TODO: try this
    fun updateTodo(index: Int, newTitle: String, newDescription: String) {
        _appViewModal.value = homeScreenState.value.copy(
            toDoList = homeScreenState.value.toDoList.mapIndexed { i, todo ->
                if (i == index) {
                    todo.copy(title = newTitle, body = newDescription)
                } else {
                    todo
                }
            }
        )
    }

    fun autoFileTodo(){
        _appViewModal.update {
            it.copy(
                title = homeScreenState.value.selectedTodo?.title ?: "",
                body = homeScreenState.value.selectedTodo?.body ?: "",
                state = homeScreenState.value.selectedTodo?.state ?: "",
            )
        }
    }

    fun onTodoTitleChanged(title: String) {
        _appViewModal.update {
            it.copy(title = title)
        }
    }

    fun onTodoBodyChanged(body: String) {
        _appViewModal.update {
            it.copy(body = body)
        }
    }

    fun getSelectedTodoInfo () {
        if(_appViewModal.value.selectedTodo == null){
            return
        }
        CoroutineScope(Dispatchers.IO).launch {
            authRepository.getTodoInfo(
                todoID = homeScreenState.value.selectedTodo?.toDoId?: ""
            ).collectLatest {
                when(it){
                    is Resource.Loading -> {
                        _appViewModal.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Resource.Error -> {
                        _appViewModal.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                            )
                        }
                    }
                    is Resource.Success -> {
                        it.data?.enqueue(object : Callback<ToDoInfo> {
                            override fun onResponse(
                                call: Call<ToDoInfo>,
                                response: Response<ToDoInfo>
                            ) {
                                if (response.isSuccessful) {
                                    _appViewModal.update {
                                        it.copy(
                                           selectedTodo = response.body()?.todo
                                        )
                                    }
                                    getUserToDo()
                                } else {
                                    if (response.code() == 401) {
                                        _appViewModal.update {
                                            it.copy(
                                                isLoading = false,
                                                isError = true,
                                                errorMessage = parseError(response)
                                            )
                                        }
                                    } else {
                                        _appViewModal.update {
                                            it.copy(
                                                isLoading = false,
                                                isError = true,
                                                errorMessage = parseError(response)
                                            )
                                        }
                                    }
                                }
                            }

                            override fun onFailure(call: Call<ToDoInfo>, t: Throwable) {
                                _appViewModal.update {
                                    it.copy(
                                        isLoading = false,
                                        isError = true,
//                                        errorMessage = parseError(response)
                                    )
                                }
                            }
                        })
                    }
                }
            }
        }
    }

    fun updateTodo(
        onSuccess: () -> Unit = {},
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            authRepository.updateTodo(
                todoID = homeScreenState.value.selectedTodo?.toDoId ?: "",
                title = homeScreenState.value.title ?: "",
                body = homeScreenState.value.body ?: "",
                state = homeScreenState.value.state ?: ""
            ).collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        _appViewModal.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Resource.Error -> {
                        _appViewModal.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                            )
                        }
                    }

                    is Resource.Success -> {
                        it.data?.enqueue(object : Callback<SuccessResponse> {
                            override fun onResponse(
                                call: Call<SuccessResponse>,
                                response: Response<SuccessResponse>
                            ) {
                                if (response.isSuccessful) {
                                    _appViewModal.update {
                                        it.copy(
                                            isLoading = false,
                                            isError = false,
                                            title = "",
                                            body = "",
                                        )
                                    }
                                    onSuccess()
                                    getUserToDo()
                                    getSelectedTodoInfo()
                                } else {
                                    if (response.code() == 401) {
                                        _appViewModal.update {
                                            it.copy(
                                                isLoading = false,
                                                isError = true,
                                                errorMessage = parseError(response)
                                            )
                                        }
                                    } else {
                                        _appViewModal.update {
                                            it.copy(
                                                isLoading = false,
                                                isError = true,
                                                errorMessage = parseError(response)
                                            )
                                        }
                                    }
                                }
                            }

                            override fun onFailure(call: Call<SuccessResponse>, t: Throwable) {
                                _appViewModal.update {
                                    it.copy(
                                        isLoading = false,
                                        isError = true,
//                                        errorMessage = parseError(response)
                                    )
                                }
                            }
                        })
                    }
                }
            }
        }
    }

    fun deleteTodo(
        onSuccess: () -> Unit = {},
    ){
        CoroutineScope(Dispatchers.IO).launch {
            authRepository.deleteTodo(todoID = homeScreenState.value.selectedTodo?.toDoId ?: "")
                .collectLatest {
                    when(it){
                        is Resource.Loading -> {
                            _appViewModal.update {
                                it.copy(isLoading = true)
                            }
                        }

                        is Resource.Error -> {
                            _appViewModal.update {
                                it.copy(
                                    isLoading = false,
                                    isError = true,
                                )
                            }
                        }
                        is Resource.Success -> {
                            it.data?.enqueue(object : Callback<DeleteResponse> {
                                override fun onResponse(
                                    call: Call<DeleteResponse>,
                                    response: Response<DeleteResponse>
                                ) {
                                    if (response.isSuccessful) {
                                        _appViewModal.update {
                                            it.copy(
                                                isLoading = false,
                                                isError = false,
                                                selectedTodo = null,
                                                title = "",
                                                body = ""
                                            )
                                        }
                                        onSuccess()
                                        getUserToDo()
                                    } else {
                                        if (response.code() == 401) {
                                            _appViewModal.update {
                                                it.copy(
                                                    isLoading = false,
                                                    isError = true,
                                                    errorMessage = parseError(response)
                                                )
                                            }
                                        } else {
                                            _appViewModal.update {
                                                it.copy(
                                                    isLoading = false,
                                                    isError = true,
                                                    errorMessage = parseError(response)
                                                )
                                            }
                                        }
                                    }
                                }

                                override fun onFailure(call: Call<DeleteResponse>, t: Throwable) {
                                    _appViewModal.update {
                                        it.copy(
                                            isLoading = false,
                                            isError = true,
//                                        errorMessage = parseError(response)
                                        )
                                    }
                                }
                            })
                        }
                    }
                }
        }
    }

    fun createTodo(
        onSuccess: () -> Unit = {},
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            authRepository.createToDo(
                title = homeScreenState.value.title ?: "",
                body = homeScreenState.value.body ?: "",
                state = homeScreenState.value.state ?: ""
            ).collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        _appViewModal.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Resource.Error -> {
                        _appViewModal.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                            )
                        }
                    }

                    is Resource.Success -> {
                        it.data?.enqueue(object : Callback<SuccessResponse> {
                            override fun onResponse(
                                call: Call<SuccessResponse>,
                                response: Response<SuccessResponse>
                            ) {
                                if (response.isSuccessful) {
                                    _appViewModal.update {
                                        it.copy(
                                            isLoading = false,
                                            isError = false,
                                            selectedTodo = null,
                                            title = "",
                                            body = ""
                                        )
                                    }
                                    onSuccess()
                                    getUserToDo()
                                } else {
                                    if (response.code() == 401) {
                                        _appViewModal.update {
                                            it.copy(
                                                isLoading = false,
                                                isError = true,
                                                errorMessage = parseError(response)
                                            )
                                        }
                                    } else {
                                        _appViewModal.update {
                                            it.copy(
                                                isLoading = false,
                                                isError = true,
                                                errorMessage = parseError(response)
                                            )
                                        }
                                    }
                                }
                            }

                            override fun onFailure(call: Call<SuccessResponse>, t: Throwable) {
                                _appViewModal.update {
                                    it.copy(
                                        isLoading = false,
                                        isError = true,
//                                        errorMessage = parseError(response)
                                    )
                                }
                            }
                        })
                    }
                }
            }
        }
    }
    fun getUserToDo() {
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
                            override fun onResponse(
                                call: Call<ToDoResponse>,
                                response: Response<ToDoResponse>
                            ) {
                                if (response.isSuccessful) {
                                    _appViewModal.update {
                                        response.body()?.todo?.let { it1 ->
                                            it.copy(
                                                isLoading = false,
                                                toDoList = it1
                                            )
                                        }!!
                                    }
                                } else {
                                    if (response.code() == 401) {
                                        _appViewModal.update {
                                            it.copy(
                                                isLoading = false,
                                                errorMessage = parseError(response)
                                            )
                                        }
                                    }
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

    fun selectToDo(item: Todo) {
        _appViewModal.update {
            it.copy(
                selectedTodo = item
            )
        }
    }

    fun removeToDo() {
        _appViewModal.update {
            it.copy(
                selectedTodo = null
            )
        }
    }

    private fun removeToken() {
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