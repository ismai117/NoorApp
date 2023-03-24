package com.example.noorapp.android.authentication.login.presentation


data class LoginState(
    val isLoading: Boolean = false,
    val email: String = "",
    val emailError: String? = "",
    val password: String = "",
    val passwordError: String? = ""
)