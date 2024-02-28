package com.example.daumsearch.db

import androidx.room.TypeConverter
import com.example.daumsearch.data.Document
import com.example.daumsearch.data.Image
import com.example.daumsearch.data.ViewType
import com.example.daumsearch.data.WebMedium
import com.google.gson.Gson
import com.google.gson.JsonParser

class Converters {
    @TypeConverter
    public fun fromWebMediumToJson(value: WebMedium): String = Gson().toJson(value)

    @TypeConverter
    fun fromJsonToWebMedium(value: String): WebMedium {
        val type = JsonParser.parseString(value).asJsonObject.get("type").asInt
        return when (type) {
            ViewType.Document.value -> Gson().fromJson(value, Document::class.java)
            ViewType.Image.value -> Gson().fromJson(value, Image::class.java)
            else -> throw IllegalArgumentException("Unknown type")
        }
    }

    @TypeConverter
    fun fromViewTypeToJson(value: ViewType): String = Gson().toJson(value.value)

    @TypeConverter
    fun fromJsonToViewType(value: String): ViewType  {
        return ViewType.valueOf(value)
    }

}