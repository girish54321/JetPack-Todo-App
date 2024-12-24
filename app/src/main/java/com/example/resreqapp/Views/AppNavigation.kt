package com.example.resreqapp.Views

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.resreqapp.Helper.Screen
import com.example.resreqapp.Screen.Auth.LoginScreen
import com.example.resreqapp.Screen.CreateTodo.CreateTodoScreen
import com.example.resreqapp.Screen.MainScreen.MainScreen
import com.example.resreqapp.Screen.SelectedToDo.SelectedToDoScreen
import com.example.resreqapp.ViewModals.AuthViewModal
import com.example.resreqapp.ViewModals.HomeScreenViewModal

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val authViewModal = hiltViewModel<AuthViewModal>()
    val todoScreenViewModal = hiltViewModel<HomeScreenViewModal>()
    val authStateValue = authViewModal.authViewModalState.collectAsState().value

    NavHost(
        navController = navController,
        startDestination = if (authStateValue.isLoggedIn) Screen.MainScreen.rout else Screen.LoginScreen.rout
    ) {
        composable(
            Screen.LoginScreen.rout,
            enterTransition = ::slideInToLeft,
            exitTransition = ::slideOutToLeft,
            popEnterTransition = ::slideInToRight,
            popExitTransition = ::slideOutToRight
        ) {
            LoginScreen(authViewModal)
        }
        composable(
            Screen.MainScreen.rout,
            enterTransition = ::slideInToLeft,
            exitTransition = ::slideOutToLeft,
            popEnterTransition = ::slideInToRight,
            popExitTransition = ::slideOutToRight
        ) {
            MainScreen(authViewModal, navController, todoScreenViewModal)
        }
        composable(
            Screen.ToDoDetailsScreen.rout,
            enterTransition = ::slideInToLeft,
            exitTransition = ::slideOutToLeft,
            popEnterTransition = ::slideInToRight,
            popExitTransition = ::slideOutToRight
        ) {
            SelectedToDoScreen(
                todoScreenViewModal,
                navController
            )
        }
        composable(
            Screen.CreateTodoScreen.rout,
            enterTransition = ::slideInToLeft,
            exitTransition = ::slideOutToLeft,
            popEnterTransition = ::slideInToRight,
            popExitTransition = ::slideOutToRight
        ) {
            CreateTodoScreen(
                navController,
                todoScreenViewModal
            )
        }
    }
}

fun slideInToLeft(scope: AnimatedContentTransitionScope<NavBackStackEntry>): EnterTransition {
    return scope.slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(300)
    )
}

fun slideInToRight(scope: AnimatedContentTransitionScope<NavBackStackEntry>): EnterTransition {
    return scope.slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(300)
    )
}

fun slideOutToLeft(scope: AnimatedContentTransitionScope<NavBackStackEntry>): ExitTransition {
    return scope.slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(300)
    )
}

fun slideOutToRight(scope: AnimatedContentTransitionScope<NavBackStackEntry>): ExitTransition {
    return scope.slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(300)
    )
}