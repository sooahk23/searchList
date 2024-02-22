package com.example.daumsearch.data

data class Document(val title:String, val contents:String,
                    val url:String, val datetime: String, var bookmarked: Boolean = false) {
}