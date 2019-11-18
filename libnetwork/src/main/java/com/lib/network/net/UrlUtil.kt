package com.lib.network.net

import com.lib.network.net.exception.HttpException
import com.lib.network.net.exception.HttpsException
import com.lib.network.util.RegexUtil

object UrlUtil {

    //val baseUrl = "http://192.168.0.180:9140/"//基本请求
    //https://h.mdeve.com/interface/request.json
    val baseUrl = "https://h.mdeve.com/"//基本请求
    var customIP = ""//自定义IP

    /**
     * 自定义http
     */
    fun customBaseUrl_Http(url: String): String {
        if (RegexUtil.httpReges(url)) {
            return url
        }
        if (RegexUtil.httpsReges(url)) {
            throw HttpsException()
        }
        return "http://$url/"
    }

    /**
     * 自定义https
     */
    fun customBaseUrl_Https(url: String): String {
        if (RegexUtil.httpReges(url)) {
            throw HttpException()
        }
        if (RegexUtil.httpsReges(url)) {
            return url
        }
        return "https://$url/"
    }
}