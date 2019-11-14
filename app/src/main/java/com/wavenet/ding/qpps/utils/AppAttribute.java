package com.wavenet.ding.qpps.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.amap.api.trace.TraceLocation;
import com.dereck.library.utils.ToastUtils;
import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.data.FeatureQueryResult;
import com.esri.arcgisruntime.data.QueryParameters;
import com.esri.arcgisruntime.data.ServiceFeatureTable;
import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.geometry.PolylineBuilder;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.activity.MainMapXJActivity;
import com.wavenet.ding.qpps.activity.MainMapYHActivity;
import com.wavenet.ding.qpps.bean.AdapterBean;
import com.wavenet.ding.qpps.db.bean.TrackBean;
import com.wavenet.ding.qpps.db.bean.TrackPalyBean;
import com.wavenet.ding.qpps.interf.AddFeatureQueryResultListen;
import com.wavenet.ding.qpps.interf.AddFeatureResultListen;
import com.wavenet.ding.qpps.interf.AddLayerListen;
import com.wavenet.ding.qpps.interf.InterfListen;
import com.wavenet.ding.qpps.serverutils.LocService;
import com.wavenet.ding.qpps.xy.MyServiceYH;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zoubeiwen on 2018/12/20.
 */

public class AppAttribute {
    Context mContext;
    public A a;
    public B b;
    public C c;
    public D d;
    public E e;
    public F f;
    public G g;
    public H h;
    public I i;

    public AppAttribute(Context mContext) {
        this.mContext = mContext;
    }

    public A initA() {
//        if (a==null)
        a = new A();
        return a;
    }

    public A getASingle() {
        if (a == null)
            a = new A();
        return a;
    }

    public B initB() {
        if (b == null)
            b = new B();
        return b;
    }

    public C initC() {
        if (c == null)
            c = new C();
        return c;
    }

    public D initD() {
        if (d == null)
            d = new D();
        return d;
    }

    public E initE() {
        if (e == null)
            e = new E();
        return e;
    }

    public F initF() {
        if (f == null)
            f = new F();
        return f;
    }

    public G initG() {
        if (g == null)
            g = new G();
        return g;
    }   public H initH() {
        if (h == null)
            h = new H();
        return h;
    }  public I initI() {
        if (i == null)
            i = new I();
        return i;
    }

    /**
     * 轨迹
     */
    public class A {

        public boolean isOnstop = false;
        public GraphicsOverlay mGraphicsOverlayRoute;//高亮图层线
        PolylineBuilder lineGeometryRoute = null;
        Graphic lineGraphicRoute;
        SimpleLineSymbol lineSymbolRoute;
public int colorfalg=Color.RED;
        public void setR(MapView mMapView) {
            if (lineSymbolRoute == null) {
                lineSymbolRoute = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, colorfalg, 2);
            }
            if (lineGeometryRoute == null) {
                lineGeometryRoute = new PolylineBuilder(SpatialReferences.getWgs84());
            }
            if (mGraphicsOverlayRoute == null) {
                mGraphicsOverlayRoute = new GraphicsOverlay(GraphicsOverlay.RenderingMode.DYNAMIC);
            }
            if (!mMapView.getGraphicsOverlays().contains(mGraphicsOverlayRoute)) {
                mMapView.getGraphicsOverlays().add(mGraphicsOverlayRoute);
            }
            mGraphicsOverlayRoute.setVisible(true);
        }

        public void drawList(MapView mMapView, List<com.amap.api.maps.model.LatLng> listPoints) {//x纬度，y经度
            setR(mMapView);
//            if ((y + "").contains("E") || (x + "").contains("E")) return;

            for (int i = 0; i < listPoints.size(); i++) {
                com.amap.api.maps.model.LatLng ll = listPoints.get(i);
                lineGeometryRoute.addPoint(ll.longitude, ll.latitude);
            }

            if (isOnstop) return;
            lineGraphicRoute = new Graphic(lineGeometryRoute.toGeometry(), lineSymbolRoute);
            mGraphicsOverlayRoute.getGraphics().clear();
            mGraphicsOverlayRoute.getGraphics().add(lineGraphicRoute);
        }

