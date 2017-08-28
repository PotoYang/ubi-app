package com.chh.yinbao.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chh.yinbao.R;
import com.chh.yinbao.utils.LogUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by potoyang on 2017/8/9.
 */

public class DiscountInfoFragment extends BaseFragment {
    private View rootView;
    @Bind(R.id.webViewInfo)
    WebView webViewInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_discount_info, container, false);
            ButterKnife.bind(this, rootView);
            init();
        }
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    private void init() {
        initWebview();
        webViewInfo.loadUrl("http://yinbao.senit.xyz:8080/yinbao/my_youhui.html");
    }

    private void initWebview() {
        webViewInfo.getSettings().setJavaScriptEnabled(true);//执行JS脚本
        webViewInfo.getSettings().setAllowFileAccess(false);
        webViewInfo.getSettings().setGeolocationEnabled(false);

        webViewInfo.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webViewInfo.getSettings().setUseWideViewPort(true);
        webViewInfo.getSettings().setLoadWithOverviewMode(true);

        webViewInfo.getSettings().setDomStorageEnabled(false);
//        webView.getSettings().setBuiltInZoomControls(true);//设置使支持缩放
        webViewInfo.setWebChromeClient(new WebChromeClient());
        webViewInfo.setWebViewClient(new WebViewClient() {
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
                LogUtils.i("5454", "发生错误:" + error);
            }

        });
    }

//    @OnClick(R.id.ivCall)
//    public void ivCallClick() {
//        Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "18328595452"));
//        startActivity(phoneIntent);
//    }
//
//    @OnClick(R.id.ivWeb)
//    public void ivWebClick() {
//        ArouterUtils.startActivity(ActivityURL.WebExtendActivity);
//    }
//
//    @OnClick(R.id.btnLogout)
//    public void btnLogoutClick() {
//        SharedPreferencesUtils.removeValue(getContext(), UserData.USER_PWD);
//        MyToast.show(getContext(), "注销成功");
//        ArouterUtils.startActivity(ActivityURL.LoginActivity);
//    }
}
