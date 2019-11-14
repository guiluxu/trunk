package com.wavenet.ding.qpps.mvp.p;


import com.dereck.library.base.BaseMvpPersenter;
import com.dereck.library.observer.CommonObserver;
import com.wavenet.ding.qpps.mvp.m.XJRecordDetailsActivityRequestModel;
import com.wavenet.ding.qpps.mvp.v.XJActivityRequestView;

public class XJRecordDetailsRequestPresenter extends BaseMvpPersenter<XJActivityRequestView> {

    private XJRecordDetailsActivityRequestModel mXJRecordDetailsActivityRequestModel;

    public XJRecordDetailsRequestPresenter() {
        this.mXJRecordDetailsActivityRequestModel = new XJRecordDetailsActivityRequestModel();
    }

    public void RequestReportDetails(String S_MANGE_ID) {
        //上报人，状态
        requestData(0, 1, "");
        mXJRecordDetailsActivityRequestModel.RequestReportDetails(S_MANGE_ID, new CommonObserver<Object>() {

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

    public void RequestFileDetailsPhoto(final int file, String S_RECODE_ID) {
        //上报人，状态
        requestData(0, file, "");
        mXJRecordDetailsActivityRequestModel.RequestFileDetailsPhoto(file, S_RECODE_ID, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                requestData(1, file, errorMsg);
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestData(2, file, (String) s);
            }
        });
    }

    public void RequestRefuselist(String S_MANGE_ID) {
        //上报人，状态
        requestData(0, 3, "");
        mXJRecordDetailsActivityRequestModel.RequestRefuselist(S_MANGE_ID, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                requestData(1, 3, errorMsg);
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestData(2, 3, (String) s);
            }
        });
    }

    public void RequestTDlist(String S_MANGE_ID) {
        //上报人，状态
        requestData(0, 4, "");
        mXJRecordDetailsActivityRequestModel.RequestTDlist(S_MANGE_ID, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                requestData(1, 4, errorMsg);
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestData(2, 4, (String) s);
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
