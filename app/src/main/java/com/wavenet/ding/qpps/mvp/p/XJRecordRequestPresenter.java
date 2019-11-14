package com.wavenet.ding.qpps.mvp.p;


import com.dereck.library.base.BaseMvpPersenter;
import com.dereck.library.observer.CommonObserver;
import com.wavenet.ding.qpps.mvp.m.XJRecordActivityRequestModel;
import com.wavenet.ding.qpps.mvp.v.XJActivityRequestView;

import java.util.Map;

public class XJRecordRequestPresenter extends BaseMvpPersenter<XJActivityRequestView> {

    private XJRecordActivityRequestModel mXJRecordActivityRequestModel;

    public XJRecordRequestPresenter() {
        this.mXJRecordActivityRequestModel = new XJRecordActivityRequestModel();
    }

    public void clickRequestTasklist(Map<String, Object> filterMap) {
        //上报人，状态
        requestData(0, 1, "");
        mXJRecordActivityRequestModel.clickRequestTasklist(filterMap, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                requestData(1, 1, errorMsg);
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestData(2, 1, (String) s);
            }
        });
    }

    public void RequestEndTask(String S_RECODE_ID, String T_END) {
        requestData(0, 2, "");

        mXJRecordActivityRequestModel.RequestEndTask(S_RECODE_ID, T_END, new CommonObserver<Object>() {

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
