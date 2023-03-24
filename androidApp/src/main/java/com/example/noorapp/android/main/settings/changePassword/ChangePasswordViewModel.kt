package com.example.noor.android.main.settings.changePassword


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noorapp.android.authentication.utils.ValidatePassword
import com.example.noorapp.android.authentication.utils.ValidateRepeatedPassword
import com.example.noorapp.auth.AuthService
import com.example.noorapp.authentication.utils.AuthServiceResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ChangePasswordViewModel(
    private val authService: AuthService,
    private val validatePassword: ValidatePassword,
    private val validateRepeatedPassword: ValidateRepeatedPassword
) : ViewModel() {

    var state by mutableStateOf(ChangePasswordState())

    private val _changePasswordState = MutableStateFlow<AuthServiceResult<Unit>?>(null)
    val changePasswordState = _changePasswordState.asStateFlow()

    fun onEvent(event: ChangePasswordEvent){
        when(event){
            is ChangePasswordEvent.OldPasswordChanged -> {
                state = state.copy(oldPassword = event.oldPassword)
            }
            is ChangePasswordEvent.NewPasswordChanged -> {
                state = state.copy(newPassword = event.newPassword)
            }
            is ChangePasswordEvent.ConfirmNewPasswordChanged -> {
                state = state.copy(confirmNewPassword = event.confirmNewPassword)
            }
            ChangePasswordEvent.Changed -> {
                change()
            }
        }
    }

    private fun change() {

        val oldPasswordResult = validatePassword.execute(state.oldPassword)
        val newPasswordResult = validatePassword.execute(state.newPassword)
        val confirmNewPasswordResult = validatePassword.execute(state.confirmNewPassword)

        val hasError = listOf(
            oldPasswordResult,
            newPasswordResult,
            confirmNewPasswordResult
        ).any { !it.successful }

        if (hasError){
            state = state.copy(
                oldPasswordError = oldPasswordResult.errorMessage,
                newPasswordError = newPasswordResult.errorMessage,
                confirmNewPasswordError = confirmNewPasswordResult.errorMessage
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            authService.resetPassword(state.oldPassword, state.newPassword)
                .collect{
                    state = state.copy(isLoading = true)
                    delay(2000)
                    _changePasswordState.value = it
                    state = state.copy(isLoading = false)
                }
        }
    }

}