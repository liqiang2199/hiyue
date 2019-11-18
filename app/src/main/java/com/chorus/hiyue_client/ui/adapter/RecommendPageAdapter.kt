package com.chorus.hiyue_client.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup

/**
 * 适配器
 */
class RecommendPageAdapter(fragmentManager: FragmentManager,
                           var fragments: MutableList<Fragment>,
                           var title: MutableList<String>) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(p0: Int): Fragment {
        return fragments[p0]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position]
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        //super.destroyItem(container, position, `object`)
    }
}