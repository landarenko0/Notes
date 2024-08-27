package com.example.notes.data.repositories.tasks

import com.example.notes.data.room.dao.TaskDao
import com.example.notes.domain.models.Task
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class TaskRepositoryImpl(private val tasksDao: TaskDao) : TaskRepository {

    override suspend fun getAllTasks(): Flow<List<Task>> = tasksDao.getAllTasks()

    override suspend fun addTask(task: Task) = tasksDao.insertTask(task)

    override suspend fun updateTask(task: Task) = tasksDao.updateTask(task)

    override suspend fun deleteTasks(tasks: List<UUID>) = tasksDao.deleteTasks(tasks)
}