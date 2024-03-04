package com.example.daumsearch.db

import android.util.Log
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
        val type = JsonParser.parseString(value).asJsonObject.get("type").asString
        Log.d("Converters", "type: $type")
        return when (type) {
            "Document" -> Gson().fromJson(value, Document::class.java)
            "Image" -> Gson().fromJson(value, Image::class.java)
            else -> throw IllegalArgumentException("Unknown type")
        }
    }

    @TypeConverter
    fun fromViewTypeToJson(value: ViewType): String = Gson().toJson(value.value.toString())

    @TypeConverter
    fun fromJsonToViewType(value: String): ViewType  {
        return ViewType.valueOf(value)
    }

}