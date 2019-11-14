package com.wavenet.ding.qpps.fragment;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dereck.library.base.BaseMvpFragment;
import com.dereck.library.download.DownloadObserver;
import com.dereck.library.utils.GsonUtils;
import com.dereck.library.utils.RxHttpUtils;
import com.dereck.library.utils.ToastUtils;
import com.google.gson.Gson;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.activity.ChangePasswordActivity;
import com.wavenet.ding.qpps.activity.LoginActivity;
import com.wavenet.ding.qpps.activity.WebViewDdyActivity;
import com.wavenet.ding.qpps.activity.WebViewErrorActivity;
import com.wavenet.ding.qpps.bean.DdyBean;
import com.wavenet.ding.qpps.bean.VersionBean;
import com.wavenet.ding.qpps.mvp.p.FifthFragmentRequestPresenter;
import com.wavenet.ding.qpps.mvp.v.AdminFragmentRequestView;
import com.wavenet.ding.qpps.utils.AppConfig;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.CacheManager;
import com.wavenet.ding.qpps.utils.LogUtils;
import com.wavenet.ding.qpps.utils.MapUtil;
import com.wavenet.ding.qpps.utils.SPUtil;

import java.io.File;

import io.reactivex.disposables.Disposable;

/**
 * Created by zoubeiwen on 2018/8/29.
 */

public class FifthFragment extends BaseMvpFragment<AdminFragmentRequestView, FifthFragmentRequestPresenter> implements AdminFragmentRequestView, View.OnClickListener {
    TextView mTvlogintime, mUpdata_cade, mTvcache, mTvquitlogin, mTvchangelogin,mTvpaidan,mTvshenpi,textView3,textView4,textView5;
    LinearLayout mLlcleancache,mLlupdatapassword,mLljssupport ,mLlupdatehistor ,mLlupdataApp,mLldiaodu,mLlUp;
    ImageView ivClose;
    String role,townname,user,myname;
    public static FifthFragment newInstance() {

        Bundle args = new Bundle();

        FifthFragment fragment = new FifthFragment();
//        args.putString("siteId", siteId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected FifthFragmentRequestPresenter createPresenter() {
        return new FifthFragmentRequestPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_fifth;
    }

    @Override
    public void init() {
        role=SPUtil.getInstance(getActivity()).getStringValue(SPUtil.APP_ROLE);
        TextView tv_role = mainView.findViewById(R.id.tv_role);
        tv_role.setText(role);
        TextView tv_username = mainView.findViewById(R.id.tv_username);
        tv_username.setText(SPUtil.getInstance(getActivity()).getStringValue(SPUtil.USERNO));
        mTvlogintime = mainView.findViewById(R.id.tv_logintime);
        mTvlogintime.setText(LoginActivity.mLoginTime);
        mLlupdatapassword = mainView.findViewById(R.id.updata_password);
        mLlupdatapassword.setOnClickListener(this);
        mLljssupport = mainView.findViewById(R.id.ll_jssupport);
        mLljssupport.setOnClickListener(this);
        mLlupdatehistor = mainView.findViewById(R.id.ll_updatehistor);
        mLlupdatehistor.setOnClickListener(this);
        mLlUp = mainView.findViewById(R.id.llUp);
        textView3 = mainView.findViewById(R.id.textView3);
        textView3.setOnClickListener(this);
        textView4 = mainView.findViewById(R.id.textView4);
        textView5 = mainView.findViewById(R.id.textView5);
        ivClose = mainView.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(this);
        mLlupdataApp = mainView.findViewById(R.id.updata_App);
        mLlupdataApp.setOnClickListener(this);
        mUpdata_cade = mainView.findViewById(R.id.updata_cade);
        mTvcache = mainView.findViewById(R.id.tv_cache);
        mLlcleancache = mainView.findViewById(R.id.ll_cleancache);
        mLlcleancache.setOnClickListener(this);
        mTvquitlogin = mainView.findViewById(R.id.tv_quitlogin);
        mTvquitlogin.setOnClickListener(this);
        mTvchangelogin = mainView.findViewById(R.id.tv_changelogin);
        mTvchangelogin.setOnClickListener(this);
        mainView.findViewById(R.id.ll_dpd).setOnClickListener(this);
//        mainView.findViewById(R.id.ll_dpsp).setOnClickListener(this);

if ("区排水所调度员".equals(role)){
    mLldiaodu = mainView.findViewById(R.id.ll_diaodu);
    mLldiaodu.setVisibility(View.VISIBLE);
    mTvpaidan = mainView.findViewById(R.id.tv_paidan);
    mTvshenpi = mainView.findViewById(R.id.tv_shenpi);
    String des=SPUtil.getInstance(getActivity()).getStringValue(SPUtil.APP_DES);
    townname=  SPUtil.getInstance(getActivity()).getStringValue(SPUtil.APP_TOWNNAME);
    user=  SPUtil.getInstance(getActivity()).getStringValue(SPUtil.USERNO);
    myname=  SPUtil.getInstance(getActivity()).getStringValue(SPUtil.APP_MYNAME);
//        des=des.replace("'","");
//    MapUtil.getDes(des);
    presenter.requestDDY(des,role);
}
    }

