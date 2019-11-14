package com.wavenet.ding.qpps.mvp.p;


import android.content.Context;

import com.dereck.library.base.BaseMvpPersenter;
import com.dereck.library.observer.CommonObserver;
import com.wavenet.ding.qpps.mvp.m.XJDealActivityRequestModel;
import com.wavenet.ding.qpps.mvp.v.XJActivityRequestView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.ResponseBody;

public class XJDealRequestPresenter extends BaseMvpPersenter<XJActivityRequestView> {

    private XJDealActivityRequestModel mXJDealActivityRequestModel;

    public XJDealRequestPresenter() {
        this.mXJDealActivityRequestModel = new XJDealActivityRequestModel();
    }

    public void clickRequestIsDealDetail(String S_MANGE_ID) {//上报成功后提示dialog提示是否处置  点击进行处置去请求事件详情信息
//        事件编号  记录编号  问题类别  问题类型 上报人  上报时间
        requestData(0, 1, "");
        mXJDealActivityRequestModel.clickRequestIsDealDetail(S_MANGE_ID, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                requestData(2, 1, errorMsg);

            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                if (getmMvpView() != null) {
//                    getmMvpView().hide();
                    requestData(2, 1, (String) s);
                }
            }
        });
    }
    public void RequestFileDetailsPhoto(String S_RECODE_ID) {
        //上报人，状态
        requestData(0, 17, "");
        mXJDealActivityRequestModel.RequestReportDetailsPhoto(S_RECODE_ID, new CommonObserver<Object>() {

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
    public void clickTaskDeal(Context mContext,String S_MANGE_ID, String S_MANGE_MAN, String T_MANGE_TIME, String S_MANGE_REMARK) {
//事件编号，用户名 ， 时间
        requestData(0, 2, "");

        mXJDealActivityRequestModel.clickTaskDeal( mContext, S_MANGE_ID, S_MANGE_MAN, T_MANGE_TIME, S_MANGE_REMARK, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                if (getmMvpView() != null) {
                    if ("空指针异常".equals(errorMsg)) {
                        requestData(2, 2, errorMsg);
                    } else {
                        requestData(1, 2, errorMsg);
                    }

                }
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestData(2, 2, (String) s);
            }
        });
    }
    public void FileRequest(final int file, Map<String, Object> map, ArrayList<String> arrayList) {
//        file  6 事件上报，61事件处置，62 补录
        requestData(0, file, "");
        mXJDealActivityRequestModel.FileRequest(file, map, arrayList, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                requestData(1, file, errorMsg);
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
}
