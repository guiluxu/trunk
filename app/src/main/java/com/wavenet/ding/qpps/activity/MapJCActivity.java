package com.wavenet.ding.qpps.activity;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.dereck.library.base.BaseMvpActivity;
import com.dereck.library.utils.ToastUtils;
import com.dereck.library.view.LoadingWaitView;
import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.data.QueryParameters;
import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.google.gson.Gson;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.api.QPSWApplication;
import com.wavenet.ding.qpps.bean.AdapterBean;
import com.wavenet.ding.qpps.bean.JCbzAclcBean;
import com.wavenet.ding.qpps.bean.JCswBean;
import com.wavenet.ding.qpps.bean.Legendbean;
import com.wavenet.ding.qpps.bean.RequestDataBean;
import com.wavenet.ding.qpps.interf.AddFeatureResultListen;
import com.wavenet.ding.qpps.interf.AddLayerListen;
import com.wavenet.ding.qpps.interf.CallBackMap;
import com.wavenet.ding.qpps.interf.InterfListen;
import com.wavenet.ding.qpps.mvp.p.JCMapRequestPresenter;
import com.wavenet.ding.qpps.mvp.v.JCMapRequestView;
import com.wavenet.ding.qpps.utils.AppAttribute;
import com.wavenet.ding.qpps.utils.AppConfig;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.AppUtils;
import com.wavenet.ding.qpps.utils.MapUtil;
import com.wavenet.ding.qpps.utils.MapdownloadUtil;
import com.wavenet.ding.qpps.utils.SPUtil;
import com.wavenet.ding.qpps.view.ViewDetailJcbz;
import com.wavenet.ding.qpps.view.ViewDetailJcclc;
import com.wavenet.ding.qpps.view.ViewDetailJcsw;
import com.wavenet.ding.qpps.view.ViewMapUtil;
import com.wavenet.ding.qpps.view.viewutils.scrollLayout.ContentScrollView;
import com.wavenet.ding.qpps.view.viewutils.scrollLayout.ScrollLayoutView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

//查看养护道路
public class MapJCActivity extends BaseMvpActivity<JCMapRequestView, JCMapRequestPresenter> implements JCMapRequestView,View.OnClickListener, CallBackMap, LoadingWaitView.OnRestLoadListener, AddLayerListen, AddFeatureResultListen, InterfListen.F, InterfListen.B ,InterfListen.E,InterfListen.H{
    String strMapUrl;
    LoadingWaitView mLoadingWaitView;
    AppAttribute mAAtt;
    String S_ROAD_ID = "";
    ViewMapUtil viewMapUtil;
    boolean isFristLoca = true;
    private ScrollLayoutView mScrollLayoutView;
    private ContentScrollView mContentScrollView;
    ViewDetailJcsw mViewDetailJcsw;
    ViewDetailJcclc mViewDetailJcclc;
    ViewDetailJcbz mViewDetailJcbz;
    QueryParameters query;
public static int type=0;
public  String CodeId="";
    @Override
    public int getLayoutId() {
        return R.layout.activity_jcmap;
    }

    @Override
    public void init() {
        isRegistered(true, this);
        setTitle("在线监测");
        type=  getIntent().getIntExtra("type",0);
        CodeId=  getIntent().getStringExtra("CodeId");
        initView();
        MapdownloadUtil mapdownloadUtil = new MapdownloadUtil(this).setCallBackMap(this);
        mapdownloadUtil.Rxhttp();

    }

