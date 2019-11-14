package com.wavenet.ding.qpps.api;


import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.bll.greendao.manager.DBManager;
import com.dereck.library.config.OkHttpConfig;
import com.dereck.library.utils.RxHttpUtils;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.Polygon;
import com.iflytek.cloud.SpeechUtility;
import com.tencent.smtt.sdk.QbSdk;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.bean.AdapterBean;
import com.wavenet.ding.qpps.utils.ActivityManage;
import com.wavenet.ding.qpps.utils.AppConfig;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;


public class QPSWApplication extends Application {
    public static Point Locpoint;
    public static Polygon CenterpointPolygon;
    public static String mSoundPath;
    public static AdapterBean maBean;
    /**
     * 快速上手，默认配置
     */
//    private void initRxHttpUtils() {
//        RxHttpUtils
//                .getInstance()
//                .init(this)
//                .config()
//                //配置全局baseUrl
//                .setBaseUrl("https://api.douban.com/");
//    }

    private static QPSWApplication wmApplication = null;
    public Map<String, String> m = new HashMap<>();//字典表
    /**
     * activity对象列表,用于activity统一管理工具类
     */
    public ActivityManage mAManage;
    private Map<String, Object> headerMaps = new HashMap<>();

    public static QPSWApplication getInstance() {
        if (wmApplication == null) {
            wmApplication = new QPSWApplication();
        }
        return wmApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//              CrashHandler.getInstance().init(wmApplication);
        mAManage = ActivityManage.getInstance();
        CrashHandler. getInstance().init(getApplicationContext());
//       initRxHttpUtils();
        headerMaps.put("Accept", "application/json");
        headerMaps.put("Content-Type", "application/json");
        initCustomRxHttpUtils();
        SpeechUtility.createUtility(this, "appid=" + getString(R.string.app_id));//讯飞
        //极光推送初始化
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        JPushInterface.setLatestNotificationNumber(this, 1);

        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                Log.e("dddddd", "11111");
            }

            @Override
            public void onViewInitFinished(boolean b) {
                Log.e("dddddd", "22222");
            }
        });
        /*CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Bold.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );*/

        /** 初始化GreenDao */
        DBManager.initGreenDao(this);
    }

    /**
     * 全局请求的统一配置（以下配置根据自身情况选择性的配置即可）
     */
    private void initCustomRxHttpUtils() {

//        获取证书
//        InputStream cerInputStream = null;
//        InputStream bksInputStream = null;
//        try {
//            cerInputStream = getAssets().open("YourSSL.cer");
//            bksInputStream = getAssets().open("your.bks");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        OkHttpClient okHttpClient = new OkHttpConfig
                .Builder()
                //全局的请求头信息
                .setHeaders(headerMaps)
                //开启缓存策略(默认false)
                //1、在有网络的时候，先去读缓存，缓存时间到了，再去访问网络获取数据；
                //2、在没有网络的时候，去读缓存中的数据。
                .setCache(false)
                //全局持久话cookie,保存本地每次都会携带在header中（默认false）
                //  .setSaveCookie(true)
                //可以添加自己的拦截器(比如使用自己熟悉三方的缓存库等等)
                //.setAddInterceptor(null)
                //全局ssl证书认证
                //1、信任所有证书,不安全有风险（默认信任所有证书）
                //.setSslSocketFactory()
                //2、使用预埋证书，校验服务端证书（自签名证书）
                //.setSslSocketFactory(cerInputStream)
                //3、使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
                //.setSslSocketFactory(bksInputStream,"123456",cerInputStream)
                //全局超时配置
                .setReadTimeout(100)
                //全局超时配置
                .setWriteTimeout(100)
                //全局超时配置
                .setConnectTimeout(100)
                //全局是否打开请求log日志
                .setDebug(true)
                .build();
        RxHttpUtils
                .getInstance()
                .init(this)
                .config()
//        http://139.196.77.103:9763/
                //配置全局baseUrlhttp://172.18.1.221
//        http://222.66.154.70:2081/odata/PSSSYH/default
//                .setBaseUrl("http://222.66.154.70:2081/")
//                .setBaseUrl("http://222.66.154.70:2081/")
                .setBaseUrl(AppConfig.BeasUrl+"2088")
//
                //开启全局配置
                .setOkClient(okHttpClient);


//        TODO: 2018/5/31 如果以上OkHttpClient的配置满足不了你，传入自己的 OkHttpClient 自行设置
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//
//        builder
//                .addInterceptor(log_interceptor)
//                .readTimeout(10, TimeUnit.SECONDS)
//                .writeTimeout(10, TimeUnit.SECONDS)
//                .connectTimeout(10, TimeUnit.SECONDS);
//
//        RxHttpUtils
//                .getInstance()
//                .init(this)
//                .config()
//                .setBaseUrl(BuildConfig.BASE_URL)
//                .setOkClient(builder.build());
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
        MultiDex.install(this);
    }

}
