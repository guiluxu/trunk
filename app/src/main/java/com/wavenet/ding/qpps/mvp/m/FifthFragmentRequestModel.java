package com.wavenet.ding.qpps.mvp.m;


import com.dereck.library.base.IMvpBaseView;
import com.dereck.library.interceptor.Transformer;
import com.dereck.library.observer.CommonObserver;
import com.dereck.library.utils.RxHttpUtils;
import com.wavenet.ding.qpps.api.ApiService;
import com.wavenet.ding.qpps.utils.AppConfig;

public class FifthFragmentRequestModel implements IMvpBaseView {

    public void requestDDY(String Townids, String Role, CommonObserver<Object> callback) {
//        http://222.66.154.70:2056/api/Err/GetErrInfo?Townids=白鹤镇,夏阳街道&Role=区调度员
        String urlstr = String.format(AppConfig.BeasUrl+"2056/api/Err/GetErrInfo?Townids=%s&Role=%s",Townids,Role);

        RxHttpUtils
                .createApi(ApiService.class)
                .mGetUrl(urlstr)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);

    }
    public void getAPPCode(CommonObserver<Object> callback) {


        RxHttpUtils
                .createApi(ApiService.class)
                .getAPPverSionCode(AppConfig.BeasUrl+"2056/api/Version/V_APP_VERSION")
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);

    }

}
