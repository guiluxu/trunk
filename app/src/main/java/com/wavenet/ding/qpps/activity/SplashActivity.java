package com.wavenet.ding.qpps.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dereck.library.base.BaseMvpActivity;
import com.dereck.library.download.DownloadObserver;
import com.dereck.library.utils.GsonUtils;
import com.dereck.library.utils.RxHttpUtils;
import com.dereck.library.utils.SPUtils;
import com.dereck.library.utils.ToastUtils;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.bean.CheckBean;
import com.wavenet.ding.qpps.bean.VersionBean;
import com.wavenet.ding.qpps.interf.InterfListen;
import com.wavenet.ding.qpps.mvp.p.SplashActivityRequestPresenter;
import com.wavenet.ding.qpps.mvp.v.SplashActivityRequestView;
import com.wavenet.ding.qpps.utils.AppConfig;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.LogUtils;
import com.wavenet.ding.qpps.utils.MapUtil;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

/**
 * Created by ding on 2018/7/20.
 */

public class SplashActivity extends BaseMvpActivity<SplashActivityRequestView, SplashActivityRequestPresenter> implements SplashActivityRequestView {

    @BindView(R.id.iv_splash)
    ImageView ivSplash;
    @BindView(R.id.tv_timer)
    TextView mTvtimer;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.llUp)
    LinearLayout llUp;
    @BindView(R.id.ivClose)
    ImageView ivClose;
    VersionBean versionBean;
    private Thread mThread;
    int timer=5;
    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    timer--;
                    if (mTvtimer==null){
                        return;
                    }
//                    mTvtimer.setText(timer+"s");
                    if (timer>0){
                        myHandler.sendEmptyMessageDelayed(2, 1000);
                    }
                    break;
                case 1:
//                    timer--;
//                    if (timer>0){
//                        myHandler.sendEmptyMessageDelayed(1, 1000);
//                        return;
//                    }


if (isHadcheck&&!isHadnex){
    if (mThread != null) {
        if (mThread.isAlive()) {
            mThread.interrupt();
            mThread = null;
        }
    }
    netPage();
}else {
    myHandler.sendEmptyMessageDelayed(1, 1000);
}

                    break;
            }
        }

    };
    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。

            // 这里的requestCode就是申请时设置的requestCode。
            // 和onActivityResult()的requestCode一样，用来区分多个不同的请求。
            if (requestCode == 200) {
                myHandler.sendEmptyMessageDelayed(1, 1000);
                mTvtimer.setVisibility(View.VISIBLE);
                mTvtimer.setText("跳过");
//                myHandler.sendEmptyMessageDelayed(2, 1000);
                presenter.getAPPCode(1);// 检测版本是否更新
//                Intent intent = new Intent(activity, LoginActivity.class);
//                startActivity(intent);
//                finish();
//                       Ioc.getIoc().init(MyApplication.app);
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            if (requestCode == 200) {
                // 用自定义的提示语
                AndPermission.defaultSettingDialog(activity, 103)
                        .setTitle("权限申请失败")
                        .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
                        .setPositiveButton("好，去设置")
                        .show();
            }
        }


    };
    private List<Disposable> disposables = new ArrayList<>();

    @Override
    public int getLayoutId() {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        return R.layout.activity_splash;
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        isHadcheck=false ;
//    }

    @Override
    public void init() {
//        RequestOptions options = new RequestOptions()
//                .fitCenter();

        Glide.with(this)
                .load(AppConfig.BeasUrl+"2088/apk/app_start20190610.png")
                .into(ivSplash);
//        textView5.setMovementMethod(ScrollingMovementMethod.getInstance());
//          , Manifest.permission.CALL_PHONE
//        mTvtimer.setText("5s");
        timer=5;
//        mTvtimer.setVisibility(View.GONE);
        AndPermission.with(this)
                .requestCode(200)
                .permission(
                        Manifest.permission.CAMERA
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE
                        , Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.ACCESS_COARSE_LOCATION
                        , Manifest.permission.ACCESS_FINE_LOCATION
                        , Manifest.permission.RECORD_AUDIO
                        , Manifest.permission.WAKE_LOCK
                        , Manifest.permission.READ_PHONE_STATE
//                                  , Manifest.permission.REQUEST_INSTALL_PACKAGES
                )
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        AndPermission.rationaleDialog(activity, rationale).show();
                    }
                })
                .callback(listener)
                .start();


    }

    @Override
    public void requestData() {

    }

    @Override
    protected SplashActivityRequestPresenter createPresenter() {
        return new SplashActivityRequestPresenter() {
        };
    }
