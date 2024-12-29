package com.example.resreqapp.Screen.HomeScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import com.example.resreqapp.Helper.Screen
import com.example.resreqapp.ViewModals.HomeScreenViewModal
import com.example.resreqapp.Views.ErrorScreen
import com.example.resreqapp.Views.ToDoItem

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    paddingValues: PaddingValues,
    todoScreenViewModal: HomeScreenViewModal,
) {
    val appViewModalValue = todoScreenViewModal.homeScreenState.collectAsState().value
    var hasRun by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!hasRun) {
               todoScreenViewModal.hardReload()
               hasRun = true
        }
    }

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
//                colors = TopAppBarDefaults.topAppBarColors(
//                    titleContentColor = MaterialTheme.colorScheme.primary,
//                ),
//                actions = {
//                    IconButton(onClick = { todoScreenViewModal.updateTodo(0,"Local Title","Some Long Text") }) {
//                        Icon(Icons.Filled.Add, contentDescription = "Add new Todo")
//                    }
//                },
                title = {
                    Text(
                        "My Todo",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) {
        if (appViewModalValue.errorMessage != null) {
            ErrorScreen(
                errorIcon = Icons.Default.Warning,
                errorTitle = appViewModalValue.errorMessage.error?.status.toString(),
                errorMessage = appViewModalValue.errorMessage.error?.message,
                onRetry = {
                    todoScreenViewModal.hardReload()
                }
            )
        } else {
            PullToRefreshBox(
                isRefreshing = appViewModalValue.isLoading,
                onRefresh = {
                    todoScreenViewModal.hardReload()
                },
                modifier = Modifier.padding(it)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    items(appViewModalValue.toDoList.size + 1) { index ->
                        if (index == appViewModalValue.toDoList.size) {
                            LaunchedEffect(true) {

                            }
                        } else {
                            val item = todoScreenViewModal.homeScreenState.value.toDoList[index]
                            ToDoItem(
                                title = item.title ?: "",
                                body = item.body ?: "",
                                isChecked = true,
                                onCheckedChange = {},
                                onClick = {
                                    todoScreenViewModal.selectToDo(item)
                                    navController.navigate(Screen.ToDoDetailsScreen.rout)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}