package com.example.notes.data.repositories.tasks

import com.example.notes.domain.models.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun getAllTasks(): Flow<List<Task>>

    suspend fun addTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun deleteTasks(tasks: List<Long>)
}