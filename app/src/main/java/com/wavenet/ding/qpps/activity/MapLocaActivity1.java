package com.wavenet.ding.qpps.activity;

import android.graphics.Color;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.TextView;

import com.dereck.library.base.BaseMvpActivity;
import com.dereck.library.utils.ToastUtils;
import com.dereck.library.view.LoadingWaitView;
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PointCollection;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.geometry.Polyline;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.esri.arcgisruntime.layers.ArcGISMapImageSublayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.esri.arcgisruntime.symbology.SimpleRenderer;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.interf.AddLayerListen;
import com.wavenet.ding.qpps.interf.CallBackMap;
import com.wavenet.ding.qpps.mvp.p.ErrorcorrectionRequestPresenter;
import com.wavenet.ding.qpps.mvp.v.RequestView;
import com.wavenet.ding.qpps.utils.AppAttribute;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.MapUtil;
import com.wavenet.ding.qpps.utils.MapdownloadUtil;

//查看巡检事件定位
public class MapLocaActivity1 extends BaseMvpActivity <RequestView, ErrorcorrectionRequestPresenter>implements View.OnClickListener,CallBackMap, LoadingWaitView.OnRestLoadListener,AddLayerListen  {
    String strMapUrl;
    LoadingWaitView mLoadingWaitView;
    public MapView mMapView;
    public ArcGISMap mArcGISMapVector;
    AppAttribute mAAtt;
    TextView tv;
    @Override
    public int getLayoutId() {
        return R.layout.activity_roadmap;
    }

    @Override
    public void init() {
        setTitle("事件位置");
        initView();
        strMapUrl = MapUtil.getInstance(this).getvtppath();
//        new MapdownloadUtil(this).setCallBackMap(this).set();
//        new MapdownloadUtil(this).setCallBackMap(this).requestMapbase(mLoadingWaitView);
        MapdownloadUtil  mapdownloadUtil=    new MapdownloadUtil(this).setCallBackMap(this);
        mapdownloadUtil.Rxhttp();

 String strMapUrl= Environment.getExternalStorageDirectory() + "/" + AppTool.getAppName(this) + "/arcgismap/qpbasemap.vtpk";
        mArcGISMapVector = MapUtil.getInstance(this).getArcGISMapVector(strMapUrl, mMapView);
        mAAtt=new AppAttribute(this);
        mAAtt.initB(). graphicsOverlayInit();
        mAAtt.initB(). graphicsOverlayAdd(mMapView);
        PointCollection polylinePoints = new PointCollection(SpatialReferences.getWgs84());
        polylinePoints.add(new Point(121.46, 31.52,SpatialReferences.getWgs84()));
        polylinePoints.add(new Point(121.46, 30.45,SpatialReferences.getWgs84()));
        Polyline polyline = new Polyline(polylinePoints);

        final Polygon polygonm = GeometryEngine.buffer(polyline, 0.0009);
        SimpleFillSymbol polygonSymbol = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, 0xFF00FF00,
                new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, 0xFF00FF00, 2));
        Graphic polylineGraphic = new Graphic(polygonm, polygonSymbol);

        mAAtt.initB().onClickgraphicsOverlayLine.getGraphics().add(polylineGraphic);
        SimpleMarkerSymbol pointSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.RED, 16);
// create simple renderer
        SimpleRenderer   pointRenderer = new SimpleRenderer(pointSymbol);
