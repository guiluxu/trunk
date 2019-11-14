package com.wavenet.ding.qpps.mvp.p;


import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.bll.greendao.FailXJResultDao;
import com.bll.greendao.dbean.FailXJResult;
import com.bll.greendao.dbean.FilePath;
import com.bll.greendao.manager.DBManager;
import com.dereck.library.base.BaseMvpPersenter;
import com.dereck.library.observer.CommonObserver;
import com.dereck.library.utils.ToastUtils;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.adapter.SearchHisAdapter;
import com.wavenet.ding.qpps.bean.SearchHistory;
import com.wavenet.ding.qpps.mvp.m.MainMapXJActivityRequestModel;
import com.wavenet.ding.qpps.db.WavenetCallBack;
import com.wavenet.ding.qpps.mvp.v.XJActivityRequestView;
import com.wavenet.ding.qpps.utils.AppAttribute;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.LogUtils;

import org.devio.takephoto.model.TImage;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;

public class MainMapXJRequestPresenter extends BaseMvpPersenter<XJActivityRequestView> {

    private MainMapXJActivityRequestModel mMainMapXJActivityRequestModel;

    public MainMapXJRequestPresenter() {
        this.mMainMapXJActivityRequestModel = new MainMapXJActivityRequestModel();
    }

    public void clickRequestUPloca(String S_ID, String S_MAN_ID, String T_UPLOAD, double N_X, double N_Y, double N_SPEED, String S_TASK_TYPE, String S_RECORD_ID, double N_MILEAGE) {
//        流水号   人员编号   上传时间   X坐标  Y坐标   瞬时速度  上次更新时间
        mMainMapXJActivityRequestModel.requestUPloca(S_ID, S_MAN_ID, T_UPLOAD, N_X, N_Y, N_SPEED, S_TASK_TYPE, S_RECORD_ID, N_MILEAGE, new WavenetCallBack() {
            @Override
            public void onError(int id, String errorCode, String errorMsg) {
                AppAttribute.G.TrackUPError++;
                requestData(1, 1, errorMsg);
            }

            @Override
            public void onSuccess(int id, JSONObject result) {
                Log.e("on111Success",result.toString());
                requestData(2, 1, result.toString());
            }
        });
//        {

//            @Override
//            protected void onError(String errorMsg) {
//                AppAttribute.G.TrackUPError++;
//                requestData(1, 1, errorMsg);
//            }
//
//            @Override
//            protected void onSuccess(Object s) {
//                //业务处理
//                requestData(2, 1, (String) s);
//
//            }
//        });
    }
    public void clickRequestUPTask(Context mContext, String S_DESC, String S_MANGE_ID, String S_RECODE_ID, String S_CATEGORY, String S_TYPE, String S_IN_MAN, String T_IN_DATE, double N_X, double N_Y, String S_LOCAL) {
//        事件编号  记录编号  问题类别  问题类型 上报人  上报时间
        requestData(0, 2, "");
        mMainMapXJActivityRequestModel.clickRequestUPTask(mContext, S_DESC, S_MANGE_ID, S_RECODE_ID, S_CATEGORY, S_TYPE, S_IN_MAN, T_IN_DATE, N_X, N_Y, S_LOCAL, new WavenetCallBack() {
            @Override
            public void onError(int id, String errorCode, String errorMsg) {
                requestData(1, 2, errorMsg);
            }

            @Override
            public void onSuccess(int id, JSONObject result) {
                if (getmMvpView() != null) {
                    Log.e("clickRequestUPTask",result.toString());
                    getmMvpView().requestSuccess(2, result.toString());
                }
            }

//            @Override
//            protected void onError(String errorMsg) {
//                requestData(1, 2, errorMsg);
//            }
//
//            @Override
//            protected void onSuccess(Object s) {
//                //业务处理
//                if (getmMvpView() != null) {
////                    getmMvpView().hide();
//                    getmMvpView().requestSuccess(2, (String) s);
//                }
//            }
        });
    }

