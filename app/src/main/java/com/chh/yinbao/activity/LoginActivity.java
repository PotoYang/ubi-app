package com.chh.yinbao.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.utils.TextUtils;
import com.chh.yinbao.FirstApplication;
import com.chh.yinbao.R;
import com.chh.yinbao.User;
import com.chh.yinbao.config.ActivityURL;
import com.chh.yinbao.config.UserData;
import com.chh.yinbao.presenter.LoginPresenter;
import com.chh.yinbao.util.TSnackbarUtils;
import com.chh.yinbao.utils.AppManager;
import com.chh.yinbao.utils.ArouterUtils;
import com.chh.yinbao.utils.LogUtils;
import com.chh.yinbao.utils.SharedPreferencesUtils;
import com.chh.yinbao.view.LoginView;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by potoyang on 2017/8/7.
 */

@Route(path = ActivityURL.LoginActivity)
public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginView {
    public static final String TAG = LoginActivity.class.getSimpleName();

    @Bind(R.id.etLoginMobile)
    EditText etLoginMobile;
    @Bind(R.id.etLoginPassword)
    EditText etLoginPassword;
    @Bind(R.id.btnLogin)
    Button btnLogin;
    @Bind(R.id.rememberPwd)
    CheckBox rememberPwd;

    private LoginPresenter userPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        showExitLayout(getApplicationContext());
        init();
    }

    private void init() {
        setToolBarLeftText(getString(R.string.exit));
        setToolbarTitleText(getString(R.string.login_title));
        setToolBarRightText(getString(R.string.login_register), ActivityURL.RegisterActivity);
        Bundle bundle = getIntent().getExtras();
        String code = bundle.getString("code");
        if (code != null) {
            showProgressDialog("微信信息传递中...");
            userPresenter.getBaseWXInfo(code);
        }

        String mobile = SharedPreferencesUtils.getDecryptValue(getApplicationContext(), UserData.USER_NAME);
        if (!TextUtils.isEmpty(mobile)) {
            etLoginMobile.setText(mobile);
        }
    }

    public void loginClick(View view) {
//        ArouterUtils.startActivity(ActivityURL.MainActivity);
        userPresenter.userLogin(etLoginMobile.getText().toString().trim(), etLoginPassword.getText().toString().trim(), rememberPwd.isChecked());
    }

    public void registerClick(View view) {
        ArouterUtils.startActivity(ActivityURL.RegisterActivity);
    }

    public void findPwdClick(View view) {
        ArouterUtils.startActivity(ActivityURL.FindPwdActivity);
    }

    @OnClick(R.id.ivWechat)
    public void weChatClick() {
        showLoadDataDialog("");
        if (!FirstApplication.iwxapi.isWXAppInstalled()) {
            TSnackbarUtils.showTSnackbar(btnLogin, "您还未安装微信客户端");
            hideLoadDataDialog();
            return;
        }
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_first_test";
        FirstApplication.iwxapi.sendReq(req);
        finish();
//        Final SendAuth.Req req = new SendAuth.Req();
//        req.scope = "snsapi_userinfo";
//        req.state = "wechat_sdk_demo_test";
//        api.sendReq(req);
    }

    @Override
    public void showLoadDataDialog(String msg) {
        showProgressDialog(msg);
    }

    @Override
    public void hideLoadDataDialog() {
        hideDialog();
    }

    @Override
    public void loginSuccess(final User data) {
        System.out.println(data.getToken() + " " + data.getName() + " " + data.getWeixinNickName() + " " + data.getPic());
        if (data.getIdCard() == null || data.getCarNo() == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            if (data.isWeChat()) {
                builder.setTitle("微信登录成功，需进行信息绑定");
                builder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userPresenter.wxInfoBind(data.getName(), data.getWeixinNickName(), data.getPic());
                    }
                });
                builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setCancelable(false);
                builder.create();
                builder.show();
            } else {
                builder.setTitle("个人信息不完整，继续完善个人信息");
                builder.setPositiveButton(getString(R.string.continue_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userData", data);
                        ArouterUtils.startActivity(bundle, ActivityURL.BinInfoActivity);
                    }
                });
                builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setCancelable(false);
                builder.create();
                builder.show();
            }

        } else {
            Bundle bundle = new Bundle();
            bundle.putString("token", data.getToken());
            ArouterUtils.startActivity(bundle, ActivityURL.LoadActivity);
//            ArouterUtils.startActivity(bundle, ActivityURL.MainActivity);
            finishWithAnim();
        }
    }

    @Override
    public void loginError(String msg) {
        TSnackbarUtils.showTSnackbar(btnLogin, "登录失败:" + msg);
        LogUtils.i(TAG, "登录失败:" + msg);
    }

    @Override
    public LoginPresenter createPresenter() {
        userPresenter = new LoginPresenter(this, this);
        return userPresenter;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AppManager.getAppManager().finishAllActivity();
        }
        return super.onKeyDown(keyCode, event);
    }
}
