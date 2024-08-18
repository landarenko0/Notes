package com.example.notes.presentation.mainscreen.components.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.notes.domain.models.Note

@Composable
fun NotesList(
    notes: List<Note>,
    selectionEnabled: Boolean,
    checkedNotes: List<Long>,
    onNoteClick: (Long) -> Unit,
    onLongNoteClick: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            notes.isNotEmpty() -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier.clip(RoundedCornerShape(16.dp))
                ) {
                    items(notes) { note ->
                        NoteCard(
                            note = note,
                            modifier = Modifier,
                            selectionEnabled = selectionEnabled,
                            isChecked = note.id in checkedNotes,
                            onClick = onNoteClick,
                            onLongClick = onLongNoteClick
                        )
                    }
                }
            }

            else -> {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(text = "Нет заметок")
                }
            }
        }
    }
}