package com.example.notes.presentation.mainscreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.domain.models.Note
import com.example.notes.domain.models.Task
import com.example.notes.domain.usecases.NoteUseCases
import com.example.notes.domain.usecases.TaskUseCases
import com.example.notes.util.replaceAllWith
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    private val taskUseCases: TaskUseCases
) : ViewModel() {

    private val _notes = mutableStateListOf<Note>()
    val notes: List<Note> = _notes

    private val _tasks = mutableStateListOf<Task>()
    val tasks: List<Task> = _tasks

    init {
        viewModelScope.launch {
            getAllNotes()
            getAllTasks()
        }
    }

    private suspend fun getAllNotes() {
        noteUseCases.getAllNotes().collect {
            _notes.replaceAllWith(it)
        }
    }

    private suspend fun getAllTasks() {
        taskUseCases.getAllTasks().collect {
            _tasks.replaceAllWith(it)
        }
    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            noteUseCases.addNote(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            noteUseCases.updateNote(note)
        }
    }

    fun deleteNotes(notes: List<Note>) {
        viewModelScope.launch {
            noteUseCases.deleteNotes(notes)
        }
    }
}