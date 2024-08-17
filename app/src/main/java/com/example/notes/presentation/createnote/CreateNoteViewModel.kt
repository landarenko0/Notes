package com.example.notes.presentation.createnote

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.notes.domain.models.Note
import com.example.notes.domain.usecases.NoteUseCases
import com.example.notes.navigation.AppScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CreateNoteViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val noteUseCases: NoteUseCases
) : ViewModel() {
    var note: Note? = null

    val title = mutableStateOf("")
    val description = mutableStateOf("")

    init {
        val noteId = savedStateHandle.toRoute<AppScreens.CreateNoteScreen>().noteId

        if (noteId != null) {
            viewModelScope.launch {
                note = noteUseCases.getNoteById(noteId)

                title.value = note!!.title
                description.value = note!!.text
            }
        }
    }

    fun saveNote() {
        viewModelScope.launch {
            when {
                note == null -> {
                    Note(
                        title = title.value,
                        text = description.value,
                        creationDate = LocalDate.now()
                    ).also { noteUseCases.addNote(it) }
                }

                else -> {
                    note!!.copy(
                        title = title.value,
                        text = description.value
                    ).also { noteUseCases.updateNote(it) }
                }
            }
        }
    }
}