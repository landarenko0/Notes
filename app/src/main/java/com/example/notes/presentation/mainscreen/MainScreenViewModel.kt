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
import java.time.LocalDateTime
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

    private val _checkedItems = mutableStateListOf<Int>()
    val checkedItems: List<Int> = _checkedItems

    var selectedTask: Task? = null

    val selectionEnabled = mutableStateOf(false)

    init {
        viewModelScope.launch {
            getAllNotes()
        }

        viewModelScope.launch {
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
            noteUseCases.deleteNotes(_checkedItems)
            clearCheckedItems()
        }
    }

    fun deleteTasks() {
        viewModelScope.launch {
            taskUseCases.deleteTasks(_checkedItems)
            clearCheckedItems()
        }
    }

    fun saveTask(text: String, notificationTime: LocalDateTime?) {
        viewModelScope.launch {
            when {
                selectedTask == null -> {
                    Task(
                        text = text,
                        notificationTime = notificationTime
                    ).also { taskUseCases.addTask(it) }
                }

                else -> {
                    selectedTask!!.copy(
                        text = text,
                        notificationTime = notificationTime
                    ).also { taskUseCases.updateTask(it) }
                }
            }

            selectedTask = null
        }
    }

    fun markTaskCompleted(task: Task, completed: Boolean) {
        viewModelScope.launch {
            taskUseCases.updateTask(
                task.copy(isDone = completed)
            )
        }
    }

    fun checkItem(itemId: Int) = _checkedItems.add(itemId)

    fun removeItemFromChecked(itemId: Int) = _checkedItems.remove(itemId)

    fun clearCheckedItems() = _checkedItems.clear()
}