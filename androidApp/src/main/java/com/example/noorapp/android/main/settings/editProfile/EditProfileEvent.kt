package com.example.noor.android.main.settings.editProfile

sealed class EditProfileEvent{
    data class NameChanged(val name: String) : EditProfileEvent()
    object Change : EditProfileEvent()
}
