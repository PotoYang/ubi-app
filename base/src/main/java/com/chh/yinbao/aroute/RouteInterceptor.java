package com.chh.yinbao.aroute;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.chh.yinbao.utils.LogUtils;

/**
 * Created by potoyang on 2017/8/7.
 */
@Interceptor(priority = 666, name = "登陆拦截器")
public class RouteInterceptor implements IInterceptor {
    @Override
    public void process(Postcard postcard, InterceptorCallback interceptorCallback) {
        LogUtils.i("RouteInterceptor", "process------->>>");
        interceptorCallback.onContinue(postcard);  // 处理完成，交还控制权
        // callback.onInterrupt(new RuntimeException("我觉得有点异常"));
    }

    @Override
    public void init(Context context) {
        LogUtils.i("RouteInterceptor", "init------->>>");
    }
}
