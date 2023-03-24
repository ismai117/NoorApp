package com.example.noorapp.storage


sealed class StorageResult<T>(
    val items:List<Playlist> = emptyList(),
    val message: String? = null
) {
    class Success<T>(items:List<Playlist> = emptyList()) : StorageResult<T>(items)
    class Failure<T>(message: String? = null, items:List<Playlist> = emptyList()) : StorageResult<T>(items, message)
}
