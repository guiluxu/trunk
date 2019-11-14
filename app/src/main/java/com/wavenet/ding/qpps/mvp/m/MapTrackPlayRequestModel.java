package com.wavenet.ding.qpps.mvp.m;

import com.dereck.library.interceptor.Transformer;
import com.dereck.library.observer.CommonObserver;
import com.dereck.library.utils.RxHttpUtils;
import com.wavenet.ding.qpps.api.ApiService;
import com.wavenet.ding.qpps.utils.AppConfig;

public class MapTrackPlayRequestModel {
    public void requestTrack(String S_RECODE_ID,CommonObserver<Object> callback) {

//        String urlstr = AppConfig.BeasUrl+"2056/api/T_TRAJECTORY/GetTrajectoryByMan"+S_RECODE_ID ;
        String urlstr = String.format(AppConfig.TrackUrl,S_RECODE_ID) ;
//        Map<String, Object> headerMaps = new HashMap<>();
//        headerMaps.put("Accept", "application/json");
//
//        SingleRxHttp.getInstance().addHeaders(headerMaps).createSApi(ApiService.class)
//
//                .requestTrack(urlstr)
//                .compose(Transformer.switchSchedulers())
//                .subscribe(callback);
//
        RxHttpUtils
                .createApi(ApiService.class)
                .requestTrack(urlstr)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);


    }
}