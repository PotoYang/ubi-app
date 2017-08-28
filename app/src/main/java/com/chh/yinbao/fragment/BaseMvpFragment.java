package com.chh.yinbao.fragment;

import android.os.Bundle;

import com.chh.yinbao.presenter.BasePresenter;

/**
 * Created by potoyang on 2017/8/9.
 */

public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment {
    protected P presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
        presenter.attachView(null);
    }

    @Override
    public void onDestroyView() {
        presenter.detachView(false);
        super.onDestroyView();
    }

    public abstract P createPresenter();
}
