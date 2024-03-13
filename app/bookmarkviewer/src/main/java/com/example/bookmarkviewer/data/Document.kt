package com.example.bookmarkviewer.data

data class Document(val title:String, val contents:String,
                    val url:String, override val datetime: String,
                    override var bookmarked: Boolean = false,
                    override val type: ViewType = ViewType.Document) : WebMedium()