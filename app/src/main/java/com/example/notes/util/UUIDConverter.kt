package com.example.notes.util

import androidx.room.TypeConverter
import java.util.UUID

object UUIDConverter {

    @TypeConverter
    fun toUUID(value: String): UUID = UUID.fromString(value)

    @TypeConverter
    fun toString(uuid: UUID): String = uuid.toString()
}