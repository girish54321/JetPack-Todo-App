package com.example.resreqapp.Screen.HomeScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.resreqapp.DataType.RemortData.toPageingTodo
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
    val userPadingItem = todoScreenViewModal.useTodoPageData.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        if (!hasRun) {
//               todoScreenViewModal.getUserToDo()
               hasRun = true
        }
    }

//    val scrollBehavior =
//        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
//        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {
//                        todoScreenViewModal.updateTodo(0,"Local Title","Some Long Text")
//                        userPadingItem.
                        userPadingItem.refresh()
                    }) {
                        Icon(Icons.Filled.Add, contentDescription = "Add new Todo")
                    }
                },
                title = {
                    Text(
                        "My Todo",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
//                scrollBehavior = scrollBehavior
            )
        },
    ) {
        if (appViewModalValue.errorMessage != null) {
            ErrorScreen(
                errorIcon = Icons.Default.Warning,
                errorTitle = appViewModalValue.errorMessage.error?.status.toString(),
                errorMessage = appViewModalValue.errorMessage.error?.message,
                onRetry = {
                    userPadingItem.retry()
                }
            )
        } else {
            PullToRefreshBox(
                isRefreshing = false,
//                isRefreshing = userPadingItem.loadState.,
                onRefresh = {
//                    userPadingItem.
//                    userPadingItem.refresh()
                },
                modifier = Modifier.padding(it)
            ) {
                // Handle empty state
                if (userPadingItem.loadState.refresh is LoadState.NotLoading && userPadingItem.itemCount == 0) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text("No items found")
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    items(userPadingItem.itemCount){ index ->
                        val item = userPadingItem[index]
                        ToDoItem(
                            title = item?.title?: "",
                            body = item?.body?: "",
                            isChecked = true,
                            onCheckedChange = {},
                            onClick = {
                                todoScreenViewModal.selectToDo(item!!.toPageingTodo(1))
                                navController.navigate(Screen.ToDoDetailsScreen.rout)
                            }
                        )
                    }
                }
                // Handle footer load state
                userPadingItem.apply {
                    when (loadState.append) {
                        is LoadState.Loading -> {
//                            items {
                                CircularProgressIndicator(modifier = Modifier.fillMaxWidth().padding(16.dp))
//                            }
                        }
                        is LoadState.Error -> {
                                val appendError = (loadState.append as LoadState.Error).error
                                ListItem(
                                    headlineContent = {
                                        Text("Error loading more: ${appendError.localizedMessage}")
                                    },
                                    trailingContent = {
                                        Button(onClick = { userPadingItem.retry() }) {
                                            Text("Retry")
                                        }
                                    }
                                )
                        }
                        else -> {}
                    }
                }
                // Handle initial loading and error states
                when (userPadingItem.loadState.refresh) {
                    is LoadState.Loading -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                    is LoadState.Error -> {
                        val error = (userPadingItem.loadState.refresh as LoadState.Error).error
                        ErrorScreen(
                            errorIcon = Icons.Default.Warning,
                            errorMessage = "Error: ${error.localizedMessage}",
                            onRetry = {
                                userPadingItem.retry()
                            }
                        )
                    }
                    else -> {}
                }
            }
        }
    }
}