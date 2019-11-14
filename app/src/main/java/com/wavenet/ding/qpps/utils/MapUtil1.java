package com.wavenet.ding.qpps.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.Window;
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
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.esri.arcgisruntime.layers.ArcGISMapImageSublayer;
import com.esri.arcgisruntime.layers.ArcGISVectorTiledLayer;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.activity.LoginActivity;
import com.wavenet.ding.qpps.activity.MainMapXJActivity;
import com.wavenet.ding.qpps.bean.AdapterBean;
import com.wavenet.ding.qpps.bean.Gps;
import com.wavenet.ding.qpps.fragment.FourthFragment;
import com.wavenet.ding.qpps.interf.AddFeatureQueryResultListen;
import com.wavenet.ding.qpps.interf.AddLayerListen;
import com.wavenet.ding.qpps.interf.CallBackMap;
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

public class MapUtil1 {
    public static String L = "location";
    public static String TNLH = "tasknewlisthide";
    public static String VEU = "vieweventunregistere";
    public static String FR = "filerequest";
    public static String FTD = "filetaskdeal";
    public static String FTA = "filetaskadd";
    public static String TD = "taskdeal";
    private static MapUtil1 mMapUtil;
    Context mContext;
    GraphicsOverlay markerOverlay;
    ArcGISMapImageLayer mainMapImageLayer;

    public MapUtil1(Context mContext) {
        this.mContext = mContext;
    }

