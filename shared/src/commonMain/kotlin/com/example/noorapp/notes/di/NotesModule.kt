package com.example.noorapp.notes.di

import com.example.noorapp.notes.data.NotesRepositoryImpl
import com.example.noorapp.notes.domain.Notes
import com.example.noorapp.notes.domain.NotesRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module

val notesModule = module {
    single {
        val config = RealmConfiguration.Builder(
            schema = setOf(
                Notes::class
            )
        )
            .compactOnLaunch()
            .schemaVersion(1)
            .build()
        Realm.open(config)
    }
    single<NotesRepository> { NotesRepositoryImpl(get()) }
}