package com.example.noorapp.android.navigation.screens.root


sealed class RootScreens(val route: String) {
    object SplashScreen : RootScreens("splash_screen")
    object PlayerScreen : RootScreens("player_screen")
    object CollectionScreen : RootScreens("collection_screen")
    object EditProfileScreen : RootScreens("editProfile_screen")
    object ChangePasswordScreen : RootScreens("changePassword_Screen")
}