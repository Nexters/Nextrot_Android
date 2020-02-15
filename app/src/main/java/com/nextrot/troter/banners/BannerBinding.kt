package com.nextrot.troter.banners

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nextrot.troter.data.Banner

@BindingAdapter("app:src")
fun setSrc(imageView: ImageView, item: Banner) {
    Glide
        .with(imageView.context)
        .load(item.imageUrl)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .into(imageView)
}