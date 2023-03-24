package com.example.noorapp.android.player

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.example.noorapp.storage.StorageService
import com.example.noorapp.storage.VideoResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class PlayerViewModel(
    private val storageService: StorageService,
    val player: Player
): ViewModel(){


    private val _video = MutableStateFlow<String?>("")
    val video = _video.asStateFlow()

    val isLoading = mutableStateOf(false)

    init {
        player.prepare()
    }

    fun getVideo(path: String) {
        viewModelScope.launch(Dispatchers.IO) {
            storageService.getVideo(path).collect{
                isLoading.value = true
                delay(2000)
                when(it){
                    is VideoResult.Success -> {
                        isLoading.value = false
                        _video.value = it.item
                    }
                    is VideoResult.Failure -> {
                        isLoading.value = false
                    }
                }
            }
        }
    }

    fun playVideo(url: String){
        player.setMediaItem(MediaItem.fromUri(Uri.parse(url)))
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }

}