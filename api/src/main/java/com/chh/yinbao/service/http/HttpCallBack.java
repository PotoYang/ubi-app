package com.chh.yinbao.service.http;

import com.chh.yinbao.service.http.exception.ApiException;

import io.reactivex.observers.DefaultObserver;

/**
 * Created by potoyang on 2017/8/7.
 */

public abstract class HttpCallBack<T> extends DefaultObserver<T> {
    public abstract void onSuccess(T data);

    public abstract void onError(int state, String message);

    @Override
    public void onNext(T data) {
        onSuccess(data);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        if (throwable instanceof ApiException) {
            onError(((ApiException) throwable).getState(), throwable.getMessage());
        } else {
            onError(HttpStatusCode.SYSTEM_ERROR, HttpConstants.SYSTEM_ERROR);
        }
    }

    @Override
    public void onComplete() {

    }
}
