package com.example.notes.util

enum class Page {
    NOTES,
    TASKS;

    companion object {
        fun getPage(index: Int): Page {
            return Page.entries[index]
        }
    }
}