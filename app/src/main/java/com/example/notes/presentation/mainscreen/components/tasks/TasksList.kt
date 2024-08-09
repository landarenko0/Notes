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

@Composable
fun TasksList(tasks: List<Task>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            tasks.isNotEmpty() -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier.clip(RoundedCornerShape(16.dp))
                ) {
                    items(tasks) { task ->
                        TaskCard(task = task, onClick = { }, modifier = Modifier)
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
}