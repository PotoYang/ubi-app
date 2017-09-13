package com.chh.yinbao.wxapi;

import android.content.Intent;
import android.os.Bundle;

import com.chh.yinbao.FirstApplication;
import com.chh.yinbao.R;
import com.chh.yinbao.activity.BaseActivity;
import com.chh.yinbao.config.ActivityURL;
import com.chh.yinbao.utils.ArouterUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/**
 * Created by potoyang on 2017/8/17.
 */

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            FirstApplication.iwxapi.handleIntent(getIntent(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        FirstApplication.iwxapi.handleIntent(intent, this);
    }

    @Override
    public void onResp(BaseResp resp) {
        int result = 0;
        System.out.println(resp.errCode);
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = R.string.code_success;
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = R.string.code_cancel;
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = R.string.code_deny;
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                result = R.string.code_unsupported;
                break;
            default:
                result = R.string.code_unknown;
                break;
        }

        System.out.println(getResources().getString(result));
        String code = ((SendAuth.Resp) resp).code;
        System.out.println(code);
        Bundle bundle = new Bundle();
        bundle.putString("code", code);
        ArouterUtils.startActivity(bundle, ActivityURL.LoginActivity);

    }
}
