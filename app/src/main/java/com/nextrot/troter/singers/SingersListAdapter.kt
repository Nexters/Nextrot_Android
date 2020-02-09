package com.nextrot.troter.singers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nextrot.troter.TroterViewModel
import com.nextrot.troter.databinding.SingerItemBinding

class SingersListAdapter(private val viewmodel: TroterViewModel, private val fragment: SingersFragment): ListAdapter<String, SingersListAdapter.ViewHolder>(TaskDiffCallback()) {

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
        fun bind(viewmodel: TroterViewModel, fragment: SingersFragment, item: String) {
            binding.viewmodel = viewmodel
            binding.fragment = fragment
            binding.singer = item
            binding.executePendingBindings()
        }
    }
}

class TaskDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem.equals(newItem)
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem.equals(newItem)
    }
}