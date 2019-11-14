package com.wavenet.ding.qpps.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.dereck.library.utils.ToastUtils;
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.esri.arcgisruntime.layers.ArcGISMapImageSublayer;
import com.esri.arcgisruntime.layers.ArcGISVectorTiledLayer;
import com.esri.arcgisruntime.layers.WebTiledLayer;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.activity.MapJCActivity;
import com.wavenet.ding.qpps.adapter.MapLegendAdapter;
import com.wavenet.ding.qpps.api.QPSWApplication;
import com.wavenet.ding.qpps.bean.Legendbean;
import com.wavenet.ding.qpps.interf.CallBackMap;
import com.wavenet.ding.qpps.interf.InterfListen;
import com.wavenet.ding.qpps.utils.AppAttribute;
import com.wavenet.ding.qpps.utils.AppConfig;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.LogUtils;
import com.wavenet.ding.qpps.utils.MapUtil;
import com.wavenet.ding.qpps.utils.MapdownloadUtil;
import com.wavenet.ding.qpps.utils.SPUtil;
import com.wavenet.ding.qpps.utils.TianDiTuMethodsClass;


public class ViewMapUtil extends LinearLayout implements View.OnClickListener, MapLegendAdapter.A, CallBackMap, InterfListen.G {
    Context mContext;
    ImageView mIvshanghai, mIvPhoto, mIvLocation, mIvtuceng;
    TextView mTvplus, mTvminus;
    public ViewMapLegend mLegend;
    MapView mMapView;
    public ArcGISMap mArcGISMapVector;
    public ArcGISMapImageLayer mainMapImageLayerjc;//jiance
    public GraphicsOverlay mLocationOverlay;//定位图层
    String des;
    public AppAttribute mAAtt;
    boolean ismLegendinit = true;

    public ViewMapUtil(Context context) {
        super(context);
        initView(context);
    }

