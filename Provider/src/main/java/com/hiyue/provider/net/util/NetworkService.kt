package com.hiyue.provider.net.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.hiyue.provider.encrypt.EncryptUtils
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import java.util.concurrent.*
import kotlin.system.exitProcess

object NetworkService {

//    private var TAG = "NetworkService"
//    private var queue: ExecutorService
//    private var workQueue: BlockingQueue<Runnable>
//
//    init {
//        // 允许线程数为当前CPU核心数
//        val processorsNums = Runtime.getRuntime().availableProcessors()
//        //Utils.log("Processors: $processorsNums")
//
//        //queue = Executors.newFixedThreadPool(processorsNums)
//        //queue = Executors.newCachedThreadPool()
//
//        workQueue = LinkedBlockingQueue<Runnable>()
//        queue = ThreadPoolExecutor(processorsNums, processorsNums, 0L, TimeUnit.MILLISECONDS, workQueue)
//
//    }
//
//
//    fun queueCancel() {
//        workQueue.clear()
//    }
//
//    /**
//     * 预加载网络图片
//     */
//    fun preloading(context: Context, urlString: String, isResources:Boolean=true) {
//        queue.execute {
//            val urlHash = EncryptUtils.md5(urlString)
//            val cacheFilePath = (if (isResources) GlobalConst.resourcesPath else GlobalConst.imageCachePath) + File.separator + urlHash
//            // 文件缓存已存在
//            if (FileUtils.exists(cacheFilePath)) {
//                return@execute
//            }
//            downloadFile(context, urlString, isResources) { }
//        }
//    }
//
//    /**
//     * 下载网络文件(小文件)，并直接返回文件内容
//     */
//    fun downloadFile(context: Context, urlString: String, isResources:Boolean=false, callback:(bitmap: Bitmap)->Unit) {
//        queue.execute {
//            try {
//                val urlHash = EncryptUtils.md5(urlString)
//                val cacheFilePath = (if (isResources) GlobalConst.resourcesPath else GlobalConst.imageCachePath) + File.separator + urlHash
//                if (FileUtils.exists(cacheFilePath)) {
//                    DiskService.getBitmapAsync(cacheFilePath) { bitmap ->
//                        callback(bitmap)
//                    }
//                } else {
//                    val url = URL(urlString)
//                    val conn = url.openConnection() as HttpURLConnection
//                    conn.requestMethod = "GET"
//                    conn.useCaches = false
//                    conn.connect()
//                    val code = conn.responseCode
//                    if (code == 200) {
//                        val inputStream = conn.inputStream
//                        val bitmap = BitmapFactory.decodeStream(inputStream)
//                        inputStream.close()
//                        callback(bitmap)
//                        DiskService.savaBitmapAsync(cacheFilePath, bitmap)
//                        println("NetworkService ------------> Download from network: $urlString")
//                    } else {
//                        Log.w(TAG, "Image: $url Download Fail!")
//                    }
//                }
//            } catch (e: Exception) {
//                Log.w(TAG, "-------------> Unable to resolve host: $urlString")
//                e.printStackTrace()
//            }
//        }
//    }
//
//    fun postApi(context: Context, method: String, params: TreeMap<String, String>?=null, needLogin: Boolean=true, callback:(Any)->Unit) {
//
//        queue.execute {
//            val device = "android"
//            val deviceIdentification = GlobalConst.deviceIdentity
//            val appVersion = Utils.getVersionName(context)
//            val deviceVersion = Build.VERSION.RELEASE
//            val deviceTime = Utils.getSystemDate()
//            val deviceUnixTime = Utils.dateToStamp(deviceTime)
//            val appVersionCode = Utils.getVersionCode(context)
//
//            // 如果程序隐形崩溃会清除所有保存的数据并自动重置，在这会再崩一次
//            // 防止这种情况直接重启整个app
//            if (deviceIdentification.length != 32) {
//                //Toast.makeText(context, "程序异常，正在自动重启", Toast.LENGTH_LONG).show()
//                Handler().postDelayed({
//                    Utils.restartAPP(context)
//                }, 1000)
//                return@execute
//            }
//
//            // 获取效验码
//            val t1 = EncryptUtils.md5(EncryptUtils.md5(deviceUnixTime.substring(deviceUnixTime.length - 6)).substring(8, 23))
//
//            val n8 = Integer.valueOf(t1.substring(7, 8), 16)
//            val n19 = Integer.valueOf(t1.substring(18, 19), 16)
//            val n23 = Integer.valueOf(t1.substring(22, 23), 16)
//            val n28 = Integer.valueOf(t1.substring(27, 28), 16)
//
//            var checkCode = EncryptUtils.md5(EncryptUtils.md5(deviceUnixTime).substring(n8))
//            checkCode += EncryptUtils.md5(deviceIdentification.substring(n19))
//            checkCode += EncryptUtils.md5(EncryptUtils.md5(deviceVersion).substring(n23))
//            checkCode = EncryptUtils.md5(EncryptUtils.md5(checkCode).substring(n28))
//
//            var data = TreeMap<String, String>()
//            // 添加访问API
//            data["m"] = method
//            // 添加系统参数
//            data["d"] = device
//            data["t"] = deviceTime
//            data["v"] = deviceVersion
//            data["i"] = deviceIdentification
//            data["e"] = Build.MODEL
//            data["n"] = appVersion
//            data["b"] = appVersionCode.toString()
//            data["c"] = checkCode
//
//            // 添加usertoken
//            if (needLogin) {
//                data["usertoken"] = GlobalConst.usertoken
//            }
//
//            // 合并用户参数
//            params?.forEach {
//                data[it.key] = it.value
//            }
//            val paramsStringEncrypted = EncryptUtils.AES_Encode(Utils.convertTreeMapToJsonString(data), GlobalConst.aesApiKey, GlobalConst.aesApiIv)
//            val postData = Utils.createSortedTreeMap()
//            postData["version"] = appVersion
//            postData["device"] = device
//            postData["params"] = paramsStringEncrypted
//
//            var resultString = ""
//            var paramsString = ""
//            postData.forEach {
//                paramsString += "&" + it.key + "=" + it.value
//            }
//            if (paramsString.isNotEmpty()) paramsString = paramsString.substring(1)
//
//            try {
//                val url = URL(GlobalConst.mainApiUrl)
//                val conn = url.openConnection() as HttpURLConnection
//                conn.readTimeout = 5000
//                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
//                conn.doInput = true
//                conn.doOutput = true
//                conn.useCaches = false
//                conn.requestMethod = "POST"
//                conn.connect()
//                val outputStream = conn.outputStream
//                val postString = paramsString
//                outputStream.write(postString.toByteArray())
//                outputStream.flush()
//                outputStream.close()
//
//                val code = conn.responseCode
//                if (code == 200) {
//                    val inStream = conn.inputStream
//                    val reader = BufferedReader(InputStreamReader(inStream))
//                    val response = StringBuilder()
//                    resultString = reader.use(BufferedReader::readText)
//                    response.append(resultString)
//
//                    //Log.w("response ===>", "网络请求成功")
//
//                    // 解码接收到的数据
//                    val decrpytString = EncryptUtils.AES_Decode(resultString, GlobalConst.aesApiKey, GlobalConst.aesApiIv)
//                    //Log.w("response ===>", decrpytString)
//
//                    // 处理返回数据
//                    handleApiResultData(method, decrpytString, callback)
//                } else {
//                    Log.e("response ===>", "网络错误")
//                }
//            } catch (e: Exception) {
//                Log.w("response ===>", resultString)
//                e.printStackTrace()
//            }
//        }
//    }
//
//
//    private fun handleApiResultData(method: String, jsonString: String, callback:(Any)->Unit) {
//        try {
//            val json = JSONObject(jsonString)
//            if(!json.has("code") || !json.has("time") || !json.has("ver") || !json.has("data")) {
//                Log.w(TAG, "Json数据格式错误")
//                return
//            }
//
//            // 检查API状态
//            when(json.getInt("code")) {
//                0 -> {
//                    callback(json.get("data"))
//                }
//                2 -> {
//                    Log.e(TAG, "登录超时")
//                    toashError("登录超时")
//                    sendBroadcastWithApiLoginTimeOut(method)
//                }
//                9 -> {
//                    Log.e(TAG, "强制退出")
//                    exitProcess(0)
//                }
//                else -> {
//                    var msg = "未知错误"
//                    if(json.has("msg")) msg = json.getString("msg")
//                    sendBroadcastWithApiRoutineError(method, msg)
//                    toashError(msg)
//                }
//            }
//
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    // 显示红底的 Toash
//    private fun toashError(msg: String) {
//        var handler = Handler(Looper.getMainLooper())
//        handler.post {
//            Toast.makeText(ContextUtils.getInstance() ,msg, Toast.LENGTH_LONG).show()
//        }
//    }
//
//    private fun sendBroadcastWithApiRoutineError(method: String, errMsg: String) {
//        Log.e(TAG, "--------------------> sendBroadcast: $errMsg")
//        // 发送广播通知API错误
//        val localBroadcastManager = LocalBroadcastManager.getInstance(ContextUtils.getInstance())
//        val intent = Intent()
//        intent.action = GlobalConst.BroadcastReceiveApiRoutineError
//        intent.putExtra("method", method)
//        intent.putExtra("msg", errMsg)
//        localBroadcastManager.sendBroadcast(intent)
//    }
//
//    private fun sendBroadcastWithApiLoginTimeOut(method: String, errMsg: String="登录超时") {
//        Log.w(TAG, "--------------------> sendBroadcast: $errMsg")
//        // 发送广播通知API错误
//        val localBroadcastManager = LocalBroadcastManager.getInstance(ContextUtils.getInstance())
//        val intent = Intent()
//        intent.action = GlobalConst.BroadcastReceiveApiLoginTimeOut
//        intent.putExtra("method", method)
//        intent.putExtra("msg", errMsg)
//        localBroadcastManager.sendBroadcast(intent)
//    }

}