package com.hiyue_client.usercenter.ui.activity.teacher

import android.content.Intent
import android.os.Bundle
import com.hiyue_client.usercenter.R
import com.lib.network.ext.onClick
import com.lib.network.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_teacher.*
import org.jetbrains.anko.startActivity

/**
 * 我是老师
 */
class TeacherActivity : BaseActivity() {
    override fun initLayoutView(): Int {
        return R.layout.activity_teacher
    }

    override fun initView() {
        llTeacherAuth.onClick {
            //认证
            startActivity<TeacherAuthActivity>()
        }
    }

    override fun initIntentData(intent: Intent) {

    }

    override fun initViewInstanceState(savedInstanceState: Bundle?) {

    }
}