package com.wavenet.ding.qpps.mvp.p;

import com.dereck.library.base.BaseMvpPersenter;
import com.dereck.library.observer.CommonObserver;
import com.wavenet.ding.qpps.mvp.m.MapTrackPlayRequestModel;
import com.wavenet.ding.qpps.mvp.v.RequestView;

public class MapTrackPlayRequestPresenter extends BaseMvpPersenter<RequestView> {

    private final MapTrackPlayRequestModel mMapTrackPlayRequestModel;

    public MapTrackPlayRequestPresenter() {
        this.mMapTrackPlayRequestModel = new MapTrackPlayRequestModel();
    }
    public void requestTrack(String S_RECODE_ID) {
//
        requestData(0, 1, "");
        mMapTrackPlayRequestModel.requestTrack(  S_RECODE_ID, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                requestData(1, 1, errorMsg);
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestData(2, 1, (String) s);
            }
        });
    }

    public void requestData(int state, int resultid, String content) {
        if (getmMvpView() != null) {
            if (state == 0) {
                getmMvpView().show();
            } else if (state == 1) {
                getmMvpView().hide();
                getmMvpView().requestFailure(resultid, content);
            } else if (state == 2) {
                getmMvpView().hide();
                getmMvpView().requestSuccess(resultid, content);
            }
        }

    }

}