package com.example.noorapp.android.authentication.login.di


import com.example.noorapp.android.authentication.login.presentation.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    viewModel { LoginViewModel(get(), get(), get()) }
}