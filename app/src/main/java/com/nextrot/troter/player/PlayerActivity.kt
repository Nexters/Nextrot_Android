package com.nextrot.troter.player

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.nextrot.troter.R
import com.nextrot.troter.TROTER_PREF
import com.nextrot.troter.TroterViewModel
import com.nextrot.troter.data.Song
import com.nextrot.troter.databinding.PlayerActivityBinding
import com.nextrot.troter.player.list.PlaylistFragment
import com.nextrot.troter.player.lyrics.LyricsFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class PlayerActivity : AppCompatActivity() {
    private lateinit var songs: ArrayList<Song>
    private lateinit var playerActivityBinding: PlayerActivityBinding
    private lateinit var player: YouTubePlayer
    private lateinit var playlistFragment: PlaylistFragment
    private lateinit var lyricsFragment: LyricsFragment
    private val troterViewModel: TroterViewModel by viewModel()
    private var currentItem: Song? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        playerActivityBinding = DataBindingUtil.setContentView(this, R.layout.player_activity)
        playerActivityBinding.lifecycleOwner = this
        lifecycle.addObserver(playerActivityBinding.playerView)
        songs = intent.getParcelableArrayListExtra<Song>(BUNDLE_SONGS)

        if (songs.isEmpty()) {
            return
        }
        troterViewModel.savePlaylist(songs)
        playerActivityBinding.items = songs
        playlistFragment = PlaylistFragment(songs)
        lyricsFragment = LyricsFragment(songs[0])
        val sectionsPagerAdapter = PlayerSectionsPagerAdapter(this, supportFragmentManager, arrayListOf(playlistFragment, lyricsFragment))
        playerActivityBinding.viewPager.adapter = sectionsPagerAdapter
        playerActivityBinding.listOrLyrics.setupWithViewPager(playerActivityBinding.viewPager)
        loadPlayer(songs)
    }

    // TODO: "object: ~~~" 이렇게 쓰는거 구림
    private fun loadPlayer(items: ArrayList<Song>) {
        playerActivityBinding.playerView.getYouTubePlayerWhenReady(object: YouTubePlayerCallback {
            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                onLoadPlayer(youTubePlayer, items)
            }
        })
   }

    private fun onLoadPlayer(youTubePlayer: YouTubePlayer, items: ArrayList<Song>) {
        player = youTubePlayer
        youTubePlayer.addListener(object: YouTubePlayerListener {
            // 아직 안쓰는 interface 들
            override fun onApiChange(youTubePlayer: YouTubePlayer) { }
            override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) { }
            override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) { }
            override fun onPlaybackQualityChange(youTubePlayer: YouTubePlayer, playbackQuality: PlayerConstants.PlaybackQuality) { }
            override fun onPlaybackRateChange(youTubePlayer: YouTubePlayer, playbackRate: PlayerConstants.PlaybackRate) { }
            override fun onReady(youTubePlayer: YouTubePlayer) { }
            override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) { }
            override fun onVideoLoadedFraction(youTubePlayer: YouTubePlayer, loadedFraction: Float) { }

            override fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerConstants.PlayerState) {
                // TODO : 여기다가 랜덤 및 반복 재생 구현하면 될듯.
                if (state === PlayerConstants.PlayerState.ENDED) {
                    val nextIdx = songs.indexOf(currentItem) + 1
                    if (nextIdx  <= songs.size) {
                        youTubePlayer.loadVideo(songs[nextIdx].video, 0f)
                    }
                }
            }

            override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {
                currentItem = items.find { item -> item.video == videoId }
                playerActivityBinding.currentItem = currentItem
                playlistFragment.setCurrentPlayingSong(currentItem!!)
            }
        })

        if (items.isNotEmpty()) {
            for (item in items.reversed()) {
                youTubePlayer.loadOrCueVideo(lifecycle, item.video, 0f)
            }
        }
    }

    fun onClickItem(item: Song) {
        player.loadVideo(item.video, 0f)
    }

    fun isPlaying(item: Song): Boolean {
        return currentItem?.video == item.video
    }

    companion object {
        const val BUNDLE_SONGS = "BUNDLE_SONGS"
    }
}
