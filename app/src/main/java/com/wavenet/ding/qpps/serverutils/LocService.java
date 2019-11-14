package com.wavenet.ding.qpps.serverutils;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.trace.LBSTraceClient;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.activity.MainMapXJActivity;
import com.wavenet.ding.qpps.mvp.p.MainMapXJRequestPresenter;
import com.wavenet.ding.qpps.utils.AppAttribute;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.MapUtil;

import java.util.List;

/**
 * Created by zoubeiwen on 17/12/21.
 */

public class LocService extends Service implements AMapLocationListener {
    public static String LOCATION_ACTION = "LOCATION_ACTION";
    public static String TIME_ACTION = "TIME_ACTION";
    public AMapLocation aMapLocation;
    public MainMapXJRequestPresenter presenter;
    public Handler mHandler = new Handler();
    public Handler mHandlertime = new Handler();
    protected String packageName;
    LBSTraceClient mTraceClient;

    public Runnable r = new Runnable() {

        @Override
        public void run() {

            if (!AppTool.isNull(AppAttribute.F.getXJID(LocService.this)) && MainMapXJActivity.isStartde) {
                mHandler.postDelayed(this, 15 * 1000);
                setbroadcast(LOCATION_ACTION);
            }
        }
    };
    public Runnable rTime = new Runnable() {

        @Override
        public void run() {
            setbroadcast(TIME_ACTION);
            //每隔1s循环执行run方法
            mHandlertime.postDelayed(this, 1000);//延时1000毫秒

        }
    };
    PowerManager mPowerManager; // 电源控制管理器，比如防锁屏
    PowerManager.WakeLock mWakeLock; // 唤醒锁
    //定位
    private AMapLocationClient locationClient;
    private AMapLocationClientOption locationOption;

