package com.example.notes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notes.presentation.createnote.CreateNoteScreen
import com.example.notes.presentation.mainscreen.MainScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = AppScreens.MainScreen.route
    ) {
        composable(route = AppScreens.MainScreen.route) {
            MainScreen(navController)
        }

        composable(route = AppScreens.CreateNoteScreen.route) {
            CreateNoteScreen()
        }
    }
}