package com.nextrot.troter.songs.list

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.nextrot.troter.data.Song


@BindingAdapter("app:src")
fun setSrc(imageView: ImageView, item: Song) {
//    Glide
//        .with(imageView.context)
//        .load(item.snippet.thumbnails.default.url)
//        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//        .into(imageView)
}
