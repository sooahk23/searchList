package com.example.daumsearch.data

abstract class WebMedium {
    abstract val datetime: String
    abstract var bookmarked: Boolean
    abstract val type: ViewType
}
