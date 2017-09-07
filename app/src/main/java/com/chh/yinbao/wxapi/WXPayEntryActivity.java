package com.chh.yinbao.wxapi;

import com.chh.yinbao.FirstApplication;
import com.chh.yinbao.activity.BaseActivity;
import com.chh.yinbao.config.Config;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/**
 * Created by potoyang on 2017/9/7.
 */

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {
    PayReq req = new PayReq();

    private void mm() {
        req.appId = Config.WX_APP_ID;
        req.timeStamp = "";
        FirstApplication.iwxapi.sendReq(req);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {

    }
}
