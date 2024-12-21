package com.example.resreqapp.Views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppInputErrorText(errorText: String? = null) {
    if (errorText.isNullOrEmpty()) {
        return
    }

    Column(
        modifier = Modifier
            .padding(top = 8.dp)
    ) {
        Text(
            text = errorText ?: "Error",
            fontSize = 16.sp,
            color = Color.Red,
            fontWeight = FontWeight.W600,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 4.dp),
            textAlign = TextAlign.End
        )
    }

}