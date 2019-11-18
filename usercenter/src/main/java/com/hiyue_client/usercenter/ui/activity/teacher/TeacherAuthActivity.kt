package com.hiyue_client.usercenter.ui.activity.teacher

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.hiyue_client.usercenter.R
import com.lib.network.ui.BaseActivity
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * 我是老师 -> 认证
 */
class TeacherAuthActivity : BaseActivity() {

    private var  but: Button? = null

    override fun initLayoutView(): Int {
        return R.layout.activity_teacher_auth
    }

    override fun initView() {
        but = find(R.id. but)
        but?.text = "提交认证"
        but?.onClick {
            //
        }
    }

    override fun initIntentData(intent: Intent) {

    }

    override fun initViewInstanceState(savedInstanceState: Bundle?) {

    }
}