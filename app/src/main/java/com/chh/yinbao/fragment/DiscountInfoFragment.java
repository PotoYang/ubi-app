package com.chh.yinbao.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chh.yinbao.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by potoyang on 2017/8/9.
 */

public class DiscountInfoFragment extends BaseFragment {
    private View rootView;
    @Bind(R.id.discountWebView)
    WebView webViewLoad;

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
        String appCachePath = getContext().getCacheDir().getAbsolutePath();
        settings.setAppCachePath(appCachePath);
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);

        webViewLoad.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if (url.contains("login.html")) {
//                    AlertDialog.Builder b = new AlertDialog.Builder(LoadActivity.this);
//                    b.setTitle("错误");
//                    b.setMessage("登录过期");
//                    b.setPositiveButton(getString(R.string.do_login), new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            ArouterUtils.startActivity(ActivityURL.LoginActivity);
//                            finish();
//                        }
//                    });
//                    b.setCancelable(false);
//                    b.create().show();
//                    return true;
//                } else
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else {
                    view.loadUrl(url);
                }
                return true;
            }
        });

        webViewLoad.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    view.setVisibility(View.INVISIBLE);
                    webViewLoad.loadUrl(getDomOperationStatements("footer"));
                    webViewLoad.loadUrl(getDomOperationStatements("header"));
                    webViewLoad.loadUrl(changeCSS("wrapper"));
                    view.setVisibility(View.VISIBLE);
                }
            }
        });
    }

//    private void setData(WebView view) {
//        String js = "window.localStorage.setItem(\"token\",\"" + token + "\")";
//        System.out.println(js);
//        view.evaluateJavascript(js, null);
//    }

    private String changeCSS(String id) {
        StringBuilder builder = new StringBuilder();
        builder.append("javascript:(function() { ");
        builder.append("var item = document.getElementById('").append(id).append("');");
        builder.append("item.style.top = \"0px\";");
        builder.append("item.style.bottom = \"0px\";");
        builder.append("})()");
        return builder.toString();
    }

    private String getDomOperationStatements(String ele) {
        StringBuilder builder = new StringBuilder();
        // add javascript prefix
        builder.append("javascript:(function() { ");
        builder.append("var item = document.getElementsByTagName('").append(ele).append("');");
        builder.append("item[0].style.display=\"none\";");
        // add javascript suffix
        builder.append("})()");
        return builder.toString();
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
