package com.example.notes.domain.usecases

import com.example.notes.data.repositories.notes.NoteRepository
import com.example.notes.domain.models.Note

class NoteUseCases(
    val getAllNotes: GetAllNotesInteractor,
    val getNoteById: GetNoteByIdInteractor,
    val addNote: AddNoteInteractor,
    val updateNote: UpdateNoteInteractor,
    val deleteNotes: DeleteNotesInteractor
)

class GetAllNotesInteractor(private val repository: NoteRepository) {
    suspend operator fun invoke() = repository.getAllNotes()
}

class GetNoteByIdInteractor(private val repository: NoteRepository) {
    suspend operator fun invoke(noteId: Int) = repository.getNoteById(noteId)
}

class AddNoteInteractor(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note) = repository.addNote(note)
}

class UpdateNoteInteractor(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note) = repository.updateNote(note)
}

class DeleteNotesInteractor(private val repository: NoteRepository) {
    suspend operator fun invoke(notes: List<Int>) = repository.deleteNotes(notes)
}