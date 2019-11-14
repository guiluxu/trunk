package com.wavenet.ding.qpps.mvp.m;


import android.content.Context;

import com.dereck.library.base.IMvpBaseView;
import com.dereck.library.http.SingleRxHttp;
import com.dereck.library.interceptor.Transformer;
import com.dereck.library.observer.CommonObserver;
import com.dereck.library.utils.RxHttpUtils;
import com.wavenet.ding.qpps.api.ApiService;
import com.wavenet.ding.qpps.utils.AppConfig;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.SPUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class XJDealActivityRequestModel implements IMvpBaseView {

    public void clickRequestIsDealDetail(String S_MANGE_ID, CommonObserver<Object> callback) {
        String urlStr = AppConfig.BeasUrl+"2056/api/Management/T_PATROL_MANAGEMENT_operation?S_MANGE_ID=" + S_MANGE_ID+ "&PAGE=1&PAGESIZE=1";
        Map<String, Object> headerMaps = new HashMap<>();
        headerMaps.put("Accept", "application/json");
        SingleRxHttp.getInstance().addHeaders(headerMaps).createSApi(ApiService.class)

                .userXJIsDealDetail(urlStr)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);


    }

    public void RequestReportDetailsPhoto(String S_RECODE_ID, CommonObserver<Object> callback) {

        String urlstr = AppConfig.BeasUrl+"2083/file/find/SJSB?relyid=" + S_RECODE_ID;
        RxHttpUtils
                .createApi(ApiService.class)
                .RequestXJDealDetails(urlstr)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);

    }


    public void clickTaskDeal(Context mContext,String S_MANGE_ID, String S_MANGE_MAN, String T_MANGE_TIME, String S_MANGE_REMARK, CommonObserver<Object> callback) {
//        String urlStr = AppConfig.BeasUrl+"2081/odata/PSSSYH/default/T_PATROL_MANAGEMENT('" + S_MANGE_ID + "')";
        String urlStr = AppConfig.BeasUrl+"2056/api/Management/EdtManagement";
        Map<String, String> reqParams = new HashMap<>();
        reqParams.put("S_MANGE_ID", S_MANGE_ID);
        reqParams.put("S_MANGE_MAN", S_MANGE_MAN);
        reqParams.put("T_MANGE_TIME", T_MANGE_TIME);
//        reqParams.put("S_IS_MANGE", "1");//处置
        reqParams.put("S_IS_MANGE", "W1001600001");//处置
        reqParams.put("S_SJCZ_ID", S_MANGE_ID);
        reqParams.put("S_STATUS", "W1006500004");

        reqParams.put("S_TOWNID_MANGE", SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_TOWNID));
        reqParams.put("S_COMPANY_MANGE", SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_COMPANY));
        if (!AppTool.isNull(S_MANGE_REMARK)) {
            reqParams.put("S_MANGE_REMARK", S_MANGE_REMARK);//
        }
        RxHttpUtils
                .createApi(ApiService.class)
                .userXJTaskDeal(urlStr, reqParams)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);


    }
    public void FileRequest(final int file, Map<String, Object> map, ArrayList<String> arrayList, CommonObserver<Object> callback) {
        String url = AppConfig.BeasUrl+"2083/file/upload/SJSB";
        if (file == 6) {
            url = AppConfig.BeasUrl+"2083/file/upload/SJSB";//上报，补录
        } else if (file == 61||file == 63) {
            url = AppConfig.BeasUrl+"2083/file/upload/SJCZ";
        } else if (file == 62) {
            url = AppConfig.BeasUrl+"2083/file/upload/SJSB";
        }
        RxHttpUtils.uploadImgsWithParams(url, "file", map, arrayList)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);


    }
}
