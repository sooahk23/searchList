package com.example.daumsearch.data

import java.util.Date

data class ResponseImage(val collection:String, val thumbnail_url:String, val image_url:String,
                         val width: Int, val height: Int, val display_sitename: String, val doc_url: String,
                         override val datetime: Date) : ResponseWebMedium(){
}