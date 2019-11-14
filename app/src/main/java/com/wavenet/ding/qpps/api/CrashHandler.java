package com.wavenet.ding.qpps.api;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;

import com.dereck.library.utils.ToastUtils;
import com.google.gson.Gson;
import com.wavenet.ding.qpps.activity.SplashActivity;
import com.wavenet.ding.qpps.bean.BreakOffBean;
import com.wavenet.ding.qpps.serverutils.LocService;
import com.wavenet.ding.qpps.utils.ActivityManage;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.MapUtil;
import com.wavenet.ding.qpps.utils.SPUtil;
import com.wavenet.ding.qpps.xy.MyServiceYH;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 全局处理异常.
 */
public class CrashHandler implements UncaughtExceptionHandler {
    public static final String TAG = "CrashHandler";
    // CrashHandler实例
    private static CrashHandler INSTANCE;
    // 系统默认的UncaughtException处理类
    private UncaughtExceptionHandler mDefaultHandler;
    // 程序的Context对象
    private Context mContext;
    public static final String EXCEPTION_INFO = "EXCEPTION_INFO";
    public static final String PACKAGE_INFO = "PACKAGE_INFO";
    public static final String DEVICE_INFO = "DEVICE_INFO";
    public static final String SYSTEM_INFO = "SYSTEM_INFO";
    public static final String SECURE_INFO = "SECURE_INFO";
    public static final String MEM_INFO = "MEM_INFO";
    // 用来存储设备信息和异常信息
    private ConcurrentHashMap<String, Object> infos = new ConcurrentHashMap<String, Object>();
    String mExceptionInfo="";
    private ConcurrentHashMap<String, String> mPackageInfo = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, String> mDeviceInfo = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, String> mSystemInfo = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, String> mSecureInfo = new ConcurrentHashMap<>();

    // 用于格式化日期,作为日志文件名的一部分
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault());

    /**
     * 保证只有一个CrashHandler实例ODO
     */
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        if (INSTANCE == null) {
            synchronized (CrashHandler.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CrashHandler();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
//        this.crashUploader = crashUploader;
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            //退出应用
            killProcess();

    }
    }
    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {

        if (ex == null) {
            return false;
        }
        ex.printStackTrace();
        // 使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Handler   mHandler = new Handler(){
                    public void handleMessage(Message msg){
                        ToastUtils.showToast("App异常退出，请重启下试试");
                    }
                };
                Looper.loop();
//                Message message=handler.obtainMessage();
//                message.what=1;
//                handler.sendMessage(message);
            };
        }.start();
        // 收集设备参数信息
        collectDeviceInfo(ex);
        // 保存日志文件
        saveCrashInfo2File(ex);
        //上传崩溃信息
//        uploadCrashMessage(infos);
        return true;
    }
    private  Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ToastUtils.showToast("App异常退出，请重启下试试");
        }
    };
    /**
     * 退出应用
     */
    private  void killProcess() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
//                Log.e(TAG, String.valueOf(e));
        }

        Intent service = new Intent();
        service.setClass(mContext, MyServiceYH.class);
        mContext.stopService(service);
        Intent service1 = new Intent();
        service1.setClass(mContext, LocService.class);
        mContext.stopService(service1);
        //注意需要清空所有已经启动的activity，否则你的错误提示框可能会弹出很多次
        ActivityManage.getInstance(). finishAllActivity();
//          退出程序，  重新打开首页，防止崩溃闪退
        Intent intent = new Intent();
        intent.setClass(mContext,SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
// ============
//        Intent intent = new Intent();
////        intent.setClassName("com.wavenet.ding.qpps", "com.wavenet.ding.qpps.activity.SplashActivity");
//        intent.setClass(mContext,SplashActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        @SuppressLint("WrongConstant") PendingIntent restartIntent = PendingIntent.getActivity(mContext, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
//        AlarmManager mgr = (AlarmManager) QPSWApplication.getInstance().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent); // 2秒钟后重启应用
//        android.os.Process.killProcess(android.os.Process.myPid());
//        System.exit(1);
    }
    /**
     * 收集设备参数信息
     *
     * @param ex
     */
    public void collectDeviceInfo(Throwable ex) {
  //崩掉时候保存的业务信息
        saveBusinessInfo();
        //记录当前时间，当程序崩溃重新打开用到这个时间
        mExceptionInfo = collectExceptionInfo(ex);
        collectPackageInfo();
        collectBuildInfos();
        collectSystemInfos();
        collectSecureInfo();
        collectMemInfo();

        infos.put(EXCEPTION_INFO, mExceptionInfo);
        infos.put(PACKAGE_INFO, mPackageInfo);
        infos.put(DEVICE_INFO, mDeviceInfo);
        infos.put(SYSTEM_INFO, mSecureInfo);
        infos.put(SECURE_INFO, mSecureInfo);
        infos.put(MEM_INFO, MEM_INFO);
    }
    /**
     * 崩掉时候保存的业务信息
     *
     * @param
     */
