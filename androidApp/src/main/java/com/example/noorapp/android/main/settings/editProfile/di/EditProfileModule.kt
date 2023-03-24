package com.example.noor.android.main.settings.editProfile.di

import com.example.noor.android.main.settings.editProfile.EditProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val editProfileModule = module {
    viewModel { EditProfileViewModel(get(), get()) }
}