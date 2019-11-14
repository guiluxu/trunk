package com.wavenet.ding.qpps.mvp.p;

import android.content.Context;
import android.util.Log;

import com.dereck.library.base.BaseMvpPersenter;
import com.dereck.library.observer.CommonObserver;
import com.google.gson.Gson;
import com.wavenet.ding.qpps.mvp.m.AdminSubmitActivityRequestModel;
import com.wavenet.ding.qpps.mvp.v.AdminSubmitActivityRequestView;

import org.devio.takephoto.model.TImage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.ResponseBody;

public class AdminSubmitActivityRequestPresenter extends BaseMvpPersenter<AdminSubmitActivityRequestView> {
    private AdminSubmitActivityRequestModel adminSubmitActivityRequestModel;
    public AdminSubmitActivityRequestPresenter(){
        this.adminSubmitActivityRequestModel = new AdminSubmitActivityRequestModel();
    }
    public void clickRequestClasbig() {
        adminSubmitActivityRequestModel.clickRequestClasbig(new CommonObserver<Object>() {
            @Override
            protected void onError(String errorMsg) {
                requestData(1, 3, errorMsg);
            }
            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestData(2, 3, (String) s);
            }
        });
    }
    public void clickRequestClassmall(String clasbig) {
        //大类编号
        adminSubmitActivityRequestModel.clickRequestClassmall(clasbig, new CommonObserver<Object>() {
            @Override
            protected void onError(String errorMsg) {
                requestData(1, 4, errorMsg);
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestData(2, 4, (String) s);
            }
        });
    }

    public void clickRequestSource() {
        //大类编号
        adminSubmitActivityRequestModel.clickRequestSource(new CommonObserver<Object>() {
            @Override
            protected void onError(String errorMsg) {
                requestData(1, 4, errorMsg);
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestData(2, 5, (String) s);
            }
        });
    }


    public void clickRequestUPTask(Context mContext, String S_DESC, String S_MANGE_ID, String S_RECODE_ID, String S_CATEGORY, String S_TYPE, String S_IN_MAN, String T_IN_DATE, double N_X, double N_Y, String S_LOCAL, String S_SOURCE, String S_TOWNID_IN) {
        requestData(0,0,"");
        //        事件编号  记录编号  问题类别  问题类型 上报人  上报时间
        adminSubmitActivityRequestModel.clickRequestUPTask(mContext, S_DESC, S_MANGE_ID, S_RECODE_ID, S_CATEGORY, S_TYPE, S_IN_MAN, T_IN_DATE, N_X, N_Y, S_LOCAL, S_SOURCE, S_TOWNID_IN, new CommonObserver<Object>() {
            @Override
            protected void onError(String errorMsg) {
                requestData(1, 2, errorMsg);
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                if (getmMvpView() != null) {
//                    getmMvpView().hide();
                    getmMvpView().requestSuccess(12, (String) s);
                }
            }
        });
    }
    //正常的附件上传
    public void FileRequest(final int file, final Map<String, Object> map, final ArrayList<String> arrayList,
                            final ArrayList<TImage> images, final String mfilevideo, final String audioPath) {

//        file  6 事件上报，61事件处置，62 暂时没有用  63 派单结束上报
        adminSubmitActivityRequestModel.FileRequest(file, map, arrayList, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
//                requestData(file, errorMsg, map, images, mfilevideo, audioPath);
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
