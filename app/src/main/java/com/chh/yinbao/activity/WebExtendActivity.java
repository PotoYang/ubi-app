package com.chh.yinbao.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chh.yinbao.R;
import com.chh.yinbao.config.ActivityURL;
import com.chh.yinbao.utils.LogUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by potoyang on 2017/8/11.
 */

@Route(path = ActivityURL.WebExtendActivity)
public class WebExtendActivity extends BaseActivity {

    private final static String TAG = WebExtendActivity.class.getSimpleName();

    @Bind(R.id.webViewBaidu)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webextend);
        ButterKnife.bind(this);
        showGoBackLayout();
        init();
    }

    private void init() {
        setToolbarTitleText("我的首页");
        initWebview();
        //将token放入html5中
//        Map<String, String> extraHeaders = new HashMap<>();
//        extraHeaders.put("token", "token");
//        webView.loadUrl("http://www.baidu.com", extraHeaders);
        webView.loadUrl("file:///android_asset/my.html");
    }

    private void initWebview() {
        webView.getSettings().setJavaScriptEnabled(true);//执行JS脚本
        webView.getSettings().setAllowFileAccess(false);
        webView.getSettings().setGeolocationEnabled(false);

        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);

        webView.getSettings().setDomStorageEnabled(false);
//        webView.getSettings().setBuiltInZoomControls(true);//设置使支持缩放
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                url.startsWith();
//                view.loadUrl(url);// 使用当前WebView处理跳转
//                return true;//true表示此事件在此处被处理，不需要再广播
                String tag = "my:tel";

                if (url.contains(tag)) {
                    String mobile = url.substring(url.lastIndexOf("/") + 1);
                    Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mobile));
                    startActivity(phoneIntent);
                    //这个超连接,java已经处理了，webview不要处理了
                    return true;
                }

                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                LogUtils.i(TAG, "发生错误:" + error);
            }

        });
    }
}
