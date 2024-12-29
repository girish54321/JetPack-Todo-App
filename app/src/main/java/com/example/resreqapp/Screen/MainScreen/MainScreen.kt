package com.example.resreqapp.Screen.MainScreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.example.resreqapp.Helper.Screen
import com.example.resreqapp.Screen.HomeScreen.HomeScreen
import com.example.resreqapp.Screen.SettingsScreen.SettingsScreen
import com.example.resreqapp.ViewModals.AuthViewModal
import com.example.resreqapp.ViewModals.HomeScreenViewModal
import com.example.resreqapp.Views.HomeBottomTabBar

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
                    todoScreenViewModal.removeToDo()
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
            0 -> HomeScreen(navController, padding, todoScreenViewModal)
            else -> {
                SettingsScreen(
                    authViewModal
                )
//                ProfileScreen()
            }
        }
    }
}