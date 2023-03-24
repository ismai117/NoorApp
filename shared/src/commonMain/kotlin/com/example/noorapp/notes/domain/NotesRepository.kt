package com.example.noorapp.notes.domain

import com.example.noorapp.notes.domain.Notes
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    fun getNotes(): Flow<List<Notes>>
    suspend fun insertNote(notes: Notes)
    suspend fun updateNote(notes: Notes)
    suspend fun deleteNote(notes: Notes)
}