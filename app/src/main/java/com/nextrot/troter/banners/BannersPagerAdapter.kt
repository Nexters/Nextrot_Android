package com.nextrot.troter.banners

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class BannersPagerAdapter(private val context: Context, fm: FragmentManager, private val fragments: ArrayList<out Fragment>): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return ""
    }

    override fun getCount(): Int {
        return fragments.size
    }
}