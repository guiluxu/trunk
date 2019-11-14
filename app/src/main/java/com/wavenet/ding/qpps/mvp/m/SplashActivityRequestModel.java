package com.wavenet.ding.qpps.mvp.m;

import com.dereck.library.interceptor.Transformer;
import com.dereck.library.observer.CommonObserver;
import com.dereck.library.utils.RxHttpUtils;
import com.wavenet.ding.qpps.api.ApiService;
import com.wavenet.ding.qpps.utils.AppConfig;

public class SplashActivityRequestModel {

    public void getAPPCode(CommonObserver<Object> callback) {


        RxHttpUtils
                .createApi(ApiService.class)
                .getAPPverSionCode(AppConfig.BeasUrl+"2056/api/Version/V_APP_VERSION")
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);

    }

    public void getCheck(CommonObserver<Object> callback) {


        RxHttpUtils
                .createApi(ApiService.class)
                .getCheck(AppConfig.BeasUrl+"2081/odata/PSSSYH/default/APP_CONTROL")
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);

    }

}
