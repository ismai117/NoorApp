package com.example.noorapp.android.authentication.register.presentation

sealed class RegistrationEvent {
    data class UsernameChanged(val username: String) : RegistrationEvent()
    data class EmailChanged(val email: String) : RegistrationEvent()
    data class PasswordChanged(val password: String) : RegistrationEvent()
    data class RepeatedPasswordChanged(val repeatedPassword: String) : RegistrationEvent()
    object Submit : RegistrationEvent()
}