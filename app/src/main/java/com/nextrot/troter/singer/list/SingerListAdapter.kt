package com.nextrot.troter.singer.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nextrot.troter.data.Item
import com.nextrot.troter.databinding.SingerItemBinding
import com.nextrot.troter.search.list.TaskDiffCallback
import com.nextrot.troter.singer.SingerFragment
import com.nextrot.troter.singer.SingerViewModel

internal class SingerListAdapter(
    private val viewmodel: SingerViewModel,
    private val fragment: SingerFragment
) :ListAdapter<Item, SingerListAdapter.ViewHolder>(TaskDiffCallback()){

    class ViewHolder constructor(private val binding: SingerItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(viewmodel: SingerViewModel, fragment: SingerFragment, item: Item){
            binding.viewmodel = viewmodel
            binding.item = item
            binding.fragment = fragment
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = SingerItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(viewmodel, fragment, item)
    }
}