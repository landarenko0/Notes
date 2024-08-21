package com.example.notes.util

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

fun convertMillisToLocalDateTime(dateMillis: Long, hour: Int, minute: Int): LocalDateTime {
    val date = LocalDateTime.ofInstant(Date(dateMillis).toInstant(), ZoneId.systemDefault())
    return LocalDateTime.of(date.year, date.month, date.dayOfMonth, hour, minute)
}