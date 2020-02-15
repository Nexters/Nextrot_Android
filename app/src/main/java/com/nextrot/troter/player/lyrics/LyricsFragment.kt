package com.nextrot.troter.player.lyrics

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nextrot.troter.CommonUtil
import com.nextrot.troter.data.Song
import com.nextrot.troter.databinding.LyricsFragmentBinding
import com.nextrot.troter.songs.PlayerViewModel

class LyricsFragment(private val playerViewModel: PlayerViewModel) : Fragment() {
    private lateinit var lyricsFragmentBinding: LyricsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lyricsFragmentBinding = LyricsFragmentBinding.inflate(inflater, container, false).apply {
            viewmodel = playerViewModel
            fragment = this@LyricsFragment
        }
        return lyricsFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lyricsFragmentBinding.lifecycleOwner = viewLifecycleOwner
    }

    fun onClickScaleButton() {
        playerViewModel.changeLyricsViewScale()
        val textSize = if (playerViewModel.lyricsViewScale.value == 1) {
            lyricsFragmentBinding.scaleButton.setTextColor(Color.WHITE)
            28
        } else if (playerViewModel.lyricsViewScale.value == 2) {
            lyricsFragmentBinding.scaleButton.setTextColor(Color.parseColor("#459cff"))
            32
        } else {
            lyricsFragmentBinding.scaleButton.setTextColor(Color.parseColor("#459cff"))
            36
        }
        lyricsFragmentBinding.lyricsText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize.toFloat())
    }
}