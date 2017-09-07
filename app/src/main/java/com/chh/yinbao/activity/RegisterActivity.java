package com.chh.yinbao.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chh.yinbao.R;
import com.chh.yinbao.Token;
import com.chh.yinbao.config.ActivityURL;
import com.chh.yinbao.config.UserData;
import com.chh.yinbao.service.account.AccountService;
import com.chh.yinbao.service.account.AccountServiceImpl;
import com.chh.yinbao.service.http.HttpCallBack;
import com.chh.yinbao.util.MyToast;
import com.chh.yinbao.util.TSnackbarUtils;
import com.chh.yinbao.utils.ArouterUtils;
import com.chh.yinbao.utils.SharedPreferencesUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by potoyang on 2017/8/8.
 */

@Route(path = ActivityURL.RegisterActivity)
public class RegisterActivity extends BaseActivity {
    private final String TAG = RegisterActivity.class.getSimpleName();
    private AccountService accountService;

    @Bind(R.id.etRegisterMobile)
    EditText etRegisterMobile;
    @Bind(R.id.etRegisterCheckCode)
    EditText etRegisterCheckCode;
    @Bind(R.id.etRegisterPassword1)
    EditText etRegisterPassword1;
    @Bind(R.id.etRegisterPassword2)
    EditText etRegisterPassword2;

    @Bind(R.id.btnRegisterGetCheckCode)
    Button btnRegisterGetCheckCode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        showGoBackLayout();
        init();
    }

    private void init() {
        setToolbarTitleText(getString(R.string.title_register));
        setToolBarRightText(getString(R.string.do_login), null);
        accountService = new AccountServiceImpl(getApplicationContext());
        refreshBtnGetCheckCodeState();
    }

    private void refreshBtnGetCheckCodeState() {
        long firstTime = 0;
        try {
            firstTime = Long.parseLong(SharedPreferencesUtils.getValue(getApplicationContext(), UserData.GET_CHECKCODE_TIME));
        } catch (Exception ignored) {
        }
        long nowTime = System.currentTimeMillis();
        final long diff = (nowTime - firstTime) / 1000;
        if (diff < 119) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    long index = 119 - diff;
                    while (index > 0) {
                        try {
                            refreshCheckcodeBtn(index);
                            Thread.sleep(1000);
                            index--;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    refreshCheckcodeBtn(0);
                }
            }).start();
        }
    }

    @UiThread
    protected void refreshCheckcodeBtn(final long second) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (second > 0) {
                    String str = String.format(getString(R.string.number_enable_checkcode), second + "");
                    btnRegisterGetCheckCode.setEnabled(false);
                    btnRegisterGetCheckCode.setText(str);
                } else {
                    btnRegisterGetCheckCode.setEnabled(true);
                    btnRegisterGetCheckCode.setText(R.string.get_checkcode_hint);
                }
            }
        });
    }

    /**
     * 获取注册短信验证码
     *
     * @param view
     */
    public void getRegisterCheckCodeClick(final View view) {
        showProgressDialog();
        HttpCallBack<Object> callBack = new HttpCallBack<Object>() {
            @Override
            public void onSuccess(Object data) {
                hideDialog();
                SharedPreferencesUtils.addValue(getApplicationContext(), UserData.GET_CHECKCODE_TIME, System.currentTimeMillis() + "");
                refreshBtnGetCheckCodeState();
                TSnackbarUtils.showTSnackbar(view, getString(R.string.send_smscode_success));
            }

            @Override
            public void onError(int state, String message) {
                hideDialog();
                TSnackbarUtils.showTSnackbar(view, message);
            }
        };
        accountService.sendRegisterSmsCode(etRegisterMobile.getText().toString().trim(), callBack);
    }

    public void registerClick(final View view) {
        final String mobile = etRegisterMobile.getText().toString().trim();
        String checkCode = etRegisterCheckCode.getText().toString().trim();

        showProgressDialog("正在进行短信验证,请稍后....");
        HttpCallBack<Object> callBack = new HttpCallBack<Object>() {
            @Override
            public void onSuccess(Object data) {
                hideDialog();
//                MyToast.show(getApplicationContext(), "完成验证");
                new AlertDialog.Builder(RegisterActivity.this).setTitle("点击下一步完成注册")
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                registerAfterChecked(view);
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).setCancelable(false).create().show();
            }

            @Override
            public void onError(int state, String message) {
                hideDialog();
//                TSnackbarUtils.showTSnackbar(view, message + "验证失败，请重试...");
            }
        };

        accountService.verifySmsCode(mobile, checkCode, callBack);
    }

    private void registerAfterChecked(final View view) {
        final String password1 = etRegisterPassword1.getText().toString().trim();
        String password2 = etRegisterPassword2.getText().toString().trim();
        final String mobile = etRegisterMobile.getText().toString().trim();

        showProgressDialog(getString(R.string.do_register));
        HttpCallBack<Object> callBack = new HttpCallBack<Object>() {
            @Override
            public void onSuccess(Object data) {
                hideDialog();
                MyToast.show(getApplicationContext(), getString(R.string.register_success));
                new AlertDialog.Builder(RegisterActivity.this).setTitle("是否直接登录")
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                doLogin(mobile, password1);
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                toLoginActivity();
                            }
                        }).setCancelable(false).create().show();
            }

            @Override
            public void onError(int state, String message) {
                hideDialog();
                TSnackbarUtils.showTSnackbar(view, message);
            }
        };
        accountService.register(mobile, password1, password2, callBack);
    }

    private void doLogin(String mobile, String password) {
        showProgressDialog(getString(R.string.do_login));
        HttpCallBack<Token> callBack = new HttpCallBack<Token>() {
            @Override
            public void onSuccess(Token data) {
                hideDialog();
                toHomeActivity();
            }

            @Override
            public void onError(int state, String message) {
                hideDialog();
                MyToast.show(getApplicationContext(), getString(R.string.login_failed));
                toLoginActivity();
            }
        };
        accountService.login(mobile, password, true, callBack);
    }

    private void toHomeActivity() {
        ArouterUtils.startActivity(ActivityURL.LoadActivity);
        finishWithAnim();
    }

    private void toLoginActivity() {
        ArouterUtils.startActivity(ActivityURL.LoginActivity);
        finishWithAnim();
    }
}
