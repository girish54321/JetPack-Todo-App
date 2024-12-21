package com.example.resreqapp.Views

import BottomNavItem
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HomeBottomTabBar(
    tabs: List<BottomNavItem>,
    selectedItem: Int,
    onTabSelected: (index: Int) -> Unit,
) {
    NavigationBar {
        tabs.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = index == selectedItem,
                onClick = {
                    onTabSelected(index)
                },
                icon = {
                    BadgedBox(
                        badge = {
                            if (item.badges != 0) {
                                Badge {
                                    Text(
                                        text = item.badges.toString(),
                                        style = MaterialTheme.typography.labelMedium,
                                        color = Color.White,
                                        modifier = Modifier.padding(1.dp)
                                    )
                                }
                            } else if (item.hasNews) {
                                Badge()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (index == selectedItem) item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.title,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                label = {
                    Text(text = item.title)
                },
            )
        }
    }
}