    public ViewMapUtil(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ViewMapUtil(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        setOrientation(LinearLayout.VERTICAL);
        setOnClickListener(this);
        LayoutInflater.from(mContext).inflate(R.layout.view_map, this);
        if (!isInEditMode()) {

            mMapView = findViewById(R.id.mapv);
            mMapView.setAttributionTextVisible(false);
            mIvtuceng = findViewById(R.id.iv_tuceng);
            mIvtuceng.setOnClickListener(this);
            mIvshanghai = findViewById(R.id.iv_shanghai);
            mIvshanghai.setOnClickListener(this);
            mIvLocation = findViewById(R.id.iv_location);
            mIvLocation.setOnClickListener(this);
            mTvplus = findViewById(R.id.tv_plus);
            mTvplus.setOnClickListener(this);
            mTvminus = findViewById(R.id.tv_minus);
            mTvminus.setOnClickListener(this);
            mLegend = findViewById(R.id.vml_legend);
            mLegend.setAListener(this);
            mLegend.setCallBackMap(this);
            mLegend.setIGListener(this);
            initData();
        }
    }

    public void initData() {
        des=  SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_DES);
        mLocationOverlay = new GraphicsOverlay();
        setGOverlays(mLocationOverlay);
        mAAtt = new AppAttribute(mContext);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_tuceng:
                if (mainMapImageLayerjc != null && mainMapImageLayerjc.getLoadStatus() != LoadStatus.LOADED) {
                    ToastUtils.showToast("业务图层加载失败，重新加载请稍后");
                    initImageLayerJc();
                    return;
                }
                mLegend.showOrHide("");
                break;
            case R.id.iv_shanghai:
//        mActivity. mMapView.getMapScale();
//        LogUtils.e("ddddddddddddddd",mActivity. mMapView.getMapScale()+"") ;
                MapUtil.getInstance(mContext).setshanghai(mMapView);

                break;
            case R.id.iv_location:
                if (QPSWApplication.Locpoint != null) {
                    mMapView.setViewpointCenterAsync(QPSWApplication.Locpoint, 10000);
                }
                break;
            case R.id.tv_plus:

//        ToastUtils.showToast(    mActivity.mMapView.getMapScale()+"");
                mMapView.setViewpointScaleAsync(mMapView.getMapScale() * 0.5);
                break;
            case R.id.tv_minus:
//        ToastUtils.showToast(    mActivity.mMapView.getMapScale()+"");
                mMapView.setViewpointScaleAsync(mMapView.getMapScale() / 0.5);
                break;
        }
    }

    public Legendbean legendbean;

    @Override
    public void toLlselected(Legendbean b) {
        legendbean = b;
        showLayer(b);
    }

    @Override
    public void toIvselected(Legendbean b) {
        showLayer(b);
    }

    @Override
    public void onClick(String mOnClick) {
        switch (mOnClick) {
            case "TDTVEC":
                ismLegendinit = false;
                ArcGISVectorTiledLayer mainArcGISVectorTiledLayer = new ArcGISVectorTiledLayer(MapdownloadUtil.strMapUrl);
                Basemap mainBasemap = new Basemap(mainArcGISVectorTiledLayer);
                mArcGISMapVector = new ArcGISMap(mainBasemap);
                mMapView.setMap(mArcGISMapVector);
                initImageLayerJc();
                mMapView.setViewpointGeometryAsync(QPSWApplication.CenterpointPolygon);
                break;
            case "TDTIMG":
                ismLegendinit = false;
                WebTiledLayer webTiledLayer = TianDiTuMethodsClass.CreateTianDiTuTiledLayer(TianDiTuMethodsClass.LayerType.TIANDITU_IMAGE_2000);
                WebTiledLayer webTiledLayer1 = TianDiTuMethodsClass.CreateTianDiTuTiledLayer(TianDiTuMethodsClass.LayerType.TIANDITU_IMAGE_ANNOTATION_CHINESE_2000);
                Basemap mainBasemap1 = new Basemap(webTiledLayer);
                mainBasemap1.getBaseLayers().add(webTiledLayer1);
                mArcGISMapVector = new ArcGISMap(mainBasemap1);
                mMapView.setMap(mArcGISMapVector);
                initImageLayerJc();
                mMapView.setViewpointGeometryAsync(QPSWApplication.CenterpointPolygon);
                break;
        }
    }

    public MapView getMapview() {
        return mMapView;
    }

    public void onResume() {
        if (mMapView == null)
            return;
        mMapView.resume();
    }

    public void onPause() {
        if (mMapView == null)
            return;
        mMapView.pause();
    }

    public void onDestroy() {
        if (mMapView == null)
            return;
        mMapView.dispose();
    }

    public void setMapState() {
        MapUtil.getInstance(mContext).showGpsDialog(mContext, 2);
        mMapView.setAttributionTextVisible(false);
        ArcGISRuntimeEnvironment.setLicense("runtimelite,1000,rud5799562951,none,PM0RJAY3FLLR2B3TR001");
        mArcGISMapVector = MapUtil.getInstance(mContext).getArcGISMapVector(MapdownloadUtil.strMapUrl, mMapView);
        mAAtt.initB().graphicsOverlayInit();
        mAAtt.initB().graphicsOverlayAdd(mMapView);
        initImageLayerJc();
        mLegend .setListbean(MapJCActivity.type);
        MapViewTouchListener mMapViewTouchListener = new MapViewTouchListener(mContext, mMapView);
        mMapView.setOnTouchListener(mMapViewTouchListener);
    }

    public void initImageLayerJc() {
        mainMapImageLayerjc = new ArcGISMapImageLayer(AppConfig.JCUrl);
        mainMapImageLayerjc.loadAsync();
        mainMapImageLayerjc.addDoneLoadingListener(new Runnable() {
            @Override
            public void run() {
                if (mainMapImageLayerjc.getLoadStatus() == LoadStatus.LOADED) {

                        for (int i = 0; i < mainMapImageLayerjc.getSubLayerContents().size(); i++) {
                            ArcGISMapImageSublayer mImageSublayer = (ArcGISMapImageSublayer) mainMapImageLayerjc.getSubLayerContents().get(i);
                            loopImageSublayer(mImageSublayer, false);

                            LogUtils.e("typetype2----",MapJCActivity.type);
                            legendbean=mLegend.getListbean().get(MapJCActivity.type);
                            showLayer(legendbean);
                        }
                    if (!ismLegendinit) {
                        for (int j = 0; j < mLegend.getListbean().size(); j++) {
                            if (mLegend.getListbean().get(j).isSelectIv) {
                                showLayer(mLegend.getListbean().get(j));
                            }
                        }
                    }


                }
            }
        });
        if (!mMapView.getMap().getOperationalLayers().contains(mainMapImageLayerjc)) {
            mMapView.getMap().getOperationalLayers().add(mainMapImageLayerjc);
        }

    }

    public void setLoc(AMapLocation aMapLocation) {
        mLocationOverlay = MapUtil.getInstance(mContext).setLocicon(aMapLocation, mMapView, mLocationOverlay, "");
    }

    public void setGOverlays(GraphicsOverlay gOverlays) {
        if (!mMapView.getGraphicsOverlays().contains(gOverlays)) {
            mMapView.getGraphicsOverlays().add(gOverlays);
        }
    }

    public void Graphicsclear(GraphicsOverlay gOverlay) {
        if (gOverlay != null && gOverlay.getGraphics() != null) {
            gOverlay.getGraphics().clear();
        }
    }

    public void showLayer(Legendbean b) {
        if (b.mImageLayer.contains("在线监测")) {
            ArcGISMapImageSublayer mImageSublayer = (ArcGISMapImageSublayer) mainMapImageLayerjc.getSubLayerContents().get(b.mLayerIndex);
            if (b.mLayer == 0) {
                loopImageSublayer(mImageSublayer, b.isSelectIv);
            } else if (b.mLayer == 1) {
                if (AppConfig.JCbzsname.equals(b.name)) {
                    mImageSublayer.setVisible(b.isSelectIv);
                    ArcGISMapImageSublayer mImageSublayer1 = (ArcGISMapImageSublayer) mImageSublayer.getSubLayerContents().get(b.mLayerIndexArry[1]);
                    loopImageSublayer(mImageSublayer1, b.isSelectIv);
                }
            }
        }
    }

    public void loopImageSublayer(ArcGISMapImageSublayer mISublayer, Boolean b) {
        int size = mISublayer.getSubLayerContents().size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {

                ArcGISMapImageSublayer mIs = (ArcGISMapImageSublayer) mISublayer.getSubLayerContents().get(i);
                loopImageSublayer(mIs, b);
            }
        } else {
            if (!AppTool.isNull(des)) {
//                LogUtils.e("upper(S","S_town='"+townname+"'");
//                mISublayer.setDefinitionExpression("S_town ='白鹤镇'")
                mISublayer.setDefinitionExpression("S_town " + MapUtil.getDes(des));
            }
            mISublayer.setVisible(b);
        }
    }

    public boolean isHadSelectIv() {
        return mLegend.isHadSelectIv();
    }

    @Override
    public MapView gA() {
        return mMapView;
    }


    class MapViewTouchListener extends DefaultMapViewOnTouchListener {
        public MapViewTouchListener(Context context, MapView mapView) {
            super(context, mapView);

        }

        @Override
        public boolean onRotate(MotionEvent event, double rotationAngle) {
            return false;
        }


        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (legendbean==null){
                ToastUtils.showToast("请选择查询图层");
                return true;
            }

            final android.graphics.Point screenPoint = new android.graphics.Point((int) e.getX(), (int) e.getY());
            if (mF != null) {
                mF.fA(e);
            }
            return super.onSingleTapConfirmed(e);
        }
    }

    InterfListen.F mF;
    InterfListen.H mH;

    public void setListen(InterfListen.F mF) {
        this.mF = mF;
    }  public void setIHListen(InterfListen.H mH) {
        mLegend.setIHListen(mH);
    }
}
