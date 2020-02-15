package com.nextrot.troter.singers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nextrot.troter.songs.SongsViewModel
import com.nextrot.troter.data.Singer
import com.nextrot.troter.databinding.SingerItemBinding

class SingersListAdapter(private val viewmodel: SongsViewModel, private val fragment: SingersFragment): ListAdapter<Singer, SingersListAdapter.ViewHolder>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = SingerItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(viewmodel, fragment, item)
    }

    class ViewHolder constructor(private val binding: SingerItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(viewmodel: SongsViewModel, fragment: SingersFragment, item: Singer) {
            binding.viewmodel = viewmodel
            binding.fragment = fragment
            binding.singer = item
            binding.executePendingBindings()
        }
    }
}

class TaskDiffCallback : DiffUtil.ItemCallback<Singer>() {
    override fun areItemsTheSame(oldItem: Singer, newItem: Singer): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Singer, newItem: Singer): Boolean {
        return oldItem.name == newItem.name
    }
}