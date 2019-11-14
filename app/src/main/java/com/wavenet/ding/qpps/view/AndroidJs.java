package com.wavenet.ding.qpps.view;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;

import com.dereck.library.utils.ToastUtils;
import com.tencent.smtt.sdk.WebView;
import com.wavenet.ding.qpps.activity.MapLocaActivity;
import com.wavenet.ding.qpps.activity.MapTrackPlayActivity;
import com.wavenet.ding.qpps.utils.AppTool;

public class AndroidJs {
    private Context mContext;
    private WebView mWebView;
    public AndroidJs(Context context) {
        this.mContext = context;
    }
    public AndroidJs(Context context, WebView webView) {
        this.mContext = context;
        this.mWebView = webView;
    }
    @JavascriptInterface
    public void javaMethod(String p){
    }
    @JavascriptInterface
    public void showAndroid(){
        String info = "来自手机内的内容！！！";
        mWebView.loadUrl("javascript:show('"+info+"')");

    }
    @JavascriptInterface
    public String getInfo(){
        return "获取手机内的信息！！";
    }
    @JavascriptInterface
    public void backPreviousView(){
        callBack.activityFinish();
    }
    @JavascriptInterface
    public void mapTrajectorycallback(String S_RECODE_ID,String s_man_name,String time,String xclc){

        Intent iHistory = new Intent(mContext, MapTrackPlayActivity.class);
        iHistory.putExtra("S_RECODE_ID", S_RECODE_ID);
        iHistory.putExtra("s_man_name", s_man_name);
        iHistory.putExtra("time", time);
        iHistory.putExtra("xclc", xclc);
        mContext.startActivity(iHistory);
    }


    @JavascriptInterface
    public void mapLocacallback (String lat ,String lon) {//H5调用的轨迹功能，H5传给的事件id
        if (AppTool.isNull(lat)||AppTool.isNull(lon)){
            ToastUtils.showToast("事件位置信息错误");
            return;
        }
        Intent iHistory = new Intent(mContext, MapLocaActivity.class);
        iHistory.putExtra("lat", lat);
        iHistory.putExtra("lon", lon);
        mContext.startActivity(iHistory);
//        Intent iHistory;
//            iHistory = new Intent(WebViewHistoryXJActivity.this, MapTrackCurActivity.class);
//
//
//        iHistory.putExtra("S_RECORD_ID", "S_RECORD_ID");
//        startActivity(iHistory);
    }

    private AndroidJs.CallBack callBack ;

    public void setCallBack(AndroidJs.CallBack back){
        this.callBack = back;
    }
    public interface CallBack{
        void activityFinish();
    }
}
