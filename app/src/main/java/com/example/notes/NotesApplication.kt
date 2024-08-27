package com.example.notes

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.example.notes.data.notifications.TaskNotificationService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NotesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            TaskNotificationService.CHANNEL_ID,
            "Напоминание",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Окончание срока задачи"
        }

        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).apply {
            createNotificationChannel(channel)
        }
    }
}