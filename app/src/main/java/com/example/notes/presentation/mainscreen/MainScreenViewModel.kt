package com.example.notes.presentation.mainscreen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.data.scheduler.AlarmScheduler
import com.example.notes.domain.models.Note
import com.example.notes.domain.models.Task
import com.example.notes.domain.usecases.NoteUseCases
import com.example.notes.domain.usecases.TaskUseCases
import com.example.notes.util.replaceAllWith
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    private val taskUseCases: TaskUseCases,
    private val alarmScheduler: AlarmScheduler
) : ViewModel() {

    private val _notes = mutableStateListOf<Note>()
    val notes: List<Note> = _notes

    private val _tasks = mutableStateListOf<Task>()
    val tasks: List<Task> = _tasks

    private val _checkedNotes = mutableStateListOf<Int>()
    val checkedNotes: List<Int> = _checkedNotes

    private val _checkedTasks = mutableStateListOf<UUID>()
    val checkedTasks: List<UUID> = _checkedTasks

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
            noteUseCases.deleteNotes(_checkedNotes)
            clearCheckedItems()
        }
    }

    fun deleteTasks() {
        viewModelScope.launch {
            taskUseCases.deleteTasks(_checkedTasks)
            alarmScheduler.cancel(_checkedTasks)
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
                    ).also {
                        taskUseCases.addTask(it)
                        if (notificationTime != null) alarmScheduler.schedule(it)
                    }
                }

                else -> {
                    selectedTask!!.copy(
                        text = text,
                        notificationTime = notificationTime
                    ).also {
                        taskUseCases.updateTask(it)

                        if (notificationTime != null) alarmScheduler.schedule(it)
                        else alarmScheduler.cancel(it.uuid)
                    }
                }
            }

            selectedTask = null
        }
    }

    fun markTaskCompleted(task: Task, completed: Boolean) {
        viewModelScope.launch {
            taskUseCases.updateTask(
                task.copy(isDone = completed)
                // TODO: Добавить или удалить аларм в зависимости от completed
            )
        }
    }

    fun checkNote(noteId: Int) = _checkedNotes.add(noteId)

    fun checkTask(taskUUID: UUID) = _checkedTasks.add(taskUUID)

    fun removeNoteFromChecked(noteId: Int) = _checkedNotes.remove(noteId)

    fun removeTaskFromChecked(taskUUID: UUID) = _checkedTasks.remove(taskUUID)

    fun clearCheckedItems() {
        _checkedNotes.clear()
        _checkedTasks.clear()
    }
}