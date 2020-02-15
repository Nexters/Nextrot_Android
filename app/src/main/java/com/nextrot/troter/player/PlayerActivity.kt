package com.nextrot.troter.player

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.nextrot.troter.R
import com.nextrot.troter.data.Song
import com.nextrot.troter.databinding.PlayerActivityBinding
import com.nextrot.troter.player.list.PlaylistFragment
import com.nextrot.troter.player.lyrics.LyricsFragment
import com.nextrot.troter.songs.PlayerViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class PlayerActivity : AppCompatActivity() {
    private lateinit var playerActivityBinding: PlayerActivityBinding
    private lateinit var playlistFragment: PlaylistFragment
    private lateinit var lyricsFragment: LyricsFragment
    private val playerViewModel: PlayerViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        playerActivityBinding = DataBindingUtil.setContentView(this, R.layout.player_activity)
        playerActivityBinding.lifecycleOwner = this
        playerActivityBinding.viewmdoel = playerViewModel
        lifecycle.addObserver(playerActivityBinding.playerView)
        val songs = intent.getParcelableArrayListExtra<Song>(BUNDLE_SONGS)

        if (songs.isEmpty()) {
            return
        }

        playerViewModel.savePlaylist(songs)
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
        playlistFragment = PlaylistFragment(playerViewModel, youTubePlayer)
        lyricsFragment = LyricsFragment(playerViewModel)
        val sectionsPagerAdapter = PlayerSectionsPagerAdapter(this, supportFragmentManager, arrayListOf(playlistFragment, lyricsFragment))
        playerActivityBinding.viewPager.adapter = sectionsPagerAdapter
        playerActivityBinding.listOrLyrics.setupWithViewPager(playerActivityBinding.viewPager)
    }

    companion object {
        const val BUNDLE_SONGS = "BUNDLE_SONGS"
    }
}
