package com.example.core

import android.content.res.Resources
import android.util.TypedValue
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


val Int.dpF
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    )

fun ImageView.loadImage(url: String, thumbUrl: String) {
    val options = RequestOptions()
        .placeholder(R.drawable.ic_launcher)
        .error(R.drawable.ic_launcher)

    val thumb = Glide.with(this).load(thumbUrl)

    Glide.with(this)
        .load(url)
        .apply(options)
        .thumbnail(thumb)
        .into(this)
}

fun ImageView.loadImage(url: String) {
    val options = RequestOptions()
        .placeholder(R.drawable.ic_launcher)
        .error(R.drawable.ic_launcher)

    Glide.with(this)
        .load(url)
        .apply(options)
        .into(this)
}

