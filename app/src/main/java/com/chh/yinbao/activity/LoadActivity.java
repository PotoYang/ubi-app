package com.chh.yinbao.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chh.yinbao.R;
import com.chh.yinbao.config.ActivityURL;
import com.chh.yinbao.utils.ArouterUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by potoyang on 2017/8/24.
 */

@Route(path = ActivityURL.LoadActivity)
public class LoadActivity extends BaseActivity {

    private final String TAG = LoadActivity.class.getSimpleName();
    private String token;

    @Bind(R.id.webViewLoad)
    WebView webViewLoad;
//    @Bind(R.id.btn_js)
//    Button btn_js;

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
        initWebview();
        webViewLoad.loadUrl("http://yinbao.senit.xyz:8080/yinbao/my_youhui.html");
//        webViewLoad.loadUrl("file:///android_asset/my.html");
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
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                setData(view);
            }
        });

        webViewLoad.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(LoadActivity.this);
                b.setTitle("错误");
                b.setMessage(message);
                b.setPositiveButton(getString(R.string.do_login), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        result.confirm();
                        ArouterUtils.startActivity(ActivityURL.LoginActivity);
                        finish();
                    }
                });
                b.setCancelable(false);
                b.create().show();
                return true;
            }

        });
    }

    private void setData(WebView view) {
        String js = "window.localStorage.setItem(\"token\",\"" + token + "\")";
//        String js = "window.localStorage.setItem(\"token\",\"123\")";
        System.out.println(js);
        view.evaluateJavascript(js, null);
    }
}
