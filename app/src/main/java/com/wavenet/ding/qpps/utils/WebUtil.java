package com.wavenet.ding.qpps.utils;

import android.graphics.Bitmap;
import android.webkit.JavascriptInterface;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * Created by Administrator on 2017/8/2.
 */

public class WebUtil {
    /**
     * 初始化WebView
     */
    public static void getwebview(WebView mWebchar) {
        WebSettings settings = mWebchar.getSettings();
        settings.setJavaScriptEnabled(true);// 启用JS脚本
        settings.setSupportZoom(true); // 支持缩放
        settings.setBuiltInZoomControls(true); // 启用内置缩放装置
        settings.setAllowFileAccess(true); // 设置可以访问文件
//        settings.setUseWideViewPort(true);// 设置此属性，可任意比例缩放
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setLoadWithOverviewMode(true);// 缩放至屏幕的大小
//        settings.setBlockNetworkImage(false);
//        settings.setLoadsImagesAutomatically(true); // 支持自动加载图片
        settings.setDisplayZoomControls(false);
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebchar.requestFocus();
        mWebchar.requestFocusFromTouch();// 获取手势焦点
        mWebchar.setVerticalScrollBarEnabled(true);
        mWebchar.setHorizontalScrollBarEnabled(true);
//        mWebchar.setWebViewClient(new WebUtil.HelloWebViewClient());
//        mWebchar.setWebChromeClient(new WebUtil.HelloWebChromeClient());
    }


    public static void setMethod(WebView mWebchar, final String jsonstr, final String method) {
        mWebchar.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public String getLocationData(String message) {
                return jsonstr;
            }
        }, method);
    }

    // Web视图
    private static class HelloWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
//            showDialog();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
//            cancelDialog();
        }
    }

    //加载数据
    private static class HelloWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
    }


}
