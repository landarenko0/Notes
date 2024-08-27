package com.example.notes.presentation.mainscreen.components.tasks

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
import com.example.notes.domain.models.Task
import com.example.notes.presentation.mainscreen.components.SaveTaskDialog
import java.time.LocalDateTime
import java.util.UUID

@Composable
fun TasksList(
    tasks: List<Task>,
    selectionEnabled: Boolean,
    checkedTasks: List<UUID>,
    selectedTask: Task?,
    isBottomSheetOpen: Boolean,
    onDialogDismiss: () -> Unit,
    saveTask: (text: String, notificationTime: LocalDateTime?) -> Unit,
    markTaskCompleted: (Task, Boolean) -> Unit,
    onTaskClick: (Task) -> Unit,
    onLongTaskClick: (UUID) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            tasks.isNotEmpty() -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier.clip(RoundedCornerShape(16.dp))
                ) {
                    items(tasks) { task ->
                        TaskCard(
                            task = task,
                            modifier = Modifier,
                            selectionEnabled = selectionEnabled,
                            isChecked = task.uuid in checkedTasks,
                            onClick = onTaskClick,
                            markTaskCompleted = markTaskCompleted,
                            onLongClick = onLongTaskClick
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
                    Text(text = "Нет заданий")
                }
            }
        }
    }

    if (isBottomSheetOpen) {
        SaveTaskDialog(
            onSaveButtonClick = saveTask,
            onDismiss = onDialogDismiss,
            selectedTask = selectedTask
        )
    }
}