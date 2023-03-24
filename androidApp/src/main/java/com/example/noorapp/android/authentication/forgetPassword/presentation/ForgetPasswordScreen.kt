package com.example.noorapp.android.authentication.forgetPassword.presentation


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.noor.android.authentication.forgetPassword.presentation.ForgetPasswordEvent
import com.example.noor.android.authentication.forgetPassword.presentation.ForgetPasswordViewModel
import com.example.noorapp.android.R
import com.example.noorapp.android.main.components.TopBar
import com.example.noorapp.android.navigation.screens.auth.AuthScreens
import com.example.noorapp.authentication.utils.AuthServiceResult
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ForgotPasswordScreen(
    modifier: Modifier = Modifier,
    forgetPasswordViewModel: ForgetPasswordViewModel = koinViewModel(),
    navController: NavController,
) {

    val scaffoldState = rememberScaffoldState()
    val hideSoftwareKeyboardController = LocalSoftwareKeyboardController.current

    val state = forgetPasswordViewModel.state

    LaunchedEffect(forgetPasswordViewModel) {
        forgetPasswordViewModel.forgetPasswordState.collect { result ->
            when (result) {
                is AuthServiceResult.Success -> {
                    navController.navigate(AuthScreens.LoginScreen.route)
                }
                is AuthServiceResult.Failure -> {
                    scaffoldState.snackbarHostState.showSnackbar("There is no user record corresponding to this email.")
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
                    navController.navigate(AuthScreens.LoginScreen.route)
                },
                trailingIconClicked = {

                }
            )
        },
        backgroundColor = Color.Transparent
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
                modifier = modifier
                    .wrapContentSize()
                    .padding(top = 44.dp)
                    .align(Alignment.TopCenter),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Reset Password",
                    color = Color(0xFFFFD300),
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold
                )
                Box(modifier = modifier.width(331.dp)) {
                    Text(
                        text = "Please enter the email address you used to set up your Noor account.",
                        color = Color(0xFFC0C0C0)
                    )
                }
                Spacer(modifier = modifier.padding(4.dp))
                TextField(
                    value = state.email,
                    onValueChange = {
                        forgetPasswordViewModel.onEvent(ForgetPasswordEvent.EmailChanged(it))
                    },
                    label = {
                        Text(
                            text = "Email",
                            color = Color.Black
                        )
                    },
                    modifier = modifier
                        .width(331.dp)
                        .height(56.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(8.dp)),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Email
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        hideSoftwareKeyboardController?.hide()
                    }),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black
                    ),
                    singleLine = true
                )
                if (state.emailError?.isNotBlank() == true) {
                    Box(
                        modifier = modifier
                            .width(331.dp)
                    ) {
                        Text(
                            text = state.emailError,
                            color = Color(0xFFFFD300)
                        )
                    }
                }
                Button(
                    onClick = {
                        forgetPasswordViewModel.onEvent(ForgetPasswordEvent.Submit)
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
                            text = "Submit"
                        )
                        if(state.isLoading) {
                            Spacer(modifier = modifier.padding(4.dp))
                            CircularProgressIndicator(strokeWidth = 2.dp, color = Color.White, modifier = modifier.size(24.dp))
                        }
                    }
                }
            }
        }
    }

}
