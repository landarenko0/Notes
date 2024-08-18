package com.example.notes.presentation.mainscreen.components.notes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notes.domain.models.Note
import com.example.notes.util.Month
import java.time.LocalDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteCard(
    note: Note,
    modifier: Modifier,
    selectionEnabled: Boolean,
    isChecked: Boolean,
    onClick: (Long) -> Unit,
    onLongClick: (Long) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .combinedClickable(
                onClick = { onClick(note.id) },
                onLongClick = { onLongClick(note.id) }
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, start = 15.dp, end = 15.dp, bottom = 10.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(3.dp),
                modifier = Modifier.weight(1f)
            ) {
                when {
                    note.title.isNotEmpty() && note.title.isNotBlank() -> {
                        TitleText(title = note.title)
                        DescriptionText(text = note.text)
                    }

                    else -> {
                        TitleText(title = note.text)
                    }
                }

                DateText(date = note.creationDate)
            }

            if (selectionEnabled) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { onClick(note.id) },
                )
            }
        }
    }
}

@Composable
fun TitleText(title: String) {
    Text(
        text = title,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun DescriptionText(text: String) {
    Text(
        text = text,
        maxLines = 1,
        fontSize = 14.sp,
        overflow = TextOverflow.Ellipsis,
        color = Color(0xFF555555)
    )
}

@Composable
fun DateText(date: LocalDate) {
    Text(
        text = "${date.dayOfMonth} ${Month.getMonthText(date.monthValue)} ${date.year}",
        fontSize = 12.sp,
        color = Color(0xFF555555)
    )
}