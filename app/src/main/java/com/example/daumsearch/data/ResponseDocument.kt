package com.example.daumsearch.data

import java.util.Date

data class ResponseDocument(
    val title: String?,
    val contents: String?,
    val url: String?,
    override val datetime: Date?
) : ResponseWebMedium(){

}