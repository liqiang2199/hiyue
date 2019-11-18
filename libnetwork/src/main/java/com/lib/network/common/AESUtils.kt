package com.lib.network.common

import android.os.Build
import androidx.annotation.RequiresApi
import java.nio.charset.Charset
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object AESUtils {
    // 加密
    @RequiresApi(Build.VERSION_CODES.O)
    @Throws(Exception::class)
    fun Encrypt(sSrc: String, sKey: String?): String? {
        if (sKey == null) {
            print("Key为空null")
            return null
        }
        // 判断Key是否为16位
        if (sKey.length != 16) {
            print("Key长度不是16位")
            return null
        }
        val raw = sKey.toByteArray(charset("utf-8"))
        val skeySpec = SecretKeySpec(raw, "AES")
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec)
        val encrypted = cipher.doFinal(sSrc.toByteArray(charset("utf-8")))

        return Base64.getEncoder().encodeToString(encrypted)//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    // 解密
    @RequiresApi(Build.VERSION_CODES.O)
    @Throws(Exception::class)
    fun Decrypt(sSrc: String, sKey: String?): String? {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                print("Key为空null")
                return null
            }
            // 判断Key是否为16位
            if (sKey.length != 16) {
                print("Key长度不是16位")
                return null
            }
            val raw = sKey.toByteArray(charset("utf-8"))
            val skeySpec = SecretKeySpec(raw, "AES")
            val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
            cipher.init(Cipher.DECRYPT_MODE, skeySpec)
            val encrypted1 = Base64.getDecoder().decode(sSrc)//先用base64解密
            try {
                val original = cipher.doFinal(encrypted1)
                return String(original, Charset.defaultCharset())
            } catch (e: Exception) {
                println(e.toString())
                return null
            }

        } catch (ex: Exception) {
            println(ex.toString())
            return null
        }

    }

}