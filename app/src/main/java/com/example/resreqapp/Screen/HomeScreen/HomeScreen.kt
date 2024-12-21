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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.example.resreqapp.Helper.Screen
import com.example.resreqapp.ViewModals.HomeScreenViewModal
import com.example.resreqapp.Views.ToDoItem

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen (
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    val authViewModal = hiltViewModel<HomeScreenViewModal>()
    val appViewModalValue = authViewModal.homeScreenState.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Home test") },)
        }
    ) {
            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .padding(it)) {
                items(appViewModalValue.toDoList.size) { index ->
                    val item = authViewModal.homeScreenState.value.toDoList[index]
                    Log.e("Home Screen", "Item render")
                    ToDoItem(
                        title = item.title?: "",
                        body =  item.body?: "",
                        isChecked = true,
                        onCheckedChange = {}
                    )
                }
            }
    }
}