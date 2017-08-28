package com.chh.yinbao.utils;

import android.app.Activity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by potoyang on 2017/8/7.
 */

public class ArouterUtils {
    public static void startActivity(String path) {
        startActivity(null, path);
    }

    public static void startActivity(Bundle bundle, String path) {
        ARouter.getInstance().build(path).with(bundle).navigation();
    }

    public static void startActivity(String path, Activity context, NavigationCallback callback) {
        startActivity(null, path, -1, context, callback);
    }

    public static void startActivity(Bundle bundle, String path, int requestCode, Activity context, NavigationCallback callback) {
        if (requestCode == -1) {
            ARouter.getInstance().build(path).with(bundle).navigation(context, callback);
        } else {
            ARouter.getInstance().build(path).with(bundle).navigation(context, requestCode, callback);
        }
    }
}
