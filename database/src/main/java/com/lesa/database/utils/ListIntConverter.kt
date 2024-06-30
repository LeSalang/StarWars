package com.lesa.database.utils

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class ListIntConverter {
    @TypeConverter
    fun listToString(value: List<Int>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun stringToList(value: String): List<Int> {
        return Json.decodeFromString<List<Int>>(value)
    }
}
