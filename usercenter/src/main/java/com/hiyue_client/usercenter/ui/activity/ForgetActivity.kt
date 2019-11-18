package com.hiyue_client.usercenter.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.example.extras.Utils
import com.hiyue.provider.ext.enAbleBut
import com.hiyue.provider.ext.enAbleDefaultFalse
import com.hiyue_client.usercenter.R
import com.lib.network.app.ActivityManager
import com.lib.network.ext.onClick
import com.lib.network.ui.BaseActivity
import com.lib.network.util.ToastUtil
import kotlinx.android.synthetic.main.activity_forget.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

/**
 * 忘记密码
 */
class ForgetActivity : BaseActivity() {

    private lateinit var but: Button

    override fun initLayoutView(): Int {
        return R.layout.activity_forget
    }

    override fun initView() {
        ActivityManager.getInstance().addChildActivity(this)
        but = find(R.id.but)
        but.text = resources.getText(R.string.sure_re_set_forget_pass)
        but.enAbleDefaultFalse()
        but.onClick {
            //跳转发送验证码界面
            val phone = editView.getEditView().text.toString().trim()
            if (!Utils.isMobile(phone)) {
                ToastUtil.Toast_ShortUtil(this, "请输入正确的手机号")
                return@onClick
            }
            startActivity<ForgetVerActivity>("phone" to phone)

        }

        editView.getEditView().enAbleBut(but)
        editView.setEditInputNumber()

    }

    override fun initIntentData(intent: Intent) {

    }

    override fun initViewInstanceState(savedInstanceState: Bundle?) {

    }
}