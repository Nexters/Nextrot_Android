package com.nextrot.troter.player.list

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.nextrot.troter.CommonUtil
import com.nextrot.troter.data.Song
import com.nextrot.troter.databinding.PlaylistFragmentBinding
import com.nextrot.troter.songs.PlayerViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import java.util.*

class PlaylistFragment(private val playerViewModel: PlayerViewModel, private val player: YouTubePlayer) : Fragment() {
    private lateinit var playlistFragmentBinding: PlaylistFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        playlistFragmentBinding = PlaylistFragmentBinding.inflate(inflater, container, false).apply {
            fragment = this@PlaylistFragment
            viewmodel = playerViewModel
        }
        return playlistFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        playlistFragmentBinding.lifecycleOwner = viewLifecycleOwner
        setupListView()
        setupPlayer()
    }

    private fun setupPlayer() {
        player.addListener(object: YouTubePlayerListener {
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
                if (state === PlayerConstants.PlayerState.ENDED) {
                    val nextIdx = getProperNextIndex()
                    if (0 <= nextIdx && nextIdx < playerViewModel.songsCount.value!!) {
                        youTubePlayer.loadVideo(playerViewModel.songs[nextIdx].video, 0f)
                    }
                }
            }

            override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {
                playerViewModel.setCurrentlyPlayingSong(videoId)
                playlistFragmentBinding.listView.adapter?.notifyDataSetChanged()
            }
        })

        if (playerViewModel.songs.isNotEmpty()) {
            for (item in playerViewModel.songs.reversed()) {
                player.loadOrCueVideo(lifecycle, item.video, 0f)
            }
            player.loadVideo(playerViewModel.songs[0].video, 0f)
        }
    }

    private fun getProperNextIndex(): Int {
        val currentSong = playerViewModel.currentlyPlayingSong.value
        val curIndex = playerViewModel.songs.indexOf(currentSong)
        return if (playerViewModel.isRandom.value == true) {
            var random = Random().nextInt(playerViewModel.songsCount.value!!)
            while (curIndex == random) {
                random = Random().nextInt(playerViewModel.songsCount.value!!)
            }
            random
        } else if (curIndex + 1 < playerViewModel.songsCount.value!!) {
            curIndex + 1
        } else if (playerViewModel.isRepeat.value == true) {
            0
        } else {
            -1
        }
    }

    private fun setupListView() {
        playlistFragmentBinding.listView.apply {
            adapter = PlaylistAdapter(this@PlaylistFragment, playerViewModel).apply {
                submitList(playerViewModel.songs)
            }
            addItemDecoration(object: RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    itemPosition: Int,
                    parent: RecyclerView
                ) {
                    super.getItemOffsets(outRect, itemPosition, parent)
                    outRect.top += CommonUtil.toDP(8).toInt()
                }
            })
        }
    }

    fun onClickItem(item: Song) {
        player.loadVideo(item.video, 0f)
    }

    // 이 이벤트들 자체를 PlaylistFragment 로 옮기는 게 됐으면 좋겠긴 하다.
    fun onClickRandom() {
        playerViewModel.toggleIsRandom()
    }

    fun onClickRepeat() {
        playerViewModel.toggleIsRepeat()
    }
}