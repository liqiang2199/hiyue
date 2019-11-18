package com.chorus.hiyue_client.ui.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chorus.hiyue_client.R
import com.chorus.hiyue_client.been.RecommendBeen

class RecommendAdapter
    : BaseMultiItemQuickAdapter<RecommendBeen, BaseViewHolder> {

    constructor(data: MutableList<RecommendBeen>?) : super(data) {
        addItemType(0, R.layout.fragment_my_class)
        addItemType(1, R.layout.fragment_hot_class)
    }

    override fun convert(holder: BaseViewHolder?, p1: RecommendBeen?) {
        when (holder?.itemViewType) {
            1 -> {
                val myCourseAdapter = MyCourseAdapter(R.layout.item_new_tj)
            }
            0 -> {
                val hotCourseAdapter = HotCourseAdapter(R.layout.item_hot_class)
            }
        }
    }
}