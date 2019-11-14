//package com.wavenet.ding.qpps.api;
//
//import android.annotation.TargetApi;
//import android.app.Activity;
//import android.content.ClipData;
//import android.content.ComponentName;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.content.pm.ResolveInfo;
//import android.graphics.Bitmap;
//import android.graphics.PixelFormat;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Environment;
//import android.os.Handler;
//import android.os.Looper;
//import android.os.Message;
//import android.os.Parcelable;
//import android.provider.MediaStore;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.Toolbar;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.MenuItem;
//import android.view.View;
//import android.webkit.JavascriptInterface;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.dereck.library.utils.GsonUtils;
//import com.google.gson.JsonSyntaxException;
//import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
//import com.tencent.smtt.sdk.ValueCallback;
//import com.tencent.smtt.sdk.WebChromeClient;
//import com.tencent.smtt.sdk.WebSettings;
//import com.tencent.smtt.sdk.WebView;
//import com.tencent.smtt.sdk.WebViewClient;
//import com.wavenet.ding.qpps.R;
//import com.wavenet.ding.qpps.activity.LoginActivity;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//
//
///**
// * Created by sld on 2017/8/3.
// */
//
//public class MyWebViewActivity extends BaseActivity {
//    private ValueCallback<Uri[]> mUploadCallbackAboveL;
//    WebView mWebView;
//    private TextView tv_title;
//    private Toolbar tb_toolbar;
//    private ValueCallback<Uri> mUploadMessage;// 表单的数据信息
//
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//            @Override
//            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                ArrayList<View> outView = new ArrayList<View>();
//                getWindow().getDecorView().findViewsWithText(outView, "QQ浏览器", View.FIND_VIEWS_WITH_TEXT);
//                int size = outView.size();
//                if (outView != null && outView.size() > 0) {
//                    outView.get(0).setVisibility(View.GONE);
//                }
//            }
//        });
//        getWindow().setFormat(PixelFormat.TRANSLUCENT);
//        setContentView(R.layout.activity_mywebview);
//        initView();
//        setSupportActionBar(tb_toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        String url = getIntent().getStringExtra("url");
//
//
//        Log.e("url", url);
//
//        mWebView = (WebView) findViewById(R.id.forum_context);
//        mWebView.getSettings().setJavaScriptEnabled(true);// 支持js
//        mWebView.getSettings().setDomStorageEnabled(true);
//        mWebView.getSettings().setUseWideViewPort(true); //自适应屏幕
//        WebSettings settings = mWebView.getSettings();
//        settings.setUseWideViewPort(true);
//        settings.setLoadWithOverviewMode(true);
//        settings.setJavaScriptEnabled(true);
//        settings.setSupportZoom(true);
//        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        mWebView.setWebViewClient(new WebViewClient() {
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                // TODO Auto-generated method stub
//                return super.shouldOverrideUrlLoading(view, url);
//            }
//
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                // TODO Auto-generated method stub
//                super.onPageStarted(view, url, favicon);
//                showDialog();
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                // TODO Auto-generated method stub
//                super.onPageFinished(view, url);
//                cancelDialog();
//            }
//
//        });
//        mWebView.setWebChromeClient(new WebChromeClient() {
//
//
//            @Override
//            public boolean onShowFileChooser(WebView webView,
//                                             ValueCallback<Uri[]> filePathCallback,
//                                             IX5WebChromeClient.FileChooserParams fileChooserParams) {
//                mUploadCallbackAboveL = filePathCallback;
//                take();
//                return true;
//            }
//
//            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
//                mUploadMessage = uploadMsg;
//                take();
//            }
//
//            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
//                mUploadMessage = uploadMsg;
//                take();
//            }
//
//            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
//                mUploadMessage = uploadMsg;
//                take();
//            }
//
//
//        });
//        mWebView.loadUrl("http://101.132.182.17:8061/html5/index.html?pageType=1&empId=2017001");
//        mWebView.addJavascriptInterface(this, "sld");
//
//    }
//
//    private Uri imageUri;
//
//    private void take() {
//        File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyApp");
//        // Create the storage directory if it does not exist
//        if (!imageStorageDir.exists()) {
//            imageStorageDir.mkdirs();
//        }
//        File file = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
//        imageUri = Uri.fromFile(file);
//
//        final List<Intent> cameraIntents = new ArrayList<Intent>();
//        final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        final PackageManager packageManager = getPackageManager();
//        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
//        for (ResolveInfo res : listCam) {
//            final String packageName = res.activityInfo.packageName;
//            final Intent i = new Intent(captureIntent);
//            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
//            i.setPackage(packageName);
//            i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//            cameraIntents.add(i);
//
//        }
//        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//        i.addCategory(Intent.CATEGORY_OPENABLE);
//        i.setType("image/*");
//        Intent chooserIntent = Intent.createChooser(i, "Image Chooser");
//        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));
//        startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
//    }
//
//    private final static int FILECHOOSER_RESULTCODE = 1;// 表单的结果回调</span>
//
//    @JavascriptInterface
//    public void showToast(String ssss) {
//        ShowToast.showToast(ssss);
//
//
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    //微信支付
//    private PayReq req = new PayReq();
//    private final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
//    private String APP_ID;
//
//    private void sendPayReq() {
//        msgApi.registerApp(APP_ID);
//        msgApi.sendReq(req);
//        Log.i(">>>>>", req.partnerId);
//    }
//
//    @JavascriptInterface
//    public void alpay1(String ssss) {
//        Log.e("dddddd",ssss);
//
//
//        try {
//            WXpayBean.WxBean wx = GsonUtils.getInstance().fromJson(ssss, WXpayBean.class).getWx();
//            APP_ID = wx.getAppid();
//            req.appId = wx.getAppid();
//            req.partnerId = wx.getPartnerid();
//            req.prepayId = wx.getPrepayid();
//            req.packageValue = wx.getPackageX();
//            req.nonceStr = wx.getNoncestr();
//            req.timeStamp = wx.getTimestamp();
//            req.sign = wx.getSign();
//            MyApplication.getSharedPreferences().edit().putString(MyRes.APP_ID, APP_ID).commit();
//            sendPayReq();
//
//
//        } catch (JsonSyntaxException e) {
//            showToast("参数异常");
//        }
//
//
//    }
//
//    @JavascriptInterface
//    public void alpay(String key) {
//        Log.e("dddddd",key);
//        alipay(key);
//    }
//
//    @JavascriptInterface
//    public void login() {
//        Intent intent = new Intent(context, LoginActivity.class);
//        Log.e("dddddd","登录");
//        startActivity(intent);
//    }
//
//
//
//    @JavascriptInterface
//    public String getToken() {
//        String token = MyApplication.getSharedPreferences().getString(MyRes.Token, "kong");
//
//
//        Log.e("dddddd","得到token");
//        return token;
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
//            mWebView.goBack();// 返回前一个页面
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    @JavascriptInterface
//    public void gotoPersonalActivity(String id) {
//        Intent intent = new Intent(context, OtherPersonalActivity.class);
//        intent.putExtra("id", id);
//        startActivity(intent);
//    }
//
//    private void initView() {
//        tv_title = (TextView) findViewById(R.id.tv_title);
//        tb_toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == FILECHOOSER_RESULTCODE) {
//            if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
//            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
//            if (mUploadCallbackAboveL != null) {
//                onActivityResultAboveL(requestCode, resultCode, data);
//            } else if (mUploadMessage != null) {
//                Log.e("result", result + "");
//                if (result == null) {
////	            		mUploadMessage.onReceiveValue(imageUri);
//                    mUploadMessage.onReceiveValue(imageUri);
//                    mUploadMessage = null;
//
//                    Log.e("imageUri", imageUri + "");
//                } else {
//                    mUploadMessage.onReceiveValue(result);
//                    mUploadMessage = null;
//                }
//
//
//            }
//        }
//    }
//
//
//    @SuppressWarnings("null")
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
//        if (requestCode != FILECHOOSER_RESULTCODE
//                || mUploadCallbackAboveL == null) {
//            return;
//        }
//
//        Uri[] results = null;
//        if (resultCode == Activity.RESULT_OK) {
//            if (data == null) {
//                results = new Uri[]{imageUri};
//            } else {
//                String dataString = data.getDataString();
//                ClipData clipData = data.getClipData();
//
//                if (clipData != null) {
//                    results = new Uri[clipData.getItemCount()];
//                    for (int i = 0; i < clipData.getItemCount(); i++) {
//                        ClipData.Item item = clipData.getItemAt(i);
//                        results[i] = item.getUri();
//                    }
//                }
//
//                if (dataString != null)
//                    results = new Uri[]{Uri.parse(dataString)};
//            }
//        }
//        if (results != null) {
//            mUploadCallbackAboveL.onReceiveValue(results);
//            mUploadCallbackAboveL = null;
//        } else {
//            results = new Uri[]{imageUri};
//            mUploadCallbackAboveL.onReceiveValue(results);
//            mUploadCallbackAboveL = null;
//        }
//
//        return;
//    }
//
//
//    //去支付
//    protected void alipay(final String key) {
//
//        Runnable payRunnable = new Runnable() {
//            @Override
//            public void run() {
//                // 构造PayTask 对象
//                PayTask alipay = new PayTask(MyWebViewActivity.this);
//                // 调用支付接口，获取支付结果
//                String result = null;
//
//                try {
//                    result = alipay.pay(key);
//
//                } catch (Exception e) {
//                    Looper.prepare();
//                    Toast.makeText(MyWebViewActivity.this, "请安装支付宝", Toast.LENGTH_SHORT).show();
//                    Looper.loop();
//                }
//
//
//                Message msg = new Message();
//                msg.what = SDK_PAY_FLAG;
//                msg.obj = result;
//                mHandler.sendMessage(msg);
//            }
//        };
//
//        // 必须异步调用
//        Thread payThread = new Thread(payRunnable);
//        payThread.start();
//    }
//
//    private static final int SDK_PAY_FLAG = 1;
//    private Handler mHandler = new Handler() {
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case SDK_PAY_FLAG: {
//                    PayResult payResult = new PayResult((String) msg.obj);
//                    String resultStatus = payResult.getResultStatus();
//                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
//                    if (TextUtils.equals(resultStatus, "9000")) {
//                        Toast.makeText(MyWebViewActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(context, MyWebViewActivity.class);
//                        intent.putExtra("url", "http://m.yuanbaotaoche.com/myOrder");
//                        startActivity(intent);
//
//
//                    } else {
//                        // 判断resultStatus 为非“9000”则代表可能支付失败
//                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
//                        if (TextUtils.equals(resultStatus, "8000")) {
//                            Toast.makeText(MyWebViewActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
//
//                        } else {
//                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
//                            Toast.makeText(MyWebViewActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                    break;
//                }
//
//            }
//        }
//
//    };
//}