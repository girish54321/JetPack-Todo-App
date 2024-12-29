package com.example.resreqapp.ViewModals

import AuthDefaultState
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mytodoandroid.helper.Resource
import com.example.resreqapp.DataType.RemortData.LoginResRemote
import com.example.resreqapp.DataType.RemortData.ToDoInfo
import com.example.resreqapp.DataType.RemortData.UserProfileDatan
import com.example.resreqapp.DataType.RemortData.createThrowableError
import com.example.resreqapp.DataType.RemortData.parseError
import com.example.resreqapp.Domain.Repository.AuthRepository
import com.example.resreqapp.Graph
import com.example.resreqapp.Helper.createApiErrorBody
import com.example.resreqapp.Helper.errorHelper
import hoods.com.quotesyt.utils.PerferenceDatastore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModal(
    private val perf: PerferenceDatastore = Graph.dataSource,
    private val authRepository: AuthRepository = Graph.authRepository
) : ViewModel() {

    private val _appViewModal = MutableStateFlow(AuthDefaultState())
    val authViewModalState = _appViewModal.asStateFlow()

    init {
        getToken()
        getUserProfile()
    }

    fun onEmailChange(email: String) {
        _appViewModal.update {
            it.copy(userEmail = email)
        }
    }

    fun onFirstNameChange(firstName: String) {
        _appViewModal.update {
            it.copy(userFirstName = firstName)
        }
    }

    fun onLastNameChange(lastName: String) {
        _appViewModal.update {
            it.copy(userLastName = lastName)
        }
    }

    fun toggleSignUp() {
        _appViewModal.update {
            it.copy(isSignIn = !authViewModalState.value.isSignIn)
        }
    }

    fun onPasswordChange(password: String) {
        _appViewModal.update {
            it.copy(userPassword = password)
        }
    }


    fun onSingUp() {
        if (authViewModalState.value.userFirstName?.isEmpty() == true) {
            _appViewModal.update {
                it.copy(userFirstNameError = "First Name is Required!")
            }
            return
        } else {
            _appViewModal.update {
                it.copy(userFirstNameError = null)
            }
        }

        if (authViewModalState.value.userLastName?.isEmpty() == true) {
            _appViewModal.update {
                it.copy(userLastNameError = "Last Name is Required!")
            }
            return
        } else {
            _appViewModal.update {
                it.copy(userLastNameError = null)
            }
        }

        if (authViewModalState.value.userEmail?.isEmpty() == true) {
            _appViewModal.update {
                it.copy(userEmailError = "Email is Required!")
            }
            return
        } else {
            _appViewModal.update {
                it.copy(userEmailError = null)
            }
        }

        if (authViewModalState.value.userPassword?.isEmpty() == true) {
            _appViewModal.update {
                it.copy(userPasswordError = "Password is Required!")
            }
            return
        } else {
            _appViewModal.update {
                it.copy(userPasswordError = null)
            }
        }

        _appViewModal.update {
            it.copy(isLoading = true)
        }
        CoroutineScope(Dispatchers.IO).launch {
            authRepository.singUp(
                email = _appViewModal.value.userEmail ?: "",
                password = _appViewModal.value.userPassword ?: "",
                firstName = _appViewModal.value.userFirstName ?: "",
                lastName = _appViewModal.value.userLastName ?: "",

                ).collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        _appViewModal.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Success -> {
                        it.data?.enqueue(object : Callback<LoginResRemote> {
                            override fun onResponse(
                                call: Call<LoginResRemote>,
                                response: Response<LoginResRemote>,
                            ) {
                                if (response.isSuccessful) {
                                    saveToken(token = response.body()?.accessToken ?: "")
                                    _appViewModal.update {
                                        it.copy(
                                            isLoggedIn = true,
                                            errorMessage = null
                                        )
                                    }
                                } else {
                                    val errorResponse = parseError(response)
                                    _appViewModal.update {
                                        it.copy(
                                            isLoading = false,
                                            errorMessage = errorResponse,
                                        )
                                    }
                                }
                            }

                            override fun onFailure(call: Call<LoginResRemote>, t: Throwable) {
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
                        _appViewModal.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                errorMessage = errorHelper(message = "API onFailure: "),
                            )
                        }
                    }
                }
            }
        }
    }

    fun onLogin() {
        if (authViewModalState.value.userEmail?.isEmpty() == true) {
            _appViewModal.update {
                it.copy(userEmailError = "Email is Required!")
            }
            return
        } else {
            _appViewModal.update {
                it.copy(userEmailError = null)
            }
        }

        if (authViewModalState.value.userPassword?.isEmpty() == true) {
            _appViewModal.update {
                it.copy(userPasswordError = "Password is Required!")
            }
            return
        } else {
            _appViewModal.update {
                it.copy(userPasswordError = null)
            }
        }
        _appViewModal.update {
            it.copy(isLoading = true)
        }
        CoroutineScope(Dispatchers.IO).launch {
            authRepository.login(
                email = _appViewModal.value.userEmail ?: "",
                password = _appViewModal.value.userPassword ?: ""
            ).collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        _appViewModal.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Success -> {
                        it.data?.enqueue(object : Callback<LoginResRemote> {
                            override fun onResponse(
                                call: Call<LoginResRemote>,
                                response: Response<LoginResRemote>,
                            ) {
                                if (response.isSuccessful) {
                                    saveToken(token = response.body()?.accessToken ?: "")
                                    _appViewModal.update {
                                        it.copy(
                                            isLoggedIn = true,
                                            errorMessage = null
                                        )
                                    }
                                } else {
                                    val errorResponse = parseError(response)
                                    _appViewModal.update {
                                        it.copy(
                                            isLoading = false,
                                            errorMessage = errorResponse,
                                        )
                                    }
                                }
                            }

                            override fun onFailure(call: Call<LoginResRemote>, t: Throwable) {
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
                        _appViewModal.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                errorMessage = errorHelper(message = "API onFailure: "),
                            )
                        }
                    }
                }
            }
        }
    }

    fun getUserProfile() {
        CoroutineScope(Dispatchers.IO).launch {
            authRepository.getUserProfile(
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
                                userProfileError = errorHelper("User profile not found")
                            )
                        }
                    }

                    is Resource.Success -> {
                        it.data?.enqueue(object : Callback<UserProfileDatan> {
                            override fun onResponse(
                                call: Call<UserProfileDatan>,
                                response: Response<UserProfileDatan>
                            ) {
                                if (response.isSuccessful) {
                                    _appViewModal.update {
                                        it.copy(
                                            userProfile = response.body()?.user!!,
                                            userProfileError = null,
                                            isLoading = false
                                        )
                                    }
                                } else {
                                    if (response.code() == 401) {
                                        _appViewModal.update {
                                            it.copy(
                                                isLoading = false,
                                                userProfileError = parseError(response)
                                            )
                                        }
                                    } else {
                                        _appViewModal.update {
                                            it.copy(
                                                isLoading = false,
                                                userProfileError = parseError(response)
                                            )
                                        }
                                    }
                                }
                            }

                            override fun onFailure(call: Call<UserProfileDatan>, t: Throwable) {
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

    private fun getToken() {
        CoroutineScope(Dispatchers.IO).launch {
            val token = perf.getToken().firstOrNull()
            if (token != "") {
                _appViewModal.update {
                    it.copy(
                        isLoggedIn = true
                    )
                }
            } else {
                _appViewModal.update {
                    it.copy(
                        isLoggedIn = false
                    )
                }
            }
        }
    }

    fun logoutUser() {
        _appViewModal.update {
            it.copy(
                isLoggedIn = false
            )
        }
    }

    // Save Token
    private fun saveToken(token: String?) {
        if (token == null) {
            return
        }
        CoroutineScope(Dispatchers.IO).launch {
            perf.setUserToken(token)
            _appViewModal.update {
                it.copy(
                    isLoggedIn = true
                )
            }
        }
    }
}