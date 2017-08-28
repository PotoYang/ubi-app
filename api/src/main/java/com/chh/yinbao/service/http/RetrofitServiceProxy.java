package com.chh.yinbao.service.http;

import java.lang.reflect.Proxy;

import retrofit2.Retrofit;

/**
 * Created by potoyang on 2017/8/7.
 */

public class RetrofitServiceProxy {
    private Retrofit retrofit;

    private RetrofitProxyHandler retrofitProxyHandler;

    public RetrofitServiceProxy(Retrofit retrofit, RetrofitProxyHandler proxyHandler) {
        this.retrofit = retrofit;
        if (retrofit == null) {
            this.retrofit = RetrofitEngine.getInstance();
        }
        retrofitProxyHandler = proxyHandler;
    }

    public <T> T getProxy(Class<T> tClass) {
        T t = retrofit.create(tClass);
        retrofitProxyHandler.setObject(t);
        return (T) Proxy.newProxyInstance(tClass.getClassLoader(), new Class<?>[]{tClass}, retrofitProxyHandler);
    }

}
