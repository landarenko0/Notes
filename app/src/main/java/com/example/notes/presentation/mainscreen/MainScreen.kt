package com.example.notes.presentation.mainscreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notes.navigation.AppScreens
import com.example.notes.presentation.mainscreen.components.DeleteSelectedItemsIcon
import com.example.notes.presentation.mainscreen.components.DisableSelectionIcon
import com.example.notes.presentation.mainscreen.components.MainScreenFloatingButton
import com.example.notes.util.Page
import com.example.notes.presentation.mainscreen.components.TopAppBarTitle
import com.example.notes.presentation.mainscreen.components.notes.NotesList
import com.example.notes.presentation.mainscreen.components.tasks.TasksList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    val viewModel: MainScreenViewModel = hiltViewModel()
    val pagerState = rememberPagerState(pageCount = { 2 })

    val isSaveTaskDialogOpen = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    DisableSelectionIcon(
                        selectionEnabled = viewModel.selectionEnabled.value,
                        onClick = {
                            viewModel.selectionEnabled.value = false
                            viewModel.clearCheckedItems()
                        }
                    )
                },
                title = {
                    TopAppBarTitle(
                        currentPage = Page.getPage(pagerState.currentPage),
                        modifier = Modifier.fillMaxWidth(),
                        selectionEnabled = viewModel.selectionEnabled.value,
                        selectedNotes = when (Page.getPage(pagerState.currentPage)) {
                            Page.NOTES -> viewModel.checkedNotes.size
                            Page.TASKS -> viewModel.checkedTasks.size
                        }
                    )
                },
                actions = {
                    DeleteSelectedItemsIcon(
                        selectionEnabled = viewModel.selectionEnabled.value,
                        enabled = when (Page.getPage(pagerState.currentPage)) {
                            Page.NOTES -> viewModel.checkedNotes.isNotEmpty()
                            Page.TASKS -> viewModel.checkedTasks.isNotEmpty()
                        },
                        onClick = {
                            when (Page.getPage(pagerState.currentPage)) {
                                Page.NOTES -> viewModel.deleteNotes()
                                Page.TASKS -> viewModel.deleteTasks()
                            }

                            viewModel.selectionEnabled.value = false
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
        },
        floatingActionButton = {
            MainScreenFloatingButton(
                modifier = Modifier
                    .padding(bottom = 30.dp, end = 20.dp)
                    .size(60.dp),
                enabled = !viewModel.selectionEnabled.value,
                onClick = {
                    when (Page.getPage(pagerState.currentPage)) {
                        Page.NOTES -> navController.navigate(AppScreens.CreateNoteScreen())
                        Page.TASKS -> isSaveTaskDialogOpen.value = true
                    }
                }
            )
        }
    ) { paddingValues ->
        HorizontalPager(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding() + 10.dp
                ),
            state = pagerState,
            beyondViewportPageCount = 1,
            pageSpacing = 10.dp,
            userScrollEnabled = !viewModel.selectionEnabled.value
        ) { index ->
            when (Page.getPage(index)) {
                Page.NOTES -> NotesList(
                    notes = viewModel.notes,
                    selectionEnabled = viewModel.selectionEnabled.value,
                    checkedNotes = viewModel.checkedNotes,
                    onNoteClick = { noteId ->
                        if (viewModel.selectionEnabled.value) {
                            if (noteId in viewModel.checkedNotes) {
                                viewModel.removeNoteFromChecked(noteId)
                            } else {
                                viewModel.checkNote(noteId)
                            }
                        } else {
                            navController.navigate(AppScreens.CreateNoteScreen(noteId))
                        }
                    },
                    onLongNoteClick = {
                        if (!viewModel.selectionEnabled.value) {
                            viewModel.checkNote(it)
                            viewModel.selectionEnabled.value = true
                        }
                    }
                )

                Page.TASKS -> TasksList(
                    tasks = viewModel.tasks,
                    selectionEnabled = viewModel.selectionEnabled.value,
                    checkedTasks = viewModel.checkedTasks,
                    selectedTask = viewModel.selectedTask,
                    isBottomSheetOpen = isSaveTaskDialogOpen.value,
                    onDialogDismiss = {
                        isSaveTaskDialogOpen.value = false
                        viewModel.selectedTask = null
                    },
                    saveTask = { taskText, notificationTime, hasNotificationPermission ->
                        viewModel.saveTask(taskText, notificationTime, hasNotificationPermission)
                        isSaveTaskDialogOpen.value = false
                    },
                    markTaskCompleted = viewModel::markTaskCompleted,
                    onTaskClick = { task ->
                        if (viewModel.selectionEnabled.value) {
                            if (task.uuid in viewModel.checkedTasks) {
                                viewModel.removeTaskFromChecked(task.uuid)
                            } else {
                                viewModel.checkTask(task.uuid)
                            }
                        } else {
                            viewModel.selectedTask = task
                            isSaveTaskDialogOpen.value = true
                        }
                    },
                    onLongTaskClick = {
                        if (!viewModel.selectionEnabled.value) {
                            viewModel.checkTask(it)
                            viewModel.selectionEnabled.value = true
                        }
                    }
                )
            }
        }
    }
}