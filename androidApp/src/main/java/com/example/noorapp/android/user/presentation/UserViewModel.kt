package com.example.noor.android.user.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noor.android.user.domain.UserDetail
import com.example.noor.android.user.domain.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableStateFlow<UserDetail?>(null)
    val user = _user.asStateFlow()

    init {
        getUserDetail()
    }

    private fun getUserDetail(){
        viewModelScope.launch(Dispatchers.IO) {

        }
    }

}