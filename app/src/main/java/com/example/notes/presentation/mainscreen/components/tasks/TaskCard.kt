package com.example.notes.presentation.mainscreen.components.tasks

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
import com.example.notes.domain.models.Task
import java.time.LocalDateTime

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskCard(
    task: Task,
    modifier: Modifier,
    selectionEnabled: Boolean,
    isChecked: Boolean,
    onClick: (Task) -> Unit,
    markTaskCompleted: (Task, Boolean) -> Unit,
    onLongClick: (Int) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .combinedClickable(
                onClick = { onClick(task) },
                onLongClick = { onLongClick(task.id) }
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, start = 15.dp, end = 15.dp, bottom = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Checkbox for changing task state
                Checkbox(
                    checked = task.isDone,
                    onCheckedChange = { markTaskCompleted(task, it) },
                    enabled = !selectionEnabled
                )

                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Text(
                        text = task.text,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    task.notificationTime?.let {
                        val dayOfMonth = "" + if (it.dayOfMonth < 10) "0${it.dayOfMonth}" else "${it.dayOfMonth}"
                        val monthValue = "" + if (it.monthValue < 10) "0${it.monthValue}" else "${it.monthValue}"
                        val hour = "" + if (it.hour < 10) "0${it.hour}" else "${it.hour}"
                        val minute = "" + if (it.minute < 10) "0${it.minute}" else "${it.minute}"

                        Text(
                            text = "$dayOfMonth.$monthValue.${it.year} $hour:$minute",
                            fontSize = 12.sp,
                            color = if (LocalDateTime.now() < it) Color(0xFF555555) else Color.Red
                        )
                    }
                }
            }

            // Checkbox for selection
            if (selectionEnabled) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { onClick(task) }
                )
            }
        }
    }
}