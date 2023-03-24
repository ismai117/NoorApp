package com.example.noor.android.main.settings.editProfile


data class EditProfileState(
    val isLoading: Boolean = false,
    val name: String = "",
    val nameError: String? = null
)
