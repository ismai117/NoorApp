package com.example.noorapp.android.authentication.starter



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.noorapp.android.R
import com.example.noorapp.android.navigation.screens.auth.AuthScreens

@Composable
fun StarterScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF000000))
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
                .wrapContentSize()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "noor",
                fontSize = 67.0.sp,
                color = Color.White,
                letterSpacing = 7.52.sp,
            )
            Spacer(modifier = modifier.padding(50.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Button(
                    onClick = {
                        navController.navigate(AuthScreens.LoginScreen.route)
                    },
                    modifier = modifier
                        .width(331.dp)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFFFD300),
                        contentColor = Color(0xFF000000),
                    )
                ) {
                    Text(
                        text = "Login"
                    )
                }
                Button(
                    onClick = {
                        navController.navigate(AuthScreens.RegisterScreen.route)
                    },
                    modifier = modifier
                        .width(331.dp)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF4F4F4F),
                        contentColor = Color(0xFFD2D8E4)
                    )
                ) {
                    Text(
                        text = "Register"
                    )
                }
            }
        }
    }
}