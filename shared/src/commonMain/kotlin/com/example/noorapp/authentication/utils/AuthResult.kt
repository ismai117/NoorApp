package com.example.noorapp.authentication.utils


sealed class AuthServiceResult<T>(
    val UID: String? = null,
    val message: String? = null
) {
    class Success<T>(uID: String? = null) : AuthServiceResult<T>(uID)
    class Failure<T>(message: String? = null) : AuthServiceResult<T>(message)
}
