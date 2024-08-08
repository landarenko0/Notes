package com.example.notes.util

import androidx.room.TypeConverter
import java.time.LocalDateTime

object LocalDateTimeConverter {

    @TypeConverter
    fun fromTimeStamp(value: String?): LocalDateTime? = value?.let { LocalDateTime.parse(it) }

    @TypeConverter
    fun toTimeStamp(value: LocalDateTime?): String? = value?.toString()
}