package com.example.noorapp.auth


import org.koin.dsl.module

val authModule = module {
    single { AuthService(get(), get()) }
}