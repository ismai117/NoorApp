package com.example.noor.android.main.settings.editProfile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noorapp.android.authentication.utils.ValidateUsername
import com.example.noorapp.auth.AuthService
import com.example.noorapp.authentication.utils.AuthServiceResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class EditProfileViewModel
@Inject
constructor(
    private val authService: AuthService,
    private val validateUsername: ValidateUsername
) : ViewModel() {

    var state by mutableStateOf(EditProfileState())

    private val _editProfileState = MutableStateFlow<AuthServiceResult<Unit>?>(null)
    val editProfileState = _editProfileState.asStateFlow()

    fun onEvent(event: EditProfileEvent) {
        when (event) {
            is EditProfileEvent.NameChanged -> {
                state = state.copy(name = event.name)
            }
            is EditProfileEvent.Change -> {
                change()
            }
        }
    }

    private fun change() {

        val nameResult = validateUsername.execute(state.name)

        val hasError = !nameResult.successful

        if (hasError) {
            state = state.copy(nameError = nameResult.errorMessage)
        }

        viewModelScope.launch(Dispatchers.IO) {
            authService.updateProfile(state.name)
                .collect {
                    state = state.copy(isLoading = true)
                    delay(2000)
                    _editProfileState.value = it
                    state = state.copy(isLoading = false)
                }
        }
    }


}