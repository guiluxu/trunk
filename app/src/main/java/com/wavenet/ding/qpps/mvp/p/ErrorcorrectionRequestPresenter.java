package com.wavenet.ding.qpps.mvp.p;

import com.dereck.library.base.BaseMvpPersenter;
import com.dereck.library.observer.CommonObserver;
import com.wavenet.ding.qpps.mvp.m.ErrorcorrectionActivityRequestModel;
import com.wavenet.ding.qpps.mvp.v.RequestView;
import com.wavenet.ding.qpps.utils.LogUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.ResponseBody;

public class ErrorcorrectionRequestPresenter extends BaseMvpPersenter<RequestView> {

    private final ErrorcorrectionActivityRequestModel mErrorcorrectionActivityRequestModel;

    public ErrorcorrectionRequestPresenter() {
        this.mErrorcorrectionActivityRequestModel = new ErrorcorrectionActivityRequestModel();
    }
    public void requestUPJiucuo(String FACSTATE, String FACNAME, String N_X, String N_Y, String ADDRESS, String REMARK, String PHONE, String RELYID, String DEVICEID,String CREATEMAN) {
//
        requestData(0, 1, "");
        mErrorcorrectionActivityRequestModel.requestUPJiucuo( FACSTATE,  FACNAME,  N_X,  N_Y,  ADDRESS,  REMARK,  PHONE,  RELYID,  DEVICEID,CREATEMAN, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                LogUtils.e("zzzzzzzou----12errorMsg",errorMsg);
                requestData(1, 1, errorMsg);
            }

            @Override
            protected void onSuccess(Object s) {
                LogUtils.e("zzzzzzzou----12",(String) s);
                //业务处理
                requestData(2, 1, (String) s);
            }
        });
    }
    public void FileRequest(final int file, Map<String, Object> map, ArrayList<String> arrayList) {
//        file  6 事件上报，61事件处置，62 补录
        requestData(0, file, "");
        mErrorcorrectionActivityRequestModel.FileRequest(file, map, arrayList, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {

                LogUtils.e("zzzzzzzou----121errorMsg",errorMsg);
                requestData(1, file, errorMsg);
            }

            @Override
            protected void onSuccess(Object responseBody) {
                try {
                    ResponseBody b = (ResponseBody) responseBody;
                    requestData(2, file, b.string());


                } catch (IOException e) {
                    e.printStackTrace();
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