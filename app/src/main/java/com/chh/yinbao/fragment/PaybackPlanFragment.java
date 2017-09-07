package com.chh.yinbao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.chh.yinbao.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by potoyang on 2017/8/9.
 */

public class PaybackPlanFragment extends BaseFragment {
    private View rootView;
    @Bind(R.id.paybackWebView)
    WebView webViewLoad;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_payback_plan, container, false);
            ButterKnife.bind(this, rootView);
            init();
        }
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    private void init() {
        initWebview();
        webViewLoad.loadUrl(getString(R.string.my_plan_html));
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

        webViewLoad.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    view.setVisibility(View.INVISIBLE);
                    webViewLoad.loadUrl(getDomOperationStatements("footer"));
                    webViewLoad.loadUrl(getDomOperationStatements("header"));
                    webViewLoad.loadUrl(changeCSS("plan-wrapper"));
                    view.setVisibility(View.VISIBLE);
                }
            }
        });
    }

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

}
