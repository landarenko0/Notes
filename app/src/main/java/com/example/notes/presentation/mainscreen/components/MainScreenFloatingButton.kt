package com.example.notes.presentation.mainscreen.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainScreenFloatingButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit
) {
    if (enabled) {
        FloatingActionButton(
            modifier = modifier,
            shape = CircleShape,
            onClick = onClick
        ) {
            Icon(
                modifier = Modifier.size(40.dp),
                imageVector = Icons.Rounded.Add,
                contentDescription = "Create note or task"
            )
        }
    }
}