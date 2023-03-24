package com.example.noor.android.main.settings


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.outlined.ArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.noorapp.android.R
import com.example.noorapp.android.main.components.TopBar
import com.example.noor.android.user.presentation.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.compose.koinViewModel


@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = koinViewModel(),
    settingsViewModel: SettingsViewModel = koinViewModel(),
    navController: NavController,
    signOut: () -> Unit,
    editProfile: () -> Unit,
    changePassword: () -> Unit
) {

    val scaffoldState = rememberScaffoldState()

    val user = userViewModel.user.collectAsState().value
    val name = settingsViewModel.name.collectAsState().value

    val pushNotification = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        settingsViewModel.getName(FirebaseAuth.getInstance().uid!!)
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                modifier = modifier,
                leadingIcon = Icons.Filled.ArrowBack,
                title = "",
                trailingIcon = null,
                leadingIconClicked = {
                    navController.popBackStack()
                },
                trailingIconClicked = {

                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFF000000))
        ) {
            Image(
                painter = painterResource(id = R.drawable.texture_bg),
                contentDescription = "",
                modifier = modifier
                    .fillMaxSize()
                    .background(color = Color(0xFFC0C0C0).copy(0.20f)),
                contentScale = ContentScale.Crop,
                alpha = 0.80f
            )
            Column(
                modifier = modifier.fillMaxSize()
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 44.dp, start = 12.dp, end = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = name,
                        color = Color.White
                    )
                }
                Divider(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 12.dp, end = 12.dp),
                    thickness = 1.dp,
                    color = Color(0xFFFFD300)
                )
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 12.dp, end = 12.dp),
                ) {
                    Text(
                        text = "Account Settings",
                        fontSize = 18.sp,
                        color = Color(0xFFADADAD)
                    )
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp)
                            .clickable {
                                editProfile()
                            }
                    ) {
                        Text(
                            text = "Edit profile",
                            color = Color.White,
                            fontSize = 18.sp,
                            modifier = modifier.align(Alignment.CenterStart)
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowRight,
                            contentDescription = "",
                            modifier = modifier.align(Alignment.CenterEnd),
                            tint = Color.White
                        )
                    }
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp)
                            .clickable {
                                changePassword()
                            }
                    ) {
                        Text(
                            text = "Change password",
                            color = Color.White,
                            fontSize = 18.sp,
                            modifier = modifier.align(Alignment.CenterStart)
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowRight,
                            contentDescription = "",
                            modifier = modifier.align(Alignment.CenterEnd),
                            tint = Color.White
                        )
                    }
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp)
                    ) {
                        Text(
                            text = "Push notifications",
                            color = Color.White,
                            fontSize = 18.sp,
                            modifier = modifier.align(Alignment.CenterStart)
                        )
                        Switch(
                            checked = pushNotification.value,
                            onCheckedChange = { pushNotification.value = it },
                            modifier =  modifier.align(Alignment.CenterEnd),
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = if (pushNotification.value) Color(0xFFFFD300) else Color.White,
                                checkedTrackColor = if (pushNotification.value) Color.White else Color(0xFFFFD300),
                                uncheckedThumbColor = if (pushNotification.value) Color(0xFFFFD300) else Color.White,
                                uncheckedTrackColor =  if (pushNotification.value) Color(0xFFFFD300) else Color.White
                            )
                        )
                    }
                    Spacer(modifier = modifier.padding(30.dp))
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp)
                    ) {
                        Text(
                            text = "About us",
                            color = Color.White,
                            fontSize = 18.sp,
                            modifier = modifier.align(Alignment.CenterStart)
                        )
                        Icon(
                            imageVector = Icons.Outlined.ArrowRight,
                            contentDescription = "",
                            modifier = modifier.align(Alignment.CenterEnd),
                            tint = Color.White
                        )
                    }
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp)
                    ) {
                        Text(
                            text = "Donate",
                            color = Color.White,
                            fontSize = 18.sp,
                            modifier = modifier.align(Alignment.CenterStart)
                        )
                        Icon(
                            imageVector = Icons.Outlined.ArrowRight,
                            contentDescription = "",
                            modifier = modifier.align(Alignment.CenterEnd),
                            tint = Color.White
                        )
                    }
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp)
                            .clickable {
                                signOut()
                            }
                    ) {
                        Text(
                            text = "Logout",
                            color = Color.White,
                            fontSize = 18.sp,
                            modifier = modifier.align(Alignment.CenterStart)
                        )
                        Icon(
                            imageVector = Icons.Outlined.ArrowRight,
                            contentDescription = "",
                            modifier = modifier.align(Alignment.CenterEnd),
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }

}

//@Preview(showBackground = true)
//@Composable
//fun SettingsScreenPreview() {
//    SettingsScreen()
//}