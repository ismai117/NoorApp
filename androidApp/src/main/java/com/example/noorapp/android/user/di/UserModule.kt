package com.example.noor.android.user.di


import com.example.noor.android.user.domain.UserRepository
import com.example.noor.android.user.domain.UserRepositoryImpl
import com.example.noor.android.user.presentation.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val userModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
    viewModel { UserViewModel(get()) }
}