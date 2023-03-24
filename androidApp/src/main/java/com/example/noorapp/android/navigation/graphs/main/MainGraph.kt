package com.example.noorapp.android.navigation.graphs.main


import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.noorapp.storage.Playlist
import com.example.noor.android.main.discover.presentation.DiscoverScreen
import com.example.noor.android.main.library.LibraryScreen
import com.example.noor.android.main.reminders.RemindersScreen
import com.example.noor.android.main.search.SearchScreen
import com.example.noor.android.main.settings.SettingsScreen
import com.example.noorapp.android.navigation.graphs.root.NavGraphs
import com.example.noorapp.android.navigation.screens.main.MainScreens




@Composable
fun MainGraph(
    navController: NavController,
    signOut: () -> Unit,
    playlist: (Playlist) -> Unit,
    editProfile: () -> Unit,
    changePassword: () -> Unit
) {
    NavHost(
        navController = navController as NavHostController,
        route = NavGraphs.MAIN,
        startDestination = MainScreens.DiscoverScreen.route,
    ) {
        composable(route = MainScreens.DiscoverScreen.route) {
            DiscoverScreen(
                navController = navController,
                playlist = {
                    playlist(it)
                }
            )
        }
        composable(route = MainScreens.SearchScreen.route) {
            SearchScreen(
                navController = navController
            )
        }
        composable(route = MainScreens.RemindersScreen.route) {
            RemindersScreen(
                navController = navController
            )
        }
        composable(route = MainScreens.LibraryScreen.route) {
            LibraryScreen(
                navController = navController
            )
        }
        composable(route = MainScreens.SettingsScreen.route) {
            SettingsScreen(
                navController = navController,
                signOut = {
                    signOut()
                },
                editProfile = {
                    editProfile()
                },
                changePassword = {
                    changePassword()
                }
            )
        }
    }
}