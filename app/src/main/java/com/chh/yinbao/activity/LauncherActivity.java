package com.chh.yinbao.activity;

import android.os.Bundle;
import android.text.TextUtils;

import com.chh.yinbao.Token;
import com.chh.yinbao.config.ActivityURL;
import com.chh.yinbao.config.UserData;
import com.chh.yinbao.service.account.AccountService;
import com.chh.yinbao.service.account.AccountServiceImpl;
import com.chh.yinbao.service.http.HttpCallBack;
import com.chh.yinbao.utils.ArouterUtils;
import com.chh.yinbao.utils.SharedPreferencesUtils;

import butterknife.ButterKnife;

/**
 * Created by potoyang on 2017/8/10.
 */

public class LauncherActivity extends BaseActivity {
    private final String TAG = LauncherActivity.class.getSimpleName();
    AccountService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        init();
    }

    private void init() {
        userService = new AccountServiceImpl(getApplicationContext());
        UserData.cleanAppData(getApplicationContext());
        initApp();
    }

    private void initApp() {
//        requestCheckAppVersion();
        login();
    }

    private void login() {
        String username = SharedPreferencesUtils.getDecryptValue(getApplicationContext(), UserData.USER_NAME);
        String password = SharedPreferencesUtils.getDecryptValue(getApplicationContext(), UserData.USER_PWD);
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            user_login(username, password);
        } else {
//            toLoginActivity();
            ArouterUtils.startActivity(ActivityURL.LoginActivity);
        }
    }

    private void user_login(String username, String password) {
        HttpCallBack<Token> callBack = new HttpCallBack<Token>() {
            @Override
            public void onSuccess(Token data) {
                toHomeActivity(data.getToken());
            }

            @Override
            public void onError(int state, String message) {
                ArouterUtils.startActivity(ActivityURL.LoginActivity);
            }
        };
        userService.login(username, password, true, callBack);
    }

    private void toHomeActivity(String token) {
        Bundle bundle = new Bundle();
        bundle.putString("token", token);
        ArouterUtils.startActivity(bundle, ActivityURL.LoadActivity);
    }

}
