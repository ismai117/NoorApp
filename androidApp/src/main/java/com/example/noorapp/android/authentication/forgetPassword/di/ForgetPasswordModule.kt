package com.example.noorapp.android.authentication.forgetPassword.di

import com.example.noorapp.android.authentication.forgetPassword.domain.ForgetPasswordRepository
import com.example.noorapp.android.authentication.forgetPassword.domain.ForgetPasswordRepositoryImpl
import com.example.noor.android.authentication.forgetPassword.presentation.ForgetPasswordViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val forgetPasswordModule = module {
    single<ForgetPasswordRepository>{ ForgetPasswordRepositoryImpl() }
    viewModel { ForgetPasswordViewModel(get(), get(), get()) }
}