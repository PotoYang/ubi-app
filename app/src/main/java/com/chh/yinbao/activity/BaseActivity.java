package com.chh.yinbao.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.chh.yinbao.R;
import com.chh.yinbao.utils.AppManager;
import com.chh.yinbao.utils.ArouterUtils;
import com.chh.yinbao.util.DialogUtil;

/**
 * Created by potoyang on 2017/8/7.
 */

public class BaseActivity extends AppCompatActivity {
    private final static String TAG = BaseActivity.class.getSimpleName();
    private Toolbar toolbar;
    private static Dialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
    }

    public void showExitLayout(final Context context) {
        if (!setupActionbar()) return;
        if (getSupportActionBar() != null) {
            View view = findViewById(R.id.goBackLayout);
            if (view != null) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppManager.getAppManager().AppExit(context);
                        overridePendingTransition(android.R.anim.fade_in,
                                android.R.anim.fade_out);

                    }
                });
            }
        }
    }

    public void hideGoBackLayout() {
        if (!setupActionbar()) return;
        if (getSupportActionBar() != null) {
            View view = findViewById(R.id.goBackLayout);
            if (view != null) {
                view.setVisibility(View.GONE);
            }
        }
    }

    public void showGoBackLayout() {
        if (!setupActionbar()) return;
        if (getSupportActionBar() != null) {
            View view = findViewById(R.id.goBackLayout);
            if (view != null) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goBackLayout();
                    }
                });
            }
        }
    }

    public boolean setupActionbar() {
        if (toolbar == null) {
            toolbar = (Toolbar) findViewById(R.id.customToolBar);
            if (toolbar != null) {
                toolbar.setTitle("");
                setSupportActionBar(toolbar);
            }
        }
        if (getSupportActionBar() == null) {
            return false;
        }
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        return true;
    }

    public void setToolBarLeftText(String text) {
        TextView leftText = (TextView) toolbar.findViewById(R.id.textToolBarLeft);
        if (leftText == null) {
            return;
        }
        leftText.setVisibility(View.VISIBLE);
        leftText.setText(text);
    }

    public void setToolBarRightText(String text, final String path) {
        TextView rightText = (TextView) toolbar.findViewById(R.id.textToolBarRight);
        if (rightText == null) {
            return;
        }
        rightText.setVisibility(View.VISIBLE);
        rightText.setText(text);
        if (path != null) {
            rightText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArouterUtils.startActivity(path);
                }
            });
        } else {
            rightText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finishWithAnim();
                }
            });
        }
    }

    public void setToolbarTitleText(String title) {
        if (!setupActionbar()) return;
        TextView titleText = (TextView) toolbar.findViewById(R.id.textToolBarTitle);
        titleText.setText(title);
    }

    public void showProgressDialog() {
        showProgressDialog("");
    }

    /**
     * 显示dialog
     *
     * @param msg 信息提示
     */
    public void showProgressDialog(String msg) {
        hideDialog();
        try {
            if (progressDialog != null) {
                progressDialog = null;
            }
            progressDialog = DialogUtil.createLoadingDialog(this, msg);
            progressDialog.setOnKeyListener(null);
            progressDialog.show();
        } catch (Exception ignored) {

        }
    }

    public void hideDialog() {
        hideProgressDialog();
    }

    /**
     * 隐藏dialog
     */
    public void hideProgressDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception ignored) {

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {// 如果点击的是返回按钮
            goBackLayout();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goBackLayout();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void goBackLayout() {
//        hideDialog();
        finishWithAnim();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 带动画的结束当前activity
     */
    public void finishWithAnim() {
        finish();
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }
}
