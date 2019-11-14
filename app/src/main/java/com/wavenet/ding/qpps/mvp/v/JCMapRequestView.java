package com.wavenet.ding.qpps.mvp.v;

import com.dereck.library.base.IMvpBaseView;

public interface JCMapRequestView extends IMvpBaseView {

    void requestSuccessJC(int resultid, String result);

    void show();

    void hide();

    void requestFailureJC(int resultid, String result);
}