package com.wavenet.ding.qpps.mvp.p;

import com.dereck.library.base.BaseMvpPersenter;
import com.wavenet.ding.qpps.mvp.m.WebView_ActivityRequestModel;
import com.wavenet.ding.qpps.mvp.v.WebView_ActivityRequesView;

public class WebView_ActivityRequestPresenter extends BaseMvpPersenter<WebView_ActivityRequesView> {
    private final WebView_ActivityRequestModel webView_activityRequestModel;
    public WebView_ActivityRequestPresenter() {
        this.webView_activityRequestModel = new WebView_ActivityRequestModel();
    }
}
