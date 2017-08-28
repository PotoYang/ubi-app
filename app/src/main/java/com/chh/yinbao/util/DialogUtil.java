package com.chh.yinbao.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.TextView;

import com.chh.yinbao.R;

/**
 * Created by potoyang on 2017/8/7.
 */

public class DialogUtil {

    private static final String TAG = DialogUtil.class.getSimpleName();

    public static Dialog createLoadingDialog(Context context, String msg) {
        AlertDialog myDialog = new AlertDialog.Builder(context).create();
        myDialog.show();
        myDialog.setCanceledOnTouchOutside(false);
        Window window = myDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setContentView(R.layout.progress_bar);
        if (!TextUtils.isEmpty(msg)) {
            TextView tipTextView = (TextView) window
                    .findViewById(R.id.tvLoadingMsg);
            tipTextView.setText(msg);
        }
        return myDialog;
    }

    public static Dialog cancelFinishActivity(final Dialog dialog) {
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    dialog.dismiss();
                    return true;
                }
                return false;
            }
        });
        return dialog;
    }

}
