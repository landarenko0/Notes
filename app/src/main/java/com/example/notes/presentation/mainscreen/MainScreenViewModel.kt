package com.example.notes.presentation.mainscreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.data.room.dao.TaskDao
import com.example.notes.domain.models.Note
import com.example.notes.domain.models.Task
import com.example.notes.domain.usecases.NoteUseCases
import com.example.notes.util.replaceAllWith
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val notesUseCases: NoteUseCases,
    private val tasksDao: TaskDao
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
        notesUseCases.getAllNotes().collect {
            _notes.replaceAllWith(it)
        }
    }

    private suspend fun getAllTasks() {
        tasksDao.getAllTasks().collect {
            _tasks.replaceAllWith(it)
        }
    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            notesUseCases.addNote(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            notesUseCases.updateNote(note)
        }
    }

    fun deleteNotes(notes: List<Note>) {
        viewModelScope.launch {
            notesUseCases.deleteNotes(notes)
        }
    }
}