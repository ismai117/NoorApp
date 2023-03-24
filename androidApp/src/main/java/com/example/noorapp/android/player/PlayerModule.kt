package com.example.noorapp.android.player

import androidx.media3.exoplayer.ExoPlayer
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val playerModule = module {
    viewModel { PlayerViewModel(get(),  ExoPlayer.Builder(androidContext()).build()) }
}