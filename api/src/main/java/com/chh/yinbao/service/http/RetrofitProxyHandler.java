package com.chh.yinbao.service.http;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.chh.yinbao.TokenList;
import com.chh.yinbao.config.ActivityURL;
import com.chh.yinbao.config.UserData;
import com.chh.yinbao.service.account.AccountApi;
import com.chh.yinbao.service.http.exception.ApiException;
import com.chh.yinbao.service.http.exception.TokenConflictException;
import com.chh.yinbao.service.http.exception.TokenInvalidException;
import com.chh.yinbao.utils.ActivityUtils;
import com.chh.yinbao.utils.ArouterUtils;
import com.chh.yinbao.utils.LogUtils;
import com.chh.yinbao.utils.SharedPreferencesUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.observers.DefaultObserver;

/**
 * Created by potoyang on 2017/8/7.
 */

public class RetrofitProxyHandler implements InvocationHandler {
    public static final String TAG = RetrofitProxyHandler.class.getSimpleName();
    private Object mObject;
    private boolean mIsTokenNeedRefresh = false;
    private Throwable mRefreshTokenError = null;
    private Context context;

    public RetrofitProxyHandler(Context context) {
        this.context = context;
    }

    public void setObject(Object obj) {
        this.mObject = obj;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        Object result = null;
        result = Observable.just("1")
                .flatMap(new Function<Object, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Object o) throws Exception {
                        //这里判断是否需要更改token，更换完成之后继续执行之前的方法。
                        try {
                            if (mIsTokenNeedRefresh) {
                                String newToken = SharedPreferencesUtils.getValue(context, TokenHelper.ACCESS_TOKEN_KEY);
                                LogUtils.i(TAG, "更新token：" + newToken);
                                TokenHelper.updateTokenParamasByBodyRequest(newToken, method, args);
                                mIsTokenNeedRefresh = false;
                            }
                            return (Observable<?>) method.invoke(mObject, args);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return Observable.just(new ApiException(-100, "method call error"));
                    }
                })
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Observable<Throwable> throwableObservable) throws Exception {
                        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
                                if (throwable instanceof TokenInvalidException) {
                                    //token 过期,执行请求刷新
                                    return refreshTokenWhenTokenInvalid();
                                } else if (throwable instanceof TokenConflictException) {
                                    //token冲突异常,执行退出登录的操作。
                                    LogUtils.i(TAG, "token冲突");
                                    UserData.cleanUserData(context);
                                    UserData.cleanAppData(context);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("msg", "该账号已在其他地方登陆");
                                    ActivityUtils.finishAllActivity(context);
                                    ArouterUtils.startActivity(bundle, ActivityURL.LoginActivity);
                                    return Observable.error(throwable);
                                }
                                return Observable.error(throwable);
                            }
                        });
                    }
                });
        return result;
    }

    private Observable<?> refreshTokenWhenTokenInvalid() {
        mIsTokenNeedRefresh = true;
        Map<String, String> map = new HashMap<>();
        String token = SharedPreferencesUtils.getValue(context, TokenHelper.ACCESS_TOKEN_KEY);
//        String accountId = SharedPreferencesUtils.getValue(context, "accountId");
//        map.put("accountId", accountId);
        map.put("token", token);
        LogUtils.i(TAG, "refreshTokenWhenTokenInvalid");
        RetrofitEngine.getInstance().create(AccountApi.class).refreshToken(map).subscribe(new DefaultObserver<TokenList>() {
            @Override
            public void onNext(TokenList tokenList) {
                if (tokenList.getAccount() != null) {
                    String newToken = tokenList.getAccount().getToken();
                    //保存token
                    SharedPreferencesUtils.addValue(context, TokenHelper.ACCESS_TOKEN_KEY, newToken);
                } else {
                    onError(new TokenConflictException());
                }
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }
        });
        if (mRefreshTokenError != null) {
            return Observable.error(mRefreshTokenError);
        } else {
            return Observable.just(true);
        }
    }
}
