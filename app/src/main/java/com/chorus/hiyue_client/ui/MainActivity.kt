package com.chorus.hiyue_client.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import com.alibaba.android.arouter.facade.annotation.Route
import com.chorus.hiyue_client.R
import com.chorus.hiyue_client.ui.fragment.*
import com.hiyue.provider.common.RouterUtil
import com.lib.network.ui.BaseActivity
import com.lib.network.ui.BaseFragment
import com.lib.network.util.ToastUtil
import kotlinx.android.synthetic.main.activity_main.*







/**
 * 首页
 */
@Route(path = RouterUtil.ROUTE_MAIN)
class MainActivity : BaseActivity() {

    private val fragmentList = arrayListOf(HomeFragment(),
            RecommendFragment(),CommunicationFragment(), MeFragment() )
    private var currentFragment: BaseFragment? = null
    private var indexPage = -1//当前页下标

    override fun initLayoutView(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
//        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.fvFragmentContent, HomeFragment())
//        fragmentTransaction.commit()


//        bottomBar.setOnTabReselectListener { tabId ->
////            if (tabId == R.id.tab_home) {
////                changePageIndex(0)
////            }
////            if (tabId == R.id.tab_music_score) {
////                changePageIndex(1)
////            }
////            if (tabId == R.id.tab_communication) {
////                changePageIndex(2)
////            }
////            if (tabId == R.id.tab_me) {
////                changePageIndex(3)
////            }
//            ToastUtil.Toast_ShortUtil(this, "setOnTabReselectListener")
//        }
//
        bottomBar.setOnTabSelectListener {
            tabId ->
            //ToastUtil.Toast_ShortUtil(this, "setOnTabSelectListener")
            if (tabId == R.id.tab_home) {
                changePageIndex(0)
            }
            if (tabId == R.id.tab_music_score) {
                changePageIndex(1)
            }
            if (tabId == R.id.tab_communication) {
                changePageIndex(2)
            }
            if (tabId == R.id.tab_me) {
                changePageIndex(3)
            }
        }

    }

    override fun initIntentData(intent: Intent) {

    }

    override fun initViewInstanceState(savedInstanceState: Bundle?) {

    }

    private fun changePageIndex(index: Int) {
        if (indexPage == index) {
            currentFragment = fragmentList[index]
            return
        }
        indexPage = index

        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        if ( fragmentList[index].isAdded && currentFragment != null) {
            fragmentTransaction.hide(currentFragment!!)
            fragmentTransaction.show(fragmentList[index])
        } else {
            fragmentTransaction.add(R.id.fvFragmentContent, fragmentList[index])
        }
        currentFragment = fragmentList[index]
        fragmentTransaction.commit()
    }


}
