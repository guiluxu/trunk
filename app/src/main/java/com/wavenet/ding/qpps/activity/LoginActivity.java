package com.wavenet.ding.qpps.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.model.LatLng;
import com.bigkoo.pickerview.OptionsPickerView;
import com.dereck.library.base.BaseMvpActivity;
import com.dereck.library.utils.GsonUtils;
import com.dereck.library.utils.SPUtils;
import com.dereck.library.utils.ToastUtils;
import com.dereck.library.view.LoadingWaitView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.bean.CrashBean;
import com.wavenet.ding.qpps.bean.LoginBean;
import com.wavenet.ding.qpps.db.TrackBiz;
import com.wavenet.ding.qpps.interf.CallBackMap;
import com.wavenet.ding.qpps.interf.GoBackInteDef;
import com.wavenet.ding.qpps.mvp.p.LoginRequestPresenter;
import com.wavenet.ding.qpps.mvp.v.LoginActivityRequestView;
import com.wavenet.ding.qpps.utils.AppConfig;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.AppUtils;
import com.wavenet.ding.qpps.utils.MapUtil;
import com.wavenet.ding.qpps.utils.MapdownloadUtil;
import com.wavenet.ding.qpps.utils.SPUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import static com.esri.arcgisruntime.portal.PortalItemContentParameters.PortalItemContentType.JSON;

/**
 * Created by ding on 2018/7/20.
 */

public class LoginActivity extends BaseMvpActivity<LoginActivityRequestView, LoginRequestPresenter> implements LoginActivityRequestView ,CallBackMap,MapdownloadUtil.B {

    public static String mLoginTime = "";
    @BindView(R.id.et_login_user)
    EditText etLoginUser;
    @BindView(R.id.et_login_pwd)
    EditText etLoginPwd;
    @BindView(R.id.cb_login_save_pwd)
    CheckBox cbLoginSavePwd;
    @BindView(R.id.cb_login_auto_login)
    CheckBox cbLoginAutoLogin;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tvPosition)
    TextView tvPosition;
    @BindView(R.id.ll_login_input_pwd)
    LinearLayout llLoginInputPwd;
    @BindView(R.id.ll_login_check)
    LinearLayout llLoginCheck;
    @BindView(R.id.activity_login)
    RelativeLayout activityLogin;
    Intent intent;
    ArrayList<String> arrayList = new ArrayList<>();
    @BindView(R.id.iv_login_icon)
    ImageView ivLoginIcon;
    @BindView(R.id.tv_login_title)
    TextView tvLoginTitle;
    @BindView(R.id.ll_position)
    LinearLayout llPosition;
    @BindView(R.id.ll_login_input_name)
    LinearLayout llLoginInputName;
    @BindView(R.id.tv_crashlog)
    TextView tv_crashlog;
    @BindView(R.id.tv_changepsd)
    TextView tv_changepsd;
    LoadingWaitView mLoadingWaitView;
    //定位
    private AMapLocationClient locationClient;
    private AMapLocationClientOption locationOption;
    AlertDialog updataCrashlogDialog;
    MapdownloadUtil  mapdownloadUtil;
    TrackBiz mTrackBiz;
    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    /**
     * 定位初始化
     */
    LatLng llold;
    long firstTime=0;
    private void getLoction() {
        locationClient = new AMapLocationClient(this);
        locationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        locationOption.setSensorEnable(true);
//        locationOption.setDeviceModeDistanceFilter(300);
        //设置定位间隔,单位毫秒,默认为5000ms
        locationOption.setInterval(1000);
        // 设置定位监听
        locationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
//                LogUtils.e("zzd22222zoubeiwenaMapLocation.getErrorCode",aMapLocation.getErrorCode());
                if (aMapLocation.getErrorCode() == 0) {
                if (!MapUtil.isLegalLL(aMapLocation)){
                    return;
                }
                    EventBus.getDefault().post(aMapLocation);
//                    LatLng llnew = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
//                    long secondTime  = System.currentTimeMillis();//上次传送的时间点和这次的时间间隔
//                    if (llold!=null){
//                        double  dis = AMapUtils.calculateLineDistance(llnew, llold);
//                        if ( !MapUtil.isLegalSpeed(dis,secondTime - firstTime)) {//15 秒内不可能走300米
//                            return;
//                        }
//                    }
//                    firstTime = secondTime;//作为上次传送的时间点
//                    llold = llnew;
//                if (aMapLocation.getSpeed()<15){
//
//                }
            }
            }
        });
        //设置单次定位  true为是
        locationOption.setOnceLocation(false);
        //设置是否返回地址信息（默认返回地址信息）
        locationOption.setNeedAddress(true);
