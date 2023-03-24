package com.example.noorapp.android.authentication.utils

import com.example.noorapp.android.authentication.utils.ValidateResult


class ValidateRepeatedPassword  {

    fun execute(password: String, repeatedPassword: String): ValidateResult {
        if(password != repeatedPassword){
            return ValidateResult(
                successful = false,
                errorMessage = "This password doesn't match"
            )
        }
        return ValidateResult(
            successful = true
        )
    }

}