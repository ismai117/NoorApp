package com.example.noor.android.authentication.forgetPassword.presentation


data class ForgetPasswordState(
    val isLoading: Boolean = false,
    val email: String = "",
    val emailError: String? = ""
)