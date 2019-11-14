package com.wavenet.ding.qpps.mvp.v;

import com.dereck.library.base.IMvpBaseView;

public interface RequestView extends IMvpBaseView {

    void requestSuccess(int resultid, String result);

    void show();

    void hide();

    void requestFailure(int resultid, String result);
}