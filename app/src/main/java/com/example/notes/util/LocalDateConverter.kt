package com.example.notes.util

import androidx.room.TypeConverter
import java.time.LocalDate

object LocalDateConverter {

    @TypeConverter
    fun toTimeStamp(value: String): LocalDate = LocalDate.parse(value)

    @TypeConverter
    fun toString(value: LocalDate): String = value.toString()
}