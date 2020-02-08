package com.nextrot.troter

import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.nextrot.troter.search.SectionsPagerAdapter
import kotlinx.android.synthetic.main.main_activity.*
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {
    private val fragments: ArrayList<Fragment> by inject()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (appBarLayout.totalScrollRange + verticalOffset<20) {
                list_section.background = getDrawable(R.drawable.white)
            }else {
                list_section.background = getDrawable(R.drawable.arc_top)
            }
        })

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, fragments)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)


        indicator.createIndicators(3,0)
        indicator.setViewPager(banner_view_pager)
        initAdView()
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
