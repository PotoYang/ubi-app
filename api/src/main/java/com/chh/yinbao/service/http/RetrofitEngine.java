package com.chh.yinbao.service.http;

import com.chh.yinbao.config.MyURL;
import com.chh.yinbao.service.http.gson.GsonConverterFactory;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by potoyang on 2017/8/7.
 */

public class RetrofitEngine {
    private static Retrofit retrofit;

    /**
     * 获取动态代理类
     *
     * @return
     */
    public static synchronized Retrofit getInstance() {
        if (retrofit == null) {
            //声明日志类
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            //设定日志级别
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //自定义OkHttpClient
            OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
            //添加拦截器
            okHttpClient.addInterceptor(httpLoggingInterceptor);

            retrofit = new Retrofit.Builder().baseUrl(MyURL.BASE_HOST + MyURL.ISVR_HOST)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//引入RxJava支持:
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))//gson转换支持
                    .client(okHttpClient.build())
                    .build();
        }
        return retrofit;
    }

}
