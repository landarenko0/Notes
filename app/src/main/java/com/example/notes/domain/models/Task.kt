package com.example.notes.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.UUID

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val uuid: UUID = UUID.randomUUID(),
    val text: String,
    val isDone: Boolean = false,
    val notificationTime: LocalDateTime? = null
)
