package com.wavenet.ding.qpps.fragment;

import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.dereck.library.base.BaseMvpFragment;
import com.dereck.library.view.CustomProgressDialog;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.mvp.p.OneFragmentRequestPresenter;
import com.wavenet.ding.qpps.mvp.v.AdminFragmentRequestView;
import com.wavenet.ding.qpps.utils.AppUtils;
import com.wavenet.ding.qpps.utils.WebUtil;

import java.util.ArrayList;

/**
 * Created by zoubeiwen on 2018/8/29.
 */

public class ThirdFragment extends BaseMvpFragment<AdminFragmentRequestView, OneFragmentRequestPresenter> implements AdminFragmentRequestView {
    private final static int FILECHOOSER_RESULTCODE = 1;// 表单的结果回调</span>
    public CustomProgressDialog waitingDialog;
    WebView mWebView;
    SwipeRefreshLayout mSrlrefresh;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private ValueCallback<Uri> mUploadMessage;// 表单的数据信息
    private Uri imageUri;

    public static ThirdFragment newInstance() {

        Bundle args = new Bundle();

        ThirdFragment fragment = new ThirdFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected OneFragmentRequestPresenter createPresenter() {
        return new OneFragmentRequestPresenter();
    }

    @Override
    public int getLayoutId() {
        getActivity().getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                ArrayList<View> outView = new ArrayList<View>();
                getActivity().getWindow().getDecorView().findViewsWithText(outView, "QQ浏览器", View.FIND_VIEWS_WITH_TEXT);
                int size = outView.size();
                if (outView != null && outView.size() > 0) {
                    outView.get(0).setVisibility(View.GONE);
                }
            }
        });
        getActivity().getWindow().setFormat(PixelFormat.TRANSLUCENT);
        return R.layout.fragment_one;
    }

    @Override
    public void init() {
        String url = getActivity().getIntent().getStringExtra("url");


        mWebView = mainView.findViewById(R.id.forum_context);
        WebUtil.getwebview(mWebView);
        mWebView.getSettings().setJavaScriptEnabled(true);// 支持js
        mWebView.addJavascriptInterface(this, "maintainObject");//添加js监听 这样html就能调用客
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setUseWideViewPort(true); //自适应屏幕
        WebSettings settings = mWebView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
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
//                showDialog();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                cancelDialog();
                super.onPageFinished(view, url);
                mSrlrefresh.setRefreshing(false);

            }

        });
        mWebView.loadUrl(AppUtils.getH5F3(getActivity()));
//        mWebView.loadUrl(     AppUtils.getH5F1(getActivity()));
    }

    @Override
    public void requestData() {
        mSrlrefresh = mainView.findViewById(R.id.srl_refresh);
        mSrlrefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWebView.reload();//前进
            }
        });
    }

    /**
     * 显示进度条
     *
     * @param
     */
    public void showDialog() {
        try {
            if (null == waitingDialog) {
                waitingDialog = CustomProgressDialog.createDialog(getActivity());
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

    @Override
    public void requestSuccess(int resultid, String result) {

    }

    @Override
    public void requestSuccessJC(int resultid, String result) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void requestFailure(int resultid, String result) {

    }

    @Override
    public void requestFailureJC(int resultid, String result) {

    }

    public boolean onBackPressed( ){
        if ( mWebView.canGoBack()) {
            mWebView.goBack();// 返回前一个页面
            return true;
        }
        return false;
    }
}
