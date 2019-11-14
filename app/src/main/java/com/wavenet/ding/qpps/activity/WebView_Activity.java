package com.wavenet.ding.qpps.activity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;

import com.dereck.library.base.BaseMvpActivity;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.mvp.p.WebView_ActivityRequestPresenter;
import com.wavenet.ding.qpps.mvp.v.WebView_ActivityRequesView;
import com.wavenet.ding.qpps.utils.WebUtil;
import com.wavenet.ding.qpps.view.AndroidJs;

public class WebView_Activity extends BaseMvpActivity<WebView_ActivityRequesView, WebView_ActivityRequestPresenter> implements  WebView_ActivityRequesView,AndroidJs.CallBack {
    SwipeRefreshLayout webview_refresh;
    WebView webview_content;

    @Override
    public int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public void init() {
        String url = getIntent().getStringExtra("webview");
        webview_content = (WebView)findViewById(R.id.webview_content_x);
        WebUtil.getwebview(webview_content);
        webview_content.getSettings().setJavaScriptEnabled(true);// 支持js
        webview_content.getSettings().setDomStorageEnabled(true);
        webview_content.getSettings().setUseWideViewPort(true); //自适应屏幕
        WebSettings settings = webview_content.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webview_content.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                return super.shouldOverrideUrlLoading(view, url);
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
                showDialog();
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                cancelDialog();
//                webview_refresh.setRefreshing(false);
                super.onPageFinished(view, url);

            }
        });
        webview_content.setWebChromeClient(new WebChromeClient(){
            public boolean onShowFileChooser(WebView webView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             IX5WebChromeClient.FileChooserParams fileChooserParams) {

                return true;
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {

            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {

            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {

            }

            @Override
            public void onReceivedTitle(WebView webView, String s) {
            }
        });
        webview_content.loadUrl(url);
//        webview_content.loadUrl("http://222.66.154.70:2088/QP_H5/module/home/index.html#/YHJL_List_VYH?personname=xjtest");
//        webview_content.loadUrl("http://222.66.154.70:2088/QP_H5/module/home/index.html#/XCFrame");
        String htmlString = "<h1>Title</h1><p>This is HTML text<br /><i>Formatted in italics</i><br />Anothor Line</p>";
        // 载入这个html页面
//        webview_content.loadData(htmlString, "text/html", "utf-8");
//        webview_content.loadUrl("file:///android_asset/test.html");
//        webview_content.addJavascriptInterface(new JSHook(), "hello");
        AndroidJs androidJs = new AndroidJs(WebView_Activity.this, webview_content);
        androidJs.setCallBack(this);
        webview_content.addJavascriptInterface(androidJs, "maintainObject");
    }

    @Override
    public void requestData() {
        /*webview_refresh = (SwipeRefreshLayout)findViewById(R.id.webview_refresh);
        webview_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webview_content.reload();
            }
        });*/
        /*final String url = "http://222.66.154.70:2088/up/image/20190507/20190507204525_7841.png";
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            final int index = i;
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.d("qingpu", "index = "+index);
//                        Thread.sleep(2000);

                        URL imageurlx = new URL(url);
                        HttpURLConnection conn = (HttpURLConnection) imageurlx.openConnection();
//                        conn.setConnectTimeout(5000);
                        conn.setDoInput(true);
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        Log.d("qingpu", "bitmap ="+bitmap);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }*/

    }

    @Override
    protected WebView_ActivityRequestPresenter createPresenter() {
        return new WebView_ActivityRequestPresenter();
    }

    @Override
    public void resultSuccess(Object result) {

    }

    @Override
    public void resultFailure(String result) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    /*@JavascriptInterface
    public void alpay(String key) {
        Log.e("dddddd", key);
        //   alipay(key);
    }


    @JavascriptInterface
    public void back(){
        Log.d("qingpu", "h5 callback = ");
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webview_content.destroy();
    }

    @Override
    public void activityFinish() {
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webview_content.canGoBack()) {
            webview_content.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    /*public class AndroidJs{
        Context mContext;
        public AndroidJs(Context context){
            this.mContext = context;
        }

        @JavascriptInterface
        public void javaMethod(String p){
            Log.d("qingpu" , "AndroidJs.JavaMethod() called! + "+p);
        }
        @JavascriptInterface
        public void showAndroid(){
            String info = "来自手机内的内容！！！";
            Log.d("qingpu", "showAndroid be called ");
//            webview_content.loadUrl("javascript:show('"+info+"')");
        }
        @JavascriptInterface
        public String getInfo(){
            return "获取手机内的信息！！";
        }
    }*/
}
