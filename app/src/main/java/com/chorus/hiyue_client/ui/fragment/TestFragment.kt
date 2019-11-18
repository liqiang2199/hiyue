package com.chorus.hiyue_client.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chorus.hiyue_client.R
import com.lib.network.ui.BaseFragment

class TestFragment : BaseFragment() {
    override fun lazyLoad() {

    }

    override fun argumentsParam(arguments: Bundle) {

    }

    override fun initView(view: View) {

    }

    override fun initLayoutRes(): Int {
        return R.layout.item_hot_class
    }

    override fun initViewInstanceState(savedInstanceState: Bundle?) {

    }

//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return LayoutInflater.from(container!!.context!!).inflate(R.layout.item_hot_class, null)
//    }
}