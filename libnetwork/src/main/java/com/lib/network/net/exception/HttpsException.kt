package com.lib.network.net.exception

import java.lang.Exception

class HttpsException(msg: String = "Is Not http request,Should be https") : Exception(msg)