//        获取当前速度(单位：米/秒) 默认值：0.0
        locationOption.setSensorEnable(true);
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        //启动定位
        locationClient.startLocation();

    }


    @Override
    public void init() {
//        String s = SHA1Utils.sHA1(activity);
        SPUtils.put("url1", SPUtils.get("url1", AppConfig.BeasUrl+"2081"));
        SPUtils.put("url2", SPUtils.get("url2", AppConfig.BeasUrl+"2083"));
        SPUtils.put("url3", SPUtils.get("url3", AppConfig.BeasUrl+"2084"));
        SPUtils.put("url4", SPUtils.get("url4", AppConfig.BeasUrl+"2088"));
        SPUtils.put("url5", SPUtils.get("url5", AppConfig.BeasUrl+"2084"));
        MapdownloadUtil.strMapUrl="";
        mTrackBiz=new TrackBiz(this);
        mTrackBiz.deleTaskTrack_datecur(Integer.parseInt(AppTool.getCurrentDate().replace("-","")));
        mLoadingWaitView = findViewById(R.id.loadingWaitView);
//        mLoadingWaitView.loadView();
        ivLoginIcon.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showMyDialog();
                return true;
            }
        });
        getLoction();
        etLoginUser.setText(SPUtil.getInstance(this).getStringValue(SPUtil.USERNO));
        etLoginPwd.setText(SPUtil.getInstance(this).getStringValue(SPUtil.USERPWD));

        boolean rememberpswd = SPUtil.getInstance(this).getBooleanValue(SPUtil.CBSAVE, false);
        boolean rememberlogon = SPUtil.getInstance(this).getBooleanValue(SPUtil.AUTOLOGIN, false);
        cbLoginSavePwd.setChecked(rememberpswd);
        cbLoginSavePwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == false) {
                    cbLoginAutoLogin.setChecked(isChecked);
                }
            }
        });
        cbLoginAutoLogin.setChecked(rememberlogon);
        cbLoginAutoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbLoginAutoLogin.setChecked(isChecked);
                }
            }
        });
        if (AppUtils.isNullCashlog(this)){
            tv_crashlog.setVisibility(View.GONE);
        }else {
            tv_crashlog.setVisibility(View.VISIBLE);

        }
        mapdownloadUtil=    new MapdownloadUtil(this).setCallBackMap(this).setBListener(this);
        mapdownloadUtil.Rxhttp();


    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    boolean isLoginClick=false;
    @Override
    public void onClick(String mOnClick) {
        switch (mOnClick) {
            case "DOWNLOADSUCCESS":
//                mLoadingWaitView.success();
                if (isLoginClick){
                    presenter.clickRequest(etLoginUser.getText().toString(), etLoginPwd.getText().toString());
                }else {
                    autoLogin();
                }
                break;
                case "DOWNLOADFIL":
                break;
        }
    }


    @Override
    public void requestData() {
        arrayList.add("巡检功能");
        arrayList.add("养护功能");
    }

    @Override
    protected LoginRequestPresenter createPresenter() {
        return new LoginRequestPresenter() {
        };
    }

    @Override
    public void resultSuccess(Object result) {
        try {
//            OkHttpPost.postStr(callBack);
            String str = new Gson().toJson(result);
            Log.e(TAG,"登陆返回:"+str);
            LoginBean loginBean = (LoginBean) result;
            if (loginBean.getData()!=null) {
                loginBean.setSPUtil(this);
                SPUtil.getInstance(LoginActivity.this).setStringValue(SPUtil.USERNO, etLoginUser.getText().toString().trim());
                SPUtil.getInstance(LoginActivity.this).setStringValue(SPUtil.USERPWDOLD, etLoginPwd.getText().toString().trim());
                if (cbLoginSavePwd.isChecked()) {
                    SPUtil.getInstance(LoginActivity.this).setStringValue(SPUtil.USERPWD, etLoginPwd.getText().toString().trim());
                } else {
                    SPUtil.getInstance(LoginActivity.this).remove(SPUtil.USERPWD);
                }
                SPUtil.getInstance(LoginActivity.this).setBooleanValue(SPUtil.CBSAVE, cbLoginSavePwd.isChecked());
                SPUtil.getInstance(LoginActivity.this).setBooleanValue(SPUtil.AUTOLOGIN, cbLoginAutoLogin.isChecked());
                SPUtils.put("user", etLoginUser.getText().toString().trim());
                if (loginBean.getData().getRole().equals("巡查人员")) {
                    intent = new Intent(activity, MainMapXJActivity.class);
                    startActivity(intent);
                }
                if (loginBean.getData().getRole().equals("养护人员")) {
                    intent = new Intent(activity, MainMapYHActivity.class);
                    startActivity(intent);
                }
                if (loginBean.getData().getRole().contains("管理员")||loginBean.getData().getRole().contains("调度员")||loginBean.getData().getRole().contains("青浦管理单位")) {
                    mLoginTime = AppTool.getCurrentDate(AppTool.FORMAT_YMDHMS);
                    intent = new Intent(activity, AdminActivity.class);
                    startActivity(intent);
                }   if (loginBean.getData().getRole().contains("一般用户")) {
                    intent = new Intent(activity, AdminNormalActivity.class);
                    startActivity(intent);
                }
                //设置别名
                mHandlers.sendMessage(mHandlers.obtainMessage(MSG_SET_ALIAS, loginBean.getData().getName()));

            } else {
                ToastUtils.showToast("账号或者密码错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override

    public void resultFailure(String result) {
        ToastUtils.showToast(result);
    }

    @Override
    public void resultFailureCrash( String result) {
ToastUtils.showToast("日志上传失败，请重试或者放弃上传");
        showUpdataCrashlogDialog();
    }
    @Override
    public void Ba(boolean b) {
        if (isLoginClick){
            presenter.clickRequest(etLoginUser.getText().toString(), etLoginPwd.getText().toString());
        }else {
            autoLogin();
        }
    }
    @Override
    public void resultSuccessCrash( String result) {
        CrashBean  b = GsonUtils.getObject(result,CrashBean.class);
        if ("200".equals(b.Code)){
            updataCrashlogDialog.dismiss();
            tv_crashlog.setVisibility(View.GONE);
            AppTool.deleteFiles(AppTool.getCrashLogFolder(LoginActivity.this,""));// 报错日志上传完成后清空日志文件夹
            autoLogin();
        }
    }
    @Override
    public void show() {
        showDialog();
    }

    @Override
    public void hide() {
        cancelDialog();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @OnClick({R.id.cb_login_save_pwd, R.id.tvPosition, R.id.cb_login_auto_login, R.id.btn_login,R.id.tv_crashlog,R.id.tv_changepsd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_changepsd:
                Intent i=new Intent(this, ChangePasswordActivity.class);
                i.putExtra("isloginper",true);
                startActivity(i);
                break;
            case R.id.tv_crashlog:
                showUpdataCrashlogDialog();
                break;
            case R.id.cb_login_save_pwd:
                break;
            case R.id.tvPosition:
                //条件选择器
                OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        tvPosition.setText(arrayList.get(options1));
                    }
                }).build();
                pvOptions.setPicker(arrayList);
                pvOptions.show();

                break;
            case R.id.cb_login_auto_login:
                break;
            case R.id.btn_login:
isLoginClick=true;
if (AppTool.isNull(MapdownloadUtil.strMapUrl)){
//    mLoadingWaitView.loadView();
    mapdownloadUtil.Rxhttp();
}else {
    if (submit()) {
        presenter.clickRequest(etLoginUser.getText().toString(), etLoginPwd.getText().toString());

    }
//    intent = new Intent(activity, MapLocaActivity1.class);
//    startActivity(intent);
}
//                if (etLoginUser.getText().toString().equals("admin1")) {
//
//
//                    intent = new Intent(activity, MainMapXJActivity.class);
//
//                    startActivity(intent);
//
//                }
//                if (etLoginUser.getText().toString().equals("admin")) {
//
//                    intent = new Intent(activity, MainMapYHActivity.class);
//
//                    startActivity(intent);
//                }
//
//                finish();



//                intent = new Intent(LoginActivity.this, TakePhotoActivity.class);
//                startActivityForResult(intent, 1);



//                MapUtil.getInstance(this).showSoundDialog(this);
//                iss=!iss;
//if (iss){
//    try {
//        PlaybackFragment playbackFragment =
//                new PlaybackFragment().newInstance("http://222.66.154.70:2083/file/download/SJSB?id=5c10a527c6a9db0774fde17b",4000);
//
//        FragmentTransaction transaction = ((FragmentActivity) LoginActivity.this)
//                .getSupportFragmentManager()
//                .beginTransaction();
//
//        playbackFragment.show(transaction, "dialog_playback");
//
//    } catch (Exception e) {
////                        Log.e(LOG_TAG, "exception", e);
//    }
//}else {
//    Intent intent = new Intent(this, PlayVideoActivity.class);
//    intent.putExtra("url", "http://222.66.154.70:2083/file/download/SJSB?id=5c107d5dc6a9db0774fde11e");
//    startActivity(intent);
//}

//
//                }
                break;
        }
    }
boolean iss=true;
    private boolean submit() {
        // validate
        String user = etLoginUser.getText().toString().trim();
        if (TextUtils.isEmpty(user)) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return false;
        }

        String pwd = etLoginPwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    public void showUpdataCrashlogDialog()
    {
        updataCrashlogDialog= MapUtil.getInstance(this).isUpdataCrashlogDialog(this , new GoBackInteDef.A() {
            @Override
            public void goBack(int falg) {
                if (falg==1){
                    presenter.FileRequest(LoginActivity.this);
                }else if (falg==2){
                    autoLogin();
                }
            }
        });
    }
    public void autoLogin(){
        if (cbLoginAutoLogin.isChecked()) {
            if (submit()) {
                presenter.clickRequest(etLoginUser.getText().toString(), etLoginPwd.getText().toString());

            }
        }
    }
    RadioButton rb1,rb2;
     EditText tv_content1,tv_content2;
    public void showMyDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //     builder.setTitle("添加故障描述！");
        View view = View.inflate(activity, R.layout.dialog_config, null);
        builder.setView(view);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();
        TextView tvSure = view.findViewById(R.id.tv_ok);
        TextView tv_no = view.findViewById(R.id.tv_no);
         rb1 = view.findViewById(R.id.rb1);
          rb2 = view.findViewById(R.id.rb2);
        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb1.setChecked(true);
                rb2.setChecked(false);
            }
        });  rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb2.setChecked(true);
                rb1.setChecked(false);
            }
        });
         tv_content1 = view.findViewById(R.id.edContent1);
       tv_content2 = view.findViewById(R.id.edContent2);
        final EditText tv_content3 = view.findViewById(R.id.edContent3);
        final EditText tv_content4 = view.findViewById(R.id.edContent4);
        final EditText tv_content5 = view.findViewById(R.id.edContent5);

