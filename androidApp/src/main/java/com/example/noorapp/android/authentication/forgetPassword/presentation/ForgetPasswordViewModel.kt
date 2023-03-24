package com.example.noor.android.authentication.forgetPassword.presentation


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noorapp.android.authentication.forgetPassword.domain.ForgetPasswordRepository
import com.example.noorapp.android.authentication.utils.ValidateEmail
import com.example.noorapp.auth.AuthService
import com.example.noorapp.authentication.utils.AuthServiceResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ForgetPasswordViewModel(
    private val authRepository: AuthService,
    private val forgetPasswordRepository: ForgetPasswordRepository,
    private val validateEmail: ValidateEmail,
) : ViewModel() {

    var state by mutableStateOf(ForgetPasswordState())

    private val _forgetPasswordState = MutableStateFlow<AuthServiceResult<Unit>?>(null)
    val forgetPasswordState = _forgetPasswordState.asStateFlow()

    fun onEvent(event: ForgetPasswordEvent){
        when(event){
            is ForgetPasswordEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is ForgetPasswordEvent.Submit -> {
                submit()
            }
        }
    }

    private fun submit() {

        val emailResult = validateEmail.execute(state.email)

        if (!emailResult.successful) {
            state = state.copy(
                emailError = emailResult.errorMessage
            )
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            authRepository.forgetPassword(
                state.email
            ).collect {
                state = state.copy(isLoading = true)
                delay(2000)
                _forgetPasswordState.value = it
                state = state.copy(isLoading = false)
            }
        }
    }

}