package com.wavenet.ding.qpps.mvp.p;


import com.dereck.library.base.BaseMvpPersenter;
import com.dereck.library.observer.CommonObserver;
import com.wavenet.ding.qpps.mvp.m.XJDealDetailsActivityRequestModel;
import com.wavenet.ding.qpps.mvp.v.XJActivityRequestView;

public class XJDealDetailsRequestPresenter extends BaseMvpPersenter<XJActivityRequestView> {

    private XJDealDetailsActivityRequestModel mXJDealDetailsActivityRequestModel;

    public XJDealDetailsRequestPresenter() {
        this.mXJDealDetailsActivityRequestModel = new XJDealDetailsActivityRequestModel();
    }

    public void RequestDealDetails(String S_MANGE_ID) {
        //上报人，状态
        requestData(0, 5, "");
        mXJDealDetailsActivityRequestModel.RequestDealDetails(S_MANGE_ID, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                requestData(1, 5, errorMsg);
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestData(2, 5, (String) s);
            }
        });
    }

    public void RequestReportDetails(String S_MANGE_ID) {
        //上报人，状态
        requestData(0, 1, "");
        mXJDealDetailsActivityRequestModel.RequestReportDetails(S_MANGE_ID, new CommonObserver<Object>() {

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
        mXJDealDetailsActivityRequestModel.RequestFileDetailsPhoto(file, S_RECODE_ID, new CommonObserver<Object>() {

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
        mXJDealDetailsActivityRequestModel.RequestRefuselist(S_MANGE_ID, new CommonObserver<Object>() {

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
        mXJDealDetailsActivityRequestModel.RequestTDlist(S_MANGE_ID, new CommonObserver<Object>() {

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
