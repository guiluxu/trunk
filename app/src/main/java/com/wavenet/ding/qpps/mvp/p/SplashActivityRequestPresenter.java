package com.wavenet.ding.qpps.mvp.p;

import com.dereck.library.base.BaseMvpPersenter;
import com.dereck.library.observer.CommonObserver;
import com.wavenet.ding.qpps.mvp.m.SplashActivityRequestModel;
import com.wavenet.ding.qpps.mvp.v.SplashActivityRequestView;

public class SplashActivityRequestPresenter extends BaseMvpPersenter<SplashActivityRequestView> {

    private final SplashActivityRequestModel splashActivityRequestModel;

    public SplashActivityRequestPresenter() {
        this.splashActivityRequestModel = new SplashActivityRequestModel();
    }

    public void getCheck(final int what) {
        if (getmMvpView() != null) {
            getmMvpView().show();
        }

        splashActivityRequestModel.getCheck(new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                //业务处理
                if (getmMvpView() != null) {
                    getmMvpView().hide();
                    getmMvpView().resultFailure(what, errorMsg);
                }

            }

            @Override
            protected void onSuccess(Object s) {

                //业务处理
                if (getmMvpView() != null) {
                    getmMvpView().resultSuccess(what, (String) s);
                    getmMvpView().hide();


                }

            }
        });
    }

    public void getAPPCode(final int what) {
        if (getmMvpView() != null) {
            getmMvpView().show();
        }

        splashActivityRequestModel.getAPPCode(new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                //业务处理
                if (getmMvpView() != null) {
                    getmMvpView().hide();
                    getmMvpView().resultFailure(what, errorMsg);
                }

            }

            @Override
            protected void onSuccess(Object s) {

                //业务处理
                if (getmMvpView() != null) {
                    getmMvpView().resultSuccess(what, (String) s);
                    getmMvpView().hide();


                }

            }
        });
    }

//    public void getWebViewUrl(final int what) {
//        if (getmMvpView() != null) {
//            getmMvpView().show();
//        }
//
//        mapFragmentRequestModel.getWebViewUrl(new CommonObserver<String>() {
//
//            @Override
//            protected void onError(String errorMsg) {
//                //业务处理
//                if (getmMvpView() != null) {
//                    getmMvpView().hide();
//                    getmMvpView().resultFailure(what, errorMsg);
//                }
//
//            }
//
//            @Override
//            protected void onSuccess(String s) {
//
//                //业务处理
//                if (getmMvpView() != null) {
//                    getmMvpView().resultSuccess(what, s);
//                    getmMvpView().hide();
//
//
//                }
//
//            }
//        });
//    }

}
