package com.chh.yinbao.util;

import android.view.View;

import com.androidadvance.topsnackbar.TSnackbar;

/**
 * Created by potoyang on 2017/8/7.
 */

public class TSnackbarUtils {
    public static void showTSnackbar(View view, String msg) {
        TSnackbar.make(view, msg, 1000).show();
    }

    public static void showTSnackbar(View view, String msg, String btnText, View.OnClickListener clickListener) {
        TSnackbar.make(view, msg, TSnackbar.LENGTH_SHORT).setAction(btnText, clickListener).show();
    }
}
