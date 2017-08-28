package com.chh.yinbao.service.http;

/**
 * Created by potoyang on 2017/8/7.
 */

public abstract class BaseCallBack<T> {

    public abstract void onSuccess(T data);

    public abstract void onError(int state, String message);

}
