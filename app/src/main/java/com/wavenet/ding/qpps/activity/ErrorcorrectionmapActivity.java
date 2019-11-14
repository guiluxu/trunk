package com.wavenet.ding.qpps.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.dereck.library.base.BaseMvpActivity;
import com.dereck.library.utils.ToastUtils;
import com.dereck.library.view.LoadingWaitView;
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.esri.arcgisruntime.layers.ArcGISMapImageSublayer;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.view.Callout;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.api.QPSWApplication;
import com.wavenet.ding.qpps.interf.AddLayerListen;
import com.wavenet.ding.qpps.interf.CallBackMap;
import com.wavenet.ding.qpps.mvp.p.ErrorcorrectionRequestPresenter;
import com.wavenet.ding.qpps.mvp.v.RequestView;
import com.wavenet.ding.qpps.utils.AppConfig;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.MapUtil;
import com.wavenet.ding.qpps.utils.MapdownloadUtil;

import static com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;

//管理员版本纠错地图选地址页面
public class ErrorcorrectionmapActivity extends BaseMvpActivity <RequestView, ErrorcorrectionRequestPresenter>implements View.OnClickListener,CallBackMap, LoadingWaitView.OnRestLoadListener,AddLayerListen ,OnGeocodeSearchListener {
    String strMapUrl;
    LoadingWaitView mLoadingWaitView;
    public MapView mMapView;
    public ArcGISMap mArcGISMapVector;
    public ArcGISMapImageLayer mainMapImageLayer;
    public static GraphicsOverlay onClickMarkergraphicsOverlay;//marker指定图层
    Callout mCallout;
    Drawable drawable ;
    PictureMarkerSymbol pictureMarkerSymbol,pictureMarkerSymbolnull;
    Graphic graphicimg,graphicimgnull;
    String id;// 当前位置id
    TextView mTvaddr;
    View viewContent;
    Point point84;
    GeocodeSearch geocoderSearch;
    @Override
    public int getLayoutId() {
        return R.layout.activity_errorcorrectionmap;
    }

    @Override
    public void init() {
        setTitle("纠错上报");
        mLoadingWaitView = findViewById(R.id.loadingWaitView);
        mLoadingWaitView.setRestLoadListener(this);
        mLoadingWaitView.setVisibility(View.VISIBLE);
        mMapView = (MapView) findViewById(R.id.mapv);
    }

