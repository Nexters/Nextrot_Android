package com.nextrot.troter.songs.list

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nextrot.troter.base.SongsFragment
import com.nextrot.troter.songs.SongsViewModel
import com.nextrot.troter.data.Song
import com.nextrot.troter.databinding.SongItemBinding

class SongsListAdapter(private val viewmodel: SongsViewModel, private val fragment: SongsFragment): ListAdapter<Song, SongsListAdapter.ViewHolder>(TaskDiffCallback()) {

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
        fun bind(viewmodel: SongsViewModel, fragment: SongsFragment, item: Song, position: Int) {
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

class TaskDiffCallback : DiffUtil.ItemCallback<Song>() {
    override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
        return oldItem.id == newItem.id
    }
}