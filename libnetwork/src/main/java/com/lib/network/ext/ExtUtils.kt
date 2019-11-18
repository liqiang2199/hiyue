package com.lib.network.ext

import android.view.View

fun View.onClick(onclick: () -> Unit) {
    this.setOnClickListener {
        onclick()
    }
}