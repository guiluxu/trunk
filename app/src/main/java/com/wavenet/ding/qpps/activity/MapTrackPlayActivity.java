package com.wavenet.ding.qpps.activity;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dereck.library.base.BaseMvpActivity;
import com.dereck.library.utils.ToastUtils;
import com.dereck.library.view.LoadingWaitView;
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.google.gson.Gson;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.db.TrackBiz;
import com.wavenet.ding.qpps.db.bean.TrackBean;
import com.wavenet.ding.qpps.db.bean.TrackPalyBean;
import com.wavenet.ding.qpps.interf.AddFeatureResultListen;
import com.wavenet.ding.qpps.interf.CallBackMap;
import com.wavenet.ding.qpps.mvp.p.MapTrackPlayRequestPresenter;
import com.wavenet.ding.qpps.mvp.v.RequestView;
import com.wavenet.ding.qpps.utils.AppAttribute;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.MapUtil;
import com.wavenet.ding.qpps.utils.MapdownloadUtil;

import java.util.List;

///查看巡检,养护当天轨迹记录
public class MapTrackPlayActivity extends BaseMvpActivity <RequestView, MapTrackPlayRequestPresenter>implements RequestView, View.OnClickListener,CallBackMap, LoadingWaitView.OnRestLoadListener ,AddFeatureResultListen {
    String strMapUrl;
    LoadingWaitView mLoadingWaitView;
    public MapView mMapView;
    public ArcGISMap mArcGISMapVector;
    AppAttribute mAAtt;
    AppAttribute.A mAAttAper,mAAttAaft;
    String   S_RECORD_ID="";
    TrackBiz mTrackBiz;
    String   S_ROAD_ID="";//养护的道路id
    int  XJorYH=0;//0巡检，1养护；

    TextView mTvname,      mTvtime ,mTvdistance,mTvagainpaly;
    CheckBox mCbtrack;
    IndicatorSeekBar mIsbprogress;
    ImageView mIvtarckpaly;
    TrackPalyBean mTarckp;
    List<TrackBean> taskTack;//过滤速度为0；
    int referenceTime=10000;
    int speed=1000;
    public GraphicsOverlay onSEgraphicsOverlay;//起终点marck；
    PictureMarkerSymbol pictureMarkerSymbolend;
    PictureMarkerSymbol pictureMarkerSymbolstar;
    PictureMarkerSymbol pictureMarkerSymbolcar;
    @Override
    public int getLayoutId() {
        return R.layout.activity_tarckplay;
    }

    @Override
    public void init() {
        setTitle("轨迹查看");
        initView();
        MapdownloadUtil  mapdownloadUtil=new MapdownloadUtil(this).setCallBackMap(this);
        mapdownloadUtil.Rxhttp();
    }
public boolean isCheckedtrack=false;
    @Override
    public void requestData() {
        S_RECORD_ID=getIntent().getStringExtra("S_RECODE_ID");
        String time=getIntent().getStringExtra("time");
        String xclc=getIntent().getStringExtra("xclc");
        String s_man_name=getIntent().getStringExtra("s_man_name");
        mTvname.setText(AppTool.getNullStr(s_man_name));
        mTvtime.setText(AppTool.getNullStr(time));
        mTvdistance.setText(AppTool.getNullStr(xclc)+"m");
        mCbtrack.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               isCheckedtrack=isChecked;
                indexTrack=0;
                mAAttAaft.cleanR(mMapView);
//                if (isChecked) {
//                    mAAttAper.drawTaskTrack(mMapView,mTarckp);
//                }else {
//                    mAAttAper.drawTaskTrack(mMapView,taskTack);
//                }
                }

        });
        mIsbprogress.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                speed=referenceTime/seekParams.progress;
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {
                speed=referenceTime/seekBar.getProgress();
            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

                 speed=referenceTime/seekBar.getProgress();
            }
        });
        onSEgraphicsOverlay=new GraphicsOverlay();
        getMarkerSymbol();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.dispose();
    }
    @Override
    public void onClick(View v) {
switch (v.getId()){
    case R.id.iv_tarckpaly:
        mIvtarckpaly.setSelected(!mIvtarckpaly.isSelected());
        if (!mIvtarckpaly.isSelected()){
            handler.sendEmptyMessageDelayed(1, 0);
        }
        break;
        case R.id.tv_againpaly:
            mIvtarckpaly.setVisibility(View.VISIBLE);
            mTvagainpaly.setVisibility(View.GONE);
            indexTrack=0;
            mAAttAaft.cleanR(mMapView);
        break;
}
    }
    @Override
    protected MapTrackPlayRequestPresenter createPresenter() {
        return new MapTrackPlayRequestPresenter() {
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
                mAAttAper=mAAtt.initA();
                mAAttAaft=mAAtt.initA();
                mAAttAper.setR(mMapView);
                mAAttAaft.colorfalg= Color.BLUE;
                mAAttAaft.setR(mMapView);
                if (!mMapView.getGraphicsOverlays().contains(onSEgraphicsOverlay)){
                    mMapView.getGraphicsOverlays().add(onSEgraphicsOverlay);
                }
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
//        new MapdownloadUtil(this).setCallBackMap(this);
    }
    @Override
    public void getFeatureResult(Feature mFeature, int sign) {
        if (sign==2){
//            mAAtt.initB().roadgraphicsOverlay.getGraphics().add(mAAtt.initB().getGraphicroad(mFeature.getGeometry()));
        }
    }
    public void initView(){
        mLoadingWaitView = findViewById(R.id.loadingWaitView);
        mLoadingWaitView.setRestLoadListener(this);
        mLoadingWaitView.setVisibility(View.VISIBLE);
        mMapView = (MapView) findViewById(R.id.mapv);
        mMapView.setAttributionTextVisible(false);
        mCbtrack = (CheckBox) findViewById(R.id.cb_track);
         mTvname = (TextView) findViewById(R.id.tv_name);
         mTvtime = (TextView) findViewById(R.id.tv_time);
         mTvdistance = (TextView) findViewById(R.id.tv_distance);
        mTvagainpaly = (TextView) findViewById(R.id.tv_againpaly);
        mTvagainpaly.setVisibility(View.GONE);
        mTvagainpaly.setOnClickListener(this);
         mIsbprogress = (IndicatorSeekBar) findViewById(R.id.isb_progresstrack);
        mIsbprogress.setProgress(10);
        mIvtarckpaly = (ImageView) findViewById(R.id.iv_tarckpaly);
        mIvtarckpaly.setVisibility(View.VISIBLE);
        mIvtarckpaly.setOnClickListener(this);
        mIvtarckpaly.setSelected(true);
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

        presenter.requestTrack(S_RECORD_ID);

        }
int indexTrack=0;
Graphic graphiccar=null;
public void drawtarcspeed(){
    Point p=null;
    try {
        if (isCheckedtrack){
            if (indexTrack<taskTack.size()){
                p=new Point(taskTack.get(indexTrack).n_lony, taskTack.get(indexTrack).n_latx, SpatialReferences.getWgs84());

            }else {
                mIvtarckpaly.setVisibility(View.GONE);
                mTvagainpaly.setVisibility(View.VISIBLE);
            }
        }else {
            if (indexTrack<mTarckp.Data.Data.get(0).Data.size()){
                p=new Point(mTarckp.Data.Data.get(0).Data.get(indexTrack).NX, mTarckp.Data.Data.get(0).Data.get(indexTrack).NY, SpatialReferences.getWgs84());
            }else {
                mIvtarckpaly.setVisibility(View.GONE);
                mTvagainpaly.setVisibility(View.VISIBLE);
            }
        }
        if (p!=null){
//            mMapView.setViewpointCenterAsync(p,10000);

            mAAttAaft.drawR(mMapView,p.getY(),p.getX());
            if (graphiccar!=null){
                onSEgraphicsOverlay.getGraphics().remove(graphiccar);
            }
            graphiccar = new Graphic(p, pictureMarkerSymbolcar);
            onSEgraphicsOverlay.getGraphics().add(graphiccar);
        }
    }catch (Exception e){//捕获异常防止后台返回的坐标为空，

    }
    indexTrack++;
//    speed=referenceTime/mIsbprogress.getProgress();
    handler.sendEmptyMessageDelayed(1,speed );

}
Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (!mIvtarckpaly.isSelected()){
                drawtarcspeed();
            }

        }
    };
