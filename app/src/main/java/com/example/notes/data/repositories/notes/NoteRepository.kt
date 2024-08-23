package com.example.notes.data.repositories.notes

import com.example.notes.domain.models.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun getAllNotes(): Flow<List<Note>>

    suspend fun getNoteById(noteId: Int): Note

    suspend fun addNote(note: Note)

    suspend fun updateNote(note: Note)

    suspend fun deleteNotes(notes: List<Int>)
}