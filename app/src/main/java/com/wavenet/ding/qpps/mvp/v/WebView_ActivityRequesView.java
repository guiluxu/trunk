package com.wavenet.ding.qpps.mvp.v;

import com.dereck.library.base.IMvpBaseView;

public interface WebView_ActivityRequesView <T> extends IMvpBaseView {
    void resultSuccess(T result);
    void resultFailure(String result);
    void show();
    void hide();
}
