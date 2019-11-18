package com.chorus.hiyue_client.util

import com.chorus.hiyue_client.ui.fragment.CommunicationFragment
import com.chorus.hiyue_client.ui.fragment.HomeFragment

/**
 * 首页fragment的管理类
 */
class FragmentManagerUtil {

    val homeFragment by lazy { HomeFragment() }
    val communicationFragment by lazy { CommunicationFragment() }

    companion object {
        val instance by lazy { FragmentManagerUtil() }
    }

    //返回界面 根据传入id 不同返回不同的界面
    fun initFragment(tabId: Int) {

        when (tabId) {

        }

    }
}