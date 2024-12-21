package com.example.resreqapp.Screen.SettingsScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.resreqapp.ViewModals.AuthViewModal
import com.example.resreqapp.ViewModals.HomeScreenViewModal
import com.example.resreqapp.Views.ToDoItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen (
//    navController: NavHostController,
//    paddingValues: PaddingValues
) {
    val homeViewModal = hiltViewModel<HomeScreenViewModal>()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Settings") })
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
        ) {
            LazyColumn {
                items(5) { index ->
                    ToDoItem(
                        title = "Title",
                        body = "This is the body text of the list item.",
                        isChecked = true,
                        onCheckedChange = {}
                    )
                }
            }
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
