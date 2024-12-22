package com.example.resreqapp.Screen.HomeScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.example.resreqapp.Helper.Screen
import com.example.resreqapp.ViewModals.AuthViewModal
import com.example.resreqapp.ViewModals.HomeScreenViewModal
import com.example.resreqapp.Views.AppBackButton
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

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
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
        PullToRefreshBox(
            isRefreshing = appViewModalValue.isLoading,
            onRefresh = {
                todoScreenViewModal.getUserToDo()
            },
            modifier = Modifier.padding(it)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(appViewModalValue.toDoList.size) { index ->
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