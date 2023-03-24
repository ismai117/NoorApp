package com.example.noorapp.android.navigation.graphs.root


import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.noorapp.android.collection.CollectionScreen
import com.example.noor.android.main.MainScreen
import com.example.noor.android.main.settings.changePassword.ChangePasswordScreen
import com.example.noor.android.main.settings.editProfile.EditProfileScreen
import com.example.noorapp.android.navigation.graphs.auth.authNavigation
import com.example.noorapp.android.navigation.graphs.root.NavGraphs.AUTH
import com.example.noorapp.android.navigation.graphs.root.NavGraphs.MAIN
import com.example.noorapp.android.navigation.screens.root.RootScreens
import com.example.noorapp.android.player.PlayerScreen
import com.example.noorapp.android.splash.SplashScreen
import com.example.noorapp.storage.Playlist
import com.google.firebase.auth.FirebaseAuth


object NavGraphs {
    const val ROOT = "root"
    const val AUTH = "auth"
    const val MAIN = "main"
}


@Composable
fun RootNavigation(
    navController: NavController,
) {
    NavHost(
        navController = navController as NavHostController,
        route = NavGraphs.ROOT,
        startDestination = RootScreens.SplashScreen.route,
    ) {
        composable(route = RootScreens.SplashScreen.route) {
            SplashScreen(
                home = {
                    navController.popBackStack()
                    navController.navigate(MAIN)
                },
                login = {
                    navController.popBackStack()
                    navController.navigate(AUTH)
                }
            )
        }
        authNavigation(
            signIn = {
                navController.popBackStack()
                navController.navigate(MAIN)
            },
            navController = navController
        )
        composable(route = MAIN) {
            MainScreen(
                signOut = {
                    FirebaseAuth.getInstance().signOut()
                    navController.popBackStack()
                    navController.navigate(AUTH)
                },
                playlist = {
                    navController.currentBackStackEntry?.savedStateHandle?.set("playlist", it)
                    navController.navigate(RootScreens.CollectionScreen.route)
                },
                editProfile = {
                    navController.navigate(RootScreens.EditProfileScreen.route)
                },
                changePassword = {
                    navController.navigate(RootScreens.ChangePasswordScreen.route)
                }
            )
        }
        composable(
            route = RootScreens.CollectionScreen.route
        ) {
            val playlist =
                navController.previousBackStackEntry?.savedStateHandle?.get<Playlist>("playlist")
            CollectionScreen(
                navController = navController,
                playlist = playlist,
                backPressed = {
                    navController.navigate(MAIN)
                },
                playVideo = { lesson, video ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("lesson", lesson)
                    navController.currentBackStackEntry?.savedStateHandle?.set("video", video)
                    navController.navigate(RootScreens.PlayerScreen.route)
                }
            )
        }
        composable(
            route = RootScreens.PlayerScreen.route
        ) {
            val lesson = navController.previousBackStackEntry?.savedStateHandle?.get<String>("lesson")
            val video = navController.previousBackStackEntry?.savedStateHandle?.get<String>("video")
            PlayerScreen(
                lesson = lesson,
                video = video
            )
        }
        composable(RootScreens.EditProfileScreen.route){
            EditProfileScreen(
                navController = navController
            )
        }
        composable(
            route = RootScreens.ChangePasswordScreen.route
        ) {
            ChangePasswordScreen(
                navController = navController
            )
        }
    }
}