package com.example.notes.presentation.mainscreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.notes.R
import com.example.notes.util.Page
import com.example.notes.presentation.ui.theme.iconFilledTint
import com.example.notes.presentation.ui.theme.iconOutlinedTint

@Composable
fun TopAppBarTitle(
    currentPage: Page,
    modifier: Modifier = Modifier,
    noteSelectionEnabled: Boolean,
    selectedNotes: Int
) {
    val currentPageIsNotes = currentPage == Page.NOTES
    val currentPageIsTasks = currentPage == Page.TASKS

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        if (noteSelectionEnabled) {
            Text(
                text = when (selectedNotes) {
                    0 -> "Выберите объекты"
                    else -> "Выбрано: $selectedNotes"
                }
            )
        } else {
            Icon(
                modifier = Modifier.size(30.dp),
                painter = painterResource(if (currentPageIsNotes) R.drawable.filled_book_24px else R.drawable.outlined_book_24px ),
                contentDescription = "Notes",
                tint = if (currentPageIsNotes) iconFilledTint else iconOutlinedTint
            )

            Spacer(modifier = Modifier.width(70.dp))

            Icon(
                modifier = Modifier.size(30.dp),
                painter = painterResource(if (currentPageIsTasks) R.drawable.filled_check_box_24px else R.drawable.outlined_check_box_24px),
                contentDescription = "Tasks",
                tint = if (currentPageIsTasks) iconFilledTint else iconOutlinedTint
            )
        }
    }
}