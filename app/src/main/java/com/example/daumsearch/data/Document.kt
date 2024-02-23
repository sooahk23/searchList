package com.example.daumsearch.data

data class Document(val title:String, val contents:String,
                    val url:String, override val datetime: String, override var bookmarked: Boolean = false) :
    WebMedia() {
}