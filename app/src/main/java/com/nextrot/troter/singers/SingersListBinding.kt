package com.nextrot.troter.singers

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nextrot.troter.data.Singer

@BindingAdapter("app:singers")
fun setItems(list: RecyclerView, items: List<Singer>) {
    val adapter = list.adapter as SingersListAdapter
    adapter.submitList(items)
}