    /**
     * 检测定位服务是否已经开启
     *
     * @param context
     * @return
     */
    public static boolean isLocationSerivceRuning(Context context) {

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) {
            return false;
        }
        // 当前所有的进程信息
        List<ActivityManager.RunningServiceInfo> infos = am.getRunningServices(Integer.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo serviceInfo : infos) {
            // 判断是否当前应用 pid 是否 相同
            if (TextUtils.equals(serviceInfo.service.getClassName(), LocService.class.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 绑定服务时才会调用
     * 必须要实现的方法
     *
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 首次创建服务时，系统将调用此方法来执行一次性设置程序（在调用 onStartCommand() 或 onBind() 之前）。
     * 如果服务已在运行，则不会调用此方法。该方法只被调用一次
     */
    @Override
    public void onCreate() {
        System.out.println("onCreate invoke");
        super.onCreate();
        packageName = this.getPackageName();
        //设置屏幕保持常量
        screenWakeUp();
        // 初始化定位
        initLocation();

    }

    /**
     * 每次通过startService()方法启动Service时都会被回调。
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        handler.sendEmptyMessage(1);
        // 设置定位返回的坐标系
        configLocationOption();
        //如果API在26以上即版本为O则调用startForefround()方法启动服务
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            startServiceForeground();
        }
        // 设置前台进程
//        startServiceForeground();
        startHandler();
//        return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    /**
     * 服务销毁时的回调
     */
    @Override
    public void onDestroy() {
        System.out.println("onDestroy invoke");
        releaseWakeLock();
        // 停止定位服务
        stopLocationService();
        cancelHandler();
        stopServiceForeground();
        super.onDestroy();
    }

    /**
     * 屏幕保持常量
     */
    private void screenWakeUp() {
        if (null == mPowerManager) {
            mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
        }
        if (null == mWakeLock) {
//            mWakeLock = mPowerManager.newWakeLock(
//                    PowerManager.FULL_WAKE_LOCK, getClass().getName());

            mWakeLock = mPowerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "CPUKeepRunning");
        }
        if (!mWakeLock.isHeld()) {
            mWakeLock.acquire();
        }
    }

    /**
     * 关闭屏幕
     */
    public void releaseWakeLock() {
        if (mWakeLock != null && mWakeLock.isHeld()) {
            mWakeLock.release();
            mWakeLock = null;
        }
    }

    private void initLocation() {
        mTraceClient = new LBSTraceClient(this.getApplicationContext());
        if (locationClient == null) {
            locationClient = new AMapLocationClient(this);
            // 设置定位监听
            locationClient.setLocationListener(this);
        }
    }

    private void configLocationOption() {

        locationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置单次定位  true为是
        locationOption.setOnceLocation(false);
        //设置定位间隔,单位毫秒,默认为5000ms
        locationOption.setInterval(1000);
//        locationOption.setDeviceModeDistanceFilter(10);
        //设置是否返回地址信息（默认返回地址信息）
        locationOption.setNeedAddress(true);
//        获取当前速度(单位：米/秒) 默认值：0.0
        locationOption.setSensorEnable(true);
//        //设置定位参数
//        locationClient.setLocationOption(locationOption);
        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
        //不允许模拟定位
//        locationOption.setMockEnable(false);
//        locationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.Transport);
        if (null != locationClient) {
            locationClient.setLocationOption(locationOption);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
//            locationClient.stopLocation();
            // 开始定位服务
            startLocationService();
        }

    }

    /**
     * 启动定位服务
     */
    protected void startLocationService() {
        if (locationClient != null ) {
//        if (locationClient != null && !locationClient.isStarted()) {
            locationClient.startLocation();
        }
    }
    /**
     * 关闭定位服务
     */
    protected void stopLocationService() {
        if (locationClient != null && locationClient.isStarted()) {
            locationClient.stopLocation();
            locationClient.unRegisterLocationListener(this);
            locationClient.onDestroy();
        }
    }

    /**
     * 设置服务运行在前台，防止内存紧张时 被kill掉
     */

    @TargetApi(Build.VERSION_CODES.O)
    private void startServiceForeground() {
        //设定的通知渠道名称
        String channelName = "channel_name";
        //设置通知的重要程度
        int importance = NotificationManager.IMPORTANCE_LOW;
        //构建通知渠道
        NotificationChannel channel = new NotificationChannel("CHANNEL_ID", channelName, importance);
        channel.setDescription("description");
        //在创建的通知渠道上发送通知
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "CHANNEL_ID");
        builder.setSmallIcon(R.mipmap.app_ico_login) //设置通知图标
                .setContentTitle("青浦排水设施养护监管平台")//设置通知标题
                .setContentText("青浦排水巡检中...")//设置通知内容
                .setAutoCancel(true) //用户触摸时，自动关闭
                .setOngoing(true);//设置处于运行状态
        //向系统注册通知渠道，注册后不能改变重要性以及其他通知行为
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
        //将服务置于启动状态 NOTIFICATION_ID指的是创建的通知的ID
        startForeground(2019, builder.build());
//        // 设置 setOngoing true 通知栏 常驻不能被清除。
//        Notification noti = new Notification.Builder(this).setOngoing(true).setAutoCancel(false)
//                .setWhen(System.currentTimeMillis()).setContentTitle("巡检进行中").setContentText("巡检进行中")
//                .setTicker("巡检进行中").getNotification();
//
//        // 设置当前服务运行在前台
//        startForeground(2019, noti);

//            Intent updateIntent = new Intent(this, MainMapXJActivity.class);
//            updateIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//            // updateIntent.setAction(Intent.ACTION_MAIN);
//            // updateIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//            // 传入参数
//            // updateIntent.putExtras(AppUtils.JSONToBundle(patrolBundle));
//            // PendingIntent.FLAG_UPDATE_CURRENT
//            // 如果该PendingIntent已经存在，则用新传入的Intent更新当前的数除。
//            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, updateIntent,
//                    PendingIntent.FLAG_UPDATE_CURRENT);
//
//            // 设置 setOngoing true 通知�?常驻不能被清除�?
//            Notification noti = new Notification.Builder(this).setOngoing(true).setAutoCancel(false)
//                    .setWhen(System.currentTimeMillis()).setContentTitle("巡查任务").setContentText("巡查进行").setTicker("巡查进行")
//                    .setContentIntent(pendingIntent).getNotification();

        // 设置当前服务运行在前台
//            startForeground(2019, noti);

    }

    /**
     * onDerstroy 时 停止前台运行
     */
    private void stopServiceForeground() {
        stopForeground(true);
    }

    public void cancelHandler() {
        mHandler.removeCallbacks(r);
//    mHandlertime.removeCallbacks(rTime);
    }

    public void startHandler() {
//        mHandler.removeCallbacks(r);
        mHandler.postDelayed(r, 0);//延时0毫秒
//    mHandlertime.postDelayed(rTime, 0);
    }

    public void setbroadcast(String mAction) {
        // 发送广播 定位消息
        Bundle b = new Bundle();
        if (TIME_ACTION.equals(mAction)) {
        }
        Intent intent = new Intent();
        intent.addCategory(packageName);
        intent.setAction(mAction);
        intent.putExtras(b);
        sendBroadcast(intent);
    }

    public void setbroadcast2(String mAction, double dis) {
        // 发送广播 定位消息
        Bundle b = new Bundle();
        if (TIME_ACTION.equals(mAction)) {
        }
        Intent intent = new Intent();
        intent.putExtra("distance", dis);
        intent.addCategory(packageName);
        intent.setAction(mAction);
        intent.putExtras(b);
        sendBroadcast(intent);
    }

    LatLng ampLatsLatlng;
    long firstTime = 0;
    long timelocp = 0;
    boolean fristUpload = true;

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        /**
         * 1。第一次进来使用综合定位在地图上显示当前位置
         * 2。开始巡查后的定位，过滤只需要GPS来来源的定位信息
         * 3。每次定位回掉时和定位时间 和 上次时间对比，判断是否是最新的（只要最新的）
         * 4。客户端定位纠偏，弊端比较多，巡查人员端界面上还是保持定位信息展示轨迹。以和 唐嫣平 沟通，其他地方需要查看轨迹时，服务端使用webApi 把轨迹点纠偏后的展示给用户（客户端不需处理）
         * 5。上报轨迹，建议直接用按距离判断上报（距离上次位置大于20米）提交一次（原地实际没有动，gps在跳动这样没有实际做用，都是无效轨迹，既不能展示轨迹线，也不能累加里程。不管服务端人员怎么说）。
         * */

//        //这个定位，可能页面进来时就需要展示，当前定位位置，这里就直接存储下，外面方便使用
//        MainMapXJActivity.aMapLocation = aMapLocation;
//        ToastUtils.showToast("getLocationType"+aMapLocation.getLocationType() );
        //2。定位来源过滤（只要gps坐标）
//        if (aMapLocation.getLocationType() != AMapLocation.LOCATION_TYPE_GPS) {
//            return;
//        }

        if (aMapLocation.getErrorCode() != 0) {
            return;
        }
        if (!MapUtil.isLegalLL(aMapLocation)) {
            return;
        }
        if (aMapLocation.getSpeed() == 0) {
            return;
        }
        AppAttribute.G.Loca++;
        long locTime = aMapLocation.getTime();
        //1。时间判断过滤
            if (timelocp == 0 || timelocp < locTime) {

                LatLng ampLatlng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
    //            long secondTime = System.currentTimeMillis();//上次传送的时间点和这次的时间间隔

                double dis = 0;
                if (ampLatsLatlng != null) {
                    dis = AMapUtils.calculateLineDistance(ampLatlng, ampLatsLatlng);

                    //3。距离过滤，大于20米报一次，并且绘制轨迹，精度小于20
                    boolean b=false;
                    float mAccuracy=aMapLocation.getAccuracy();
                    if (!fristUpload){
    //                    if (MapUtil.isLegalSpeed(dis,locTime-timelocp)&&mAccuracy<20){
                        if (MapUtil.isLegalSpeed(dis,locTime-timelocp)){
                            b=true;
                        }
                    }
                    if (fristUpload || b) {
                        //记录定位信息，下次定位时 判断距离
                        ampLatsLatlng = ampLatlng;
                        //记录定位是假，下次定位时 判断速度
                        timelocp = locTime;
                        //这个定位，可能页面进来时就需要展示，当前定位位置，这里就直接存储下，外面方便使用(放在里面因为跟随原因需要跟轨迹位置一样)
            MainMapXJActivity.aMapLocation = aMapLocation;
            AppAttribute.G.LocaLegal++;
                        //直接发广播 到activity，那边直接上报，或者这里直接上报轨迹
    //                    setbroadcast2(LOCATION_ACTION, dis);
                        fristUpload = false;
                    }
    //        }
                }else {
                    ampLatsLatlng = ampLatlng;
                }
            }

    }
}
