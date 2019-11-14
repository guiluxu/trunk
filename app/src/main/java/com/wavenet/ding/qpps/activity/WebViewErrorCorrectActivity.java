package com.wavenet.ding.qpps.activity;

import android.view.View;
import android.webkit.JavascriptInterface;

public class WebViewErrorCorrectActivity extends WebViewActivity implements View.OnClickListener {
@Override
    public void dealData() {
    isShowTop(false);
    }
    @Override
    public void onClick(View view) {
    super.onClick(view);
        switch (view.getId()) {
        }
    }
    @JavascriptInterface
    public void backPreviousView() {
        super.backPreviousView();
    }
}