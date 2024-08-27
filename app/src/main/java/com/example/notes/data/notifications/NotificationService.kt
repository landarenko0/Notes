package com.example.notes.data.notifications

interface NotificationService {

    fun notify(notificationText: String, notificationId: Int)
}