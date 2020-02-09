package com.nextrot.troter.player

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nextrot.troter.R
import com.nextrot.troter.data.Item
import com.nextrot.troter.databinding.PlayerActivityBinding
import com.nextrot.troter.player.list.PlaylistFragment
import com.nextrot.troter.player.lyrics.LyricsFragment
import com.nextrot.troter.TroterViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import org.koin.android.ext.android.inject
import java.util.ArrayList

class PlayerActivity : AppCompatActivity() {
    private val troterViewModel: TroterViewModel by inject()
    private lateinit var playerActivityBinding: PlayerActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        playerActivityBinding = DataBindingUtil.setContentView(this, R.layout.player_activity)
        playerActivityBinding.lifecycleOwner = this
        lifecycle.addObserver(playerActivityBinding.playerView)
        val items = troterViewModel.selectedItems.value
        playerActivityBinding.items = items

        val sectionsPagerAdapter = PlayerSectionsPagerAdapter(this, supportFragmentManager, arrayListOf(PlaylistFragment(items!!), LyricsFragment(items[0])))
        playerActivityBinding.viewPager.adapter = sectionsPagerAdapter
        playerActivityBinding.listOrLyrics.setupWithViewPager(playerActivityBinding.viewPager)

        loadPlayer(items!!)
    }

    // TODO: "object: ~~~" 이렇게 쓰는거 구림
    private fun loadPlayer(items: ArrayList<Item>) {
        playerActivityBinding.playerView.getYouTubePlayerWhenReady(object: YouTubePlayerCallback {
            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                onLoadPlayer(youTubePlayer, items)
            }
        })
   }

    private fun onLoadPlayer(youTubePlayer: YouTubePlayer, items: ArrayList<Item>) {
        youTubePlayer.addListener(object: YouTubePlayerListener {
            override fun onApiChange(youTubePlayer: YouTubePlayer) { }
            override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) { }
            override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) { }
            override fun onPlaybackQualityChange(youTubePlayer: YouTubePlayer, playbackQuality: PlayerConstants.PlaybackQuality) { }
            override fun onPlaybackRateChange(youTubePlayer: YouTubePlayer, playbackRate: PlayerConstants.PlaybackRate) { }
            override fun onReady(youTubePlayer: YouTubePlayer) { }
            override fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerConstants.PlayerState) { }
            override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) { }
            override fun onVideoLoadedFraction(youTubePlayer: YouTubePlayer, loadedFraction: Float) { }

            override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {
                playerActivityBinding.currentItem = items.find { item -> item.id.videoId == videoId }
            }
        })

        if (items.isNotEmpty()) {
            for (item in items) {
                youTubePlayer.loadOrCueVideo(lifecycle, item.id.videoId, 0f)
            }
        }
    }
}
