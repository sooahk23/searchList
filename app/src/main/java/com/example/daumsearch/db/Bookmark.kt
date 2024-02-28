package com.example.daumsearch.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.daumsearch.data.WebMedium

@Entity(tableName = "bookmarks")
data class Bookmark (@PrimaryKey(autoGenerate = true) val id: Int = 0, val content: WebMedium)