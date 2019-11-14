package com.wavenet.ding.qpps.mvp.p;


import android.content.Context;

import com.dereck.library.base.BaseMvpPersenter;
import com.dereck.library.observer.CommonObserver;
import com.wavenet.ding.qpps.mvp.m.LoginActivityRequestModel;
import com.wavenet.ding.qpps.mvp.v.LoginActivityRequestView;

import okhttp3.ResponseBody;

public class LoginRequestPresenter extends BaseMvpPersenter<LoginActivityRequestView> {

    private final LoginActivityRequestModel loginActivityRequestModel;

    public LoginRequestPresenter() {
        this.loginActivityRequestModel = new LoginActivityRequestModel();
    }

    public void clickRequest(String userName, String passWord) {
        if (getmMvpView() != null) {
            getmMvpView().show();
        }

        loginActivityRequestModel.request(userName, passWord, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                //业务处理
                if (getmMvpView() != null) {
                    getmMvpView().hide();
                    getmMvpView().resultFailure(errorMsg);
                }

            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                if (getmMvpView() != null) {
                    getmMvpView().resultSuccess(s);
                    getmMvpView().hide();
                }

            }
        });
    }public void FileRequest(Context mContext ) {

        if (getmMvpView() != null) {
            getmMvpView().show();
        }
        loginActivityRequestModel.FileRequest( mContext, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                if (getmMvpView() != null) {
                    getmMvpView().hide();
                    getmMvpView().resultFailureCrash(errorMsg);
                }
            }

            @Override
            protected void onSuccess(Object responseBody) {
                if (getmMvpView() != null) {
                    try {
                        if (getmMvpView() != null) {
                            ResponseBody b = (ResponseBody) responseBody;
                            getmMvpView().resultSuccessCrash(b.string());
                            getmMvpView().hide();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


}
