package com.wavenet.ding.qpps.activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;

import com.dereck.library.utils.ToastUtils;
import com.wavenet.ding.qpps.utils.AppAttribute;
import com.wavenet.ding.qpps.utils.AppTool;

import java.util.Timer;
import java.util.TimerTask;

public class WebViewHistoryXJActivity extends WebViewActivity implements View.OnClickListener {
    boolean isTasking=false;
    boolean isStartde=false;
    public final int dealSuccess=100;
    String url;
    @Override
    public void dealData() {
        urlTem=url = getIntent().getStringExtra("url");
        isStartde=getIntent().getBooleanExtra("isStartde",false);
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
    public void mapTrackcallback (String S_RECORD_ID,boolean isDateCur) {//H5调用的轨迹功能，H5传给的事件id
        if (AppTool.isNull(S_RECORD_ID)){
            ToastUtils.showToast("巡检记录id为空，不能查看轨迹");
            return;
        }
        Intent iHistory= new Intent(WebViewHistoryXJActivity.this, MapTrackAllActivity.class);
//        if (isDateCur){//当天的本地缓存轨迹
//            iHistory = new Intent(WebViewHistoryXJActivity.this, MapTrackCurActivity.class);
//
//
//        }else {//当天前的地图服务轨迹
//            iHistory = new Intent(WebViewHistoryXJActivity.this, MapTrackActivity.class);
//        }
        iHistory.putExtra("XJorYH", 0);
        iHistory.putExtra("S_RECORD_ID", S_RECORD_ID);
        startActivity(iHistory);
    }
    @JavascriptInterface
    public void mapLocacallback (String lat ,String lon) {//H5调用的轨迹功能，H5传给的事件id
        if (AppTool.isNull(lat)||AppTool.isNull(lon)){
            ToastUtils.showToast("事件位置信息错误");
            return;
        }
        Intent iHistory = new Intent(WebViewHistoryXJActivity.this, MapLocaActivity.class);
        iHistory.putExtra("lat", lat);
        iHistory.putExtra("lon", lon);
        startActivity(iHistory);
//        Intent iHistory;
//            iHistory = new Intent(WebViewHistoryXJActivity.this, MapTrackCurActivity.class);
//
//
//        iHistory.putExtra("S_RECORD_ID", "S_RECORD_ID");
//        startActivity(iHistory);
    }
     @JavascriptInterface
    public void xjDealmapcallback(String S_MANGE_ID) {//H5调用的处置功能，H5传给的记录id
         if (isStartde){
             ToastUtils.showToast("正在巡检中，不能处置");
             return;
         }
         if (AppTool.isNull(S_MANGE_ID)){
             ToastUtils.showToast("巡检事件id为空，不能处置");
             return;
         }
         Intent iHistory = new Intent(WebViewHistoryXJActivity.this, XJDealActivity.class);
         iHistory.putExtra("S_MANGE_ID", S_MANGE_ID);
         startActivityForResult(iHistory,dealSuccess);

    }
    @JavascriptInterface
    public  boolean xjIsStartdecallback(String S_RECORD_ID) {//H5调用的判断当前操作的记录是否是正在执行的记录
         if (isStartde){
          if (S_RECORD_ID.equals(AppAttribute.F.getXJID(WebViewHistoryXJActivity.this))){
              return true;
          }else {
              return false;
          }

         }

       return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case dealSuccess:
                if (resultCode==XJDealActivity.dealSuccess){
                    mWebView.loadUrl(url+"&pagetab=dcz");
//                    mWebView.reload();
                    // 画轨迹
                    TimerTask task = new TimerTask(){
                        public void run(){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mWebView.reload();
                                }
                            });
                        }
                    };
                    Timer timer = new Timer();
                    timer.schedule(task, 1000);
//
                }
                break;
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            if (urlTem.contains("XCSJTab?")){
                finish();
            }else {
                return  super.onKeyDown(keyCode,event);
            }

            return true;
        }else {
            finish();
        }
        return false;
    }
}