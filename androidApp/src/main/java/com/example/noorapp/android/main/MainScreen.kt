package com.example.noor.android.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShieldMoon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.rememberNavController
import com.example.noorapp.android.navigation.graphs.main.MainGraph
import com.example.noorapp.storage.Playlist


private sealed class BottomNavigationScreen(
    val title: String,
    val icon: ImageVector,
    val route: String,
) {
    object DiscoverScreen : BottomNavigationScreen(
        title = "Discover",
        icon = Icons.Filled.Home,
        route = "discover_screen"
    )

    object SearchScreen : BottomNavigationScreen(
        title = "Search",
        icon = Icons.Filled.Search,
        route = "search_screen"
    )

    object RemindersScreen : BottomNavigationScreen(
        title = "Reminders",
        icon = Icons.Filled.ShieldMoon,
        route = "reminders_screen"
    )

    object LibraryScreen : BottomNavigationScreen(
        title = "Library",
        icon = Icons.Filled.Bookmark,
        route = "library_screen"
    )
}

private val screens = listOf(
    BottomNavigationScreen.DiscoverScreen,
    BottomNavigationScreen.SearchScreen,
    BottomNavigationScreen.RemindersScreen,
    BottomNavigationScreen.LibraryScreen
)

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    signOut: () -> Unit,
    playlist: (Playlist) -> Unit,
    editProfile: () -> Unit,
    changePassword: () -> Unit
) {

    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()

    Scaffold(
        scaffoldState = scaffoldState
//        bottomBar = {
//            BottomNavigation(
//                modifier = modifier.fillMaxWidth(),
//                backgroundColor = Color(0xFF202020),
//                contentColor = Color(0xFF808191),
//            ) {
//                screens.forEach { screen ->
//                    val navBackStackEntry by navController.currentBackStackEntryAsState()
//                    val currentDestination = navBackStackEntry?.destination
//                    BottomNavigationItem(
//                        icon = {
//                            Icon(imageVector = screen.icon, contentDescription = "")
//                        },
//                        label = {
//                            Text(text = screen.title)
//                        },
//                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
//                        onClick = {
//                            navController.navigate(screen.route) {
//                                popUpTo(navController.graph.findStartDestination().id) {
//                                    saveState = true
//                                }
//                                launchSingleTop = true
//                                restoreState = true
//                            }
//                        },
//                        selectedContentColor = Color(0xFFFFC700),
//                        unselectedContentColor = Color(0xFF808191)
//                    )
//                }
//            }
//        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            MainGraph(
                navController = navController,
                signOut = {
                    signOut()
                },
                playlist = {
                    playlist(it)
                },
                editProfile = {
                    editProfile()
                },
                changePassword ={
                    changePassword()
                }
            )
        }
    }

}