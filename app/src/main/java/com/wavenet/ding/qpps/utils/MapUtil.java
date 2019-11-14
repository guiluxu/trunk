package com.wavenet.ding.qpps.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.dereck.library.utils.AppManager;
import com.dereck.library.utils.ToastUtils;
import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.data.FeatureQueryResult;
import com.esri.arcgisruntime.data.QueryParameters;
import com.esri.arcgisruntime.data.ServiceFeatureTable;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.esri.arcgisruntime.layers.ArcGISMapImageSublayer;
import com.esri.arcgisruntime.layers.ArcGISVectorTiledLayer;
import com.esri.arcgisruntime.layers.WebTiledLayer;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.esri.arcgisruntime.symbology.TextSymbol;
import com.google.gson.Gson;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.activity.AdminActivity;
import com.wavenet.ding.qpps.activity.LoginActivity;
import com.wavenet.ding.qpps.activity.MainMapXJActivity;
import com.wavenet.ding.qpps.activity.MainMapYHActivity;
import com.wavenet.ding.qpps.api.QPSWApplication;
import com.wavenet.ding.qpps.bean.AdapterBean;
import com.wavenet.ding.qpps.bean.AdminPeopleBean;
import com.wavenet.ding.qpps.bean.AdminPersonBean2;
import com.wavenet.ding.qpps.bean.BreakOffBean;
import com.wavenet.ding.qpps.bean.Gps;
import com.wavenet.ding.qpps.fragment.FourthFragment;
import com.wavenet.ding.qpps.interf.AddFeatureQueryResultListen;
import com.wavenet.ding.qpps.interf.AddLayerListen;
import com.wavenet.ding.qpps.interf.CallBackMap;
import com.wavenet.ding.qpps.interf.GoBackInteDef;
import com.wavenet.ding.qpps.interf.InterfListen;
import com.wavenet.ding.qpps.interf.OnClickInteDef;
import com.wavenet.ding.qpps.view.ControllerMainUIView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by zoubeiwen on 2018/7/19.
 */

public class MapUtil {
    public static int ui = 0;//0巡检页面，1养护页面，2管理员页面
    public static String L = "location";
    public static String TNLH = "tasknewlisthide";
    public static String TCI = "taskcurrentinit";
    public static String VEU = "vieweventunregistere";
    public static String FR = "filerequest";
    public static String FTD = "filetaskdeal";//日常处置
    public static String FTDP = "filetaskdealp";//结束派单
    public static String FTA = "filetaskadd";
    public static String TD = "taskdeal";//日常上报成功后弹出窗口确定去处置
    public static String CANCLE_TD = "cancle_taskdeal";//日常上报成功后弹出窗口取消去处置
    public static String czdw = "处置定位";
    public static double roadbuffer = 0.0004;
    private static MapUtil mMapUtil;
    public MainMapXJActivity mainMapXJActivity;
    Context mContext;
    GraphicsOverlay markerOverlay;
    ArcGISMapImageLayer mainMapImageLayer;
    SimpleMarkerSymbol mCircleSymbol;
    SimpleLineSymbol mLineSymbol;
    static PictureMarkerSymbol pictureLocSymbol = null;
    String APP_TOWNNAME;
    PictureMarkerSymbol pictureMarkerSymbolhight;
    PictureMarkerSymbol pictureMarkerSymbolXJr;
    PictureMarkerSymbol pictureMarkerSymbolXJy;
    PictureMarkerSymbol pictureMarkerSymbolYH;
    PictureMarkerSymbol pictureMarkerSymbolOfflineX;
    PictureMarkerSymbol pictureMarkerSymbolOfflineY;

    public MapUtil(Context mContext) {
        this.mContext = mContext;
    }

    public static MapUtil getInstance(Context mContext) {
        if (mMapUtil == null ) {
            mMapUtil = new MapUtil(mContext);
        }
        return mMapUtil;
    }  public static MapUtil init(Context mContext) {
            mMapUtil = new MapUtil(mContext);
        return mMapUtil;
    }

    public String getvtppath() {
        String strMapUrl = Environment.getExternalStorageDirectory() + "/" + AppTool.getAppName(mContext) + "/arcgismap/QPBaseMapLowColor.vtpk";
        return strMapUrl;
    }

    public boolean isExistspath(String strMapUrl) {
        boolean isExist = new File(strMapUrl).exists();
        if (!isExist) {
            ToastUtils.showToast("地图路径不存在");

        }
        return isExist;
    }

    public void setActivity(MainMapXJActivity mainMapXJActivity) {
        this.mainMapXJActivity = mainMapXJActivity;
    }
    public ArcGISMap getArcGISMapVector(String strMapUrl) {
        ArcGISVectorTiledLayer mainArcGISVectorTiledLayer = new ArcGISVectorTiledLayer(strMapUrl);
        Basemap mainBasemap = new Basemap(mainArcGISVectorTiledLayer);
        ArcGISMap mapVector = new ArcGISMap(mainBasemap);
        return mapVector;
    }

    public ArcGISMap getArcGISMapVector(MapView mapView) {
        WebTiledLayer webTiledLayer = TianDiTuMethodsClass.CreateTianDiTuTiledLayer(TianDiTuMethodsClass.LayerType.TIANDITU_IMAGE_2000);
        WebTiledLayer webTiledLayer1 = TianDiTuMethodsClass.CreateTianDiTuTiledLayer(TianDiTuMethodsClass.LayerType.TIANDITU_IMAGE_ANNOTATION_CHINESE_2000);
        Basemap mainBasemap = new Basemap(webTiledLayer);
        mainBasemap.getBaseLayers().add(webTiledLayer1);
        ArcGISMap mapVector = new ArcGISMap(mainBasemap);
//        mapView.setMap(mapVector);
//        setshanghai(mapView);
        return mapVector;
    }

    public ArcGISMap getArcGISMapVector(String strMapUrl, MapView mapView) {

        ArcGISMap mapVector = getArcGISMapVector(strMapUrl);
        mapView.setMap(mapVector);
        setshanghai(mapView);
        return mapVector;
    }

    public GraphicsOverlay setLocico(AMapLocation mAMapLocation, MapView mMapView, GraphicsOverlay mLocationOverlay, String message, CallBackMap mCallBackMap) {
//        MainMapXJActivity mapXJActivity= (MainMapXJActivity) mContext;
        if (mAMapLocation == null || mAMapLocation.getLatitude() == 0 || mAMapLocation.getLatitude() == 0) {
            if (!AppTool.isNull(message)) {
                ToastUtils.showToast(message);
            }
            return null;
        }

        if (mLocationOverlay == null) {
            mLocationOverlay = new GraphicsOverlay();

        } else {
            mLocationOverlay.getGraphics().clear();
            mMapView.getGraphicsOverlays().remove(mLocationOverlay);
        }

        Gps gpsE = PositionUtil.gcj_To_Gps84(mAMapLocation.getLatitude(), mAMapLocation.getLongitude());

        Drawable drawable = mContext.getResources().getDrawable(R.mipmap.btn_weizhi);
        PictureMarkerSymbol pictureMarkerSymbol = new PictureMarkerSymbol((BitmapDrawable) drawable);
        double lon = gpsE.getWgLon();
        double lat = gpsE.getWgLat();
        Point point = new Point(lon, lat, SpatialReference.create(4326));
//        GeographicTransformation transform = GeographicTransformation.create(GeographicTransformationStep.create(108336));
//        Point pot102100 = (Point) GeometryEngine.project(point, SpatialReference.create(102100), transform);
        Graphic graphicimg = new Graphic(point, pictureMarkerSymbol);
        mLocationOverlay.getGraphics().add(graphicimg);

        mMapView.getGraphicsOverlays().add(mLocationOverlay);
        mMapView.setViewpointCenterAsync(point, 10000);

        if (mCallBackMap != null) {
            mCallBackMap.onClick(OnClickInteDef.setOnclick(OnClickInteDef.REMOVEMAPLOC));
        }
        return mLocationOverlay;
    }

    public PictureMarkerSymbol getLocSymbol() {

        Drawable drawable = mContext.getResources().getDrawable(R.mipmap.btn_weizhi);
        PictureMarkerSymbol pictureMarkerSymbol = new PictureMarkerSymbol((BitmapDrawable) drawable);
        return pictureMarkerSymbol;
    }
public void setGensui(AMapLocation mAMapLocation,MapView mMapView){
       if (!isLegalLL(mAMapLocation)){
           return;
       }
    Gps gpsE = PositionUtil.gcj_To_Gps84(mAMapLocation.getLatitude(), mAMapLocation.getLongitude());
    double lon = gpsE.getWgLon();
    double lat = gpsE.getWgLat();
    Point p = new Point(lon, lat, SpatialReference.create(4326));
    mMapView.setViewpointCenterAsync(p);
}
    public GraphicsOverlay setLocicon(AMapLocation mAMapLocation, MapView mMapView, GraphicsOverlay mLocationOverlay, String message) {
//        MainMapXJActivity mapXJActivity= (MainMapXJActivity) mContext;
        if (!isLegalLL(mAMapLocation)) {
            if (!AppTool.isNull(message)) {
                ToastUtils.showToast(message);
            }
            return null;
        }
        if (mLocationOverlay.getGraphics()!=null){
            mLocationOverlay.getGraphics().clear();
        }
        if (pictureLocSymbol == null) {
            pictureLocSymbol = getLocSymbol();
        }
        Gps gpsE = PositionUtil.gcj_To_Gps84(mAMapLocation.getLatitude(), mAMapLocation.getLongitude());
        double lon = gpsE.getWgLon();
        double lat = gpsE.getWgLat();
        QPSWApplication.Locpoint = new Point(lon, lat, SpatialReference.create(4326));
        Graphic graphicimg = new Graphic(QPSWApplication.Locpoint, pictureLocSymbol);
        mLocationOverlay.getGraphics().add(graphicimg);
        if (!mMapView.getGraphicsOverlays().contains(mLocationOverlay)) {
            mMapView.getGraphicsOverlays().add(mLocationOverlay);
        }
        return mLocationOverlay;
    }

