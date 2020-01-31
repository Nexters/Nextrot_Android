package com.nextrot.troter.player.lyrics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nextrot.troter.data.Item
import com.nextrot.troter.databinding.LyricsFragmentBinding

class LyricsFragment(private var item: Item) : Fragment() {
    private lateinit var lyricsFragmentBinding: LyricsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lyricsFragmentBinding = LyricsFragmentBinding.inflate(inflater, container, false).apply {
            item = this@LyricsFragment.item
        }
        return lyricsFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lyricsFragmentBinding.lifecycleOwner = viewLifecycleOwner
    }
}