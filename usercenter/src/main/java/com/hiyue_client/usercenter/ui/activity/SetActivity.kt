package com.hiyue_client.usercenter.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.hiyue_client.usercenter.R
import com.lib.network.ext.onClick
import com.lib.network.ui.BaseActivity

/**
 * 设置界面
 */
class SetActivity : BaseActivity(){

    private var but: Button? = null

    override fun initLayoutView(): Int {
        return R.layout.activity_set
    }

    override fun initView() {
        but = findViewById(R.id.but)
        but?.text = "退出登录"
        but?.onClick {
            //确定

        }
    }

    override fun initIntentData(intent: Intent) {

    }

    override fun initViewInstanceState(savedInstanceState: Bundle?) {

    }
}