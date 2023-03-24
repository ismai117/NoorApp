package com.example.noor.android.main.settings.changePassword.di

import com.example.noor.android.main.settings.changePassword.ChangePasswordViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val changePasswordModule = module {
    viewModel { ChangePasswordViewModel(get(), get(), get()) }
}