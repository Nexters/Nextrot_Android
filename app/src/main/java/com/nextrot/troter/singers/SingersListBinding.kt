package com.nextrot.troter.singers

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("app:singers")
fun setItems(list: RecyclerView, items: List<String>) {
    val adapter = list.adapter as SingersListAdapter
    adapter.submitList(items)
}
