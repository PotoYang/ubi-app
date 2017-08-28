package com.chh.yinbao.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chh.yinbao.config.ActivityURL;
import com.chh.yinbao.R;
import com.chh.yinbao.util.MyToast;
import com.chh.yinbao.util.TSnackbarUtils;
import com.chh.yinbao.service.account.AccountService;
import com.chh.yinbao.service.account.AccountServiceImpl;
import com.chh.yinbao.service.http.HttpCallBack;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by potoyang on 2017/8/10.
 */

@Route(path = ActivityURL.UpdatePwdActivity)
public class UpdatePwdActivity extends BaseActivity {
    private static String TAG = UpdatePwdActivity.class.getSimpleName();

    @Bind(R.id.etOldPwd)
    EditText etOldPwd;
    @Bind(R.id.etNewPwd)
    EditText etNewPwd;
    @Bind(R.id.etNewPwdAgain)
    EditText etNewPwdAgain;

    private AccountService accountService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatepwd);
        ButterKnife.bind(this);
        showGoBackLayout();
        init();
    }

    private void init() {
        setToolbarTitleText(getString(R.string.update_pwd_title));
        accountService = new AccountServiceImpl(getApplicationContext());
    }

    public void updatePwdClick(View view) {
        String oldPwd = etOldPwd.getText().toString().trim();
        String newPwd = etNewPwd.getText().toString().trim();
        String newPwdAgain = etNewPwdAgain.getText().toString().trim();

        showProgressDialog("");

        HttpCallBack<Object> callBack = new HttpCallBack<Object>() {
            @Override
            public void onSuccess(Object data) {
                hideDialog();
                MyToast.show(getApplicationContext(), getString(R.string.modify_pwd_success));
                goBackLayout();
            }

            @Override
            public void onError(int state, String message) {
                hideDialog();
                TSnackbarUtils.showTSnackbar(etNewPwd, message);
//                MyToast.show(getApplicationContext(), message);
            }
        };
        accountService.updatePwd(oldPwd, newPwd, newPwdAgain, callBack);
    }

}
