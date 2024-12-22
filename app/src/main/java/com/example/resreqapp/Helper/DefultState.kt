
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.resreqapp.DataType.RemortData.ErrorBody
import com.example.resreqapp.DataType.RemortData.ErrorMainBody
import com.example.resreqapp.DataType.RemortData.Todo

data class BottomNavItem(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean = false,
    val badges: Int
)

data class AuthDefaultState(
    val isLoggedIn: Boolean = false,
    val userToken: String? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: ErrorMainBody? = null,

    val userEmail: String? = null,
    val userEmailError: String? = null,
    val userPasswordError: String? = null,
    val userPassword: String? = null,

    val bottomNavItems: List<BottomNavItem> = listOf(
        BottomNavItem(
            title = "Home",
            route = "home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNews = false,
            badges = 8
        ),
        BottomNavItem(
            title = "Settings",
            route = "settings",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
            hasNews = false,
            badges = 0
        ),
    )
)

data class HomeScreenDefaultState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: ErrorMainBody? = null,
    val toDoList: List<Todo> = emptyList(),
    val selectedTodo: Todo? = null
)

data class SettingsScreenDefaultState(
    val showLogOutModal: Boolean = false,
)