package com.example.resreqapp.Screen.HomeScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.resreqapp.ViewModals.AuthViewModal
import com.example.resreqapp.ViewModals.HomeScreenViewModal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen (
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    val authViewModal = hiltViewModel<HomeScreenViewModal>()



    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Home") })
        }
    ) {
        Column(
            modifier = Modifier
               .fillMaxWidth()
               .padding(it)
        ) {
            Button(onClick = { authViewModal.toTestAuth() }) {
                Text("Filled")
            }
        }
    }
}