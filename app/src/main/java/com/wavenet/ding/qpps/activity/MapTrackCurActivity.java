package com.wavenet.ding.qpps.activity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.dereck.library.base.BaseMvpActivity;
import com.dereck.library.utils.ToastUtils;
import com.dereck.library.view.LoadingWaitView;
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.db.TrackBiz;
import com.wavenet.ding.qpps.db.bean.TrackBean;
import com.wavenet.ding.qpps.interf.AddFeatureResultListen;
import com.wavenet.ding.qpps.interf.CallBackMap;
import com.wavenet.ding.qpps.mvp.p.ErrorcorrectionRequestPresenter;
import com.wavenet.ding.qpps.mvp.v.RequestView;
import com.wavenet.ding.qpps.utils.AppAttribute;
import com.wavenet.ding.qpps.utils.AppConfig;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.MapUtil;
import com.wavenet.ding.qpps.utils.MapdownloadUtil;

import java.util.List;

///查看巡检,养护当天轨迹记录
public class MapTrackCurActivity extends BaseMvpActivity <RequestView, ErrorcorrectionRequestPresenter>implements View.OnClickListener,CallBackMap, LoadingWaitView.OnRestLoadListener ,AddFeatureResultListen {
    String strMapUrl;
    LoadingWaitView mLoadingWaitView;
    public MapView mMapView;
    public ArcGISMap mArcGISMapVector;
    AppAttribute mAAtt;
    String   S_RECORD_ID="";
    TrackBiz mTrackBiz;
    String   S_ROAD_ID="";//养护的道路id
    int  XJorYH=0;//0巡检，1养护；
    @Override
    public int getLayoutId() {
        return R.layout.activity_roadmap;
    }

    @Override
    public void init() {
        String title=getIntent().getStringExtra("title");
        setTitle("轨迹查看");
        initView();
        S_RECORD_ID=getIntent().getStringExtra("S_RECORD_ID");
        XJorYH=getIntent().getIntExtra("XJorYH",0);
        S_ROAD_ID=getIntent().getStringExtra("S_ROAD_ID");
        mTrackBiz=new TrackBiz(this);
//        S_RECORD_ID=  "XJ1540364722200xjtest";
        strMapUrl = MapUtil.getInstance(this).getvtppath();
//        new MapdownloadUtil(this).setCallBackMap(this).set();
//        new MapdownloadUtil(this).setCallBackMap(this).requestMapbase(mLoadingWaitView);
        MapdownloadUtil  mapdownloadUtil=new MapdownloadUtil(this).setCallBackMap(this);
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
//                mLoadingWaitView.setVisibility(View.GONE);
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

        //数据库查询轨迹
        if (AppTool.isNull(S_RECORD_ID)){
            mLoadingWaitView.setVisibility(View.GONE);
            ToastUtils.showToast("轨迹id为空");
            return;
        }
        if (XJorYH==1){  // 养护道路高亮查询处理
            mAAtt.initD().mFeatureListen=this;
            mAAtt.initD().seachFeature("S_ROAD_ID='"+S_ROAD_ID+"'", AppConfig.YHRoadurl,2);
            mAAtt.initB().roadgraphicsOverlay=new GraphicsOverlay();
            if (!mMapView.getGraphicsOverlays().contains(mAAtt.initB().roadgraphicsOverlay)) {
                mMapView.getGraphicsOverlays().add(mAAtt.initB().roadgraphicsOverlay);
            }
        }
        // 本地数据库查询轨迹缓冲
        final List<TrackBean> taskTack= mTrackBiz.findTaskTrack(S_RECORD_ID);
        mLoadingWaitView.setVisibility(View.GONE);
        if (taskTack==null|| taskTack.size()==0){
            showDialog();
//            finish();
            return;
        }
        final Point p=new Point(taskTack.get(0).n_lony, taskTack.get(0).n_latx, SpatialReferences.getWgs84());
        mMapView.setViewpointCenterAsync(p,10000);
        // 画轨迹
        mAAtt.getASingle().drawTaskTrack(mMapView,taskTack);
    }
    public void showDialog() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 有白色背
        window.setContentView(R.layout.dialog_tips);
        window.findViewById(R.id.version_describe).setVisibility(View.GONE);
        TextView tv_content = window.findViewById(R.id.tv_content);
        tv_content.setText("没有查询到当天轨迹");
        TextView tv_no = window.findViewById(R.id.tv_no);
        tv_no.setVisibility(View.GONE);
        window.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                finish();
            }
        });
    }

    @Override
    public void getFeatureResult(Feature mFeature, int sign) {
        if (sign==2){
            mAAtt.initB().roadgraphicsOverlay.getGraphics().add(mAAtt.initB().getGraphicroad(mFeature.getGeometry()));
        }
    }
}
