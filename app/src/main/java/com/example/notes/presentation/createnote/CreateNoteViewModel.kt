package com.example.notes.presentation.createnote

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.domain.models.Note
import com.example.notes.domain.usecases.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CreateNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    val title = mutableStateOf("")
    val description = mutableStateOf("")

    fun createNote() {
        viewModelScope.launch {
            Note(
                title = title.value,
                text = description.value,
                creationDate = LocalDate.now()
            ).also { noteUseCases.addNote(it) }
        }
    }
}