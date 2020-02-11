package com.nextrot.troter.songs

import android.os.Bundle
import androidx.lifecycle.Observer
import com.nextrot.troter.base.SongsFragment
import com.nextrot.troter.songs.list.SongsListAdapter

class PopularSongsFragment: SongsFragment() {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        troterViewModel.getPopular()
//        troterViewModel.popular.observe(this.viewLifecycleOwner, Observer {
//            (binding.list.adapter as SongsListAdapter).submitList(it)
//        })
    }
}