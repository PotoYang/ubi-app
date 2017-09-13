package com.chh.yinbao.service.http;

import android.util.Log;

import com.chh.yinbao.config.MyURL;
import com.chh.yinbao.service.http.gson.GsonConverterFactory;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;

import static com.chh.yinbao.service.http.RetrofitEngine.LogInterceptor.genericClient;

//import static com.chh.yinbao.service.http.RetrofitEngine.LogInterceptor.genericClient;

//import retrofit2.converter.gson.GsonConverterFactory;

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
//            OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
            OkHttpClient okHttpClient = genericClient();
            //添加拦截器
//            okHttpClient.addInterceptor(httpLoggingInterceptor);
//            okHttpClient.addInterceptor(new LogInterceptor());

            retrofit = new Retrofit.Builder().baseUrl(MyURL.BASE_HOST + MyURL.ISVR_HOST)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//引入RxJava支持:
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))//gson转换支持
//                    .client(okHttpClient.build())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

    static class LogInterceptor implements Interceptor {
        private final String TAG = "OKHTTP";

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            MediaType m = request.body().contentType();

            Log.v(TAG, "request:" + request.toString() + " " + m);
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

        static OkHttpClient genericClient() {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request();
                            Request.Builder requestBuilder = request.newBuilder();
                            if (request.method().equals("GET")) {
                                request = requestBuilder.get()
                                        .build();
//                            System.out.println(request.body().contentType());
                                return chain.proceed(request);
                            } else {
                                request = requestBuilder.post(RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),
                                        URLDecoder.decode(bodyToString(request.body()), "UTF-8")))
                                        .build();
                                System.out.println("request: " + request.body().contentType());
                                return chain.proceed(request);
                            }
                        }
                    })
                    .addInterceptor(logging)
                    .build();
            return httpClient;
        }

        private static String bodyToString(final RequestBody request) {
            try {
                final RequestBody copy = request;
                final Buffer buffer = new Buffer();
                if (copy != null)
                    copy.writeTo(buffer);
                else
                    return "";
                return buffer.readUtf8();
            } catch (final IOException e) {
                return "did not work";
            }
        }
    }
}
