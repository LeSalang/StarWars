package com.lesa.database.utils

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class ListStringConverter {
    @TypeConverter
    fun listToString(value: List<String>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun stringToList(value: String): List<String> {
        return Json.decodeFromString<List<String>>(value)
    }
}
