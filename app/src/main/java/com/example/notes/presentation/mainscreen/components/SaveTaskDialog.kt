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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
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
import androidx.compose.ui.window.DialogProperties
import com.example.notes.domain.models.Task
import com.example.notes.presentation.ui.theme.notificationCardBackground
import com.example.notes.util.Month
import com.example.notes.util.convertMillisToLocalDateTime
import java.time.LocalDateTime
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveTaskDialog(
    onSaveButtonClick: (text: String, notificationTime: LocalDateTime?) -> Unit,
    onDismiss: () -> Unit,
    selectedTask: Task?
) {
    var taskText by remember { mutableStateOf(selectedTask?.text ?: "") }
    val focusRequester = remember { FocusRequester() }

    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = selectedTask?.notificationTime?.toInstant(ZoneOffset.UTC)?.toEpochMilli())
    val timePickerState = rememberTimePickerState()

    var userSelectedDate by remember { mutableStateOf(selectedTask?.notificationTime != null) }

    var showDatePickerDialog by remember { mutableStateOf(false) }
    var showTimePickerDialog by remember { mutableStateOf(false) }

    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToLocalDateTime(
            dateMillis = it,
            hour = timePickerState.hour,
            minute = timePickerState.minute
        )
    } ?: LocalDateTime.now()

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (userSelectedDate) {
                        DateTimePickerCard(
                            dateTime = selectedDate,
                            onDayClick = { showDatePickerDialog = true },
                            onTimeClick = { showTimePickerDialog = true },
                            clearSelectedDay = { userSelectedDate = false }
                        )
                    } else {
                        NotificationCard(onClick = { showDatePickerDialog = true })
                    }

                    Button(
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 24.dp),
                        onClick = {
                            onSaveButtonClick(
                                taskText,
                                if (userSelectedDate) selectedDate else null
                            )
                        },
                        enabled = taskText.isNotEmpty()
                    ) { Text(text = "Готово", fontSize = 14.sp) }
                }
            }
        }
    }

    if (showDatePickerDialog) {
        DatePickerDialog(
            onDismissRequest = { showDatePickerDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDatePickerDialog = false
                        userSelectedDate = true
                    },
                    content = { Text(text = "Сохранить") },
                    enabled = datePickerState.selectedDateMillis != null
                )
            },
            dismissButton = {
                TextButton(
                    onClick = { showDatePickerDialog = false },
                    content = { Text(text = "Отмена") }
                )
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (showTimePickerDialog) {
        TimePickerDialog(
            onDismiss = { showTimePickerDialog = false },
            onConfirm = { showTimePickerDialog = false }
        ) {
            TimePicker(state = timePickerState)
        }
    }
}

@Composable
fun NotificationCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors().copy(containerColor = notificationCardBackground),
        shape = RoundedCornerShape(8.dp),
        onClick = onClick
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

@Composable
fun DateTimePickerCard(
    modifier: Modifier = Modifier,
    dateTime: LocalDateTime,
    onDayClick: () -> Unit,
    onTimeClick: () -> Unit,
    clearSelectedDay: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        Button(
            shape = RoundedCornerShape(
                topStart = 8.dp,
                bottomStart = 8.dp,
                topEnd = 0.dp,
                bottomEnd = 0.dp
            ),
            contentPadding = PaddingValues(horizontal = 3.dp),
            onClick = onDayClick
        ) {
            Text(
                text = "${dateTime.dayOfMonth} ${Month.getMonthText(dateTime.monthValue, true)} ${dateTime.year}"
            )
        }

        Button(
            shape = RoundedCornerShape(0.dp),
            contentPadding = PaddingValues(horizontal = 3.dp),
            onClick = onTimeClick
        ) {
            Text(
                text = "" + (if (dateTime.hour < 10) "0${dateTime.hour}:" else "${dateTime.hour}:") + (if (dateTime.minute < 10) "0${dateTime.minute}" else "${dateTime.minute}")
            )
        }

        Button(
            shape = RoundedCornerShape(
                topStart = 0.dp,
                bottomStart = 0.dp,
                topEnd = 8.dp,
                bottomEnd = 8.dp
            ),
            contentPadding = PaddingValues(horizontal = 3.dp),
            onClick = clearSelectedDay
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Clear selected day"
            )
        }
    }
}

@Composable
fun TimePickerDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                content = { Text(text = "Сохранить") }
            )
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                content = { Text(text = "Отменить") }
            )
        },
        text = content
    )
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

@Preview
@Composable
private fun DateTimePickerCardTest() {
    DateTimePickerCard(
        dateTime = LocalDateTime.now(),
        onDayClick = { },
        onTimeClick = { },
        clearSelectedDay = { }
    )
}