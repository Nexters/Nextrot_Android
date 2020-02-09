package com.nextrot.troter.search.list

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nextrot.troter.data.Item
import com.nextrot.troter.player.list.PlaylistAdapter
import com.nextrot.troter.singer.list.SingerListAdapter

@BindingAdapter("app:items")
fun setItems(list: RecyclerView, items: List<Item>) {
    // TODO: 완전 구리다 ㅠ
    when (val adapter = list.adapter) {
        is SearchListAdapter -> {
            adapter.submitList(items)
        }
        is PlaylistAdapter -> {
            adapter.submitList((items))
        }
        is SingerListAdapter -> {
            adapter.submitList(items)
        }
    }
}


@BindingAdapter("app:src")
fun setSrc(imageView: ImageView, item: Item) {
    Glide
        .with(imageView.context)
        .load(item.snippet.thumbnails.default.url)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .into(imageView)
}

