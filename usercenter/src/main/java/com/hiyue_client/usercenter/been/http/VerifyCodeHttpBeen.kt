package com.hiyue_client.usercenter.been.http

import com.hiyue.provider.been.RequestBeen

class VerifyCodeHttpBeen(var mobile: String,
                         var pass: String,
                         var verify_code: String) : RequestBeen() {
}