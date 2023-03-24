package com.example.noorapp.storage

import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class StorageService(
    private val storage: FirebaseStorage
) : StorageRepository {


    override suspend fun getAllPlaylists(): Flow<StorageResult<Unit>> = callbackFlow {
        storage.reference.child("playlists")
            .listAll()
            .addOnSuccessListener { listResult ->
                val playlist = listResult.prefixes.map {
                    Playlist(
                        name = it.name,
                        path = it.path
                    )
                }
                Log.d("folders", "${playlist.get(0)}")
                trySend(StorageResult.Success(playlist))
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                trySend(StorageResult.Failure(e.message))
            }
        awaitClose {
            cancel()
        }
    }

    override suspend fun getPlaylist(playlist: String): Flow<StorageResult<Unit>> = callbackFlow {
        storage.getReference(playlist).listAll()
            .addOnSuccessListener { listResult ->
                val videos = listResult.items.map {
                    Playlist(
                        name = it.name,
                        path = it.path
                    )
                }
                trySend(StorageResult.Success(videos))
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                Log.d("nrrList", "playlists/${e.message}/")
                trySend(StorageResult.Failure(e.message))
            }
        awaitClose {
            cancel()
        }
    }

    override suspend fun getVideo(path: String): Flow<VideoResult<Unit>> = callbackFlow{
        storage.getReference(path).downloadUrl
            .addOnSuccessListener {
                trySend(VideoResult.Success(it.toString()))
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                trySend(VideoResult.Failure(e.message))
            }
        awaitClose{
            close()
        }
    }


}