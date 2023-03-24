package com.example.noorapp.android.authentication.register.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noor.android.authentication.register.domain.RegisterRepository
import com.example.noorapp.auth.AuthService
import com.example.noorapp.authentication.utils.AuthServiceResult
import com.example.noorapp.android.authentication.utils.ValidateEmail
import com.example.noorapp.android.authentication.utils.ValidatePassword
import com.example.noorapp.android.authentication.utils.ValidateRepeatedPassword
import com.example.noorapp.android.authentication.utils.ValidateUsername
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class RegisterViewModel(
    private val authRepository: AuthService,
    private val registerRepository: RegisterRepository,
    private val validateUsername: ValidateUsername,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val validateRepeatedPassword: ValidateRepeatedPassword,
) : ViewModel() {

    var state by mutableStateOf(RegistrationState())

    private val _registerState = MutableStateFlow<AuthServiceResult<Unit>?>(null)
    val registerState =_registerState.asStateFlow()

    fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.UsernameChanged -> {
                state = state.copy(username = event.username)
            }
            is RegistrationEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is RegistrationEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is RegistrationEvent.RepeatedPasswordChanged -> {
                state = state.copy(repeatPassword = event.repeatedPassword)
            }
            is RegistrationEvent.Submit -> {
                submit()
            }
        }
    }

    private fun submit() {

        val usernameResult = validateUsername.execute(state.username)
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)
        val repeatedPasswordResult = validateRepeatedPassword.execute(state.password, state.repeatPassword)

        val hasError = listOf(
            usernameResult,
            emailResult,
            passwordResult,
            repeatedPasswordResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                usernameError = usernameResult.errorMessage,
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                repeatPasswordError = repeatedPasswordResult.errorMessage
            )
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            authRepository.signUp(
                state.username,
                state.email,
                state.password
            ).collect {
                state = state.copy(isLoading = true)
                delay(2000)
                _registerState.value = it
                state = state.copy(isLoading = false)
            }
        }
    }


}

