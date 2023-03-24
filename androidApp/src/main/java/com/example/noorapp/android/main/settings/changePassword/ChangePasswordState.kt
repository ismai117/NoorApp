package com.example.noor.android.main.settings.changePassword


data class ChangePasswordState(
    val isLoading: Boolean = false,
    val oldPassword: String = "",
    val oldPasswordError: String? = null,
    val newPassword: String = "",
    val newPasswordError: String? = null,
    val confirmNewPassword: String = "",
    val confirmNewPasswordError: String? = null
)