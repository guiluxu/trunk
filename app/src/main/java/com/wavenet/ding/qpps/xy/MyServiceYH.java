package com.wavenet.ding.qpps.xy;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.model.LatLng;
import com.dereck.library.interceptor.Transformer;
import com.dereck.library.observer.CommonObserver;
import com.dereck.library.utils.RxHttpUtils;
import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.google.gson.Gson;
import com.wavenet.ding.qpps.activity.MainMapYHActivity;
import com.wavenet.ding.qpps.api.ApiService;
import com.wavenet.ding.qpps.bean.XYbean;
import com.wavenet.ding.qpps.db.TrackBiz;
import com.wavenet.ding.qpps.db.bean.TrackBean;
import com.wavenet.ding.qpps.utils.AppAttribute;
import com.wavenet.ding.qpps.utils.AppConfig;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.LogUtils;
import com.wavenet.ding.qpps.utils.MapUtil;
import com.wavenet.ding.qpps.utils.SPUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyServiceYH extends Service {

    public static String MAPTRACKINIT = "maptrackinit";
    public static String MAPTRACKING = "maptracking";
    public static String MAPTRACKCLEAN = "maptrackclean";
    public static String NMILEAGE = "NMILEAGE";//公里数
    LatLng llold;
    long firstTime=0;
      double Dis  = 0;
    com.wavenet.ding.qpps.bean.Gps g;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    Map<String, Object> map;
    private PowerManager pm;
    private PowerManager.WakeLock wakeLock;

    boolean isTasking=false;
    protected String packageName;
     Polygon roadbuffer;
    TrackBiz mTrackBiz;
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }
    String urlstr = AppConfig.Guiijurl;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //创建PowerManager对象
        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        //保持cpu一直运行，不管屏幕是否黑屏
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "CPUKeepRunning");
        wakeLock.acquire();
        Dis  = intent.getDoubleExtra("Dis",0.00);
        isTasking=true;
        packageName = this.getPackageName();
        mTrackBiz = new TrackBiz(this);
        aMapLocation= MainMapYHActivity.aMapLocation;
        setbroadcast(MAPTRACKINIT);
        map = new HashMap<>();
        map.put("S_TASK_TYPE", "W1007000002");
        map.put("S_ID", System.currentTimeMillis() + SPUtil.getInstance(this).getStringValue(SPUtil.USERNO)+AppTool.getDeviceIMEI(this));
        map.put("S_RECORD_ID",SPUtil.getInstance(MyServiceYH.this).getStringValue(SPUtil.YHID));
        map.put("S_MAN_ID", intent.getStringExtra("S_MAN_ID"));
        map.put("T_UPLOAD", AppTool.getCurrentDate(AppTool.FORMAT_YMDHMS));
        map.put("N_Y", intent.getDoubleExtra("N_Y",0));
        map.put("N_X", intent.getDoubleExtra("N_X",0));
        roadbuffer = GeometryEngine.buffer(MainMapYHActivity.geometry, MapUtil.roadbuffer);
        handler.sendEmptyMessageDelayed(1, 1000);
        getLoction();
        return super.onStartCommand(intent, flags, startId);

    }

    //定位
    private AMapLocationClient locationClient;
    private AMapLocationClientOption locationOption;
    public static AMapLocation aMapLocation;

    /**
     * 定位初始化
     */
    LatLng llold1;
    long firstTime1=0;
    private void getLoction() {
        locationClient = new AMapLocationClient(this);
        locationOption = new AMapLocationClientOption();
        // 设置定位监听
        locationClient.setLocationListener(mAMapLocationListener);
        // 设置定位模式为高精度模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        locationOption.setSensorEnable(true);
        //设置单次定位  true为是
        locationOption.setOnceLocation(false);
//        locationOption.setDeviceModeDistanceFilter(10);
        //设置定位间隔,单位毫秒,默认为5000ms
        locationOption.setInterval(1000);
        //设置是否返回地址信息（默认返回地址信息）
        locationOption.setNeedAddress(true);
//        //设置定位参数
//        locationClient.setLocationOption(locationOption);
        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
        //不允许模拟定位
//        locationOption.setMockEnable(false);
//        locationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.Transport);
//        if (null != locationClient) {
            locationClient.setLocationOption(locationOption);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
//            locationClient.stopLocation();
            locationClient.startLocation();
//        }
        //启动定位
//        locationClient.startLocation();


    }
    long timelocp = 0;
    AMapLocationListener mAMapLocationListener=new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation.getErrorCode() != 0) {
                return;
            }
            if (!MapUtil.isLegalLL(aMapLocation)){
                return;
            }
                                                                                                             if (aMapLocation.getSpeed() == 0) {
            return;
            }
            AppAttribute.G.Loca++;
    long locTime = aMapLocation.getTime();
    //1。时间判断过滤
    if (timelocp == 0 || timelocp < locTime) {
                     timelocp = locTime;

        LatLng llnew = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
//    long secondTime  = System.currentTimeMillis();//上次传送的时间点和这次的时间间隔
//    if (llold1!=null){
//        double  dis = AMapUtils.calculateLineDistance(llnew, llold1);
//        if ( !MapUtil.isLegalSpeedYH(dis,secondTime - firstTime1)) {//养护有缓冲区控制所以不用速度条件限制
//            return;
//        }
//    }
//    firstTime1 = secondTime;//作为上次传送的时间点
//    llold1 = llnew;

//            if (aMapLocation.getSpeed()<6&&aMapLocation.getAccuracy()<15){
//            if (aMapLocation.getSpeed()<6){
//                MyServiceYH.this.aMapLocation = aMapLocation;
//                MainMapYHActivity.aMapLocation = aMapLocation;
//                setbroadcast(MAPTRACKING);
//            }
    g = com.wavenet.ding.qpps.utils.PositionUtil.gcj_To_Gps84(llnew.latitude, llnew.longitude);
        Point p=new Point(g.getWgLon(),g.getWgLat(), SpatialReferences.getWgs84());
            if (GeometryEngine.intersects(roadbuffer,p)||GeometryEngine.crosses(roadbuffer,p)||GeometryEngine.touches(roadbuffer,p)){
                MyServiceYH.this.aMapLocation = aMapLocation;
                AppAttribute.G.LocaLegal++;
                setbroadcast(MAPTRACKING);
            }
       MainMapYHActivity.aMapLocation = aMapLocation;
            }

        }
    };
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (isTasking){
                upXY();
            }

        }
    };


    @Override
    public void onDestroy() {
        LogUtils.e("服务", "取消");
        isTasking=false;
        Dis  = 0;
        setbroadcast(MAPTRACKCLEAN);
        wakeLock.release();
        locationClient.unRegisterLocationListener(mAMapLocationListener);
        locationClient.onDestroy();
        super.onDestroy();
    }
    double mm=0;
    private void upXY() {
        AppAttribute.G.TrackUPTimer++;
        if (!MapUtil.isLegalLL(aMapLocation)){
            handler.sendEmptyMessageDelayed(1, 10000);
            return;
        }
        LatLng llnew = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
//        long secondTime = System.currentTimeMillis();//上次传送的时间点和这次的时间间隔
        if (llold != null) {
            double d = AMapUtils.calculateLineDistance(llnew, llold);
//            if (!MapUtil.isLegalSpeedYH(d,secondTime - firstTime)) {//合法速度
//                handler.sendEmptyMessageDelayed(1, 10000);
//                return;
//            }
//            if (d<10) {//上一个坐标和下一个坐标小于10米不处理坐标
//                handler.sendEmptyMessageDelayed(1, 10000);
//                return;
//            }
//            if (!MapUtil.isLegalTime(secondTime - firstTime,d)) {//15 秒内不可能走300mi
//                handler.sendEmptyMessageDelayed(1, 10000);
//                return;
//            }
            Dis=Dis+d;
//            mm=mm+300;
            setbroadcast(NMILEAGE);
        }

        llold = llnew;
//        firstTime = secondTime;//作为上次传送的时间点
        com.wavenet.ding.qpps.bean.Gps g = com.wavenet.ding.qpps.utils.PositionUtil.gcj_To_Gps84(llnew.latitude, llnew.longitude);
        map.put("N_Y", g.getWgLat());
        map.put("N_X", g.getWgLon());
        map.put("N_SPEED", AppTool.getDoubleAccurate(aMapLocation.getSpeed()));
        map.put("S_ID", System.currentTimeMillis() + "---"+ SPUtil.getInstance(this).getStringValue(SPUtil.USERNO)+AppTool.getDeviceIMEI(this));
        map.put("T_UPLOAD", AppTool.getCurrentDate(AppTool.FORMAT_YMDHMS));
        map.put("N_MILEAGE", AppTool. getTwoDecimal(Dis) );
        AppAttribute.G.TrackUPPre++;
        RxHttpUtils
                .createApi(ApiService.class)
                .userXJCoordinate(urlstr,map)
                .compose(Transformer.<String>switchSchedulers())
                .subscribe(new CommonObserver<String>(false) {
                    @Override
                    protected void onError(String errorMsg) {
                        AppAttribute.G.TrackUPError++;
                            handler.sendEmptyMessageDelayed(1, 15000);

                    }

                    @Override
                    protected void onSuccess(String s) {
                        try {
                            handler.sendEmptyMessageDelayed(1, 15000);
                            JSONObject jsonObject=new JSONObject(s);
                            if (jsonObject.getInt("Code")!=200){
                                AppAttribute.G.TrackUPError++;
                                return;
                            }
                            AppAttribute.G.TrackUPSuccess++;
                            String js=jsonObject.getString("Data");
//                            XYbeanStr=s;
                            XYbean xYbean = new Gson().fromJson(js, XYbean.class);
                            mTrackBiz.inserttasktrack(new TrackBean(xYbean));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        ToastUtils.showToast(Dis+"-----"+mm);
//                        if (!AppTool.isNull(xYbean.NMILEAGE+"")){
//                            Dis=Dis+xYbean.NMILEAGE;
//                        }
//                        setbroadcast(MAPTRACKING);
                        //upXY();
                    }
                });


    }
    String XYbeanStr="";
    public void setbroadcast(String mAction){
        Intent intent = new Intent();
        intent.addCategory(packageName);
        intent.setAction(mAction);
        if (MAPTRACKING.equals(mAction)){
//            intent.putExtra("XYbeanStr",XYbeanStr);//上传成功后画轨迹

            intent.putExtra("lat84",g.getWgLat());//15秒实施轨迹
            intent.putExtra("lon84",g.getWgLon());
        }
        if (NMILEAGE.equals(mAction)){
            intent.putExtra("NMILEAGE",Dis);
        }
        sendBroadcast(intent);
    }
}
