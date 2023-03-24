package com.example.noor.android.main.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noorapp.auth.AuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class SettingsViewModel(
    private val authService: AuthService
): ViewModel(){

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    fun getName(uid: String){
        viewModelScope.launch(Dispatchers.IO) {
            authService.getUserDetail(uid).collect {
                _name.value = it
            }
        }
    }

}