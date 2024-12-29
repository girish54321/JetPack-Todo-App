package com.example.resreqapp.Screen.Profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.resreqapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(

) {
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text("Profile") },
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
                .padding( horizontal = 14.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
            ) {


            }
            ListItem(
                modifier = Modifier
                    .clickable {  },
                headlineContent = { Text("Logout") },
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
fun ProfileScreenPreview() {
    ProfileScreen()
}