public  void getMarkerSymbol(){
    BitmapDrawable startDrawable = (BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.ico_shes_guij_begin);
    BitmapDrawable endDrawable = (BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.ico_shes_guij_over);
    BitmapDrawable carDrawable = (BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.btn_map_xunj_ricxc);
    pictureMarkerSymbolend = new PictureMarkerSymbol((BitmapDrawable) endDrawable);
    pictureMarkerSymbolstar = new PictureMarkerSymbol((BitmapDrawable) startDrawable);
    pictureMarkerSymbolcar = new PictureMarkerSymbol((BitmapDrawable) carDrawable);
    pictureMarkerSymbolend.setOffsetY(10);
    pictureMarkerSymbolend.setOffsetX(0);
    pictureMarkerSymbolstar.setOffsetY(10);
    pictureMarkerSymbolstar.setOffsetX(0);
    pictureMarkerSymbolcar.setOffsetY(10);
    pictureMarkerSymbolcar.setOffsetX(0);
}
    @Override
    public void requestSuccess(int resultid, String result) {
        if (!AppTool.isNull(result) && !result.contains("error")) {
            switch (resultid) {
                case 1://事件上报详情

                     mTarckp = new Gson().fromJson(result, TrackPalyBean.class);

                    if (mTarckp.Code!=200){
                        ToastUtils.showToast(mTarckp.Msg);
                        return;
                    }
                    if (mTarckp.Data==null||mTarckp.Data.Data==null||mTarckp.Data.Data.size()==0||mTarckp.Data.Data.get(0).Data==null||mTarckp.Data.Data.get(0).Data.size()==0){
                        ToastUtils.showToast("没有查询到轨迹");
                        return;
                    }
                     Point ps=new Point(mTarckp.Data.Data.get(0).Data.get(0).NX, mTarckp.Data.Data.get(0).Data.get(0).NY, SpatialReferences.getWgs84());
                    int size=mTarckp.Data.Data.get(0).Data.size();
                     Point pe=new Point(mTarckp.Data.Data.get(0).Data.get(size-1).NX, mTarckp.Data.Data.get(0).Data.get(size-1).NY, SpatialReferences.getWgs84());
                    mMapView.setViewpointCenterAsync(ps,10000);
                    Graphic graphicimgs = new Graphic(ps, pictureMarkerSymbolstar);
                    Graphic graphicimge = new Graphic(pe, pictureMarkerSymbolend);
                    onSEgraphicsOverlay.getGraphics().add(graphicimgs);
                    onSEgraphicsOverlay.getGraphics().add(graphicimge);
                    mAAttAper.drawTaskTrack(mMapView,mTarckp);
                    taskTack=mTarckp.getTrackBeanL(mTarckp.Data.Data.get(0).Data);
                    handler.sendEmptyMessageDelayed(1, 0);
                    break;}}
    }

    @Override
    public void show() {
showDialog();
    }

    @Override
    public void hide() {

        mLoadingWaitView.setVisibility(View.GONE);
        cancelDialog();
    }

    @Override
    public void requestFailure(int resultid, String result) {

    }


}
