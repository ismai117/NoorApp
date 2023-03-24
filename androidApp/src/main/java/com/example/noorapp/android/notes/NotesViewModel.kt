package com.example.noorapp.android.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noorapp.notes.domain.Notes
import com.example.noorapp.notes.domain.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class NotesViewModel(

) : ViewModel() {

    private val notesRepository by inject<NotesRepository>(NotesRepository::class.java)

    private val _notes = MutableStateFlow<List<Notes>>(listOf())
    val notes = _notes.asStateFlow()

    fun searchQuery(note: String) {
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.getNotes().distinctUntilChanged().collect { notes ->
                val filteredNotes = notes.filter {
                    it.lesson.contains(note, ignoreCase = true) || it.note.contains(
                        note,
                        ignoreCase = true
                    )
                }
                _notes.value = filteredNotes
            }
        }
    }

    fun insertNote(notes: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.insertNote(notes)
        }
    }

    fun updateNote(
        notes: Notes
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.updateNote(notes)
        }
    }

    fun deleteNote(notes: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.deleteNote(notes)
        }
    }

}