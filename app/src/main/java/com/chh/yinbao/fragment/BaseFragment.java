package com.chh.yinbao.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.chh.yinbao.R;
import com.chh.yinbao.activity.BaseActivity;

import java.util.ArrayList;

/**
 * Created by potoyang on 2017/8/9.
 */

public class BaseFragment extends Fragment {
    public static final String OBJ_KEY = "Obj_key";
    protected BaseActivity activity;

    protected int pageNow;

    protected int pageSize = 20;

    private Bundle bundle;
    private Toolbar mToolbar;

    /**
     * 显示或者隐藏返回键
     */
    public void showGoBackLayout(boolean isShow) {
        if (!setupActionbar()) return;
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(isShow);
            activity.getSupportActionBar().setHomeButtonEnabled(isShow);
            View view = activity.findViewById(R.id.goBackLayout);
            if (view != null) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goBack();
                    }
                });
            }
        }
    }

    public void setToolBarRightButtnText(String text, int resId) {
        TextView rightText = (TextView) mToolbar.findViewById(R.id.textToolBarRight);
        if (rightText == null) {
            return;
        }
        rightText.setVisibility(View.VISIBLE);
        rightText.setText(text);
        rightText.setBackgroundResource(resId);
    }

    public View getToolBarRightBtn() {
        return mToolbar.findViewById(R.id.textToolBarRight);
    }

    public boolean setupActionbar() {
        if (mToolbar == null) {
            mToolbar = (Toolbar) activity.findViewById(R.id.toolBar);
            if (mToolbar != null) {
                mToolbar.setTitle("");
                activity.setSupportActionBar(mToolbar);
            }
        }
        if (activity.getSupportActionBar() == null) {
            return false;
        }
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        return true;
    }

    public void setActionbarTitleText(String title) {
        if (!setupActionbar()) return;
        TextView titleText = (TextView) mToolbar.findViewById(R.id.textToolBarTitle);
        titleText.setText(title);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            this.activity = (BaseActivity) context;
        }
    }

    public boolean isEnd(ArrayList data, int pageNo, int pageSize) {
        if (data != null) {
            int count = data.size();
            if (count != (pageNo + 1) * pageSize) {
                return true;
            }
        }
        return false;
    }

    public void requestData() {
        requestData(null);
    }

    public void requestData(Object obj) {
    }

    public void show(Object obj) {

    }

    public void hideDialog() {
        activity.hideDialog();
    }

    public void showProgressDialog() {
        activity.showProgressDialog("");
    }

    public void showProgressDialog(String msg) {
        activity.showProgressDialog(msg);
    }

    public String getMyTag() {
        return this.getClass().getSimpleName();
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    protected void goBack() {
//        hideDialog();
        activity.finishWithAnim();
    }
}
