package com.example.noorapp.android.container

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.noorapp.android.navigation.graphs.root.RootNavigation


@Composable
fun ContainerScreen() {

    val navController = rememberNavController()

    RootNavigation(
        navController = navController
    )

}