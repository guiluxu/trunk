package com.wavenet.ding.qpps.mvp.m;

import com.dereck.library.http.SingleRxHttp;
import com.dereck.library.interceptor.Transformer;
import com.dereck.library.observer.CommonObserver;
import com.wavenet.ding.qpps.api.ApiService;

import java.util.HashMap;
import java.util.Map;

public class JCMapActivityRequestModel {

    public void getJCData_id(String url, CommonObserver<Object> callback) {
        String urlstr = "";
        Map<String, Object> headerMaps = new HashMap<>();
        headerMaps.put("Accept", "application/json");
        SingleRxHttp.getInstance().addHeaders(headerMaps).createSApi(ApiService.class)
                .getJCData_id(url)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);

    }public void getJCData_his(String url, CommonObserver<Object> callback) {
        String urlstr = "";
        Map<String, Object> headerMaps = new HashMap<>();
        headerMaps.put("Accept", "application/json");
        SingleRxHttp.getInstance().addHeaders(headerMaps).createSApi(ApiService.class)
                .getJCData_his(url)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);

    }


}