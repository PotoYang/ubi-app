package com.chh.yinbao.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chh.yinbao.R;
import com.chh.yinbao.User;
import com.chh.yinbao.config.ActivityURL;
import com.chh.yinbao.service.account.AccountService;
import com.chh.yinbao.service.account.AccountServiceImpl;
import com.chh.yinbao.service.http.HttpCallBack;
import com.chh.yinbao.util.MyToast;
import com.chh.yinbao.utils.ArouterUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by potoyang on 2017/8/15.
 */

@Route(path = ActivityURL.BinInfoActivity)
public class BindInfoActivity extends BaseActivity {
    private static String TAG = BindInfoActivity.class.getSimpleName();

    @Bind(R.id.etBindInfoName)
    EditText etBindInfoName;
    @Bind(R.id.etBindInfoIdCard)
    EditText etBindInfoIdCard;
    @Bind(R.id.etBindInfoCarNo)
    EditText etBindInfoCarNo;

    AccountService accountService;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bindinfo);
        hideGoBackLayout();
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        setToolbarTitleText(getString(R.string.bind_info_title));
        accountService = new AccountServiceImpl(getApplicationContext());
        user = (User) getIntent().getSerializableExtra("userData");
        if (user == null) {
            user = new User();
        }
    }

    public void bindInfoClick(View view) {
        String name = etBindInfoName.getText().toString().trim();
        String idCard = etBindInfoIdCard.getText().toString().trim();
        String carNo = etBindInfoCarNo.getText().toString().trim();
        final String token = user.getToken();

        HttpCallBack<Object> callBack = new HttpCallBack<Object>() {
            @Override
            public void onSuccess(Object data) {
                MyToast.show(getApplicationContext(), "绑定成功!");
                Bundle bundle = new Bundle();
                bundle.putString("token", token);
                ArouterUtils.startActivity(bundle, ActivityURL.LoadActivity);
                finish();
            }

            @Override
            public void onError(int state, String message) {
                MyToast.show(getApplicationContext(), message);
            }
        };

        accountService.bindInfo(name, idCard, carNo, token, callBack);
    }
}
