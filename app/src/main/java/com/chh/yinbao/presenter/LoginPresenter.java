package com.chh.yinbao.presenter;

import android.content.Context;

import com.chh.yinbao.Token;
import com.chh.yinbao.User;
import com.chh.yinbao.config.Config;
import com.chh.yinbao.config.UserData;
import com.chh.yinbao.service.account.AccountService;
import com.chh.yinbao.service.account.AccountServiceImpl;
import com.chh.yinbao.service.account.WeixinApi;
import com.chh.yinbao.service.http.HttpCallBack;
import com.chh.yinbao.view.LoginView;
import com.chh.yinbao.weixin.WXBaseInfo;
import com.chh.yinbao.weixin.WXUserInfo;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by potoyang on 2017/8/7.
 */

public class LoginPresenter extends BasePresenter {
    private LoginView userView;
    private AccountService accountService;
    private Context context;

    public LoginPresenter(Context context, LoginView userView) {
        this.userView = userView;
        this.context = context;
        accountService = new AccountServiceImpl(context);
    }

    public void userLogin(String mobile, String password, boolean isRememberPwd) {
        userView.showLoadDataDialog("正在登录");
        HttpCallBack<Token> callBack = new HttpCallBack<Token>() {
            @Override
            public void onSuccess(Token token) {
                userView.hideLoadDataDialog();
                getUserInfo();
//                userView.loginSuccess();
            }

            @Override
            public void onError(int state, String message) {
                userView.hideLoadDataDialog();
                userView.loginError(message);
            }
        };
        accountService.login(mobile, password, isRememberPwd, callBack);
        this.objReferenceList.add(callBack);
    }

    public void wxInfoBind(String unionId, String nickName, String headImgUrl) {
        userView.showLoadDataDialog("绑定中...");
        HttpCallBack<Token> callBack = new HttpCallBack<Token>() {
            @Override
            public void onSuccess(Token data) {
                userView.hideLoadDataDialog();
                User user = new User();
                user.setToken(data.getToken());
                userView.loginSuccess(user);
            }

            @Override
            public void onError(int state, String message) {
                userView.hideLoadDataDialog();
                userView.loginError(message);
            }
        };
        accountService.wxInfoBind(unionId, nickName, headImgUrl, callBack);
        this.objReferenceList.add(callBack);
    }

    private void getUserInfo() {
        final String token = UserData.getToken(context);
        HttpCallBack<User> callBack = new HttpCallBack<User>() {
            @Override
            public void onSuccess(User data) {
                data.setToken(token);
                userView.loginSuccess(data);
            }

            @Override
            public void onError(int state, String message) {
//                userView.loginSuccess(data);
            }
        };
        accountService.getUserInfo(token, callBack);
        this.objReferenceList.add(callBack);
    }

//    public void getBaseWXInfo(String code) {
//        final Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.weixin.qq.com/")
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create(new Gson()))
//                .build();
//
//        WeixinApi weixin = retrofit.create(WeixinApi.class);
//        Map<String, String> map = new HashMap<>();
//        map.put("appid", Config.WX_APP_ID);
//        map.put("secret", Config.WX_APP_SECRET);
//        map.put("code", code);
//        map.put("grant_type", "authorization_code");
//
//        HttpCallBack<WXBaseInfo> callBack = new HttpCallBack<WXBaseInfo>() {
//            @Override
//            public void onSuccess(WXBaseInfo data) {
//                userView.hideLoadDataDialog();
//                getWXUserInfo(data.getAccess_token(), data.getOpenid(), retrofit);
//            }
//
//            @Override
//            public void onError(int state, String message) {
//                userView.hideLoadDataDialog();
//                userView.loginError(message);
//            }
//        };
//
//        weixin.getWXBaseInfo(map)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(callBack);
//
//        this.objReferenceList.add(callBack);
//    }

    public void getBaseWXInfo(String code) {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weixin.qq.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

        WeixinApi weixin = retrofit.create(WeixinApi.class);

        Map<String, String> map = new HashMap<>();
        map.put("appid", Config.WX_APP_ID);
        map.put("secret", Config.WX_APP_SECRET);
        map.put("code", code);
        map.put("grant_type", "authorization_code");

        HttpCallBack<WXBaseInfo> callBack = new HttpCallBack<WXBaseInfo>() {
            @Override
            public void onSuccess(WXBaseInfo data) {
                userView.hideLoadDataDialog();
                getWXUserInfo(data.getAccess_token(), data.getOpenid(), retrofit);
            }

            @Override
            public void onError(int state, String message) {
                userView.hideLoadDataDialog();
                userView.loginError(message);
            }
        };

        weixin.getWXBaseInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);

        this.objReferenceList.add(callBack);
    }

    private void getWXUserInfo(String access_token, String openid, Retrofit retrofit) {
        WeixinApi wx = retrofit.create(WeixinApi.class);
        Map<String, String> map = new HashMap<>();
        map.put("access_token", access_token);
        map.put("openid", openid);

        HttpCallBack<WXUserInfo> callBack = new HttpCallBack<WXUserInfo>() {
            @Override
            public void onSuccess(WXUserInfo data) {
                User user = new User();
                user.setWeChat(true);
                user.setName(data.getUnionid());
                user.setPic(data.getHeadimgurl());
                user.setWeixinNickName(data.getNickname());
                userView.loginSuccess(user);
            }

            @Override
            public void onError(int state, String message) {
//                userView.loginError(message);
            }
        };

        wx.getWXUserInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);

        this.objReferenceList.add(callBack);
    }
}
