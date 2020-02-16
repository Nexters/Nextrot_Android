package com.nextrot.troter.songs

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.nextrot.troter.CommonUtil
import com.nextrot.troter.R
import com.nextrot.troter.base.BottomSheetActivity
import com.nextrot.troter.base.SongsFragment
import com.nextrot.troter.databinding.SongsActivityBinding
import com.nextrot.troter.player.PlayerActivity
import kotlinx.android.synthetic.main.main_activity.*
import org.koin.android.viewmodel.ext.android.viewModel

class SongsActivity: AppCompatActivity(), BottomSheetActivity {
    private val songsViewModel: SongsViewModel by viewModel()
    private lateinit var songsActivityBinding: SongsActivityBinding
    private lateinit var title: String
    private lateinit var singerId: String

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        songsActivityBinding = DataBindingUtil.setContentView(this, R.layout.songs_activity)
        songsActivityBinding.lifecycleOwner = this
        songsActivityBinding.viewmodel = songsViewModel
        songsActivityBinding.activity = this

        singerId = intent.getStringExtra(BUNDLE_SINGER_ID)
        title = intent.getStringExtra(BUNDLE_SONGS_TITLE)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
//        supportActionBar?.setDisplayShowTitleEnabled(false)
        songsActivityBinding.collapsingToolbar.title = title

        songsViewModel.selectedItems.observe(this, Observer {
            if (it.isEmpty()) {
                songsActivityBinding.bottomSheet
                    .animate()
                    .translationY(CommonUtil.toDP(87))
                    .start()
            } else {
                songsActivityBinding.bottomSheet
                    .animate()
                    .translationY(CommonUtil.toDP(-24))
                    .start()
            }
        })


        initSongsFragment()
    }

    private fun initSongsFragment() {
        if (title == resources.getString(R.string.recently_played)) {
            songsViewModel.getSavedPlaylist()
        } else {
            songsViewModel.getSongsOfSinger(title)
        }

        supportFragmentManager
            .beginTransaction()
            .add(R.id.list_section, SongsFragment(songsViewModel))
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        songsViewModel.clearSelectedItem()
    }

    /**
    TODO: 레이아웃 형식상 bottom sheet 레이아웃이 main activity 에 오게 됨으로써 얻는 기술부채가 많다.
    참고사항
    1. selectedItems 를 viewModel 로 옮기기 싫었는데, 옮기지 않으면 일정을 맞추기 어려울 듯
    2. 가능하다면 selectedItems 는 SearchFragment 가 들고 있는 것이 낫지 않을까?
    3. SearchFragment 안에서 CoordinatorLayout 을 사용하게 되면 main activity 의 CoordinatorLayout 의 동작과 충돌하여 정상 동작하지 않는 듯 함
     */
    override fun onClickPlay() {
        val intent = Intent(this, PlayerActivity::class.java).apply {
            putParcelableArrayListExtra(PlayerActivity.BUNDLE_SONGS, songsViewModel.selectedItems.value)
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        startActivity(intent)
        songsViewModel.clearSelectedItem()
    }

    override fun onClickCancel() {
        songsViewModel.clearSelectedItem()
    }

    companion object {
        const val BUNDLE_SONGS_TITLE = "BUNDLE_SONGS_TITLE"
        const val BUNDLE_SINGER_ID = "BUNDLE_SINGER_ID"
    }
}