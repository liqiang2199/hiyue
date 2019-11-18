package com.hiyue.provider.widget

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.hiyue.provider.R
import com.lib.network.ext.onClick
import org.jetbrains.anko.find

class NavCommonView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var ivLeftIcon: ImageView? = null
    private var tvTitle: TextView? = null
    private var tvRight: TextView? = null

    var title: String? = ""
    var rightTitle: String?  = ""
    var isGoneBack = false

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_nav_title, this)
        ivLeftIcon = find(R.id.ivLeftIcon)
        tvTitle = find(R.id.tvTitle)
        tvRight = find(R.id.tvRight)

        ivLeftIcon?.onClick {
            (context as Activity).onBackPressed()
        }
        if (attrs != null) {
            typeAttr(attrs!!)
        }

    }

    private fun typeAttr(attrs: AttributeSet) {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.NavigationBarView)
        title = typeArray.getString(R.styleable.NavigationBarView_title)
        rightTitle = typeArray.getString(R.styleable.NavigationBarView_rightTitle)
        isGoneBack = typeArray.getBoolean(R.styleable.NavigationBarView_isGoneBack, false)

        tvRight?.text = if (!TextUtils.isEmpty(rightTitle)) rightTitle else ""
        tvTitle?.text = if (!TextUtils.isEmpty(title)) title else ""
        ivLeftIcon?.visibility = if (isGoneBack) View.GONE else View.VISIBLE

        typeArray.recycle()
    }
}