String Message="正在检测版本更，请稍后....";
    @Override
    public void resultSuccess(int what, String result) {
        switch (what) {


            case 1:


                if (!result.contains("error")) {
                    versionBean = GsonUtils.getObject(result, VersionBean.class);
                    if (!"200".equals(versionBean.Code)){
                        Message="版本监测失败，不能正常使用该系统，是否要继续";
                        ToastUtils.showToast(versionBean.Msg);
                        return;
                    }
                    PackageInfo packageInfo = null;
                    try {
                        packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
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
    Message="版本监测失败，不能正常使用该系统，是否要继续";
    return;}

                        if (cur_num < versionBean.Data.VERSIONID) {
                             textView4.setText("" + versionBean.Data.VERSIONNUM);
                            textView5.setText(AppTool.getNullStr(versionBean.Data.UPDATECONTENT));
                            llUp.setVisibility(View.VISIBLE);
                        } else {
                            isHadcheck=true ;
//                            presenter.ge  tCheck(2);

                            mThread = new Thread() {
                                @Override
                                public void run() {
                                    myHandler.sendEmptyMessageDelayed(1, 3000);

                                }
                            };
                            mThread.start();

                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }


                } else {
                    ToastUtils.showToast(result);
                }


                break;


            case 2:

                int s_istest = GsonUtils.getObject(result, CheckBean.class).value.get(0).S_ISTEST;


                SPUtils.put("标记", s_istest + "");

//                mThread = new Thread() {
//                    @Override
//                    public void run() {
//                        myHandler.sendEmptyMessageDelayed(1, 3000);
//                    }
//                };
//                mThread.start();


                break;
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void resultFailure(int what, String result) {
//        MapUtil.getInstance(SplashActivity.this).showIsReplaceFileDialog();
if (what==1){
    Message="版本监测失败，不能正常使用该系统，是否要继续";
    presenter.getAPPCode(1);// 检测版本是否更新
}
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
            Uri apkUri = FileProvider.getUriForFile(activity, "com.wavenet.ding.qpps.fileprovider", file);  //包名.fileprovider

            LogUtils.e("路径", apkUri.toString() + "...........");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {

            LogUtils.e("路径", Uri.fromFile(file).toString() + "...........");

            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        startActivity(intent);
    }


    @OnClick({R.id.textView3, R.id.ivClose,R.id.tv_timer})
    public void onClick(View view) {
        switch (view.getId()) {
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
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        textView3.setEnabled(true);
                                    }
                                });

                            }

                            @Override
                            protected void onSuccess(long bytesRead, long contentLength, final float progress, final boolean done, final String filePath) {
                                runOnUiThread(new Runnable() {
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
            case R.id.ivClose:
                llUp.setVisibility(View.GONE);
                break;
            case R.id.tv_timer:
                if (isHadcheck&&!isHadnex){
                    netPage();

                }else {
                    MapUtil.getInstance(SplashActivity.this).showUPDialog(SplashActivity.this, Message, new InterfListen.A() {
                        @Override
                        public void aA() {
                            netPage();
                        }
                    });
                }
                    break;
        }

    }
    public void netPage(){
        isHadnex=true;
        Intent intent = new Intent(activity, LoginActivity.class);
//                    Intent intent = new Intent(activity, ActivityMyMiniWakeUp.class);
        startActivity(intent);
        finish();
    }
    boolean isHadcheck=false;
    boolean isHadnex=false;
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            moveTaskToBack(true);
            return true;//不执行父类点击事件
        }
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }
}
