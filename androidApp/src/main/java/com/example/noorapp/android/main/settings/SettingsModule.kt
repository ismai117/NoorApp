package com.example.noor.android.main.settings

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val settingsModule = module {
    viewModel { SettingsViewModel(get()) }
}