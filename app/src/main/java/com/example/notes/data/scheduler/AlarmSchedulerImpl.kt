package com.example.notes.data.scheduler

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.notes.data.receivers.AlarmReceiver
import com.example.notes.domain.models.Task
import java.time.ZoneId

class AlarmSchedulerImpl(
    private val context: Context,
    private val alarmManager: AlarmManager
): AlarmScheduler {

    override fun schedule(task: Task) {
        if (task.notificationTime == null) return

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            task.id,
            Intent(context, AlarmReceiver::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            task.notificationTime.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
            pendingIntent
        )
    }

    override fun cancel(taskId: Int) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                taskId,
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun cancel(taskIds: List<Int>) {
        val intent = Intent(context, AlarmReceiver::class.java)

        taskIds.forEach { taskId ->
            alarmManager.cancel(
                PendingIntent.getBroadcast(
                    context,
                    taskId,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            )
        }
    }
}