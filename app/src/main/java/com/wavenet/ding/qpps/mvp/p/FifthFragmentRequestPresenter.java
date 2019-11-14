package com.wavenet.ding.qpps.mvp.p;


import com.dereck.library.base.BaseMvpPersenter;
import com.dereck.library.observer.CommonObserver;
import com.wavenet.ding.qpps.mvp.m.FifthFragmentRequestModel;
import com.wavenet.ding.qpps.mvp.v.AdminFragmentRequestView;

public class FifthFragmentRequestPresenter extends BaseMvpPersenter<AdminFragmentRequestView> {

    private FifthFragmentRequestModel mAdminActivityRequestModel;

    public FifthFragmentRequestPresenter() {
        this.mAdminActivityRequestModel = new FifthFragmentRequestModel();
    }
//调度员，带派单、带审批总数
    public void requestDDY(String Townids, String Role) {
        //上报人，状态
        requestData(0, 1, "");
        mAdminActivityRequestModel.requestDDY(Townids, Role, new CommonObserver<Object>() {

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
    public void getAPPCode(final int what) {
        requestData(0, 2, "");

        mAdminActivityRequestModel.getAPPCode(new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                //业务处理
                requestData(1, 2, errorMsg);

            }

            @Override
            protected void onSuccess(Object s) {

                //业务处理
                requestData(2, 2, (String) s);

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
