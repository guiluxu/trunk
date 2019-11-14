package com.wavenet.ding.qpps.mvp.m;

import android.content.Context;

import com.dereck.library.interceptor.Transformer;
import com.dereck.library.observer.CommonObserver;
import com.dereck.library.utils.RxHttpUtils;
import com.wavenet.ding.qpps.api.ApiService;
import com.wavenet.ding.qpps.utils.AppConfig;
import com.wavenet.ding.qpps.utils.SPUtil;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivityRequestModel {

    public void changePassword(Context mContext ,String NewPassword, String OldPassword, String username,boolean isloginper,CommonObserver<String> callback) {
        Map<String, Object> reqParams = new HashMap<>();
        reqParams.put("NewPassword", NewPassword);
        reqParams.put("OldPassword", OldPassword);
        if (isloginper){
            reqParams.put("Account", username);
        }else {
            reqParams.put("Account", SPUtil.getInstance(mContext).getStringValue(SPUtil.USERNO));
        }


        RxHttpUtils
                .createApi(ApiService.class)
                .changePassword(AppConfig.BeasUrl+"2088/Account/APPChangePassword",reqParams)
                .compose(Transformer.<String>switchSchedulers())
                .subscribe(callback);



    }



}