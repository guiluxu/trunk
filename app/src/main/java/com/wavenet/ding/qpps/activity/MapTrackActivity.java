package com.wavenet.ding.qpps.activity;

import android.view.View;

import com.dereck.library.base.BaseMvpActivity;
import com.dereck.library.utils.ToastUtils;
import com.dereck.library.view.LoadingWaitView;
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.esri.arcgisruntime.layers.ArcGISMapImageSublayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.interf.AddFeatureResultListen;
import com.wavenet.ding.qpps.interf.AddLayerListen;
import com.wavenet.ding.qpps.interf.CallBackMap;
import com.wavenet.ding.qpps.mvp.p.ErrorcorrectionRequestPresenter;
import com.wavenet.ding.qpps.mvp.v.RequestView;
import com.wavenet.ding.qpps.utils.AppAttribute;
import com.wavenet.ding.qpps.utils.AppConfig;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.MapUtil;
import com.wavenet.ding.qpps.utils.MapdownloadUtil;

//查看巡检,养护当天前轨迹记录
public class MapTrackActivity extends BaseMvpActivity <RequestView, ErrorcorrectionRequestPresenter>implements View.OnClickListener,CallBackMap, LoadingWaitView.OnRestLoadListener,AddLayerListen ,AddFeatureResultListen {
    String strMapUrl;
    LoadingWaitView mLoadingWaitView;
    public MapView mMapView;
    public ArcGISMap mArcGISMapVector;
    AppAttribute mAAtt;
  String   S_RECORD_ID="";//巡检或者养护的记录id
  String   S_ROAD_ID="";//养护的道路id
  int  XJorYH=0;//0巡检，1养护；
    @Override
    public int getLayoutId() {
        return R.layout.activity_roadmap;
    }

    @Override
    public void init() {
        String title=getIntent().getStringExtra("title");
        XJorYH=getIntent().getIntExtra("XJorYH",0);
        S_RECORD_ID=getIntent().getStringExtra("S_RECORD_ID");
        S_ROAD_ID=getIntent().getStringExtra("S_ROAD_ID");
        setTitle("轨迹查看");
        initView();
//        S_RECORD_ID=  "XJ1540364722200xjtest";
        strMapUrl = MapUtil.getInstance(this).getvtppath();
//        new MapdownloadUtil(this).setCallBackMap(this).set();
//        new MapdownloadUtil(this).setCallBackMap(this).requestMapbase(mLoadingWaitView);
        MapdownloadUtil  mapdownloadUtil=    new MapdownloadUtil(this).setCallBackMap(this);
        mapdownloadUtil.Rxhttp();
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
                mAAtt.initD().FeatureMessg=  "该记录没有轨迹";
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
    @Override
    public void getImageLayer(ArcGISMapImageLayer mImageLayer) {
        mLoadingWaitView.setVisibility(View.GONE);
        ArcGISMapImageSublayer mImageSublayer= (ArcGISMapImageSublayer)  mAAtt.initD().iLayerRoad.getSubLayerContents().get(0);
        mAAtt.initD().iLayerRoad.setVisible(true);
        mImageSublayer.setVisible(true);
        if (!AppTool.isNull(S_RECORD_ID)){
            mImageSublayer.setDefinitionExpression("S_RECORD_ID='"+S_RECORD_ID+"'");//显示道路
//            mImageSublayer.setVisible(true);
        }else{
            ToastUtils.showToast("巡检id为空");
        }
        if (XJorYH==0){
            mAAtt.initD().seachFeature("S_RECORD_ID='"+S_RECORD_ID+"'",AppConfig.XJRoadImageLayerurl+"/0",1);
        }else {
            mAAtt.initD().seachFeature("S_RECORD_ID='"+S_RECORD_ID+"'",AppConfig.YHRoadImageLayerurl+"/0",1);

            mAAtt.initD().seachFeature("S_ROAD_ID='"+S_ROAD_ID+"'",AppConfig.YHRoadurl,2);
            mAAtt.initB().roadgraphicsOverlay=new GraphicsOverlay();
            if (!mMapView.getGraphicsOverlays().contains(mAAtt.initB().roadgraphicsOverlay)) {
                mMapView.getGraphicsOverlays().add(mAAtt.initB().roadgraphicsOverlay);
            }
        }


    }

    @Override
    public void getImageLayerJc(ArcGISMapImageLayer mImageLayer) {

    }

    public void initView(){
        mLoadingWaitView = findViewById(R.id.loadingWaitView);
        mLoadingWaitView.setRestLoadListener(this);
        mLoadingWaitView.setVisibility(View.VISIBLE);
        mMapView = (MapView) findViewById(R.id.mapv);
        mMapView.setAttributionTextVisible(false);
    }


    public void setMapState(){
        ArcGISRuntimeEnvironment.setLicense("runtimelite,1000,rud5799562951,none,PM0RJAY3FLLR2B3TR001");
        MapUtil.getInstance(this).setshanghai(mMapView);
        mAAtt.initD().mFeatureListen=this;
        if (XJorYH==0){
            mAAtt.initD().addRoadImageLayer(mArcGISMapVector, AppConfig.XJRoadImageLayerurl,this);
        }else {
            mAAtt.initD().addRoadImageLayer(mArcGISMapVector, AppConfig.YHRoadImageLayerurl,this);
        }

    }

    @Override
    public void getFeatureResult(Feature mFeature,int sign) {
//        mAAtt.initD().addgOverlayRoad(mMapView, mFeature.getGeometry());
        mMapView.setViewpointGeometryAsync(mFeature.getGeometry(), 50);
        if (sign==2){
            mAAtt.initB().roadgraphicsOverlay.getGraphics().add(mAAtt.initB().getGraphicroad(mFeature.getGeometry()));
        }
        }
}
