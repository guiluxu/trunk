package com.wavenet.ding.qpps.mvp.v;

import com.dereck.library.base.IMvpBaseView;

public interface AdminFragmentRequestView extends IMvpBaseView {

    void requestSuccess(int resultid, String result);
    void requestSuccessJC(int resultid, String result);

    void show();

    void hide();

    void requestFailure(int resultid, String result);
    void requestFailureJC(int resultid, String result);
}