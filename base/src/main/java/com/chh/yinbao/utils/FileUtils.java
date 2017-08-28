package com.chh.yinbao.utils;

import android.content.Context;
import android.os.Environment;

import com.chh.yinbao.config.Config;

import java.io.File;

/**
 * Created by potoyang on 2017/8/7.
 */

public class FileUtils {


    /**
     * 创建目录
     *
     * @param path
     */
    public static void createDirs(File path) {
        if (path != null && !path.exists()) {
            path.mkdirs();
        }
    }

    public static void createDirs(String path) {
        File file = new File(path);
        createDirs(file);
    }


    /**
     * 文件是否存在
     *
     * @param file
     * @return
     */
    public static boolean isFileExist(File file) {
        if (file != null && file.exists()) {
            return true;
        }
        return false;
    }

    /**
     * �?��sdcard是否可用
     *
     * @return true为可用，否则为不可用
     */
    public static boolean sdCardIsAvailable() {
        String status = Environment.getExternalStorageState();
        if (!status.equals(Environment.MEDIA_MOUNTED))
            return false;
        return true;
    }


    /**
     * 创建app使用的目录,如果内存卡不可用，则使用内存路径
     *
     * @param context
     * @return
     */
    public static String createAppDir(Context context) {
        if (sdCardIsAvailable()) {
            File file = new File(Environment.getExternalStorageDirectory(), Config.SHARED_APP_DIR_NAME);
            if (!isFileExist(file)) {
                createDirs(file);
            }
            return file.getAbsolutePath();
        } else {//如果内存卡不可用
            return context.getCacheDir().getAbsolutePath();
        }
    }


    public static String createAppDownloadDir(Context context, String dirName) {
        String appDir = createAppDir(context);
        if (sdCardIsAvailable()) {
            File file = new File(appDir, dirName);
            if (!isFileExist(file)) {
                createDirs(file);
            }
            return file.getAbsolutePath();
        } else {//如果内存卡不可用
            return context.getCacheDir().getAbsolutePath();
        }
    }

    public static String getSDCardPath() {
        if (sdCardIsAvailable()) {
            File file = Environment.getExternalStorageDirectory();
            return file.getAbsolutePath();
        }
        return null;

    }


}
