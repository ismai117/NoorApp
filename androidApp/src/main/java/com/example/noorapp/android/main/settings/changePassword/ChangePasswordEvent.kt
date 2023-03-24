package com.example.noor.android.main.settings.changePassword

sealed class ChangePasswordEvent {
    data class OldPasswordChanged(val oldPassword: String) : ChangePasswordEvent()
    data class NewPasswordChanged(val newPassword: String) : ChangePasswordEvent()
    data class ConfirmNewPasswordChanged(val confirmNewPassword: String) : ChangePasswordEvent()
    object Changed : ChangePasswordEvent()
}