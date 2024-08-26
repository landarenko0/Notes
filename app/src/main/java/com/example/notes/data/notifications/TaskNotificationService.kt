package com.example.notes.data.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.notes.R
import com.example.notes.presentation.MainActivity

class TaskNotificationService(
    private val context: Context,
    private val notificationManager: NotificationManager
) : NotificationService {

    override fun notify(notificationText: String, notificationId: Int) {
        val pendingIntent = PendingIntent.getActivity(
            context,
            notificationId,
            Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )

        NotificationCompat.Builder(context, CHANNEL_ID).apply {
            setSmallIcon(R.drawable.filled_check_box_24px)
            setContentTitle("Напоминание")
            setContentText(notificationText)
            setContentIntent(pendingIntent)
        }.build().also { notification ->
            notificationManager.notify(notificationId, notification)
        }
    }

    companion object {
        const val CHANNEL_ID = "notes_channel_id"
    }
}