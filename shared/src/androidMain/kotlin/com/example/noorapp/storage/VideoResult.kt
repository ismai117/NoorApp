package com.example.noorapp.storage


sealed class VideoResult<T>(
val item: String? = null,
val message: String? = null
) {
    class Success<T>(item: String? = null) : VideoResult<T>(item)
    class Failure<T>(message: String? = null, item: String? = null) : VideoResult<T>(item, message)
}