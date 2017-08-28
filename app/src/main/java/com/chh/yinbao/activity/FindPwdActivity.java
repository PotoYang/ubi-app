package com.chh.yinbao.activity;

import android.os.Bundle;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chh.yinbao.R;
import com.chh.yinbao.config.ActivityURL;
import com.chh.yinbao.config.UserData;
import com.chh.yinbao.service.account.AccountService;
import com.chh.yinbao.service.account.AccountServiceImpl;
import com.chh.yinbao.service.http.HttpCallBack;
import com.chh.yinbao.util.MyToast;
import com.chh.yinbao.utils.ArouterUtils;
import com.chh.yinbao.utils.SharedPreferencesUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by potoyang on 2017/8/9.
 */

@Route(path = ActivityURL.FindPwdActivity)
public class FindPwdActivity extends BaseActivity {

    private static String TAG = FindPwdActivity.class.getSimpleName();

    @Bind(R.id.etFindUserName)
    EditText etFindUserName;
    @Bind(R.id.etFindCheckCode)
    EditText etFindCheckCode;
    @Bind(R.id.btnFindGetCheckCode)
    Button btnFindGetCheckCode;

    private AccountService accountService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpwd);
        ButterKnife.bind(this);
        showGoBackLayout();
        init();
    }

    private void init() {
        setToolbarTitleText(getString(R.string.find_pwd_title));
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
                    btnFindGetCheckCode.setEnabled(false);
                    btnFindGetCheckCode.setText(str);
                } else {
                    btnFindGetCheckCode.setEnabled(true);
                    btnFindGetCheckCode.setText(R.string.get_checkcode_hint);
                }
            }
        });
    }

    public void getFindCheckCodeClick(View view) {
        showProgressDialog();
        HttpCallBack<Object> callBack = new HttpCallBack<Object>() {
            @Override
            public void onSuccess(Object data) {
                hideDialog();
                SharedPreferencesUtils.addValue(getApplicationContext(), UserData.GET_CHECKCODE_TIME, System.currentTimeMillis() + "");
                refreshBtnGetCheckCodeState();
                MyToast.show(getApplicationContext(), getString(R.string.send_smscode_success));
            }

            @Override
            public void onError(int state, String message) {
                hideDialog();
                MyToast.show(getApplicationContext(), message);
            }
        };
        accountService.sendFindPwdSmsCode(etFindUserName.getText().toString().trim(), callBack);
    }

    public void findClick(View view) {
        final String mobile = etFindUserName.getText().toString().trim();
        final String checkCode = etFindCheckCode.getText().toString().trim();
        showProgressDialog("");
        HttpCallBack<Object> callBack = new HttpCallBack<Object>() {
            @Override
            public void onSuccess(Object data) {
                hideDialog();
                MyToast.show(getApplicationContext(), "验证成功!");
                Bundle bundle = new Bundle();
                bundle.putString(ResetPwdActivity.SMSCODE_KEY, checkCode);
                bundle.putString(ResetPwdActivity.LOGINNAME_KEY, mobile);
                ArouterUtils.startActivity(bundle, ActivityURL.ResetPwdActivity);
                finishWithAnim();
            }

            @Override
            public void onError(int state, String message) {
                hideDialog();
                MyToast.show(getApplicationContext(), message);
            }
        };
        accountService.verifySmsCode(mobile, checkCode, callBack);
    }
}
