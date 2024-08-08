package com.example.notes.util

import androidx.room.TypeConverter
import java.time.LocalDate

object LocalDateConverter {

    @TypeConverter
    fun fromTimeStamp(value: String): LocalDate = LocalDate.parse(value)

    @TypeConverter
    fun toTimeStamp(value: LocalDate): String = value.toString()
}