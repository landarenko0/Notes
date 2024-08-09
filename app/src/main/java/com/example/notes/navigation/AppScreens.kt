package com.example.notes.navigation

sealed class AppScreens(val route: String) {

    data object MainScreen : AppScreens("main")
    data object CreateNoteScreen : AppScreens("createnote")
}