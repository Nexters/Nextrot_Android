package com.nextrot.troter.songs

import android.os.Bundle
import androidx.lifecycle.Observer
import com.nextrot.troter.base.SongsFragment
import com.nextrot.troter.data.Singer
import com.nextrot.troter.songs.list.SongsListAdapter

class SongsOfSingerFragment(val singerId: String): SongsFragment() {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        troterViewModel.getSongsOfSinger(singerId)
        troterViewModel.songsOfSinger.observe(this.viewLifecycleOwner, Observer {
            (binding.list.adapter as SongsListAdapter).submitList(it)
        })
    }
}