package com.example.notes.data.scheduler

import com.example.notes.domain.models.Task
import java.util.UUID

interface AlarmScheduler {

    fun schedule(task: Task)

    fun cancel(taskUuid: UUID)

    fun cancel(taskUuids: List<UUID>)
}