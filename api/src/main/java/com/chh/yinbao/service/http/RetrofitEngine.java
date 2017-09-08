package com.chh.yinbao.service.http;

import android.util.Log;

import com.chh.yinbao.config.MyURL;
import com.chh.yinbao.service.http.gson.GsonConverterFactory;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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
//            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//            HttpLoggingInterceptor httpLoggingInterceptor = new LogInterceptor();
            //设定日志级别
//            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //自定义OkHttpClient
            OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
            //添加拦截器
//            okHttpClient.addInterceptor(httpLoggingInterceptor);
            okHttpClient.addInterceptor(new LogInterceptor());

            retrofit = new Retrofit.Builder().baseUrl(MyURL.BASE_HOST + MyURL.ISVR_HOST)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//引入RxJava支持:
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))//gson转换支持
                    .client(okHttpClient.build())
                    .build();
        }
        return retrofit;
    }

    private static class LogInterceptor implements Interceptor {
        private final String TAG = "OKHTTP";

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Log.v(TAG, "request:" + request.toString());
            long t1 = System.nanoTime();
            okhttp3.Response response = chain.proceed(chain.request());
            long t2 = System.nanoTime();
            Log.v(TAG, String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            okhttp3.MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            Log.i(TAG, "response body:" + content + "  " + mediaType);
            return response.newBuilder()
                    .body(okhttp3.ResponseBody.create(mediaType, content))
                    .build();
        }
    }
}
