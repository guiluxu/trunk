package com.wavenet.ding.qpps.mvp.v;

import com.dereck.library.base.IMvpBaseView;

public interface ChangePasswordActivityRequestView extends IMvpBaseView {

    void resultSuccess(int what, String result);
    void show();
    void hide();
    void resultFailure(int what, String result);
}