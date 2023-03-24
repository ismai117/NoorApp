package com.example.noorapp.android.splash



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.example.noorapp.android.authentication.login.presentation.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import com.example.noorapp.android.R


@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = koinViewModel(),
    login: () -> Unit,
    home: () -> Unit,
) {

    val isLoggedIn = FirebaseAuth.getInstance().currentUser

    LaunchedEffect(key1 = Unit) {
        delay(2000)
        if (isLoggedIn != null) {
            home()
        } else {
            login()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF202020))
    ) {
        Image(
            painter = painterResource(id = R.drawable.texture_bg),
            contentDescription = "",
            modifier = modifier
                .fillMaxSize()
                .background(color = Color(0xFFC0C0C0).copy(0.20f)),
            contentScale = ContentScale.Crop,
            alpha = 0.80f,
        )
        Column(
            modifier = modifier
                .fillMaxSize()
                .align(Alignment.BottomStart),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "noor",
                fontSize = 67.0.sp,
                color = Color.White,
                letterSpacing = 7.52.sp,
            )
        }
    }


}