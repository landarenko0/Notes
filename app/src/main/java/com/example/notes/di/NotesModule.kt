package com.example.notes.di

import android.content.Context
import com.example.notes.data.repositories.notes.NoteRepository
import com.example.notes.data.repositories.notes.NoteRepositoryImpl
import com.example.notes.data.repositories.tasks.TaskRepository
import com.example.notes.data.repositories.tasks.TaskRepositoryImpl
import com.example.notes.data.room.NotesDatabase
import com.example.notes.data.room.dao.NoteDao
import com.example.notes.data.room.dao.TaskDao
import com.example.notes.domain.usecases.AddNoteInteractor
import com.example.notes.domain.usecases.AddTaskInteractor
import com.example.notes.domain.usecases.DeleteNotesInteractor
import com.example.notes.domain.usecases.DeleteTasksInteractor
import com.example.notes.domain.usecases.GetAllNotesInteractor
import com.example.notes.domain.usecases.GetAllTasksInteractor
import com.example.notes.domain.usecases.GetNoteByIdInteractor
import com.example.notes.domain.usecases.NoteUseCases
import com.example.notes.domain.usecases.TaskUseCases
import com.example.notes.domain.usecases.UpdateNoteInteractor
import com.example.notes.domain.usecases.UpdateTaskInteractor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotesModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): NotesDatabase =
        NotesDatabase.create(context)

    @Singleton
    @Provides
    fun provideNotesDao(database: NotesDatabase): NoteDao = database.notesDao()

    @Singleton
    @Provides
    fun provideTasksDao(database: NotesDatabase): TaskDao = database.tasksDao()

    @Singleton
    @Provides
    fun provideNoteRepository(noteDao: NoteDao): NoteRepository = NoteRepositoryImpl(noteDao)

    @Singleton
    @Provides
    fun provideNoteUseCases(noteRepository: NoteRepository): NoteUseCases =
        NoteUseCases(
            getAllNotes = GetAllNotesInteractor(noteRepository),
            getNoteById = GetNoteByIdInteractor(noteRepository),
            addNote = AddNoteInteractor(noteRepository),
            updateNote = UpdateNoteInteractor(noteRepository),
            deleteNotes = DeleteNotesInteractor(noteRepository)
        )

    @Singleton
    @Provides
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository = TaskRepositoryImpl(taskDao)

    @Singleton
    @Provides
    fun provideTasksUseCases(taskRepository: TaskRepository): TaskUseCases =
        TaskUseCases(
            getAllTasks = GetAllTasksInteractor(taskRepository),
            addTask = AddTaskInteractor(taskRepository),
            updateTask = UpdateTaskInteractor(taskRepository),
            deleteTasks = DeleteTasksInteractor(taskRepository)
        )
}