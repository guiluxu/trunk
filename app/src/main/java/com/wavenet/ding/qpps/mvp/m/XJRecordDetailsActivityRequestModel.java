package com.wavenet.ding.qpps.mvp.m;


import com.dereck.library.base.IMvpBaseView;
import com.dereck.library.interceptor.Transformer;
import com.dereck.library.observer.CommonObserver;
import com.dereck.library.utils.RxHttpUtils;
import com.wavenet.ding.qpps.api.ApiService;
import com.wavenet.ding.qpps.utils.AppConfig;
import com.wavenet.ding.qpps.utils.LogUtils;

public class XJRecordDetailsActivityRequestModel implements IMvpBaseView {

    public void RequestReportDetails(String S_MANGE_ID, CommonObserver<Object> callback) {

        String urlstr = AppConfig.BeasUrl+"2056/api/Management/T_PATROL_MANAGEMENT_operation?S_MANGE_ID=" + S_MANGE_ID + "&PAGE=1&PAGESIZE=1";
        RxHttpUtils
                .createApi(ApiService.class)
                .RequestXJDealDetails(urlstr)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);

    }

    //获取附件照片
    public void RequestFileDetailsPhoto(int file, String S_RECODE_ID, CommonObserver<Object> callback) {

//        LogUtils.e("S_RECODE_ID", S_RECODE_ID);

        String urlstr = "";//file 201上报   202处置
        if (file == 201) {
            urlstr =AppConfig.BeasUrl+":2083/file/find/SJSB?relyid=" + S_RECODE_ID;
        } else if (file == 202) {
            urlstr =AppConfig.BeasUrl+"2083/file/find/SJCZ?relyid=" + S_RECODE_ID;
        }
        LogUtils.e("dddddddddddd", urlstr);

        RxHttpUtils
                .createApi(ApiService.class)
                .RequestXJDealDetails(urlstr)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);

    }

    public void RequestRefuselist(String S_MANGE_ID, CommonObserver<Object> callback) {

        String urlstr  =AppConfig.BeasUrl+ "2056/api/PatrolRecode/V_APP_JJXX?S_TASK_ID="+S_MANGE_ID;
        RxHttpUtils
                .createApi(ApiService.class)
                .RequestXJDealDetails(urlstr)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);

    }

    public void RequestTDlist(String S_MANGE_ID, CommonObserver<Object> callback) {
        String urlstr = AppConfig.BeasUrl+"2056/api/PatrolRecode/V_APP_TDXX?S_TASK_ID="+S_MANGE_ID;

        RxHttpUtils
                .createApi(ApiService.class)
                .RequestXJDealDetails(urlstr)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);

    }


}