    public static MapUtil1 getInstance(Context mContext) {
        if (mMapUtil == null) {
            mMapUtil = new MapUtil1(mContext);
        }
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

    public ArcGISMap getArcGISMapVector(String strMapUrl) {
        ArcGISVectorTiledLayer mainArcGISVectorTiledLayer = new ArcGISVectorTiledLayer(strMapUrl);
        Basemap mainBasemap = new Basemap(mainArcGISVectorTiledLayer);
        ArcGISMap mapVector = new ArcGISMap(mainBasemap);
        return mapVector;
    }

    public ArcGISMap getArcGISMapVector(String strMapUrl, MapView mapView) {
        ArcGISMap mapVector = getArcGISMapVector(strMapUrl);
        mapView.setMap(mapVector);
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
            if (DorP == 0) {
                mapXJActivity.presenter.clickTaskStart(mContext, userstr, MainMapXJActivity.S_RECODE_ID, AppTool.getCurrenttimeT(), "1");
            } else if ((DorP == 1)) {
                mapXJActivity.presenter.clickTaskPaiStart1(mContext, userstr, MainMapXJActivity.S_RECODE_ID, MainMapXJActivity.STASKID, AppTool.getCurrenttimeT(), "1");
            } else {
                mapXJActivity.presenter.clickTaskPaiStart(userstr, MainMapXJActivity.S_RECODE_ID, MainMapXJActivity.STASKID, AppTool.getCurrenttimeT(), "1");
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

    public void showGpsDialog(final Context mContext) {
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
                AppTool.openGPS(mContext);
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
                EventBus.getDefault().post(MapUtil1.TNLH);
                alertDialog.dismiss();
            }
        });
        window.findViewById(R.id.tv_suer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(MapUtil1.TNLH);
                alertDialog.dismiss();
            }
        });

        mRggps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRbgps1.isChecked()) {
//                    EventBus.getDefault().post(MapUtil.TLS);
//                    alertDialog.dismiss();
                }
                if (mRbgps2.isChecked()) {
//                    EventBus.getDefault().post(MapUtil.TLS);
//                    alertDialog.dismiss();
                }
            }

        });
    }

    public void showTaskendDialog(Context mContext) {
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
                mapXJActivity.presenter.RequestEndTask(MainMapXJActivity.S_RECODE_ID, AppTool.getCurrenttimeT());
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

    public void showTaskcancelDialog(Context mContext, final ControllerMainUIView mainUIView) {
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
        window.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                mapXJActivity.mMapView.getGraphicsOverlays().clear();
                mainUIView.setDoingstyle(false);
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
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 有白色背
        window.setContentView(R.layout.dialog_tips);
        window.findViewById(R.id.version_describe).setVisibility(View.GONE);
        TextView tv_content = window.findViewById(R.id.tv_content);
        tv_content.setText("是否现在处置?");
        window.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(MapUtil1.TD);
                alertDialog.dismiss();
            }
        });
        window.findViewById(R.id.tv_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
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
        tv_content.setText("是否清除缓存?");
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

    public ArcGISMapImageLayer addMapService(ArcGISMap mArcGISMapVector, final AddLayerListen mAddLayerListen) {
        final List<ArcGISMapImageSublayer> imageSublayerList = new ArrayList<>();

        mainMapImageLayer.loadAsync();
        mainMapImageLayer.addDoneLoadingListener(new Runnable() {
            @Override
            public void run() {
                if (mainMapImageLayer.getLoadStatus() == LoadStatus.LOADED) {
//                for (int i = 0; i < mainMapImageLayer.getSubLayerContents().size() ; i++) {
//                    mainMapImageLayer.getSubLayerContents().get(i).setVisible(true);
//                }
                    mainMapImageLayer.getSubLayerContents().get(1).setVisible(true);
                    mainMapImageLayer.getSubLayerContents().get(2).setVisible(true);
                    mainMapImageLayer.getSubLayerContents().get(2).getSubLayerContents().get(0).getSubLayerContents().get(2).setVisible(true);
                    if (mAddLayerListen != null) {
//                    imageSublayerList.add((ArcGISMapImageSublayer) mainMapImageLayer.getSublayers().get(3));
                        mAddLayerListen.getImageLayer(mainMapImageLayer);
                    }
                }
            }
        });

        mArcGISMapVector.getOperationalLayers().add(mainMapImageLayer);
        return mainMapImageLayer;
    }

    public void FeatureQueryResulPSJ(QueryParameters query, final AddFeatureQueryResultListen mResultListen) {
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
                                String mk1 = attr.get("N_MANHOLE_TYPE") + "";
                                String mv1 = "";
                                if ("1".equals(mk1)) {
                                    mv1 = "雨水井:";
                                } else if ("2".equals(mk1)) {
                                    mv1 = "污水井:";
                                } else if ("3".equals(mk1)) {
                                    mv1 = "合流井:";
                                }
                                String mv2 = attr.get("N_MANHOLE_DEPTH") + "";
                                String mv3 = attr.get("N_MANHOLE_LENGTH") + "";
                                b.mag1 = mv1 + "φ" + mv2 + "-" + mv3;
                                String mk4 = attr.get("N_MANHOLE_STATE") + "";
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
                                b.mag3 = attr.get("S_MANHOLE_NAME_ROAD") + "";
                                abList.add(b);
                            }
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMap(abList, AppConfig.PSJ);
                            }
                        } catch (Exception ep) {
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMap(null, AppConfig.PSJ);
                            }
                            LogUtils.e(FourthFragment.class.getSimpleName(), ep.toString());
                        }
                    }
                }).start();
            }
        });
    }

    public void FeatureQueryResulPSGD(QueryParameters query, final AddFeatureQueryResultListen mResultListen) {
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

                                String mk1 = attr.get("N_DRAI_PIPE_TYPE") + "";
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
                                String mv2 = attr.get("N_DRAI_PIPE_D1") + "";
                                String mv3 = attr.get("N_DRAI_PIPE_LENGTH") + "";
                                b.mag1 = mv1 + "φ" + mv2 + "-" + mv3;
                                String mk4 = attr.get("N_DRAI_PIPE_GRADE") + "";
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
                                String mk5 = attr.get("N_DRAI_PIPE_TYPE") + "";
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
                                mv6 = attr.get("S_DRAI_PIPE_NAME_ROAD") + "";
                                mv7 = attr.get("S_DRAI_PIPE_BROAD_NAME") + "";
                                mv8 = attr.get("S_DRAI_PIPE_EROAD_NAME") + "";
                                b.mag3 = mv6 + "(" + mv7 + "-" + mv8 + ")";
                                abList.add(b);

                            }
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMap(abList, AppConfig.PSGD);
                            }
                        } catch (Exception ep) {
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMap(null, AppConfig.PSGD);
                            }
                            LogUtils.e(FourthFragment.class.getSimpleName(), ep.toString());
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
                                b = new AdapterBean();
                                attr = feature.getAttributes();
                                String mk1 = attr.get("N_MANHOLE_TYPE") + "";
                                String mv1 = "";
                                if ("1".equals(mk1)) {
                                    mv1 = "雨水井:";
                                } else if ("2".equals(mk1)) {
                                    mv1 = "污水井:";
                                } else if ("3".equals(mk1)) {
                                    mv1 = "合流井:";
                                }
                                String mv2 = attr.get("N_MANHOLE_DEPTH") + "";
                                String mv3 = attr.get("N_MANHOLE_LENGTH") + "";
                                b.mag1 = mv1 + "φ" + mv2 + "-" + mv3;
                                String mk4 = attr.get("N_MANHOLE_STATE") + "";
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
                                b.mag3 = attr.get("S_MANHOLE_NAME_ROAD") + "";
                                abList.add(b);
                            }
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMap(abList, AppConfig.PFK);
                            }
                        } catch (Exception ep) {
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMap(null, AppConfig.PFK);
                            }
                            LogUtils.e(FourthFragment.class.getSimpleName(), ep.toString());
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
                                b = new AdapterBean();
                                attr = feature.getAttributes();
                                b.mag1 = attr.get("S_DRAI_PUMP_NAME") + "";
                                String mv1 = attr.get("N_DRAI_PUMP_TYPE") + "";
                                String mv2 = attr.get("N_DRAI_PUMP_TYPE_FEAT") + "";
                                b.mag2 = mv1 + "-" + mv2;
                                b.mag3 = attr.get("S_DRAI_PUMP_ADD") + "";
                                abList.add(b);
                            }
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMap(abList, AppConfig.PSBZ);
                            }
                        } catch (Exception ep) {
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMap(null, AppConfig.PSBZ);
                            }
                            LogUtils.e(FourthFragment.class.getSimpleName(), ep.toString());
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
                                b = new AdapterBean();
                                b.mag1 = feature.getAttributes().get("S_FACT_NAME") + "";
                                String mv1 = feature.getAttributes().get("N_FACT_CAP_DSN") + "";
                                String mv2 = feature.getAttributes().get("S_FACT_METHOD_TREAT") + "";
                                b.mag2 = mv1 + "-" + mv2;
                                b.mag3 = feature.getAttributes().get("S_FACT_ADD") + "";
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
                                b.mag1 = (String) feature.getAttributes().get("s_name");
                                String mv1 = (String) feature.getAttributes().get("s_type");
                                String mv2 = (String) feature.getAttributes().get("s_wz");
                                b.mag2 = mv1 + "-" + mv2;
                                String mv3 = (String) feature.getAttributes().get("s_town");
                                String mv4 = (String) feature.getAttributes().get("s_address");
                                b.mag3 = mv3 + "-" + mv4;
                                abList.add(b);
                            }
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMap(abList, AppConfig.PSH);
                            }
                        } catch (Exception ep) {
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMap(null, AppConfig.PSH);
                            }
                            LogUtils.e(FourthFragment.class.getSimpleName(), ep.toString());
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
                                b = new AdapterBean();
                                b.mag1 = feature.getAttributes().get("S_OutletNAME") + "";
                                String mv1 = feature.getAttributes().get("S_SYS_NAME") + "";
                                String mv2 = feature.getAttributes().get("S_pairufangxiang") + "";
                                b.mag2 = mv1 + "-" + mv2;
                                b.mag3 = feature.getAttributes().get("S_ManageUnit") + "";
                                abList.add(b);
                            }
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMap(abList, AppConfig.ZDPFK);
                            }
                        } catch (Exception ep) {
                            if (mResultListen != null) {
                                mResultListen.getQueryResultMap(null, AppConfig.ZDPFK);
                            }
                            LogUtils.e(FourthFragment.class.getSimpleName(), ep.toString());
                        }
                    }
                }).start();

            }
        });
    }
}
