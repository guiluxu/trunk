package com.wavenet.ding.qpps.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bigkoo.pickerview.OptionsPickerView;
import com.dereck.library.base.BaseMvpActivity;
import com.dereck.library.utils.NetUtils;
import com.dereck.library.utils.ToastUtils;
import com.dereck.library.view.LoadingWaitView;
import com.esri.arcgisruntime.data.QueryParameters;
import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.google.gson.Gson;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.bean.AdapterBean;
import com.wavenet.ding.qpps.bean.ClasBean;
import com.wavenet.ding.qpps.bean.Gps;
import com.wavenet.ding.qpps.bean.SourceBean;
import com.wavenet.ding.qpps.interf.AddFeatureQueryResultListen;
import com.wavenet.ding.qpps.interf.AddLayerListen;
import com.wavenet.ding.qpps.interf.CallBackMap;
import com.wavenet.ding.qpps.mvp.p.AdminSubmitActivityRequestPresenter;
import com.wavenet.ding.qpps.mvp.v.AdminSubmitActivityRequestView;
import com.wavenet.ding.qpps.utils.AppConfig;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.MapServerQueryUtils;
import com.wavenet.ding.qpps.utils.MapUtil;
import com.wavenet.ding.qpps.utils.PositionUtil;
import com.wavenet.ding.qpps.utils.SPUtil;
import com.wavenet.ding.qpps.view.AdminControllerCameraView;
import com.wavenet.ding.qpps.view.ViewGalleryPhoto;
import com.wavenet.ding.qpps.view.viewutils.CustomClickListener;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.model.TImage;
import org.devio.takephoto.model.TResult;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AdminSubmitActivity extends BaseMvpActivity<AdminSubmitActivityRequestView, AdminSubmitActivityRequestPresenter> implements
        AdminSubmitActivityRequestView, CallBackMap, View.OnClickListener
        , LoadingWaitView.OnRestLoadListener, TakePhoto.TakeResultListener, AddLayerListen, AddFeatureQueryResultListen,
        AdminControllerCameraView.ShowGalleryPhotoListen, MapServerQueryUtils.QueryCallBackInterface {
    public TakePhoto takePhoto;
    public static Gps g;
    public static AMapLocation aMapLocation;
    public static String S_MANGE_ID = "";//
    public static String S_CORRESPOND = "";
    public static String S_RECODE_ID = "";
    public static String S_JIEZHEN_NAME = "";
    public static String S_JIEZHEN_CODE = "";

    String UTCTime = "";
    ClasBean mClasBean;
    SourceBean mSourceBean;
    String clasbig;
    String classmall;
    int options;
    int selectposition;
    int options2;
    int selectposition2;

    TextView tv_clasbig, tv_clasmall, tv_source, tv_suer, tv_jiezhen, tv_time, tv_user, tv_cancel;
    EditText et_addr, et_contentdeal;
    LinearLayout ll_back;
    public static LoadingWaitView submit_loading;
    AdminControllerCameraView adminControllerCameraView;
    public ViewGalleryPhoto mViewGalleryPhoto;

    @Override
    public int getLayoutId() {
        return R.layout.activity_adminsubmit;
    }

    @Override
    public void init() {
        isRegistered(true, this);
        tv_clasbig = (TextView) findViewById(R.id.tv_clasbig);
        tv_clasmall = (TextView) findViewById(R.id.tv_clasmall);
        tv_source = (TextView) findViewById(R.id.tv_source);
        tv_clasbig.setOnClickListener(this);
        tv_clasmall.setOnClickListener(this);
        tv_source.setOnClickListener(this);
        et_addr = (EditText) findViewById(R.id.et_addr);
        et_contentdeal = (EditText) findViewById(R.id.et_contentdeal);
        tv_suer = (TextView) findViewById(R.id.tv_suer);
        tv_jiezhen = (TextView) findViewById(R.id.tv_jiezhen);
        tv_suer.setOnClickListener(onClickL(tv_suer, 3000));
        adminControllerCameraView = (AdminControllerCameraView) findViewById(R.id.c_camer);
        takePhoto = getTakePhoto();
        adminControllerCameraView.setShowGalleryPhotoListen(this);
        UTCTime = getCurrentTime(System.currentTimeMillis());
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_time.setText(UTCTime);
        tv_user = (TextView) findViewById(R.id.tv_user);
        tv_user.setText(SPUtil.getInstance(this).getStringValue(SPUtil.APP_MYNAME));
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(this);
        submit_loading = (LoadingWaitView) findViewById(R.id.submit_loading);
        mViewGalleryPhoto = (ViewGalleryPhoto) findViewById(R.id.v_galleryphoto);
        MapServerQueryUtils.getInstance(this).setQueryCallBackInterface(this);
        getLoction();
    }

    @Override
    public void requestData() {
//        MapServerQueryUtils.getInstance(this).queryAllJieZhenData();
    }

    @Override
    protected AdminSubmitActivityRequestPresenter createPresenter() {
        return new AdminSubmitActivityRequestPresenter() {
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_clasbig:
                presenter.clickRequestClasbig();
                break;
            case R.id.tv_clasmall:
                if (tv_clasbig.getText().toString().equals("")) {
                    ToastUtils.showToast("请选择大类");
                    return;
                }
                presenter.clickRequestClassmall(clasbig);
                break;
            case R.id.tv_source:
                presenter.clickRequestSource();
                break;
            case R.id.tv_suer:

                break;
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_cancel:
                finish();
                break;
        }
    }

    @Override
    public void requestSuccess(int resultid, String result) {
        switch (resultid) {
            case 2:
                break;
            case 3:
                ClasBean cb = new Gson().fromJson(result, ClasBean.class);
                cb.options = 1;
                cb.ui = 1;
                setPopClas(cb);
                break;
            case 4:
                ClasBean cs = new Gson().fromJson(result, ClasBean.class);
                cs.options = 2;
                cs.ui = 1;
                setPopClas(cs);
                break;
            case 5:
                SourceBean sourceBean = new Gson().fromJson(result, SourceBean.class);
                for (int i = 0; i < sourceBean.getData().size(); i++) {
                    if (sourceBean.getData().get(i).getS_VALUE().equals("巡查上报")) {
                        sourceBean.getData().remove(i);
                    }
                }
                sourceBean.options = 1;
                setPopClas2(sourceBean);
                break;
            case 12:
                try {
                    JSONObject jObject = new JSONObject(result);

                    String Code = jObject.getString("Code");
                    String Msg = jObject.getString("Msg");
                    if ("200".equals(Code)) {
                        adminControllerCameraView.FileRequest(MapUtil.FR);
                    } else {
                        hide();
                        ToastUtils.showToast(Msg);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 6:
                ToastUtils.showToast("上报成功");
                finish();
                break;
        }
    }

    public void setPopClas(ClasBean mClasBean) {
        if (mClasBean.ui == 1) {
            this.options = mClasBean.options;
            this.mClasBean = mClasBean;
            setPopSelect(mClasBean.value);
        }
    }

    public void setPopSelect(final ArrayList<ClasBean.ValueBean> popall) {
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(AdminSubmitActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                if (options == 1) {
                    selectposition = options1;
                    tv_clasbig.setText(mClasBean.value.get(options1).SVALUE);
                    clasbig = mClasBean.value.get(options1).SCORRESPOND;
                    tv_clasmall.setText("");
                } else if (options == 2) {
                    tv_clasmall.setText(mClasBean.value.get(options1).SVALUE);
                    classmall = mClasBean.value.get(options1).SCORRESPOND;
                }
            }
        }).build();
        pvOptions.setPicker(popall);
        if (pvOptions.isShowing()) {
            return;
        }
        pvOptions.show();

    }


    public void setPopClas2(SourceBean sourceBean) {
        options2 = sourceBean.options;
        setPopSelect2(sourceBean.getData());
    }

    public void setPopSelect2(final List<SourceBean.DataBean> popall) {
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(AdminSubmitActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                if (options2 == 1) {
                    selectposition2 = options1;
                    tv_source.setText(popall.get(options1).getS_VALUE());
                    S_CORRESPOND = popall.get(options1).getS_CORRESPOND();
                }
            }
        }).build();
        pvOptions.setPicker(popall);
        if (pvOptions.isShowing()) {
            return;
        }
        pvOptions.show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 2:
                if (resultCode == 2) {
                    adminControllerCameraView.onActivityResult(requestCode, resultCode, data);
                }
                break;
        }
    }

    @Override
    public void show() {
        submit_loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hide() {
        submit_loading.setVisibility(View.GONE);
    }

    @Override
    public void requestFailure(int resultid, String result) {
        ToastUtils.showToast(result);
    }

    @Override
    public void requestFailure(int resultid, String result, Map<String, Object> map, ArrayList<TImage> images, String videoPath, String audioPath) {

    }


    @Override
    public void takeSuccess(TResult result) {
        //被调用
        adminControllerCameraView.notifyData(result);
    }

    @Override
    public void takeFail(TResult result, String msg) {
    }

    @Override
    public void takeCancel() {
    }

    AMapLocationClient locationClient;

    private void getLoction() {
        locationClient = new AMapLocationClient(this);
        AMapLocationClientOption locationOption = new AMapLocationClientOption();
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
                if (aMapLocation.getErrorCode() == 0) {
                    if (!MapUtil.isLegalLL(aMapLocation)) {
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

    boolean locationFlag = false;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventBusCar(AMapLocation aMapLocation) {
        if (MapUtil.isLegalLL(aMapLocation)) {
            if (!locationFlag) {
                locationFlag = true;
                AdminSubmitActivity.aMapLocation = aMapLocation;
                g = PositionUtil.gcj_To_Gps84(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                et_addr.setText(aMapLocation.getStreet() + aMapLocation.getStreetNum());
//                S_JIEZHEN_ID
                //暂时写死，然后修改成实时的坐标点
                Point point84 = new Point(aMapLocation.getLongitude(), aMapLocation.getLatitude());
//                Point point84 = new Point(121.14508289885596, 31.230603009976416);
                Polygon polygon = GeometryEngine.buffer(point84, 0.00014);
                QueryParameters query = new QueryParameters();
                query.setReturnGeometry(true);//指定是否返回几何对象
                query.setGeometry(polygon);// 设置空间几何对象
                MapServerQueryUtils.getInstance(AdminSubmitActivity.this).queryPointFromJieZhen(query);
            }
        }
    }


    @Override
    public void onRestLoad(LoadingWaitView loadWaitView) {

    }

    @Override
    public void getQueryResultMap(ArrayList<AdapterBean> abList, int mTable) {

    }

    @Override
    public void getQueryResultMapSeach(ArrayList<AdapterBean> abList, int mTable) {

    }

    @Override
    public void getImageLayer(ArcGISMapImageLayer mImageLayer) {

    }

    @Override
    public void getImageLayerJc(ArcGISMapImageLayer mImageLayer) {

    }

    @Override
    public void onClick(String mOnClick) {

    }

    @Override
    public void ShowGalleryPhotoListen(boolean isShow, int position) {
        mViewGalleryPhoto.setData(position, adminControllerCameraView.imaDatas);
    }

    public boolean isSubmit() {
        if (!NetUtils.isNetworkConnected()) {
            ToastUtils.showToast("网络未连接，请检查网络！");
            return false;
        }
        if (AppTool.isNull(tv_clasbig.getText().toString()) || AppTool.isNull(tv_clasmall.getText().toString())) {
            ToastUtils.showToast("请选择问题类型");
            return false;
        }
        if (AppTool.isNull(tv_source.getText().toString())) {
            ToastUtils.showToast("请选择来源");
            return false;
        }
        if (AppTool.isNull(et_addr.getText().toString())) {
            ToastUtils.showToast("请输入地址");
            return false;
        }
        if (!adminControllerCameraView.getishavefile()) {
            ToastUtils.showToast("请选择上报的图片");
            return false;
        }
        if (AppTool.isNull(tv_jiezhen.getText().toString())) {
            ToastUtils.showToast("当前位置不在青浦区");
            return false;
        }
        String role = SPUtil.getInstance(this).getStringValue(SPUtil.APP_ROLE);

        if ("街镇管理员".equals(role) || "街镇调度员".equals(role)) {

            String des = SPUtil.getInstance(this).getStringValue(SPUtil.APP_DES);
            des = des.replace("'", "");
            if (!AppTool.isNull(des) || !des.equals(tv_jiezhen.getText().toString())) {
                ToastUtils.showToast("您上报的所在位置不属于自己管辖范围内");
                return false;
            }

        }

        return true;
    }

    private CustomClickListener onClickL(View v, long timeInterval) {
        CustomClickListener mCustomClickListener = new CustomClickListener(timeInterval, v) {
            @Override
            protected void onSingleClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_suer:
                        if (!isSubmit()) {
                            return;
                        }
                        //把高德经纬度转换为84
                        AdminSubmitActivity.g = PositionUtil.gcj_To_Gps84(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                        String userstr = SPUtil.getInstance(AdminSubmitActivity.this).getStringValue(SPUtil.USERNO);
                        AdminSubmitActivity.S_MANGE_ID = System.currentTimeMillis() + userstr;
                        UTCTime = getCurrentTime(System.currentTimeMillis());
                        AdminSubmitActivity.S_RECODE_ID = "XJ" + System.currentTimeMillis() + userstr;
                        // 事件编号  记录编号  问题类别  问题类型   上报人  上报时间
                        presenter.clickRequestUPTask(AdminSubmitActivity.this, et_contentdeal.getText().toString().trim(), AdminSubmitActivity.S_MANGE_ID, AdminSubmitActivity.S_RECODE_ID, clasbig, classmall, userstr, UTCTime, AdminSubmitActivity.g.getWgLon(), AdminSubmitActivity.g.getWgLat(), et_addr.getText().toString(), AdminSubmitActivity.S_CORRESPOND, AdminSubmitActivity.S_JIEZHEN_CODE);
                        break;
                }
            }

            @Override
            protected void onFastClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_suer:
                        if (!isSubmit()) {
                            return;
                        }
                        ToastUtils.showToast("正在提交,请稍后重试");
                        break;
                }
            }

        };
        return mCustomClickListener;
    }

    public String getCurrentTime(long value) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(new Date(value));
        return time;
    }

    @Override
    protected void onDestroy() {

        locationClient.stopLocation();
        isRegistered(false, this);
        super.onDestroy();
    }

    @Override
    public void queryFail(String content) {
        tv_jiezhen.setText("");
        if ("http://222.66.154.70:".equals(AppConfig.BeasUrl)) {//不在青浦区域测试用的街镇信息，
            AdminSubmitActivity.S_JIEZHEN_CODE = "W1007400001";
            tv_jiezhen.setText("白鹤镇（测试数据）");
        }
        Message message = new Message();
        message.what = 2;
        message.obj = content;
        handler.sendMessage(message);
    }

    @Override
    public void querySucess(String Name, String Code) {

        tv_jiezhen.setText(Name);
    }

    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 2) {//点击查询列表展示导航按钮
                String string = msg.obj.toString();
                ToastUtils.showToast(string);
            }
        }
    };

}