        public void drawR(MapView mMapView, double x, double y) {//x纬度，y经度
            setR(mMapView);
            if ((y + "").contains("E") || (x + "").contains("E")) return;
            lineGeometryRoute.addPoint(y, x);
            G.TrackDraw=lineGeometryRoute.getParts().get(0).getPointCount();
            if (isOnstop) return;
            lineGraphicRoute = new Graphic(lineGeometryRoute.toGeometry(), lineSymbolRoute);
            mGraphicsOverlayRoute.getGraphics().clear();
            mGraphicsOverlayRoute.getGraphics().add(lineGraphicRoute);
        }

        public void drawR(MapView mMapView, double x, double y, boolean isStart) {//x纬度，y经度
            if (isStart) {
                if (mGraphicsOverlayRoute != null) {
                    lineGeometryRoute.toGeometry().getParts().clear();
                    mGraphicsOverlayRoute.getGraphics().clear();
                }
                lineGeometryRoute = new PolylineBuilder(SpatialReferences.getWgs84());
                if (mMapView.getGraphicsOverlays().contains(mGraphicsOverlayRoute)) {
                    mMapView.getGraphicsOverlays().remove(mGraphicsOverlayRoute);
                }
            }
            drawR(mMapView, x, y);
        }

        public void tackCorrect(int position, List<TraceLocation> mTraceList) {
            if (lineGeometryRoute != null && lineGeometryRoute.getParts() != null) {

                for (int i = 0; i < 4; i++) {
                    lineGeometryRoute.getParts().remove(position - i);
                }
                for (int i = 0; i < 4; i++) {
                    com.wavenet.ding.qpps.bean.Gps g = PositionUtil.gcj_To_Gps84(mTraceList.get(i).getLatitude(), mTraceList.get(i).getLongitude());
                    lineGeometryRoute.addPoint(position + i, g.getWgLat(), g.getWgLon());
                }

            }

        }

        public int tackSize() {
            if (lineGeometryRoute != null && lineGeometryRoute.getParts() != null) {
                return lineGeometryRoute.getParts().size();
            }
            return 0;
        }

        public void cleanR(MapView mMapView) {
            if (lineGeometryRoute != null && lineGeometryRoute.getParts() != null) {
                lineGeometryRoute.getParts().clear();
            }
            if (mGraphicsOverlayRoute != null && mMapView != null) {
                mGraphicsOverlayRoute.getGraphics().clear();

                if (mGraphicsOverlayRoute != null && mMapView.getGraphicsOverlays().contains(mGraphicsOverlayRoute)) {
                    mMapView.getGraphicsOverlays().remove(mGraphicsOverlayRoute);
                }
            }

        }

