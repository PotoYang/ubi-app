package com.chh.yinbao.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chh.yinbao.R;
import com.chh.yinbao.Token;
import com.chh.yinbao.config.ActivityURL;
import com.chh.yinbao.config.UserData;
import com.chh.yinbao.service.account.AccountService;
import com.chh.yinbao.service.account.AccountServiceImpl;
import com.chh.yinbao.service.http.HttpCallBack;
import com.chh.yinbao.util.MyToast;
import com.chh.yinbao.utils.AppManager;
import com.chh.yinbao.utils.ArouterUtils;
import com.chh.yinbao.utils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by potoyang on 2017/8/24.
 */

@Route(path = ActivityURL.LoadActivity)
public class LoadActivity extends BaseActivity {

    private final String TAG = LoadActivity.class.getSimpleName();
    private String token;
    private boolean isExit = false;
    private Map<String, String> map = new HashMap<>();
    private boolean initv = false;
    @Bind(R.id.webViewLoad)
    WebView webViewLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        ButterKnife.bind(this);
        showGoBackLayout();
        init();
    }

    private void init() {
        Bundle bundle = getIntent().getExtras();
        token = bundle.getString("token");
        map.put("token", token);
        System.out.println(token);
        initWebview();
//        webViewLoad.loadUrl(getString(R.string.my_youhui_html), map);
        webViewLoad.loadUrl(getString(R.string.my_youhui_html));
    }

    private void initWebview() {
        WebSettings settings = webViewLoad.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setGeolocationEnabled(false);

        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDomStorageEnabled(true);

        settings.setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        settings.setAppCachePath(appCachePath);
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);

        webViewLoad.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("login.html")) {
                    AlertDialog.Builder b = new AlertDialog.Builder(LoadActivity.this);
                    b.setTitle("登录过期");
                    b.setMessage("是否重新登录?");
                    b.setPositiveButton(R.string.re_login, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            isInfoSaved();
                        }
                    });
                    b.setNegativeButton("退出", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferencesUtils.removeValue(getApplicationContext(), UserData.USER_PWD);
                            AppManager.getAppManager().finishAllActivity();
                        }
                    });
                    b.setCancelable(false);
                    b.create().show();
                    return true;
                } else
//                if (url.contains("my_plan.html")) {
//                    view.loadUrl(url, map);
//                    view.loadUrl(url);
//                } else
                    if (url.startsWith("tel:")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    } else {
//                    view.loadUrl(url, map);
                        view.loadUrl(url);
                    }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (!initv) {
                    setData(view);
                    initv = true;
                }
            }
        });

//        webViewLoad.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                if (newProgress == 100) {
//                    view.setVisibility(View.INVISIBLE);
//                    webViewLoad.loadUrl(getDomOperationStatements("footer"));
//                    webViewLoad.loadUrl(getDomOperationStatements("header"));
//                    webViewLoad.loadUrl(changeCSS("wrapper"));
//                    view.setVisibility(View.VISIBLE);
//                }
//            }
//        });
    }

//    private String changeCSS(String id) {
//        StringBuilder builder = new StringBuilder();
//        builder.append("javascript:(function() { ");
//        builder.append("var item = document.getElementById('").append(id).append("');");
//        builder.append("item.style.top = \"0px\";");
//        builder.append("item.style.bottom = \"0px\";");
//        builder.append("})()");
//        return builder.toString();
//    }
//
//    private String getDomOperationStatements(String ele) {
//        StringBuilder builder = new StringBuilder();
//        // add javascript prefix
//        builder.append("javascript:(function() { ");
//        builder.append("var item = document.getElementsByTagName('").append(ele).append("');");
//        builder.append("item[0].style.display=\"none\";");
//        // add javascript suffix
//        builder.append("})()");
//        return builder.toString();
//    }

    private void setData(WebView view) {
        String js = "window.localStorage.setItem(\"token\",\"" + token + "\")";
        System.out.println(js);
        view.evaluateJavascript(js, null);
    }

    private void isInfoSaved() {
        String username = SharedPreferencesUtils.getDecryptValue(getApplicationContext(), UserData.USER_NAME);
        String password = SharedPreferencesUtils.getDecryptValue(getApplicationContext(), UserData.USER_PWD);
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            showProgressDialog("重新登录中...");

            AccountService accountService = new AccountServiceImpl(getApplicationContext());

            HttpCallBack<Token> callBack = new HttpCallBack<Token>() {
                @Override
                public void onSuccess(Token data) {
                    token = data.getToken();
                    hideProgressDialog();
                    MyToast.show(getApplicationContext(), "免密登录成功!");
                    Bundle bundle = new Bundle();
                    bundle.putString("token", data.getToken());
                    finish();
                    ArouterUtils.startActivity(bundle, ActivityURL.LoadActivity);
//                    toHomeActivity(data.getToken());
                }

                @Override
                public void onError(int state, String message) {
                    MyToast.show(getApplicationContext(), "免密登录失败!");
                    ArouterUtils.startActivity(ActivityURL.LoginActivity);
                    finish();
                }
            };

            accountService.login(username, password, true, callBack);
        } else {
//            toLoginActivity();
//            ArouterUtils.startActivity(ActivityURL.LoginActivity);
            ArouterUtils.startActivity(ActivityURL.LoginActivity);
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webViewLoad.canGoBack()) {
            webViewLoad.goBack();
            return true;
        } else {
            if (!isExit) {
                isExit = true;
                MyToast.show(this, R.string.exit_warm);
                startExitTask();
            } else {
                AppManager.getAppManager().AppExit(this);
                System.exit(0);
            }
            return true;
        }
    }

    private void startExitTask() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3 * 1000);
                    isExit = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
