package com.wavenet.ding.qpps.serverutils;

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
import com.dereck.library.interceptor.Transformer;
import com.dereck.library.observer.CommonObserver;
import com.dereck.library.utils.RxHttpUtils;
import com.wavenet.ding.qpps.api.ApiService;
import com.wavenet.ding.qpps.utils.AppConfig;
import com.wavenet.ding.qpps.utils.AppTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyService extends Service {

    private PowerManager pm;
    private PowerManager.WakeLock wakeLock;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    Map<String, Object> map;
    boolean tag;
int t=0;
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //创建PowerManager对象
        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        //保持cpu一直运行，不管屏幕是否黑屏
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "CPUKeepRunning");
        wakeLock.acquire();
        tag = true;

        handler.sendEmptyMessageDelayed(1, 1000);
        mHandlertime.postDelayed(rTime, 0);
        getLoction();
        return super.onStartCommand(intent, flags, startId);
    }

    Map<String, Object> reqParams;
    //定位
    private AMapLocationClient locationClient;
    private AMapLocationClientOption locationOption;
    public AMapLocation aMapLocation;
    ArrayList<AMapLocation> allAmapLocation = new ArrayList<>();

    /**
     * 定位初始化
     */
    private void getLoction() {
        allAmapLocation.clear();
        locationClient = new AMapLocationClient(this);
        locationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // 设置定位监听
        locationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {

//                Log.e("onLocationChanged", "onLocationChanged: " + aMapLocation.toStr());
                if (tag) {
                    allAmapLocation.add(aMapLocation);

                }




                MyService.this.aMapLocation = aMapLocation;

            }
        });
        //设置单次定位  true为是
        locationOption.setOnceLocation(false);
        //设置是否返回地址信息（默认返回地址信息）
        locationOption.setNeedAddress(true);
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        //启动定位
        locationClient.startLocation();

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (tag) {
                upXY();
            }
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        wakeLock.release();
        tag = false;

    }


    public static Handler mHandlertime = new Handler();

    public Runnable rTime = new Runnable() {

        @Override
        public void run() {
//            MainMapXJActivity.mMainUIView.setTime();
            //每隔1s循环执行run方法

            mHandlertime.postDelayed(this, 1000);//延时1000毫秒

        }
    };
    String urlstr = AppConfig.BeasUrl+"2081/odata/PSSSYH/default/T_TRAJECTORY";

    private void upXY() {
//        t++;
//        LogUtils.e("zbbbbbb----出", t+"");
//        MainMapXJActivity.mMainUIView. mTvtaskpai.setText(t+"");
//        handler.sendEmptyMessageDelayed(1,1 * 1000);
        t++;
//        MainMapXJActivity.mMainUIView. mTvtaskpai.setText(t+"");
        reqParams = new HashMap<>();
        reqParams.put("S_ID", System.currentTimeMillis()+"----"+t);
        reqParams.put("S_MAN_ID", "xjtest");
        reqParams.put("T_UPLOAD", AppTool.getCurrenttimeT());
        reqParams.put("N_X", 31.276297743055554);
        reqParams.put("N_Y",  121.53822184244791);
        reqParams.put("N_SPEED",   0.12);
        reqParams.put("S_TASK_TYPE", "W1007000001");
        reqParams.put("S_RECORD_ID", "12345");


        RxHttpUtils.createApi(ApiService.class)
                .userXJCoordinate(urlstr,reqParams)
                .compose(Transformer.<String>switchSchedulers())
                .subscribe(new CommonObserver<String>() {
                    @Override
                    protected void onError(String errorMsg) {
                        handler.sendEmptyMessageDelayed(1, 5000);

                    }

                    @Override
                    protected void onSuccess(String s) {
//                        EventBus.getDefault().post(allAmapLocation);
//                        MainMapXJActivity.mMainUIView. mTvtaskpai.setText(t+"");
                        handler.sendEmptyMessageDelayed(1, 5000);

                    }
                });


    }
}
