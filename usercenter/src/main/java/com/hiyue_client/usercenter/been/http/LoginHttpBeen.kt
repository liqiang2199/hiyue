package com.hiyue_client.usercenter.been.http

import com.hiyue.provider.been.RequestBeen

class LoginHttpBeen(var mobile: String,
                    var pass: String) : RequestBeen() {
}