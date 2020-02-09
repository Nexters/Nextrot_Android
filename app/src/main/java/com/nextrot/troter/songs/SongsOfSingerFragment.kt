package com.nextrot.troter.songs

import android.os.Bundle
import androidx.lifecycle.Observer
import com.nextrot.troter.base.SongsFragment
import com.nextrot.troter.songs.list.SongsListAdapter

class SongsOfSingerFragment(val singer: String): SongsFragment() {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        troterViewModel.getSongsOfSinger(singer)
        troterViewModel.songsOfSinger.observe(this.viewLifecycleOwner, Observer {
            (binding.list.adapter as SongsListAdapter).submitList(it)
        })
    }
}