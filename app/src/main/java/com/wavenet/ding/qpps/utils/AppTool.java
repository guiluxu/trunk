package com.wavenet.ding.qpps.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.location.LocationManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaScannerConnection;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

import com.amap.api.maps2d.model.LatLng;
import com.dereck.library.utils.ScreenUtils;
import com.dereck.library.utils.ToastUtils;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wavenet.ding.qpps.activity.PlayVideoActivity;
import com.wavenet.ding.qpps.api.QPSWApplication;
import com.wavenet.ding.qpps.bean.ObjectIdsBean;
import com.wavenet.ding.qpps.fragment.PlaybackFragment;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class AppTool {
    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static String FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";
    public static String FORMAT_YMDHM = "yyyy-MM-dd HH:mm";
    public static String FORMAT_YMD = "yyyy-MM-dd";
    public static String FORMAT_HMS = "HH:mm:ss";
    static MediaScannerConnection msc = null;
    private static String appName;
    private static File CrashLogFolder = null;
    private static File PhotoFolder = null;
    private static File CacheFolder = null;
    private static File AudioFolder = null;
    private static File VideoFolder = null;
    private static File DownloadFolder = null;
    private static File MapFolder = null;
    // 取得本地时间：
    private static Calendar cal = Calendar.getInstance();
    // 取得时间偏移量：
    private static int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
    // 取得夏令时差：
    private static int dstOffset = cal.get(Calendar.DST_OFFSET);

    public static File getCrashLogFolder(Context context, String fileName) {
        File file = CrashLogFolder != null ? CrashLogFolder : new File(Environment.getExternalStorageDirectory() + "/"
                + getAppName(context) + "/CrashLog");
        if (!file.exists()) {
            file.mkdirs();
        }
        return new File(file.getAbsoluteFile() + (fileName != null ? "/" + fileName : ""));
    }

    public static File getMapFolder(Context context, String fileName) {
        File file = MapFolder != null ? MapFolder : new File(Environment.getExternalStorageDirectory() + "/" + getAppName(context) + "/arcgismap");
        if (!file.exists()) {
            file.mkdirs();
        }
        return new File(file.getAbsoluteFile() + (fileName != null ? "/" + fileName : ""));
    }

    public static File getPhotoFolder(Context context, String fileName) {
        File file = PhotoFolder != null ? PhotoFolder : new File(Environment.getExternalStorageDirectory() + "/"
                + getAppName(context) + "/CameraPhoto");
//		File	dcim = Environment
//				.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
//		File file = PhotoFolder != null ? PhotoFolder : new File(dcim + "/Camera/CameraPhoto");
        if (!file.exists()) {
            file.mkdirs();
        }
        return new File(file.getAbsoluteFile() + (fileName != null ? "/" + fileName : ""));
    }

    public static File getCacheFolder(Context context, String fileName) {
        File file = CacheFolder != null ? CacheFolder : new File(Environment.getExternalStorageDirectory() + "/"
                + getAppName(context) + "/Cache");
        if (!file.exists()) {
            file.mkdirs();
        }
        return new File(file.getAbsoluteFile() + (fileName != null ? "/" + fileName : ""));
    }

    public static File getAudioFolder(Context context, String fileName) {
        File file = AudioFolder != null ? AudioFolder : new File(Environment.getExternalStorageDirectory() + "/"
                + getAppName(context) + "/Audio");
        if (!file.exists()) {
            file.mkdirs();
        }
        return new File(file.getAbsoluteFile() + (fileName != null ? "/" + fileName : ""));
    }

    public static File getVideoFolder(Context context, String fileName) {
        File file = VideoFolder != null ? VideoFolder : new File(Environment.getExternalStorageDirectory() + "/"
                + getAppName(context) + "/Video");
        if (!file.exists()) {
            file.mkdirs();
        }
        return new File(file.getAbsoluteFile() + (fileName != null ? "/" + fileName : ""));
    }

    public static File getDownloadFolder(Context context, String fileName) {
        File file = DownloadFolder != null ? DownloadFolder : new File(Environment.getExternalStorageDirectory() + "/"
                + getAppName(context) + "/Download");
        if (!file.exists()) {
            file.mkdirs();
        }
        return new File(file.getAbsoluteFile() + (fileName != null ? "/" + fileName : ""));
    }

    public static File getTrackDBFolder(Context context, String fileName) {
        File file = CrashLogFolder != null ? CrashLogFolder : new File(Environment.getExternalStorageDirectory() + "/"
                + getAppName(context) + "/trackdb");
        file.mkdirs();
        return new File(file.getAbsoluteFile() + (fileName != null ? "/" + fileName : ""));
    }

    /**
     * 获取数据文件夹路径，fileName 未null 返回文件夹路径，否则返回 文件夹带上fileName的路径
     */
    public static File getDataBaseFolder(Context context, String fileName) {
        File databaseFile = null;
        if (fileName == null) {
            databaseFile = context.getDir("databases", Context.MODE_PRIVATE);
            if (!databaseFile.exists()) {
                databaseFile.mkdirs();
            }
        } else {
            databaseFile = context.getDatabasePath(fileName);
            // 判断文件 的 文件夹是否存在，不存在就创建
            File parentFile = databaseFile.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
        }
        return databaseFile;
    }

    public static String getAppName(Context context) {
        if (appName == null) {
            String[] appInfos = getVersionInfo(context);
            appName = appInfos[0];
        }

        return appName;
    }

    /**
     * 转换成通用标准尺寸
     *
     * @param value
     * @return
     */
    public static int Dip(float value, Context context) {
        final float fontScale = context.getResources().getDisplayMetrics().density;
        return (int) (value * fontScale + 0.5f);

    }

    /**
     * 调用相机拍照完成后，更新到相册
     *
     * @param context
     */
    public static void updateCameraAlbum(Context context, final Uri uri) {
        // 方法1；(微信使用的)
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(uri);
        context.sendBroadcast(intent);
        // 方法2:
        // msc = new MediaScannerConnection(context,
        // new MediaScannerConnectionClient() {
        // public void onMediaScannerConnected() {
        // msc.scanFile(uri.getPath(), "image/jpeg");
        // }
        //
        // public void onScanCompleted(String path, Uri uri) {
        // msc.disconnect();
        // }
        // });
    }

    /**
     * 获取程序版本信息 索引0:程序名称,1:版本显示名,2:版本号
     *
     * @return
     */
    public static String[] getVersionInfo(Context context) {
        String[] versions = new String[3];
        try {

            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            versions[0] = info.applicationInfo.loadLabel(pm).toString();
            versions[1] = info.versionName;
            versions[2] = String.valueOf(info.versionCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versions;
    }

    /**
     * 获取版本信息
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "V1.0.0";

        }
    }

//	/** 测量 View 的 大小 */
//	public static VSize measureView(View view) {
//		VSize size = new VSize();
//		int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//		int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//
//		view.measure(w, h);
//		size.height = view.getMeasuredHeight();
//		size.width = view.getMeasuredWidth();
//
//		return size;
//	}

    /**
     * 获取运行的服务Pid
     *
     * @param mContext
     * @param processNames
     * @return
     */
    public static int[] getRunServicePid(Context mContext, String... processNames) {
        int[] bool = new int[processNames.length];
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> serviceList = activityManager.getRunningAppProcesses();
        if (!(serviceList.size() > 0)) {
            return bool;
        }
        // 判断进程
        for (int i = 0; i < serviceList.size(); i++) {
            // System.out.println(serviceList.get(i).process);
            for (int j = 0; j < processNames.length; j++) {
                String processName = processNames[j];
                ActivityManager.RunningAppProcessInfo appProcess = serviceList.get(i);
                if (appProcess.processName.equals(processName) == true) {
                    bool[j] = appProcess.pid;
                    break;
                }
            }
        }
        return bool;
    }

    /**
     * 判断当前程序是否在后台
     */
    public static boolean isAppBackground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }

        return false;

    }

    /**
     * 拨号
     *
     * @param phone
     */
    public static void setPhone(Context mContext, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    /**
     * 判断是否是0-9数字
     *
     * @param str
     * @return pattern.matcher(str).matches()
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    public static String emptyString(Object obj) {
        if (obj != null) {
            return obj.toString();
        }
        return "";
    }

    /**
     * bitmap 转 base64字符串
     *
     * @param bitmap
     * @param mQuality
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap, int mQuality) {
        String js_out = null;
        ByteArrayOutputStream jpeg_data = new ByteArrayOutputStream();
        try {
            if (bitmap.compress(CompressFormat.JPEG, mQuality, jpeg_data)) {
                byte[] code = jpeg_data.toByteArray();
                byte[] output = Base64.encode(code, Base64.NO_WRAP);
                js_out = new String(output);

                output = null;
                code = null;
            }
        } catch (Exception e) {
            js_out = "Image转换Base64错误:" + e.getLocalizedMessage();
        }
        return js_out;
    }

    /**
     * JSONObject 转换成 Bundle
     *
     * @param object
     * @return
     */
    public static Bundle JSONToBundle(Map<String, Object> object) {
        Bundle b = new Bundle();
        Set<String> keySet = object.keySet();
        for (String key : keySet) {
            if (key == null)
                continue;

            Object obj = object.get(key);
            if (obj != null) {
                if (obj instanceof String) {
                    b.putString(key, (String) obj);

                } else if (obj instanceof Integer) {
                    b.putInt(key, (Integer) obj);

                } else if (obj instanceof Double) {
                    b.putDouble(key, (Double) obj);

                } else if (obj instanceof Float) {
                    b.putFloat(key, (Float) obj);

                } else if (obj instanceof Long) {
                    b.putLong(key, (Long) obj);

                } else if (obj instanceof Serializable) {
                    b.putSerializable(key, (Serializable) obj);

                } else if (obj instanceof Parcelable) {
                    b.putParcelable(key, (Parcelable) obj);
                }
            }
        }
        return b;
    }

    public static String getHMSTime(long time) {
        String h = (int) (time / 1000 / 3600) + "";
        String m = ((int) (time / 1000 % 3600)) / 60 + "";
        String s = ((int) (time / 1000 % 3600)) % 60 + "";

        if (h.equals("0")) {
            h = "00";
        }
        if (m.equals("0")) {
            m = "00";
        }
        if (s.length() == 1) {
            s = "0" + s;
        }
        return h + ":" + m + ":" + s;

    }

    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    /**
     * 获取当前手机串号
     */
    public static String getDeviceIMEI(Context context) {
        String imeiId = null;
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            @SuppressLint("MissingPermission") String deviceId = tm.getDeviceId();

            if (deviceId.length() > 15) {
                imeiId = deviceId.substring(0, 15);
            } else {
                imeiId = deviceId.substring(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imeiId;
        // return "864505022171899";
        // return "A0000044F533DC";//A0000048336F3B
        // return "868033013715539";
        // return "99000457112442";
        // return "99000316941942";
        // return "99000522779303";
    }

    public static String getMac(Context context) {
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String mac = wm.getConnectionInfo().getMacAddress();
        return mac == null ? "" : mac;
    }

    public static String toURLEncoded(String paramString) {
        if (paramString == null || paramString.equals("")) {
            return "";
        }

        try {
            String str = new String(paramString.getBytes(), "UTF-8");
            str = URLEncoder.encode(str, "UTF-8");
            return str;
        } catch (Exception localException) {
        }

        return "";
    }

    public static long getUTCTimeStr() {

        System.out.println("local millis = " + cal.getTimeInMillis()); // 等效System.currentTimeMillis() , 统一值，不分时区

        // 从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));

        long mills = cal.getTimeInMillis();
        System.out.println("UTC = " + mills);

        return mills;
    }

    public static String getCurUTCDateStr(String format) {
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        return date2Str(cal, format);

    }

    public static String date2Str(Calendar c, String format) {
        if (c == null) {
            return null;
        }
        return date2Str(c.getTime(), format);
    }

    public static String date2Str(Date d, String format) {// yyyy-MM-dd HH:mm:ss
        if (d == null) {
            return null;
        }
        if (format == null || format.length() == 0) {
            format = FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String s = sdf.format(d);
        return s;
    }

    public static void ShowmessageDialog(Context _context, String message, DialogInterface.OnClickListener sureclicklisnter) {
        new AlertDialog.Builder(_context, AlertDialog.THEME_HOLO_LIGHT).setMessage(message).setPositiveButton("确定", sureclicklisnter).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();
    }

    /**
     * 获取屏幕内容高度
     *
     * @param activity
     * @return
     */
    public static int getScreenHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int result = 0;
        int resourceId = activity.getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        int screenHeight = dm.heightPixels - result;
        return screenHeight;
    }

    /**
     * 获取屏幕中心坐标，84坐标系
     *
     * @param activity
     * @return
     */
    public static Point getScreenCenterPoint_84(Activity activity, MapView mMapView) {

        int height = ScreenUtils.getScreenHeight(activity) - ScreenUtils.getStatusHeight(activity) / 2;
        int width = ScreenUtils.getScreenWidth(activity);
        Point point84 = mMapView.screenToLocation(new android.graphics.Point(width / 2, height / 2));
        return point84;
    }

    /**
     * dp转px
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    public static int compareDate(Date d1, Date d2) {
        if (d1.getTime() > d2.getTime()) {
            System.out.println("dt1 在dt2前");
            return 1;
        } else if (d1.getTime() < d2.getTime()) {
            System.out.println("dt1在dt2后");
            return -1;
        } else {//相等
            return 0;
        }
    }

    public static Date stringToDate(String strTime)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    public static String getCurrentDate(String format) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(format); //设置获取系统时间格式
        String date = sDateFormat.format(new Date()); //获取当前系统
        return date;
    }

    public static String getSpecifiedDayBefore(String specifiedDay) {//可以用new Date().toLocalString()传递参数
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
                .getTime());
        return dayBefore;
    }

    public static String getSpecifiedDayAfter(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);

        String dayAfter = new SimpleDateFormat("yyyy-MM-dd")
                .format(c.getTime());
        return dayAfter;
    }

    public static ArrayList<String> getTime3(String timeseleted) {
        ArrayList<String> timelist = new ArrayList<String>();
        String times = "";
        String timee = "";
        if (AppTool.getCurrentDate("yyyy-MM-dd").equals(timeseleted)) {
            if (AppTool.getTime1(AppTool.getCurrentDate("yyyy-MM-dd HH:mm:ss"))) {
//            if (AppTool.getTime1("2017-09-30 02:02:02")){
                times = getSpecifiedDayBefore(getCurrentDate("yyyy-MM-dd")) + "  08:00:00";
                timee = getCurrentDate("yyyy-MM-dd HH:mm:ss");
//                timee = "2017-09-30 02:02:02";
            } else {
                times = timeseleted + " 08:00:00";
                timee = (getSpecifiedDayAfter(timeseleted)) + " 07:59:59";
            }
        } else {
            times = timeseleted + " 08:00:00";
            timee = (getSpecifiedDayAfter(timeseleted)) + " 07:59:59";
        }
        timelist.add(times);
        timelist.add(timee);
        return timelist;
    }

    public static boolean getTime1(String date) {
        boolean b = true;
        SimpleDateFormat CurrentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat CurrentTime1 = new SimpleDateFormat("yyyy-MM-dd");
        String current_time = CurrentTime1.format(new Date());
        Date beginTime = null;
        try {
            beginTime = CurrentTime.parse(date);
//            beginTime = CurrentTime.parse("2017-09-09 02:00:02");
            Date endTime = CurrentTime.parse(current_time + " 08:00:00");
            long time1 = beginTime.getTime();
            long time2 = endTime.getTime();
            b = time1 < time2;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return b;
    }

    public static String getFormatDate(String time, String format) {
        Date data = strToDate(time, format);
        SimpleDateFormat sDateFormat = new SimpleDateFormat(format); //设置获取系统时间格式
        String dateString = sDateFormat.format(data);
        return dateString;
    }

    public static Date strToDate(String strDate, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date strtodate = null;
        try {
            strtodate = formatter.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return strtodate;
    }

    public static boolean setTimeSelect(Context mContext, String d1, String d2) {
        boolean isyes = true;
        int result = 0;
        try {
            result = compareDate(stringToDate(d1), stringToDate(d2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (result == 1) {
            ToastUtils.showToast("开始日期不能大于结束日期");
            isyes = false;
        }
        return isyes;
    }

    public static String getBeforeData(int m, int d) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -d);    //得到前一天
//		calendar.add(Calendar.MONTH, -m);    //得到前3个月
        String Beforedata = new SimpleDateFormat("yyyy-MM-dd").format(calendar
                .getTime());
        return Beforedata;
    }

    public static String getBeforeData(int m, int d, String format) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -d);    //得到前一天
        calendar.add(Calendar.MONTH, -m);    //得到前3个月
        String Beforedata = new SimpleDateFormat(format).format(calendar
                .getTime());
        return Beforedata;
    }

    public static String getSetText(LinkedList<String> sets) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < sets.size(); i++) {
            sb.append(sets.get(i));
            if (i < (sets.size() - 1)) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public static String getCurrentDate() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd"); //设置获取系统时间格式
        String date = sDateFormat.format(new Date()); //获取当前系统
        return date;
    }

    public static File createIfNoExistsf(String path) {
        File file = new File(path);
        boolean mk = false;
        if (!file.exists()) {
            mk = file.mkdirs();
        }
        return file;
    }

    public static void deleteFiles(File file) {
        //递归出口
        //判断目前文件，如果是文件 或 是一个空的文件夹，则删除
//		if(file.isFile() || file.list().length ==  0)
        if (!file.isDirectory()) {
            file.delete();
        } else {
            File[] files = file.listFiles();
            for (File f : files) {
                //递归入口
                deleteFiles(f);
                f.delete();
            }

        }
    }

    public static List<String> getFilesAllName(File file) {

        File[] files = file.listFiles();
        if (files == null) {
//            Log.e("error","空目录");
            return null;
        }
        List<String> s = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            s.add(files[i].getName());
        }
        return s;

    }

    public static List<String> getFilesAllPath(File file) {
        File[] files = file.listFiles();
        if (files == null) {
//            Log.e("error","空目录");
            return null;
        }
        List<String> s = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            s.add(files[i].getAbsolutePath());
        }
        return s;
    }

    public static List<String> getFilesAllPath(String path) {
        File file = new File(path);
        return getFilesAllPath(file);
    }

    public static List<String> getFilesAllName(String path) {
        File file = new File(path);
        return getFilesAllPath(file);
    }

    public static void sendFile(Context mContext, String mOutPath) {
        File f = new File(mOutPath);
        Uri uri = Uri.fromFile(f);
        Intent it = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
        mContext.sendBroadcast(it);
    }

    public static Bitmap combineBitmap(Bitmap background, Bitmap foreground) {

        if (background == null) {

            return null;

        }

        int bgWidth = background.getWidth();

        int bgHeight = background.getHeight();

        int fgWidth = foreground.getWidth();

        int fgHeight = foreground.getHeight();

        Bitmap newmap = Bitmap.createBitmap(bgWidth, bgHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(newmap);

        canvas.drawBitmap(background, 0, 0, null);

        canvas.drawBitmap(foreground, (bgWidth - fgWidth) / 2,

                (bgHeight - fgHeight) / 2, null);

        canvas.save(Canvas.ALL_SAVE_FLAG);

        canvas.restore();

        return newmap;

    }


    public static Date stirngFormatDate(String format, String dateStr) {

        if (!TextUtils.isEmpty(dateStr)
                && !dateStr.trim().toLowerCase().equals("null")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            try {
                return dateFormat.parse(dateStr);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }


    public static String StringFormTime(int time) {
        String date1 = "00:";
        String date2 = "00:";
        String date3 = "00";
        int hour = time / 3600;
        int miniate = (time % 3600) / 60;
        int second = (time % 3600) % 60;
        if (hour > 9) {
            date1 = hour + ":";
        } else {
            date1 = "0" + hour + ":";
        }
        if (miniate > 9) {
            date2 = miniate + ":";
        } else {
            date2 = "0" + miniate + ":";
        }
        if (second > 9) {
            date3 = second + "";
        } else {
            date3 = "0" + second + "";
        }
        return date1 + date2 + date3;
    }

    //	public static String getTrackid(){
//		if (TextUtils.isEmpty(OISApplication.tarckid)){
//			OISApplication.tarckid=OISApplication.preferenceUtil.getStringValue(SharePreferenceUtil.TRACKID);
//		}
//		return OISApplication.tarckid;
//	}
// 计算两个触摸点之间的距离
    public static double distance(MotionEvent event) {
        double dis = 0;
        try {
            float x = event.getX(0) - event.getX(1);
            float y = event.getY(0) - event.getY(1);
            dis = Math.sqrt(x * x + y * y);
        } catch (Exception e) {

        }
        return dis;
    }


    public static boolean checkPackage(String packageName, Context context) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        return packageInfo != null;
    }


    //获取网络视频截图
    public static Bitmap createVideoThumbnail(String url, int width, int height) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(url, new HashMap<String, String>());
            } else {
                retriever.setDataSource(url);

            }
//            bitmap = retriever.getFrameAtTime();
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_NEXT_SYNC);
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }

    public static int isRegistered(boolean isRegistered, Object o, int isUn) {
        if (isRegistered) {
            if (!EventBus.getDefault().isRegistered(o)) {
                EventBus.getDefault().register(o);
                isUn = 1;
            }
        } else {
            if (isUn == 1) {
                EventBus.getDefault().unregister(o);
            }
        }
        return isUn;
    }

    public static ArrayList<ArrayList<String>> getReportclassPopData() {
        String[] big = {"排水管渠", "检查井（外部）", "检查井（内部）", "雨水口（外部)", "雨水口（内部)", "排水户"};
        String[] small1 = {"塌陷", "占压", "违章排放", "私自接管", "井盖缺失", "建设工地"};
        String[] small2 = {"污水冒溢", "井盖破损（变形、埋没）", "井盖标识错误", "周边施工", "井框异常"};
        String[] small3 = {"链条、锁具缺损", "爬梯缺损", "井壁异常", "管口、流槽破损", "井底积泥", "防坠设施缺损", "防坠设施存在垃圾、杂物", "井内水位、流向异常", "雨污混接", "违章排放", "私自接管"};
        String[] small4 = {"雨水箅缺损", "雨水口框缺损", "盖框间高差和间隙超限", "雨水箅孔眼堵塞损", "雨水口框突出、凹陷或跳动", "存在异味"};
        String[] small5 = {"雨水箅铰、链条缺损", "存在裂缝、渗漏、抹面剥落", "存在积泥或杂物", "存在积水", "雨污混接", "私接连管", "井体倾斜", "连管异常", "网篮破损", "防臭装置是否有效"};
        String[] small6 = {"违章排放", "未注册排水户", "信息错误", "建筑工地"};
        String[][] clasarr = {small1, small2, small3, small4, small5, small6};
        int size = clasarr.length;
        ArrayList<ArrayList<String>> classmalllist = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            ArrayList<String> classmall = new ArrayList<>();
            for (int j = 0; j < clasarr[i].length; j++) {
                classmall.add(clasarr[i][j]);
            }
            classmalllist.add(classmall);
        }
        return classmalllist;
    }

    public static ArrayList<String> getReportclasbPopData() {
        String[] big = {"排水管渠", "检查井（外部）", "检查井（内部）", "雨水口（外部)", "雨水口（内部)", "排水户"};
        int size = big.length;
        ArrayList<String> clasbiglist = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            clasbiglist.add(big[i]);
        }
        return clasbiglist;
    }

    public static boolean isNull(String str) {
        if (str == null || TextUtils.isEmpty(str))
            return true;
        String s = str.toLowerCase().trim();
        if (s.length() == 0 || TextUtils.isEmpty(s) || "".equals(s) || "null".equals(s))
            return true;
        return false;

    }

    public static String setTvTime(String str) {
        if (isNull(str)) {
            return "";
        }
        return str.replace("T", " ").replace("+08:00", "");
    }

    public static double strtodouble(String str) {
        if (str == null)
            return 0;
        if (TextUtils.isEmpty(str))
            return 0;
        return Double.parseDouble(str);

    }

    public static String getNullStr(String str) {
        if (isNull(str))
            return "";
        return str;
    }

    public static String getCurrenttimeT() {
        String time = new GetUTCTime().getCurUTCDateStr(GetUTCTime.FORMAT_YMDHMS);
        return time.replace(" ", "T");
//        return getCurrentDate(FORMAT_YMD) + "T" + getCurrentDate(FORMAT_HMS);
    }

    public static Map<String, String> getDictionaries(Context mContext) {
        if (QPSWApplication.getInstance().m == null) {
            String Dictstr = SPUtil.getInstance(mContext).getStringValue(SPUtil.Dict);
            if (Dictstr == null || "".equals(Dictstr)) {
                return null;
            }
            QPSWApplication.getInstance().m = new Gson().fromJson(Dictstr, new TypeToken<HashMap<String, String>>() {
            }.getType());
        }
        return QPSWApplication.getInstance().m;
    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     *
     * @param context
     * @return true 表示开启
     */
    public static final boolean isGPSOPen(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return gps || network;

    }

    /**
     * 强制帮用户打开GPS
     *
     * @param context
     */
    public static final void openGPS(Context context) {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        try {
            NetworkInfo[] netinfo = cm.getAllNetworkInfo();
            if (netinfo == null) {
                return false;
            }
            for (NetworkInfo element : netinfo) {
                if (element.isConnected()) {
                    return true;
                }
            }
        } catch (Exception ex) {

        }
        return false;
    }

    public static Double getDoubleAccurate(double num) {
        //使用0.00不足位补0，#.##仅保留有效位
        String str = new DecimalFormat("0.00").format(num);
        return Double.parseDouble(str);
    }

    /**
     * 将实际地理距离转换为屏幕像素值
     *
     * @param distance  实际距离,单位为米
     * @param currScale 当前地图尺寸
     * @param context
     * @return
     */
    public static double metreToScreenPixel(double distance, double currScale,
                                            Context context) {
        float dpi = context.getResources().getDisplayMetrics().densityDpi;
        // 当前地图范围内1像素代表多少地图单位的实际距离
        double resolution = (25.39999918 / dpi)
                * currScale / 1000;
        return distance / resolution;
    }

    /**
     * 将屏幕上对应的像素距离转换为当前显示地图上的地理距离(米)
     *
     * @param pxlength
     * @param currScale
     * @param context
     * @return
     */
    public static double screenPixelToMetre(double pxlength, double currScale,
                                            Context context) {
        float dpi = context.getResources().getDisplayMetrics().densityDpi;
        double resolution = (25.39999918 / dpi)
                * currScale / 1000;
        return pxlength * resolution;
    }

    public static Double[] findNeighPosition(double longitude, double latitude) {
        //先计算查询点的经纬度范围
        double r = 6371;//地球半径千米
        double dis = 0.1;//0.5千米距离
        double dlng = 2 * Math.asin(Math.sin(dis / (2 * r)) / Math.cos(latitude * Math.PI / 180));
        dlng = dlng * 180 / Math.PI;//角度转为弧度
        double dlat = dis / r;
        dlat = dlat * 180 / Math.PI;
        double minlat = latitude - dlat;
        double maxlat = latitude + dlat;
        double minlng = longitude - dlng;
        double maxlng = longitude + dlng;
        Double[] d = new Double[]{minlat, maxlat, minlng, maxlng};
        return d;
    }

    public static String getPagingID(ObjectIdsBean b, int pageIndex) {
//		1%2C2%2C3%2C4%2C5%2C6

        int start = (pageIndex - 1) * 20;
        int end = pageIndex * 20;
        int size = b.objectIds.size();
        if (size < end) {
            end = size;
        }
        StringBuilder sb = new StringBuilder();

        for (int i = start; i < end; i++) {
            if (i == end) {
                sb.append(b.objectIds.get(i));
            } else {
                sb.append(b.objectIds.get(i) + "%2C");
            }
        }
        return sb.toString();
    }

    public static boolean openFile(Context mContext, int position, ArrayList<String> imaDatas) {
        if (position == 0 || position == 1) {
            if (imaDatas.get(position).endsWith("视频")) {
                Intent intent = new Intent(mContext, PlayVideoActivity.class);
                intent.putExtra("url", imaDatas.get(position).replace("视频", ""));
                mContext.startActivity(intent);
                return true;
            } else if (imaDatas.get(position).endsWith("语音")) {
                String[] urlarry = imaDatas.get(position).replace("语音", "").split("@");
                PlaybackFragment playbackFragment =
                        new PlaybackFragment().newInstance(urlarry[0], Long.parseLong(urlarry[1]));

                FragmentTransaction transaction = ((FragmentActivity) mContext)
                        .getSupportFragmentManager()
                        .beginTransaction();

                playbackFragment.show(transaction, "dialog_playback");
                return true;
            }
        }
        return false;
    }

    public static long getTimeperiod(String timestrat, String timeend) {
        long diff = 0;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d1 = df.parse(timestrat);
            Date d2 = df.parse(timeend);
            diff = d2.getTime() - d1.getTime();//这样得到的差值是毫秒级别
//        long days = diff / (1000 * 60 * 60 * 24);
//
//        long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
//        long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
//        long second = (diff/1000-days*24*60*60-hours*60*60-minutes*60);
//        time= hours+":"+minutes+":"+second;
        } catch (Exception e) {
        }
        return diff / 1000;
    }

    public static long StrToLong(String dataStr) {
        long diff = 0;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d1 = df.parse(dataStr);
            diff = d1.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return diff;
    }

    public static long getTimeperiod(String timestrat, String timePause, String timeend) {
        long diff = 0;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d1 = df.parse(timestrat);
            Date d2 = df.parse(timePause);
            Date d3 = df.parse(timeend);
            diff = d3.getTime() - d1.getTime() - d2.getTime();//这样得到的差值是毫秒级别
        } catch (Exception e) {
        }
        return diff / 1000;
    }

    /**
     * 判断服务是否开启
     *
     * @return
     */
    public static boolean isServiceRunning(Context context, String ServiceName) {
        if (TextUtils.isEmpty(ServiceName)) {
            return false;
        }
        ActivityManager myManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager
                .getRunningServices(30);
        for (int i = 0; i < runningService.size(); i++) {
            String serviceName = runningService.get(i).service.getClassName().toString();
            if (serviceName.equals(ServiceName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 进程是否存活
     *
     * @return
     */
    public static boolean isRunningProcess(ActivityManager manager, String processName) {
        if (manager == null)
            return false;
        List<ActivityManager.RunningAppProcessInfo> runnings = manager.getRunningAppProcesses();
        if (runnings != null) {
            for (ActivityManager.RunningAppProcessInfo info : runnings) {
                if (TextUtils.equals(info.processName, processName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取当前进程名称
     *
     * @return
     */
    public static String getProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将数据保留两位小数
     */
    public static double getTwoDecimal(double doublenum) {

        BigDecimal b = new BigDecimal(doublenum);
        doublenum = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return doublenum;


    }

    public static void setdialog(final Context mContext, String title, String mes, final int falt, final LatLng lls, final LatLng lle) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext, AlertDialog.THEME_HOLO_LIGHT)
                .setTitle(title)
                .setMessage(mes).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (falt == 1) {
//                            OpenClientUtil.getLatestBaiduMapApp(mContext);
                        } else if (falt == 2) {
                            AppTool.openWebMap(mContext, lls, lle);
//                            AppTool.openDownloadMap(mContext, lls,lle);//错的，下载完好不能直接打开
                        }
                    }
                });
        dialogBuilder.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialogBuilder.show();
    }

    public static void startNative(Context context, LatLng locations, LatLng loc) {
        Intent intent = new Intent("android.intent.action.VIEW",
                android.net.Uri.parse("amapuri://route/plan/?sid=BGVIS1&slat=" + locations.latitude + "&slon=" + locations.longitude +
                        "&sname=" + "起始位置" + "&did=BGVIS2&dlat=" + loc.latitude + "&dlon=" + loc.longitude + "&dname=" + "终点位置" + "&dev=0&t=0"));
        intent.setPackage("com.autonavi.minimap");
        context.startActivity(intent);
    }

    /**
     * 打开浏览器进行高德地图导航
     */
    public static void openWebMap(Context context, LatLng locations, LatLng loc) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse("http://uri.amap.com/navigation?from=" + locations.longitude + "," + locations.latitude + ",startpoint&to=" + loc.longitude + "," + loc.latitude + "," +
                "endpoint&via&midwaypoint&mode=car&policy=1&src=mypage&coordinate=gaode&callnative=0");
        intent.setData(content_url);
        context.startActivity(intent);
    }

    public static void openDownloadMap(Context context, LatLng locations, LatLng loc) {
        Intent intent = null;
        try {
            intent = Intent.parseUri("http://wap.amap.com/?from=m&type=m", 0);
            context.startActivity(intent);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    public static boolean isInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String pkName = packageInfos.get(i).packageName;
                if (pkName.equals(packageName)) return true;
            }
        }
        return false;
    }
}
