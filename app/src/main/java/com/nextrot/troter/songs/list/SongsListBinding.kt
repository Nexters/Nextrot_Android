package com.nextrot.troter.songs.list

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nextrot.troter.data.Song


@BindingAdapter("app:src")
fun setSrc(imageView: ImageView, item: Song) {
    Glide
        .with(imageView.context)
        .load("https://img.youtube.com/vi/${item.video}/0.jpg")
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .into(imageView)
}
