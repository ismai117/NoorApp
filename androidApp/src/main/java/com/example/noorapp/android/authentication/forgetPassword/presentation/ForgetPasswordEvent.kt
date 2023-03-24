package com.example.noor.android.authentication.forgetPassword.presentation

sealed class ForgetPasswordEvent {
    data class EmailChanged(val email: String) : ForgetPasswordEvent()
    object Submit : ForgetPasswordEvent()
}