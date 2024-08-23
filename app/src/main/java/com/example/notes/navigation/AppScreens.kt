package com.example.notes.navigation

import kotlinx.serialization.Serializable

object AppScreens {

    @Serializable
    data object MainScreen

    @Serializable
    data class CreateNoteScreen(val noteId: Int?)
}