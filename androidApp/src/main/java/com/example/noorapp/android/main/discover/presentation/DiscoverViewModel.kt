package com.example.noor.android.main.discover.presentation

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


class DiscoverViewModel(
    private val storageService: StorageService
) : ViewModel() {


    private val _allPlaylist = MutableStateFlow<List<Playlist>>(listOf())
    val allPlaylist =_allPlaylist.asStateFlow()

    val isLoading = mutableStateOf(false)

    init {
        getAllPlaylist()
    }

   fun getAllPlaylist() {
        viewModelScope.launch(Dispatchers.IO) {
            storageService.getAllPlaylists().collect{
                isLoading.value = true
                delay(2000)
                when(it){
                    is StorageResult.Success -> {
                        isLoading.value = false
                        _allPlaylist.value = it.items
                    }
                    is StorageResult.Failure -> {
                        isLoading.value = false
                    }
                }
            }
        }
    }


}