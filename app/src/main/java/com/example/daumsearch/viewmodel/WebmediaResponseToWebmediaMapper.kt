package com.example.daumsearch.viewmodel

import android.app.Application
import com.example.daumsearch.DaumSearchApp
import com.example.daumsearch.data.Document
import com.example.daumsearch.data.Image
import com.example.daumsearch.data.ResponseDocument
import com.example.daumsearch.data.ResponseImage
import com.example.daumsearch.db.BookmarkDao
import com.example.daumsearch.util.DateTimeUtils.formatIsoDateTime

object WebmediaResponseToWebmediaMapper{

    fun docMap(bookmarked: Boolean, res: ResponseDocument): Document {
        return Document(
            title = res.title ?: "", contents = res.contents ?: "", url = res.url ?: "",
            datetime =  formatIsoDateTime(res.datetime.toString()),
            bookmarked = bookmarked
        )
    }

    fun ImgMap(bookmarked: Boolean, res: ResponseImage): Image {
        return Image(
            res.collection ?: "", res.thumbnail_url ?: "", res.image_url ?: "",
        res.width ?: 0, res.height ?: 0, res.display_sitename ?: "", res.doc_url ?: "",
            datetime = formatIsoDateTime(res.datetime.toString()),
            bookmarked = bookmarked
        )
    }
}