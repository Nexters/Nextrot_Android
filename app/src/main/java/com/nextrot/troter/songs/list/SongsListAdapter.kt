package com.nextrot.troter.songs.list

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nextrot.troter.data.Item
import com.nextrot.troter.base.SongsFragment
import com.nextrot.troter.TroterViewModel
import com.nextrot.troter.databinding.SongItemBinding

class SongsListAdapter(private val viewmodel: TroterViewModel, private val fragment: SongsFragment): ListAdapter<Item, SongsListAdapter.ViewHolder>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = SongItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(viewmodel, fragment, item, position+1)
    }

    class ViewHolder constructor(private val binding: SongItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(viewmodel: TroterViewModel, fragment: SongsFragment, item: Item, position: Int) {
            binding.viewmodel = viewmodel
            binding.item = item
            binding.itemRank.text = position.toString()
            binding.fragment = fragment
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                binding.thumb.clipToOutline = true
            }
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