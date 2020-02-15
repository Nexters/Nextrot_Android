package com.nextrot.troter.songs

import android.os.Bundle
import androidx.lifecycle.Observer
import com.nextrot.troter.base.SongsFragment
import com.nextrot.troter.songs.list.SongsListAdapter

class SongsOfSingerFragment(private val songsViewModel: SongsViewModel, private val singerId: String): SongsFragment(songsViewModel) {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        songsViewModel.getSongsOfSinger(singerId)
        songsViewModel.currentList.observe(this.viewLifecycleOwner, Observer {
            (binding.list.adapter as SongsListAdapter).submitList(it)
        })
    }
}