package com.example.noorapp.di



import com.example.noorapp.notes.di.notesModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module


fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin{
        appDeclaration()
        module {
            notesModule
        }
    }

fun initKoin() = initKoin {}