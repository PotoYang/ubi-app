package com.chh.yinbao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chh.yinbao.R;

import butterknife.ButterKnife;

/**
 * Created by potoyang on 2017/8/9.
 */

public class PaybackPlanFragment extends BaseFragment {
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_payback_plan, container, false);
            ButterKnife.bind(this, rootView);
        }
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
