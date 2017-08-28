package com.chh.yinbao.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.chh.yinbao.config.Config;

/**
 * Created by potoyang on 2017/8/7.
 */

public class SharedPreferencesUtils {

    /**
     * 添加数据
     *
     * @param context
     * @param key
     * @param value
     */
    public static void addValue(Context context, String key, String value) {
        try {
            SharedPreferences sharedPrefs = context.getSharedPreferences(
                    Config.SHARED_APP_PREFERENCE_NAME, Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString(key, value);
            editor.commit();
        } catch (Exception e) {
        }
    }

    /**
     * 获取数据
     *
     * @param context
     * @param key
     * @return
     */
    public static String getValue(Context context, String key) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(
                Config.SHARED_APP_PREFERENCE_NAME, Activity.MODE_PRIVATE);
        String result = sharedPrefs.getString(key, null);

        return result;
    }

    /**
     * 添加加密数据
     *
     * @param key
     * @param value
     */
    public static void addEncryptValue(Context context, String key, String value) {
        try {
            SharedPreferences sharedPrefs = context.getSharedPreferences(
                    Config.SHARED_APP_PREFERENCE_NAME, Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString(key, SecurityUtils.encrypt(value));
            editor.commit();
        } catch (Exception e) {

        }
    }

    /**
     * 获取解密数据
     *
     * @param context
     * @param key
     * @return
     */
    public static String getDecryptValue(Context context, String key) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(
                Config.SHARED_APP_PREFERENCE_NAME, Activity.MODE_PRIVATE);
        String result = sharedPrefs.getString(key, null);
        try {
            result = SecurityUtils.decrypt(result);
        } catch (Exception e) {
            result = null;
        }
        return result;
    }

    /**
     * 移除数据
     *
     * @param context
     * @param key
     */
    public static void removeValue(Context context, String key) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(
                Config.SHARED_APP_PREFERENCE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.remove(key);
        editor.commit();
    }
}
