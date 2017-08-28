package com.chh.yinbao.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by potoyang on 2017/8/7.
 */

public class ActivityUtils {

    public static void openActivityByView(Activity context, Class<?> pClass, View view) {
        openActivityByView(context, pClass, view, null);
    }

    /**
     * 工具类打开acitvity,并使用 工具类打开acitvity,并使用动画动画
     */
    public static void openActivityByView(Activity context, Class<?> pClass, View view, Bundle pBundle) {
        Intent intent = new Intent(context, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
//        让新的Activity从一个小的范围扩大到全屏
        ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(view, (int) view.getX(), (int) view.getY(), view.getWidth() / 2, view.getHeight() / 2);
        ActivityCompat.startActivity(context, intent, options.toBundle());
    }


    public static void openActivityResultByFragment(Fragment context, Class<?> pClass, Bundle pBundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(context.getActivity(), pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        context.startActivityForResult(intent, requestCode);
    }

    public static void openActivityResult(Activity context, Class<?> pClass, Bundle pBundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(context, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        context.startActivityForResult(intent, requestCode);
    }

    public static void openActivityByFragment(Fragment context, Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent();
        intent.setClass(context.getActivity(), pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        context.startActivity(intent);
    }

    /**
     * 退出APP
     */
    public static void exitApp(Context context) {
        AppManager.getAppManager().AppExit(context);
    }

    public static void finishAllActivity(Context context) {
        AppManager.getAppManager().finishAllActivity();
    }


    public static void openActivity(Activity activity, Class mClass) {
        Intent intent = new Intent(activity, mClass);
        activity.startActivity(intent);
    }

    public static void openActivity(Context activity, Class mClass, Bundle pBundle) {
        Intent intent = new Intent(activity, mClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    public static void openActivityInService(Context activity, Class mClass) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(activity, mClass);
        activity.startActivity(intent);
    }

}