    public void clickRequestClasbig() {
        requestData(0, 3, "");
        mMainMapXJActivityRequestModel.clickRequestClasbig(new WavenetCallBack() {
            @Override
            public void onError(int id, String errorCode, String errorMsg) {
                requestData(1, 3, errorMsg);
            }

            @Override
            public void onSuccess(int id, JSONObject result) {
                Log.e("clickRequestClasbig",result.toString());
                requestData(2, 3, result.toString());
            }

        });/*(new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg)


            {

                requestData(1, 3, errorMsg);

            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestData(2, 3, (String) s);
            }
        });*/
    }

    public void clickRequestClassmall(String clasbig) {
        //大类编号
        requestData(0, 4, "");
        mMainMapXJActivityRequestModel.clickRequestClassmall(clasbig, new WavenetCallBack() {
                    @Override
                    public void onError(int id, String errorCode, String error) {
                        requestData(1, 4, error);
                    }

                    @Override
                    public void onSuccess(int id, JSONObject result) {
                        requestData(2, 4, result.toString());
                    }
                });
                /*new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                requestData(1, 4, errorMsg);
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestData(2, 4, (String) s);
            }
        });*/
    }

    public void clickRequestTasklist(String S_MAN, String S_STATUS) {
        //上报人，状态
        requestData(0, 5, "");
        mMainMapXJActivityRequestModel.clickRequestTasklist(S_MAN, S_STATUS, new WavenetCallBack() {
                    @Override
                    public void onError(int id, String errorCode, String error) {
                        ToastUtils.showToast(error);
                        requestData(1, 5, error);
                    }

                    @Override
                    public void onSuccess(int id, JSONObject result) {
                        requestData(2, 5, result.toString());
                    }
                });
                /*new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                ToastUtils.showToast(errorMsg);
                requestData(1, 5, errorMsg);
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestData(2, 5, (String) s);
            }
        });*/
    }

    public void clickRequestTasklistsum(String S_MAN, String S_STATUS) {
        //上报人，状态
        requestData(0, 51, "");
        mMainMapXJActivityRequestModel.clickRequestTasklist(S_MAN, S_STATUS, new WavenetCallBack() {
                    @Override
                    public void onError(int id, String errorCode, String errorMsg) {
                        ToastUtils.showToast(errorMsg);
                        requestData(1, 51, errorMsg);
                    }

                    @Override
                    public void onSuccess(int id, JSONObject result) {
                        requestData(2, 51,result.toString());
                    }
                });
                /*new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                ToastUtils.showToast(errorMsg);
                requestData(1, 51, errorMsg);
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestData(2, 51, (String) s);
            }
        });*/
    }

