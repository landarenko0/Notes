package com.example.notes.domain.usecases

import com.example.notes.data.repositories.tasks.TaskRepository
import com.example.notes.domain.models.Task

class TaskUseCases(
    val getAllTasks: GetAllTasksInteractor,
    val addTask: AddTaskInteractor,
    val updateTask: UpdateTaskInteractor,
    val deleteTasks: DeleteTasksInteractor
)

class GetAllTasksInteractor(private val repository: TaskRepository) {
    suspend operator fun invoke() = repository.getAllTasks()
}

class AddTaskInteractor(private val repository: TaskRepository) {
    suspend operator fun invoke(task: Task) = repository.addTask(task)
}

class UpdateTaskInteractor(private val repository: TaskRepository) {
    suspend operator fun invoke(task: Task) = repository.updateTask(task)
}

class DeleteTasksInteractor(private val repository: TaskRepository) {
    suspend operator fun invoke(tasks: List<Long>) = repository.deleteTasks(tasks)
}