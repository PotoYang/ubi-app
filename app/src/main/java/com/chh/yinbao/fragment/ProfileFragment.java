package com.chh.yinbao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.chh.yinbao.config.ActivityURL;
import com.chh.yinbao.R;
import com.chh.yinbao.utils.ArouterUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by potoyang on 2017/8/9.
 */

public class ProfileFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private View rootView;

    @Bind(R.id.btnProfileUpdatePwd)
    Button btnProfileUpdatePwd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_profile, container, false);
            ButterKnife.bind(this, rootView);
        }
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onRefresh() {

    }

    @OnClick(R.id.btnProfileUpdatePwd)
    public void btnProfileUpdatePwdClick() {
        ArouterUtils.startActivity(ActivityURL.UpdatePwdActivity);
    }
}
