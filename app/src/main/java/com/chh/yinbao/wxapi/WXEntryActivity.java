package com.chh.yinbao.wxapi;

import android.content.Intent;
import android.os.Bundle;

import com.chh.yinbao.FirstApplication;
import com.chh.yinbao.R;
import com.chh.yinbao.activity.BaseActivity;
import com.chh.yinbao.config.ActivityURL;
import com.chh.yinbao.config.Config;
import com.chh.yinbao.utils.ArouterUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

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

//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//
//        setIntent(intent);
//        api.handleIntent(intent, this);
//    }

    @Override
    public void onResp(BaseResp resp) {
        int result = 0;
        System.out.println(resp.errCode);
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = R.string.code_success;
                //得到code，马上请求access_token
//                showProgressDialog("请求中...");
//                String code = ((SendAuth.Resp) resp).code;
//                System.out.println(result + "  " + code);
//                String s = getWXUserInfo(code);
//                System.out.println(s);
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
//        WXBaseInfo info = getWXUserInfo(code);
//        getWXUserInfo(code);
//        System.out.println(info.getOpenid());
    }

    private String getWXUserInfo(String code) {
        String strUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" +
                Config.WX_APP_ID + "&secret=" +
                Config.WX_APP_SECRET + "&code=" +
                code + "&grant_type=authorization_code";

        try {
            URL url = new URL(strUrl);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            InputStream is = urlConnection.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while (-1 != (len = is.read(buffer))) {
                baos.write(buffer, 0, len);
                baos.flush();
            }
            return baos.toString("utf-8");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

//        OkHttpClient okHttpClient = new OkHttpClient();
//        final Request request = new Request.Builder()
//                .url(strUrl)
//                .get()
//                .build();
//
//        try {
//            Response response = okHttpClient.newCall(request).execute();
//            Gson gson = new GsonBuilder().create();
//            final String string = response.body().string();
//            WXBaseInfo info = gson.fromJson(string, WXBaseInfo.class);
//            return info;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//        OkHttpClient client = new OkHttpClient();
//
//        Request request = new Request.Builder()
//                .url(strUrl)
//                .get()
//                .build();
//
//        try {
//            Response response = client.newCall(request).execute();
//            System.out.println(response.body().string());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
