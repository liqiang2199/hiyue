package com.hiyue_client.usercenter.been

import com.hiyue.provider.been.RequestBeen

class SendSmsBeen(var receiver : String? = null,
                  var call_index: String? = null) : RequestBeen() {

}