package com.example.notes.presentation.mainscreen.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DeleteSelectedItemsIcon(
    modifier: Modifier = Modifier,
    selectionEnabled: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    if (selectionEnabled) {
        IconButton(
            modifier = modifier,
            onClick = onClick,
            enabled = enabled
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete selected items",
            )
        }
    }
}