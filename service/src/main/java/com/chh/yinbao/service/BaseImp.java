package com.chh.yinbao.service;

import com.chh.yinbao.service.http.HttpCallBack;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by potoyang on 2017/8/7.
 */

public class BaseImp {
    public <T> void doRequest(Observable<T> observable, HttpCallBack<T> callBack) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }
}
