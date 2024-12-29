package com.example.resreqapp.Screen.SettingsScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.resreqapp.ViewModals.AuthViewModal
import com.example.resreqapp.ViewModals.SettingsScreenViewModal
import com.example.resreqapp.Views.ErrorScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    authViewModal: AuthViewModal,
) {
    val settingViewModal = hiltViewModel<SettingsScreenViewModal>()
    val appViewModal = settingViewModal.settingScreenState.collectAsState().value
    val authViewModalState = authViewModal.authViewModalState.collectAsState().value

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text("Profile") },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        },
    ) {
        PullToRefreshBox(
            isRefreshing = authViewModalState.isLoading, onRefresh = {
                authViewModal.getUserProfile()
            }, modifier = Modifier.padding(it)
        ) {
            if(authViewModalState.userProfileError != null){
                LazyColumn {
                    item {
                       ErrorScreen(errorMessage = authViewModalState.userProfileError.error?.message, onRetry = {
                           authViewModal.getUserProfile()
                       })
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item {
                        if (appViewModal.showLogOutModal) {
                            AlertDialog(onDismissRequest = {
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
                                        settingViewModal.logout {
                                            authViewModal.logoutUser()
                                        }
                                    }) { Text("Yes") }
                                },
                                dismissButton = {
                                    TextButton(onClick = {
                                        settingViewModal.closeLogoutModal()
                                    }) { Text("No") }
                                })
                        }
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(
                                    modifier = Modifier.height(60.dp),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        "Hello ${authViewModalState.userProfile?.firstName}",
                                        fontSize = 27.sp
                                    )
                                    Text(
                                        "${authViewModalState.userProfile?.email}",
                                        fontSize = 14.sp
                                    )
                                }
                                AsyncImage(
                                    model = "https://irs.www.warnerbros.com/gallery-v2-mobile-jpeg/movies/node/77906/edit/WW-06907r.jpg",
                                    contentDescription = "Translated description of what the image contains",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.size(80.dp)
                                )
                            }
                        }
                        ListItem(modifier = Modifier.clickable {
                            authViewModal.getUserProfile()
//                        settingViewModal.openLogoutModal()
                        },
                            headlineContent = { Text("Logout") },
                            supportingContent = { Text("LogOut form app.") },

                            leadingContent = {
                                Icon(
                                    Icons.Filled.Lock,
                                    contentDescription = "Localized description",
                                )
                            })
                    }
                }
            }
        }
    }
}

