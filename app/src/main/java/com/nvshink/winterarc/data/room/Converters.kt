package com.nvshink.winterarc.data.room

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class Converters
{
    @TypeConverter
    fun fromJsonToList(string: String): List<String> {
        return Json.decodeFromString<List<String>>(string)
    }

    @TypeConverter
    fun fromListToJson(list: List<String>): String {
        return Json.encodeToString(list)
    }
}

