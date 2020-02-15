package com.nextrot.troter.songs

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.nextrot.troter.base.SongsFragment
import com.nextrot.troter.songs.list.SongsListAdapter

class RecentPlaylistFragment(private val songsViewModel: SongsViewModel): SongsFragment(songsViewModel) {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        songsViewModel.getSavedPlaylist()
        songsViewModel.savedPlaylist.observe(this.viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                binding.noRecentSongLayout.visibility = View.VISIBLE
            } else {
                binding.noRecentSongLayout.visibility = View.GONE
                (binding.list.adapter as SongsListAdapter).submitList(it)
            }
        })
    }
}