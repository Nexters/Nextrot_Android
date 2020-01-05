package com.nextrot.troter.search.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nextrot.troter.data.Item
import com.nextrot.troter.databinding.SearchItemBinding
import com.nextrot.troter.search.SearchFragment
import com.nextrot.troter.search.SearchViewModel

class SearchListAdapter(private val viewmodel: SearchViewModel, private val fragment: SearchFragment): ListAdapter<Item, SearchListAdapter.ViewHolder>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = SearchItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(viewmodel, fragment, item)
    }

    class ViewHolder constructor(private val binding: SearchItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(viewmodel: SearchViewModel, fragment: SearchFragment, item: Item) {
            binding.viewmodel = viewmodel
            binding.item = item
            binding.fragment = fragment
            binding.executePendingBindings()
        }
    }
}

class TaskDiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id.toString() == newItem.id.toString()
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}