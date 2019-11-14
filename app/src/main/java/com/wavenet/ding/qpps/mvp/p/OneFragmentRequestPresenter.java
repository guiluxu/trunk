package com.wavenet.ding.qpps.mvp.p;


import com.dereck.library.base.BaseMvpPersenter;
import com.dereck.library.observer.CommonObserver;
import com.wavenet.ding.qpps.mvp.m.FifthFragmentRequestModel;
import com.wavenet.ding.qpps.mvp.v.AdminFragmentRequestView;

public class OneFragmentRequestPresenter extends BaseMvpPersenter<AdminFragmentRequestView> {

    private FifthFragmentRequestModel mAdminActivityRequestModel;

    public OneFragmentRequestPresenter() {
        this.mAdminActivityRequestModel = new FifthFragmentRequestModel();
    }

    public void AdminGetObjectIds(String S_MAN, String filter) {
        //上报人，状态
        requestData(0, 1, "");
        mAdminActivityRequestModel.requestDDY(S_MAN, filter, new CommonObserver<Object>() {

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