        public void drawTaskTrack( MapView mMapView, List<TrackBean> taskTack) {//当天轨迹查看
            setR(mMapView);
            for (int i = 0; i < taskTack.size(); i++) {
                lineGeometryRoute.addPoint(taskTack.get(i).n_lony, taskTack.get(i).n_latx);
            }
            draw(mMapView);
        }
        public void drawTaskTrack( MapView mMapView, TrackPalyBean mTrackPalyBean) {//当天轨迹查看
            setR(mMapView);
            List<TrackPalyBean.DataBeanXX.DataBeanX.DataBean> taskTack=mTrackPalyBean.Data.Data.get(0).Data;
            for (int i = 0; i < taskTack.size(); i++) {
                lineGeometryRoute.addPoint(taskTack.get(i).NX, taskTack.get(i).NY);
            }
            draw(mMapView);
        }
        public void draw(final MapView mMapView){

            lineGraphicRoute = new Graphic(lineGeometryRoute.toGeometry(), lineSymbolRoute);
            mGraphicsOverlayRoute.getGraphics().clear();
            mGraphicsOverlayRoute.getGraphics().add(lineGraphicRoute);
            TimerTask task = new TimerTask() {
                public void run() {
                    try {
                        mMapView.setViewpointGeometryAsync(lineGeometryRoute.toGeometry(), 50);
                    } catch (Exception e) {

                    }
                }
            };
            Timer timer = new Timer();
            timer.schedule(task, 1000);
        }
    }

    /**
     * 巡检变量
     */
    public class B {
        public int[] urlarry = new int[]{AppConfig.PSGDJ1, AppConfig.PSGD, AppConfig.PSGDWSJ1, AppConfig.PSGDWS, AppConfig.PFK, AppConfig.PSBZ, AppConfig.WSCLC, AppConfig.ZDPFK, AppConfig.PSH};
        public String[] filterkey = new String[]{"S_MANHOLE_NAME_ROAD", "S_DRAI_PIPE_NAME_ROAD", "S_MANHOLE_NAME_ROAD", "S_DRAI_PIPE_NAME_ROAD", "S_MANHOLE_NAME_ROAD", "S_DRAI_PUMP_NAME", "S_FACT_NAME", "S_OutletNAME", "s_name"};//查询的关键子段
        public String[] filterkey1 = new String[]{AppConfig.DetailsSeachPSJ, AppConfig.DetailsSeachPSGD, AppConfig.DetailsSeachPSJ, AppConfig.DetailsSeachPSGD, AppConfig.DetailsSeachPFK, AppConfig.DetailsSeachPSBZ, AppConfig.DetailsSeachWSCLC, AppConfig.DetailsSeachZDPFK, AppConfig.DetailsSeachPSH};
        public SimpleFillSymbol mTouchSymbol = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, 0x667265fb, null);
        public BitmapDrawable endDrawable = (BitmapDrawable) ContextCompat.getDrawable(mContext, R.mipmap.sheshi_mapjs_active);
        public PictureMarkerSymbol pictureMarkerSymbol = new PictureMarkerSymbol((BitmapDrawable) mContext.getResources().getDrawable(R.mipmap.btn_location));

        public GraphicsOverlay onClickgraphicsOverlay;//高亮图层
        public GraphicsOverlay onClickgraphicsOverlayPoint;//高亮图层点
        public GraphicsOverlay onClickgraphicsOverlayLine;//高亮图层线
        public GraphicsOverlay onClickTextgraphicsOverlay;//文字图层
        public GraphicsOverlay onClickMarkergraphicsOverlay;//marker指定图层
        public GraphicsOverlay mTouchgraphicsOverlay; // 画圆图层
        public GraphicsOverlay roadgraphicsOverlay; // 道路缓冲高亮

        public void graphicsOverlayInit() {//初始化地图高亮图层
            onClickgraphicsOverlay = new GraphicsOverlay();
            mTouchgraphicsOverlay = new GraphicsOverlay();
            onClickgraphicsOverlayPoint = new GraphicsOverlay();
            onClickgraphicsOverlayLine = new GraphicsOverlay();
            onClickTextgraphicsOverlay = new GraphicsOverlay();
            onClickMarkergraphicsOverlay = new GraphicsOverlay();
            roadgraphicsOverlay = new GraphicsOverlay();
        }

        public void graphicsOverlayAdd(MapView mapv) {//地图添加高亮图层
            if (!mapv.getGraphicsOverlays().contains(onClickgraphicsOverlay)) {
                mapv.getGraphicsOverlays().add(onClickgraphicsOverlay);
            }
            if (!mapv.getGraphicsOverlays().contains(mTouchgraphicsOverlay)) {
                mapv.getGraphicsOverlays().add(mTouchgraphicsOverlay);
            }
            if (!mapv.getGraphicsOverlays().contains(onClickgraphicsOverlayLine)) {
                mapv.getGraphicsOverlays().add(onClickgraphicsOverlayLine);
            }
            if (!mapv.getGraphicsOverlays().contains(onClickgraphicsOverlayPoint)) {
                mapv.getGraphicsOverlays().add(onClickgraphicsOverlayPoint);
            }
            if (!mapv.getGraphicsOverlays().contains(onClickMarkergraphicsOverlay)) {
                mapv.getGraphicsOverlays().add(onClickMarkergraphicsOverlay);
            }
            if (!mapv.getGraphicsOverlays().contains(onClickTextgraphicsOverlay)) {
                mapv.getGraphicsOverlays().add(onClickTextgraphicsOverlay);
            }
            if (!mapv.getGraphicsOverlays().contains(roadgraphicsOverlay)) {
                mapv.getGraphicsOverlays().add(roadgraphicsOverlay);
            }
        }

        public Graphic getGraphicroad(Geometry geometry) {
            if (roadgraphicsOverlay != null && roadgraphicsOverlay.getGraphics() != null) {
                roadgraphicsOverlay.getGraphics().clear();
            }
            Polygon polygonm = GeometryEngine.buffer(geometry, MapUtil.roadbuffer);
            Graphic polylineGraphic = new Graphic(polygonm, getSymbolroad());
            roadgraphicsOverlay.setVisible(true);
            return polylineGraphic;
        }

        SimpleFillSymbol polygonSymbol;

        public SimpleFillSymbol getSymbolroad() {
            if (polygonSymbol == null) {
                polygonSymbol = new SimpleFillSymbol(SimpleFillSymbol.Style.FORWARD_DIAGONAL, 0xFF00FF00,
                        new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, 0xFF00FF00, 1));
            }
            return polygonSymbol;
        }

        public void graphicsOverlayClean(GraphicsOverlay Overlay) {
            if (Overlay != null && Overlay.getGraphics() != null) {
                Overlay.getGraphics().clear();
            }
        }

        public void graphicsOverlayClean() {//清除地图添加高亮图层数据
            if (mTouchgraphicsOverlay != null) {
                mTouchgraphicsOverlay.getGraphics().clear();
            }
            if (onClickgraphicsOverlayPoint != null) {
                onClickgraphicsOverlayPoint.getGraphics().clear();
            }
            if (onClickgraphicsOverlayLine != null) {
                onClickgraphicsOverlayLine.getGraphics().clear();
            }
            if (onClickgraphicsOverlay != null) {
                onClickgraphicsOverlay.getGraphics().clear();
            }
            if (onClickTextgraphicsOverlay != null) {
                onClickTextgraphicsOverlay.getGraphics().clear();
            }
            if (onClickMarkergraphicsOverlay != null) {
                onClickMarkergraphicsOverlay.getGraphics().clear();
            }
        }
    }

    /**
     * 广播注册
     */
    public class C {
        public void initFilter(Context mContext, BroadcastReceiver broadcast) {
            // 注册路径信息广播接收器 -接收经纬度广播
            IntentFilter filter = new IntentFilter();
            filter.addCategory(mContext.getPackageName());
            filter.setPriority(100);
            filter.addAction(MyServiceYH.MAPTRACKCLEAN);
            filter.addAction(MyServiceYH.MAPTRACKING);
            filter.addAction(MyServiceYH.MAPTRACKINIT);
            filter.addAction(MyServiceYH.NMILEAGE);
            // 屏幕关闭，和开启
            filter.addAction(Intent.ACTION_SCREEN_ON);
            mContext.registerReceiver(broadcast, filter);
        }

        public void setMessgae(Handler myHandler, ArrayList<AdapterBean> abList, int mTable, int what) {
            Message message = new Message();
//        Bundle bundle=new Bundle();
//        bundle.putSerializable("2",abList);
//        message.setData(bundle);//bundle传值，耗时，效率低
            message.arg1 = mTable;
            message.obj = abList;
            myHandler.sendMessage(message);//发送message信息
            message.what = what;//
        }
    }

    /**
     * 事件定位 ；道路定位；  巡检轨迹；
     */
    public class D {//
        SimpleLineSymbol lineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.parseColor("#ff0000"), 6);
        Point point;
        SimpleFillSymbol fillSymbol = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, Color.parseColor("#99ff0000"), lineSymbol);//  color_338

        public PictureMarkerSymbol pictureMarkerSymbol = new PictureMarkerSymbol((BitmapDrawable) mContext.getResources().getDrawable(R.mipmap.sheshi_mapjs_active));
        public ArcGISMapImageLayer iLayerRoad;
        GraphicsOverlay gOverlayRoad;
        GraphicsOverlay gOverlayLoca;
        AddLayerListen addlayerListen;
        QueryParameters query;
        ListenableFuture<FeatureQueryResult> future;
        public AddFeatureResultListen mFeatureListen;
        public String FeatureMessg = "";

        public void addRoadImageLayer(ArcGISMap mArcGISMapVector, String mapLayerurl, AddLayerListen alListen) {
            iLayerRoad = new ArcGISMapImageLayer(mapLayerurl);
            addlayerListen = alListen;
            iLayerRoad.loadAsync();
            iLayerRoad.addDoneLoadingListener(new Runnable() {
                @Override
                public void run() {
                    if (iLayerRoad.getLoadStatus() == LoadStatus.LOADED) {
//                        ArcGISMapImageSublayer mImageSublayerG = (ArcGISMapImageSublayer) iLayerRoad.getSubLayerContents().get(0);
//                        mImageSublayerG.setVisible(true);
                        if (addlayerListen != null) {
                            addlayerListen.getImageLayer(iLayerRoad);
                        }

                    } else {
                        ToastUtils.showToast("图层加载失败");
                    }

                }
            });
            if (!mArcGISMapVector.getBasemap().getBaseLayers().contains(iLayerRoad)) {
                mArcGISMapVector.getOperationalLayers().add(iLayerRoad);
            }
        }

        public void seachFeature(String queryStr, String url, final int sign) {//sign，0道路定位,1巡检养护轨迹，2养护道路查询用于缓冲高亮
            //"upper(S_ROAD_ID) LIKE '%路%'"
            query = new QueryParameters();
            query.setReturnGeometry(true);//指定是否返回几何对象
            query.setWhereClause(queryStr);//查询语句
            ServiceFeatureTable mTable = new ServiceFeatureTable(url);
            future = mTable
                    .queryFeaturesAsync(query, ServiceFeatureTable.QueryFeatureFields.LOAD_ALL);
            future.addDoneListener(new Runnable() {
                @Override
                public void run() {
                    try {
                        FeatureQueryResult result = future.get();
                        int count = 0;
                        for (Feature feature : result) {
                            count++;
                            if (mFeatureListen != null) {
                                mFeatureListen.getFeatureResult(feature, sign);
                            }
                        }
                        if (count == 0) {
                            if (sign == 2) {
                                ToastUtils.showToast("该记录没有养护道路");
                            } else {
                                ToastUtils.showToast(FeatureMessg);
                            }
                        }
                    } catch (Exception ep) {
                        ToastUtils.showToast(ep.toString());
                    }
                }
            });
        }

        public void addgOverlayRoad(MapView mapView, Geometry mG) {
            if (gOverlayRoad == null) {
                gOverlayRoad = new GraphicsOverlay();
            }
            gOverlayRoad.getGraphics().clear();
            gOverlayRoad.getGraphics().add(new Graphic(mG, lineSymbol));
            if (!mapView.getGraphicsOverlays().contains(gOverlayRoad)) {
                mapView.getGraphicsOverlays().add(gOverlayRoad);
            }
            mapView.setViewpointGeometryAsync(mG, 50);
        }

        public void addgOverlayLoca(MapView mapView, Point p) {
            if (gOverlayLoca == null) {
                gOverlayLoca = new GraphicsOverlay();
            }
            gOverlayLoca.getGraphics().clear();
            gOverlayLoca.getGraphics().add(new Graphic(p, pictureMarkerSymbol));
            if (!mapView.getGraphicsOverlays().contains(gOverlayLoca)) {
                mapView.getGraphicsOverlays().add(gOverlayLoca);
            }
            mapView.setViewpointCenterAsync(p, 10000);
        }


    }

    /**
     * 养护继续执行
     */
    public class E {//
        public Point pLoca;
        QueryParameters query;
        ListenableFuture<FeatureQueryResult> future;
        public AddFeatureQueryResultListen mFeatureListen;

        public void isCorrectPoint(AddFeatureQueryResultListen mFeatureListen) {   //判断当前位置是否在制定的道路上
            this.mFeatureListen = mFeatureListen;
            if (!MapUtil.isLegalLL(MainMapYHActivity.aMapLocation)) {
                ToastUtils.showToast("当前位置定位失败");
                return;
            }
            com.wavenet.ding.qpps.bean.Gps g = PositionUtil.gcj_To_Gps84(MainMapYHActivity.aMapLocation.getLatitude(), MainMapYHActivity.aMapLocation.getLongitude());
            pLoca = new Point(g.getWgLon(), g.getWgLat(), SpatialReferences.getWgs84());
//          pLoca = new Point(121.134, 31.155, SpatialReferences.getWgs84());
            seachFeature();
        }

        public void seachFeature() {
//        Polygon polygon = GeometryEngine.buffer(pLoca, 0.00014);
            double tolerance = 0.001;
            double mapTolerance = tolerance;
//                    * mapv.getUnitsPerDensityIndependentPixel();
            //查询范围
            Envelope envelope = new Envelope(pLoca.getX() + mapTolerance, pLoca.getY() - mapTolerance,
                    pLoca.getX() - mapTolerance, pLoca.getY() + mapTolerance, SpatialReference.create(4326));
            query = new QueryParameters();
            query.setReturnGeometry(true);//指定是否返回几何对象
            query.setGeometry(envelope);// 设置空间几何对象
            ServiceFeatureTable mTable = new ServiceFeatureTable(AppConfig.YHRoadurl);
            future = mTable
                    .queryFeaturesAsync(query, ServiceFeatureTable.QueryFeatureFields.LOAD_ALL);
            future.addDoneListener(new Runnable() {
                @Override
                public void run() {
                    try {
                        FeatureQueryResult result = future.get();
                        ArrayList<AdapterBean> abList = new ArrayList<>();
                        AdapterBean b;
                        int count = 0;
                        for (Feature feature : result) {
                            b = new AdapterBean();
                            b.mag1 = AppTool.getNullStr((String) feature.getAttributes().get("S_ROAD_ID"));
                            b.mFeaturesGeometry = feature.getGeometry();
                            abList.add(b);

                        }
                        if (abList == null || abList.size() == 0) {
                            ToastUtils.showToast("附近没有需要养护的路段");
                            return;
                        }

                        if (mFeatureListen != null) {
                            mFeatureListen.getQueryResultMapSeach(abList, 0);
                        }
                    } catch (Exception ep) {

//                    ToastUtils .showToast(ep.toString());
                    }
                }
            });
        }
    }

    public static class F {
        public static String getXJID(Context mContext) {
            if (AppTool.isNull(MainMapXJActivity.S_RECODE_ID)) {
                return SPUtil.getInstance(mContext).getStringValue(SPUtil.XJID);
            }
            return MainMapXJActivity.S_RECODE_ID;
        }

        public static String getYHID(Context mContext) {
            if (AppTool.isNull(MainMapYHActivity.relyid)) {
                return SPUtil.getInstance(mContext).getStringValue(SPUtil.YHID);
            }
            return MainMapYHActivity.relyid;
        }
    }

    public static class G {
        public static boolean XJServer = false;
        public static int Loca = 0;
        public static int LocaLegal = 0;
        public static int TrackUPTimer = 0;
        public static int TrackUPPre = 0;
        public static int TrackUPSuccess = 0;
        public static int TrackUPError = 0;
        public static int TrackDraw = 0;
        public static boolean YHServer = false;

        public static boolean isServerStart(Context mContext, String s) {
            if ("XJ".equals(s)) {
                if (AppTool.isServiceRunning(mContext, LocService.class.getName())) {
                    XJServer = true;
                } else {
                    XJServer = false;
                }
                return  XJServer;
            } else if ("YH".equals(s)) {
                if (AppTool.isServiceRunning(mContext, MyServiceYH.class.getName())) {
                    YHServer = true;
                } else {
                    YHServer = false;
                }
                return  YHServer;
            }
            return false;
        }

        public static void initState() {
            XJServer = true;
            Loca = 0;
            LocaLegal = 0;
            TrackUPTimer = 0;
            TrackUPPre = 0;
            TrackUPSuccess = 0;
            TrackUPError = 0;
            TrackDraw = 0;
            YHServer = true;
        }


    }
    public  class H{


        public  InterfListen.setTimeSelete mTimeSelete;

        public  void setTimeSelete(InterfListen.setTimeSelete m) {
            mTimeSelete = m;
        }

        public  void setTimeSelete(Context c, TextView tv, String time) {
            int a = 10;
            try {
                a = DateUtil.compareDate(DateUtil.stringToDate(time), DateUtil.stringToDate(AppTool.getCurrentDate()));
                if (a == 1) {
                    ToastUtils.showToast( "不能超过当天日期");
                } else {
                    tv.setText(time);
                    if (mTimeSelete != null) {
                        mTimeSelete.okTime();
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
    public class I {//
        SimpleLineSymbol lineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.parseColor("#ff0000"), 6);
        Point point;
        SimpleFillSymbol fillSymbol = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, Color.parseColor("#99ff0000"), lineSymbol);//  color_338

        public PictureMarkerSymbol pictureMarkerSymbol = new PictureMarkerSymbol((BitmapDrawable) mContext.getResources().getDrawable(R.mipmap.sheshi_mapjs_active));
        public ArcGISMapImageLayer iLayerRoad;
        GraphicsOverlay gOverlayRoad;
        GraphicsOverlay gOverlayLoca;
        AddLayerListen addlayerListen;
        QueryParameters query;
        ListenableFuture<FeatureQueryResult> future;
        public AddFeatureResultListen mFeatureListen;
        public String FeatureMessg = "";

        public void addRoadImageLayer(ArcGISMap mArcGISMapVector, String mapLayerurl, AddLayerListen alListen) {
            iLayerRoad = new ArcGISMapImageLayer(mapLayerurl);
            addlayerListen = alListen;
            iLayerRoad.loadAsync();
            iLayerRoad.addDoneLoadingListener(new Runnable() {
                @Override
                public void run() {
                    if (iLayerRoad.getLoadStatus() == LoadStatus.LOADED) {
//                        ArcGISMapImageSublayer mImageSublayerG = (ArcGISMapImageSublayer) iLayerRoad.getSubLayerContents().get(0);
//                        mImageSublayerG.setVisible(true);
                        if (addlayerListen != null) {
                            addlayerListen.getImageLayer(iLayerRoad);
                        }

                    } else {
                        ToastUtils.showToast("图层加载失败");
                    }

                }
            });
            if (!mArcGISMapVector.getBasemap().getBaseLayers().contains(iLayerRoad)) {
                mArcGISMapVector.getOperationalLayers().add(iLayerRoad);
            }
        }

        public void seachFeature(String queryStr, String url, final int sign) {//sign，0道路定位,1巡检养护轨迹，2养护道路查询用于缓冲高亮
            //"upper(S_ROAD_ID) LIKE '%路%'"
            query = new QueryParameters();
            query.setReturnGeometry(true);//指定是否返回几何对象
            query.setWhereClause(queryStr);//查询语句
            ServiceFeatureTable mTable = new ServiceFeatureTable(url);
            future = mTable
                    .queryFeaturesAsync(query, ServiceFeatureTable.QueryFeatureFields.LOAD_ALL);
            future.addDoneListener(new Runnable() {
                @Override
                public void run() {
                    try {
                        FeatureQueryResult result = future.get();
                        int count = 0;
                        for (Feature feature : result) {
                            count++;
                            if (mFeatureListen != null) {
                                mFeatureListen.getFeatureResult(feature, sign);
                            }
                        }
                        if (count == 0) {
                            if (sign == 2) {
                                ToastUtils.showToast("该记录没有养护道路");
                            } else {
                                ToastUtils.showToast(FeatureMessg);
                            }
                        }
                    } catch (Exception ep) {
                        ToastUtils.showToast(ep.toString());
                    }
                }
            });
        }

        public void addgOverlayRoad(MapView mapView, Geometry mG) {
            if (gOverlayRoad == null) {
                gOverlayRoad = new GraphicsOverlay();
            }
            gOverlayRoad.getGraphics().clear();
            gOverlayRoad.getGraphics().add(new Graphic(mG, lineSymbol));
            if (!mapView.getGraphicsOverlays().contains(gOverlayRoad)) {
                mapView.getGraphicsOverlays().add(gOverlayRoad);
            }
            mapView.setViewpointGeometryAsync(mG, 50);
        }

        public void addgOverlayLoca(MapView mapView, Point p) {
            if (gOverlayLoca == null) {
                gOverlayLoca = new GraphicsOverlay();
            }
            gOverlayLoca.getGraphics().clear();
            gOverlayLoca.getGraphics().add(new Graphic(p, pictureMarkerSymbol));
            if (!mapView.getGraphicsOverlays().contains(gOverlayLoca)) {
                mapView.getGraphicsOverlays().add(gOverlayLoca);
            }
            mapView.setViewpointCenterAsync(p, 10000);
        }


    }
}
