package com.nextrot.troter.search.list

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nextrot.troter.data.Item

@BindingAdapter("app:items")
fun setItems(list: RecyclerView, items: List<Item>) {
    (list.adapter as SearchListAdapter).submitList(items)
}

@BindingAdapter("app:src")
fun setSrc(imageView: ImageView, item: Item) {
    Glide
        .with(imageView.context)
        .load(item.snippet.thumbnails.default.url)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .into(imageView)
}