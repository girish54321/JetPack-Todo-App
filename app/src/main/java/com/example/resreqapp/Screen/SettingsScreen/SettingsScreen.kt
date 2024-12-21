package com.example.resreqapp.Screen.SettingsScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.resreqapp.ViewModals.AuthViewModal
import com.example.resreqapp.ViewModals.HomeScreenViewModal
import com.example.resreqapp.ViewModals.SettingsScreenViewModal
import com.example.resreqapp.Views.ToDoItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen (
) {
    val settingViewModal = hiltViewModel<SettingsScreenViewModal>()
    val appViewModal = settingViewModal.settingScreenState.collectAsState().value

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text("Login") },
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
        ) {
            if(appViewModal.showLogOutModal) {
                AlertDialog(
                    onDismissRequest = {
                       settingViewModal.closeLogoutModal()
                    },
                    icon = { Icon(Icons.Filled.Lock, contentDescription = null) },
                    title = { Text(text = "Logout!") },
                    text = {
                        Text(
                            "Are you sure you?"
                        )
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            settingViewModal.logout()
                        }) { Text("Yes") }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            settingViewModal.closeLogoutModal()
                        }) { Text("No") }
                    }
                )
            }
            ListItem(
                modifier = Modifier
                    .clickable { settingViewModal.openLogoutModal() },
                headlineContent = { Text("LogOut") },
                supportingContent = { Text("LogOut form app.") },

                leadingContent = {
                    Icon(
                        Icons.Filled.Lock,
                        contentDescription = "Localized description",
                    )
                }
            )

        }
    }
}

@Preview
@Composable
fun SimpleComposablePreview() {
    SettingsScreen()
}

//@Preview
//fun HomeScreen();
