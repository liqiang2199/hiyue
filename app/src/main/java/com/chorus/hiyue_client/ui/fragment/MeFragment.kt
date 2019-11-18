package com.chorus.hiyue_client.ui.fragment

import android.os.Bundle
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chorus.hiyue_client.R
import com.chorus.hiyue_client.been.RecommendBeen
import com.chorus.hiyue_client.ui.adapter.RecommendAdapter
import com.hiyue_client.usercenter.ui.activity.PersonalInfoActivity
import com.hiyue_client.usercenter.ui.activity.SetActivity
import com.hiyue_client.usercenter.ui.activity.integral.IntegralActivity
import com.hiyue_client.usercenter.ui.activity.teacher.TeacherActivity
import com.lib.network.ext.onClick
import com.lib.network.ui.BaseFragment
import com.lib.network.ui.fragment.BaseListFragment
import kotlinx.android.synthetic.main.fragment_me.*
import org.jetbrains.anko.support.v4.startActivity

class MeFragment : BaseFragment() {

    override fun initView(view: View) {
        llPageInfo.onClick {
            //个人中心
            startActivity<PersonalInfoActivity>()
        }
        llTeacher.onClick {
            //我是老师
            startActivity<TeacherActivity>()
        }
        llSet.onClick {
            //设置
            startActivity<SetActivity>()
        }
        llIntegral.onClick {
            //可用积分
            startActivity<IntegralActivity>()
        }
        llIntegralTotal.onClick {
            //总积分
            startActivity<IntegralActivity>()
        }
    }

    override fun initViewInstanceState(savedInstanceState: Bundle?) {

    }


    override fun lazyLoad() {

    }

    override fun argumentsParam(arguments: Bundle) {
    }

    override fun initLayoutRes(): Int {
        return R.layout.fragment_me
    }
}