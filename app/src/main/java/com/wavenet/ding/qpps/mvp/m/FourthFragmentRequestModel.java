package com.wavenet.ding.qpps.mvp.m;


import com.dereck.library.base.IMvpBaseView;
import com.dereck.library.http.SingleRxHttp;
import com.dereck.library.interceptor.Transformer;
import com.dereck.library.observer.CommonObserver;
import com.dereck.library.utils.RxHttpUtils;
import com.wavenet.ding.qpps.api.ApiService;
import com.wavenet.ding.qpps.utils.AppConfig;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.LogUtils;
import com.wavenet.ding.qpps.utils.UrlUtils;

import java.util.HashMap;
import java.util.Map;

public class FourthFragmentRequestModel implements IMvpBaseView {

    public void AdminGetObjectIds(String idstr, String filterkey, String filtervalue ,String townname,CommonObserver<Object> callback) {
        String filerStr="";
        if (AppTool.isNull(townname)){
            filerStr=  String.format(AppConfig.Filterurlstrstart2,filterkey,"%",filtervalue,"%");
        }else {
            filerStr=  String.format(AppConfig.Filterurlstrstart1Admin,townname,filterkey,"%",filtervalue,"%");
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
        LogUtils.d("qingpu", "filterStr = "+filterStr);
        String urlstr =AppConfig.BeasUrl+ "2081/services/QPSSYH_DATASERVICE/t_person_operation?" + filterStr;
        Map<String, Object> headerMaps = new HashMap<>();
        headerMaps.put("Accept", "application/json");
        SingleRxHttp.getInstance().addHeaders(headerMaps).createSApi(ApiService.class)

                .requestPeople(urlstr)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);

    }

    public void requestPeople2(String filterStr, CommonObserver<Object> callback) {
        LogUtils.d("qingpu", "filterStr = "+filterStr);
        String urlstr = "";
            urlstr = AppConfig.BeasUrl+"2056/api/T_Person/GetPersonStatus?" + filterStr;
        Map<String, Object> headerMaps = new HashMap<>();
        headerMaps.put("Accept", "application/json");
        SingleRxHttp.getInstance().addHeaders(headerMaps).createSApi(ApiService.class)

                .requestPeople2(urlstr)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);

    }
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

    public void requestAdminHead(String filterStr, CommonObserver<Object> callback) {
        String urlstr =AppConfig.BeasUrl+ "2081/services/QPSSYH_DATASERVICE/t_person_operation?" + filterStr;
        Map<String, Object> headerMaps = new HashMap<>();
        headerMaps.put("Accept", "application/json");
        SingleRxHttp.getInstance().addHeaders(headerMaps).createSApi(ApiService.class)
                .getAdminHeader(urlstr)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);
    }

    public void addSearchHis(String user, String str, CommonObserver<Object> callback) {
        String url = AppConfig.BeasUrl+"2056/api/AppSearchHis/AddAppSearch";
        Map<String, Object> params = new HashMap<>();
        params.put("AppType", "3");
        params.put("UserId", user);
        params.put("SearchValue", str);
        RxHttpUtils.createApi(ApiService.class)
                .addSearchHis(url, params)
                .compose(Transformer.<String>switchSchedulers())
                .subscribe(callback);
    }

    public void requestSearchHis(String user, CommonObserver<Object> callback) {
        String url = AppConfig.BeasUrl+"2056/api/AppSearchHis/GetAppSearch?AppType=3&UserId=" + user;
        RxHttpUtils.createApi(ApiService.class)
                .getSearchList(url)
                .compose(Transformer.<String>switchSchedulers())
                .subscribe(callback);
    }

    public void clearSearchHis(String user, CommonObserver<Object> callback) {
        String url = AppConfig.BeasUrl+"2056/api/AppSearchHis/ClearAppHis";
        Map<String, Object> params = new HashMap<>();
        params.put("AppType", "3");
        params.put("UserId", user);
        RxHttpUtils.createApi(ApiService.class)
                .clearHis(url, params)
                .compose(Transformer.<String>switchSchedulers())
                .subscribe(callback);
    }



}
