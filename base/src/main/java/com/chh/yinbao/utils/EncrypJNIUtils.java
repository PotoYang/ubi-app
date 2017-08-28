package com.chh.yinbao.utils;

/**
 * Created by potoyang on 2017/8/7.
 */

public class EncrypJNIUtils {
    static
    {
        System.loadLibrary("encrypt_key");
    }
    public native static String getKey();

}