    @Override
    public void requestData() {
        mUpdata_cade.setText(AppTool.getVersion(getActivity()));
        try {
            String mCachestr = CacheManager.getTotalCacheSize(getContext()) + "";
            mTvcache.setText(mCachestr);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_jssupport:
                Intent intent=new Intent(getActivity(), WebViewErrorActivity.class);
                 intent.putExtra("title", "技术支持");
                 intent.putExtra("url", AppConfig.Jssupport);
                 startActivity(intent);
                break;  case R.id.ll_updatehistor:
                Intent intent1=new Intent(getActivity(), WebViewErrorActivity.class);
                intent1.putExtra("title", "更新日志");
                intent1.putExtra("url", AppConfig.Updatehistor);
                 startActivity(intent1);
                break; case R.id.updata_password:
                Intent i=new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(i);
                break;
            case R.id.ll_cleancache:
                MapUtil.getInstance(getActivity()).showcleanCacheDialog(getActivity(), mTvcache);
                break;
            case R.id.tv_quitlogin:
                MapUtil.getInstance(getActivity()).showExitDialog(getActivity(), 1);
                break;
            case R.id.tv_changelogin:
                MapUtil.getInstance(getActivity()).showExitDialog(getActivity(), 0);
                break;
            case R.id.ll_dpd:
                Intent mIntentdpd = new Intent(getActivity(), WebViewDdyActivity.class);
                mIntentdpd.putExtra("url", String.format(AppConfig.DDYdpdurl,user,myname,townname));
                startActivity(mIntentdpd);
                break;
            case R.id.updata_App:
                presenter.getAPPCode(2);// 检测版本是否更新
                break;

            case R.id.ll_dsp:
//                Intent mIntentdsp = new Intent(getActivity(), WebViewErrorActivity.class);
//                mIntentdsp.putExtra("url", url);
//                startActivity(mIntentdsp);
                break;

            case R.id.ivClose:
                mLlUp.setVisibility(View.GONE);
                break;
            case R.id.textView3:
                String url = versionBean.Data.URL;
                String fileName = "PSSSYH.APK";

                LogUtils.e("进度", Looper.myLooper() == Looper.getMainLooper()
                );
                RxHttpUtils
//                        .downloadFile("http://222.66.154.70:8079/FileList/APK/JSHZ.apk")
                        .downloadFile(url)
                        .subscribe(new DownloadObserver(fileName) {
                            @Override
                            protected void getDisposable(Disposable d) {
//                                disposables.add(d);
                            }

                            @Override
                            protected void onError(String errorMsg) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        textView3.setEnabled(true);
                                    }
                                });

                            }

                            @Override
                            protected void onSuccess(long bytesRead, long contentLength, final float progress, final boolean done, final String filePath) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        textView3.setText("下载中：" + progress + "%");
                                        if (done) {
                                            textView3.setEnabled(true);
                                            textView3.setText("下载完成");
                                            installApk(new File(filePath));

                                        }
                                    }
                                });


                            }
                        });
                textView3.setEnabled(false);
                break;
        }
    }

    VersionBean versionBean;
    @Override
    public void requestSuccess(int resultid, String result) {
        if (!AppTool.isNull(result) && !result.contains("error")) {
            switch (resultid) {
                case 1:
                    DdyBean ddyData=new Gson().fromJson(result,DdyBean.class);
                    if (!"200".equals(ddyData.Code)){
                        ToastUtils.showToast(ddyData.Msg);
                        return;
                    }
if (ddyData.Data!=null){
    mTvpaidan.setText(AppTool.isNull(ddyData.Data.ManageCnt)?"0":ddyData.Data.ManageCnt);
            mTvshenpi.setText(AppTool.isNull(ddyData.Data.ErrCnt)?"0":ddyData.Data.ErrCnt);
}
                    break;
              case 2:
                  versionBean = GsonUtils.getObject(result, VersionBean.class);
                  if (!"200".equals(versionBean.Code)){
                      ToastUtils.showToast(versionBean.Msg);
                      return;
                  }
                  PackageInfo packageInfo = null;
                  try {
                      packageInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);

                      int cur_num = packageInfo.versionCode;
                      /////////测试
//                        mThread = new Thread() {
//                            @Override
//                            public void run() {
//                                myHandler.sendEmptyMessageDelayed(1, 3000);
//                            }
//                        };
//                        mThread.start();
                      ////////
                      if (versionBean.Data==null){
                          ToastUtils.showToast("版本监测失败");
                          return;}

                      if (cur_num < versionBean.Data.VERSIONID) {
                          textView4.setText("" + versionBean.Data.VERSIONNUM);
                          textView5.setText(AppTool.getNullStr(versionBean.Data.UPDATECONTENT));
                          mLlUp.setVisibility(View.VISIBLE);
                      } else {

                          ToastUtils.showToast("版本监测成功");

                      }
                  } catch (PackageManager.NameNotFoundException e) {
                      e.printStackTrace();
                  }


                  break;

            }
        } else {
            ToastUtils.showToast(result);
        }
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
        if (resultid==2){
            ToastUtils.showToast("版本监测失败");
        }
    }

    @Override
    public void requestFailureJC(int resultid, String result) {

    }
    /**
     * @param file
     * @return
     * @Description 安装apk
     */
    protected void installApk(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // 7.0+以上版本
            Uri apkUri = FileProvider.getUriForFile(getActivity(), "com.wavenet.ding.qpps.fileprovider", file);  //包名.fileprovider

            LogUtils.e("路径", apkUri.toString() + "...........");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {

            LogUtils.e("路径", Uri.fromFile(file).toString() + "...........");

            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        startActivity(intent);
    }

}
