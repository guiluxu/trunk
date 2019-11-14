package com.wavenet.ding.qpps.mvp.m;

import com.dereck.library.http.SingleRxHttp;
import com.dereck.library.interceptor.Transformer;
import com.dereck.library.observer.CommonObserver;
import com.dereck.library.utils.RxHttpUtils;
import com.wavenet.ding.qpps.api.ApiService;
import com.wavenet.ding.qpps.utils.AppConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ErrorcorrectionActivityRequestModel {
    public void requestUPJiucuo(String FACSTATE, String FACNAME, String N_X, String N_Y, String ADDRESS, String REMARK, String PHONE, String RELYID, String DEVICEID,String CREATEMAN ,CommonObserver<Object> callback) {
        String urlstr = AppConfig.BeasUrl+"2056/api/Err/AddErr";
        Map<String, Object> reqParams = new HashMap<>();
        Map<String, Object> headerMaps = new HashMap<>();
        reqParams.put("FACSTATE", FACSTATE);
        reqParams.put("FACNAME", FACNAME);
        reqParams.put("N_X", N_X);
        reqParams.put("N_Y", N_Y);
        reqParams.put("ADDRESS", ADDRESS);
        reqParams.put("REMARK", REMARK);
        reqParams.put("PHONE", PHONE);
        reqParams.put("RELYID", RELYID);
        reqParams.put("DEVICEID", DEVICEID);
        reqParams.put("CREATEMAN", CREATEMAN);
        headerMaps.put("Accept", "application/json");

        SingleRxHttp.getInstance().addHeaders(headerMaps).createSApi(ApiService.class)

                .mPostUrlMap(urlstr, reqParams)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);
//
//        RxHttpUtils
//                .createApi(ApiService.class)
//                .mPostUrlMap(urlstr, reqParams)
//                .compose(Transformer.switchSchedulers())
//                .subscribe(callback);


    }
    public void FileRequest(final int file, Map<String, Object> map, ArrayList<String> arrayList, CommonObserver<Object> callback) {
        String url =AppConfig.BeasUrl+ "2083/file/upload/SSJC" ;
        RxHttpUtils.uploadImgsWithParams(url, "file", map, arrayList)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);


    }



}