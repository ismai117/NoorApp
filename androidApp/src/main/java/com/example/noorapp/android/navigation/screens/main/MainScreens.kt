package com.example.noorapp.android.navigation.screens.main


sealed class MainScreens(val route: String) {
    object DiscoverScreen : MainScreens("discover_screen")
    object SearchScreen : MainScreens("search_screen")
    object RemindersScreen : MainScreens("reminders_screen")
    object LibraryScreen : MainScreens("library_screen")
    object SettingsScreen : MainScreens("settings_screen")
}