package com.chh.yinbao.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chh.yinbao.config.ActivityURL;
import com.chh.yinbao.R;
import com.chh.yinbao.util.MyToast;
import com.chh.yinbao.service.account.AccountService;
import com.chh.yinbao.service.account.AccountServiceImpl;
import com.chh.yinbao.service.http.HttpCallBack;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by potoyang on 2017/8/14.
 */

@Route(path = ActivityURL.ResetPwdActivity)
public class ResetPwdActivity extends BaseActivity {

    private static String TAG = ResetPwdActivity.class.getSimpleName();

    public static final String SMSCODE_KEY = "smsCode_key";
    public static final String LOGINNAME_KEY = "loginName_key";

    @Bind(R.id.etResetPwd)
    EditText etResetPwd;
    @Bind(R.id.etResetPwdAgain)
    EditText etResetPwdAgain;

    private AccountService accountService;

    private String smsCode;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpwd);
        ButterKnife.bind(this);
        showGoBackLayout();
        init();
    }

    private void init() {
        setToolbarTitleText(getString(R.string.reset_pwd_title));
        accountService = new AccountServiceImpl(getApplicationContext());
        Bundle bundle = getIntent().getExtras();
        smsCode = bundle.getString(SMSCODE_KEY);
        userName = bundle.getString(LOGINNAME_KEY);
        if (TextUtils.isEmpty(smsCode) || TextUtils.isEmpty(userName)) {
            goBackLayout();
        }
    }

    public void resetClick(View views) {
        String newPwd = etResetPwd.getText().toString().trim();
        String newPwdAgain = etResetPwdAgain.getText().toString().trim();

        showProgressDialog("");
        HttpCallBack<Object> callBack = new HttpCallBack<Object>() {
            @Override
            public void onSuccess(Object data) {
                hideDialog();
                MyToast.show(getApplicationContext(), getString(R.string.reset_pwd_success));
                goBackLayout();
            }

            @Override
            public void onError(int state, String message) {
                hideDialog();
                MyToast.show(getApplicationContext(), message);
            }
        };
        accountService.resetPwd(userName, newPwd, newPwdAgain, callBack);
    }
}