//        tv_content1.setText(SPUtils.get("url1", AppConfig.BeasUrl+"2081"));
//        tv_content2.setText(SPUtils.get("url2", AppConfig.BeasUrl+"2083"));
//        tv_content3.setText(SPUtils.get("url3", AppConfig.BeasUrl+"2084"));
//        tv_content4.setText(SPUtils.get("url4", AppConfig.BeasUrl+"2088"));
//        tv_content4.setText(SPUtils.get("url5", AppConfig.BeasUrl+"2084"));
        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SPUtils.put("url1", tv_content1.getText().toString().trim());
//                SPUtils.put("url2", tv_content2.getText().toString().trim());
//                SPUtils.put("url3", tv_content3.getText().toString().trim());
//                SPUtils.put("url4", tv_content3.getText().toString().trim());
//                SPUtils.put("url5", tv_content3.getText().toString().trim());
                if (rb1.isChecked()){
                    if (!AppTool.isNull(tv_content1.getText().toString())){

                        AppConfig.BeasUrl="http://"+tv_content1.getText().toString()+":";
                    }
                }
                if (rb2.isChecked()){
                    if (!AppTool.isNull(tv_content2.getText().toString())){
AppConfig.BeasUrl="http://"+tv_content2.getText().toString()+":";
                }
                }

                dialog.dismiss();
            }
        });
        dialog.setCancelable(true);
        dialog.show();
    }
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
          if (mLoadingWaitView.getVisibility()==View.VISIBLE&&mLoadingWaitView.getLodingText().contains("正在下载最新")){
              ToastUtils.showToast("正在下载地图，请勿退出");
              return true;//不执行父类点击事件
          }else {
              finish();
              return true;//不执行父类点击事件
          }
        }
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }

    /**
     * =====================极光推送别名设置=============================================================================================================================
     */
    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandlers = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(LoginActivity.this, (String) msg.obj, null, mAliasCallback);
                    break;
                default:
            }
        }
    };
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
//                    Log.i(TAG, "极光推送别名设置：" + alias);
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandlers.sendMessageDelayed(mHandlers.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
            }
        }
    };


}
