package com.chorus.hiyue_client.been

import com.chad.library.adapter.base.entity.MultiItemEntity

class RecommendBeen : MultiItemEntity {

    var type: Int = 0

    override fun getItemType(): Int {
        return type
    }
}