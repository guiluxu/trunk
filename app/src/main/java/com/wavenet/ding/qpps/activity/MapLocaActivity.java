package com.wavenet.ding.qpps.activity;

import android.view.View;

import com.dereck.library.base.BaseMvpActivity;
import com.dereck.library.utils.ToastUtils;
import com.dereck.library.view.LoadingWaitView;
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.interf.AddLayerListen;
import com.wavenet.ding.qpps.interf.CallBackMap;
import com.wavenet.ding.qpps.mvp.p.ErrorcorrectionRequestPresenter;
import com.wavenet.ding.qpps.mvp.v.RequestView;
import com.wavenet.ding.qpps.utils.AppAttribute;
import com.wavenet.ding.qpps.utils.MapUtil;
import com.wavenet.ding.qpps.utils.MapdownloadUtil;

//查看巡检事件定位
public class MapLocaActivity extends BaseMvpActivity <RequestView, ErrorcorrectionRequestPresenter>implements View.OnClickListener,CallBackMap, LoadingWaitView.OnRestLoadListener,AddLayerListen  {
    String strMapUrl;
    LoadingWaitView mLoadingWaitView;
    public MapView mMapView;
    public ArcGISMap mArcGISMapVector;
    AppAttribute mAAtt;
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
    @Override
    public void getImageLayer(ArcGISMapImageLayer mImageLayer) {



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
        try{
            String lat=getIntent().getStringExtra("lat");
            String lon=getIntent().getStringExtra("lon");
            if (Double.parseDouble(lat)==0||Double.parseDouble(lon)==0){
                ToastUtils.showToast("事件位置信息错误");
                return;
            }
            Point p=new Point(Double.parseDouble(lat),Double.parseDouble(lon),mMapView.getSpatialReference());
            mAAtt.initD().addgOverlayLoca(mMapView,p);
        }catch (Exception e){

        }

    }

}
