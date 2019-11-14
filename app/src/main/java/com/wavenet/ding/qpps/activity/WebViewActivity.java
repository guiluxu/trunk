package com.wavenet.ding.qpps.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dereck.library.view.CustomProgressDialog;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.wavenet.ding.qpps.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class WebViewActivity extends AppCompatActivity implements View.OnClickListener {
    private final static int FILECHOOSER_RESULTCODE = 1;// 表单的结果回调</span>
    WebView mWebView;
    public CustomProgressDialog waitingDialog;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private TextView tv_title;
    private ValueCallback<Uri> mUploadMessage;// 表单的数据信息
    private Uri imageUri;
    TextView mTvjiucuo;
    RelativeLayout mTltop;
    public String urlTem="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                ArrayList<View> outView = new ArrayList<View>();
                getWindow().getDecorView().findViewsWithText(outView, "QQ浏览器", View.FIND_VIEWS_WITH_TEXT);
                int size = outView.size();
                if (outView != null && outView.size() > 0) {
                    outView.get(0).setVisibility(View.GONE);
                }
            }
        });
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.activity_mywebview);
        initView();


        mTltop = findViewById(R.id.rl_top);
        mTvjiucuo = findViewById(R.id.tv_jiucuo);
        mTvjiucuo.setVisibility(View.GONE);
        mTvjiucuo.setOnClickListener(this);
        mWebView = findViewById(R.id.forum_context);
        mWebView.addJavascriptInterface(this, "maintainObject");//添加js监听 这样html就能调用客
        WebSettings settings = mWebView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setDomStorageEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.setWebViewClient(new WebViewClient() {

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
                super.onPageFinished(view, url);
                urlTem=url;
                cancelDialog();
            }

        });
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView webView, String s) {
//                tv_title.setText(s);
//                return false;
            }

            public boolean onShowFileChooser(WebView webView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             IX5WebChromeClient.FileChooserParams fileChooserParams) {
                mUploadCallbackAboveL = filePathCallback;
                take();
                return true;
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMessage = uploadMsg;
                take();
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                mUploadMessage = uploadMsg;
                take();
            }

            @Override
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                mUploadMessage = uploadMsg;
                take();
            }


        });

//        mWebView.addJavascriptInterface(this, "sld");
        String url = getIntent().getStringExtra("url");
        mWebView.loadUrl(url);
        dealData();
    }
    public abstract void dealData();


 public void isShowRight(boolean isshow){
     mTvjiucuo.setVisibility(isshow?View.VISIBLE:View.INVISIBLE);
 }
 public void isShowTop(boolean isshow){
     mTltop.setVisibility(isshow?View.VISIBLE:View.GONE);
 }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
    }

    /**
     * 显示进度条
     *
     * @param
     */
    public void showDialog() {
        try {
            if (null == waitingDialog) {
                waitingDialog = CustomProgressDialog.createDialog(this);
                waitingDialog.setMessage("正在加载..");
                waitingDialog.show();
            } else if (null != waitingDialog) {
                waitingDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏进度条
     */
    public void cancelDialog() {
        try {
            if (null != waitingDialog) {
                waitingDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void take() {
        File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyApp");
        // Create the storage directory if it does not exist
        if (!imageStorageDir.exists()) {
            imageStorageDir.mkdirs();
        }
        File file = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        imageUri = Uri.fromFile(file);

        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent i = new Intent(captureIntent);
            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            i.setPackage(packageName);
            i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            cameraIntents.add(i);
        }
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        Intent chooserIntent = Intent.createChooser(i, "Image Chooser");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));
        startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

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

//    @JavascriptInterface
//    public void alpay(String key) {
//        //   alipay(key);
//    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initView() {
        findViewById(R.id.ll_back).setOnClickListener(this);
        tv_title = findViewById(R.id.tv_activity_title);
        tv_title.setText(getIntent().getExtras().getString("title"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (mUploadMessage != null) {
                if (result == null) {
//	            		mUploadMessage.onReceiveValue(imageUri);
                    mUploadMessage.onReceiveValue(imageUri);
                    mUploadMessage = null;

                } else {
                    mUploadMessage.onReceiveValue(result);
                    mUploadMessage = null;
                }


            }
        }
    }


    @SuppressWarnings("null")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode != FILECHOOSER_RESULTCODE
                || mUploadCallbackAboveL == null) {
            return;
        }

        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
                results = new Uri[]{imageUri};
            } else {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();

                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }

                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        if (results != null) {
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
        } else {
            results = new Uri[]{imageUri};
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
        }

        return;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;

        }
    }


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
    @JavascriptInterface
    public void backPreviousView() {
        finish();
    }
}