    //正常的附件上传
    public void FileRequest(final int file, final Map<String, Object> map, final ArrayList<String> arrayList,
                            final ArrayList<TImage> images, final String mfilevideo, final String audioPath) {

//        file  6 事件上报，61事件处置，62 暂时没有用  63 派单结束上报
        requestData(0, file, "");
        mMainMapXJActivityRequestModel.FileRequest(file, map, arrayList, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                requestData(file, errorMsg, map, images, mfilevideo, audioPath);
            }

            @Override
            protected void onSuccess(Object responseBody) {
                if (getmMvpView() != null) {
                    try {
                        ResponseBody b = (ResponseBody) responseBody;
                        requestData(2, file, b.string());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**************************上传附件失败重新上传  开始**********************/

    //上传未成功的附件,如果上传成功，需要删除掉本地数据，如果失败，不删除
    public void uploadFile(Context context) {
        //查询上传未成功的数据
        List<FailXJResult> xjResults = DBManager.getFailXJDao(context).queryBuilder().where(FailXJResultDao.Properties.IsSave.eq(false)).list();

        if (xjResults == null || xjResults.isEmpty()) {
            ToastUtils.showToast("没有数据可上传！");
            return;
        }
        int index = 0;
        fileRequest(context, xjResults, index);
    }

    private void fileRequest(Context context, List<FailXJResult> xjResults, int index) {

        if (xjResults == null || xjResults.size() < 1 || index >= xjResults.size()) {
            if (getmMvpView() != null) {
                getmMvpView().hide();
            }
            //根据上传状态查询最新的数据列表，上传成功的数据
            List<FailXJResult> successResults = DBManager.getFailXJDao(context).queryBuilder().where(FailXJResultDao.Properties.IsSave.eq(true)).list();
            int successSize = successResults.size();
            int failSize = xjResults.size() - successSize;
            if (failSize < 0) {
                failSize = 0;
            }
            String tip = "数据上传完成，成功:" + successSize + "条，失败:" + failSize + "条";
            if (failSize == 0) {
                requestData(2, 10000, "success");

            }
            showTipDialog(context, tip, successResults);
            return;
        }

        FailXJResult xjResult = xjResults.get(index);
        String x = xjResult.getX();
        String y = xjResult.getY();
        String relyid = xjResult.getRelyid();
        String mfilevideo = xjResult.getVideoUrl();
        String audioPath = xjResult.getAudioUrl();
        int type = xjResult.getXjType();
        List<FilePath> filePaths = xjResult.getImagePaths();

        Map<String, Object> map = new HashMap<>();

        map.put("x", x);
        map.put("y", y);
        map.put("relyid", relyid);//S_SJSB_ID

        ArrayList<String> arrayList = new ArrayList<>();

        for (int i = 0; i < filePaths.size(); i++) {
            arrayList.add(filePaths.get(i).getImagePath());

        }
        if (!AppTool.isNull(mfilevideo)) {
            arrayList.add(mfilevideo);
        }
        if (!AppTool.isNull(audioPath)) {
            arrayList.add(audioPath);
        }

        FileRequest(context, xjResults, map, arrayList, index, type);
    }

    //补传的附件上传
    public void FileRequest(final Context context, final List<FailXJResult> xjResults, final Map<String, Object> map,
                            final ArrayList<String> arrayList, final int index, final int type) {

        requestData(0, 0, "");
        mMainMapXJActivityRequestModel.FileRequest(type, map, arrayList, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                fileRequest(context, xjResults, index + 1);
            }

            @Override
            protected void onSuccess(Object responseBody) {
                //如果成功，将isSave置为true
                xjResults.get(index).setIsSave(true);
                DBManager.getFailXJDao(context).update(xjResults.get(index));

                fileRequest(context, xjResults, index + 1);
            }
        });
    }

    private void showTipDialog(final Context context, String tip, final List<FailXJResult> successResults) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 有白色背
        window.setContentView(R.layout.dialog_net_tips);
        window.findViewById(R.id.version_describe).setVisibility(View.VISIBLE);
        TextView tv_content = window.findViewById(R.id.tv_content);
        tv_content.setText(tip);

        window.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                //删除上传成功的数据
                DBManager.getFailXJDao(context).deleteInTx(successResults);
            }
        });
    }

    /**************************上传附件失败重新上传   结束**********************/

    public void clickTaskStart(Context context, String S_MAN_ID, String S_RECODE_ID, String T_START, String N_CYCLE) {
        // 记录号，时间，巡检人
        if (getmMvpView() != null) {
            requestData(0, 7, "");

            mMainMapXJActivityRequestModel.clickTaskStart(context, S_MAN_ID, S_RECODE_ID, T_START, N_CYCLE, new CommonObserver<Object>() {

                @Override
                protected void onError(String errorMsg) {
                    requestData(1, 7, errorMsg);

                }

                @Override
                protected void onSuccess(Object s) {
                    //业务处理
                    requestData(2, 7, (String) s);
                }
            });
        }
    }

    public void clickTaskDeal(Context mContext, String S_MANGE_ID, String S_MANGE_MAN, String T_MANGE_TIME, String S_MANGE_REMARK) {
//事件编号，用户名 ， 时间
        requestData(0, 8, "");

        mMainMapXJActivityRequestModel.clickTaskDeal(mContext, S_MANGE_ID, S_MANGE_MAN, T_MANGE_TIME, S_MANGE_REMARK, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                if (getmMvpView() != null) {
                    if ("空指针异常".equals(errorMsg)) {
                        requestData(2, 8, errorMsg);
                    } else {
                        requestData(1, 8, errorMsg);
                    }

                }
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestData(2, 8, (String) s);
            }
        });
    }
