package com.example.noor.android.main.discover.di

import com.example.noorapp.storage.StorageService
import com.example.noor.android.main.discover.presentation.DiscoverViewModel
import com.google.firebase.storage.FirebaseStorage
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val discoverModule = module {
    single { FirebaseStorage.getInstance() }
    single { StorageService(get()) }
    viewModel { DiscoverViewModel(get()) }
}