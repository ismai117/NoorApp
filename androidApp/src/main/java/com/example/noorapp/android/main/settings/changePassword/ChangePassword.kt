package com.example.noor.android.main.settings.changePassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.noorapp.android.R
import com.example.noorapp.android.main.components.TopBar
import com.example.noorapp.authentication.utils.AuthServiceResult
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChangePasswordScreen(
    modifier: Modifier = Modifier,
    changePasswordViewModel: ChangePasswordViewModel = koinViewModel(),
    navController: NavController
) {

    val scaffoldState = rememberScaffoldState()
    val hideSoftwareKeyboardController = LocalSoftwareKeyboardController.current

    val state = changePasswordViewModel.state

    LaunchedEffect(changePasswordViewModel) {
        changePasswordViewModel.changePasswordState.collect { result ->
            when (result) {
                is AuthServiceResult.Success -> {
                    result.UID?.let { scaffoldState.snackbarHostState.showSnackbar(it) }
                }
                is AuthServiceResult.Failure -> {
                    result.message?.let { scaffoldState.snackbarHostState.showSnackbar(it) }
                }
                else -> {}
            }
        }
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
                trailingIconClicked = {}
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
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 44.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            Text(
                text = "Change Password",
                color = Color(0xFFFFD300),
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = modifier.padding(12.dp))

            TextField(
                value = state.oldPassword,
                onValueChange = {
                    changePasswordViewModel.onEvent(ChangePasswordEvent.OldPasswordChanged(it))
                },
                label = {
                    Text(
                        text = "Old Password",
                        color = Color(0xFFFFD300)
                    )
                },
                modifier = modifier
                    .width(331.dp)
                    .height(56.dp)
                    .background(color = Color.Transparent),
                visualTransformation =  PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(onDone = {
                    hideSoftwareKeyboardController?.hide()
                }),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color(0xFFFFD300),
                    unfocusedIndicatorColor = Color(0xFFFFD300),
                    cursorColor = Color(0xFFFFD300),
                    textColor =   Color(0xFFFFD300)
                ),
                singleLine = true
            )
            if (state.oldPasswordError?.isNotBlank() == true) {
                Box(
                    modifier = modifier.width(331.dp)
                ) {
                    Text(
                        text = state.oldPasswordError,
                        color = Color(0xFFFFD300)
                    )
                }
            }
            TextField(
                value = state.newPassword,
                onValueChange = {
                    changePasswordViewModel.onEvent(ChangePasswordEvent.NewPasswordChanged(it))
                },
                label = {
                    Text(
                        text = "New Password",
                        color = Color(0xFFFFD300)
                    )
                },
                modifier = modifier
                    .width(331.dp)
                    .height(56.dp)
                    .background(color = Color.Transparent),
                visualTransformation =  PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(onDone = {
                    hideSoftwareKeyboardController?.hide()
                }),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color(0xFFFFD300),
                    unfocusedIndicatorColor = Color(0xFFFFD300),
                    cursorColor = Color(0xFFFFD300),
                    textColor =   Color(0xFFFFD300)
                ),
                singleLine = true
            )
            if (state.newPasswordError?.isNotBlank() == true) {
                Box(
                    modifier = modifier.width(331.dp)
                ) {
                    Text(
                        text = state.newPasswordError,
                        color = Color(0xFFFFD300)
                    )
                }
            }
            TextField(
                value = state.confirmNewPassword,
                onValueChange = {
                    changePasswordViewModel.onEvent(ChangePasswordEvent.ConfirmNewPasswordChanged(it))
                },
                label = {
                    Text(
                        text = "Confirm New Password",
                        color = Color(0xFFFFD300)
                    )
                },
                modifier = modifier
                    .width(331.dp)
                    .height(56.dp)
                    .background(color = Color.Transparent),
                visualTransformation =  PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(onDone = {
                    hideSoftwareKeyboardController?.hide()
                }),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color(0xFFFFD300),
                    unfocusedIndicatorColor = Color(0xFFFFD300),
                    cursorColor = Color(0xFFFFD300),
                    textColor =   Color(0xFFFFD300)
                ),
                singleLine = true
            )
            if (state.confirmNewPasswordError?.isNotBlank() == true) {
                Box(
                    modifier = modifier.width(331.dp)
                ) {
                    Text(
                        text = state.confirmNewPasswordError,
                        color = Color(0xFFFFD300)
                    )
                }
            }

            Spacer(modifier = modifier.padding(4.dp))

            Button(
                onClick = {
                    changePasswordViewModel.onEvent(ChangePasswordEvent.Changed)
                },
                modifier = modifier
                    .width(331.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFFFD300),
                    contentColor = Color(0xFF000000),
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Change"
                    )
                    if (state.isLoading) {
                        Spacer(modifier = modifier.padding(4.dp))
                        CircularProgressIndicator(
                            strokeWidth = 2.dp,
                            color = Color.White,
                            modifier = modifier.size(24.dp)
                        )
                    }
                }
            }

        }

    }

}