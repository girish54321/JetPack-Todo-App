package com.example.resreqapp.Screen.Auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.resreqapp.ViewModals.AuthViewModal
import com.example.resreqapp.Views.AppInputErrorText
import com.example.resreqapp.Views.AppInputText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    authViewModal: AuthViewModal,
){
    val authStateValue = authViewModal.authViewModalState.collectAsState().value
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text("Login") },
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .imePadding()
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                AppInputText(
                    value = authStateValue.userEmail ?: "",
                    label = "Email",
                    rightIcon = Icons.Filled.Email,
                    errorMessage = authStateValue.userEmailError,
                    onValueChange = { authViewModal.onEmailChange(it) }
                )
                Spacer(modifier = Modifier.height(16.dp))
                AppInputText(
                    value = authStateValue.userPassword ?: "",
                    label = "Password",
                    rightIcon = Icons.Filled.Lock,
                    errorMessage =  authStateValue.userPasswordError,
                    onValueChange = { authViewModal.onPasswordChange(it) }
                )
                Spacer(modifier = Modifier.height(16.dp))
                AppInputErrorText(
                    errorText = authStateValue.errorMessage?.error?.message ?: ""
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        authViewModal.onLogin()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Login")
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center
                ) {
                    TextButton(onClick = {
                    }) {
                        Text("Don't have an account? Sign Up")
                    }
                }
            }
        }
    }
}

