package com.example.notes.presentation.mainscreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notes.navigation.AppScreens
import com.example.notes.presentation.mainscreen.components.AddNoteOrTaskBottomSheet
import com.example.notes.util.Page
import com.example.notes.presentation.mainscreen.components.TopAppBarTitle
import com.example.notes.presentation.mainscreen.components.notes.NotesList
import com.example.notes.presentation.mainscreen.components.tasks.TasksList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    val viewModel: MainScreenViewModel = hiltViewModel()
    val pagerState = rememberPagerState(pageCount = { 2 })
//    val isBottomSheetOpen = remember { mutableStateOf(false) }
//    val sheetState = rememberModalBottomSheetState()
//    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    if (viewModel.selectionEnabled.value) {
                        IconButton(
                            onClick = {
                                viewModel.deleteNotes()
                                viewModel.selectionEnabled.value = false
                            },
                            enabled = viewModel.selectedNotes.isNotEmpty()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete selected notes",
                            )
                        }
                    }
                },
                title = {
                    TopAppBarTitle(
                        currentPage = Page.getPage(pagerState.currentPage),
                        modifier = Modifier.fillMaxWidth(),
                        noteSelectionEnabled = viewModel.selectionEnabled.value,
                        selectedNotes = viewModel.selectedNotes.size
                    )
                },
                actions = {
                    if (viewModel.selectionEnabled.value) {
                        IconButton(
                            onClick = {
                                viewModel.selectionEnabled.value = false
                                viewModel.clearSelectedNotes()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Disable note selection"
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
        },
        floatingActionButton = {
            if (!viewModel.selectionEnabled.value) {
                FloatingActionButton(
                    modifier = Modifier
                        .padding(bottom = 30.dp, end = 20.dp)
                        .size(60.dp),
                    shape = CircleShape,
                    onClick = { navController.navigate(AppScreens.CreateNoteScreen(null)) }
                ) {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "Add note"
                    )
                }
            }
        }
    ) { paddingValues ->
        HorizontalPager(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding()),
            state = pagerState,
            pageSpacing = 10.dp,
            userScrollEnabled = !viewModel.selectionEnabled.value
        ) { index ->
            when (Page.getPage(index)) {
                Page.NOTES -> NotesList(
                    notes = viewModel.notes,
                    noteSelectionEnabled = viewModel.selectionEnabled.value,
                    checkedNotes = viewModel.selectedNotes,
                    onNoteClick = { noteId ->
                        if (viewModel.selectionEnabled.value) {
                            if (noteId in viewModel.selectedNotes) {
                                viewModel.removeNoteFromSelectedNotes(noteId)
                            } else {
                                viewModel.addNoteToSelectedNotes(noteId)
                            }
                        } else {
                            navController.navigate(AppScreens.CreateNoteScreen(noteId))
                        }
                    },
                    onLongNoteClick = {
                        viewModel.addNoteToSelectedNotes(it)
                        viewModel.selectionEnabled.value = true
                    }
                )

                Page.TASKS -> TasksList(tasks = viewModel.tasks)
            }
        }
    }

//    if (isBottomSheetOpen.value) {
//        AddNoteOrTaskBottomSheet(
//            isOpen = isBottomSheetOpen,
//            currentPage = if (pagerState.currentPage == Page.NOTES.index) Page.NOTES else Page.TASKS,
//            onAddButtonClick = viewModel::addNote,
//            scope = scope,
//            sheetState = sheetState
//        )
//    }
}