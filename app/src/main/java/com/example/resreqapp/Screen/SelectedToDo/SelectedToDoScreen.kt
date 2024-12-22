package com.example.resreqapp.Screen.SelectedToDo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.resreqapp.Helper.Screen
import com.example.resreqapp.ViewModals.HomeScreenViewModal
import com.example.resreqapp.Views.AppBackButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectedToDoScreen(
    todoScreenViewModal: HomeScreenViewModal,
    navController: NavHostController,
    ) {
    val appViewModal = todoScreenViewModal.homeScreenState.collectAsState().value
    var expanded by remember { mutableStateOf(false) }

    fun openCLoseMenu(){
        expanded = !expanded
    }

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text(appViewModal.selectedTodo?.title ?: "Not There") },
                navigationIcon = {
                    AppBackButton(navController)
                },
                actions = {
                    IconButton(onClick = { openCLoseMenu() }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More options")
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            leadingIcon={ Icon(Icons.Default.Edit, contentDescription = "More options")},
                            text = { Text("Edit") },
                            onClick = {
                                todoScreenViewModal.autoFileTodo()
                                openCLoseMenu()
                                navController.navigate(Screen.CreateTodoScreen.rout)
                            }
                        )
                        DropdownMenuItem(
                            leadingIcon={ Icon(Icons.Default.Delete, contentDescription = "More options")},
                            text = { Text("Delete") },
                            onClick = { openCLoseMenu() }
                        )
                    }
                }
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
        ) {
            ListItem(
                modifier = Modifier
                    .clickable {  },
                headlineContent = { Text(appViewModal.selectedTodo?.title ?: "Not There") },
                supportingContent = { Text(appViewModal.selectedTodo?.body ?: "Not THere") },
            )
        }
    }
}

