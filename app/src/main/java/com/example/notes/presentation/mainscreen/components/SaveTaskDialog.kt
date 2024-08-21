package com.example.notes.presentation.mainscreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.notes.domain.models.Task
import com.example.notes.presentation.ui.theme.notificationCardBackground
import java.time.LocalDateTime

@Composable
fun SaveTaskDialog(
    onSaveButtonClick: (text: String, notificationTime: LocalDateTime?) -> Unit,
    onDismiss: () -> Unit,
    selectedTask: Task?
) {
    var taskText by remember { mutableStateOf(selectedTask?.text ?: "") }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Dialog(onDismissRequest = onDismiss) {
        Card {
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = selectedTask?.isDone ?: false,
                        onCheckedChange = null,
                        enabled = false
                    )

                    TextField(
                        value = taskText,
                        onValueChange = { taskText = it },
                        modifier = Modifier.focusRequester(focusRequester)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    NotificationCard()

                    Button(
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 24.dp),
                        onClick = { onSaveButtonClick(taskText, null) },
                        enabled = taskText.isNotEmpty()
                    ) { Text(text = "Готово", fontSize = 14.sp) }
                }
            }
        }
    }
}

@Composable
fun NotificationCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors().copy(containerColor = notificationCardBackground),
        shape = RoundedCornerShape(8.dp),
        onClick = { }
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Outlined.Notifications,
                contentDescription = "Notification"
            )

            Text(
                text = "Напоминание",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
private fun SaveTaskDialogTest() {
    SaveTaskDialog(
        onSaveButtonClick = { _, _ -> },
        onDismiss = { },
        selectedTask = null
    )
}