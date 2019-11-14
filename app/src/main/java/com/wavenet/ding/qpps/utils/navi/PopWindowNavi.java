package com.wavenet.ding.qpps.utils.navi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.amap.api.maps2d.model.LatLng;
import com.dereck.library.utils.ToastUtils;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.bean.Gps;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.PositionUtil;

/**
 * 自定义泡泡窗口(居中显示窗口)
 */
public class PopWindowNavi extends PopupWindow implements View.OnClickListener
{Context mContext;
    Activity activity;

    private SparseArray<View> mViews;
    private ViewGroup mConvertView;
    private int mWidth;
    private int mHeight;
    public PopWindowNavi(Activity activity){
        this.activity=activity;
        mContext=activity;
        this.mViews=new SparseArray<View>();
        //计算宽度和高度
        calWidthAndHeight(activity);
        setWidth(mWidth);
        setHeight(mHeight);
        LayoutInflater mLayoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mConvertView = (ViewGroup) mLayoutInflater.inflate(
                R.layout.pop_function, null, true);
        this.setContentView(mConvertView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        mConvertView.setTag(this);
        // 设置动画效果
        this.setAnimationStyle(R.style.popwin_slide_style);
        //设置如下四条信息，当点击其他区域使其隐藏，要在show之前配置
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
        this.setBackgroundDrawable(new BitmapDrawable());
        mConvertView.findViewById(R.id.tv_navibd).setOnClickListener(this);
        mConvertView.findViewById(R.id.tv_navigd).setOnClickListener(this);
        mConvertView.findViewById(R.id.tv_navitx).setOnClickListener(this);
        mConvertView.findViewById(R.id.iv_null).setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
                case R.id.iv_null:
                isClosePop();
                break;case R.id.tv_navibd:
                isClosePop();
                StatrEnd(1);
                break; case R.id.tv_navigd:
                isClosePop();
                StatrEnd(2);
                break; case R.id.tv_navitx:
                isClosePop();
                break;
        }
    }


    //关闭窗口
    public void dismissPopupWindow(){
        PopWindowNavi.this.dismiss();
    }

    public View getConvertView()
    {
        return mConvertView;
    }
    int selecet = 0;
    //判断是否关闭窗口
    public void isClosePop(){
        if(this.isShowing()){
            this.dismiss();
        }
    }
    public void showPop(){
        this.showAtLocation(mConvertView, Gravity.CENTER, 0, 0);
    }

    /**
     * 设置PopupWindow的大小
     * @param context
     */
    private void calWidthAndHeight(Context context) {
        WindowManager wm= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics= new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);

        mWidth=metrics.widthPixels;
        //设置高度为全屏高度的70%
        mHeight= (int) (metrics.heightPixels*1);
    }
    String addrstr;
    public void setLocinfo( LatLng startLL  ,LatLng endLL,String addrstr){//火星坐标系；
        this. addrstr=addrstr;
        this. endLL=endLL;
        this. startLL=startLL;
    }
    public void StatrEnd(int falt) {
        if (falt == 1000) {
//            isFristLoc=false;
//            mControllerMapView.setLoca(mLatLng);//定位
        } else if (falt == 0) {
//            mBNaviutil = new BNaviutil(activity, endLL, mLatLng);
        } else if (falt == 1) {
            setNavi(endLL, startLL);//百度导航
        } else if (falt == 2) {
            setNaviGD(endLL, startLL);//高德导航
        }
    }

    LatLng endLL;
    LatLng startLL;


    public void getLatLngEnd(int falt){
        if (endLL != null) {
            setLocation(falt);
        } else {
            ToastUtils.showToast( "目的地坐标有误");
        }
    }
    public void setLocation(int falt) {
        ToastUtils.showToast("正在定位，请稍后...");
    }
    public void setNavi(LatLng mLatLngE,LatLng mLatLngS){
        if (!AppTool.checkPackage("com.baidu.BaiduMap", mContext)) {
            AppTool.setdialog(mContext,"提示","您尚未安装百度地图app或app版本过低，点击确认安装？",1,null,null);
            return;
        }

        Gps gs=PositionUtil.gcj02_To_Bd09(mLatLngS.latitude,mLatLngS.longitude);
        Gps ge=PositionUtil.gcj02_To_Bd09(mLatLngE.latitude,mLatLngE.longitude);
        openBaiduMap(gs.getWgLat(),gs.getWgLon(),"",ge.getWgLat(),ge.getWgLon(),addrstr,"");
        /**
         * 驾车导航
         */
//    NaviParaOption para = new NaviParaOption();
//    para.startPoint(mLatLngS).startName("我的位置");
////                para.endPoint(desLatLng).endName("任务地点");
//    para.endPoint(mLatLngE).endName("任务地点");
//    try {
//        BaiduMapNavigation.openBaiduMapNavi(para,this);
//    } catch (BaiduMapAppNotSupportNaviException e) {
//        AppTool.setdialog(this,"提示","您尚未安装百度地图app或app版本过低，点击确认安装？",1,null,null);
//    }
    }
    /**
     *  打开百度地图
     */
//    private static String SRC = "thirdapp.navi.beiing.openlocalmapdemo";
    private static String SRC = "thirdapp.navi.beiing.openlocalmapdemo";
    private boolean isOpened;
    private void openBaiduMap(double slat, double slon, String sname, double dlat, double dlon, String dname, String city) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("baidumap://map/direction?destination=latlng:"
                + dlat + ","
                + dlon + "|name:" + sname + // 终点
                "&mode=driving" + // 导航路线方式
                "&src=" + SRC));
        mContext.startActivity(intent); // 启动调用
    }
    public void setNaviGD(LatLng mLatLngE,LatLng mLatLngS){
        Gps gpsE = PositionUtil.bd09_To_Gcj02(mLatLngE.latitude, mLatLngE.longitude);
        Gps gpsS = PositionUtil.bd09_To_Gcj02(mLatLngS.latitude, mLatLngS.longitude);
        LatLng lls=new LatLng(gpsS.getWgLat(),gpsS.getWgLon());
        LatLng lle=new LatLng(gpsE.getWgLat(),gpsE.getWgLon());
        if(AppTool.isInstalled(mContext,"com.autonavi.minimap")){
            AppTool.startNative(mContext, lls,lle);
        }else{
//            AppTool.setdialog(this,"提示","您尚未安装高德地图app或app版本过低，点击确认安装？",2,lls,lle);
            AppTool.setdialog(mContext,"提示","您尚未安装高德地图app或app版本过低，点击进入网页地图？",2,lls,lle);

        }
    }

}
