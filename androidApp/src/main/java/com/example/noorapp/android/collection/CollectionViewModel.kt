package com.example.noor.android.collection

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noorapp.storage.Playlist
import com.example.noorapp.storage.StorageResult
import com.example.noorapp.storage.StorageService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class CollectionViewModel(
    private val storageService: StorageService
) : ViewModel() {


    private val _playlist = MutableStateFlow<List<Playlist>>(listOf())
    val playlist = _playlist.asStateFlow()

    val isLoading = mutableStateOf(false)

    fun getPlaylist(path: String) {
        viewModelScope.launch(Dispatchers.IO) {
            storageService.getPlaylist(path)
                .collect{
                isLoading.value = true
                delay(2000)
                when(it) {
                    is StorageResult.Success -> {
                        isLoading.value = false
                        _playlist.value = it.items.sortedBy {
                            it.name.substringAfter(" ").trim().removeSuffix(".mp3").toInt()
                        }
                    }
                    is StorageResult.Failure -> {
                        isLoading.value = false
                    }
                }
            }
        }
    }


}