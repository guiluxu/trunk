package com.wavenet.ding.qpps.mvp.v;

import com.dereck.library.base.IMvpBaseView;

import org.devio.takephoto.model.TImage;

import java.util.ArrayList;
import java.util.Map;

public interface XJActivityRequestView extends IMvpBaseView {

    void requestSuccess(int resultid, String result);

    void show();

    void hide();

    void requestFailure(int resultid, String result);

    void requestSuccess(int resultid, String result, boolean b);

    void requestFailure(int resultid, String result, boolean b);

    void requestFailure(int resultid, String result, Map<String, Object> map, ArrayList<TImage> images,
                        String videoPath, String audioPath);
}