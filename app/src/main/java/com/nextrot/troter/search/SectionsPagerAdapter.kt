package com.nextrot.troter.search

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.nextrot.troter.R

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager, private val fragments: ArrayList<out Fragment>): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return fragments.size
    }

    companion object {
        @JvmStatic
        val TAB_TITLES = arrayOf(
            R.string.tab_title_1,
            R.string.tab_title_2,
            R.string.tab_title_3
        )
    }
}