package com.example.resreqapp.Screen.Auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.resreqapp.ViewModals.AuthViewModal
import com.example.resreqapp.Views.AppInputErrorText
import com.example.resreqapp.Views.AppInputText
import com.example.resreqapp.Views.KeyboardAware
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    authViewModal: AuthViewModal,
) {
    val authStateValue = authViewModal.authViewModalState.collectAsState().value

    val isSignIn = authStateValue.isSignIn

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val keyboardHeight = WindowInsets.ime.getBottom(LocalDensity.current)

    val screenText = if (isSignIn) "Sign In" else "Sign Up"

    LaunchedEffect(key1 = keyboardHeight) {
        coroutineScope.launch {
            scrollState.scrollBy(keyboardHeight.toFloat())
        }
    }

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text(screenText) },
            )
        }
    ) {
        KeyboardAware {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .windowInsetsPadding(WindowInsets.safeContent.only(WindowInsetsSides.Bottom + WindowInsetsSides.Top))
                    .padding(horizontal = 16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .imePadding()
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
                    AnimatedVisibility(!isSignIn) {
                        Column {
                            AppInputText(
                                value = authStateValue.userFirstName ?: "",
                                label = "First Name",
                                rightIcon = Icons.Filled.Person,
                                errorMessage = authStateValue.userFirstNameError,
                                keyboardType = KeyboardType.Text,
                                maxLines = 1,
                                onValueChange = { authViewModal.onFirstNameChange(it) }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            AppInputText(
                                value = authStateValue.userLastName ?: "",
                                label = "Last Name",
                                rightIcon = Icons.Filled.Person,
                                errorMessage = authStateValue.userLastNameError,
                                keyboardType = KeyboardType.Text,
                                maxLines = 1,
                                onValueChange = { authViewModal.onLastNameChange(it) }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }

                    AppInputText(
                        value = authStateValue.userEmail ?: "",
                        label = "Email",
                        rightIcon = Icons.Filled.Email,
                        errorMessage = authStateValue.userEmailError,
                        keyboardType = KeyboardType.Email,
                        maxLines = 1,
                        onValueChange = { authViewModal.onEmailChange(it) }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    AppInputText(
                        value = authStateValue.userPassword ?: "",
                        label = "Password",
                        rightIcon = Icons.Filled.Lock,
                        passwordVisible = false,
                        maxLines = 1,
                        errorMessage = authStateValue.userPasswordError,
                        keyboardType = KeyboardType.Password,
                        onValueChange = { authViewModal.onPasswordChange(it) }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    AppInputErrorText(
                        errorText = authStateValue.errorMessage?.error?.message ?: ""
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            if (isSignIn) {
                                authViewModal.onLogin()
                            } else {
                                authViewModal.onSingUp()
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(screenText)
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Center
                    ) {
                        TextButton(onClick = {
                            authViewModal.toggleSignUp()
                        }) {
                            Text(if (isSignIn) "Don't have an account? Sign Up" else "Have account? Sign Up")
                        }
                    }
                }
            }
        }
    }
}