//派单处置
    public void clickTaskDeal2(double x, Context mContext, String S_MANGE_ID, String S_MANGE_MAN, String T_MANGE_TIME, String S_SJCZ_ID, String S_MANGE_REMARK) {
//事件编号，用户名 ， 时间
        requestData(0, 82, "");

        mMainMapXJActivityRequestModel.clickTaskDeal2(x, mContext, S_MANGE_ID, S_MANGE_MAN, T_MANGE_TIME, S_SJCZ_ID, S_MANGE_REMARK, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                if (getmMvpView() != null) {
                    if ("空指针异常".equals(errorMsg)) {
                        requestData(2, 82, errorMsg);
                    } else {
                        requestData(1, 82, errorMsg);
                    }

                }
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestData(2, 82, (String) s);
            }
        });
    }

    public void clickRequestTaskAdd(String S_MANGE_ID, String S_RECODE_ID, String S_CATEGORY, String S_TYPE, String S_IN_MAN, String T_IN_DATE) {
//        事件编号  记录编号  问题类别  问题类型 上报人  上报时间
        requestData(0, 9, "");

        mMainMapXJActivityRequestModel.clickRequestTaskAdd(S_MANGE_ID, S_RECODE_ID, S_CATEGORY, S_TYPE, S_IN_MAN, T_IN_DATE, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                requestData(1, 9, errorMsg);
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                if (getmMvpView() != null) {
                    getmMvpView().requestSuccess(9, (String) s);
                }
            }
        });
    }

    //派单开始
    public void clickTaskPaiStart(String S_MAN_ID, String S_RECODE_ID, String S_MANGE_ID, String T_START, String N_CYCLE) {
//      记录编号，   任务编号  ， 时间时间 ， 周期（暂时默认1）
        requestData(0, 10, "");

        mMainMapXJActivityRequestModel.clickTaskPaiStart(S_MAN_ID, S_RECODE_ID, S_MANGE_ID, T_START, N_CYCLE, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                requestData(1, 10, errorMsg);
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                if (getmMvpView() != null) {
                    getmMvpView().requestSuccess(10, (String) s);
                }
            }
        });
    }

    //紧急任务开始执行
    public void clickTaskPaiStart1(Context context, String S_MAN_ID, String S_RECODE_ID, String S_MANGE_ID, String T_START, String N_CYCLE) {
//      记录编号，   任务编号  ， 时间时间 ， 周期（暂时默认1）
        requestData(0, 10, "");

        mMainMapXJActivityRequestModel.clickTaskPaiStart1(context, S_MAN_ID, S_RECODE_ID, S_MANGE_ID, T_START, N_CYCLE, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                requestData(1, 10, errorMsg);
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                if (getmMvpView() != null) {
                    getmMvpView().requestSuccess(10, (String) s);
                }
            }
        });
    }


    //派单开始状态值改变
    public void clickTaskPaiState(String S_MANGE_ID) {
//      记录编号，   任务编号  ， 时间时间 ， 周期（暂时默认1）
        requestData(0, 1111, "");

        mMainMapXJActivityRequestModel.clickTaskPaiState(S_MANGE_ID, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                if (getmMvpView() != null) {
                    if ("空指针异常".equals(errorMsg)) {
                        requestData(2, 1111, errorMsg);
                    } else {
                        requestData(1, 1111, errorMsg);
                    }

                }

            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                if (getmMvpView() != null) {
                    getmMvpView().requestSuccess(1111, (String) s);
                }
            }
        });
    }

    //派单拒绝1
    public void clickTaskPaiReason1(String S_MANGE_ID, String S_STATUS) {
//      记录编号，   任务编号  ， 时间时间 ， 周期（暂时默认1）
        requestData(0, 102, "");

        mMainMapXJActivityRequestModel.clickTaskPaiReason1(S_MANGE_ID, S_STATUS, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                if (getmMvpView() != null) {
                    if ("空指针异常".equals(errorMsg)) {
                        requestData(2, 102, errorMsg);
                    } else {
                        requestData(1, 102, errorMsg);
                    }

                }

            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                if (getmMvpView() != null) {
                    getmMvpView().requestSuccess(102, (String) s);
                }
            }
        });
    }

    //派单列表中拒绝
    public void clickTaskPaiReason2(Context mContext, String S_REASON, String S_REMARK, String S_MANGE_ID, String S_STATUS) {
//      记录编号，   任务编号  ， 时间时间 ， 周期（暂时默认1）
        requestData(0, 1021, "");

        mMainMapXJActivityRequestModel.clickTaskPaiReason2(mContext, S_REASON, S_REMARK, S_MANGE_ID, S_STATUS, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                if (getmMvpView() != null) {
                    if ("空指针异常".equals(errorMsg)) {
                        requestData(2, 1021, errorMsg);
                    } else {
                        requestData(1, 1021, errorMsg);
                    }

                }

            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                if (getmMvpView() != null) {
                    getmMvpView().requestSuccess(1021, (String) s);
                }
            }
        });
    }

    //执行中的任务退单
    public void clickTaskPaiReason3(Context mContext, String S_REASON, String S_REMARK, String S_MANGE_ID, String S_STATUS) {
//      记录编号，   任务编号  ， 时间时间 ， 周期（暂时默认1）
        requestData(0, 1022, "");

        mMainMapXJActivityRequestModel.clickTaskPaiReason3(mContext, S_REASON, S_REMARK, S_MANGE_ID, S_STATUS, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                if (getmMvpView() != null) {
                    if ("空指针异常".equals(errorMsg)) {
                        requestData(2, 1022, errorMsg);
                    } else {
                        requestData(1, 1022, errorMsg);
                    }

                }

            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                if (getmMvpView() != null) {
                    getmMvpView().requestSuccess(1022, (String) s);
                }
            }
        });
    }

    //字典
    public void getRequestDictionaries() {
        requestData(0, 11, "");

        mMainMapXJActivityRequestModel.getRequestDictionaries(new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                requestData(1, 11, errorMsg);
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestData(2, 11, (String) s);

            }
        });

    }

    //日常巡检取消
    public void RequestCancleTask(String S_RECODE_ID, String T_END, String S_TOWNNAME) {
        requestData(0, 121, "");

        mMainMapXJActivityRequestModel.RequestCancleTask(S_RECODE_ID, T_END, S_TOWNNAME, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                if (getmMvpView() != null) {
                    if ("空指针异常".equals(errorMsg)) {
                        requestData(2, 121, errorMsg);
                    } else {
                        requestData(1, 121, errorMsg);
                    }

                }
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestData(2, 121, (String) s);

            }
        });
    }

    //派单巡检中的任务取消
    public void RequestCancleTask1(String S_RECODE_ID, String S_MANGE_ID, String T_END, String S_TOWNNAME) {
        requestData(0, 177, "");

        mMainMapXJActivityRequestModel.RequestCancleTask1(S_RECODE_ID, S_MANGE_ID, T_END, S_TOWNNAME, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                if (getmMvpView() != null) {
                    if ("空指针异常".equals(errorMsg)) {
                        //   requestData(2, 7, errorMsg);
                    } else {
                        //  requestData(1, 7, errorMsg);
                    }

                }
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestData(2, 177, (String) s);

            }
        });
    }


    public void RequestEndTask(String S_RECODE_ID, String T_END) {
        requestData(0, 12, "");

        mMainMapXJActivityRequestModel.RequestEndTask(S_RECODE_ID, T_END, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                if (getmMvpView() != null) {
                    if ("空指针异常".equals(errorMsg)) {
                        requestData(2, 12, errorMsg);
                    } else {
                        requestData(1, 12, errorMsg);
                    }

                }
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestData(2, 12, (String) s);

            }
        });
    }

    //    派单结束第一步
    public void RequestEndTaskpai1(Context mContext, String S_RECODE_ID) {
        requestData(0, 122, "");

        mMainMapXJActivityRequestModel.RequestEndTaskpai1(mContext, S_RECODE_ID, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                if (getmMvpView() != null) {
                    if ("空指针异常".equals(errorMsg)) {
                        requestData(2, 122, errorMsg);
                    } else {
                        requestData(1, 122, errorMsg);
                    }

                }
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestData(2, 122, (String) s);

            }
        });
    }

    //    派单结束第二步
    public void RequestEndTaskpai2(String S_RECODE_ID) {
        requestData(0, 123, "");

        mMainMapXJActivityRequestModel.RequestEndTaskpai2(S_RECODE_ID, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                if (getmMvpView() != null) {
                    if ("空指针异常".equals(errorMsg)) {
                        requestData(2, 123, errorMsg);
                    } else {
                        requestData(1, 123, errorMsg);
                    }

                }
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestData(2, 123, (String) s);

            }
        });
    }

    //    派单结束第三步
    public void RequestEndTaskpai3(Context mContext, String T_MANGE_TIME, String S_MANGE_ID_REL) {
        requestData(0, 124, "");

        mMainMapXJActivityRequestModel.RequestEndTaskpai3(mContext, T_MANGE_TIME, S_MANGE_ID_REL, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                if (getmMvpView() != null) {
                    if ("空指针异常".equals(errorMsg)) {
                        requestData(2, 124, errorMsg);
                    } else {
                        requestData(1, 124, errorMsg);
                    }

                }
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestData(2, 124, (String) s);

            }
        });
    }

    //    派单结束第四步
    public void RequestEndTaskpai4(Context mContext, String T_MANGE_TIME) {
        requestData(0, 125, "");

        mMainMapXJActivityRequestModel.RequestEndTaskpai4(mContext, T_MANGE_TIME, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                if (getmMvpView() != null) {
                    if ("空指针异常".equals(errorMsg)) {
                        requestData(2, 125, errorMsg);
                    } else {
                        requestData(1, 125, errorMsg);
                    }

                }
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestData(2, 125, (String) s);

            }
        });
    }

    public void RequestISDeal(String S_MANGE_ID) {//点击地图maker进入页面判断是否处置来设置按钮tv. settext();
        requestData(0, 13, "");

        mMainMapXJActivityRequestModel.RequestISDeal(S_MANGE_ID, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {

                requestData(1, 13, errorMsg);
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestData(2, 13, (String) s);

            }
        });
    }

    public void RequestRelevantTask(String S_RECODE_ID) {
        requestData(0, 14, "");

        mMainMapXJActivityRequestModel.RequestRelevantTask(S_RECODE_ID, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {

                requestData(1, 14, errorMsg);
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestData(2, 14, (String) s);

            }
        });
    }

    public void RequestReportDetails(String S_MANGE_ID) {
        //上报人，状态
        requestData(0, 15, "");
        mMainMapXJActivityRequestModel.RequestReportDetails(S_MANGE_ID, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                requestData(1, 15, errorMsg);
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestData(2, 15, (String) s);
            }
        });
    }

    public void RequestReportDetailsPhoto(String S_RECODE_ID) {
        //上报人，状态
        requestData(0, 16, "");
        mMainMapXJActivityRequestModel.RequestReportDetailsPhoto(S_RECODE_ID, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                requestData(1, 16, errorMsg);
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestData(2, 16, (String) s);
            }
        });
    }

    public void RequestFileDetailsPhoto(String S_RECODE_ID) {
        //上报人，状态
        requestData(0, 17, "");
        mMainMapXJActivityRequestModel.RequestReportDetailsPhoto(S_RECODE_ID, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                requestData(1, 17, errorMsg);
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestData(2, 17, (String) s);
            }
        });
    }

    public void RequestFileDetailsPhoto1(String S_RECODE_ID) {
        //上报人，状态
        requestData(0, 171, "");
        mMainMapXJActivityRequestModel.RequestReportDetailsPhoto(S_RECODE_ID, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                requestData(1, 171, errorMsg);
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestData(2, 171, (String) s);
            }
        });
    }

    public void RequestRefuselist(String S_TASK_ID) {
        //上报人，状态
        requestData(0, 18, "");
        mMainMapXJActivityRequestModel.RequestRefuselist(S_TASK_ID, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                requestData(1, 18, errorMsg);
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestData(2, 18, (String) s);
            }
        });
    }

    public void clickRequestPushMessage(String name, String local, String category, String xl, String sbsj) {
        mMainMapXJActivityRequestModel.clickRequestPushMessage(name, local, category, xl, sbsj, new CommonObserver<Object>() {
            @Override
            protected void onError(String errorMsg) {
                ToastUtils.showToast("暂无管理员登录账号，收不到消息！");
            }

            @Override
            protected void onSuccess(Object o) {
                LogUtils.d("clickRequestPushMessage", o.toString());
            }
        });
    }

    public void clickRequestIsDeal(Context mContext, String S_MANGE_ID) {//上报成功后点击处置派单任务列表更新（自己接手当前事件，自己派单给自己）
//        事件编号  记录编号  问题类别  问题类型 上报人  上报时间
        requestData(0, 19, "");
        mMainMapXJActivityRequestModel.clickRequestIsDeal(mContext, S_MANGE_ID, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                if (getmMvpView() != null) {
                    if ("空指针异常".equals(errorMsg)) {
                        requestData(2, 19, errorMsg);
                    } else {
                        requestData(1, 19, errorMsg);
                    }

                }

            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                if (getmMvpView() != null) {
//                    getmMvpView().hide();
                    requestData(2, 19, (String) s);
                }
            }
        });
    }

    public void clickRequestIsDealDetail(String S_MANGE_ID) {//上报成功后提示dialog提示是否处置  点击进行处置去请求事件详情信息
//        事件编号  记录编号  问题类别  问题类型 上报人  上报时间
        requestData(0, 20, "");
        mMainMapXJActivityRequestModel.clickRequestIsDealDetail(S_MANGE_ID, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                requestData(1, 20, errorMsg);

            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                if (getmMvpView() != null) {
//                    getmMvpView().hide();
                    requestData(2, 20, (String) s);
                }
            }
        });
    }

    public void clickRequestIsDealDetail1(String S_MANGE_ID) {//点击地图Marker时请求的基本信息；
//        事件编号  记录编号  问题类别  问题类型 上报人  上报时间
        requestData(0, 21, "");
        mMainMapXJActivityRequestModel.clickRequestIsDealDetail(S_MANGE_ID, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                requestData(1, 21, errorMsg);

            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                if (getmMvpView() != null) {
//                    getmMvpView().hide();
                    requestData(2, 21, (String) s);
                }
            }
        });
    }

    public void clickRequestIsDealDetail2(String S_MANGE_ID) {//点击地图Marker时当该事件未处置时，点击处置按钮请求处置信息
//        事件编号  记录编号  问题类别  问题类型 上报人  上报时间
        requestData(0, 22, "");
        mMainMapXJActivityRequestModel.clickRequestIsDealDetail(S_MANGE_ID, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                requestData(2, 22, errorMsg);

            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                if (getmMvpView() != null) {
//                    getmMvpView().hide();
                    requestData(2, 22, (String) s);
                }
            }
        });
    }

    public void requestData(int state, int resultid, String content) {
        if (getmMvpView() != null) {
            if (state == 0) {
                getmMvpView().show();
            } else if (state == 1) {
                getmMvpView().hide();
                getmMvpView().requestFailure(resultid, content);
            } else if (state == 2) {
                getmMvpView().hide();
                getmMvpView().requestSuccess(resultid, content);
            }
        }
    }

    public void requestData(int resultid, String content, Map<String, Object> map, ArrayList<TImage> images,
                            String videoPath, String audioPath) {
        if (getmMvpView() != null) {
            getmMvpView().hide();
            getmMvpView().requestFailure(resultid, content, map, images, videoPath, audioPath);
        }
    }

    public void AdminGetObjectIds(String idstr, String filterkey, String filtervalue, String townname) {
        //上报人，状态
        requestData1(0, 1, "");
        mMainMapXJActivityRequestModel.AdminGetObjectIds(idstr, filterkey, filtervalue, townname, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                requestData1(1, 1, errorMsg);
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                Log.e("AdminGetObjectIds", (String) s);
                requestData1(2, 1, (String) s);
            }
        });
    }

    public void AdminGetObjectDetails(final int url, String ids, String filterkey) {
        //上报人，状态
        requestData1(0, url, "");
        mMainMapXJActivityRequestModel.AdminGetObjectDetails(url, ids, filterkey, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                requestData1(1, url, errorMsg);
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                Log.e("AdminGetObjectDetails", s.toString()+"..."+url);
                requestData1(2, url, (String) s);
            }
        });
    }

    public void requestPeople(final int urlid, String filterStr) {//urlid 301巡检，302养护
        //上报人，状态
        requestData1(0, urlid, "");
        mMainMapXJActivityRequestModel.requestPeople(filterStr, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                requestData1(1, urlid, errorMsg);
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestData1(2, urlid, (String) s);
            }
        });
    }


    public void requestData1(int state, int resultid, String content) {
        if (getmMvpView() != null) {
            if (state == 0) {
                getmMvpView().show();
            } else if (state == 1) {
                getmMvpView().hide();
                getmMvpView().requestFailure(resultid, content, true);
            } else if (state == 2) {
                getmMvpView().hide();
                getmMvpView().requestSuccess(resultid, content, true);
            }
        }

    }

    /**
     * 历史搜索记录
     */
    public void requestSearchHis(String user) {
        requestData1(0, 3, "");
        mMainMapXJActivityRequestModel.requestSearchHis(user, new CommonObserver<Object>() {
            @Override
            protected void onError(String errorMsg) {
                requestData1(1, 3, errorMsg);
            }

            @Override
            protected void onSuccess(Object o) {
                requestData1(2, 3, (String) o);
            }
        });
    }

    /**
     * 增加搜索记录
     */
    public void addSearchHis(String user, String str) {
        mMainMapXJActivityRequestModel.addSearchHis(user, str, new CommonObserver<Object>() {
            @Override
            protected void onError(String errorMsg) {
                ToastUtils.showToast(errorMsg);
            }

            @Override
            protected void onSuccess(Object o) {
                LogUtils.d("addSearchHis", (String) o);
            }
        });
    }

    /**
     * 清空搜索记录
     */
    public void clearHisList(final List<SearchHistory.DataBean> hisList, String user, final SearchHisAdapter mAdapter) {
        mMainMapXJActivityRequestModel.clearSearchHis(user, new CommonObserver<Object>() {
            @Override
            protected void onError(String errorMsg) {
                ToastUtils.showToast(errorMsg);
            }

            @Override
            protected void onSuccess(Object o) {
                hisList.clear();
                mAdapter.notifyDataSetChanged();
                requestData1(2, 4, (String) o);
                ToastUtils.showToast("清空成功！");
            }
        });
    }
}
