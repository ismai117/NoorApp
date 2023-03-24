package com.example.noorapp.notes.data

import com.example.noorapp.notes.domain.Notes
import com.example.noorapp.notes.domain.NotesRepository
import io.realm.kotlin.Realm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NotesRepositoryImpl(
    private val realm: Realm
): NotesRepository {

    override fun getNotes(): Flow<List<Notes>> {
        return realm.query(Notes::class).asFlow().map { it.list }
    }

    override suspend fun insertNote(notes: Notes) {
       realm.write {
          this.copyToRealm(notes)
       }
    }

    override suspend fun updateNote(notes: Notes) {
        realm.write {
            val queriedNotes = query(clazz = Notes::class, query = "_id == $0", notes._id).first().find()
            queriedNotes?.note = notes.note
        }
    }

    override suspend fun deleteNote(notes: Notes) {
        realm.write {
            val queriedNotes = query(clazz = Notes::class, query = "_id == $0", notes._id).find().first()
            delete(queriedNotes)
        }
    }

}