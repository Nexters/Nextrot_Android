package com.nextrot.troter

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.ads.*
import com.google.android.material.tabs.TabLayout
import com.nextrot.troter.search.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val fragments: ArrayList<Fragment> by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, fragments)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

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
