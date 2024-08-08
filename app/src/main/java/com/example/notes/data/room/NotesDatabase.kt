package com.example.notes.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.notes.data.room.dao.NoteDao
import com.example.notes.data.room.dao.TaskDao
import com.example.notes.domain.models.Note
import com.example.notes.domain.models.Task
import com.example.notes.util.LocalDateConverter
import com.example.notes.util.LocalDateTimeConverter

@Database(entities = [Note::class, Task::class], version = 1)
@TypeConverters(LocalDateTimeConverter::class, LocalDateConverter::class)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun notesDao(): NoteDao

    abstract fun tasksDao(): TaskDao

    companion object {
        fun create(context: Context) = Room.databaseBuilder(
            context = context,
            klass = NotesDatabase::class.java,
            name = "notes-db"
        ).build()
    }
}