package com.wavenet.ding.qpps.mvp.p;

import com.dereck.library.base.BaseMvpPersenter;
import com.dereck.library.observer.CommonObserver;
import com.dereck.library.utils.ToastUtils;
import com.wavenet.ding.qpps.mvp.m.JCMapActivityRequestModel;
import com.wavenet.ding.qpps.mvp.v.JCMapRequestView;

import org.json.JSONObject;

public class JCMapRequestPresenter extends BaseMvpPersenter<JCMapRequestView> {

    private final JCMapActivityRequestModel mJCMapActivityRequestModel;

    public JCMapRequestPresenter() {
        this.mJCMapActivityRequestModel = new JCMapActivityRequestModel();
    }
    public void getJCData_id(final int resultid, String url) {//urlid 1008巡检，1009养护
        //上报人，状态
        requestDataJC(0, resultid, "");
        mJCMapActivityRequestModel.getJCData_id(url, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                requestDataJC(1, resultid, errorMsg);
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestDataJC(2, resultid, (String) s);
            }
        });
    }    public void getJCData_his(final int resultid, String url) {//urlid 1008巡检，1009养护
        //上报人，状态
        requestDataJC(0, resultid, "");
        mJCMapActivityRequestModel.getJCData_his(url, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                requestDataJC(1, resultid, errorMsg);
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestDataJC(2, resultid, (String) s);
            }
        });
    }

    public void requestDataJC(int state, int resultid, String content) {
        if (getmMvpView() != null) {
            if (state == 0) {
                getmMvpView().show();
                return;
            }

            getmMvpView().hide();
            if (state == 1) {
                ToastUtils.showToast(content);
                getmMvpView().requestFailureJC(resultid, content);
            } else if (state == 2) {
                try {
                    JSONObject JB=new JSONObject(content);
                    if (JB.getInt("Code")==200){
                        getmMvpView().requestSuccessJC(resultid,content);
                    }else {
                        ToastUtils.showToast(JB.getString("Msg"));
                        getmMvpView().requestFailureJC(resultid, content);
                    }
                } catch (Exception e) {
                    ToastUtils.showToast(e.getMessage());
                    e.printStackTrace();
                }

            }



        }
    }
}