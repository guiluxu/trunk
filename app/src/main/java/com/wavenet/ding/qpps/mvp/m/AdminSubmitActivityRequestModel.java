package com.wavenet.ding.qpps.mvp.m;

import android.content.Context;

import com.dereck.library.base.IMvpBaseView;
import com.dereck.library.interceptor.Transformer;
import com.dereck.library.observer.CommonObserver;
import com.dereck.library.utils.RxHttpUtils;
import com.dereck.library.utils.ToastUtils;
import com.wavenet.ding.qpps.api.ApiService;
import com.wavenet.ding.qpps.utils.AppConfig;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.SPUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminSubmitActivityRequestModel implements IMvpBaseView {
    public void clickRequestClasbig(CommonObserver<Object> callback) {
//        String urlstr = AppConfig.BeasUrl+"2081/odata/PSSSYH/default/T_DICT?$select=S_CORRESPOND,S_VALUE&$filter=S_CODE%20eq%20%27W10026%27";
        String urlstr = AppConfig.BeasUrl+"2056/api/T_Dict/GetT_Dict?S_Code=W10026";
        RxHttpUtils
                .createApi(ApiService.class)
                .userXJgetClasbig(urlstr)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);

    }
    public void clickRequestClassmall(String clasbig, CommonObserver<Object> callback) {
//        clasbig =AppConfig.BeasUrl+"2081/odata/PSSSYH/default/T_DICT?$select=S_CORRESPOND,S_VALUE&$filter=S_PCODE eq '" + clasbig + "'";
        clasbig =AppConfig.BeasUrl+"2056/api/T_Dict/GetT_Dict?S_PCODE=" + clasbig ;
        RxHttpUtils
                .createApi(ApiService.class)
                .userXJgetClassmall(clasbig)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);
    }

    public void clickRequestSource( CommonObserver<Object> callback) {
        String urlstr = AppConfig.BeasUrl+"2056/api/T_Dict/GetT_Dict?S_Code=W10075";
        RxHttpUtils
                .createApi(ApiService.class)
                .userXJgetSource(urlstr)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);
    }

    public void clickRequestUPTask(Context mContext, String S_DESC, String S_MANGE_ID, String S_RECODE_ID, String S_CATEGORY, String S_TYPE, String S_IN_MAN, String T_IN_DATE, double N_X, double N_Y, String S_LOCAL, String S_SOURCE, String S_TOWNID_IN, CommonObserver<Object> callback) {
//        String urlstr = "http://172.18.0.21:2056/api/Management/AddManagement";
        String urlstr = AppConfig.BeasUrl+"2056/api/Management/AddManagement";
        Map<String, Object> reqParams = new HashMap<>();
        reqParams.put("S_MANGE_ID", S_MANGE_ID);
        reqParams.put("S_RECODE_ID", S_RECODE_ID);
        reqParams.put("S_CATEGORY", S_CATEGORY);
        reqParams.put("S_TYPE", S_TYPE);
        if (AppTool.isNull(S_IN_MAN)) {
            ToastUtils.showToast("上报人为空");
            return;
        }
        reqParams.put("S_IN_MAN", S_IN_MAN);
        reqParams.put("T_IN_DATE", T_IN_DATE);
        reqParams.put("N_X", N_X);
        reqParams.put("N_Y", N_Y);
        reqParams.put("S_IS_MANGE", "W1001600000");//为处置
        reqParams.put("S_SJSB_ID", S_MANGE_ID);
        reqParams.put("S_DESC", S_DESC);
        reqParams.put("S_LOCAL", S_LOCAL);
        reqParams.put("S_LOCAL", S_LOCAL);
        reqParams.put("S_TOWNID_IN", S_TOWNID_IN);
        reqParams.put("S_COMPANY_IN", SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_COMPANY));
        reqParams.put("S_STATUS", "W1006500001");
        reqParams.put("S_SOURCE", S_SOURCE );
        reqParams.put("S_EMERGENCY", "W1008100001" );

        RxHttpUtils
                .createApi(ApiService.class)
                .userXJReportData2(urlstr, reqParams)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);
    }

    public void FileRequest(final int file, Map<String, Object> map, ArrayList<String> arrayList, CommonObserver<Object> callback) {
        String url = AppConfig.BeasUrl+"2083/file/upload/SJSB";
        if (file == 6) {
            url = AppConfig.BeasUrl+"2083/file/upload/SJSB";//上报，补录
        } else if (file == 61||file == 63) {
            url =AppConfig.BeasUrl+"2083/file/upload/SJCZ";
        } else if (file == 62) {
            url = AppConfig.BeasUrl+"2083/file/upload/SJSB";
        }
        RxHttpUtils.uploadImgsWithParams(url, "file", map, arrayList)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);


    }
}
