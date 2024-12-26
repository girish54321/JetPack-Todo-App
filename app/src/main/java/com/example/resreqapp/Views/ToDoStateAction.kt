package com.example.resreqapp.Views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TodoState(
    options: List<String>,
    onSelectionChange: (Int) -> Unit,
    selectedIndex:Int
){
    SingleChoiceSegmentedButtonRow(
        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth()
    ) {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                onClick = { onSelectionChange(index)},
                selected = index == selectedIndex,
                label = { Text(label) }
            )
        }
    }

}

@Preview
@Composable
fun TodoStatePreview() {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val options = listOf("Day", "Month", "Week")

    fun onSelectionChange(index: Int){
        selectedIndex = index
    }

    DemoScreen {
      TodoState(
          options=options,
          onSelectionChange = {
              onSelectionChange(it)
          },
          selectedIndex = selectedIndex
      )
    }
}