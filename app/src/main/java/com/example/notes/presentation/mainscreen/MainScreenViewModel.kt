package com.example.notes.presentation.mainscreen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
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

    private val _selectedNotes = mutableStateListOf<Long>()
    val selectedNotes: List<Long> = _selectedNotes

    private val _selectedTasks = mutableStateListOf<Long>()
    val selectedTasks: List<Long> = _selectedTasks

    val selectionEnabled = mutableStateOf(false)

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

    fun deleteNotes() {
        viewModelScope.launch {
            noteUseCases.deleteNotes(_selectedNotes)
            clearSelectedNotes()
        }
    }

    fun deleteTasks() {
        viewModelScope.launch {
            taskUseCases.deleteTasks(_selectedTasks)
            clearSelectedTasks()
        }
    }

    fun addNoteToSelectedNotes(noteId: Long) = _selectedNotes.add(noteId)

    fun removeNoteFromSelectedNotes(noteId: Long) = _selectedNotes.remove(noteId)

    fun clearSelectedNotes() = _selectedNotes.clear()

    fun addTaskToSelectedTasks(taskId: Long) = _selectedTasks.add(taskId)

    fun removeTaskFromSelectedTasks(taskId: Long) = _selectedTasks.remove(taskId)

    fun clearSelectedTasks() = _selectedTasks.clear()
}