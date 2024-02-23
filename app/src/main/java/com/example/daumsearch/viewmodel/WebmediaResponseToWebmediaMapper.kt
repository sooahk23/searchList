package com.example.daumsearch.viewmodel

import com.example.daumsearch.data.Document
import com.example.daumsearch.data.Image
import com.example.daumsearch.data.ResponseDocument
import com.example.daumsearch.data.ResponseImage
import com.example.daumsearch.util.DateTimeUtils.formatIsoDateTime

object WebmediaResponseToWebmediaMapper {
    fun docMap(res: ResponseDocument): Document {
        return Document(
            res.title, res.contents, res.url, formatIsoDateTime(res.datetime.toString())
        )
    }

    fun ImgMap(res: ResponseImage): Image {
        return Image(
            res.collection, res.thumbnail_url, res.image_url,
        res.width, res.height, res.display_sitename, res.doc_url,
            formatIsoDateTime(res.datetime.toString())
        )
    }
}