package com.example.resreqapp.Screen.MainScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.resreqapp.Helper.Screen
import com.example.resreqapp.Screen.HomeScreen.HomeScreen
import com.example.resreqapp.Screen.SettingsScreen.SettingsScreen
import com.example.resreqapp.ViewModals.AuthViewModal
import com.example.resreqapp.ViewModals.HomeScreenViewModal
import com.example.resreqapp.Views.HomeBottomTabBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    authViewModal: AuthViewModal,
    navController: NavHostController,
    todoScreenViewModal: HomeScreenViewModal
) {
    val authStateValue = authViewModal.authViewModalState.collectAsState().value
    var selectedItem by rememberSaveable { mutableStateOf(0) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.CreateTodoScreen.rout)
                },
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        },
        bottomBar = {
            HomeBottomTabBar(
                tabs = authStateValue.bottomNavItems,
                selectedItem = selectedItem,
                onTabSelected = { selectedItem = it }
            )
        }
    ) {
        val padding = it
        when (selectedItem) {
            0 -> HomeScreen(navController, padding,todoScreenViewModal)
            else -> {
                SettingsScreen(
//                    navController,padding
                )
            }
        }
    }
}