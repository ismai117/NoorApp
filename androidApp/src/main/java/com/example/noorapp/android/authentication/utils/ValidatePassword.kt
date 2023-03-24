package com.example.noorapp.android.authentication.utils


class ValidatePassword {

    fun execute(password: String): ValidateResult {
        if (password.length < 8){
            return ValidateResult(
                successful = false,
                errorMessage = "The password needs to consist of at least 8 characters"
            )
        }
        val containsLetterAndNumber = password.any { it.isDigit() } && password.any { it.isLetter() }
        if(!containsLetterAndNumber){
            return ValidateResult(
                successful = false,
                errorMessage = "The password needs to contain at least one letter and digit"
            )
        }
        return ValidateResult(
            successful = true
        )
    }

}