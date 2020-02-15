package com.nextrot.troter

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.appbar.AppBarLayout
import com.nextrot.troter.base.BottomSheetActivity
import com.nextrot.troter.databinding.MainActivityBinding
import com.nextrot.troter.player.PlayerActivity
import com.nextrot.troter.singers.SingersFragment
import com.nextrot.troter.songs.PopularSongsFragment
import com.nextrot.troter.songs.SectionsPagerAdapter
import com.nextrot.troter.songs.SongsActivity
import com.nextrot.troter.songs.SongsViewModel
import kotlinx.android.synthetic.main.main_activity.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity(), BottomSheetActivity {
    private lateinit var songsFragment: PopularSongsFragment
    private val singersFragment: SingersFragment by inject()
    private val songsViewModel: SongsViewModel by viewModel()
    private lateinit var mainActivityBinding: MainActivityBinding

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        mainActivityBinding.lifecycleOwner = this
        mainActivityBinding.viewmodel = songsViewModel
        mainActivityBinding.activity = this
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (appBarLayout.totalScrollRange + verticalOffset<20) {
                list_section.background = getDrawable(R.drawable.background_default)
            }else {
                list_section.background = getDrawable(R.drawable.arc_top)
            }
        })
        songsFragment = PopularSongsFragment(songsViewModel)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, arrayListOf(songsFragment, singersFragment))
        val viewPager = mainActivityBinding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs = mainActivityBinding.tabs
        tabs.setupWithViewPager(viewPager)

        songsViewModel.selectedItems.observe(this, Observer {
            if (it.isEmpty()) {
                mainActivityBinding.bottomSheet
                    .animate()
                    .translationY(CommonUtil.toDP(87, resources.displayMetrics))
                    .start()
            } else {
                mainActivityBinding.bottomSheet
                    .animate()
                    .translationY(CommonUtil.toDP(-24, resources.displayMetrics))
                    .start()
            }
        })

        indicator.createIndicators(3,0)
        indicator.setViewPager(banner_view_pager)
        initAdView()
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

    fun onClickRecentPlaylist() {
        val intent = Intent(this, SongsActivity::class.java).apply {
            putExtra(SongsActivity.BUNDLE_SONGS_TITLE, resources.getString(R.string.recently_played))
            putExtra(SongsActivity.BUNDLE_SINGER_ID, "")
        }
        startActivityForResult(intent, MAIN_ACTIVITY_REQUEST_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MAIN_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // 인기곡 탭이 무조건 0번 탭이라는 굳은 믿음
                mainActivityBinding.tabs.getTabAt(0)?.select()
            }
        }
    }

    companion object {
        const val MAIN_ACTIVITY_REQUEST_CODE = 0
    }

    /**
     * 액티비티가 늘어나면 클래스로 분리하여 호출
     * 참고: https://github.com/googleads/googleads-mobile-android-examples/tree/master/kotlin/admob/AdaptiveBannerExample */
    private lateinit var adView: AdView
    private var initialLayoutComplete = false
    private val adSize: AdSize
        get() {
            val display = windowManager.defaultDisplay
            val outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)

            val density = outMetrics.density

            var adWidthPixels = ad_view_container.width.toFloat()
            if (adWidthPixels == 0f) {
                adWidthPixels = outMetrics.widthPixels.toFloat()
            }

            val adWidth = (adWidthPixels / density).toInt()
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
        }

    private fun initAdView(){
        MobileAds.initialize(this) {}
        adView = AdView(this)
        ad_view_container.addView(adView)
        ad_view_container.viewTreeObserver.addOnGlobalLayoutListener {
            // onCreate의 재호출에 의한 초기화 방지
            if (!initialLayoutComplete) {
                initialLayoutComplete = true
                loadBanner()
            }
        }
    }

    /** BuildConfig를 이용한 광고 ID 처리*/
    private fun loadBanner() {
        adView.adUnitId = BuildConfig.ADMOB_BANNER_ID
        adView.adSize = adSize

        val adRequest=
            AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build()
        adView.loadAd(adRequest)
    }

    // TODO: lifecycle 바인딩 시킬 수 있는 지 확인
    public override fun onPause() {
        adView.pause()
        super.onPause()
    }

    public override fun onResume() {
        super.onResume()
        adView.resume()
    }

    public override fun onDestroy() {
        adView.destroy()
        super.onDestroy()
    }

}