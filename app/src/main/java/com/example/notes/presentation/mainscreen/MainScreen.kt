package com.example.notes.presentation.mainscreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import com.example.notes.util.Page
import com.example.notes.presentation.mainscreen.components.AddNoteOrTaskBottomSheet
import com.example.notes.presentation.mainscreen.components.TopBar
import com.example.notes.presentation.mainscreen.components.notes.NotesList
import com.example.notes.presentation.mainscreen.components.tasks.TasksList

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(navController: NavController) {
    val viewModel: MainScreenViewModel = hiltViewModel()
    val pagerState = rememberPagerState(pageCount = { 2 })
    val isBottomSheetOpen = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TopBar(
                        currentPage = if (pagerState.currentPage == Page.NOTES.index) Page.NOTES else Page.TASKS,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .padding(bottom = 30.dp, end = 20.dp)
                    .size(60.dp),
                shape = CircleShape,
                onClick = { navController.navigate(AppScreens.CreateNoteScreen.route) }
            ) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add note"
                )
            }
        }
    ) {
        HorizontalPager(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding()),
            state = pagerState,
            pageSpacing = 10.dp
        ) { page ->
            when (page) {
                Page.NOTES.index -> NotesList(notes = viewModel.notes)

                Page.TASKS.index -> TasksList(tasks = viewModel.tasks)
            }
        }
    }

    if (isBottomSheetOpen.value) {
        AddNoteOrTaskBottomSheet(
            isOpen = isBottomSheetOpen,
            currentPage = if (pagerState.currentPage == Page.NOTES.index) Page.NOTES else Page.TASKS,
            onAddButtonClick = viewModel::addNote,
            scope = scope,
            sheetState = sheetState
        )
    }
}