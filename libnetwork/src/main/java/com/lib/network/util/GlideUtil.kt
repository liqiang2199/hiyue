package com.lib.network.util


import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

object GlideUtil {

    fun glideLoadImage(activity: Context, url: String, imageView: ImageView) {
        Glide
                .with(activity)
                .load(url)
                .centerCrop()
                //.placeholder(R.drawable.)
                .into(imageView)
    }

    //图片地址
    @SuppressLint("CheckResult")
    fun loadImg(context: Context, imgUrl: String, imageView: ImageView) {
        val requestOptions = RequestOptions();
        //requestOptions.placeholder(R.drawable.ic_launcher_background);
        //requestOptions.circleCropTransform()
        requestOptions.optionalTransform( RoundedCorners(30))
        Glide.with(context).load(imgUrl).apply(requestOptions).into(imageView)
    }
}