package com.wavenet.ding.qpps.view;

import android.content.Context;
import android.net.http.SslError;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dereck.library.view.LoadingWaitView;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.interf.InterfListen;
import com.wavenet.ding.qpps.utils.AppAttribute;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.DateUtil;

import java.text.SimpleDateFormat;

/**
 * 曲线图表
 * Created by Administrator on 2017/7/25.
 */

public class ViewChartCurve extends LinearLayout implements View.OnClickListener ,InterfListen.setTimeSelete,DateDialog.OnDateSetListener{

    private Context mContext;
    private TextView day_this;//当前显示的日期
    private ImageView day_before;//前一天
    private ImageView day_after;//后一天
    private WebView detail_web;//加载图表
    private LoadingWaitView mLoadingWaitView;
    DateDialog dateDialog;

    private SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd"); //设置获取系统时间格式
    private String date = sDateFormat.format(new java.util.Date()); //获取当前系统

    private String s_no = "";
    AppAttribute mAppAttr;
    String time;
    public ViewChartCurve(Context context) {
        super(context);
        initView(context);
    }

    public ViewChartCurve(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ViewChartCurve(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;

        LayoutInflater.from(mContext).inflate(R.layout.view_chartcurve, this);
        if (!isInEditMode()) {
            day_this =  findViewById(R.id.tv_day);
            day_this.setOnClickListener(this);
             time=AppTool.getCurrentDate();
            day_this.setText(time);
            dateDialog=new DateDialog(mContext);
            dateDialog.setOnDateSetListener(this);
            day_before =  findViewById(R.id.iv_before);
            day_after =  findViewById(R.id.iv_after);
            detail_web =  findViewById(R.id.detail_web);
            day_before.setOnClickListener(this);
            day_after.setOnClickListener(this);
            mLoadingWaitView = (LoadingWaitView) findViewById(R.id.viewchart_loadingWaitView);
            getwebview();
        }
        mAppAttr=new AppAttribute(mContext);
        mAppAttr.initH().setTimeSelete(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_before://前一天
                isWebviewShow(View.INVISIBLE);
                time=DateUtil.getSpecifiedDayBefore(day_this.getText().toString());
                day_this.setText(time);
                if(mE!=null){
                    mE.eA(time,s_no);

                }
                break;
            case R.id.iv_after://后一天
                String time=DateUtil.getSpecifiedDayAfter(day_this.getText().toString());
                mAppAttr.initH().setTimeSelete(mContext,day_this,time);
                break;
            case R.id.tv_day:
                dateDialog.show();
                break;
        }
    }


    @Override
    public void onDateSet(DatePicker view, int year, String month, String dayOfMonth) {
        String time=year + "-" + month + "-" + dayOfMonth;
        mAppAttr.initH().setTimeSelete(mContext,day_this,time);
    }
    @Override
    public void okTime() {
if(mE!=null){
    isWebviewShow(View.INVISIBLE);
    mE.eA(day_this.getText().toString(),s_no);
}
    }



    public void setShow(String s_no){
        this.s_no=s_no;
        time=AppTool.getCurrentDate();
        day_this.setText(time);
        day_this.setText(date.toString());//显示当前时间
    }

    /**
     * 初始化WebView
     */
    private void getwebview() {
        WebSettings settings = detail_web.getSettings();
        settings.setJavaScriptEnabled(true);// 启用JS脚本
        settings.setSupportZoom(false); // 支持缩放
        settings.setBuiltInZoomControls(true); // 启用内置缩放装置
        settings.setAllowFileAccess(true); // 设置可以访问文件
//        settings.setUseWideViewPort(true);// 设置此属性，可任意比例缩放
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setLoadWithOverviewMode(true);// 缩放至屏幕的大小
//        settings.setBlockNetworkImage(false);
//        settings.setLoadsImagesAutomatically(true); // 支持自动加载图片
        settings.setDisplayZoomControls(false);
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        detail_web.requestFocus();
        detail_web.requestFocusFromTouch();// 获取手势焦点

    }
String chartdata="";
String urlfile="";
public  void setsdata(String chartdata,String urlfile){
    this.urlfile=urlfile;
   this. chartdata=chartdata;
//   this. chartdata="";
    detail_web.addJavascriptInterface(new Object() {
                                    //@param message:  html页面传进来的数据
                                    @JavascriptInterface
                                    public String getLocationData() {
                                        return ViewChartCurve.this.chartdata.toString(); // 把本地数据弄成json串，传给html
                                    }
                                }, "MyBrowserAPI");//MyBrowserAPI:自定义的js函数名

                                detail_web.setWebViewClient(new ViewChartCurve.HelloWebViewClient());
                                detail_web.setWebChromeClient(new ViewChartCurve.HelloWebChromeClient());
                                detail_web.loadUrl("file:///android_asset/"+urlfile);
}




    // Web视图
    private class HelloWebViewClient extends WebViewClient {
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
//            progressBar.setVisibility(View.GONE);
            mLoadingWaitView.setVisibility(View.GONE);
        }
    }

    //加载数据
    private class HelloWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
//                progressBar.setVisibility(View.GONE);
                mLoadingWaitView.setVisibility(View.GONE);
                day_before.setEnabled(true);
                day_after.setEnabled(true);

            } else {

                mLoadingWaitView.setVisibility(View.VISIBLE);
                day_before.setEnabled(false);
                day_after.setEnabled(false);
            }
            super.onProgressChanged(view, newProgress);
        }
    }
public void isWebviewShow(int isshow){
    detail_web.setVisibility(isshow);
    if (isshow==View.INVISIBLE){
        mLoadingWaitView.setVisibility(View.VISIBLE);
    }else{
        mLoadingWaitView.setVisibility(View.GONE);
    }


}
    public static  int legend=0;
    Runnable  runnable = new Runnable() {
        @Override
        public void run() {
            if(detail_web.getLayoutParams() instanceof RelativeLayout.LayoutParams){
                RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) detail_web.getLayoutParams();
                linearParams.height = newHeight;
                detail_web. setLayoutParams(linearParams);
                if (mD!=null){
                    mD.dA(lH);
                }
            }
        }
    };
     int newHeight;
     String lH="8%";
public void changeheigt(InterfListen.D mD){
    this.mD=mD;
    int h=200;
    lH="12%";
    if (legend>0&&legend<4){
        h=250;
        lH="22%";
    }else  if (legend>3&&legend<7){
        h=280;
        lH="30%";
    }else if (legend>6&&legend<13){
        h=380;
        lH="45%";
    }else if (legend>13&&legend<19){
        h=400;
        lH="50%";
    }
    newHeight = (int)  (h * getResources().getDisplayMetrics().density);

    new Handler().postDelayed(runnable,0);
}
    InterfListen.D mD;
InterfListen.E mE;
public void setEListen(InterfListen.E mE){
    this.mE=mE;
}
}
