package com.hiyue_client.videomodule.ui

import android.content.Intent
import android.os.Bundle
import com.hiyue_client.videomodule.R
import com.lib.network.ui.BaseActivity
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import org.jetbrains.anko.find


/**
 * 视频加载界面 和播放界面
 */
class VideoActivity : BaseActivity() {
    override fun initLayoutView(): Int {
        return R.layout.activity_video
    }

    override fun initView() {
        val videoplayer: JzvdStd = find(R.id.videoplayer)
        videoplayer.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4"
                , "饺子闭眼睛");

//        videoplayer.setUp("http://xunleib.zuida360.com/1811/僵尸世界2.HD高清中英双字版.mp4"
//                , "饺子闭眼睛");
        //videoplayer.thumbImageView.setImage("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");

    }

    override fun initIntentData(intent: Intent) {

    }

    override fun initViewInstanceState(savedInstanceState: Bundle?) {

    }

    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        Jzvd.resetAllVideos()
    }
}