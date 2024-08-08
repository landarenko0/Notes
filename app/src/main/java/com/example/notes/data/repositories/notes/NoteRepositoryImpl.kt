package com.example.notes.data.repositories.notes

import com.example.notes.data.room.dao.NoteDao
import com.example.notes.domain.models.Note
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(private val notesDao: NoteDao) : NoteRepository {

    override suspend fun getAllNotes(): Flow<List<Note>> = notesDao.getAllNotes()

    override suspend fun addNote(note: Note) = notesDao.insertNote(note)

    override suspend fun updateNote(note: Note) = notesDao.updateNote(note)

    override suspend fun deleteNotes(notes: List<Note>) = notesDao.deleteNotes(notes)
}