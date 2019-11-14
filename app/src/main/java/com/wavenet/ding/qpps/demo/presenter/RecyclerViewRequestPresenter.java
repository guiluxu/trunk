package com.wavenet.ding.qpps.demo.presenter;


import android.util.Log;

import com.dereck.library.base.BaseMvpPersenter;
import com.dereck.library.observer.CommonObserver;
import com.wavenet.ding.qpps.demo.model.RecyclerViewActivityRequestModel;
import com.wavenet.ding.qpps.demo.view.RecyclerViewActivityRequestView;

public class RecyclerViewRequestPresenter extends BaseMvpPersenter<RecyclerViewActivityRequestView> {

    private final RecyclerViewActivityRequestModel recyclerViewActivityRequestModel;

    public RecyclerViewRequestPresenter() {
        this.recyclerViewActivityRequestModel = new RecyclerViewActivityRequestModel();
    }

    public void clickRequest(final String pageNumber) {
        Log.e("dddddd", "clickRequest: ");

        recyclerViewActivityRequestModel.request(pageNumber, new CommonObserver<String>() {

            @Override
            protected void onError(String errorMsg) {
                getmMvpView().resultFailure(errorMsg);
            }

            @Override
            protected void onSuccess(String s) {
                //业务处理
                if (getmMvpView() != null) {
                    getmMvpView().resultSuccess(s);
                }
            }
        });
    }


}
