package com.wavenet.ding.qpps.mvp.m;


import android.content.Context;
import android.util.Log;

import com.dereck.library.base.IMvpBaseView;
import com.dereck.library.http.SingleRxHttp;
import com.dereck.library.interceptor.Transformer;
import com.dereck.library.observer.CommonObserver;
import com.dereck.library.utils.RxHttpUtils;
import com.dereck.library.utils.ToastUtils;
import com.wavenet.ding.qpps.activity.MainMapXJActivity;
import com.wavenet.ding.qpps.api.ApiService;
import com.wavenet.ding.qpps.db.WavenetCallBack;
import com.wavenet.ding.qpps.utils.AppAttribute;
import com.wavenet.ding.qpps.utils.AppConfig;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.GetUTCTime;
import com.wavenet.ding.qpps.utils.LogUtils;
import com.wavenet.ding.qpps.utils.SPUtil;
import com.wavenet.ding.qpps.utils.UrlUtils;
import com.wavenet.ding.qpps.view.ControllerMainUIView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.zhy.http.okhttp.OkHttpUtils.post;

public class MainMapXJActivityRequestModel implements IMvpBaseView {

    public void requestUPloca(String S_ID, String S_MAN_ID, String T_UPLOAD, double N_X, double N_Y, double N_SPEED, String S_TASK_TYPE, String S_RECORD_ID, double N_MILEAGE, WavenetCallBack callback) {
        String urlstr = AppConfig.BeasUrl1+"/trajectory/addTrajectory";
        Map<String, String> reqParams = new HashMap<>();
        reqParams.put("sId", S_ID);
        reqParams.put("sManId", S_MAN_ID);
        reqParams.put("tUpload", T_UPLOAD);
        reqParams.put("nx", String.valueOf(N_X));
        reqParams.put("ny", String.valueOf(N_Y));
        reqParams.put("nSpeed",  String.valueOf(N_SPEED));
        reqParams.put("sTaskType", S_TASK_TYPE);
        reqParams.put("sRecordId", S_RECORD_ID);
        reqParams.put("nMilie", String.valueOf(N_MILEAGE));
        post()
                .url(urlstr)
                .params(reqParams)
                .build()
                .execute(callback);
    }

    public void clickRequestUPTask(Context mContext, String S_DESC, String S_MANGE_ID, String S_RECODE_ID, String S_CATEGORY, String S_TYPE, String S_IN_MAN, String T_IN_DATE, double N_X, double N_Y, String S_LOCAL, WavenetCallBack callback) {
        String urlstr =AppConfig.BeasUrl1+ "/management/addManagement";
        Map<String, String> reqParams = new HashMap<>();
        reqParams.put("sMangeId", S_MANGE_ID);//S_MANGE_ID
        reqParams.put("sRecodeId", S_RECODE_ID);//S_RECODE_ID
        reqParams.put("sCategory", S_CATEGORY);//S_CATEGORY
        reqParams.put("sType", S_TYPE);//S_TYPE
        if (AppTool.isNull(S_IN_MAN)) {
            ToastUtils.showToast("上报人为空");
            return;
        }

        reqParams.put("sEmergency", "W1008100001");//S_EMERGENCY
        reqParams.put("sInMan", S_IN_MAN);//S_IN_MAN
        reqParams.put("tInDate", T_IN_DATE);//T_IN_DATE
        reqParams.put("nx", String.valueOf(N_X));//N_X
        reqParams.put("ny", String.valueOf(N_Y));//N_Y
        reqParams.put("sIsMange", "W1001600000");//为处置//S_IS_MANGE
        reqParams.put("sSjsbId", S_MANGE_ID);//S_SJSB_ID
        reqParams.put("sDesc", S_DESC);//S_DESC
        reqParams.put("sSource", "W1007500004");//S_SOURCE
        reqParams.put("sLocal", S_LOCAL);//S_LOCAL
        reqParams.put("sTownidIn", SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_TOWNID));//S_TOWNID_IN
        reqParams.put("sCompanyIn", SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_COMPANY));//S_COMPANY_IN
        reqParams.put("sStatue", "W1006500001");//S_STATUS

        for (Map.Entry<String, String> entry : reqParams.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println("clickRequestUPTask：  " + "Key = " + key + "  ----  " + "Value = " + value);
        }

