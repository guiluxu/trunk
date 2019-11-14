package com.wavenet.ding.qpps.mvp.p;

import android.content.Context;

import com.dereck.library.base.BaseMvpPersenter;
import com.dereck.library.observer.CommonObserver;
import com.wavenet.ding.qpps.mvp.m.ChangePasswordActivityRequestModel;
import com.wavenet.ding.qpps.mvp.v.ChangePasswordActivityRequestView;

public class ChangePasswordRequestPresenter extends BaseMvpPersenter<ChangePasswordActivityRequestView> {

    private final ChangePasswordActivityRequestModel changePasswordActivityRequestModel;

    public ChangePasswordRequestPresenter() {
        this.changePasswordActivityRequestModel = new ChangePasswordActivityRequestModel();
    }

    public void changePassword(Context mContext , String NewPassword, String OldPassword,String username,boolean isloginper) {
        if (getmMvpView() != null) {
            getmMvpView().show();
        }

        changePasswordActivityRequestModel.changePassword(mContext,NewPassword, OldPassword,username,isloginper ,new CommonObserver<String>() {

            @Override
            protected void onError(String errorMsg) {
                //业务处理
                if (getmMvpView() != null) {
                    getmMvpView().hide();
                    getmMvpView().resultFailure(1, errorMsg);
                }

            }

            @Override
            protected void onSuccess(String s) {

                //业务处理
                if (getmMvpView() != null) {
                    getmMvpView().resultSuccess(1, s);
                    getmMvpView().hide();


                }

            }
        });
    }



}