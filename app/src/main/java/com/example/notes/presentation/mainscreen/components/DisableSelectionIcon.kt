package com.example.notes.presentation.mainscreen.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DisableSelectionIcon(
    modifier: Modifier = Modifier,
    selectionEnabled: Boolean,
    onClick: () -> Unit
) {
    if (selectionEnabled) {
        IconButton(
            modifier = modifier,
            onClick = onClick,
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Disable selection"
            )
        }
    }
}