package com.wavenet.ding.qpps.mvp.m;


import com.dereck.library.http.SingleRxHttp;
import com.dereck.library.interceptor.Transformer;
import com.dereck.library.observer.CommonObserver;
import com.dereck.library.utils.RxHttpUtils;
import com.wavenet.ding.qpps.api.ApiService;
import com.wavenet.ding.qpps.utils.AppConfig;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.LogUtils;
import com.wavenet.ding.qpps.utils.UrlUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainMapYHRequestModel {

    public void requestFile(Map<String, Object> map, ArrayList<String> arrayList, CommonObserver<Object> callback) {
        RxHttpUtils.uploadImgsWithParams(AppConfig.BeasUrl+"2083/file/upload/SSYH", "file", map, arrayList)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);


    }

    public void requestUPXY(Map<String, Object> map, CommonObserver<Object> callback) {
        String urlstr = AppConfig.BeasUrl+"2081/odata/PSSSYH/default/T_TRAJECTORY";
        RxHttpUtils
                .createApi(ApiService.class)
                .userXJCoordinate(urlstr, map)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);


    }

    public void requestRelyid(Map<String, Object> map, CommonObserver<Object> callback) {

        RxHttpUtils
                .createApi(ApiService.class)
                .userXJRelyid(AppConfig.BeasUrl+"2056/api/CuringRecode/AddCuringRecode", map)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);


    }

    public void getPicUrl(Map<String, Object> map, CommonObserver<Object> callback) {
        RxHttpUtils
                .createApi(ApiService.class)
                .getPicUrl(AppConfig.BeasUrl+"2083/file/find/SSYH", map)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);


    }


    public void userFinishTask(String id, Map<String, Object> map, CommonObserver<Object> callback) {
        map.put("S_RECODE_ID",id);
        RxHttpUtils
                .createApi(ApiService.class)
                .userFinishTask(AppConfig.BeasUrl+"2056/api/CuringRecode/UpdateCuringRecode", map)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);


    }


    public void userCancelTask(String id, Map<String, Object> map, CommonObserver<Object> callback) {
        map.put("S_RECODE_ID",id);
        RxHttpUtils
                .createApi(ApiService.class)
                .userFinishTask(AppConfig.BeasUrl+"2056/api/CuringRecode/UpdateCuringRecode", map)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);


    }
    public void AdminGetObjectIds(String idstr, String filterkey, String filtervalue ,String townname,CommonObserver<Object> callback) {
        String filerStr="";
        if (AppTool.isNull(townname)){
            filerStr=  String.format(AppConfig.Filterurlstrstart2,filterkey,"%",filtervalue,"%");
        }else {
            filerStr=  String.format(AppConfig.Filterurlstrstart1,townname,filterkey,"%",filtervalue,"%");
        }
        String urlstr =String.format(AppConfig.Filterurlstr,AppConfig.GuanWangUrl,idstr, UrlUtils.toURLEncoded(filerStr),AppConfig.Filterurlstrend);
        RxHttpUtils
                .createApi(ApiService.class)
                .AdminGetObjectIds(urlstr)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);

    }

    public void AdminGetObjectDetails(int url, String ids, String filterkey, CommonObserver<Object> callback) {
        String urlstr = AppConfig.GuanWangUrl + "/" + url + "/query?where=&text=&objectIds=" + ids + "&time=&geometry=&geometryType=esriGeometryEnvelope&inSR=&spatialRel=esriSpatialRelIntersects&relationParam=&outFields=" + filterkey + "&returnGeometry=true&maxAllowableOffset=&geometryPrecision=&outSR=&returnIdsOnly=false&returnCountOnly=false&orderByFields=&groupByFieldsForStatistics=&outStatistics=&returnZ=false&returnM=false&gdbVersion=&returnDistinctValues=false&returnTrueCurves=false&resultOffset=&resultRecordCount=&f=pjson";
        RxHttpUtils
                .createApi(ApiService.class)
                .AdminGetObjectDetails(urlstr)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);

    }

    public void requestPeople(String filterStr, CommonObserver<Object> callback) {
        String urlstr = AppConfig.BeasUrl+"2081/services/QPSSYH_DATASERVICE/t_person_operation?" + filterStr;
        Map<String, Object> headerMaps = new HashMap<>();
        headerMaps.put("Accept", "application/json");
        LogUtils.e("pppppppp",urlstr);
        SingleRxHttp.getInstance().addHeaders(headerMaps).createSApi(ApiService.class)

                .requestPeople(urlstr)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);

    }

    public void addSearchHis(String user, String str, CommonObserver<Object> callback) {
        String url = AppConfig.BeasUrl+"2056/api/AppSearchHis/AddAppSearch";
        Map<String, Object> params = new HashMap<>();
        params.put("AppType", "2");
        params.put("UserId", user);
        params.put("SearchValue", str);
        RxHttpUtils.createApi(ApiService.class)
                .addSearchHis(url, params)
                .compose(Transformer.<String>switchSchedulers())
                .subscribe(callback);
    }

    public void requestSearchHis(String user, CommonObserver<Object> callback) {
        String url = AppConfig.BeasUrl+"2056/api/AppSearchHis/GetAppSearch?AppType=2&UserId=" + user;
        RxHttpUtils.createApi(ApiService.class)
                .getSearchList(url)
                .compose(Transformer.<String>switchSchedulers())
                .subscribe(callback);
    }

    public void clearSearchHis(String user, CommonObserver<Object> callback) {
        String url = AppConfig.BeasUrl+"2056/api/AppSearchHis/ClearAppHis";
        Map<String, Object> params = new HashMap<>();
        params.put("AppType", "2");
        params.put("UserId", user);
        RxHttpUtils.createApi(ApiService.class)
                .clearHis(url, params)
                .compose(Transformer.<String>switchSchedulers())
                .subscribe(callback);
    }

}
