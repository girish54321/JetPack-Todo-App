package com.example.resreqapp.Screen.CreateTodo

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.size.Size
import com.example.resreqapp.ViewModals.AuthViewModal
import com.example.resreqapp.ViewModals.HomeScreenViewModal
import com.example.resreqapp.ViewModals.SettingsScreenViewModal
import com.example.resreqapp.Views.AppBackButton
import com.example.resreqapp.Views.AppInputErrorText
import com.example.resreqapp.Views.AppInputText
import com.example.resreqapp.Views.ToDoItem
import com.example.resreqapp.Views.TodoState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTodoScreen(
    navController: NavHostController,
    homeScreenViewModal: HomeScreenViewModal
) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val appViewModal = homeScreenViewModal.homeScreenState.collectAsState().value
    val context = LocalContext.current
    fun getFilePathFromContentUri(context: Context, contentUri: Uri): String? {

        var filePath: String? = null
        val projection = arrayOf(MediaStore.Images.Media.DATA)

        context.contentResolver.query(contentUri, projection, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                filePath = cursor.getString(columnIndex)
            }
        }
        return filePath
    }

    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
            homeScreenViewModal.pickFile(getFilePathFromContentUri(context,uri)!!)
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }



    val isUpdate = appViewModal.selectedTodo.toDoId != null

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                navigationIcon = {
                    AppBackButton(navController)
                },
                scrollBehavior = scrollBehavior,
                title = { Text(if (isUpdate) "Update Todo" else "Create Todo") },
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(it)
                .padding(14.dp)

        ) {
            Card(
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    AppInputText(
                        value = appViewModal.selectedTodo?.title ?: "",
                        label = "Title",
                        onValueChange = {
                            homeScreenViewModal.onTodoTitleChanged(it)
                        },
                        rightIcon = Icons.Filled.Edit,
                        keyboardType = KeyboardType.Text
                    )
                    Box(
                        modifier = Modifier.padding(top = 11.dp)
                    )
                    AppInputText(
                        value = appViewModal.selectedTodo?.body ?: "",
                        label = "Body",
                        onValueChange = {
                            homeScreenViewModal.onTodoBodyChanged(it)
                        },
                        rightIcon = Icons.Filled.Info,
                        keyboardType = KeyboardType.Text,
                        maxLines = 10
                    )
                    Box(
                        modifier = Modifier.padding(top = 11.dp)
                    )
                }
            }
            Box(
                modifier = Modifier.padding(top = 18.dp)
            )
            TodoState(
                options = appViewModal.options,
                onSelectionChange = { selectedIndex ->
                    homeScreenViewModal.selectedToDoState(appViewModal.options[selectedIndex])
                },
                selectedIndex = appViewModal.optionsIndex,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (isUpdate) {
                        homeScreenViewModal.updateTodo(onSuccess = {
                            homeScreenViewModal.hardReload()
                            navController.popBackStack()
                        })
                    } else {
                        homeScreenViewModal.createTodo(onSuccess = {
                            homeScreenViewModal.hardReload()
                            navController.popBackStack()
                        })
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Create Todo")
            }
            Spacer(modifier = Modifier.height(16.dp))
            AppInputErrorText(
                errorText = appViewModal.errorMessage?.error?.message ?: ""
            )
            Button(
                onClick = {
                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Pick Image")
            }
        }
    }
}