//        RxHttpUtils
//                .createApi(ApiService.class)
//                .userXJReportData(urlstr, reqParams)
//                .compose(Transformer.switchSchedulers())
//                .subscribe(callback);

        post()
                .url(urlstr)
                .params(reqParams)
                .build()
                .execute(callback);
    }

    public void clickRequestClasbig(WavenetCallBack callback) {

//        String urlstr = AppConfig.BeasUrl+"2081/odata/PSSSYH/default/T_DICT?$select=S_CORRESPOND,S_VALUE&$filter=S_CODE%20eq%20%27W10026%27";
        String urlstr = AppConfig.BeasUrl1+"/tDict/getTDict";
        Map<String, String> reqParams = new HashMap<>();
        reqParams.put("sCode", "W10026");
        post()
                .url(urlstr)
                .params(reqParams)
                .build()
                .execute(callback);
//        RxHttpUtils
//                .createApi(ApiService.class)
//                .userXJReportData(urlstr,reqParams)
//                .compose(Transformer.switchSchedulers())
//                .subscribe(callback);


    }

    public void clickRequestClassmall(String clasbig, WavenetCallBack callback) {
//        clasbig = AppConfig.BeasUrl+"2081/odata/PSSSYH/default/T_DICT?$select=S_CORRESPOND,S_VALUE&$filter=S_PCODE eq '" + clasbig + "'";
        String urlstr = AppConfig.BeasUrl1+"/tDict/getTDict";
        Map<String, String> reqParams = new HashMap<>();
        reqParams.put("sPcode", clasbig);
        post()
                .url(urlstr)
                .params(reqParams)
                .build()
                .execute(callback);
//        RxHttpUtils
//                .createApi(ApiService.class)
//                .userXJgetClassmall(clasbig)
//                .compose(Transformer.switchSchedulers())
//                .subscribe(callback);

    }

    public void clickRequestTasklist(String S_MAN, String S_STATUS, WavenetCallBack callback) {

//        String urlstr =AppConfig.BeasUrl+ "2081/odata/PSSSYH/default/V_PATROL_MANAGEMENT?$select=S_MANGE_ID,S_NAME,T_IN_DATE,S_SOURCE,S_CATEGORY,S_TYPE,T_CREATE,S_LOCAL,S_EMERGENCY,S_SJSB_ID,S_STATUS,S_SJCZ_ID,N_X,N_Y,S_COMPANY_MANGE,IS_SJSB_FJ,IS_SJCZ_FJ,IS_TD,IS_JJ,S_SOURCE_CN,S_EMERGENCY_CN,S_STATUS_CN,S_DESC,S_CATEGORY_CN,S_TYPE_CN&$filter=S_MANGE_MAN eq '" + S_MAN + "' and S_STATUS eq 'W1006500002' and S_DELETE eq 1";
        String urlstr =AppConfig.BeasUrl1+ "/management/vPatrolManagement";
        Map<String, String> reqParams = new HashMap<>();
        reqParams.put("sMan", S_MAN);
        post()
                .url(urlstr)
                .params(reqParams)
                .build()
                .execute(callback);

    }

    //事件上报（文件）
    public void FileRequest(final int file, Map<String, Object> map, ArrayList<String> arrayList, CommonObserver<Object> callback) {
        String url =AppConfig.BeasUrl+ "2083/file/upload/SJSB";
        if (file == 6) {
            url = AppConfig.BeasUrl+"2083/file/upload/SJSB";//上报，补录
        } else if (file == 61 || file == 63) {
            url = AppConfig.BeasUrl+"2083/file/upload/SJCZ";
        } else if (file == 62) {
            url = AppConfig.BeasUrl+"2083/file/upload/SJSB";
        }
        RxHttpUtils.uploadImgsWithParams(url, "file", map, arrayList)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);


    }

    public void clickTaskStart(Context context, String S_MAN_ID, String S_RECODE_ID, String T_IN_DATE, String N_CYCLE, CommonObserver<Object> callback) {
        String urlstr =AppConfig.BeasUrl+ "2056/api/PatrolRecode/AddT_Patrol_Recode";
        //流水号，系统时间， 暂时默认1
        Map<Object, Object> reqParams = new HashMap<>();
        reqParams.put("S_RECODE_ID", S_RECODE_ID);
        reqParams.put("T_START", T_IN_DATE);
        //reqParams.put("N_CYCLE", 1);
        reqParams.put("S_MAN_ID", S_MAN_ID);
        reqParams.put("S_TOWNID", SPUtil.getInstance(context).getStringValue(SPUtil.APP_TOWNID));
        reqParams.put("S_COMPANY",
                UrlUtils.toURLDecoded(SPUtil.getInstance(context).getStringValue(SPUtil.APP_COMPANY))
        );
        reqParams.put("S_TOWNNAME",
                UrlUtils.toURLDecoded(SPUtil.getInstance(context).getStringValue(SPUtil.APP_TOWNNAME))
        );
        reqParams.put("S_MAN_CN",
                UrlUtils.toURLDecoded(SPUtil.getInstance(context).getStringValue(SPUtil.APP_MYNAME))
        );

        RxHttpUtils
                .createApi(ApiService.class)
                .userXJcTaskStart(urlstr, reqParams)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);

        for (Map.Entry<Object, Object> entry : reqParams.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            System.out.println("clickTaskStart：  " + "Key = " + key + "  ----  " + "Value = " + value);
        }
    }

    public void clickTaskDeal(Context mContext, String S_MANGE_ID, String S_MANGE_MAN, String T_MANGE_TIME, String S_MANGE_REMARK, CommonObserver<Object> callback) {
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
        if (!AppTool.isNull(S_MANGE_REMARK)) {
            reqParams.put("S_MANGE_REMARK", S_MANGE_REMARK);//
        }

        reqParams.put("S_TOWNID_MANGE", SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_TOWNID));
        reqParams.put("S_COMPANY_MANGE", SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_COMPANY));
        RxHttpUtils
                .createApi(ApiService.class)
                .userXJTaskDeal(urlStr, reqParams)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);


    }



    public void clickTaskDeal2(double x, Context mContext, String S_MANGE_ID, String S_MANGE_MAN, String T_MANGE_TIME, String S_SJCZ_ID, String S_MANGE_REMARK, CommonObserver<Object> callback) {
        String urlStr = AppConfig.BeasUrl+"2056/api/PatrolRecode/TaskComplete";
        Map<String, Object> reqParams = new HashMap<>();
        Map<String, Object> headerMaps = new HashMap<>();
        reqParams.put("T_END", GetUTCTime.getNow());

        reqParams.put("S_MANGE_ID", S_MANGE_ID);
        reqParams.put("S_MANGE_MAN", S_MANGE_MAN);
        if (!AppTool.isNull(S_MANGE_REMARK)) {
            reqParams.put("S_MANGE_REMARK", S_MANGE_REMARK);//
        }
        reqParams.put("S_STATUS", "W1006500004");

        //N_MILEAGE   里程   单位米
        reqParams.put("N_MILEAGE", x);
        reqParams.put("N_DEL", 1);
        reqParams.put("S_SJSB_ID", MainMapXJActivity.S_SJSB_ID);//派单任务记录上报id
        reqParams.put("S_SJCZ_ID", S_MANGE_ID);//派单任务记录处置id
        reqParams.put("S_RECODE_ID", S_SJCZ_ID);//自动生成的s_recode_id

        reqParams.put("S_MANGE_FULL", SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_MYNAME));
        reqParams.put("S_TOWNID_MANGE", SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_TOWNID));
        reqParams.put("S_COMPANY_MANGE", SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_COMPANY));
        reqParams.put("S_MANGE_MAN", SPUtil.getInstance(mContext).getStringValue(SPUtil.USERNO));
        reqParams.put("T_MANGE_TIME", GetUTCTime.getNow());
        LogUtils.e("数据", reqParams.toString());
