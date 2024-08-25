package com.example.notes.data.scheduler

import com.example.notes.domain.models.Task

interface AlarmScheduler {

    fun schedule(task: Task)

    fun cancel(taskId: Int)

    fun cancel(taskIds: List<Int>)
}