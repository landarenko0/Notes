package com.example.notes.presentation.createnote

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNoteScreen(navController: NavController) {
    val viewModel: CreateNoteViewModel = hiltViewModel()
    val interactionSource = remember { MutableInteractionSource() }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier
                            .padding(start = 20.dp)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null,
                                onClick = { navController.popBackStack() }
                            )
                    )
                },
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Новая заметка")

                        if (viewModel.description.value.isNotEmpty() && viewModel.description.value.isNotBlank()) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Create note",
                                modifier = Modifier.clickable(
                                    interactionSource = interactionSource,
                                    indication = null,
                                    onClick = {
                                        viewModel.saveNote()
                                        navController.popBackStack()
                                    }
                                )
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 20.dp
                ),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            OutlinedTextField(
                value = viewModel.title.value,
                label = { Text(text = "Заголовок") },
                onValueChange = { viewModel.title.value = it },
                textStyle = TextStyle.Default.copy(fontSize = 24.sp),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.description.value,
                label = { Text(text = "Начните ввод") },
                onValueChange = { viewModel.description.value = it },
                textStyle = TextStyle.Default.copy(fontSize = 20.sp),
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}