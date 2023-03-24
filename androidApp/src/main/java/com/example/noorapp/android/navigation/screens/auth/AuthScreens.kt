package com.example.noorapp.android.navigation.screens.auth


sealed class AuthScreens(val route: String) {
    object StarterScreen : AuthScreens("starter_screen")
    object LoginScreen : AuthScreens("login_screen")
    object RegisterScreen : AuthScreens("register_screen")
    object ForgotPasswordScreen : AuthScreens("forgotPassword_screen")
}