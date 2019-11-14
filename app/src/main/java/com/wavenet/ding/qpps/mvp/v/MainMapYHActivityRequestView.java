package com.wavenet.ding.qpps.mvp.v;

import com.dereck.library.base.IMvpBaseView;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.ResponseBody;

public interface MainMapYHActivityRequestView extends IMvpBaseView {


    void show();

    void hide();

    void resultFailure(int what, String result);

    void resultFailure(int what, String result, Map<String, Object> map, String audioPath, String videoPath, ArrayList<String> imgPaths);

    void resultSuccess(int what, ResponseBody result);


    void resultStringSuccess(int what, String result);

    void resultFailureMAP(int what, String result);

    void resultSuccessMAP(int what, String result);

}