    public GraphicsOverlay setLocicosld(AMapLocation mAMapLocation, final MapView mMapView) {
        if (mAMapLocation == null || mAMapLocation.getLatitude() == 0 || mAMapLocation.getLatitude() == 0) {
//            ToastUtils.showToast("定位失败");
            return null;
        }

        Gps gpsE = PositionUtil.gcj_To_Gps84(mAMapLocation.getLatitude(), mAMapLocation.getLongitude());

        final GraphicsOverlay mLocationOverlay = new GraphicsOverlay();
        Drawable drawable = mContext.getResources().getDrawable(R.mipmap.btn_weizhi);
        PictureMarkerSymbol pictureMarkerSymbol = new PictureMarkerSymbol((BitmapDrawable) drawable);
        double lon = gpsE.getWgLon();
        double lat = gpsE.getWgLat();
        Point point = new Point(lon, lat, SpatialReference.create(4326));
//        GeographicTransformation transform = GeographicTransformation.create(GeographicTransformationStep.create(108336));
//        Point pot102100 = (Point) GeometryEngine.project(point, SpatialReference.create(102100), transform);
        Graphic graphicimg = new Graphic(point, pictureMarkerSymbol);
        mLocationOverlay.getGraphics().add(graphicimg);
        mMapView.getGraphicsOverlays().add(mLocationOverlay);
        mMapView.setViewpointCenterAsync(point, 10000);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                mMapView.getGraphicsOverlays().remove(mLocationOverlay);
            }
        }, 5000);
        return mLocationOverlay;
    }

    public void removeLocationOverlay(GraphicsOverlay mLocationOverlay, MapView mMapView) {
        if (mLocationOverlay != null) {
            mMapView.getGraphicsOverlays().remove(mLocationOverlay);
        }
    }

    public void setshanghai(MapView mMapView) {
        mMapView.setViewpointRotationAsync(0);

//            Point point_loc = new Point(121,31 );
        Point point = new Point(121.094619, 31.166084, SpatialReference.create(4326));
//            GeographicTransformation transform_loc = GeographicTransformation.create(GeographicTransformationStep.create(108336));
//            Point pot102100_loc = (Point) GeometryEngine.project(point_loc, SpatialReference.create(102100), transform_loc);
        mMapView.setViewpointCenterAsync(point, 554423.0185056665);
    }

    public void setstardailyloc(AMapLocation mAMapLocation, MainMapXJActivity mapXJActivity, int DorP) {//DorP 0日常，1派单
        if (mAMapLocation == null || mAMapLocation.getLatitude() == 0 || mAMapLocation.getLatitude() == 0) {
            ToastUtils.showToast("定位失败");
//
        } else {
            String userstr = SPUtil.getInstance(mContext).getStringValue(SPUtil.USERNO);
            MainMapXJActivity.S_RECODE_ID = "XJ" + System.currentTimeMillis() + userstr;
            SPUtil.getInstance(mContext).setStringValue(SPUtil.XJID, MainMapXJActivity.S_RECODE_ID);
            if (DorP == 0) {
                mapXJActivity.presenter.clickTaskStart(mContext, userstr,AppAttribute.F.getXJID(mContext),AppTool.getCurrentDate(AppTool.FORMAT_YMDHMS), "1");
            } else if ((DorP == 1)) {
                mapXJActivity.presenter.clickTaskPaiStart1(mContext, userstr, AppAttribute.F.getXJID(mContext), MainMapXJActivity.STASKID, AppTool.getCurrentDate(AppTool.FORMAT_YMDHMS), "1");
            } else {
                mapXJActivity.presenter.clickTaskPaiStart(userstr, MainMapXJActivity.S_RECODE_ID, MainMapXJActivity.STASKID, AppTool.getCurrentDate(AppTool.FORMAT_YMDHMS), "1");
            }


        }
    }

    public GraphicsOverlay setstarico(AMapLocation mAMapLocation, MapView mMapView) {

        Gps gpsE = PositionUtil.gcj_To_Gps84(mAMapLocation.getLatitude(), mAMapLocation.getLongitude());
        GraphicsOverlay mStarOverlay = new GraphicsOverlay();
        Drawable drawable = mContext.getResources().getDrawable(R.mipmap.btn_location);
        PictureMarkerSymbol pictureMarkerSymbol = new PictureMarkerSymbol((BitmapDrawable) drawable);
        double lon = gpsE.getWgLon();
        double lat = gpsE.getWgLat();
        Point point = new Point(lon, lat, SpatialReference.create(4326));
//        GeographicTransformation transform = GeographicTransformation.create(GeographicTransformationStep.create(108336));
//        Point pot102100 = (Point) GeometryEngine.project(point, SpatialReference.create(102100), transform);
        Graphic graphicimg = new Graphic(point, pictureMarkerSymbol);
        mStarOverlay.getGraphics().add(graphicimg);
        mMapView.getGraphicsOverlays().add(mStarOverlay);
        mMapView.setViewpointCenterAsync(point, 10000);
        return mStarOverlay;
    }

    public GraphicsOverlay setstarico1(double x, double y, MapView mMapView) {


        Gps gpsE = PositionUtil.gcj_To_Gps84(x, y);
        GraphicsOverlay mStarOverlay = new GraphicsOverlay();
        Drawable drawable = mContext.getResources().getDrawable(R.mipmap.btn_location_shijian);
        PictureMarkerSymbol pictureMarkerSymbol = new PictureMarkerSymbol((BitmapDrawable) drawable);
        pictureMarkerSymbol.setOffsetY(40);
        double lon = gpsE.getWgLon();
        double lat = gpsE.getWgLat();
        Point point = new Point(y, x, SpatialReferences.getWgs84());
        Graphic graphicimg = new Graphic(point, pictureMarkerSymbol);
        mStarOverlay.getGraphics().add(graphicimg);
        mMapView.getGraphicsOverlays().add(mStarOverlay);

        mMapView.setViewpointCenterAsync(point, 10000);
        return mStarOverlay;
    }

    public GraphicsOverlay setstarpaiico(Point point, MapView mMapView, GraphicsOverlay mStarOverlay, PictureMarkerSymbol pictureMarkerSymbol) {
        mStarOverlay.getGraphics().clear();
        Graphic graphicimg = new Graphic(point, pictureMarkerSymbol);
        mStarOverlay.getGraphics().add(graphicimg);
        if (!mMapView.getGraphicsOverlays().contains(mStarOverlay)) {
            mMapView.getGraphicsOverlays().add(mStarOverlay);
        }
        mMapView.setViewpointCenterAsync(point, 10000);
        return mStarOverlay;
    }

    public void showGpsDialog(final Context mContext, final int a) {
        if (AppTool.isGPSOPen(mContext)) {
            return;
        }
        final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 有白色背
        window.setContentView(R.layout.dialog_gpsseting);
//    tv_sure   rb_gps2  rg_gps  rb_gps1  iv_close
        RadioGroup mRggps = window.findViewById(R.id.rg_gps);

        window.findViewById(R.id.ll_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        window.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
//                AppTool.openGPS(mContext);


                // 转到手机设置界面，用户设置GPS
                Intent intent = new Intent(
                        Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                if (a == 0) {
                    final MainMapXJActivity mapXJActivity = (MainMapXJActivity) mContext;
                    mapXJActivity.startActivityForResult(intent, 0); // 设置完成后返回到原来的界面
                } else if (a == 1) {
                    final MainMapYHActivity MapYHActivity = (MainMapYHActivity) mContext;
                    MapYHActivity.startActivityForResult(intent, 0); // 设置完成后返回到原来的界面
                } else if (a == 2) {
                    final AdminActivity mAdminActivity = (AdminActivity) mContext;
                    mAdminActivity.startActivityForResult(intent, 0); // 设置完成后返回到原来的界面
                }


            }
        });
    }

    public GraphicsOverlay setMarkerico(final Point tapPoint, final AMapLocation mAMapLocation, MapView mMapView, GraphicsOverlay markerOverlay, BitmapDrawable ico) {

        if (mAMapLocation == null || mAMapLocation.getLatitude() == 0 || mAMapLocation.getLatitude() == 0)
            return null;
        Gps gpsE = PositionUtil.gcj_To_Gps84(mAMapLocation.getLatitude(), mAMapLocation.getLongitude());
        if (markerOverlay == null) {
            markerOverlay = new GraphicsOverlay();
            this.markerOverlay = markerOverlay;
        }
        final PictureMarkerSymbol pinDestinationSymbol;
        try {
            {
                pinDestinationSymbol = PictureMarkerSymbol.createAsync(ico).get();
                pinDestinationSymbol.loadAsync();
                final GraphicsOverlay finalMarkerOverlay = markerOverlay;
                pinDestinationSymbol.addDoneLoadingListener(new Runnable() {
                    @Override
                    public void run() {
                        //add a new graphic as end point
//                        Point     mDestinationPoint = new Point(121, 31, SpatialReferences.getWgs84());
                        Map<String, Object> m = new HashMap<>();
                        m.put("mAMapLocation", "mAMapLocation");
                        Graphic graphic = new Graphic(tapPoint, m, pinDestinationSymbol);

                        finalMarkerOverlay.getGraphics().add(graphic);
                    }
                });
//                pinDestinationSymbol.setOffsetY(20);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (mMapView.getGraphicsOverlays().contains(markerOverlay)) {

            mMapView.getGraphicsOverlays().add(markerOverlay);
        }
        mMapView.invalidate();
        return markerOverlay;
    }

    public void showTasknewDialog(Context mContext) {
        final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 有白色背
        window.setContentView(R.layout.dialog_taskselect);
//    tv_sure   rb_gps2  rg_gps  rb_gps1  iv_close
        RadioGroup mRggps = window.findViewById(R.id.rg_gps);

        final RadioButton mRbgps1 = window.findViewById(R.id.rb_gps1);

        final RadioButton mRbgps2 = window.findViewById(R.id.rb_gps2);
        window.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        window.findViewById(R.id.tv_suer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRbgps1.isChecked()) {
                    alertDialog.dismiss();
                } else if (mRbgps2.isChecked()) {
                    EventBus.getDefault().post(MapUtil.TCI);
                } else {
                    ToastUtils.showToast("请选择执行项");
                }
                alertDialog.dismiss();
            }
        });
    }

    public void showTaskendDialog(final Context mContext) {
        final MainMapXJActivity mapXJActivity = (MainMapXJActivity) mContext;
        final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 有白色背
        window.setContentView(R.layout.dialog_tips);
        TextView tv_content = window.findViewById(R.id.tv_content);
        tv_content.setText("确定结束本次巡检吗?");
        window.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                mapXJActivity.presenter.RequestEndTask(AppAttribute.F.getXJID(mContext), AppTool.getCurrentDate(AppTool.FORMAT_YMDHMS));
//                EventBus.getDefault().post(MapUtil.DES);

            }
        });
        window.findViewById(R.id.tv_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    public void showTaskendPaiDialog(Context mContext) {
        final MainMapXJActivity mapXJActivity = (MainMapXJActivity) mContext;
        final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 有白色背
        window.setContentView(R.layout.dialog_tips);
        TextView tv_content = window.findViewById(R.id.tv_content);
        tv_content.setText("确定上报处置情况?");
        window.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                mapXJActivity.mTaskDealView.initData(MainMapXJActivity.mtvBean);
//                EventBus.getDefault().post(MapUtil.DES);

            }
        });
        window.findViewById(R.id.tv_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    public void showTaskreportDialog(Context mContext, final View v) {
        final MainMapXJActivity mapXJActivity = (MainMapXJActivity) mContext;
        final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 有白色背
        window.setContentView(R.layout.dialog_tips);
        TextView tv_content = window.findViewById(R.id.tv_content);
        tv_content.setText("您确定离开此界面吗?");
        window.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                v.setVisibility(View.GONE);
            }
        });
        window.findViewById(R.id.tv_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    //日常取消
    public void showTaskcancelDialog(final Context mContext, final ControllerMainUIView mainUIView) {
        final MainMapXJActivity mapXJActivity = (MainMapXJActivity) mContext;
        final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 有白色背
        window.setContentView(R.layout.dialog_tips);
        TextView tv_content = window.findViewById(R.id.tv_content);
        tv_content.setText("您确定取消本次巡检吗?");
        APP_TOWNNAME = SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_TOWNNAME);
        window.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                mapXJActivity.presenter.RequestCancleTask(AppAttribute.F.getXJID(mContext), AppTool.getCurrenttimeT(), APP_TOWNNAME);

            }
        });
        window.findViewById(R.id.tv_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    //派单取消
    public void showTaskcancelDialog2(final Context mContext, final ControllerMainUIView mainUIView) {
        final MainMapXJActivity mapXJActivity = (MainMapXJActivity) mContext;
        final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 有白色背
        window.setContentView(R.layout.dialog_tips);
        TextView tv_content = window.findViewById(R.id.tv_content);
        tv_content.setText("您确定取消本次巡检吗?");
        APP_TOWNNAME = SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_TOWNNAME);
        window.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                mapXJActivity.presenter.RequestCancleTask1(AppAttribute.F.getXJID(mContext), MainMapXJActivity.S_MANGE_ID, AppTool.getCurrentDate(AppTool.FORMAT_YMDHMS), APP_TOWNNAME);

            }
        });
        window.findViewById(R.id.tv_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    public void showTaskdealDialog(Context mContext) {

        final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.alpha = 0.5f;    // 设置透明度为0.5
//        window.setAttributes(lp);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 有白色背
        window.setContentView(R.layout.dialog_tips);
        window.findViewById(R.id.version_describe).setVisibility(View.GONE);
        TextView tv_content = window.findViewById(R.id.tv_content);
        tv_content.setText("确定现在处置吗?");
        window.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(MapUtil.TD);
                alertDialog.dismiss();
            }
        });
        window.findViewById(R.id.tv_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                //如果不处置，发送push消息
                EventBus.getDefault().post(MapUtil.CANCLE_TD);
            }
        });
    }

    public void showcleanCacheDialog(final Context mContext, final TextView tv) {

        final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 有白色背
        window.setContentView(R.layout.dialog_tips);
        window.findViewById(R.id.version_describe).setVisibility(View.GONE);
        TextView tv_content = window.findViewById(R.id.tv_content);
        tv_content.setText("确定清除缓存吗?");
        window.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                CacheManager.clearAllCache(mContext);
                try {
                    tv.setText(CacheManager.getTotalCacheSize(mContext) + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        window.findViewById(R.id.tv_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
    public void showIsReplaceFileDialog(final Context mContext, final GoBackInteDef.GoBack mGoBack, final int def, String content) {

        final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 有白色背
        window.setContentView(R.layout.dialog_tips);
        window.findViewById(R.id.version_describe).setVisibility(View.GONE);
        TextView tv_content = window.findViewById(R.id.tv_content);
        tv_content.setText(content);
        window.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                if (mGoBack!=null){
                    mGoBack.goBack(def);
                }

            }
        });
        window.findViewById(R.id.tv_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();

            }
        });
    } public void showIsContinueTaskDialog(final Context mContext, final GoBackInteDef.A mGoBack) {

        final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 有白色背
        window.setContentView(R.layout.dialog_tips);
        window.findViewById(R.id.version_describe).setVisibility(View.GONE);
        TextView tv_content = window.findViewById(R.id.tv_content);
        TextView tv_ok = window.findViewById(R.id.tv_ok);
        TextView tv_no = window.findViewById(R.id.tv_no);
        StringBuilder sb=new StringBuilder("上次巡检未完成，是否继续上次巡检");
//        sb.append(  new Gson().toJson(BreakOffBean.getInitSingle().mtvBean));
        sb.append("\n");
        sb.append("巡检类型：");
        sb.append(BreakOffBean.getInitSingle().getTaskType());
        sb.append("\n");
        sb.append("公里数：");
        sb.append(BreakOffBean.getInitSingle().getTaskDis());
        sb.append("\n");
        sb.append("时长：");
        sb.append(BreakOffBean.getInitSingle().getTaskTime());
        tv_content.setText(sb.toString());
        tv_ok.setText("继续巡检");
        tv_no.setText("取消并清除");
        window.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                if (mGoBack!=null){
                    mGoBack.goBack(1);
                }

            }
        });
        window.findViewById(R.id.tv_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BreakOffBean.getInitSingle().init();
                SPUtil.getInstance(mContext).setBooleanValue(SPUtil.ISBREAKOFF,false);
                alertDialog.dismiss();


            }
        });
    }public AlertDialog isUpdataCrashlogDialog(final Context mContext, final GoBackInteDef.A mGoBack) {

        final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 有白色背
        window.setContentView(R.layout.dialog_tips);
        window.findViewById(R.id.version_describe).setVisibility(View.GONE);
        TextView tv_content = window.findViewById(R.id.tv_content);
        tv_content.setMovementMethod(ScrollingMovementMethod.getInstance());
        TextView tv_ok = window.findViewById(R.id.tv_ok);
        TextView tv_no = window.findViewById(R.id.tv_no);
        StringBuilder sb=new StringBuilder("崩溃日志收否上传服务器\n");
      List<String> f= AppTool.getFilesAllName(AppTool.getCrashLogFolder(mContext,""));
        for (int i = 0; i < f.size(); i++) {
            sb.append( f.get(i));
            sb.append("\n");
        }
        tv_content.setText(sb.toString());
        tv_ok.setText("确定上传");
        tv_no.setText("取消");
        window.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                alertDialog.dismiss();
                if (mGoBack!=null){
                    mGoBack.goBack(1);
                }

            }
        });
        window.findViewById(R.id.tv_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                if (mGoBack!=null){
                    mGoBack.goBack(2);
                }


            }
        });
        return alertDialog;
    }
    public void showSoundDialog(final Context mContext) {

        final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 有白色背
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.alpha = 0f;
//        window.setAttributes(lp);
        alertDialog.getWindow().setDimAmount(0f);//核心代码
        window.setContentView(R.layout.dialog_sound);
//        final TextView tv1=window.findViewById(R.id.tv_1);
//        TextView tv2=window.findViewById(R.id.tv_2);
//        tv1 .setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                alertDialog.dismiss();
////if (mGoBack!=null){
////    mGoBack.goBack(GoBackInteDef.setBack(GoBackInteDef.DELEVIDEO));
////}
//
//
//            }
//        });
//        tv2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                alertDialog.dismiss();
////                if (mGoBack!=null){
////                    mGoBack.goBack(GoBackInteDef.setBack(GoBackInteDef.PALYVIDEO));
////                }
//            }
//        });
    }public void showIspalyvoideDialog(final Context mContext, final GoBackInteDef.GoBack mGoBack) {

        final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 有白色背
        window.setContentView(R.layout.dialog_isplayviode);
        final TextView tv1=window.findViewById(R.id.tv_1);
        TextView tv2=window.findViewById(R.id.tv_2);
        tv1 .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
if (mGoBack!=null){
    mGoBack.goBack(GoBackInteDef.setBack(GoBackInteDef.DELEVIDEO));
}


            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                if (mGoBack!=null){
                    mGoBack.goBack(GoBackInteDef.setBack(GoBackInteDef.PALYVIDEO));
                }
            }
        });
    }

    public void showExitDialog(final Context mContext, final int mExit) {

        final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 有白色背
        window.setContentView(R.layout.dialog_tips);
        window.findViewById(R.id.version_describe).setVisibility(View.GONE);
        TextView tv_content = window.findViewById(R.id.tv_content);
        if (mExit == 0) {
            tv_content.setText("确定要切换用户吗?");
        } else {
            tv_content.setText("确定要退出吗?");
        }

        window.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                Exit(mExit);

            }
        });
        window.findViewById(R.id.tv_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    public void Exit(int mExit) {
        if (mExit == 0) {
            SPUtil.getInstance(mContext).remove(SPUtil.USERPWD);
            Intent intent = new Intent(mContext, LoginActivity.class);
            mContext.startActivity(intent);
            if (AppManager.getAppManager() != null) {
                AppManager.getAppManager().finishAllActivity();
            }
        } else {
            if (AppManager.getAppManager() != null) {
                AppManager.getAppManager().AppExit(mContext);
            }
        }
    }


    public ArcGISMapImageLayer getMapImageLayer() {
        mainMapImageLayer = new ArcGISMapImageLayer(AppConfig.GuanWangUrl);
        return mainMapImageLayer;
    }

    public ArcGISMapImageLayer addMapService(final ArcGISMapImageLayer mainMapImageLayer, ArcGISMap mArcGISMapVector, final AddLayerListen mAddLayerListen, final boolean isShowLayer) {
        final List<ArcGISMapImageSublayer> imageSublayerList = new ArrayList<>();

        mainMapImageLayer.loadAsync();
        mainMapImageLayer.addDoneLoadingListener(new Runnable() {
            @Override
            public void run() {
                if (mainMapImageLayer.getLoadStatus() == LoadStatus.LOADED) { if (mAddLayerListen != null) {
//                    imageSublayerList.add((ArcGISMapImageSublayer) mainMapImageLayer.getSublayers().get(3));
                        mAddLayerListen.getImageLayer(mainMapImageLayer);
                    }
                }
            }
        });
        if (!mArcGISMapVector.getBasemap().getBaseLayers().contains(mainMapImageLayer)) {
            mArcGISMapVector.getOperationalLayers().add(mainMapImageLayer);
        }
        return mainMapImageLayer;
    }

    public ArcGISMapImageLayer addMapService(ArcGISMap mArcGISMapVector, final AddLayerListen mAddLayerListen, final boolean isShowLayer) {
        final List<ArcGISMapImageSublayer> imageSublayerList = new ArrayList<>();

        mainMapImageLayer.loadAsync();
        mainMapImageLayer.addDoneLoadingListener(new Runnable() {
            @Override
            public void run() {
                if (mainMapImageLayer.getLoadStatus() == LoadStatus.LOADED) {
                    if (mAddLayerListen != null) {
//                    imageSublayerList.add((ArcGISMapImageSublayer) mainMapImageLayer.getSublayers().get(3));
                        mAddLayerListen.getImageLayer(mainMapImageLayer);
                    }
                }
            }
        });
        if (!mArcGISMapVector.getOperationalLayers().contains(mainMapImageLayer)) {
            mArcGISMapVector.getOperationalLayers().add(mainMapImageLayer);
        }
        return mainMapImageLayer;
    }

    public void FeatureQueryResulPSJ(final QueryParameters query, final AddFeatureQueryResultListen mResultListen) {
        ServiceFeatureTable mTablePSJ = new ServiceFeatureTable(AppConfig.GuanWangUrl + "/" + AppConfig.PSJ);
        final ListenableFuture<FeatureQueryResult> future = mTablePSJ
                .queryFeaturesAsync(query, ServiceFeatureTable.QueryFeatureFields.LOAD_ALL);
        future.addDoneListener(new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FeatureQueryResult result = future.get();
                            ArrayList<AdapterBean> abList = new ArrayList<>();
                            AdapterBean b;
                            for (Feature feature : result) {
                                addGraphicsOverlay(feature, 0);
                                Map<String, Object> attr = feature.getAttributes();
                                b = new AdapterBean();
                                String mk1 = AppTool.getNullStr(attr.get("N_MANHOLE_TYPE") + "");
                                String mv1 = "";
                                if ("1".equals(mk1)) {
                                    mv1 = "雨水井:";
                                } else if ("2".equals(mk1)) {
                                    mv1 = "污水井:";
                                } else if ("3".equals(mk1)) {
                                    mv1 = "合流井:";
                                }
                                String mv2 = AppTool.getNullStr(attr.get("N_MANHOLE_LENGTH") + "");
                                String mv3 = AppTool.getNullStr(attr.get("N_MANHOLE_LENGTH") + "");
                                b.mag1 = mv1 + "φ" + mv2;
                                String mk4 = AppTool.getNullStr(attr.get("N_MANHOLE_STATE") + "");
                                String mv4 = "";
                                if ("0".equals(mk4)) {
                                    mv4 = "雨水口";
                                } else if ("1".equals(mk4)) {
                                    mv4 = "检查井";
                                } else if ("2".equals(mk4)) {
                                    mv4 = "接户井";
                                } else if ("3".equals(mk4)) {
                                    mv4 = "闸阀井";
                                } else if ("4".equals(mk4)) {
                                    mv4 = "溢流井:";
                                } else if ("5".equals(mk4)) {
                                    mv4 = "倒虹井";
                                } else if ("6".equals(mk4)) {
                                    mv4 = "透气井";
                                } else if ("7".equals(mk4)) {
                                    mv4 = "压力井";
                                } else if ("8".equals(mk4)) {
                                    mv4 = "检测井";
                                } else if ("8".equals(mk4)) {
                                    mv4 = "其它井";
                                }
                                b.mag2 = mv4;
                                b.mag3 = AppTool.getNullStr(attr.get("S_MANHOLE_NAME_ROAD") + "");
                                b.url = AppTool.getNullStr(attr.get("S_XTBM") + "");
                                b.mCenterPoint = feature.getGeometry().getExtent().getCenter();
                                abList.add(b);
                            }
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMap(abList, AppConfig.PSJ);
                            }
                        } catch (Exception ep) {
                            LogUtils.e("zzzzzzzou-",ep.toString());
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMap(null, AppConfig.PSJ);
                            }
                        }
                    }
                }).start();
            }
        });
    }

    public void FeatureQueryResulPSJ(final QueryParameters query, final AddFeatureQueryResultListen mResultListen, final String urlid) {
        ServiceFeatureTable mTablePSJ = new ServiceFeatureTable(AppConfig.GuanWangUrl + "/" + urlid);
        final ListenableFuture<FeatureQueryResult> future = mTablePSJ
                .queryFeaturesAsync(query, ServiceFeatureTable.QueryFeatureFields.LOAD_ALL);
        future.addDoneListener(new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FeatureQueryResult result = future.get();
                            ArrayList<AdapterBean> abList = new ArrayList<>();
                            AdapterBean b;
                            for (Feature feature : result) {
                                addGraphicsOverlay(feature, 0);
                                Map<String, Object> attr = feature.getAttributes();
                                b = new AdapterBean();
                                String mk1 = AppTool.getNullStr(attr.get("N_MANHOLE_TYPE") + "");
                                String mv1 = "";
                                if ("1".equals(mk1)) {
                                    mv1 = "雨水井:";
                                } else if ("2".equals(mk1)) {
                                    mv1 = "污水井:";
                                } else if ("3".equals(mk1)) {
                                    mv1 = "合流井:";
                                }
                                String mv2 = AppTool.getNullStr(attr.get("N_MANHOLE_LENGTH") + "");
                                String mv3 = AppTool.getNullStr(attr.get("N_MANHOLE_LENGTH") + "");
                                b.index = Integer.parseInt(urlid);
                                b.title = mv1.replace(":","")+"@";
                                b.mag1 = mv1 + "φ" + mv2;
                                b.type = "jinggai";
                                String jingai_caizhi_type = AppTool.getNullStr(attr.get("N_MANHOLE_MATERIAL") + "");
                                String jingai_caizhi = "";
                                if("1".equals(jingai_caizhi_type)){
                                    jingai_caizhi = "水泥";
                                }else if("2".equals(jingai_caizhi_type)){
                                    jingai_caizhi = "铸铁";
                                }else if("3".equals(jingai_caizhi_type)){
                                    jingai_caizhi = "复合材料";
                                }else if("4".equals(jingai_caizhi_type)||"-1".equals(jingai_caizhi_type)){
                                    jingai_caizhi = "其他";
                                }else {
                                    jingai_caizhi = "其他";
                                }
                                b.jingai_caizhi_type = "井盖制作材料："+jingai_caizhi;
                                b.guandao_caizhi_type = null;
                                String mk4 = AppTool.getNullStr(attr.get("N_MANHOLE_STATE") + "");
                                String mv4 = "";
                                if ("0".equals(mk4)) {
                                    mv4 = "雨水口";
                                } else if ("1".equals(mk4)) {
                                    mv4 = "检查井";
                                } else if ("2".equals(mk4)) {
                                    mv4 = "接户井";
                                } else if ("3".equals(mk4)) {
                                    mv4 = "闸阀井";
                                } else if ("4".equals(mk4)) {
                                    mv4 = "溢流井:";
                                } else if ("5".equals(mk4)) {
                                    mv4 = "倒虹井";
                                } else if ("6".equals(mk4)) {
                                    mv4 = "透气井";
                                } else if ("7".equals(mk4)) {
                                    mv4 = "压力井";
                                } else if ("8".equals(mk4)) {
                                    mv4 = "检测井";
                                } else if ("9".equals(mk4)) {
                                    mv4 = "其它井";
                                }


                                String mk5 = AppTool.getNullStr(attr.get("N_MANHOLE_GRADE") + "");
                                String mv5 = "";
                                if ("1".equals(mk5)) {
                                    mv5 ="主井";
                                }else if ("2".equals(mk5)){
                                    mv5 ="附井（接户井）";
                                }else if ("3".equals(mk5)){
                                    mv5 ="附井（过度井）";
                                }else if("4".equals(mk5)){
                                    mv5 = "附井（其它）";
                                }else if ("5".equals(mk5)){
                                    mv5 = "附井（公共弄堂）";
                                }else if ("6".equals(mk5)){
                                    mv5 ="附井(非小区管理)";
                                }else{
                                    mv5 = "其他井";
                                }
                                b.mag2 = mv4+"-"+mv5;
                                b.mag3 = AppTool.getNullStr(attr.get("S_MANHOLE_NAME_ROAD") + "");
                                b.url = AppTool.getNullStr(attr.get("S_XTBM") + "");
                                b.urlJC = AppTool.getNullStr(attr.get("S_MANHOLE_ID") + "");
                                b.OBJECTID = Integer.parseInt(attr.get("OBJECTID")+"");
                                b.mCenterPoint = feature.getGeometry().getExtent().getCenter();
                                abList.add(b);
                            }
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMap(abList, Integer.parseInt(urlid));
                            }
                        } catch (Exception ep) {
                            LogUtils.e("zzzzzzzou-",ep.toString());
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMap(null, Integer.parseInt(urlid));
                            }
                        }
                    }
                }).start();
            }
        });
    }

    public void FeatureQueryResulPSGDYS(QueryParameters query, final AddFeatureQueryResultListen mResultListen) {
        ServiceFeatureTable mTablePSGD = new ServiceFeatureTable(AppConfig.GuanWangUrl + "/" + AppConfig.PSGD);
        final ListenableFuture<FeatureQueryResult> future = mTablePSGD
                .queryFeaturesAsync(query, ServiceFeatureTable.QueryFeatureFields.LOAD_ALL);
        future.addDoneListener(new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FeatureQueryResult result = future.get();
                            ArrayList<AdapterBean> abList = new ArrayList<>();
                            AdapterBean b;
                            int count = 0;
                            for (Feature feature : result) {
                                Map<String, Object> mQuerryString = feature.getAttributes();
                                addGraphicsOverlay(feature, 1);
//                                addGraphicsOverlay(feature, 1, count++);
                                Map<String, Object> attr = feature.getAttributes();
                                b = new AdapterBean();

                                String mk1 = AppTool.getNullStr(attr.get("N_DRAI_PIPE_TYPE") + "");
                                String mv1 = "";
                                if ("1".equals(mk1)) {
                                    mv1 = "雨水管:";
                                } else if ("2".equals(mk1)) {
                                    mv1 = "污水管:";
                                } else if ("3".equals(mk1)) {
                                    mv1 = "合流管:";
                                } else if ("9".equals(mk1)) {
                                    mv1 = "其他管:";
                                }
                                String mv2 = AppTool.getNullStr(attr.get("N_DRAI_PIPE_D1") + "");
                                String mv3 = AppTool.getNullStr(attr.get("N_DRAI_PIPE_LENGTH") + "");
                                b.index = AppConfig.PSGD;
                                b.title = mv1.replace(":","")+"#";
                                b.mag1 = mv1 + "φ" + mv2 + "-" + mv3;
                                String mk4 = AppTool.getNullStr(attr.get("N_DRAI_PIPE_GRADE") + "");
                                String mv4 = "";
                                if ("1".equals(mk4)) {
                                    mv4 = "总干管";
                                } else if ("2".equals(mk4)) {
                                    mv4 = "干管（截流管）";
                                } else if ("3".equals(mk4)) {
                                    mv4 = "主管";
                                } else if ("4".equals(mk4)) {
                                    mv4 = "连管:";
                                } else if ("5".equals(mk4)) {
                                    mv4 = "接户管";
                                } else if ("6".equals(mk4)) {
                                    mv4 = "街坊管";
                                } else if ("9".equals(mk4)) {
                                    mv4 = "其它管";
                                }
                                String mk5 = AppTool.getNullStr(attr.get("N_DRAI_PIPE_STYLE") + "");
                                String mv5 = "";
                                if ("1".equals(mk5)) {
                                    mv5 = "圆形";
                                } else if ("2".equals(mk5)) {
                                    mv5 = "蛋形";
                                } else if ("3".equals(mk5)) {
                                    mv5 = "矩形";
                                } else if ("9".equals(mk5)) {
                                    mv5 = "其它";
                                }
                                b.mag2 = mv4 + "-" + mv5;
                                b.type = "guandao";

                                String guandao_caizhi_type = AppTool.getNullStr(attr.get("N_DRAI_PIPE_MATERIAL") + "");
                                String guandao_caizhi = "";
                                if("1".equals(guandao_caizhi_type)){
                                    guandao_caizhi = "砼";
                                }else if("2".equals(guandao_caizhi_type)){
                                    guandao_caizhi = "钢砼";
                                }else if("3".equals(guandao_caizhi_type)){
                                    guandao_caizhi = "砖石";
                                }else if("4".equals(guandao_caizhi_type)){
                                    guandao_caizhi = "塑料";
                                }else if("9".equals(guandao_caizhi_type)){
                                    guandao_caizhi = "其他";
                                }else {
                                    guandao_caizhi = "其他";
                                }
                                b.guandao_caizhi_type = "管道制作材料："+guandao_caizhi;
                                b.jingai_caizhi_type = null;
                                String mv6, mv7, mv8;
                                mv6 = AppTool.getNullStr(attr.get("S_DRAI_PIPE_NAME_ROAD") + "");
                                mv7 = AppTool.getNullStr(attr.get("S_DRAI_PIPE_BROAD_NAME") + "");
                                mv8 = AppTool.getNullStr(attr.get("S_DRAI_PIPE_EROAD_NAME") + "");
                                b.mag3 = mv6 + "(" + mv7 + "-" + mv8 + ")";
                                b.url = AppTool.getNullStr(attr.get("S_XTBM") + "");
                                b.OBJECTID = Integer.parseInt(attr.get("OBJECTID")+"");
                                b.mCenterPoint = feature.getGeometry().getExtent().getCenter();
                                abList.add(b);

                            }
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMap(abList, AppConfig.PSGD);
                            }
                        } catch (Exception ep) {
                            LogUtils.e("zzzzzzzou-",ep.toString());
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMap(null, AppConfig.PSGD);
                            }
                        }
                    }
                }).start();

            }
        });
    }

    public void FeatureQueryResulPSGDWS(QueryParameters query, final AddFeatureQueryResultListen mResultListen) {
        ServiceFeatureTable mTablePSGD = new ServiceFeatureTable(AppConfig.GuanWangUrl + "/" + AppConfig.PSGDWS);
        final ListenableFuture<FeatureQueryResult> future = mTablePSGD
                .queryFeaturesAsync(query, ServiceFeatureTable.QueryFeatureFields.LOAD_ALL);
        future.addDoneListener(new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FeatureQueryResult result = future.get();
                            ArrayList<AdapterBean> abList = new ArrayList<>();
                            AdapterBean b;
                            int count = 0;
                            for (Feature feature : result) {
                                addGraphicsOverlay(feature, 1);
//                                addGraphicsOverlay(feature, 1, count++);
                                Map<String, Object> attr = feature.getAttributes();
                                b = new AdapterBean();

                                String mk1 = AppTool.getNullStr(attr.get("N_DRAI_PIPE_TYPE") + "");
                                String mv1 = "";
                                if ("1".equals(mk1)) {
                                    mv1 = "雨水管:";
                                } else if ("2".equals(mk1)) {
                                    mv1 = "污水管:";
                                } else if ("3".equals(mk1)) {
                                    mv1 = "合流管:";
                                } else if ("9".equals(mk1)) {
                                    mv1 = "其他管:";
                                }
                                String mv2 = AppTool.getNullStr(attr.get("N_DRAI_PIPE_D1") + "");
                                String mv3 = AppTool.getNullStr(attr.get("N_DRAI_PIPE_LENGTH") + "");
                                b.index = AppConfig.PSGDWS;
                                b.title = mv1.replace(":","")+"#";
                                b.mag1 = mv1 + "φ" + mv2 + "-" + mv3;
                                String mk4 = AppTool.getNullStr(attr.get("N_DRAI_PIPE_GRADE") + "");
                                String mv4 = "";
                                if ("1".equals(mk4)) {
                                    mv4 = "总干管";
                                } else if ("2".equals(mk4)) {
                                    mv4 = "干管（截流管）";
                                } else if ("3".equals(mk4)) {
                                    mv4 = "主管";
                                } else if ("4".equals(mk4)) {
                                    mv4 = "连管:";
                                } else if ("5".equals(mk4)) {
                                    mv4 = "接户管";
                                } else if ("6".equals(mk4)) {
                                    mv4 = "街坊管";
                                } else if ("9".equals(mk4)) {
                                    mv4 = "其它管";
                                }
                                String mk5 = AppTool.getNullStr(attr.get("N_DRAI_PIPE_STYLE") + "");
                                String mv5 = "";
                                if ("1".equals(mk5)) {
                                    mv5 = "圆形";
                                } else if ("2".equals(mk5)) {
                                    mv5 = "蛋形";
                                } else if ("3".equals(mk5)) {
                                    mv5 = "矩形";
                                } else if ("9".equals(mk5)) {
                                    mv5 = "其它";
                                }
                                b.mag2 = mv4 + "-" + mv5;
                                String mv6, mv7, mv8;
                                mv6 = AppTool.getNullStr(attr.get("S_DRAI_PIPE_NAME_ROAD") + "");
                                mv7 = AppTool.getNullStr(attr.get("S_DRAI_PIPE_BROAD_NAME") + "");
                                mv8 = AppTool.getNullStr(attr.get("S_DRAI_PIPE_EROAD_NAME") + "");
                                b.mag3 = mv6 + "(" + mv7 + "-" + mv8 + ")";
                                b.url = AppTool.getNullStr(attr.get("S_XTBM") + "");
                                b.OBJECTID   = Integer.parseInt(attr.get("OBJECTID")+"");
                                b.mCenterPoint = feature.getGeometry().getExtent().getCenter();
                                abList.add(b);

                            }
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMap(abList, AppConfig.PSGD);
                            }
                        } catch (Exception ep) {
                            LogUtils.e("zzzzzzzou-",ep.toString());
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMap(null, AppConfig.PSGD);
                            }
                        }
                    }
                }).start();

            }
        });
    }

    public void FeatureQueryResulPFK(QueryParameters query, final AddFeatureQueryResultListen mResultListen) {

        ServiceFeatureTable mTablePFK = new ServiceFeatureTable(AppConfig.GuanWangUrl + "/" + AppConfig.PFK);
        final ListenableFuture<FeatureQueryResult> future = mTablePFK
                .queryFeaturesAsync(query, ServiceFeatureTable.QueryFeatureFields.LOAD_ALL);
        future.addDoneListener(new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FeatureQueryResult result = future.get();

                            ArrayList<AdapterBean> abList = new ArrayList<>();
                            Map<String, Object> attr;
                            AdapterBean b;
                            for (Feature feature : result) {
                                addGraphicsOverlay(feature, 0);
                                b = new AdapterBean();
                                attr = feature.getAttributes();
                                String mk1 = AppTool.getNullStr(attr.get("N_MANHOLE_TYPE") + "");
                                String mv1 = "";
                                if ("1".equals(mk1)) {
                                    mv1 = "雨水井:";
                                } else if ("2".equals(mk1)) {
                                    mv1 = "污水井:";
                                } else if ("3".equals(mk1)) {
                                    mv1 = "合流井:";
                                }
                                String mv2 = AppTool.getNullStr(attr.get("N_DRAI_PIPE_D1") + "");
                                String mv3 = AppTool.getNullStr(attr.get("N_MANHOLE_LENGTH") + "");
                                b.title = "排放口";
                                b.mag1 = mv1 + "φ" + mv2 + "-" + mv3;
                                String mk4 = AppTool.getNullStr(attr.get("N_MANHOLE_STATE") + "");
                                String mv4 = "";
                                if ("0".equals(mk4)) {
                                    mv4 = "雨水口";
                                } else if ("1".equals(mk4)) {
                                    mv4 = "检查井";
                                } else if ("2".equals(mk4)) {
                                    mv4 = "接户井";
                                } else if ("3".equals(mk4)) {
                                    mv4 = "闸阀井";
                                } else if ("4".equals(mk4)) {
                                    mv4 = "溢流井:";
                                } else if ("5".equals(mk4)) {
                                    mv4 = "倒虹井";
                                } else if ("6".equals(mk4)) {
                                    mv4 = "透气井";
                                } else if ("7".equals(mk4)) {
                                    mv4 = "压力井";
                                } else if ("8".equals(mk4)) {
                                    mv4 = "检测井";
                                } else if ("9".equals(mk4)) {
                                    mv4 = "其它井";
                                }
                                String mk5 = AppTool.getNullStr(attr.get("N_MANHOLE_GRADE") + "");
                                String mv5 = "";
                                if ("1".equals(mk5)) {
                                    mv5 = "主井";
                                } else if ("2".equals(mk5)) {
                                    mv5 = "附井（接户井）";
                                } else if ("3".equals(mk5)) {
                                    mv5 = "附井（过度井）";
                                } else if ("4".equals(mk5)) {
                                    mv5 = "附井（其它）";
                                } else if ("5".equals(mk5)) {
                                    mv5 = "附井（公共弄堂）";
                                } else if ("6".equals(mk5)) {
                                    mv5 = "附井（非小区管理的）";
                                }
                                b.mag2 = mv4+"-"+mv5;
                                b.mag3 = AppTool.getNullStr(attr.get("S_MANHOLE_NAME_ROAD") + "");
                                b.url = AppTool.getNullStr(attr.get("S_XTBM") + "");
                                b.OBJECTID  = Integer.parseInt(attr.get("OBJECTID")+"");
                                b.mCenterPoint = feature.getGeometry().getExtent().getCenter();
                                abList.add(b);
                            }
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMap(abList, AppConfig.PFK);
                            }
                        } catch (Exception ep) {
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMap(null, AppConfig.PFK);
                            }
                        }
                    }
                }).start();

            }
        });
    }

    public void FeatureQueryResulPSBZ(QueryParameters query, final AddFeatureQueryResultListen mResultListen) {
        ServiceFeatureTable mTablePSBZ = new ServiceFeatureTable(AppConfig.GuanWangUrl + "/" + AppConfig.PSBZ);
        final ListenableFuture<FeatureQueryResult> future = mTablePSBZ
                .queryFeaturesAsync(query, ServiceFeatureTable.QueryFeatureFields.LOAD_ALL);
        future.addDoneListener(new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FeatureQueryResult result = future.get();
                            ArrayList<AdapterBean> abList = new ArrayList<>();
                            Map<String, Object> attr;
                            AdapterBean b;
                            for (Feature feature : result) {
                                addGraphicsOverlay(feature, 0);
                                b = new AdapterBean();
                                attr = feature.getAttributes();
                                b.title = "排水泵站";
                                b.mag1 = AppTool.getNullStr(attr.get("S_DRAI_PUMP_NAME") + "");
                                String mk1 = AppTool.getNullStr(attr.get("N_DRAI_PUMP_TYPE") + "");
                                String mk2 = AppTool.getNullStr(attr.get("N_DRAI_PUMP_TYPE_FEAT") + "");
                                String mv1 = "";
                                String mv2 = "";
                                if ("1".equals(mk1)) {
                                    mv1 = "雨水";
                                } else if ("2".equals(mk1)) {
                                    mv1 = "污水";
                                } else if ("3".equals(mk1)) {
                                    mv1 = "合建";
                                } else if ("9".equals(mk1)) {
                                    mv1 = "其它";
                                }
//                                分流污水，6-合流截流，7-干线输送，8-分流，9-合流，10-其他
                                if ("1".equals(mk2)) {
                                    mv2 = "分流雨水";
                                } else if ("2".equals(mk2)) {
                                    mv2 = "合流防汛";
                                } else if ("3".equals(mk2)) {
                                    mv2 = "立交";
                                } else if ("4".equals(mk2)) {
                                    mv2 = "闸泵";
                                } else if ("5".equals(mk2)) {
                                    mv1 = "分流污水";
                                } else if ("6".equals(mk2)) {
                                    mv1 = "合流截流";
                                } else if ("7".equals(mk2)) {
                                    mv1 = "干线输送";
                                } else if ("8".equals(mk2)) {
                                    mv1 = "分流";
                                } else if ("9".equals(mk2)) {
                                    mv1 = "合流";
                                } else if ("10".equals(mk2)) {
                                    mv1 = "其他";
                                }
                                b.mag2 = mv1 + "-" + mv2;
                                b.mag3 = AppTool.getNullStr(attr.get("S_DRAI_PUMP_ADD") + "");
                                b.url = AppTool.getNullStr(feature.getAttributes().get("S_XTBM") + "");        b.OBJECTID  = Integer.parseInt(attr.get("OBJECTID")+"");
                                b.mCenterPoint = feature.getGeometry().getExtent().getCenter();
                                abList.add(b);
                            }
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMap(abList, AppConfig.PSBZ);
                            }
                        } catch (Exception ep) {
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMap(null, AppConfig.PSBZ);
                            }
                        }
                    }
                }).start();

            }
        });
    }

    public void FeatureQueryResulWSCLC(QueryParameters query, final AddFeatureQueryResultListen mResultListen) {

        ServiceFeatureTable mTableWSCLC = new ServiceFeatureTable(AppConfig.GuanWangUrl + "/" + AppConfig.WSCLC);
        final ListenableFuture<FeatureQueryResult> future = mTableWSCLC
                .queryFeaturesAsync(query, ServiceFeatureTable.QueryFeatureFields.LOAD_ALL);
        future.addDoneListener(new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FeatureQueryResult result = future.get();
                            ArrayList<AdapterBean> abList = new ArrayList<>();
                            AdapterBean b;
                            for (Feature feature : result) {
                                addGraphicsOverlay(feature, 0);
                                b = new AdapterBean();
                                b.title = "污水处理厂";
                                b.mag1 = AppTool.getNullStr(feature.getAttributes().get("S_FACT_NAME") + "");
//                                String mv1 = AppTool.getNullStr(feature.getAttributes().get("N_FACT_CAP_DSN") + "");
//                                String mv2 = AppTool.getNullStr(feature.getAttributes().get("S_FACT_METHOD_TREAT") + "");
//                                b.mag2 = mv1 + "-" + mv2;
                                b.mag2 = "";
                                b.mag3 = AppTool.getNullStr(feature.getAttributes().get("S_FACT_ADD") + "");
                                b.url = AppTool.getNullStr(feature.getAttributes().get("S_FACT_ID") + "");
                                b.OBJECTID  = Integer.parseInt(feature.getAttributes().get("OBJECTID")+"");
                                b.mCenterPoint = feature.getGeometry().getExtent().getCenter();
                                abList.add(b);
                            }
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMap(abList, AppConfig.WSCLC);
                            }
                        } catch (Exception ep) {
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMap(null, AppConfig.WSCLC);
                            }
                        }
                    }
                }).start();

            }
        });

    }

    public void FeatureQueryResulPSH(QueryParameters query, final AddFeatureQueryResultListen mResultListen) {

        final ServiceFeatureTable mTableZDPFK = new ServiceFeatureTable(AppConfig.GuanWangUrl + "/" + 67);
        final ListenableFuture<FeatureQueryResult> future = mTableZDPFK
                .queryFeaturesAsync(query, ServiceFeatureTable.QueryFeatureFields.LOAD_ALL);
        future.addDoneListener(new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            FeatureQueryResult result = future.get();
                            ArrayList<AdapterBean> abList = new ArrayList<>();
                            AdapterBean b;
                            for (Feature feature : result) {
                                b = new AdapterBean();
                                addGraphicsOverlay(feature, 0);
//                        Map<String, Object> mQuerryString = feature.getAttributes();
//                        String str= (String) mQuerryString.get("s_name");
                                b.title = "排水户";
                                b.mag1 = AppTool.getNullStr((String) feature.getAttributes().get("S_NAME"));
                                String mv1 = AppTool.getNullStr((String) feature.getAttributes().get("S_TOWN"));
                                String mv2 = AppTool.getNullStr((String) feature.getAttributes().get("S_TYPE"));
                                b.mag2 = mv1 + "-" + mv2;

//                                String mv3 = AppTool.getNullStr((String) feature.getAttributes().get("s_town"));
//                                String mv4 = AppTool.getNullStr((String) feature.getAttributes().get("s_address"));
//                                b.mag3 = mv3 + "-" + mv4;
                                b.mag3 = AppTool.getNullStr((String) feature.getAttributes().get("S_ADDRESS"));
                                b.url = AppTool.getNullStr(feature.getAttributes().get("QPPSH_TABLE_ID") + "");
                                b.OBJECTID  = Integer.parseInt(feature.getAttributes().get("OBJECTID")+"");
                                b.mCenterPoint = feature.getGeometry().getExtent().getCenter();
                                abList.add(b);
                            }
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMap(abList, AppConfig.PSH);
                            }
                        } catch (Exception ep) {
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMap(null, AppConfig.PSH);
                            }
                        }
                    }
                }).start();

            }
        });
    }

    public void FeatureQueryResulZDPFK(QueryParameters query, final AddFeatureQueryResultListen mResultListen) {
        ServiceFeatureTable mTablePSH = new ServiceFeatureTable(AppConfig.GuanWangUrl + "/" + AppConfig.ZDPFK);
        final ListenableFuture<FeatureQueryResult> future = mTablePSH
                .queryFeaturesAsync(query, ServiceFeatureTable.QueryFeatureFields.LOAD_ALL);
        future.addDoneListener(new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FeatureQueryResult result = future.get();

                            ArrayList<AdapterBean> abList = new ArrayList<>();
                            AdapterBean b;
                            for (Feature feature : result) {
                                addGraphicsOverlay(feature, 0);
                                b = new AdapterBean();
                                b.title = "重点排放口";
                                b.mag1 = AppTool.getNullStr(feature.getAttributes().get("S_OutletNAME") + "");
//                                String mv1 = AppTool.getNullStr(feature.getAttributes().get("S_SYS_NAME") + "");
//                                String mv2 = AppTool.getNullStr(feature.getAttributes().get("S_pairufangxiang") + "");
//                                b.mag2 = mv1 + "-" + mv2;
                                b.mag3 = AppTool.getNullStr(feature.getAttributes().get("S_ManageUnit") + "");
                                b.mag2 = AppTool.getNullStr(feature.getAttributes().get("S_pairufangxiang") + "");
                                b.url = AppTool.getNullStr(feature.getAttributes().get("S_OUTLETID") + "");
                                b.OBJECTID   = Integer.parseInt(feature.getAttributes().get("OBJECTID")+"");
                                b.mCenterPoint = feature.getGeometry().getExtent().getCenter();
                                abList.add(b);
                            }
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMap(abList, AppConfig.ZDPFK);
                            }
                        } catch (Exception ep) {
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMap(null, AppConfig.ZDPFK);
                            }
                        }
                    }
                }).start();

            }
        });
    }  public void FeatureQueryJC(QueryParameters query, final InterfListen.B ILb, int url, final String urls) {
        ServiceFeatureTable mTablePSH = new ServiceFeatureTable(AppConfig.JCUrl + "/" + url);
        final ListenableFuture<FeatureQueryResult> future = mTablePSH
                .queryFeaturesAsync(query, ServiceFeatureTable.QueryFeatureFields.LOAD_ALL);
        future.addDoneListener(new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            FeatureQueryResult result = future.get();
                            ArrayList<AdapterBean> abList = new ArrayList<>();
                            AdapterBean b;
                            for (Feature feature : result) {

//                                addGraphicsOverlay(feature, 0);
                                b = new AdapterBean();
                                b.mag1 = AppTool.getNullStr(feature.getAttributes().get(urls) + "");
                                abList.add(b);
                                if (ILb != null) {
                                    ILb.bA( new Graphic(feature.getGeometry(), getCSymbol()),abList ,AppConfig.JCsw,urls,0);//Symbol0代表点，1代表线
                                }
                                return;
                            }

                            if (abList.size()==0){

                                if (ILb != null) {
                                    ILb.bA( null ,null ,10000 ,"没有查询到数据",10000);//Symbol0代表点，1代表线,10000无效
                                }

                            }
                        } catch (Exception ep) {

                            if (ILb != null) {
                                ILb.bA( null ,null ,10000 ,ep.getMessage(),10000);//Symbol0代表点，1代表线,10000无效
                            }
                        }
                    }
                }).start();

            }
        });
    }public void FeatureQueryDetail(QueryParameters query, final InterfListen.B ILb, int url, final String urls) {
        ServiceFeatureTable mTablePSH = new ServiceFeatureTable(AppConfig.JCUrl + "/" + url);
        final ListenableFuture<FeatureQueryResult> future = mTablePSH
                .queryFeaturesAsync(query, ServiceFeatureTable.QueryFeatureFields.LOAD_ALL);
        future.addDoneListener(new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            FeatureQueryResult result = future.get();
                            ArrayList<AdapterBean> abList = new ArrayList<>();
                            AdapterBean b;
                            for (Feature feature : result) {

//                                addGraphicsOverlay(feature, 0);
                                b = new AdapterBean();
                                b.mag1 = AppTool.getNullStr(feature.getAttributes().get(urls) + "");
                                abList.add(b);
                                if (ILb != null) {
                                    ILb.bA( new Graphic(feature.getGeometry(), getCSymbol()),abList ,AppConfig.JCsw,urls,0);//Symbol0代表点，1代表线
                                }
                                return;
                            }

                            if (abList.size()==0){

                                if (ILb != null) {
                                    ILb.bA( null ,null ,10000 ,"没有查询到数据",10000);//Symbol0代表点，1代表线,10000无效
                                }

                            }
                        } catch (Exception ep) {

                            if (ILb != null) {
                                ILb.bA( null ,null ,10000 ,ep.getMessage(),10000);//Symbol0代表点，1代表线,10000无效
                            }
                        }
                    }
                }).start();

            }
        });
    }

    public SimpleMarkerSymbol getCSymbol() {
        if (mCircleSymbol == null) {
            mCircleSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, 0xFFFF0000, 16);
        }
        return mCircleSymbol;
    }

    public SimpleLineSymbol getLSymbol() {
        if (mLineSymbol == null) {
            mLineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.GREEN, 5);
        }
        return mLineSymbol;
    }

    public void addGraphicsOverlay(Feature feature, int Symbol) {
        Graphic sublayerGraphic = null;
        if (Symbol == 0) {
            sublayerGraphic = new Graphic(feature.getGeometry(), getCSymbol());
            if (ui == 0) {
                MainMapXJActivity.onClickgraphicsOverlayPoint.getGraphics().add(sublayerGraphic);
            } else if (ui == 1) {
                MainMapYHActivity mActivity= (MainMapYHActivity) mContext;
                mActivity.mAAtt.initB().onClickgraphicsOverlayPoint.getGraphics().add(sublayerGraphic);
            }else if (ui == 2) {
                FourthFragment.onClickgraphicsOverlayPoint.getGraphics().add(sublayerGraphic);
            }
        } else if (Symbol == 1) {
            sublayerGraphic = new Graphic(feature.getGeometry(), getLSymbol());
            if (ui == 0) {
                MainMapXJActivity.onClickgraphicsOverlayLine.getGraphics().add(sublayerGraphic);
            } else if (ui == 1) {
                MainMapYHActivity mActivity= (MainMapYHActivity) mContext;
                mActivity.mAAtt.initB().onClickgraphicsOverlayLine.getGraphics().add(sublayerGraphic);
            }else if (ui == 2) {
                FourthFragment.onClickgraphicsOverlayLine.getGraphics().add(sublayerGraphic);
            }

        }


    }

    public void addGraphicsOverlay(Feature feature, int Symbol, int i) {
        addGraphicsOverlay(feature, Symbol);

        Point p = feature.getGeometry().getExtent().getCenter();
        Drawable drawable = mContext.getResources().getDrawable(R.mipmap.map_jing_yu);
        PictureMarkerSymbol pictureMarkerSymbol = new PictureMarkerSymbol((BitmapDrawable) drawable);
        pictureMarkerSymbol.setOffsetX(0);
        pictureMarkerSymbol.setOffsetY(15);
        Graphic graphicimg = new Graphic(p, pictureMarkerSymbol);
        FourthFragment.onClickMarkergraphicsOverlay.getGraphics().add(graphicimg);
        TextSymbol textSymbol = new TextSymbol();
        textSymbol.setText(i + 1 + "");
        textSymbol.setSize(13);
        textSymbol.setColor(Color.BLACK);
//        textSymbol.setOffsetX(-10);
        textSymbol.setOffsetY(22);
        final Graphic graphictext = new Graphic(p, textSymbol);
        FourthFragment.onClickTextgraphicsOverlay.getGraphics().add(graphictext);
//        new Handler().postDelayed(new Runnable() {
//            public void run() {
////                FourthFragment.onClickgraphicsOverlay.getGraphics().add(graphictext);
//            }
//        }, 2000);
    }

    public void addGraphicsOverlayPeople(AdminPeopleBean mListBean) {
        List<AdminPeopleBean.TPersonCollectionBean.TPersonBean> listb = mListBean.tPersonCollection.tPerson;
        if (pictureMarkerSymbolXJr == null) {
            pictureMarkerSymbolXJr = getpictureMarkerSymbol(1);
        }
        if (pictureMarkerSymbolXJy == null) {
            pictureMarkerSymbolXJy = getpictureMarkerSymbol(2);
        }
        if (pictureMarkerSymbolYH == null) {
            pictureMarkerSymbolYH = getpictureMarkerSymbol(3);
        }
        if (pictureMarkerSymbolOfflineX == null) {
            pictureMarkerSymbolOfflineX = getpictureMarkerSymbol(4);
        }    if (pictureMarkerSymbolOfflineY == null) {
            pictureMarkerSymbolOfflineY = getpictureMarkerSymbol(5);
        }
        for (int i = 0; i < listb.size(); i++) {
            AdminPeopleBean.TPersonCollectionBean.TPersonBean b = listb.get(i);
            if (b!=null&!AppTool.isNull(b.NX)&&!AppTool.isNull(b.NY)){
            Point p = new Point(Double.parseDouble(b.NX), Double.parseDouble(b.NY));
            Map<String, Object> m = new HashMap<>();
            m.put("markerpeople", new Gson().toJson(listb.get(i)));
//            if (i%2==0){
//                b.STATUS="zai"  ;
//            }
            if ("离线".equals(b.STATUS)) {

                if ("W1007000002".equals(b.STASKTYPE)) {
                    Graphic graphicimg = new Graphic(p, m, pictureMarkerSymbolOfflineY);
                    FourthFragment.onClicgraphicsOverlayYH.getGraphics().add(graphicimg);
                } else {
                    Graphic graphicimg = new Graphic(p, m, pictureMarkerSymbolOfflineX);
                    FourthFragment.onClicgraphicsOverlayXJ.getGraphics().add(graphicimg);
                }
            } else {
                if ("W1007000001".equals(b.STASKTYPE)) {
                    Graphic graphicimg = new Graphic(p, m, pictureMarkerSymbolXJr);
                    FourthFragment.onClicgraphicsOverlayXJ.getGraphics().add(graphicimg);
                } else if ("W1007000003".equals(b.STASKTYPE)) {
                    Graphic graphicimg = new Graphic(p, m, pictureMarkerSymbolXJy);
                    FourthFragment.onClicgraphicsOverlayXJ.getGraphics().add(graphicimg);
                } else if ("W1007000002".equals(b.STASKTYPE)) {
                    Graphic graphicimg = new Graphic(p, m, pictureMarkerSymbolYH);
                    FourthFragment.onClicgraphicsOverlayYH.getGraphics().add(graphicimg);
                }else {
                    if ("巡查人员".equals(b.SMANNAMEABBREVIATION)){
                        Graphic graphicimg = new Graphic(p, m, pictureMarkerSymbolXJr);
                        FourthFragment.onClicgraphicsOverlayXJ.getGraphics().add(graphicimg);
                    }else {
                        Graphic graphicimg = new Graphic(p, m, pictureMarkerSymbolYH);
                        FourthFragment.onClicgraphicsOverlayYH.getGraphics().add(graphicimg);
                    }
                }
            }
            }
        }
    }

    public void addGraphicsOverlayPeople2(AdminPersonBean2 mListBean) {
        List<AdminPersonBean2.DataBean> listb = mListBean.getData();
        if (pictureMarkerSymbolXJr == null) {
            pictureMarkerSymbolXJr = getpictureMarkerSymbol(1);
        }
        if (pictureMarkerSymbolXJy == null) {
            pictureMarkerSymbolXJy = getpictureMarkerSymbol(2);
        }
        if (pictureMarkerSymbolYH == null) {
            pictureMarkerSymbolYH = getpictureMarkerSymbol(3);
        }
        if (pictureMarkerSymbolOfflineX == null) {
            pictureMarkerSymbolOfflineX = getpictureMarkerSymbol(4);
        }    if (pictureMarkerSymbolOfflineY == null) {
            pictureMarkerSymbolOfflineY = getpictureMarkerSymbol(5);
        }
        for (int i = 0; i < listb.size(); i++) {
            AdminPersonBean2.DataBean b = mListBean.getData().get(i);
            if (b!=null&!AppTool.isNull(b.getN_X()+"")&&!AppTool.isNull(b.getN_Y()+"")){
                if (b.getN_X() == 0.0||b.getN_Y() == 0.0){

                }else {
                    Point p = new Point(b.getN_X(), b.getN_Y());
                    Map<String, Object> m = new HashMap<>();
                    m.put("markerpeople", new Gson().toJson(listb.get(i)));
                    if (AppTool.isNull(b.getS_TASK_TYPE())){
                        if ("巡查人员".equals(b.getS_MAN_NAME_ABBREVIATION())){
                            b.setS_TASK_TYPE("W1007000001");
                    }else {
                            b.setS_TASK_TYPE("W1007000002");
                        }
                    }
                    if ("离线".equals(b.getSTATUS())) {
                        if ("W1007000002".equals(b.getS_TASK_TYPE())) {
                            Graphic graphicimg = new Graphic(p, m, pictureMarkerSymbolOfflineY);
                            FourthFragment.onClicgraphicsOverlayYH.getGraphics().add(graphicimg);
                        } else {
                            Graphic graphicimg = new Graphic(p, m, pictureMarkerSymbolOfflineX);
                            FourthFragment.onClicgraphicsOverlayXJ.getGraphics().add(graphicimg);
                        }
                    } else {
                        if ("W1007000002".equals(b.getS_TASK_TYPE())) {
                            Graphic graphicimg = new Graphic(p, m, pictureMarkerSymbolYH);
                            FourthFragment.onClicgraphicsOverlayYH.getGraphics().add(graphicimg);
                        }else if ("W1007000001".equals(b.getS_TASK_TYPE())) {
                                    Graphic graphicimg = new Graphic(p, m, pictureMarkerSymbolXJr);
                                    FourthFragment.onClicgraphicsOverlayXJ.getGraphics().add(graphicimg);
                                } else if ("W1007000003".equals(b.getS_TASK_TYPE())) {
                            Graphic graphicimg = new Graphic(p, m, pictureMarkerSymbolXJy);
                            FourthFragment.onClicgraphicsOverlayXJ.getGraphics().add(graphicimg);
                        }

                    }
                }

            }
        }
    }

    public PictureMarkerSymbol getpictureMarkerSymbol(int flag) {//0 高亮中心点，1日常人员，2应急人员，3养护人员，4离线人员
        Drawable drawable = null;
        if (flag == 0) {
            drawable = mContext.getResources().getDrawable(R.mipmap.map_jing_yu);
        } else if (flag == 1) {
            drawable = mContext.getResources().getDrawable(R.mipmap.btn_map_xunj_ricxc);
        } else if (flag == 2) {
            drawable = mContext.getResources().getDrawable(R.mipmap.btn_map_xunj_yingjrw);
        } else if (flag == 3) {
            drawable = mContext.getResources().getDrawable(R.mipmap.btn_map_yangh_zaixian);
        } else if (flag == 4) {
            drawable = mContext.getResources().getDrawable(R.mipmap.btn_map_xunj_out);
        } else if (flag == 5) {
            drawable = mContext.getResources().getDrawable(R.mipmap.btn_map_yangh_out);
        }
        PictureMarkerSymbol pictureMarkerSymbol = new PictureMarkerSymbol((BitmapDrawable) drawable);
        pictureMarkerSymbol.setOffsetX(0);
        pictureMarkerSymbol.setOffsetY(15);
        return pictureMarkerSymbol;
    }

    public void addGraphicsOverlaychange(ArrayList<AdapterBean> b) {
//        addGraphicsOverlay(feature, Symbol);
//        Point p = feature.getGeometry().getExtent().getCenter();

        if (ui == 0) {
            MainMapXJActivity.onClickMarkergraphicsOverlay.getGraphics().clear();
            MainMapXJActivity.onClickTextgraphicsOverlay.getGraphics().clear();
        } else if (ui == 1) {
            MainMapYHActivity mActivity= (MainMapYHActivity) mContext;
            mActivity.mAAtt.initB().graphicsOverlayClean(mActivity.mAAtt.initB().onClickMarkergraphicsOverlay);
            mActivity.mAAtt.initB().graphicsOverlayClean(mActivity.mAAtt.initB().onClickTextgraphicsOverlay);
        }else if (ui == 2) {
            FourthFragment.onClickMarkergraphicsOverlay.getGraphics().clear();
            FourthFragment.onClickTextgraphicsOverlay.getGraphics().clear();
        }
        if (pictureMarkerSymbolhight == null) {
            pictureMarkerSymbolhight = getpictureMarkerSymbol(0);
        }
        for (int j = 0; j < b.size(); j++) {
            Point p = b.get(j).mCenterPoint;
            Graphic graphicimg = new Graphic(p, pictureMarkerSymbolhight);
            TextSymbol textSymbol = new TextSymbol();
            textSymbol.setText(j + 1 + "");
            textSymbol.setSize(13);
            textSymbol.setColor(Color.BLACK);
//        textSymbol.setOffsetX(-10);
            textSymbol.setOffsetY(22);
            final Graphic graphictext = new Graphic(p, textSymbol);
            if (ui == 0) {
                MainMapXJActivity.onClickTextgraphicsOverlay.getGraphics().add(graphictext);
                MainMapXJActivity.onClickMarkergraphicsOverlay.getGraphics().add(graphicimg);
            } else if (ui == 1) {
                MainMapYHActivity mActivity= (MainMapYHActivity) mContext;
                mActivity.mAAtt.initB().onClickTextgraphicsOverlay.getGraphics().add(graphictext);
                mActivity.mAAtt.initB().onClickMarkergraphicsOverlay.getGraphics().add(graphicimg);
            } else if (ui == 2) {
                FourthFragment.onClickTextgraphicsOverlay.getGraphics().add(graphictext);
                FourthFragment.onClickMarkergraphicsOverlay.getGraphics().add(graphicimg);
            }
        }


//        new Handler().postDelayed(new Runnable() {
//            public void run() {
////                FourthFragment.onClickgraphicsOverlay.getGraphics().add(graphictext);
//            }
//        }, 2000);


    }

    public void FeatureQueryResulSeachPSJ(String keystr, QueryParameters query, final AddFeatureQueryResultListen mResultListen) {
        query.setWhereClause("upper(S_MANHOLE_NAME_ROAD) LIKE '%" + keystr + "%' and OBJECTID<50");
        ServiceFeatureTable mTablePSJ = new ServiceFeatureTable(AppConfig.GuanWangUrl + "/" + AppConfig.PSJ);
        final ListenableFuture<FeatureQueryResult> future = mTablePSJ
                .queryFeaturesAsync(query, ServiceFeatureTable.QueryFeatureFields.LOAD_ALL);
        future.addDoneListener(new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FeatureQueryResult result = future.get();
                            ArrayList<AdapterBean> abList = new ArrayList<>();
                            AdapterBean b;
                            for (Feature feature : result) {
                                Map<String, Object> attr = feature.getAttributes();
                                b = new AdapterBean();
                                String mk1 = AppTool.getNullStr(attr.get("N_MANHOLE_TYPE") + "");
                                String mv1 = "";
                                if ("1".equals(mk1)) {
                                    mv1 = "雨水井:";
                                } else if ("2".equals(mk1)) {
                                    mv1 = "污水井:";
                                } else if ("3".equals(mk1)) {
                                    mv1 = "合流井:";
                                }
                                String mv2 = AppTool.getNullStr(attr.get("N_MANHOLE_DEPTH") + "");
                                String mv3 = AppTool.getNullStr(attr.get("N_MANHOLE_LENGTH") + "");
                                b.mag1 = mv1 + "φ" + mv2 + "-" + mv3;
                                String mk4 = AppTool.getNullStr(attr.get("N_MANHOLE_STATE") + "");
                                String mv4 = "";
                                if ("0".equals(mk4)) {
                                    mv4 = "雨水口";
                                } else if ("1".equals(mk4)) {
                                    mv4 = "检查井";
                                } else if ("2".equals(mk4)) {
                                    mv4 = "接户井";
                                } else if ("3".equals(mk4)) {
                                    mv4 = "闸阀井";
                                } else if ("4".equals(mk4)) {
                                    mv4 = "溢流井:";
                                } else if ("5".equals(mk4)) {
                                    mv4 = "倒虹井";
                                } else if ("6".equals(mk4)) {
                                    mv4 = "透气井";
                                } else if ("7".equals(mk4)) {
                                    mv4 = "压力井";
                                } else if ("8".equals(mk4)) {
                                    mv4 = "检测井";
                                } else if ("8".equals(mk4)) {
                                    mv4 = "其它井";
                                }
                                b.mag2 = mv4;
                                b.mag3 = AppTool.getNullStr(attr.get("S_MANHOLE_NAME_ROAD") + "");
                                abList.add(b);
                            }
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMapSeach(abList, AppConfig.PSJ);
                            }
                        } catch (Exception ep) {
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMapSeach(null, AppConfig.PSJ);
                            }
                        }
                    }
                }).start();
            }
        });
    }

    public void FeatureQueryResulSeachPSGD(String keystr, QueryParameters query, final AddFeatureQueryResultListen mResultListen) {
        query.setWhereClause("upper(S_DRAI_PIPE_NAME_ROAD) LIKE '%" + keystr + "%' and OBJECTID<50");
        ServiceFeatureTable mTablePSGD = new ServiceFeatureTable(AppConfig.GuanWangUrl + "/" + AppConfig.PSGD);
        final ListenableFuture<FeatureQueryResult> future = mTablePSGD
                .queryFeaturesAsync(query, ServiceFeatureTable.QueryFeatureFields.LOAD_ALL);
        future.addDoneListener(new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FeatureQueryResult result = future.get();
                            ArrayList<AdapterBean> abList = new ArrayList<>();
                            AdapterBean b;
                            for (Feature feature : result) {
                                Map<String, Object> attr = feature.getAttributes();
                                b = new AdapterBean();

                                String mk1 = AppTool.getNullStr(attr.get("N_DRAI_PIPE_TYPE") + "");
                                String mv1 = "";
                                if ("1".equals(mk1)) {
                                    mv1 = "雨水管:";
                                } else if ("2".equals(mk1)) {
                                    mv1 = "污水管:";
                                } else if ("3".equals(mk1)) {
                                    mv1 = "合流管:";
                                } else if ("9".equals(mk1)) {
                                    mv1 = "其他管:";
                                }
                                String mv2 = AppTool.getNullStr(attr.get("N_DRAI_PIPE_D1") + "");
                                String mv3 = AppTool.getNullStr(attr.get("N_DRAI_PIPE_LENGTH") + "");
                                b.mag1 = mv1 + "φ" + mv2 + "-" + mv3;
                                String mk4 = AppTool.getNullStr(attr.get("N_DRAI_PIPE_GRADE") + "");
                                String mv4 = "";
                                if ("1".equals(mk4)) {
                                    mv4 = "总干管";
                                } else if ("2".equals(mk4)) {
                                    mv4 = "干管（截流管）";
                                } else if ("3".equals(mk4)) {
                                    mv4 = "主管";
                                } else if ("4".equals(mk4)) {
                                    mv4 = "连管:";
                                } else if ("5".equals(mk4)) {
                                    mv4 = "接户管";
                                } else if ("6".equals(mk4)) {
                                    mv4 = "街坊管";
                                } else if ("9".equals(mk4)) {
                                    mv4 = "其它管";
                                }
                                String mk5 = AppTool.getNullStr(attr.get("N_DRAI_PIPE_TYPE") + "");
                                String mv5 = "";
                                if ("1".equals(mk5)) {
                                    mv5 = "圆型";
                                } else if ("2".equals(mk5)) {
                                    mv5 = "蛋型";
                                } else if ("3".equals(mk5)) {
                                    mv5 = "矩形";
                                } else if ("9".equals(mk5)) {
                                    mv5 = "其它";
                                }
                                b.mag2 = mv4 + "-" + mv5;
                                String mv6, mv7, mv8;
                                mv6 = AppTool.getNullStr(attr.get("S_DRAI_PIPE_NAME_ROAD") + "");
                                mv7 = AppTool.getNullStr(attr.get("S_DRAI_PIPE_BROAD_NAME") + "");
                                mv8 = AppTool.getNullStr(attr.get("S_DRAI_PIPE_EROAD_NAME") + "");
                                b.mag3 = mv6 + "(" + mv7 + "-" + mv8 + ")";
                                abList.add(b);

                            }
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMapSeach(abList, AppConfig.PSGD);
                            }
                        } catch (Exception ep) {
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMapSeach(null, AppConfig.PSGD);
                            }
                        }
                    }
                }).start();

            }
        });
    }

    public void FeatureQueryResulSeachPFK(String keystr, QueryParameters query, final AddFeatureQueryResultListen mResultListen) {
        query.setWhereClause("upper(S_MANHOLE_NAME_ROAD) LIKE '%" + keystr + "%'");
        ServiceFeatureTable mTablePFK = new ServiceFeatureTable(AppConfig.GuanWangUrl + "/" + AppConfig.PFK);
        final ListenableFuture<FeatureQueryResult> future = mTablePFK
                .queryFeaturesAsync(query, ServiceFeatureTable.QueryFeatureFields.LOAD_ALL);
        future.addDoneListener(new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FeatureQueryResult result = future.get();
                            ArrayList<AdapterBean> abList = new ArrayList<>();
                            Map<String, Object> attr;
                            AdapterBean b;
                            for (Feature feature : result) {
                                b = new AdapterBean();
                                attr = feature.getAttributes();
                                String mk1 = AppTool.getNullStr(attr.get("N_MANHOLE_TYPE") + "");
                                String mv1 = "";
                                if ("1".equals(mk1)) {
                                    mv1 = "雨水井:";
                                } else if ("2".equals(mk1)) {
                                    mv1 = "污水井:";
                                } else if ("3".equals(mk1)) {
                                    mv1 = "合流井:";
                                }
                                String mv2 = AppTool.getNullStr(attr.get("N_MANHOLE_DEPTH") + "");
                                String mv3 = AppTool.getNullStr(attr.get("N_MANHOLE_LENGTH") + "");
                                b.mag1 = mv1 + "φ" + mv2 + "-" + mv3;
                                String mk4 = AppTool.getNullStr(attr.get("N_MANHOLE_STATE") + "");
                                String mv4 = "";
                                if ("0".equals(mk4)) {
                                    mv4 = "雨水口";
                                } else if ("1".equals(mk4)) {
                                    mv4 = "检查井";
                                } else if ("2".equals(mk4)) {
                                    mv4 = "接户井";
                                } else if ("3".equals(mk4)) {
                                    mv4 = "闸阀井";
                                } else if ("4".equals(mk4)) {
                                    mv4 = "溢流井:";
                                } else if ("5".equals(mk4)) {
                                    mv4 = "倒虹井";
                                } else if ("6".equals(mk4)) {
                                    mv4 = "透气井";
                                } else if ("7".equals(mk4)) {
                                    mv4 = "压力井";
                                } else if ("8".equals(mk4)) {
                                    mv4 = "检测井";
                                } else if ("8".equals(mk4)) {
                                    mv4 = "其它井";
                                }
                                b.mag2 = mv4;
                                b.mag3 = AppTool.getNullStr(attr.get("S_MANHOLE_NAME_ROAD") + "");
                                abList.add(b);
                            }
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMapSeach(abList, AppConfig.PFK);
                            }
                        } catch (Exception ep) {
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMapSeach(null, AppConfig.PFK);
                            }
                        }
                    }
                }).start();

            }
        });
    }

    public void FeatureQueryResulSeachPSBZ(String keystr, QueryParameters query, final AddFeatureQueryResultListen mResultListen) {
//        query.setWhereClause("upper(S_DRAI_PUMP_NAME) LIKE '%"+keystr+"%'");
        query.setWhereClause("upper(S_DRAI_PUMP_NAME) LIKE '%" + keystr + "%'");
        ServiceFeatureTable mTablePSBZ = new ServiceFeatureTable(AppConfig.GuanWangUrl + "/" + AppConfig.PSBZ);
        final ListenableFuture<FeatureQueryResult> future = mTablePSBZ
                .queryFeaturesAsync(query, ServiceFeatureTable.QueryFeatureFields.LOAD_ALL);
        future.addDoneListener(new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FeatureQueryResult result = future.get();
                            ArrayList<AdapterBean> abList = new ArrayList<>();
                            Map<String, Object> attr;
                            AdapterBean b;
                            for (Feature feature : result) {
                                b = new AdapterBean();
                                attr = feature.getAttributes();
                                b.mag1 = AppTool.getNullStr(attr.get("S_DRAI_PUMP_NAME") + "");
                                String mk1 = AppTool.getNullStr(attr.get("N_DRAI_PUMP_TYPE") + "");
                                String mk2 = AppTool.getNullStr(attr.get("N_DRAI_PUMP_TYPE_FEAT") + "");
                                String mv1 = "";
                                String mv2 = "";
                                if ("1".equals(mk1)) {
                                    mv1 = "雨水";
                                } else if ("2".equals(mk1)) {
                                    mv1 = "污水";
                                } else if ("3".equals(mk1)) {
                                    mv1 = "合建";
                                } else if ("9".equals(mk1)) {
                                    mv1 = "其它";
                                }
//                                分流污水，6-合流截流，7-干线输送，8-分流，9-合流，10-其他
                                if ("1".equals(mk2)) {
                                    mv1 = "分流雨水";
                                } else if ("2".equals(mk2)) {
                                    mv1 = "合流防汛";
                                } else if ("3".equals(mk2)) {
                                    mv1 = "立交";
                                } else if ("9".equals(mk2)) {
                                    mv1 = "闸泵";
                                } else if ("2".equals(mk2)) {
                                    mv1 = "分流污水";
                                } else if ("3".equals(mk2)) {
                                    mv1 = "合流截流";
                                } else if ("9".equals(mk2)) {
                                    mv1 = "干线输送";
                                } else if ("3".equals(mk2)) {
                                    mv1 = "分流";
                                } else if ("9".equals(mk2)) {
                                    mv1 = "合流";
                                } else if ("9".equals(mk2)) {
                                    mv1 = "其他";
                                }
                                b.mag2 = mv1 + "-" + mv2;
                                b.mag3 = AppTool.getNullStr(attr.get("S_DRAI_PUMP_ADD") + "");
                                abList.add(b);
                            }
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMapSeach(abList, AppConfig.PSBZ);
                            }
                        } catch (Exception ep) {
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMapSeach(null, AppConfig.PSBZ);
                            }
                        }
                    }
                }).start();

            }
        });
    }

    public void FeatureQueryResulSeachWSCLC(String keystr, QueryParameters query, final AddFeatureQueryResultListen mResultListen) {
        query.setWhereClause("upper(S_FACT_NAME) LIKE '%" + keystr + "%'");
        ServiceFeatureTable mTableWSCLC = new ServiceFeatureTable(AppConfig.GuanWangUrl + "/" + AppConfig.WSCLC);
        final ListenableFuture<FeatureQueryResult> future = mTableWSCLC
                .queryFeaturesAsync(query, ServiceFeatureTable.QueryFeatureFields.LOAD_ALL);
        future.addDoneListener(new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FeatureQueryResult result = future.get();
                            ArrayList<AdapterBean> abList = new ArrayList<>();
                            AdapterBean b;
                            for (Feature feature : result) {
                                b = new AdapterBean();
                                b.mag1 = AppTool.getNullStr(feature.getAttributes().get("S_FACT_NAME") + "");
                                String mv1 = AppTool.getNullStr(feature.getAttributes().get("N_FACT_CAP_DSN") + "");
                                String mv2 = AppTool.getNullStr(feature.getAttributes().get("S_FACT_METHOD_TREAT") + "");
                                b.mag2 = mv1 + "-" + mv2;
                                b.mag3 = AppTool.getNullStr(feature.getAttributes().get("S_FACT_ADD") + "");
                                abList.add(b);
                            }
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMapSeach(abList, AppConfig.WSCLC);
                            }
                        } catch (Exception ep) {
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMapSeach(null, AppConfig.WSCLC);
                            }
                        }
                    }
                }).start();

            }
        });
    }

    public void FeatureQueryResulSeachPSH(String keystr, QueryParameters query, final AddFeatureQueryResultListen mResultListen) {
        query.setWhereClause("upper(s_name) LIKE '%" + keystr + "%'");
        ServiceFeatureTable mTableZDPFK = new ServiceFeatureTable(AppConfig.GuanWangUrl + "/" + AppConfig.PSH);
        final ListenableFuture<FeatureQueryResult> future = mTableZDPFK
                .queryFeaturesAsync(query, ServiceFeatureTable.QueryFeatureFields.LOAD_ALL);
        future.addDoneListener(new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            FeatureQueryResult result = future.get();
                            ArrayList<AdapterBean> abList = new ArrayList<>();
                            AdapterBean b;
                            for (Feature feature : result) {
                                b = new AdapterBean();
//                        Map<String, Object> mQuerryString = feature.getAttributes();
//                        String str= (String) mQuerryString.get("s_name");
                                b.mag1 = AppTool.getNullStr((String) feature.getAttributes().get("s_name"));
                                String mv1 = AppTool.getNullStr((String) feature.getAttributes().get("s_type"));
                                String mv2 = AppTool.getNullStr((String) feature.getAttributes().get("s_wz"));
                                b.mag2 = mv1 + "-" + mv2;
                                String mv3 = AppTool.getNullStr((String) feature.getAttributes().get("s_town"));
                                String mv4 = AppTool.getNullStr((String) feature.getAttributes().get("s_address"));
                                b.mag3 = mv3 + "-" + mv4;
                                abList.add(b);
                            }
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMapSeach(abList, AppConfig.PSH);
                            }
                        } catch (Exception ep) {
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMapSeach(null, AppConfig.PSH);
                            }
                        }
                    }
                }).start();

            }
        });
    }

    public void FeatureQueryResulSeachZDPFK(String keystr, QueryParameters query, final AddFeatureQueryResultListen mResultListen) {
        query.setWhereClause("upper(S_OutletNAME) LIKE '%" + keystr + "%'");
        ServiceFeatureTable mTablePSH = new ServiceFeatureTable(AppConfig.GuanWangUrl + "/" + AppConfig.ZDPFK);
        final ListenableFuture<FeatureQueryResult> future = mTablePSH
                .queryFeaturesAsync(query, ServiceFeatureTable.QueryFeatureFields.LOAD_ALL);
        future.addDoneListener(new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FeatureQueryResult result = future.get();
                            ArrayList<AdapterBean> abList = new ArrayList<>();
                            AdapterBean b;
                            for (Feature feature : result) {
                                b = new AdapterBean();
                                b.mag1 = AppTool.getNullStr(feature.getAttributes().get("S_OutletNAME") + "");
                                String mv1 = AppTool.getNullStr(feature.getAttributes().get("S_SYS_NAME") + "");
                                String mv2 = AppTool.getNullStr(feature.getAttributes().get("S_pairufangxiang") + "");
                                b.mag2 = mv1 + "-" + mv2;
                                b.mag3 = AppTool.getNullStr(feature.getAttributes().get("S_ManageUnit") + "");
                                abList.add(b);
                            }
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMapSeach(abList, AppConfig.ZDPFK);
                            }
                        } catch (Exception ep) {
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMapSeach(null, AppConfig.ZDPFK);
                            }
                        }
                    }
                }).start();

            }
        });
    }

