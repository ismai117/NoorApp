package com.example.noorapp.android.notes.di

import com.example.noorapp.android.notes.NotesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userNotesModule = module{
    viewModel { NotesViewModel() }
}