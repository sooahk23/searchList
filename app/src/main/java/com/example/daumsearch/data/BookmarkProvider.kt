package com.example.daumsearch.data

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import com.example.daumsearch.DaumSearchApp
import com.example.daumsearch.db.BookmarkDao
import com.google.gson.Gson

class BookmarkProvider : ContentProvider() {

    private lateinit var bookmarkDao: BookmarkDao

    companion object {
        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI("com.example.daumsearch.bookmarkprovider", "bookmark", 1) }
    }

    override fun onCreate(): Boolean {
        bookmarkDao = context?.let { ctx ->
            DaumSearchApp.getDatabase(ctx).bookmarkDao()
        } ?: throw IllegalStateException("Failed to retrieve context.")
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val cursor: Cursor
        cursor = when (uriMatcher.match(uri)) {
            1 ->{
                val bookmarks = bookmarkDao.getAllBookmarks()
                val matrixCursor = MatrixCursor(arrayOf("id", "content")) // replace with your actual columns
                bookmarks.forEach { bookmark ->
                    matrixCursor.addRow(arrayOf<Any>(bookmark.id,  Gson().toJson(bookmark.content))) // replace with your actual data
                }
                matrixCursor
            }
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }
        return cursor
    }

    override fun getType(uri: Uri): String? {
        throw UnsupportedOperationException("Insert operation is not supported")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException("Insert operation is not supported")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        throw UnsupportedOperationException("Insert operation is not supported")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        throw UnsupportedOperationException("Insert operation is not supported")
    }
}