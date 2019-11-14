package com.wavenet.ding.qpps.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dereck.library.download.DownloadObserver;
import com.dereck.library.interceptor.Transformer;
import com.dereck.library.observer.CommonObserver;
import com.dereck.library.utils.RxHttpUtils;
import com.dereck.library.utils.ToastUtils;
import com.dereck.library.view.LoadingWaitView;
import com.google.gson.Gson;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.api.ApiService;
import com.wavenet.ding.qpps.bean.Response;
import com.wavenet.ding.qpps.interf.CallBackMap;
import com.wavenet.ding.qpps.interf.OnClickInteDef;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import io.reactivex.disposables.Disposable;

/**
 * Created by zoubeiwen on 2018/7/18.
 */

public class MapdownloadUtil {
    Context mContext;
    CallBackMap mCallBackMap;
    int mapversion = 2;//判断地图是否版本提高2
    String mapbaseDisk;
    Activity mActivity  ;
  public static String strMapUrl;
  public static int APPSIZE=-1;
  public static boolean isStop=false;
  public  boolean isLogin=false;


    String mapName;
    public MapdownloadUtil(Context mContext) {
        this.mContext = mContext;
        mActivity = (Activity) mContext;
    }
// mLoadingWaitView.setLoadingText("正在下载地图...")
LoadingWaitView mLoadingWaitView;
    public void requestMapbase(LoadingWaitView mLoadingWaitView) {
        isStop=false;
        this.mLoadingWaitView=mLoadingWaitView;
        this.mLoadingWaitView.setLoadingText("检查地图版本...");
        if (!AppTool.isNull(MapdownloadUtil.strMapUrl)){
            File f = new File(strMapUrl );
            if (f.exists()){
                try {
                    long fsize=f.length()/1024/1024;
                    if (APPSIZE!=-1&&!(fsize<APPSIZE)){
                        setListener(OnClickInteDef.DOWNLOADSUCCESS);
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        mapbaseDisk=AppTool.getMapFolder(mContext,"").toString();
        RxHttpUtils
                .createApi(ApiService.class)
                .mMapbase(AppConfig.BeasUrl+"2056/api/Version/GetMapVersion")
                .compose(Transformer.<String>switchSchedulers())
                .subscribe(new CommonObserver<String>() {
                    @Override
                    protected void onError(String errorMsg) {
                        ToastUtils.showToast(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String listResponse) {
                        Response response= new Gson().fromJson(listResponse,Response.class);
                        if (response.Code== 200) {
                            if (response.Data!=null && response.Data.size()>0)
                                isDownloadMapbase(response.Data.get(0));


                        } else {
                            ToastUtils.showToast(response.Msg);
                        }
                    }


                });
    }


    public void isDownloadMapbase(Response.DataBean rd){
        this .rd=rd;
        APPSIZE=rd.APPSIZE;
        mapName=rd.NAME;
        String diskurl=getvtppath(rd.NAME);
        File f = new File(diskurl );
        if (f.exists()){
            strMapUrl = diskurl;//文件存在存地址路径为全局静态变了
            try {
                long fsize=f.length()/1024/1024;
                if (fsize<rd.APPSIZE-10){
                    startDown();
                }else {
                    setListener(OnClickInteDef.DOWNLOADSUCCESS);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            startDown();
        }
    }
    public void startDown(){
        mapbaseDisk=AppTool.getMapFolder(mContext,"").toString();
        File f1 = new File(mapbaseDisk);
        AppTool.deleteFiles(f1);
        strMapUrl="";
//        mLoadingWaitView.setLoadingText("正在下载最新版本地图...");
            setProgressDialog();
        downloadMapbase(rd.URL,rd.NAME+".vtpk");
    }
    Disposable mDisposable ;
    public  void downloadMapbase(String url,String fileName){

        RxHttpUtils
//                        .downloadFile("http://222.66.154.70:2088/apk/qpbasemap.vtpk")
                .downloadFile(url)
                .subscribe(new DownloadObserver(fileName,mapbaseDisk) {
                    @Override
                    protected void getDisposable(Disposable d) {
                        mDisposable=d;
                    }

                    @Override
                    protected void onError(String errorMsg) {
                        setListener(OnClickInteDef.DOWNLOADFIL);
                    }

                    @Override
                    protected void onSuccess(long bytesRead, long contentLength, final float progress, final boolean done, final String filePath) {
if (isStop){
    RxHttpUtils.cancelSingleRequest(mDisposable);
    return;
}

                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                mLoadingWaitView.setLoadingText("正在下载最新版本地图..."+progress+"%");
                                cacl_progressBar.setProgress((int) progress);
//                                pDialog.setProgress((int) progress);
//                                ToastUtils.showToast("下载中：" + progress + "%");
                                if (done) {
                                    pDialog.dismiss();
                                    ToastUtils.showToast("下载完成");
                                    strMapUrl=getvtppath(mapName);
                                    goAListener(a,"");//不可点击
                                    setListener(OnClickInteDef.DOWNLOADSUCCESS);
                                }
                            }
                        });
                    }
                });
    }
    public String getvtppath(String filename) {
        return Environment.getExternalStorageDirectory() + "/" + AppTool.getAppName(mContext) + "/arcgismap/"+filename+".vtpk";
    }

    public void set() {
        boolean b = SPUtil.getInstance(mContext).getBooleanValue(SPUtil.MAPOFFERLINE, false);
        int ver = SPUtil.getInstance(mContext).getIntValue(SPUtil.MAPOFFERLINEVER, 0);
        File f = new File(Environment.getExternalStorageDirectory() + "/" + AppTool.getAppName(mContext) + "/arcgismap");
       if (!b || !f.exists()  || f.list().length == 0 || ver != mapversion) {
            f.delete();
            SPUtil.getInstance(mContext).setBooleanValue(SPUtil.MAPOFFERLINE, false);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    copyFilesFassets(mContext, "arcgismap", "");
                }
            }).start();

        } else {
            setListener(OnClickInteDef.DOWNLOADSUCCESS);
        }
    }

    /**
     * 从assets目录中复制整个文件夹内容
     *
     * @param context Context 使用CopyFiles类的Activity
     * @param oldPath String  原文件路径  如：/aa
     * @param newPath String  复制后夹名称  如：bb
     */
    public void copyFilesFassets(Context context, String oldPath, String newPath) {
        try {
            String fileNames[] = context.getAssets().list(oldPath);//获取assets目录下的所有文件及目录名
            if (fileNames.length > 0) { //如果是目录
                File file = new File(newPath);
                file.mkdirs();//如果文件夹不存在，则递归
                for (String fileName : fileNames) {
                    copyFilesFassets(context, oldPath + "/" + fileName, newPath + "/" + fileName);
                }
            } else {//如果是文件
                InputStream is = context.getAssets().open(oldPath);
                FileOutputStream fos = new FileOutputStream(AppTool.getMapFolder(mContext, newPath));
                byte[] buffer = new byte[1024];
                int byteCount = 0;
                while ((byteCount = is.read(buffer)) != -1) {//循环从输入流读取 buffer字节
                    fos.write(buffer, 0, byteCount);//将读取的输入流写入到输出流
                }
                fos.flush();//刷新缓冲区
                is.close();
                fos.close();
                SPUtil.getInstance(mContext).setBooleanValue(SPUtil.MAPOFFERLINE, true);
                SPUtil.getInstance(mContext).setIntValue(SPUtil.MAPOFFERLINEVER, mapversion);
                setListener(OnClickInteDef.DOWNLOADSUCCESS);
            }
        } catch (Exception e) {
            //如果捕捉到错误则通知UI线程
            setListener(OnClickInteDef.DOWNLOADFIL);
        }
    }
    ProgressBar cacl_progressBar;
    AlertDialog pDialog;
public void setProgressDialog(){
    pDialog = new AlertDialog.Builder(mContext).create();
    pDialog.setCancelable(false);
    pDialog.setCanceledOnTouchOutside(false);
    pDialog.show();
    Window window = pDialog.getWindow();
    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 有白色背
    window.setContentView(R.layout.dialog_custom_progress);
     cacl_progressBar = window.findViewById(R.id.cacl_progressBar);
    // 让ProgressDialog显示
    pDialog.show();
}

    public  void Rxhttp(){
        RxHttpUtils
                .createApi(ApiService.class)
                .mMapbase(AppConfig.BeasUrl+"2056/api/Version/GetMapVersion")
                .compose(Transformer.<String>switchSchedulers())
                .subscribe(new CommonObserver<String>() {
                    @Override
                    protected void onError(String errorMsg) {
                        ToastUtils.showToast(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String listResponse) {
                        Response response= new Gson().fromJson(listResponse,Response.class);

                        if (response.Code== 200) {
                            if (response.Data!=null && response.Data.size()>0)
                                setMapbaseData(response.Data.get(0));


                        } else {
                            ToastUtils.showToast(response.Msg);
                        }
                    }


                });  }
    Response.DataBean rd ;
    String a="";
    public void setMapbaseData(Response.DataBean rd){
       this.rd=rd;
        APPSIZE=rd.APPSIZE;
        mapName=rd.NAME;// qpbasemap1.0.1
         a="基础底图 (V"+mapName.replace("qpbasemap","")+"  "+APPSIZE+"M)";

        MapInfo(mapName.replace("qpbasemap",""));
//        String diskurl=getvtppath(rd.NAME);
//        File f = new File(diskurl );
        if (isLogin){
            if (isHaveMap){
                setListener(OnClickInteDef.DOWNLOADSUCCESS);
            }else {
                goBListener(false);
            }
            return;
        }else {
            if (isHaveMap){
                if (isNewVerMap){
                    if (isNewVerMapComplete){

                        goAListener(a,"");//不可点击
                    }else {
                        goAListener(a,"下载");
                    }
                }else {
                    goAListener(a,"有更新");//少了old的地图不完整情况 需要接口提供最版本地图的包大小  暂不写
                }
                setMap();
            }else {
                goAListener(a,"下载");
            }

        }

    }
    boolean isHaveMap=false;//本地是否有地图        ture？""：下载
    boolean isNewVerMap=false;//本地地图是否是最新版本   ture？""：更新
    boolean isNewVerMapComplete=false;//本地最新地图是否完整  ture？"不可点击"：下载
    public void MapInfo(String ver) {
        File f=AppTool.getMapFolder(mContext,"");
        File[] flist=f.listFiles();
        isHaveMap=false;
        isNewVerMap=false;
        isNewVerMapComplete=false;
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].getName().contains("qpbasemap")&&flist[i].getName().contains(".vtpk")){
                isHaveMap=true;//是否有地图
                if (flist[i].getName().contains(ver)){
                    isNewVerMap=true;//是否只最新版本地图
                    long fsize=flist[i].length()/1024/1024;
                    if (fsize<rd.APPSIZE){
                        isNewVerMapComplete=false;
                    }else {
                        isNewVerMapComplete=true;
                    }
                }
            }

        }
    }
    public void setMap(){
        File f=AppTool.getMapFolder(mContext,"");
        File[] flist=f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].getName().contains("qpbasemap")&&flist[i].getName().contains(".vtpk")){
                strMapUrl=flist[i].getAbsolutePath();
                setListener(OnClickInteDef.DOWNLOADSUCCESS);
                i=flist.length;
//                    long fsize=flist[i].length()/1024/1024;
//                    if (!(fsize<rd.OLDAPPSIZE)){
//                        strMapUrl=flist[i].getAbsolutePath();
//                        setListener(OnClickInteDef.DOWNLOADSUCCESS);
//                        i=flist.length;
//                    }

                }
            }
    }    public void isHaveOldMap(){
        File f=AppTool.getMapFolder(mContext,"");
        File[] flist=f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].getName().contains("qpbasemap")&&flist[i].getName().contains(".vtpk")){

                    long fsize=flist[i].length()/1024/1024;
                    if (fsize<rd.OLDAPPSIZE-10){//包大小大概范围
                        goBListener(false);
                        i=flist.length;
                    }

                }
            }
    }
     AlertDialog alertDialog ;
    public void showMesDialog() {
        alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 有白色背
        window.setContentView(R.layout.dialog_tips);
        window.findViewById(R.id.version_describe).setVisibility(View.GONE);
        TextView tv_content = window.findViewById(R.id.tv_content);

            tv_content.setText("需要下载地图包，否则可能会导致程序无法正常运行?");

        window.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
               startDown();
            }
        });
        window.findViewById(R.id.tv_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
    public void setListener(final int backMap) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (backMap==OnClickInteDef.DOWNLOADSUCCESS){
//                    mLoadingWaitView.success();
                }else {
//                    mLoadingWaitView.setVisibility(View.GONE);
                    if (pDialog!=null){
                        pDialog.dismiss();
                    }
                    ToastUtils.showToast("地图包数据下载失败");
                }
                if (mCallBackMap != null) {
                    mCallBackMap.onClick(OnClickInteDef.setOnclick(backMap));
                }
            }
        });

    }

    public MapdownloadUtil setCallBackMap(CallBackMap mCallBackMap) {
        this.mCallBackMap = mCallBackMap;
        return this;
    }
    public void goAListener(String a,String b){
        if (mA!=null){
            mA.Aa(a,b);
        }
    }   public void goBListener(boolean b){
        if (!b){
            showMesDialog();
            return;
        }
        if (mB!=null){
            mB.Ba(b);
        }
    }
    private A mA;
    public interface  A {
        void Aa(String a,String b);//（版本号  包大小）  ，更新/下载
    }
    public  MapdownloadUtil setAListener(A mA){
        this.mA=mA;
        isStop=false;
        return this;
    }
    private B mB;
    public interface  B {
        void Ba(boolean b);
    }
    public  MapdownloadUtil setBListener(B mB){
        this.mB=mB;
        isStop=false;
        isLogin=true;
        return this;
    }
}
