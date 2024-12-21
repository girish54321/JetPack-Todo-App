package com.example.resreqapp.ViewModals

import AuthDefaultState
import HomeScreenDefaultState
import SettingsScreenDefaultState
import android.util.Log
import androidx.compose.ui.text.font.createFontFamilyResolver
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

class SettingsScreenViewModal (
    private val perf: PerferenceDatastore = Graph.dataSource,
) : ViewModel() {
    private val _appViewModal = MutableStateFlow(SettingsScreenDefaultState())
    val settingScreenState = _appViewModal.asStateFlow()

    fun openLogoutModal() {
        _appViewModal.update {
            it.copy(showLogOutModal = true)
        }
    }

    fun logout() {
        CoroutineScope(Dispatchers.IO).launch {
            perf.removeToken()
//            appViewModal.update {
//                it.copy(
//                    isLoggedIn = false
//                )
//            }
        }
        closeLogoutModal()
        // navigate to login screen or reset state to default state (clear all user data)
        // for example:
        // _appViewModal.update { it.copy(userEmail = null, userPassword = null) }
        // _appViewModal.update { it.copy(isLoading = false, isError = false, errorMessage = null) }
        // _appViewModal.update { it.copy(bottomNavItems = defaultBottomNavItems) }
        // _appViewModal.update { it.copy(toDoList = emptyList()) }
        // _appViewModal.update { it.copy(showLogOutModal = false) }
    }

    fun closeLogoutModal() {
        _appViewModal.update {
            it.copy(showLogOutModal = false)
        }
    }
}