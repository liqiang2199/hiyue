package com.hiyue.provider.utils;

public class StringUtil {

    /*
     * 字节数组转16进制字符串
     */
    public static String bytes2HexString(byte[] b) {
        String r = "";

        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            r += hex.toUpperCase();
        }

        return r;
    }

    /**
     * 返回签名需要字符下标
     * @param str
     * @param index
     * @param subIndexLength
     * @return
     */
    public static int substringUtil(String str,int index,int subIndexLength) {
        return Integer.valueOf(str.substring(index,index+subIndexLength),16);
    }
}
