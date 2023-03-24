package com.example.noorapp.android.authentication.utils


class ValidateUsername {

    fun execute(username: String): ValidateResult {
        if (username.isBlank()) {
            return ValidateResult(
                successful = false,
                errorMessage = "The username can't be blank"
            )
        }
        if (username.length < 6) {
            return ValidateResult(
                successful = false,
                errorMessage = "The username cannot be less than 6 characters"
            )
        }
        return ValidateResult(
            successful = true
        )
    }

}