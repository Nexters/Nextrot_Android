package com.nextrot.troter.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.nextrot.troter.songs.SongsViewModel
import com.nextrot.troter.data.Song
import com.nextrot.troter.databinding.SongsFragmentBinding
import com.nextrot.troter.player.PlayerActivity
import com.nextrot.troter.songs.list.SongsListAdapter

abstract class SongsFragment(private val songsViewModel: SongsViewModel) : Fragment() {
    protected lateinit var binding: SongsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SongsFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this.viewLifecycleOwner
        songsViewModel.selectedItems.observe(this.viewLifecycleOwner, Observer {
            // 옵저빙 해서 notify 함으로써 얻는 성능 저하는 감수해야함... 편할려고 ㅠ
            binding.list.adapter?.notifyDataSetChanged()
        })

        binding.list.adapter = SongsListAdapter(songsViewModel, this)
    }

    fun onClickItem(item: Song) {
        songsViewModel.toggleSelectedItem(item)
    }

    fun onClickPlay(item: Song) {
        songsViewModel.clearSelectedItem()
        val intent = Intent(context, PlayerActivity::class.java).apply {
            putParcelableArrayListExtra(PlayerActivity.BUNDLE_SONGS, arrayListOf(item))
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        startActivity(intent)
    }
}