    @Override
    public void requestData() {
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        strMapUrl = MapUtil.getInstance(this).getvtppath();
//        new MapdownloadUtil(this).setCallBackMap(this).set();
        new MapdownloadUtil(this).setCallBackMap(this).requestMapbase(mLoadingWaitView);
        setGeocodeState();

    }
public void setGeocodeState(){
    drawable =getResources().getDrawable(R.mipmap.btn_location_shijian);
     pictureMarkerSymbolnull = new PictureMarkerSymbol((BitmapDrawable) drawable);
    pictureMarkerSymbolnull.setOffsetX(0);
    pictureMarkerSymbolnull.setOffsetY(35);
     pictureMarkerSymbol = new PictureMarkerSymbol((BitmapDrawable) drawable);
    pictureMarkerSymbol.setOffsetX(0);
    pictureMarkerSymbol.setOffsetY(15);
     mCallout = mMapView.getCallout();
    setCallout();
}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.dispose();
    }

    @Override
    public void onClick(View v) {
switch (v.getId()){
    case R.id.tv_sure:
        if("正在查询...".equals(mTvaddr.getText().toString())||AppTool.isNull(mTvaddr.getText().toString())){
            ToastUtils.showToast("正在查找位置");
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("addr", mTvaddr.getText().toString());
        intent.putExtra("point84_X",point84.getX()+"");
        intent.putExtra("point84_Y",point84.getY()+"");
        setResult(2, intent);
        finish();

        break;
}
    }

    @Override
    public void onClick(String mOnClick) {
        switch (mOnClick) {
            case "DOWNLOADSUCCESS":
                mLoadingWaitView.setVisibility(View.GONE);
                if (!MapUtil.getInstance(this).isExistspath(MapdownloadUtil.strMapUrl))
                    return;
                initGraphicsOverlay();
                mArcGISMapVector = MapUtil.getInstance(this).getArcGISMapVector(MapdownloadUtil.strMapUrl, mMapView);
                mainMapImageLayer = MapUtil.getInstance(this).getMapImageLayer();
                MapUtil.getInstance(this).addMapService(mainMapImageLayer, mArcGISMapVector, this, false);
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
        if (mImageLayer.getLoadStatus() == LoadStatus.LOADED) {
            mLoadingWaitView.setVisibility(View.VISIBLE);
            mainMapImageLayer = mImageLayer;
            if (!mMapView.getGraphicsOverlays().contains(onClickMarkergraphicsOverlay)) {
                mMapView.getGraphicsOverlays().add(onClickMarkergraphicsOverlay);
            }
            onClickMarkergraphicsOverlay.getGraphics().clear();
            point84=QPSWApplication.maBean.mCenterPoint;
            Graphic graphicimg = new Graphic(point84,   pictureMarkerSymbol);
            final Graphic  graphicimg1 = new Graphic(point84, pictureMarkerSymbolnull);
            onClickMarkergraphicsOverlay.getGraphics().add(graphicimg);
            reverseGeocode(point84);
            changeLayer(QPSWApplication.maBean.index);
            new Handler().postDelayed(new Runnable(){
                public void run() {
                    mCallout.setGeoElement(graphicimg1,null);
                    mCallout.show();
                }
            }, 1000);

//            if (ismLegendinit) {
//                mLegend.onClick(mLegend.mIvguanwangys);
//            } else {
//                mLegend.initShowLayerchange();
//            }
    }else {
            ToastUtils.showToast(" 图层加载失败");
        }

    }

    @Override
    public void getImageLayerJc(ArcGISMapImageLayer mImageLayer) {

    }

    public void initGraphicsOverlay(){
        onClickMarkergraphicsOverlay = new GraphicsOverlay();
    }
    public void setMapState(){
        ArcGISRuntimeEnvironment.setLicense("runtimelite,1000,rud5799562951,none,PM0RJAY3FLLR2B3TR001");
        mMapView.setOnTouchListener(new MapViewTouchListener(this, mMapView));
        MapUtil.getInstance(this).setshanghai(mMapView);
    }
    public void changeLayer(int urlid) {//根据图层ID判断打开哪个图层
        switch (urlid) {
            case AppConfig.PSBZ:
                isShowLayer(true, AppConfig.IndexPSBZ);
                break;
            case AppConfig.PSGD:
                isShowLayer(  true,AppConfig.IndexPSGD);
                break;
            case AppConfig.PSGDJ1:
                isShowLayer( true, AppConfig.IndexPSGD);
                break;
            case AppConfig.PSGDWS:
                isShowLayer(true, AppConfig.IndexPSGDWS);//污水地图图层2下标为2，
                break;
            case AppConfig.PSGDWSJ1:
                isShowLayer(true, AppConfig.IndexPSGDWS);//污水地图图层2下标为2，
                break;
            case AppConfig.WSCLC:
                isShowLayer( true,AppConfig.IndexWSCLC);
                break;
            case AppConfig.PSH:
                isShowLayer(true,AppConfig.IndexPSH);
                break;
            case AppConfig.ZDPFK:
                isShowLayer(true, AppConfig.IndexZDPFK);
                break;
            case AppConfig.PFK:
                isShowLayer(true,AppConfig.IndexPFK);
                break;
        }

    }
    public void isShowLayer(Boolean isSelete, int index) {
//        ToastUtils.showToast(isSelete+"-----"+index);
        if (index== 2001 ){//雨水管网
            //管
            ArcGISMapImageSublayer mImageSublayerG= (ArcGISMapImageSublayer)mainMapImageLayer.getSubLayerContents().get(2).getSubLayerContents().get(0);
            mImageSublayerG.setVisible(isSelete);
            loopImageSublayer(mImageSublayerG,isSelete);
            //井
            ArcGISMapImageSublayer mImageSublayerJ= (ArcGISMapImageSublayer) mainMapImageLayer.getSubLayerContents().get(3).getSubLayerContents().get(0);
            mImageSublayerJ.setVisible(isSelete);
            loopImageSublayer(mImageSublayerJ,isSelete);
        }else  if (index == 2002 ){//污水管网
            //管
            ArcGISMapImageSublayer mImageSublayerG= (ArcGISMapImageSublayer) mainMapImageLayer.getSubLayerContents().get(2).getSubLayerContents().get(1);
            mImageSublayerG.setVisible(isSelete);
            loopImageSublayer(mImageSublayerG,isSelete);
            //井
            ArcGISMapImageSublayer mImageSublayerJ= (ArcGISMapImageSublayer) mainMapImageLayer.getSubLayerContents().get(3).getSubLayerContents().get(1  );
            mImageSublayerJ.setVisible(isSelete);
            loopImageSublayer(mImageSublayerJ,isSelete);
        }else {//其他图层（单个图层架构）
            ArcGISMapImageSublayer mImageSublayer= (ArcGISMapImageSublayer) mainMapImageLayer.getSubLayerContents().get(index);
            loopImageSublayer(mImageSublayer,isSelete);
        }
    }
    public void loopImageSublayer(ArcGISMapImageSublayer mISublayer,Boolean b){
        int size=mISublayer.getSubLayerContents().size();
        if(size>0){
            for (int i = 0; i <size ; i++) {
                ArcGISMapImageSublayer mIs= (ArcGISMapImageSublayer) mISublayer.getSubLayerContents().get(i);
                loopImageSublayer(mIs,b);
            }
        }else {
            if (!AppTool.isNull(QPSWApplication.maBean.OBJECTID+"")){
                mISublayer.setDefinitionExpression("OBJECTID="+QPSWApplication.maBean.OBJECTID);
            }
//            ToastUtils.showToast(QPSWApplication.maBean.OBJECTID+""+"-----");
            mISublayer.setVisible(b);
        }
    }
    public void setCallout(){
        LayoutInflater inflater = LayoutInflater.from(this);
        viewContent =  inflater.inflate(R.layout.view_calloutcontent,null);
        viewContent.findViewById(R.id.tv_sure).setOnClickListener(this);
        mTvaddr=viewContent.findViewById(R.id.tv_addr);
//    mTvaddr.setTextIsSelectable(true);
//    Callout.Style cs1=new Callout.Style(this,R.xml.callout_style);
        Callout.Style cs=new Callout.Style(ErrorcorrectionmapActivity.this);
        cs.setBorderColor(getResources().getColor(R.color.app_bg));
        cs.setBorderWidth(1);
        cs.setCornerRadius(10);
        cs.setLeaderPosition(Callout.Style.LeaderPosition.AUTOMATIC); //设置指示性位置
        cs.setBackgroundColor(getResources().getColor(R.color.white));
        mCallout.setStyle(cs);
        mCallout.setContent(viewContent);
    }
    @Override
    protected ErrorcorrectionRequestPresenter createPresenter() {
        return new ErrorcorrectionRequestPresenter() {
        };
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        mLoadingWaitView.setVisibility(View.GONE);
        if (i>0){
            RegeocodeAddress r=regeocodeResult.getRegeocodeAddress();
            mTvaddr.setText(r.getFormatAddress());
            mCallout.getStyle().setLeaderPosition(Callout.Style.LeaderPosition.LOWER_MIDDLE);
        }
    }

    @Override
    public void onGeocodeSearched(com.amap.api.services.geocoder.GeocodeResult geocodeResult, int i) {
//        mTvaddr.setText(geocodeResult.getGeocodeAddressList().size());
//        mCallout.getStyle().setLeaderPosition(Callout.Style.LeaderPosition.LOWER_MIDDLE);
    }

    class MapViewTouchListener extends DefaultMapViewOnTouchListener {
        Graphic graphicimg1;
        public MapViewTouchListener(Context context, MapView mapView) {
            super(context, mapView);
        }

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (graphicimg1!=null){
                mCallout.setGeoElement(graphicimg1,null);
                mCallout.getStyle().setLeaderPosition(Callout.Style.LeaderPosition.AUTOMATIC); //设置指示性位置
//                mCallout.show();
            }
            return super.onTouch(view, event);

        }

        @Override
        public boolean onRotate(MotionEvent event, double rotationAngle) {
            return false;
        }

        @Override
        public boolean onSingleTapConfirmed(final MotionEvent e) {
            android.graphics.Point screenPoint = new android.graphics.Point((int) e.getX(), (int) e.getY());
            point84 = mMapView.screenToLocation(screenPoint);
            onClickMarkergraphicsOverlay.getGraphics().clear();
            Graphic graphicimg = new Graphic(point84, pictureMarkerSymbol);
             graphicimg1 = new Graphic(point84, pictureMarkerSymbolnull);
            onClickMarkergraphicsOverlay.getGraphics().add(graphicimg);
//            setCallout();
            mCallout.setGeoElement(graphicimg1,null);
            reverseGeocode(point84);
            return super.onSingleTapConfirmed(e);
        }

    }
    /**
     * Uses the locator task to reverse geocode for the given point.
     *
     * @param point on which to perform the reverse geocode
     */
    private void reverseGeocode(Point point) {
        mMapView.setViewpointCenterAsync(point84,1000);
        mTvaddr.setText("正在查询...");
        RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(point84.getY(),point84.getX()), 200,GeocodeSearch.GPS);
        geocoderSearch.getFromLocationAsyn(query);
}
}