final Point p=new Point(121.4609, 31,SpatialReferences.getWgs84());
        Graphic pGraphic = new Graphic( p , pointSymbol);
        mAAtt.initB().onClickgraphicsOverlayPoint.getGraphics().add(pGraphic);


        mMapView.setOnTouchListener(new DefaultMapViewOnTouchListener(this,mMapView){
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                String s="ddddd";
                if (GeometryEngine.crosses(polygonm, p))
                    s=s+"----CROSSES";
                if (GeometryEngine.contains(polygonm, p))
                    s=s+"----contains";
                if (GeometryEngine.disjoint(polygonm, p))
                    s=s+"----disjoint";
                if (GeometryEngine.intersects(polygonm, p))
                    s=s+"----intersects";
                if (GeometryEngine.overlaps(polygonm, p))
                    s=s+"----overlaps";
                if (GeometryEngine.touches(polygonm, p))
                    s=s+"----touches";
                if (GeometryEngine.within(polygonm, p))
                    s=s+"----within";
                ToastUtils.showToast(s);
                return super.onSingleTapConfirmed(e);
            }

            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                setTitle("事件位置"+mMapView.getMapScale());
                return super.onScale(detector);
            }
        });
    }

    @Override
    public void requestData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.dispose();
    }

    @Override
    public void onClick(View v) {
switch (v.getId()){
}
    }
    @Override
    protected ErrorcorrectionRequestPresenter createPresenter() {
        return new ErrorcorrectionRequestPresenter() {
        };
    }
    @Override
    public void onClick(String mOnClick) {
        switch (mOnClick) {
            case "DOWNLOADSUCCESS":
                mLoadingWaitView.setVisibility(View.GONE);
                if (!MapUtil.getInstance(this).isExistspath(MapdownloadUtil.strMapUrl))
                    return;

                mAAtt = new AppAttribute(this);
                mArcGISMapVector = MapUtil.getInstance(this).getArcGISMapVector(MapdownloadUtil.strMapUrl, mMapView);
                setMapState();
                break;
            case "DOWNLOADFIL":
                mLoadingWaitView.failure();
                break;

        }
    }

    @Override
    public void onRestLoad(LoadingWaitView loadWaitView) {
        new MapdownloadUtil(this).setCallBackMap(this);
    }
    ArcGISMapImageSublayer mImageSublayer1;
    ArcGISMapImageSublayer mImageSublayer2;
    boolean isfirst=true;
    @Override
    public void getImageLayer(ArcGISMapImageLayer mImageLayer) {
        ArcGISMapImageSublayer mImageSublayer= (ArcGISMapImageSublayer)  mAAtt.initD().iLayerRoad.getSubLayerContents().get(2);
//        ArcGISMapImageSublayer mImageSublayer11= (ArcGISMapImageSublayer)  mImageSublayer.getSubLayerContents().get(2);
//        mImageSublayer11.setVisible(false);
//        ArcGISMapImageSublayer mImageSublayer22= (ArcGISMapImageSublayer)  mImageSublayer.getSubLayerContents().get(3);
//        mImageSublayer22.setVisible(false);
        mAAtt.initD().iLayerRoad.setVisible(true);
        mImageSublayer.setVisible(true);
         mImageSublayer1= (ArcGISMapImageSublayer) mImageSublayer.getSubLayerContents().get(0);
         mImageSublayer2= (ArcGISMapImageSublayer) mImageSublayer.getSubLayerContents().get(1);
        mImageSublayer1.setVisible(true);
        mImageSublayer2.setVisible(true);
        loopImageSublayer(mImageSublayer1,isfirst);
        loopImageSublayer(mImageSublayer2,!isfirst);


    }

    @Override
    public void getImageLayerJc(ArcGISMapImageLayer mImageLayer) {

    }

    public void initView(){
        tv = findViewById(R.id.tv_1);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isfirst=!isfirst;
                tv.setText(isfirst+"");

                loopImageSublayer(mImageSublayer1,isfirst);
                loopImageSublayer(mImageSublayer2,!isfirst);
            }
        });
        mLoadingWaitView = findViewById(R.id.loadingWaitView);
        mLoadingWaitView.setRestLoadListener(this);
//        mLoadingWaitView.setVisibility(View.VISIBLE);
        mMapView = (MapView) findViewById(R.id.mapv);
    }


    public void setMapState(){
        ArcGISRuntimeEnvironment.setLicense("runtimelite,1000,rud5799562951,none,PM0RJAY3FLLR2B3TR001");
        MapUtil.getInstance(this).setshanghai(mMapView);
//        mAAtt.initD().addRoadImageLayer(mArcGISMapVector, AppConfig.ceshiurl,this);
        mAAtt.initD().addRoadImageLayer(mArcGISMapVector, "http://218.1.67.12/arcgisadaptor/rest/services/psc_psss_wcg84_20160412/MapServer",this);
//        String lat=getIntent().getStringExtra("lat");
//        String lon=getIntent().getStringExtra("lon");
//
//        if (Double.parseDouble(lat)==0||Double.parseDouble(lon)==0){
//            ToastUtils.showToast("事件位置信息错误");
//            return;
//        }
//
//        Point p=new Point(Double.parseDouble(lat),Double.parseDouble(lon),mMapView.getSpatialReference());
//        mAAtt.initD().addgOverlayLoca(mMapView,p);
    }
    public void loopImageSublayer(ArcGISMapImageSublayer mISublayer,Boolean b){
        int size=mISublayer.getSubLayerContents().size();
        if(size>0){
            for (int i = 0; i <size ; i++) {
                ArcGISMapImageSublayer mIs= (ArcGISMapImageSublayer) mISublayer.getSubLayerContents().get(i);
                loopImageSublayer(mIs,b);
            }
        }else {
            mISublayer.setDefinitionExpression("S_SYS_NAME='北新泾南'");
            mISublayer.setVisible(b);
        }
    }
}
