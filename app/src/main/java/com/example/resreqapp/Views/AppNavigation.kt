package com.example.resreqapp.Views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.resreqapp.Helper.Screen
import com.example.resreqapp.Screen.Auth.LoginScreen
import com.example.resreqapp.Screen.MainScreen.MainScreen
import com.example.resreqapp.Screen.SettingsScreen.SettingsScreen
import com.example.resreqapp.ViewModals.AuthViewModal

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val authViewModal = hiltViewModel<AuthViewModal>()

    val authStateValue = authViewModal.authViewModalState.collectAsState().value

    NavHost(
        navController = navController,
        startDestination = if (authStateValue.isLoggedIn) Screen.MainScreen.rout else Screen.LoginScreen.rout
    ) {
        composable(Screen.LoginScreen.rout) {
            LoginScreen(authViewModal)
        }
        composable(Screen.MainScreen.rout) {
            MainScreen(authViewModal, navController)
        }
        composable(Screen.ToDoDetailsScreen.rout){
            SettingsScreen()
        }
    }

}