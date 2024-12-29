package com.example.resreqapp.Views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AppInputText(
    value: String,
    label: String,
    onValueChange: (String) -> Unit = {},
    rightIcon: ImageVector,
    keyboardType: KeyboardType,
    maxLines: Int = 1,
    errorMessage: String? = null,
    passwordVisible: Boolean = true
) {
    Column {
        OutlinedTextField(
            value = value,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            onValueChange = onValueChange,
            label = { Text(label) },
            maxLines = maxLines,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                Icon(
                    imageVector = rightIcon,
                    contentDescription = "",
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
        AppInputErrorText(
            errorText = errorMessage
        )
    }
}


@Composable
fun Textarea(
    value: String,
    label: String,
    onValueChange: (String) -> Unit = {},
    rightIcon: ImageVector,
) {
    OutlinedTextField(
        value = value,
        label = { Text(label) },
        onValueChange = onValueChange,
        trailingIcon = {
            Icon(
                imageVector = rightIcon,
                contentDescription = "",
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    )
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun AppInputTextPreview() {
    //ImageVector
   Scaffold {
       Column (
           modifier = Modifier
              .padding(18.dp)
       ) {
           AppInputText(
               value = "",
               label = "Email",
               onValueChange = {},
               rightIcon = Icons.Filled.Home,
               keyboardType = KeyboardType.Email
           )
       }
   }
}