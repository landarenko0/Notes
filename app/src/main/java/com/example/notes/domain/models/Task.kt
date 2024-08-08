package com.example.notes.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val text: String,
    val isDone: Boolean,
    val notificationTime: LocalDateTime? = null
)
