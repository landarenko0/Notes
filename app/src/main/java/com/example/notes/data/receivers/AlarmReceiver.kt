package com.example.notes.data.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.notes.data.notifications.NotificationService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var notificationService: NotificationService

    override fun onReceive(context: Context, intent: Intent) {
        val taskTitle = intent.getStringExtra(TASK_TITLE_TAG) ?: return
        val taskId = intent.getIntExtra(TASK_ID_TAG, 0)

        notificationService.notify(taskTitle, taskId)
    }

    companion object {
        const val TASK_TITLE_TAG = "task_title"
        const val TASK_ID_TAG = "task_id"
    }
}