package com.chh.yinbao.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chh.yinbao.R;

/**
 * Created by potoyang on 2017/8/7.
 */

public class MyToast {
    public static void show(Context context, String text) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.my_toast, null);
        if (!TextUtils.isEmpty(text)) {
            TextView toastMsg = (TextView) view.findViewById(R.id.toastMsg);
            toastMsg.setText(text);
        }
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(view);
        toast.show();
    }

    public static void show(Context context, int resourceId) {
        String msg = context.getResources().getString(resourceId);
        show(context, msg);
    }
}