public static void graphicsClean(MapView mapView,GraphicsOverlay mgOverlay){
        if (mapView.getGraphicsOverlays().contains(mgOverlay)){
            mgOverlay.getGraphics().clear();
        }
}
public static  boolean isLegalLL(AMapLocation mLoca){
        return isLegalLL(mLoca,true);
}
public static  boolean isLegalLL(AMapLocation mLoca,boolean isShanghai){
    if (mLoca==null)// 定位对象为null
        return false;
    double x=mLoca.getLatitude();
    double y=mLoca.getLongitude();
    if (isShanghai){
        if (x> 31.5 || x< 30.5 ||y<120.0 || y>122.0)//下上右左范围     //是否上海大概范围坐标
            return false;
    }
    String xStr=String.valueOf(x);
    String yStr=String.valueOf(y);
    if (xStr.contains("E") ||yStr.contains("E"))//定位失败
        return false;
        return true;
}
public static   boolean isLegalTime(double timePre,double d){//是否合法坐标的距离的时间判断

        return isLegalTimeDIs(timePre,d);
}public static   boolean isLegalSpeed(double dis,long time){//巡检是否合法速度
double d=dis/(time/1000);
if (d>28){ //一秒不可能走20米；
    return false;
     }
        return true;
}public static   boolean isLegalSpeedYH(double dis,long time){//养护是否合法速度
double d=dis/(time/1000);
if (d>20){ //一秒不可能走20米；
    return false;
     }
        return true;
}
public static   boolean isLegalTimeDIs(double timePre,double d){//是否合法坐标的距离的时间判断
        boolean b=true;//默认合法
        double timecur=System.currentTimeMillis()-timePre;
    if (d>300&&timecur-timePre<20*1000){//是否小于20秒（坐标20秒之内传一次）
             b=false;
         }  if (d>600&&timecur-timePre<40*1000){//是否小于40秒（坐标20秒之内传一次）
             b=false;
         } if (d>900&&timecur-timePre<60*1000){//是否小于60秒（坐标20秒之内传一次）
             b=false;
         } if (d>1200&&timecur-timePre<80*1000){//是否小于80秒（坐标20秒之内传一次）
             b=false;
         }if (d>1500&&timecur-timePre<100*1000){//是否小于80秒（坐标20秒之内传一次）
             b=false;
         }
        return b;
}
    public void showTipsDialog(Context mContext,String message) {
        final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 有白色背
        window.setContentView(R.layout.dialog_tips);
        window.findViewById(R.id.version_describe).setVisibility(View.GONE);
        TextView tv_content = window.findViewById(R.id.tv_content);
        tv_content.setText(message);
        TextView tv_no = window.findViewById(R.id.tv_no);
        tv_no.setVisibility(View.GONE);
        window.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }public void showUPDialog(final Activity mContext, final String message, final InterfListen.A mA) {
        final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 有白色背
        window.setContentView(R.layout.dialog_tips);
        window.findViewById(R.id.version_describe).setVisibility(View.GONE);
        TextView tv_content = window.findViewById(R.id.tv_content);
        tv_content.setText(message);
        final TextView tv_no = window.findViewById(R.id.tv_no);
        tv_no.setVisibility(View.GONE);
        if (message.contains("失败")){
            tv_no.setVisibility(View.VISIBLE);
        }
        window.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                if (message.contains("失败")){
                    if (mA!=null)
                    mA.aA();
                }

            }
        });    window.findViewById(R.id.tv_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
    public static String getDes(String des){

//        des="'"+des+"'";
//        des=des.replace(",","','");
       return des="in ("+des+")";
    }
    public void showTaskingDialog(final Context mContext,String s) {
        final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 有白色背
        window.setContentView(R.layout.dialog_tips);
        TextView tv_content = window.findViewById(R.id.tv_content);
        TextView tv_close = window.findViewById(R.id.tv_no);
        StringBuilder sb=new StringBuilder();
        if (AppAttribute.G.isServerStart(mContext,s)){
            tv_close.setVisibility(View.GONE);
            sb.append("任务服务是否开启：已经开启 " );
        }else {
            tv_close.setVisibility(View.VISIBLE);
            tv_close.setText( "重新开启");
            sb.append("任务服务是否开启：没有开启 " );
        }
        sb.append("\n定位信息："+AppAttribute.G.Loca);
        sb.append("\n合法定位信息："+AppAttribute.G.LocaLegal);
        sb.append("\n轨迹定时器："+AppAttribute.G.TrackUPTimer);
        sb.append("\n合法轨迹："+AppAttribute.G.TrackUPPre);
        sb.append("\n轨迹上传成功："+AppAttribute.G.TrackUPSuccess);
        sb.append("\n轨迹上传失败："+AppAttribute.G.TrackUPError);
        sb.append("\n轨迹画地图："+AppAttribute.G.TrackDraw);
        tv_content.setText(sb.toString());
        window.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();

            }
        });
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AppAttribute.G.initState();

                alertDialog.dismiss();
            }
        });
    }
}
