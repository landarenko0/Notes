package com.example.notes.data.repositories.notes

import com.example.notes.data.room.dao.NoteDao
import com.example.notes.domain.models.Note
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(private val noteDao: NoteDao) : NoteRepository {

    override suspend fun getAllNotes(): Flow<List<Note>> = noteDao.getAllNotes()

    override suspend fun getNoteById(noteId: Long): Note = noteDao.getNoteById(noteId)

    override suspend fun addNote(note: Note) = noteDao.insertNote(note)

    override suspend fun updateNote(note: Note) = noteDao.updateNote(note)

    override suspend fun deleteNotes(notes: List<Long>) = noteDao.deleteNotes(notes)
}