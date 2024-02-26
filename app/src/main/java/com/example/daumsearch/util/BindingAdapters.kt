package com.example.daumsearch.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("app:src")
    fun setImageUrl(imageView: ImageView, url: String?) {
        if (url != null) {
            Glide.with(imageView.context)
                .load(url)
                .into(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter("app:srcCompat")
    fun setImageSource(imageView: ImageView, resource: Drawable) {
        imageView.setImageDrawable(resource)
    }
}