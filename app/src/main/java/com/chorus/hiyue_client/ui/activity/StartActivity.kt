package com.chorus.hiyue_client.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.chorus.hiyue_client.R
import com.hiyue_client.usercenter.ChoiceLoginActivity
import com.hiyue_client.usercenter.ui.LoginActivity
import com.hiyue_client.videomodule.ui.VideoActivity
import com.lib.network.ui.BaseActivity
import org.jetbrains.anko.startActivity

/**
 * 启动页面
 */
class StartActivity : BaseActivity() {
    override fun initLayoutView(): Int {
        return R.layout.activity_start
    }

    override fun initView() {
        Handler().postDelayed({
            startActivity<ChoiceLoginActivity>()
            //startActivity<VideoActivity>()
        }, 3000L)
    }

    override fun initIntentData(intent: Intent) {
    }

    override fun initViewInstanceState(savedInstanceState: Bundle?) {
    }
}