package com.nextrot.troter.banners

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nextrot.troter.data.Banner
import com.nextrot.troter.databinding.BannerFragmentBinding

class BannerFragment(private val banner: Banner): Fragment() {
    private lateinit var bannerFragmentBinding: BannerFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bannerFragmentBinding = BannerFragmentBinding.inflate(layoutInflater, container, false).apply {
            banner = this@BannerFragment.banner
        }
        return bannerFragmentBinding.root
    }

    fun onClickBanner(banner: Banner) {
        // TODO: 배너 클릭 구현
    }
}