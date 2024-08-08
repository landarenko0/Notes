package com.example.notes.util

fun <T> MutableCollection<T>.replaceAllWith(newElements: Collection<T>) {
    this.clear()
    this.addAll(newElements)
}