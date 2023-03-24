package com.example.noorapp.storage



import kotlinx.coroutines.flow.Flow


interface StorageRepository {
    suspend fun getAllPlaylists(): Flow<StorageResult<Unit>>
    suspend fun getPlaylist(playlist: String): Flow<StorageResult<Unit>>
    suspend fun getVideo(path: String): Flow<VideoResult<Unit>>
}

