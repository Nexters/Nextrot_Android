package com.nextrot.troter.songs.list

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nextrot.troter.data.Item


@BindingAdapter("app:src")
fun setSrc(imageView: ImageView, item: Item) {
    Glide
        .with(imageView.context)
        .load(item.snippet.thumbnails.default.url)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .into(imageView)
}

