package com.chorus.hiyue_client.ui.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chorus.hiyue_client.R
import com.chorus.hiyue_client.been.VideoBeen
import com.chorus.hiyue_client.ui.adapter.RecommendPageAdapter
import com.chorus.hiyue_client.ui.adapter.VideoAdapter
import com.chorus.hiyue_client.util.GlideImageLoader
import com.lib.network.ui.BaseFragment
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * 首页加载
 */
class HomeFragment : BaseFragment() {

    private var recommendAdapter: RecommendPageAdapter? = null

    override fun initViewInstanceState(savedInstanceState: Bundle?) {

    }

    override fun lazyLoad() {

    }

    override fun argumentsParam(arguments: Bundle) {

    }

    override fun initView(view: View) {

        val fragmentList: MutableList<Fragment> = ArrayList()
        fragmentList.add(RecommendFragment())
        fragmentList.add(RecommendFragment())
        fragmentList.add(RecommendFragment())
        fragmentList.add(RecommendFragment())
        fragmentList.add(RecommendFragment())
        val titleStr = arrayListOf("推荐", "钢琴", "吉他", "电子琴", "笛子")

        recommendAdapter = RecommendPageAdapter(activity!!.supportFragmentManager, fragmentList,
                titleStr)
        viewPager.adapter = recommendAdapter
        viewPager.setNoScroll(false)
        tabMusicType.setupWithViewPager(viewPager)
    }


    override fun initLayoutRes(): Int {
        return R.layout.fragment_home
    }

}