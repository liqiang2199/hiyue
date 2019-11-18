package com.chorus.hiyue_client.ui.fragment

import android.os.Bundle
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chorus.hiyue_client.R
import com.chorus.hiyue_client.been.RecommendBeen
import com.chorus.hiyue_client.ui.adapter.RecommendAdapter
import com.lib.network.ui.fragment.BaseListFragment

class RecommendFragment : BaseListFragment<RecommendBeen, BaseViewHolder>() {

    private var data: MutableList<RecommendBeen> = ArrayList()


    override fun initAdapter(): BaseQuickAdapter<RecommendBeen, BaseViewHolder> {
        return RecommendAdapter(data)
    }

    override fun getRefreshData() {
        for (i in 0 until 10) {
            val reBeen = RecommendBeen()
            data.add(reBeen)
        }
        setListData(data)
    }

    override fun getLoadMoreData() {
    }

    override fun lazyLoad() {
        autoRefresh()
    }

    override fun argumentsParam(arguments: Bundle) {
    }

    override fun initView(view: View) {
        super.initView(view)

    }

    override fun initLayoutRes(): Int {
        return R.layout.fragment_recommend
    }
}