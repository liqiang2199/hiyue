package com.lib.network.net.exception

import java.lang.Exception

class HttpException(msg: String = "Is Not https request,Should be http") : Exception(msg)