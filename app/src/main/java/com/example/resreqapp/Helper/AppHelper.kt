package com.example.resreqapp.Helper

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination


sealed class Screen(val rout: String) {
    object LoginScreen: Screen("LoginScreen")
    object MainScreen: Screen("MainScreen")
}

fun NavController.navigateToSingleTop(route: String) {
    navigate(route = route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}