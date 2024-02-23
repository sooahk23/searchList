package com.example.daumsearch.data

data class Image(val collection:String, val thumbnail_url:String, val image_url:String,
                 val width: Int, val height: Int, val display_sitename: String, val doc_url: String,
                 override val datetime: String,
                 override var bookmarked: Boolean=false) : WebMedia() {
}