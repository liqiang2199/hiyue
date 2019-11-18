package com.chorus.hiyue_client.util

import android.content.Context
import android.widget.ImageView
import com.lib.network.util.GlideUtil
import com.youth.banner.loader.ImageLoader

/**
 * 加载图片
 */
class GlideImageLoader : ImageLoader() {

    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        //加载图片
        GlideUtil.glideLoadImage(context!!, path!! as String, imageView!!)
    }

    override fun createImageView(context: Context?): ImageView {
        val imageView = ImageView(context)
        imageView.scaleType = ImageView.ScaleType.FIT_XY

        return imageView
    }
}