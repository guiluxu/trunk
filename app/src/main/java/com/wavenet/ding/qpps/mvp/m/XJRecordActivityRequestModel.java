package com.wavenet.ding.qpps.mvp.m;


import com.dereck.library.base.IMvpBaseView;
import com.dereck.library.http.SingleRxHttp;
import com.dereck.library.interceptor.Transformer;
import com.dereck.library.observer.CommonObserver;
import com.dereck.library.utils.RxHttpUtils;
import com.wavenet.ding.qpps.activity.MainMapXJActivity;
import com.wavenet.ding.qpps.api.ApiService;
import com.wavenet.ding.qpps.utils.AppConfig;
import com.wavenet.ding.qpps.utils.AppTool;

import java.util.HashMap;
import java.util.Map;

public class XJRecordActivityRequestModel implements IMvpBaseView {

    public void clickRequestTasklist(Map<String, Object> filterMap, CommonObserver<Object> callback) {

//        String urlstr = UrlUtils.toURLDecoded(SPUtils.get("url1", "http://222.66.154.70:2081") + "/odata/PSSSYH/default/T_PATROL_TASK?$select=S_MANGE_ID,S_NAME,T_CREATE,S_SOURCE,S_LOCAL,S_EMERGENCY,S_MANGE_ID_REL,S_TYPE,S_SOURCE,S_CATEGORY &$filter=S_MAN eq '" + S_MAN + "'" + filter+"&$top=10&$skip="+skip);
        //  String urlstr = "http://222.66.154.70:2081/odata/PSSSYH/default/T_PATROL_MANAGEMENT?$select=S_MANGE_ID,S_NAME,T_CREATE,S_SOURCE,S_LOCAL,S_EMERGENCY,S_MANGE_ID_REL,S_TYPE,S_SOURCE,S_CATEGORY,T_IN_DATE&$filter=S_MANGE_MAN eq '" + S_MAN + "'" + filter+"&$top=10";
//        if (skip!=0){
//            urlstr=urlstr+"&$skip="+skip;
//        }
        Map<String, Object> headerMaps = new HashMap<>();
        String urlStr = AppConfig.BeasUrl+"2081/services/QPSSYH_DATASERVICE/T_PATROL_MANAGEMENT_operation";
        //   Map<String, Object> reqParams = new HashMap<>();
        // reqParams.put("S_MANGE_MAN", SPUtils.get("user", ""));


        headerMaps.put("Accept", "application/json");

        SingleRxHttp.getInstance().addHeaders(headerMaps).createSApi(ApiService.class)

                .refuse(urlStr, filterMap)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);

    }

    public void RequestEndTask(String S_RECODE_ID, String S_MANGE_ID, CommonObserver<Object> callback) {
        String urlStr = AppConfig.BeasUrl+"2081/odata/PSSSYH/default/T_PATROL_RECODE('" + S_RECODE_ID + "')";
        Map<Object, Object> reqParams = new HashMap<>();
        reqParams.put("S_MANGE_ID", S_MANGE_ID);
        reqParams.put("N_MILEAGE", AppTool.getDoubleAccurate(MainMapXJActivity.Distance));
        RxHttpUtils
                .createApi(ApiService.class)
                .RequestXJEndTask(urlStr, reqParams)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);


    }

}
