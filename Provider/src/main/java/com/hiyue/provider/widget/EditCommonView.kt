package com.hiyue.provider.widget

import android.content.Context
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import com.hiyue.provider.R
import com.hiyue.provider.ext.isNoNullOrEmpty
import com.hiyue.provider.utils.TextWatcherUtil
import com.lib.network.ext.onClick
import org.jetbrains.anko.find

class EditCommonView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var editCommon: EditText? = null
    private var ivDeleteEdit: ImageView? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_edit_common, this)
        val attributeSet = context.obtainStyledAttributes(
                attrs, R.styleable.edit_CommonVew)
        val hintText = attributeSet.getString(R.styleable.edit_CommonVew_editHintText)

        editCommon = find(R.id.editCommon)
        ivDeleteEdit = find(R.id.ivDeleteEdit)

        editCommon?.hint = hintText ?: ""

        ivDeleteEdit?.onClick {
            editCommon?.setText("")
        }
        editCommon?.addTextChangedListener(object : TextWatcherUtil() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val str = s?.toString()?.trim() ?: ""
                if (str.isNoNullOrEmpty()) {
                    ivDeleteEdit?.visibility = View.VISIBLE
                } else {
                    ivDeleteEdit?.visibility = View.GONE
                }
            }
        })
        attributeSet.recycle()
    }

    fun getEditView(): EditText {
        return editCommon!!
    }

    /**
     * 设置 输入框的样式只能是 数字
     */
    fun setEditInputNumber() {
        editCommon?.inputType = InputType.TYPE_CLASS_NUMBER
    }

    /**
     * 输入密码
     */
    fun setEditInputPass() {
        editCommon?.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
    }
}