package com.nvshink.winterarc.data.room

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class Converters
{
    @TypeConverter
    fun fromJsonToListStrings(string: String): List<String> {
        return Json.decodeFromString<List<String>>(string)
    }

    @TypeConverter
    fun fromListStringsToJson(list: List<String>): String {
        return Json.encodeToString(list)
    }

//    @TypeConverter
//    fun fromJsonToListUris(string: String): List<Uri> {
//        return Json.decodeFromString<List<Uri>>(string)
//    }
//
//    @TypeConverter
//    fun fromListUrisToJson(list: List<Uri>): String {
//        return Json.encodeToString(list)
//    }
//
//    @TypeConverter
//    fun fromJsonToListUris(string: String): List<Uri> {
//        val imagesUrisStrings: List<String> = Json.decodeFromString<List<String>>(string)
//        val imagesUris: MutableList<Uri> = mutableListOf()
//        imagesUrisStrings.forEach { imageUrisString ->
//            imagesUris.add(Uri.parse(imageUrisString))
//        }
//        return imagesUris
//    }
//
//    @TypeConverter
//    fun fromListUrisToJson(list: List<Uri>): String {
//        val imagesUrisStrings: MutableList<String> = mutableListOf()
//        list.forEach { imageUris ->
//            imagesUrisStrings.add(imageUris.toString())
//        }
//        return Json.encodeToString(imagesUrisStrings)
//    }
//
//    @TypeConverter
//    fun fromJsonToListUris(value: String): List<Uri> {
//        val type = object : TypeToken<List<String>>() {}.type
//        val uriStrings = Gson().fromJson<List<String>>(value, type)
//        return uriStrings.map { Uri.parse(it) }
//    }
//
//    @TypeConverter
//    fun fromListUrisToJson(uris: List<Uri>): String {
//        val uriStrings = uris.map { it.toString() }
//        return Gson().toJson(uriStrings)
//    }
}

