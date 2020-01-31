package com.nextrot.troter.player

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.nextrot.troter.R
import com.nextrot.troter.data.Item
import com.nextrot.troter.databinding.PlayerActivityBinding
import com.nextrot.troter.player.list.PlaylistFragment
import com.nextrot.troter.player.lyrics.LyricsFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback

class PlayerActivity : AppCompatActivity() {
    private lateinit var playerActivityBinding: PlayerActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        playerActivityBinding = DataBindingUtil.setContentView(this, R.layout.player_activity)
        playerActivityBinding.lifecycleOwner = this
        val items = intent?.getParcelableArrayListExtra<Item>(BUNDLE_PLAYER_ITEM) ?: arrayListOf()
        playerActivityBinding.items = items

        val sectionsPagerAdapter = PlayerSectionsPagerAdapter(this, supportFragmentManager, arrayListOf(PlaylistFragment(items), LyricsFragment(items[0])))
        playerActivityBinding.viewPager.adapter = sectionsPagerAdapter
        playerActivityBinding.listOrLyrics.setupWithViewPager(playerActivityBinding.viewPager)

        if (items.isNotEmpty()) {
            startVideo(items[0])
            playerActivityBinding.currentItem = items[0]
        }
    }

    // TODO: dataBinding 으로 처리 할 순 없을까? (app:videoId)
    private fun startVideo(item: Item) {
        playerActivityBinding.playerView.getYouTubePlayerWhenReady(object: YouTubePlayerCallback {
            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(item.id.videoId, 0f)
            }
        })
    }

    override fun onPause() {
        super.onPause()
        playerActivityBinding.playerView.release()
    }

    companion object {
        const val BUNDLE_PLAYER_ITEM = "bundle_player_item"
    }
}
