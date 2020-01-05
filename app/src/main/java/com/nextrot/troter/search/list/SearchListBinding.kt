package com.nextrot.troter.search.list

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nextrot.troter.data.Item

@BindingAdapter("app:items")
fun setItems(list: RecyclerView, items: List<Item>) {
    (list.adapter as SearchListAdapter).submitList(items)
}