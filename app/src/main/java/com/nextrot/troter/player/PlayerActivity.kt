package com.nextrot.troter.player

import android.app.AlertDialog
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.nextrot.troter.IS_FIRST_PLAY_SONGS
import com.nextrot.troter.R
import com.nextrot.troter.data.Song
import com.nextrot.troter.databinding.PlayerActivityBinding
import com.nextrot.troter.player.list.PlaylistFragment
import com.nextrot.troter.player.lyrics.LyricsFragment
import com.nextrot.troter.songs.PlayerViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class PlayerActivity : AppCompatActivity() {
    private lateinit var playerActivityBinding: PlayerActivityBinding
    private lateinit var playlistFragment: PlaylistFragment
    private lateinit var lyricsFragment: LyricsFragment
    private val playerViewModel: PlayerViewModel by viewModel()
    private val sharedPreferences: SharedPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        playerActivityBinding = DataBindingUtil.setContentView(this, R.layout.player_activity)
        playerActivityBinding.lifecycleOwner = this
        playerActivityBinding.viewmdoel = playerViewModel
        lifecycle.addObserver(playerActivityBinding.playerView)

        if (sharedPreferences.getBoolean(IS_FIRST_PLAY_SONGS, true)) {
            showPlayerNoticeDialog()
        }

        val songs = intent.getParcelableArrayListExtra<Song>(BUNDLE_SONGS)

        if (songs.isEmpty()) {
            return
        }

        playerViewModel.savePlaylist(songs)
        loadPlayer(songs)
    }

    override fun onDestroy() {
        super.onDestroy()
        playerActivityBinding.playerView.release()
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

    private fun showPlayerNoticeDialog() {
        val builder = AlertDialog.Builder(this).apply {
            setTitle("백그라운드 재생 불가 안내")
            setMessage("이 앱은 유튜브 영상재생 정책을 따릅니다.\n화면이 꺼지거나 재생영상이 화면에서 사라지면 재생이 불가하오니 이점 양해 부탁드립니다.")
                // TODO: message 또는 MultiChoice 양자택일 해야 합니다.. 리팩 때 디자인 나오면 수정 고고
                //  builder.setMultiChoiceItems(
                //      arrayOf("다시 보지 않기"), booleanArrayOf(false)) { dialog, which, isChecked -> }
                .setPositiveButton("확인") { dialog, which ->
                    this@PlayerActivity.sharedPreferences.edit().putBoolean(IS_FIRST_PLAY_SONGS, false).apply()
                }
        }
        val dialog = builder.create()
        dialog.show()

        // show로 dialog가 생성되어야함.. NPE 주의
        val positiveBtn = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        positiveBtn.setTextColor(Color.parseColor("#1884ff"))
    }

    companion object {
        const val BUNDLE_SONGS = "BUNDLE_SONGS"
    }
}
