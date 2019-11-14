package com.wavenet.ding.qpps.activity;

import android.content.Intent;
import android.view.View;
import android.webkit.JavascriptInterface;

import com.dereck.library.interceptor.Transformer;
import com.dereck.library.observer.CommonObserver;
import com.dereck.library.utils.RxHttpUtils;
import com.dereck.library.utils.ToastUtils;
import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.google.gson.Gson;
import com.wavenet.ding.qpps.api.ApiService;
import com.wavenet.ding.qpps.bean.AdapterBean;
import com.wavenet.ding.qpps.bean.Response;
import com.wavenet.ding.qpps.interf.AddFeatureQueryResultListen;
import com.wavenet.ding.qpps.utils.AppAttribute;
import com.wavenet.ding.qpps.utils.AppConfig;
import com.wavenet.ding.qpps.utils.AppTool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class WebViewHistoryYHActivity extends WebViewActivity implements View.OnClickListener,AddFeatureQueryResultListen {
    String S_ROAD_ID = "";
    String S_RECORD_ID = "";
    boolean isTasking=false;
    Point pLoca;
    AppAttribute mAAtt;
    @Override
    public void dealData() {
        isTasking=getIntent().getBooleanExtra("isTasking",false);
        mAAtt = new AppAttribute(this);
        isShowTop(false);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {

        }
    }
    //     返回：maintainObject.backPreviousView
//    定位：maintainObject.gotoLocation(S_Road_ID);//调用原生方法
//    执行：maintainObject.continueMaintain(S_RECODE_ID);//调用原生方法
    @JavascriptInterface
    public void backPreviousView() {
        super.backPreviousView();
    }
    @JavascriptInterface
    public void gotoLocation(String S_ROAD_ID) {//js回调定位

        Intent iHistory = new Intent(WebViewHistoryYHActivity.this, MapRoadActivity.class);
        iHistory.putExtra("S_ROAD_ID", S_ROAD_ID);
        startActivity(iHistory);
    }
     @JavascriptInterface
    public void continueMaintainRoadId(String S_RECORD_ID, String S_ROAD_ID) {//js回调继续嗨执行
         if (isTasking){
             ToastUtils.showToast("正在养护中，不能执行另一个养护任务");
             return;
         }
         runUiDialog(true);
        this.S_ROAD_ID = S_ROAD_ID;
        this.S_RECORD_ID = S_RECORD_ID;
        mAAtt.initE().isCorrectPoint(WebViewHistoryYHActivity.this);//判断当前卫视是否合法

    }
    @JavascriptInterface
    public void mapTrackcallback (String S_RECORD_ID,boolean isDateCur,String S_ROAD_ID) {//H5调用的轨迹功能，H5传给的事件id,
        //养护轨迹==============
        if (AppTool.isNull(S_RECORD_ID)){
            ToastUtils.showToast("巡检记录id为空，不能查看轨迹");
            return;
        }
        Intent iHistory= new Intent(WebViewHistoryYHActivity.this, MapTrackAllActivity.class);
//        if (isDateCur){//当天的本地缓存轨迹
//            iHistory = new Intent(WebViewHistoryYHActivity.this, MapTrackCurActivity.class);
//
//        }else {//当天前的地图服务轨迹
//            iHistory = new Intent(WebViewHistoryYHActivity.this, MapTrackActivity.class);
//        }
        iHistory.putExtra("S_ROAD_ID", S_ROAD_ID);
        iHistory.putExtra("XJorYH", 1);
        iHistory.putExtra("S_RECORD_ID", S_RECORD_ID);
        startActivity(iHistory);

        //===================
    }
    @Override
    public void getQueryResultMap(ArrayList<AdapterBean> abList, int mTable) {

    }

    @Override
    public void getQueryResultMapSeach(ArrayList<AdapterBean> abList, int mTable) {
if (mTable==0){
    int size=abList.size();
    boolean b=false;
    for (int i = 0; i <size ; i++) {
        if (S_ROAD_ID.equals(abList.get(i).mag1)){
            MainMapYHActivity.geometry=abList.get(i).mFeaturesGeometry;
            requestEndLL();
            b=true;
            size=i;
        }
    }
    if (!b){
        runUiDialog(false);
        runUiToast("当前位置不再将要巡检的道路附近");
    }
}
    }
    public void requestEndLL() {
        HashMap map = new HashMap<String, String>();
       String url= String.format(AppConfig.ContinueYHurl,S_RECORD_ID);
        RxHttpUtils
                .createApi(ApiService.class)
                .YhContinue(url)
                .compose(Transformer.<String>switchSchedulers())
                .subscribe(new CommonObserver<String>() {
                    @Override
                    protected void onError(String errorMsg) {
                        runUiDialog(false);
                        ToastUtils.showToast(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String listResponse) {
                        Response response= new Gson().fromJson(listResponse,Response.class);
                        if (response.Code== 200) {
                            getEndLL(response);

                        } else {
                            runUiToast(response.Msg);
                        }
                    }


                });
    }
    public void getEndLL(Response listResponse){//选取最合理的坐标继续养护
        runUiDialog(false);
        try {
            List<Response.DataBean> dislist = new ArrayList<>();
            if (listResponse.Data==null||listResponse.Data.size()==0){
                runUiToast("返回坐标为空");
                return;
            }
            for (int i = 0; i < listResponse.Data.size(); i++) {
                Response. DataBean db1 = listResponse.Data.get(i);
                double dis = GeometryEngine.distanceBetween(new Point(db1.NX, db1.NY, SpatialReferences.getWgs84()),  mAAtt.initE().pLoca);
                db1.orderDis=dis;
                dislist.add(db1);
            }
            Collections.sort(dislist, new order());
            dislist.get(0).S_RECORD_ID=S_RECORD_ID;
            Intent intent = new Intent();
            intent.putExtra("DataBean", dislist.get(0) );
            setResult(MainMapYHActivity.resultCountinuTask,  intent);
            finish();
        }catch (Exception e){
            runUiToast(e.toString());
        }

    }
    public class order implements Comparator<Response.DataBean> {

        @Override
        public int compare(Response.DataBean lhs, Response.DataBean rhs) {
            // TODO Auto-generated method stub
//            return (int) (rhs.orderDis - lhs.orderDis);
            return lhs.orderDis.compareTo(rhs.orderDis);
        }

    }
    public void runUiToast(final String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showToast(msg);
            }
        });
    }
    public void runUiDialog(final boolean isShow){
runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isShow){
                    waitingDialog.show();
                }else {
                    waitingDialog.dismiss();
                }

            }
        });
    }
}