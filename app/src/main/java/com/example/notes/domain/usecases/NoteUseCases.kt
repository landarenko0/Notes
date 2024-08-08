package com.example.notes.domain.usecases

import com.example.notes.data.repositories.notes.NoteRepository
import com.example.notes.domain.models.Note

class NoteUseCases(
    val getAllNotes: GetAllNotesInteractor,
    val addNote: AddNoteInteractor,
    val updateNote: UpdateNoteInteractor,
    val deleteNotes: DeleteNotesInteractor
)

class GetAllNotesInteractor(private val repository: NoteRepository) {
    suspend operator fun invoke() = repository.getAllNotes()
}

class AddNoteInteractor(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note) = repository.addNote(note)
}

class UpdateNoteInteractor(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note) = repository.updateNote(note)
}

class DeleteNotesInteractor(private val repository: NoteRepository) {
    suspend operator fun invoke(notes: List<Note>) = repository.deleteNotes(notes)
}