    @Override
    public void requestData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (viewMapUtil !=null){
            viewMapUtil.onResume();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (viewMapUtil !=null){
        viewMapUtil.onPause();
    }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
            if (viewMapUtil !=null){
        viewMapUtil.onDestroy();
    }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventBusCar(AMapLocation aMapLocation) {
        if (MapUtil.isLegalLL(aMapLocation, false)) {
            if (viewMapUtil==null){
                return;
            }
            viewMapUtil.setLoc(aMapLocation);
            if (viewMapUtil.mainMapImageLayerjc != null) {
                if (isFristLoca && QPSWApplication.Locpoint != null) {
                    isFristLoca = !isFristLoca;
//                    viewMapUtil.getMapview().setViewpointCenterAsync(QPSWApplication.Locpoint, 10000);

                }

            }

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    protected JCMapRequestPresenter createPresenter() {
        return new JCMapRequestPresenter() {
        };
    }

    @Override
    public void onClick(String mOnClick) {
        switch (mOnClick) {
            case "DOWNLOADSUCCESS":
                mLoadingWaitView.setVisibility(View.GONE);
                if (!MapUtil.getInstance(this).isExistspath(MapdownloadUtil.strMapUrl))
                    return;
                viewMapUtil = findViewById(R.id.vmu_maptuil);
                viewMapUtil.setIHListen(this);
                mAAtt = new AppAttribute(this);
//                mAAtt.initI().FeatureMessg = "该记录没有养护的道路";
                viewMapUtil.setListen(this);
                viewMapUtil.setMapState();
                getDetail();
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

    }

    @Override
    public void getImageLayerJc(ArcGISMapImageLayer mImageLayer) {

    }

    public void initView() {
        mLoadingWaitView = findViewById(R.id.loadingWaitView);
        mLoadingWaitView.setRestLoadListener(this);
        mLoadingWaitView.setVisibility(View.VISIBLE);
        View v = LayoutInflater.from(this).inflate(getLayoutId(), null);
        mScrollLayoutView = AppUtils.setScrollLayoutView(this, getWindow().getDecorView(), null);
        mContentScrollView = findViewById(R.id.content_scroll_layout);
        mViewDetailJcsw=findViewById(R.id.v_jcsw);
        mViewDetailJcsw.setEListen(this);
        mViewDetailJcclc=findViewById(R.id.v_jcclc);
        mViewDetailJcclc.setEListen(this);
        mViewDetailJcbz=findViewById(R.id.v_jcbz);
        mViewDetailJcbz.setEListen(this);
        setViewScrollHide();
    }


    @Override
    public void getFeatureResult(Feature mFeature, int sign) {
    }

    @Override
    public void bA(final Graphic g, final ArrayList<AdapterBean> abList, int url, final String urlname, int Symbol) {
        if (url == 10000) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.showToast(urlname);
                    mLoadingWaitView.success();
                    mScrollLayoutView.setToExit();

                }
            });
            return;
        }
        if (Symbol == 0) {
            viewMapUtil.mAAtt.initB().onClickgraphicsOverlayPoint.getGraphics().add(g);
        } else {
            viewMapUtil.mAAtt.initB().onClickgraphicsOverlayLine.getGraphics().add(g);
        }
//        viewMapUtil.getMapview().setViewpointCenterAsync(g);0.00001度=1米
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

        viewMapUtil.getMapview().setViewpointGeometryAsync(g.getGeometry());//度跟米的换算
                viewMapUtil.getMapview().setViewpointScaleAsync( 10000);
                mLoadingWaitView.success();
                setViewScrollHide();
                presenter.getJCData_id(viewMapUtil.legendbean.requestIddata, viewMapUtil.legendbean.requesturldata + abList.get(0).mag1);

            }
        });

    }
    @Override
    public void eA(String time, String id) {
        presenter.getJCData_his(viewMapUtil.legendbean.requestIdhis,String.format(viewMapUtil.legendbean.requesturlhis,id,time));
    }
    @Override
    public void fA(MotionEvent e) {
        final android.graphics.Point screenPoint = new android.graphics.Point((int) e.getX(), (int) e.getY());
        onClickSingle(screenPoint);
    }
    @Override
    public void hA(int  isshow) {
        mScrollLayoutView.setVisibility(isshow);
    }
    public void setScrollState(int  time) {
        mScrollLayoutView.setToOpen();
//        mContentScrollView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mContentScrollView.fullScroll(ScrollView.FOCUS_UP);
//            }
//        },time);
    }

    public boolean onClickSingle(android.graphics.Point screenPoint) {
        if (viewMapUtil.getMapview().getMapScale() > 100000)
            return true;
        mLoadingWaitView.setVisibility(View.VISIBLE);
        viewMapUtil.mAAtt.initB().graphicsOverlayClean();
        Point point84 = viewMapUtil.getMapview().screenToLocation(screenPoint);
        viewMapUtil.getMapview().setViewpointCenterAsync(point84);//度跟米的换算 0.00001度=1米
        Polygon polygon = GeometryEngine.buffer(point84, 0.001);
        Graphic gra = new Graphic(polygon,  viewMapUtil.mAAtt.initB().mTouchSymbol);
        viewMapUtil. mAAtt.initB().mTouchgraphicsOverlay.getGraphics().add(gra);
        query = new QueryParameters();
        query.setReturnGeometry(true);//指定是否返回几何对象
        query.setGeometry(polygon);// 设置空间几何对象
        String des = SPUtil.getInstance(this).getStringValue(SPUtil.APP_DES);
        if (!AppTool.isNull(des)) {
            String s = "upper(S_town)" + MapUtil.getDes(des) + "";
            query.setWhereClause(s);
        }
        if (viewMapUtil.isHadSelectIv()) {
            seachImageLayerJc();
        }

        return true;
    }

    public void seachImageLayerJc() {
        Legendbean b = viewMapUtil.legendbean;
        if (b==null){
            ToastUtils.showToast("请选择查询的图层");
            return;
        }
        MapUtil.getInstance(this).FeatureQueryJC(query, this, b.mLayerId, b.key[0]);

    }

    public void setViewScrollHide() {
        mViewDetailJcsw.setVisibility(View.GONE);
        mViewDetailJcclc.setVisibility(View.GONE);
        mViewDetailJcbz.setVisibility(View.GONE);
    }
    public void getDetail(){
        if (!AppTool.isNull(CodeId)) {
            int[] layerid={ AppConfig.JCsw, AppConfig.JCclc, AppConfig.JCbz};
            String[] key={AppConfig.JCsws,AppConfig.JCclcs,AppConfig.JCbzs};
            query = new QueryParameters();
            query.setReturnGeometry(true);//指定是否返回几何对象
            String s = "upper("+key[type]+") in ('"+CodeId+"')";
            query.setWhereClause(s);
            MapUtil.getInstance(this).FeatureQueryJC(query, this, layerid[type], key[type]);
        }


    }
    @Override
    public void requestSuccessJC(int resultid, String result) {
        if (resultid==1||resultid==2||resultid==3){
            RequestDataBean rdatabean=new Gson().fromJson(result,RequestDataBean.class);
            if (rdatabean.Data!=null&&rdatabean.Data.size()>0){
                if (resultid==1){
                    mViewDetailJcsw.setData(rdatabean);
                    presenter.getJCData_his(4,String.format(AppConfig.jCurlswhis,rdatabean.Data.get(0).GWNo,AppTool.getCurrentDate()));
//                presenter.getJCData_his(4,String.format(AppConfig.jCurlswhis,"GW0001","2019-08-07"));
                }else if (resultid==2){
                    mViewDetailJcclc.setData(rdatabean);
                    presenter.getJCData_his(5,String.format(AppConfig.jCurlclchis,rdatabean.Data.get(0).FactId,AppTool.getCurrentDate()));
//                presenter.getJCData_his(5,String.format(AppConfig.jCurlclchis,"04030401150000001","2018-10-25"));

                }else if (resultid==3){
                    mViewDetailJcbz.setData(rdatabean);
                    presenter.getJCData_his(6,String.format(AppConfig.jCurlbzhis,rdatabean.Data.get(0).S_DRAI_PUMP_ID,AppTool.getCurrentDate()));
//                presenter.getJCData_his(6,String.format(AppConfig.jCurlbzhis,"BZ00054","2019-05-27"));

                }
                setScrollState(0);
            }else {
                ToastUtils.showToast("暂无数据");
            }
        }else if (resultid==4){

            JCswBean jchis=new Gson().fromJson(result,JCswBean.class);
            mViewDetailJcsw.getJCData_his(  jchis);
        }else if (resultid==5){
            JCbzAclcBean jchis=new Gson().fromJson(result.replace("Datetime","T_STIME"),JCbzAclcBean.class);
            mViewDetailJcclc.getJCData_his(jchis);
        }else if (resultid==6){
            JCbzAclcBean jchis=new Gson().fromJson(result,JCbzAclcBean.class);
            mViewDetailJcbz.getJCData_his(jchis);
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void requestFailureJC(int resultid, String result) {

    }
    //防止不小心按到返回键
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (mLoadingWaitView.getVisibility() == View.VISIBLE) {
                    mLoadingWaitView.setVisibility(View.GONE);
                } else {
                    finish();

                }

                break;

        }
        return true;
    }


}
