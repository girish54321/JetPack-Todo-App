package com.example.resreqapp.ViewModals

import AuthDefaultState
import HomeScreenDefaultState
import android.net.Uri
import android.os.Handler
import android.util.Log
import androidx.compose.ui.text.font.createFontFamilyResolver
import androidx.lifecycle.ViewModel
import com.example.mytodoandroid.helper.Resource
import com.example.resreqapp.DataType.RemortData.DeleteResponse
import com.example.resreqapp.DataType.RemortData.ErrorBody
import com.example.resreqapp.DataType.RemortData.ErrorMainBody
import com.example.resreqapp.DataType.RemortData.SuccessResponse
import com.example.resreqapp.DataType.RemortData.ToDoInfo
import com.example.resreqapp.DataType.RemortData.ToDoResponse
import com.example.resreqapp.DataType.RemortData.Todo
import com.example.resreqapp.DataType.RemortData.createThrowableError
import com.example.resreqapp.DataType.RemortData.parseError
import com.example.resreqapp.Domain.Repository.HomeScreenRepository
import com.example.resreqapp.Graph
import com.example.resreqapp.Helper.errorHelper
import hoods.com.quotesyt.utils.PerferenceDatastore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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


    //TODO: update item on Index
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

    fun pickFile(filePath:String){
        _appViewModal.update {
            it.copy(selectedTodo = it.selectedTodo.copy(filePath = filePath))
        }
    }

    fun onTodoTitleChanged(title: String) {
        _appViewModal.update { currentTodo ->
            currentTodo.copy(
                selectedTodo = currentTodo.selectedTodo.copy(title = title)
            )
        }
    }

    fun onTodoBodyChanged(body: String) {
        _appViewModal.update {
            it.copy(
                selectedTodo = it.selectedTodo.copy(body = body)
            )
        }
    }

    fun getSelectedTodoInfo() {
        CoroutineScope(Dispatchers.IO).launch {
            authRepository.getTodoInfo(
                todoID = homeScreenState.value.selectedTodo.toDoId ?: ""
            ).collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        _appViewModal.update {
                            it.copy(
                                isLoading = true,
                                errorMessage = null
                            )
                        }
                    }

                    is Resource.Error -> {
                        _appViewModal.update {
                            it.copy(
                                isLoading = false,
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
                                            selectedTodo = response.body()?.todo!!,
                                            errorMessage = null
                                        )
                                    }
                                    hardReload()
                                    selectedToDoState(response.body()?.todo?.state ?: "")
                                } else {
                                    if (response.code() == 401) {
                                        _appViewModal.update {
                                            it.copy(
                                                isLoading = false,
                                                errorMessage = parseError(response)
                                            )
                                        }
                                    } else {
                                        _appViewModal.update {
                                            it.copy(
                                                isLoading = false,
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
                                        errorMessage = createThrowableError(t)
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
            val updateStateObj = homeScreenState.value.selectedTodo
            updateStateObj.state = homeScreenState.value.options[homeScreenState.value.optionsIndex]?: ""

            authRepository.updateTodo(
                updateStateObj
            ).collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        _appViewModal.update {
                            it.copy(
                                isLoading = true,
                                errorMessage = null
                            )
                        }
                    }

                    is Resource.Error -> {
                        _appViewModal.update {
                            it.copy(
                                isLoading = false,
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
                                            errorMessage = null
                                        )
                                    }
                                    onSuccess()
                                    getSelectedTodoInfo()
                                } else {
                                    getSelectedTodoInfo()
                                    if (response.code() == 401) {
                                        _appViewModal.update {
                                            it.copy(
                                                isLoading = false,
                                                errorMessage = parseError(response)
                                            )
                                        }
                                    } else {
                                        _appViewModal.update {
                                            it.copy(
                                                isLoading = false,
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
                                        errorMessage = createThrowableError(t)
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
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            authRepository.deleteTodo(todoID = homeScreenState.value.selectedTodo?.toDoId ?: "")
                .collectLatest {
                    when (it) {
                        is Resource.Loading -> {
                            _appViewModal.update {
                                it.copy(
                                    isLoading = true,
                                    errorMessage = null
                                )
                            }
                        }

                        is Resource.Error -> {
                            _appViewModal.update {
                                it.copy(
                                    isLoading = false,
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
                                                selectedTodo = Todo(),
                                                errorMessage = null
                                            )
                                        }
                                        onSuccess()
                                    } else {
                                        if (response.code() == 401) {
                                            _appViewModal.update {
                                                it.copy(
                                                    isLoading = false,
                                                    errorMessage = parseError(response)
                                                )
                                            }
                                        } else {
                                            _appViewModal.update {
                                                it.copy(
                                                    isLoading = false,
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
                                            errorMessage = createThrowableError(t)
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
        if(_appViewModal.value.selectedTodo == null) {
            return
        }
        val updateStateObj = homeScreenState.value.selectedTodo
        updateStateObj.state = homeScreenState.value.options[homeScreenState.value.optionsIndex]?: ""
        CoroutineScope(Dispatchers.IO).launch {
            authRepository.createToDo(updateStateObj,_appViewModal.value.selectedTodo.filePath).collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        _appViewModal.update {
                            it.copy(
                                isLoading = true,
                                errorMessage = null
                            )
                        }
                    }

                    is Resource.Error -> {
                        _appViewModal.update {
                            it.copy(
                                isLoading = false,
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
                                            selectedTodo = Todo(),
                                            errorMessage = null,
                                        )
                                    }
                                    onSuccess()
                                } else {
                                    if (response.code() == 401) {
                                        _appViewModal.update {
                                            it.copy(
                                                isLoading = false,
                                                errorMessage = parseError(response)
                                            )
                                        }
                                    } else {
                                        _appViewModal.update {
                                            it.copy(
                                                isLoading = false,
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
                                        errorMessage = createThrowableError(t)
                                    )
                                }
                            }
                        })
                    }
                }
            }
        }
    }

    fun hardReload() {
        _appViewModal.update {
            it.copy(
                isLoading = true,
                errorMessage = null,
                toDoList = emptyList(),
                toDoListCurrentPage = 1
            )
        }
        getUserToDo()
    }

    fun selectedToDoState(state: String) {
        val index = _appViewModal.value.options.indexOf(state)
        _appViewModal.update {
            it.copy(
                optionsIndex = index
            )
        }
    }

    fun getUserToDo() {
        CoroutineScope(Dispatchers.IO).launch {
            authRepository.getToDo(
                page =  _appViewModal.value.toDoListCurrentPage
            ).collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        _appViewModal.update {
                            it.copy(
                                isLoading = true,
                                errorMessage = null
                            )
                        }
                    }

                    is Resource.Success -> {
                        it.data?.enqueue(object : Callback<ToDoResponse> {
                            override fun onResponse(
                                call: Call<ToDoResponse>,
                                response: Response<ToDoResponse>
                            ) {
                                if (response.isSuccessful) {
                                    val totalPages = response.body()!!.totalPages
                                    val cruuetPage = _appViewModal.value.toDoListCurrentPage
                                    var newPageNumber = 0
                                    if (totalPages != null) {
                                        if (totalPages > cruuetPage) {
                                           newPageNumber++
                                        }
                                    }
                                    _appViewModal.update {
                                        response.body()?.todo?.let { newTodos ->
                                            it.copy(
                                                isLoading = false,
                                                toDoList = it.toDoList + newTodos,
                                                errorMessage = null,
                                                toDoListCurrentPage = newPageNumber
                                            )
                                        } ?: it.copy(
                                            isLoading = false,
                                        )
                                    }
                                } else {
                                    _appViewModal.update {
                                        it.copy(
                                            isLoading = false,
                                            errorMessage = parseError(response)
                                        )
                                    }
                                }
                            }

                            override fun onFailure(call: Call<ToDoResponse>, t: Throwable) {
                                Log.e("OH ERROR", "Oh Error")
                                _appViewModal.update {
                                    it.copy(
                                        isLoading = false,
                                        errorMessage = createThrowableError(t)
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
        selectedToDoState(item.state!!)
    }

    fun removeToDo() {
        _appViewModal.update {
            it.copy(
                selectedTodo = Todo()
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