//        headerMaps.put("Accept", "application/json");

        RxHttpUtils
                .createApi(ApiService.class)
                .userXJTaskDeal3(urlStr, reqParams)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);
//        SingleRxHttp
//                .getInstance().addHeaders(headerMaps).createSApi(ApiService.class)
//
//                .userXJTaskDeal3(urlStr, reqParams)
//                .compose(Transformer.switchSchedulers())
//                .subscribe(callback);


    }

    public void clickRequestTaskAdd(String S_MANGE_ID, String S_RECODE_ID, String S_CATEGORY, String S_TYPE, String S_IN_MAN, String T_IN_DATE, CommonObserver<Object> callback) {
        String urlstr = AppConfig.BeasUrl+"2081/odata/PSSSYH/default/T_PATROL_MANAGEMENT";
        Map<String, Object> reqParams = new HashMap<>();
        reqParams.put("S_MANGE_ID", S_MANGE_ID);
        reqParams.put("S_RECODE_ID", S_RECODE_ID);
        reqParams.put("S_CATEGORY", S_CATEGORY);
        reqParams.put("S_TYPE", S_TYPE);
        reqParams.put("S_IN_MAN", S_IN_MAN);
        reqParams.put("T_IN_DATE", T_IN_DATE);
//        reqParams.put("S_IS_MANGE", "0");//为处置
        reqParams.put("S_IS_MANGE", "W1001600000");//为处置

        RxHttpUtils
                .createApi(ApiService.class)
                .userXJReportData(urlstr, reqParams)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);


    }

    public void clickTaskPaiStart(String S_MAN_ID, String S_RECODE_ID, String S_MANGE_ID, String T_IN_DATE, String N_CYCLE, CommonObserver<Object> callback) {
        String urlstr = AppConfig.BeasUrl+"2081/odata/PSSSYH/default/T_PATROL_RECODE";
        Map<Object, Object> reqParams = new HashMap<>();
        reqParams.put("S_RECODE_ID", S_RECODE_ID);
        reqParams.put("S_MANGE_ID", S_MANGE_ID);
        reqParams.put("T_IN_DATE", T_IN_DATE);
        // reqParams.put("N_CYCLE", 1);
        reqParams.put("S_MAN_ID", S_MAN_ID);

        RxHttpUtils
                .createApi(ApiService.class)
                .userXJReportDataPai(urlstr, reqParams)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);


    }

    public void clickTaskPaiStart1(Context context, String S_MAN_ID, String S_RECODE_ID, String S_MANGE_ID, String T_IN_DATE, String N_CYCLE, CommonObserver<Object> callback) {
        String urlstr = AppConfig.BeasUrl+"2056/api/PatrolRecode/TaskBegin";
        Map<String, Object> reqParams = new HashMap<>();
        reqParams.put("S_RECODE_ID", S_RECODE_ID);
        reqParams.put("S_MANGE_ID", S_MANGE_ID);

//        reqParams.put("S_TASK_ID", S_MANGE_ID);
        reqParams.put("T_START", GetUTCTime.getNow());
        // reqParams.put("N_CYCLE", 1);
        reqParams.put("S_MAN_ID", S_MAN_ID);
        reqParams.put("S_TOWNID", SPUtil.getInstance(context).getStringValue(SPUtil.APP_TOWNID));
        reqParams.put("S_COMPANY",
                UrlUtils.toURLDecoded(SPUtil.getInstance(context).getStringValue(SPUtil.APP_COMPANY))
        );
        reqParams.put("S_TOWNNAME",
                UrlUtils.toURLDecoded(SPUtil.getInstance(context).getStringValue(SPUtil.APP_TOWNNAME))
        );
        reqParams.put("S_MAN_CN",
                UrlUtils.toURLDecoded(SPUtil.getInstance(context).getStringValue(SPUtil.APP_MYNAME))
        );

        SingleRxHttp.getInstance().createSApi(ApiService.class)
                .userXJpaidanstart(urlstr, reqParams)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);


    }

    public void clickTaskPaiState(String S_MANGE_ID, CommonObserver<Object> callback) {
        String urlstr =AppConfig.BeasUrl+ "2081/odata/PSSSYH/default/T_PATROL_MANAGEMENT('" + S_MANGE_ID + "')";
        Map<Object, Object> reqParams = new HashMap<>();
//        reqParams.put("S_MANGE_ID", S_MANGE_ID);
        reqParams.put("S_STATUS", "W1006500003");

        RxHttpUtils
                .createApi(ApiService.class)
                .userXJReportDataPaistate(urlstr, reqParams)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);


    }

    public void clickTaskPaiReason1(String S_MANGE_ID, String S_STATUS, CommonObserver<Object> callback) {
        String urlstr = AppConfig.BeasUrl+"2081/odata/PSSSYH/default/T_PATROL_MANAGEMENT('" + S_MANGE_ID + "')";
//        任务编号 S_ PATROL_TASK 主键
//        任务状态 S_STATUS W1006500006 已退单
//        W1006500005 已拒绝
        Map<Object, Object> reqParams = new HashMap<>();
        reqParams.put("S_STATUS", S_STATUS);
        reqParams.put("S_STATUS", S_MANGE_ID);

        RxHttpUtils
                .createApi(ApiService.class)
                .userXJReportDataPaireason1(urlstr, reqParams)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);


    }

    public void requestFile(Map<String, Object> map, ArrayList<String> arrayList, CommonObserver<Object> callback) {
        RxHttpUtils.uploadImgsWithParams(AppConfig.BeasUrl+"2083/file/upload/SSYH", "file", map, arrayList)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);


    }


    public void clickTaskPaiReason2(Context mContext, String S_REASON, String S_REMARK, String S_MANGE_ID, String S_STATUS, CommonObserver<Object> callback) {
        String urlstr = AppConfig.BeasUrl+"2056/api/PatrolRecode/TaskRefuse ";
        String user = SPUtil.getInstance(mContext).getStringValue(SPUtil.USERNO);

        Map<String, Object> reqParams = new HashMap<>();
        reqParams.put("S_ID", user + System.currentTimeMillis());
        reqParams.put("S_TOWNID", SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_TOWNID));
        reqParams.put("S_COMPANY", SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_COMPANY));
        reqParams.put("S_TOWNNAME", SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_TOWNNAME));
        reqParams.put("T_TM", GetUTCTime.getNow()
        );
        if (!AppTool.isNull(S_REMARK)) {
            reqParams.put("S_REMARK", S_REMARK);
        }
        reqParams.put("S_MAN", user);
        reqParams.put("S_TASK_ID", S_MANGE_ID);

        reqParams.put("S_REASON", S_REASON);
        reqParams.put("S_STATUS", S_STATUS);//W1006500006 已退单 W1006500005 已拒绝
        reqParams.put("S_MANGE_ID", S_MANGE_ID);
        reqParams.put("S_MAN_FULLNAME", SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_MYNAME));


        LogUtils.e("数据", reqParams.toString());

        SingleRxHttp.getInstance()
                .createSApi(ApiService.class)
                .refuse(urlstr, reqParams)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);


    }

    //执行派单的时候退单调用的接口
    public void clickTaskPaiReason3(Context mContext, String S_REASON, String S_REMARK, String S_MANGE_ID, String S_STATUS, CommonObserver<Object> callback) {
        String urlstr =AppConfig.BeasUrl+ "2056/api/PatrolRecode/TaskChargeback";
        String user = SPUtil.getInstance(mContext).getStringValue(SPUtil.USERNO);

        Map<String, Object> reqParams = new HashMap<>();
        reqParams.put("S_ID", user + System.currentTimeMillis());
        reqParams.put("S_TOWNID", SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_TOWNID));
        reqParams.put("S_COMPANY", SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_COMPANY));
        reqParams.put("S_TOWNNAME", SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_TOWNNAME));
        reqParams.put("T_TM", GetUTCTime.getNow()
        );
        reqParams.put("T_END", GetUTCTime.getNow()
        );
        if (!AppTool.isNull(S_REMARK)) {
            reqParams.put("S_REMARK", S_REMARK);
        }
        reqParams.put("S_MAN", user);
        reqParams.put("S_TASK_ID", S_MANGE_ID);
        reqParams.put("N_DEL", 1);
        reqParams.put("S_REASON", S_REASON);
        reqParams.put("S_STATUS", "W1006500006");//W1006500006 已退单 W1006500005 已拒绝
        reqParams.put("S_MANGE_ID", S_MANGE_ID);
        reqParams.put("S_MAN_FULLNAME", SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_MYNAME));


        LogUtils.e("数据", reqParams.toString());

        SingleRxHttp.getInstance()
                .createSApi(ApiService.class)
                .refuse(urlstr, reqParams)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);


    }

    public void getRequestDictionaries(CommonObserver<Object> callback) {

        String urlstr = AppConfig.BeasUrl+"2056/api/T_Dict/GetT_Dict";
//
        RxHttpUtils
                .createApi(ApiService.class)
                .userXJgetDictionaries(urlstr)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);


    }

    public void RequestEndTask(String S_RECODE_ID, String T_END, CommonObserver<Object> callback) {
        String urlstr =AppConfig.BeasUrl+ "2056/api/PatrolRecode/UpdatePatrolRecode";
        Map<Object, Object> reqParams = new HashMap<>();
        reqParams.put("T_END", T_END);
        reqParams.put("S_RECODE_ID", S_RECODE_ID);

        reqParams.put("N_MILEAGE", AppTool.getDoubleAccurate(MainMapXJActivity.Distance));

        RxHttpUtils
                .createApi(ApiService.class)
                .RequestXJEndTask(urlstr, reqParams)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);

    }

    public void RequestEndTaskpai1(Context mContext, String S_RECODE_ID, CommonObserver<Object> callback) {//废弃
        String urlstr =AppConfig.BeasUrl+"2081/odata/PSSSYH/default/T_PATROL_RECODE('" + S_RECODE_ID + "')";//TODO  文档没写主键传什么值  自己认为传记录列表的taskid
        Map<Object, Object> reqParams = new HashMap<>();
        reqParams.put("T_END", GetUTCTime.getNow());
        reqParams.put("N_MILEAGE", AppTool.getDoubleAccurate(MainMapXJActivity.Distance));
        reqParams.put("N_TIME", ControllerMainUIView.timeint);
        reqParams.put("S_SJCZ_ID", AppAttribute.F.getXJID(mContext));
        reqParams.put("S_MAN_ID", SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_MYNAME));
        reqParams.put("S_MAN_CN", SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_MYNAME));
        reqParams.put("S_TOWNNAME", SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_TOWNNAME));
        reqParams.put("S_TOWNID", SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_TOWNID));
        reqParams.put("S_COMPANY", SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_COMPANY));
        reqParams.put("N_DEL", 1);//正常为1，取消为0

        RxHttpUtils
                .createApi(ApiService.class)
                .RequestXJEndTask(urlstr, reqParams)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);


    }

    public void RequestEndTaskpai2(String S_TASKID, CommonObserver<Object> callback) {//废弃
        String urlstr = AppConfig.BeasUrl+"2081/odata/PSSSYH/default/T_PATROL_MANAGEMENT('" + S_TASKID + "')";
        Map<Object, Object> reqParams = new HashMap<>();
        reqParams.put("S_STATUS", "W1006500004");
        RxHttpUtils
                .createApi(ApiService.class)
                .RequestXJEndTask(urlstr, reqParams)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);
    }

    public void RequestEndTaskpai3(Context mContext, String T_MANGE_TIME, String S_MANGE_ID_REL, CommonObserver<Object> callback) {//废弃
        String urlstr = AppConfig.BeasUrl+"2081/odata/PSSSYH/default/T_PATROL_MANAGEMENT('" + S_MANGE_ID_REL + "')";
        Map<Object, Object> reqParams = new HashMap<>();

        reqParams.put("S_TOWNID_IN", SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_TOWNID));
        reqParams.put("S_COMPANY_IN", SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_COMPANY));
        reqParams.put("S_IN_MAN_FULL", SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_MYNAME));


        reqParams.put("S_MANGE_FULL", SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_MYNAME));
        reqParams.put("S_TOWNID_MANGE", SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_TOWNID));
        reqParams.put("S_COMPANY_MANGE", SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_COMPANY));
        reqParams.put("S_MANGE_MAN", SPUtil.getInstance(mContext).getStringValue(SPUtil.USERNO));
        reqParams.put("T_MANGE_TIME", T_MANGE_TIME);

        RxHttpUtils
                .createApi(ApiService.class)
                .RequestXJEndTask(urlstr, reqParams)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);


    }

    public void RequestEndTaskpai4(Context mContext, String T_MANGE_TIME, CommonObserver<Object> callback) {
        String urlstr =AppConfig.BeasUrl+"2081/odata/PSSSYH/default/T_PATROL_MANAGEMENT";
        Map<Object, Object> reqParams = new HashMap<>();
        reqParams.put("T_MANGE_TIME", T_MANGE_TIME);
        reqParams.put("S_MANGE_MAN", SPUtil.getInstance(mContext).getStringValue(SPUtil.USERNO));
        reqParams.put("S_TOWNID_IN", SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_TOWNID));
        reqParams.put("S_COMPANY_IN", SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_COMPANY));
        reqParams.put("S_IN_MAN_FULL", SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_MYNAME));
        reqParams.put("S_MANGE_FULL", SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_MYNAME));
        reqParams.put("S_TOWNID_MANGE", SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_TOWNID));
        reqParams.put("S_COMPANY_MANGE", SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_COMPANY));
        RxHttpUtils
                .createApi(ApiService.class)
                .RequestXJEndTask1(urlstr, reqParams)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);


    }

    public void RequestCancleTask(String S_RECODE_ID, String T_END, String S_TOWNNAME, CommonObserver<Object> callback) {
        String urlstr = AppConfig.BeasUrl+"2056/api/PatrolRecode/UpdatePatrolRecode";
        Map<Object, Object> reqParams = new HashMap<>();
        reqParams.put("T_END", AppTool.getCurrentDate(AppTool.FORMAT_YMDHMS));
        reqParams.put("S_RECODE_ID", S_RECODE_ID);
        reqParams.put("S_TOWNNAME", S_TOWNNAME);
        reqParams.put("N_DEL", 0);//正常为1，取消为0
        RxHttpUtils
                .createApi(ApiService.class)
                .RequestCancleTask(urlstr, reqParams)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);


    }


    //派单取消
    public void RequestCancleTask1(String S_RECODE_ID, String S_MANGE_ID, String T_END, String S_TOWNNAME, CommonObserver<Object> callback) {
        String urlstr =
                AppConfig.BeasUrl+"2056/api/PatrolRecode/TaskCancel";


        Map<String, Object> reqParams = new HashMap<>();
        reqParams.put("T_END", GetUTCTime.getNow());
        reqParams.put("S_RECODE_ID", S_RECODE_ID);
        reqParams.put("S_MANGE_ID", S_MANGE_ID);
        reqParams.put("S_STATUS", "W1006500002");


        reqParams.put("N_DEL", 0);//正常为1，取消为0
        SingleRxHttp.getInstance().createSApi(ApiService.class)
                .cancleTask(urlstr, reqParams)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);


    }


    public void RequestISDeal(String S_MANGE_ID, CommonObserver<Object> callback) {
//        String urlstr = AppConfig.BeasUrl+"2081/odata/PSSSYH/default/T_PATROL_MANAGEMENT?$select=S_IS_MANGE &$filter=S_MANGE_ID eq '" + S_MANGE_ID + "'";
String urlstr = AppConfig.BeasUrl+"2056/api/Management/GetIsMange?S_MANGE_ID=" + S_MANGE_ID ;
        RxHttpUtils
                .createApi(ApiService.class)
                .RequestXJISDeal(urlstr)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);


    }

    public void RequestRelevantTask(String S_RECODE_ID, CommonObserver<Object> callback) {
        String urlstr = "http://222.66.154.70:2081/odata/PSSSYH/default/T_PATROL_MANAGEMENT?$select=S_IS_MANGE,N_X,N_Y,S_CATEGORY,S_TYPE,S_MANGE_ID,S_IN_MAN,T_IN_DATE &$filter=S_RECODE_ID eq '" + S_RECODE_ID + "'";
        RxHttpUtils
                .createApi(ApiService.class)
                .RequestXJRelevantTask(urlstr)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);


    }

    public void RequestReportDetails(String S_MANGE_ID, CommonObserver<Object> callback) {

        String urlstr = AppConfig.BeasUrl+"2056/api/Management/T_PATROL_MANAGEMENT_operation?S_MANGE_ID=" + S_MANGE_ID + "&PAGE=1&PAGESIZE=1";
        RxHttpUtils
                .createApi(ApiService.class)
                .RequestXJDealDetails(urlstr)
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

    public void RequestRefuselist(String S_MANGE_ID, CommonObserver<Object> callback) {
        String urlstr = AppConfig.BeasUrl+"2081/odata/PSSSYH/default/T_REJECT_PATROL?$select=T_TM,S_REMARK,S_MAN,S_REASON,S_STATUS,S_MAN_FULLNAME,S_TASK_ID,S_TOWNID,S_COMPANY,S_TOWNNAME&$filter=S_TASK_ID%20eq%20%27" + S_MANGE_ID + "%27";


        RxHttpUtils
                .createApi(ApiService.class)
                .RequestXJDealDetails(urlstr)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);

    }

    public void clickRequestPushMessage(String name, String local, String category, String xl,
                                        String sbsj, CommonObserver<Object> callback) {

        String url = AppConfig.BeasUrl+"2056/api/JPush/ExecutePushExample?type=2&local=" + local +
                "&category=" + category + "&xl=" + xl + "&sbsj=" + sbsj + "&personid=" + name;

        RxHttpUtils
                .createApi(ApiService.class)
                .pushMessage(url)
                .compose(Transformer.<String>switchSchedulers())
                .subscribe(callback);
    }

    public void clickRequestIsDeal(Context mContext, String S_MANGE_ID, CommonObserver<Object> callback) {
        String urlstr =AppConfig.BeasUrl+"2056/api/Management/EdtManagement";
        Map<String, Object> reqParams = new HashMap<>();
        reqParams.put("S_MANGE_MAN", SPUtil.getInstance(mContext).getStringValue(SPUtil.USERNO));
        reqParams.put("S_MANGE_FULL", SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_MYNAME));
        reqParams.put("S_MANGE_ID", S_MANGE_ID);
        reqParams.put("S_STATUS", "W1006500002");

        RxHttpUtils
                .createApi(ApiService.class)
                .userXJIsDeal(urlstr, reqParams)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);


    }

    public void clickRequestIsDealDetail(String S_MANGE_ID, CommonObserver<Object> callback) {
        String urlStr = AppConfig.BeasUrl+"2056/api/Management/T_PATROL_MANAGEMENT_operation?S_MANGE_ID=" + S_MANGE_ID + "&PAGE=1&PAGESIZE=1";
        Map<String, Object> headerMaps = new HashMap<>();
        headerMaps.put("Accept", "application/json");
        SingleRxHttp.getInstance().addHeaders(headerMaps).createSApi(ApiService.class)

                .userXJIsDealDetail(urlStr)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);


    }


    public void AdminGetObjectIds(String idstr, String filterkey, String filtervalue, String townname, CommonObserver<Object> callback) {
        String filerStr = "";
        if (AppTool.isNull(townname)) {
            filerStr = String.format(AppConfig.Filterurlstrstart2, filterkey, "%", filtervalue, "%");
        } else {
            filerStr = String.format(AppConfig.Filterurlstrstart1, townname, filterkey, "%", filtervalue, "%");
        }
        String urlstr = String.format(AppConfig.Filterurlstr, AppConfig.GuanWangUrl, idstr, UrlUtils.toURLEncoded(filerStr), AppConfig.Filterurlstrend);
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
        LogUtils.e("pppppppp", urlstr);
        SingleRxHttp.getInstance().addHeaders(headerMaps).createSApi(ApiService.class)

                .requestPeople(urlstr)
                .compose(Transformer.switchSchedulers())
                .subscribe(callback);

    }

    public void addSearchHis(String user, String str, CommonObserver<Object> callback) {
        String url = AppConfig.BeasUrl+"2056/api/AppSearchHis/AddAppSearch";
        Map<String, Object> params = new HashMap<>();
        params.put("AppType", "1");
        params.put("UserId", user);
        params.put("SearchValue", str);
        RxHttpUtils.createApi(ApiService.class)
                .addSearchHis(url, params)
                .compose(Transformer.<String>switchSchedulers())
                .subscribe(callback);
    }

    public void requestSearchHis(String user, CommonObserver<Object> callback) {
        String url = AppConfig.BeasUrl+"2056/api/AppSearchHis/GetAppSearch?AppType=1&UserId=" + user;
        RxHttpUtils.createApi(ApiService.class)
                .getSearchList(url)
                .compose(Transformer.<String>switchSchedulers())
                .subscribe(callback);
    }

    public void clearSearchHis(String user, CommonObserver<Object> callback) {
        String url = AppConfig.BeasUrl+"2056/api/AppSearchHis/ClearAppHis";
        Map<String, Object> params = new HashMap<>();
        params.put("AppType", "1");
        params.put("UserId", user);
        RxHttpUtils.createApi(ApiService.class)
                .clearHis(url, params)
                .compose(Transformer.<String>switchSchedulers())
                .subscribe(callback);
    }
}