public void saveBusinessInfo(){
    if (BreakOffBean.getInitSingle().isStartde &&  MapUtil.ui == 0){//是否正在巡检，保存巡检中最新信息
        SPUtil.getInstance(mContext).setBooleanValue(SPUtil.ISBREAKOFF,true);
        String bobeanstr=new Gson().toJson(BreakOffBean.getInitSingle());
        SPUtil.getInstance(mContext).setStringValue(SPUtil.BREAKOFFBEAN,bobeanstr);
    }
}
    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex) {

        StringBuffer sb=  getInfoStr(mPackageInfo);
        StringBuffer sb1=  getInfoStr(mDeviceInfo);
//        StringBuffer sb2=  getInfoStr(mSystemInfo);
//        StringBuffer sb3=  getInfoStr(mSecureInfo);
        sb.append("========");
        sb.append(sb1);
//        sb.append(sb2);
//        sb.append(sb3);
        sb.append(mExceptionInfo);

        try {

            long timestamp = System.currentTimeMillis();
            String time = formatter.format(new Date());
            String fileName = "crash-" + time + "-" + timestamp + SPUtil.getInstance(mContext).getStringValue(SPUtil.USERNO)+ ".text";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String crashpath = AppTool.getCrashLogFolder(mContext, fileName).getAbsolutePath();
                FileOutputStream fos = new FileOutputStream(crashpath);
                fos.write(sb.toString().getBytes());
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file..." + String.valueOf(e));
        }
        return null;
    }
    /**
     * 将HashMap遍历转换成StringBuffer
     */
    private static StringBuffer getInfoStr(ConcurrentHashMap<String, String> info) {
        StringBuffer mStringBuffer = new StringBuffer();
        for (Map.Entry<String, String> entry : info.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            mStringBuffer.append(key + "=" + value + "\r\n");
        }
        return mStringBuffer;
    }
    /**
     * 获取捕获异常的信息
     */
    private String collectExceptionInfo(Throwable ex) {
        Writer mWriter = new StringWriter();
        PrintWriter mPrintWriter = new PrintWriter(mWriter);
        ex.printStackTrace(mPrintWriter);
        ex.printStackTrace();
        Throwable mThrowable = ex.getCause();
        // 迭代栈队列把所有的异常信息写入writer中
        while (mThrowable != null) {
            mThrowable.printStackTrace(mPrintWriter);
            // 换行 每个个异常栈之间换行
            mPrintWriter.append("\r\n");
            mThrowable = mThrowable.getCause();
        }
        // 记得关闭
        mPrintWriter.close();
        return mWriter.toString();
    }
    /**
     * 获取应用包参数信息
     */
    private void collectPackageInfo() {
        try {
            // 获得包管理器
            PackageManager mPackageManager = mContext.getPackageManager();
            // 得到该应用的信息，即主Activity
            PackageInfo mPackageInfo = mPackageManager.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);

            if (mPackageInfo != null) {
                String versionName = mPackageInfo.versionName == null ? "null" : mPackageInfo.versionName;
                String versionCode = mPackageInfo.versionCode + "";
                this.mPackageInfo.put("VersionName", versionName);
                this.mPackageInfo.put("VersionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info:" + String.valueOf(e));
        }
    }
    /**
     * 从系统属性中提取设备硬件和版本信息
     */
    private void collectBuildInfos() {
        // 反射机制
        Field[] mFields = Build.class.getDeclaredFields();
        // 迭代Build的字段key-value 此处的信息主要是为了在服务器端手机各种版本手机报错的原因
        for (Field field : mFields) {
            try {
                field.setAccessible(true);
                mDeviceInfo.put(field.getName(), field.get("").toString());
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info:" + String.valueOf(e));
            }
        }
    }
    /**
     * 获取系统常规设定属性
     */
    private void collectSystemInfos() {
        Field[] fields = Settings.System.class.getFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Deprecated.class)
                    && field.getType() == String.class) {
                try {
                    String value = Settings.System.getString(mContext.getContentResolver(), (String) field.get(null));
                    if (value != null) {
                        mSystemInfo.put(field.getName(), value);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取系统安全设置信息
     */
    private void collectSecureInfo() {
        Field[] fields = Settings.Secure.class.getFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Deprecated.class)
                    && field.getType() == String.class
                    && field.getName().startsWith("WIFI_AP")) {
                try {
                    String value = Settings.Secure.getString(mContext.getContentResolver(), (String) field.get(null));
                    if (value != null) {
                        mSecureInfo.put(field.getName(), value);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 获取内存信息
     */
    private String collectMemInfo() {
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer();

        ArrayList<String> commandLine = new ArrayList<>();
        commandLine.add("cat");
        commandLine.add("/proc/meminfo");
        commandLine.add(Integer.toString(android.os.Process.myPid()));
        try {
            java.lang.Process process = Runtime.getRuntime()
                    .exec(commandLine.toArray(new String[commandLine.size()]));
            br = new BufferedReader(new InputStreamReader(process.getInputStream()), 1024);

            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
    private CrashUploader crashUploader;
    /**
     * 上传崩溃信息到服务器
     */
    private void uploadCrashMessage(ConcurrentHashMap<String, Object> info) {
        crashUploader.uploadCrashMessage(info);
    }

    /**
     * 崩溃信息上传接口回调
     */
    public interface CrashUploader {
        void uploadCrashMessage(ConcurrentHashMap<String, Object> info);
    }
}
