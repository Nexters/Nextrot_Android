package com.nextrot.troter.player.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nextrot.troter.data.Song
import com.nextrot.troter.databinding.PlaylistItemBinding
import com.nextrot.troter.songs.list.TaskDiffCallback

class PlaylistAdapter(private val fragment: PlaylistFragment) : ListAdapter<Song, PlaylistAdapter.ViewHolder>(TaskDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PlaylistItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(fragment, item)
    }

    class ViewHolder constructor(private val binding: PlaylistItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(fragment: PlaylistFragment, item: Song) {
            binding.item = item
            binding.fragment = fragment
            binding.executePendingBindings()
        }
    }
}
