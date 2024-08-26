package com.example.notes.data.scheduler

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.notes.data.receivers.AlarmReceiver
import com.example.notes.domain.models.Task
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID

class AlarmSchedulerImpl(
    private val context: Context,
    private val alarmManager: AlarmManager
) : AlarmScheduler {

    override fun schedule(task: Task) {
        if (task.notificationTime == null || LocalDateTime.now() > task.notificationTime) return

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            task.uuid.hashCode(),
            Intent(context, AlarmReceiver::class.java).apply {
                putExtra(AlarmReceiver.TASK_TITLE_TAG, task.text)
                putExtra(AlarmReceiver.TASK_ID_TAG, task.uuid.hashCode())
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            task.notificationTime.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
            pendingIntent
        )
    }

    override fun cancel(taskUuid: UUID) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                taskUuid.hashCode(),
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun cancel(taskUuids: List<UUID>) {
        val intent = Intent(context, AlarmReceiver::class.java)

        taskUuids.forEach { taskUuid ->
            alarmManager.cancel(
                PendingIntent.getBroadcast(
                    context,
                    taskUuid.hashCode(),
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            )
        }
    }
}