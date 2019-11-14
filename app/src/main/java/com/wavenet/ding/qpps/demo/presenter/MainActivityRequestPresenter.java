package com.wavenet.ding.qpps.demo.presenter;


import com.dereck.library.base.BaseMvpPersenter;
import com.dereck.library.observer.CommonObserver;
import com.wavenet.ding.qpps.demo.model.MainActivityRequestModel;
import com.wavenet.ding.qpps.demo.view.MainActivityRequestView;

public class MainActivityRequestPresenter extends BaseMvpPersenter<MainActivityRequestView> {

    private final MainActivityRequestModel mRequestMode;

    public MainActivityRequestPresenter() {
        this.mRequestMode = new MainActivityRequestModel();
    }

    public void clickRequest(final String cityId) {
        if (getmMvpView() != null) {
            getmMvpView().show();
        }

        mRequestMode.request(cityId, "", new CommonObserver<String>() {

            @Override
            protected void onError(String errorMsg) {
                if (getmMvpView() != null) {
                    getmMvpView().hide();
                }
                getmMvpView().resultFailure(errorMsg);
            }

            @Override
            protected void onSuccess(String s) {
                if (getmMvpView() != null) {
                    getmMvpView().hide();
                }
                //业务处理
                if (getmMvpView() != null) {
                    getmMvpView().resultSuccess(s);
                }
            }
        });
    }


}
