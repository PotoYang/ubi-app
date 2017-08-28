package com.chh.yinbao.presenter;

import com.chh.yinbao.view.IView;

/**
 * Created by potoyang on 2017/8/7.
 */

public interface IPresenter<V extends IView> {

    void attachView(V view);

    void detachView(boolean retainInstance);

}
