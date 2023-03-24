package com.example.noorapp.android.authentication.register.presentation


data class RegistrationState(
    val isLoading: Boolean = false,
    val username: String = "",
    val usernameError: String? = "",
    val email: String = "",
    val emailError: String? = "",
    val password: String = "",
    val passwordError: String?= "",
    val repeatPassword: String = "",
    val repeatPasswordError: String? = ""
)