package com.wavenet.ding.qpps.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.dereck.library.base.BaseMvpFragment;
import com.dereck.library.utils.StringUtils;
import com.dereck.library.utils.ToastUtils;
import com.dereck.library.view.LoadingWaitView;
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.data.QueryParameters;
import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.esri.arcgisruntime.layers.ArcGISMapImageSublayer;
import com.esri.arcgisruntime.layers.ArcGISVectorTiledLayer;
import com.esri.arcgisruntime.layers.WebTiledLayer;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.IdentifyGraphicsOverlayResult;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.google.gson.Gson;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.activity.WebView_Activity;
import com.wavenet.ding.qpps.adapter.SearchHisAdapter;
import com.wavenet.ding.qpps.api.QPSWApplication;
import com.wavenet.ding.qpps.bean.AdapterBean;
import com.wavenet.ding.qpps.bean.AdminPeopleBean;
import com.wavenet.ding.qpps.bean.AdminPersonBean2;
import com.wavenet.ding.qpps.bean.JCbzAclcBean;
import com.wavenet.ding.qpps.bean.JCswBean;
import com.wavenet.ding.qpps.bean.ObjectDetailsBean;
import com.wavenet.ding.qpps.bean.ObjectIdsBean;
import com.wavenet.ding.qpps.bean.RequestDataBean;
import com.wavenet.ding.qpps.bean.SearchHistory;
import com.wavenet.ding.qpps.interf.AddFeatureQueryResultListen;
import com.wavenet.ding.qpps.interf.AddLayerListen;
import com.wavenet.ding.qpps.interf.CallBackMap;
import com.wavenet.ding.qpps.interf.InterfListen;
import com.wavenet.ding.qpps.mvp.p.FourthFragmentRequestPresenter;
import com.wavenet.ding.qpps.mvp.v.AdminFragmentRequestView;
import com.wavenet.ding.qpps.utils.AppConfig;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.AppUtils;
import com.wavenet.ding.qpps.utils.CoordinateUtil;
import com.wavenet.ding.qpps.utils.LogUtils;
import com.wavenet.ding.qpps.utils.MapUtil;
import com.wavenet.ding.qpps.utils.MapdownloadUtil;
import com.wavenet.ding.qpps.utils.SPUtil;
import com.wavenet.ding.qpps.utils.TianDiTuMethodsClass;
import com.wavenet.ding.qpps.utils.iflytek.voicedemo.IatUtil;
import com.wavenet.ding.qpps.view.ControllerAdminUIView;
import com.wavenet.ding.qpps.view.ControllerFourthFragmentView;
import com.wavenet.ding.qpps.view.DialogUtil;
import com.wavenet.ding.qpps.view.ViewDetailJcbz;
import com.wavenet.ding.qpps.view.ViewDetailJcclc;
import com.wavenet.ding.qpps.view.ViewDetailJcsw;
import com.wavenet.ding.qpps.view.ViewFourthFragementLegend;
import com.wavenet.ding.qpps.view.ViewFourthFragmentPeopledetail;
import com.wavenet.ding.qpps.view.ViewFourthFragmentSeachdetail;
import com.wavenet.ding.qpps.view.ViewFourthFragmentSeachlist;
import com.wavenet.ding.qpps.view.viewutils.scrollLayout.ContentScrollView;
import com.wavenet.ding.qpps.view.viewutils.scrollLayout.ScrollLayoutView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by zoubeiwen on 2018/8/29.
 */

public class FourthFragment extends BaseMvpFragment<AdminFragmentRequestView, FourthFragmentRequestPresenter> implements AdminFragmentRequestView, CallBackMap, LoadingWaitView.OnRestLoadListener, AddLayerListen, AddFeatureQueryResultListen, View.OnClickListener, ViewFourthFragmentPeopledetail.GoWebCallBack ,ControllerFourthFragmentView.A , InterfListen.B ,InterfListen.C,InterfListen.E{
    public static GraphicsOverlay onClickgraphicsOverlay;//高亮图层
    public static GraphicsOverlay onClickgraphicsOverlayPoint;//高亮图层点
    public static GraphicsOverlay onClickgraphicsOverlayLine;//高亮图层线
    public static GraphicsOverlay onClickTextgraphicsOverlay;//文字图层
    public static GraphicsOverlay onClickMarkergraphicsOverlay;//marker指定图层
    public static GraphicsOverlay mLocationOverlay;//定位图层
    public static GraphicsOverlay onClicgraphicsOverlayXJ; // 巡检人员
    public static GraphicsOverlay onClicgraphicsOverlayYH; // 养护人员
    public GraphicsOverlay mTouchgraphicsOverlay; // 画圆图层
    public MapView mMapView = null;
    public ArcGISMap mArcGISMapVector;
    public String strMapUrl;
    private static Context mContext;
    public AMapLocation aMapLocation;
    public ArcGISMapImageLayer mainMapImageLayer;
    public ArcGISMapImageLayer mainMapImageLayerjc;//jiance
    public ControllerFourthFragmentView mFragmentView;
    public ViewFourthFragmentSeachlist mVfragmentSeachlist;
    public ViewFourthFragmentSeachdetail mViewFourthFragmentSeachdetail;
    public ViewFourthFragmentPeopledetail mViewFourthFragmentPeopledetail;
    public EditText mEtseach;
    public ViewFourthFragementLegend mLegend;
    public int[] urlarry = new int[]{AppConfig.PSGDJ1, AppConfig.PSGD, AppConfig.PSGDWSJ1, AppConfig.PSGDWS, AppConfig.PFK, AppConfig.PSBZ, AppConfig.WSCLC, AppConfig.ZDPFK, AppConfig.PSH};
    public String[] filterkey = new String[]{"S_MANHOLE_NAME_ROAD", "S_DRAI_PIPE_NAME_ROAD", "S_MANHOLE_NAME_ROAD", "S_DRAI_PIPE_NAME_ROAD", "S_MANHOLE_NAME_ROAD", "S_DRAI_PUMP_NAME", "S_FACT_NAME", "S_OutletNAME", "s_name"};
    public String[] filterkey1 = new String[]{AppConfig.DetailsSeachPSJ, AppConfig.DetailsSeachPSGD, AppConfig.DetailsSeachPSJ, AppConfig.DetailsSeachPSGD, AppConfig.DetailsSeachPFK, AppConfig.DetailsSeachPSBZ, AppConfig.DetailsSeachWSCLC, AppConfig.DetailsSeachZDPFK, AppConfig.DetailsSeachPSH};
    public AddLayerListen mAddLayerListen;
    public ObjectIdsBean mObjectIdsBean;
    public LoadingWaitView mLoadingWaitView;
    public boolean isOnclickPeopleXJ = true;
    public boolean isOnclickPeopleYH = true;
    ControllerAdminUIView mAdminUIView;
    ArcGISMapImageSublayer ImageSublayerPSJ;
    TextView mTvseach;
    ImageView mIvback, mIvspeak, mIvclean;
    SimpleFillSymbol mTouchSymbol;
    int showLayerSum = 0;
    SimpleMarkerSymbol citySymbol;
    IatUtil mIatUtil;
    String tvSeachStr = "";
    boolean ismLegendinit = true;
    int mTablesum = 0;
    boolean isFristLoaded = false;//判断是否查询到数据
    ArrayList<AdapterBean> abListJS = new ArrayList<>();
    Envelope envelopeseach;
    QueryParameters mQueryseach;
    QueryParameters query;
    public String role;

    /**
     * 详情
     */
    private ScrollLayoutView mScrollLayoutView;
  ViewDetailJcsw  mViewDetailJcsw;
        ViewDetailJcclc mViewDetailJcclc;
    ViewDetailJcbz mViewDetailJcbz;
    private ContentScrollView mContentScrollView;
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    ArrayList<AdapterBean> abList = (ArrayList<AdapterBean>) msg.obj;

                    if (msg.arg1 == AppConfig.PSGDJ1 || msg.arg1 == AppConfig.PSGDJ2 || msg.arg1 == AppConfig.PSGDJ3 || msg.arg1 == AppConfig.PSGDWSJ1 || msg.arg1 == AppConfig.PSGDWSJ2) {
                        if (msg.arg1 == AppConfig.PSGDJ1) {
                            abListJS.clear();
                            addabListJS(abList);
                            MapUtil.getInstance(getActivity()).FeatureQueryResulPSJ(query, FourthFragment.this, AppConfig.PSGDJ2 + "");
                        } else if (msg.arg1 == AppConfig.PSGDJ2) {
                            addabListJS(abList);
                            MapUtil.getInstance(getActivity()).FeatureQueryResulPSJ(query, FourthFragment.this, AppConfig.PSGDJ3 + "");
                        } else if (msg.arg1 == AppConfig.PSGDJ3) {
                            addabListJS(abList);
//                            mFragmentView.mRbf1.setText("雨水井");
                            mFragmentView.mNotifyDataSet(abListJS, AppConfig.PSJ);
//                            mFragmentView.showData();
//                            mFragmentView.setVisibility(View.VISIBLE);
                        } else if (msg.arg1 == AppConfig.PSGDWSJ1) {
                            abListJS.clear();
                            addabListJS(abList);
                            MapUtil.getInstance(getActivity()).FeatureQueryResulPSJ(query, FourthFragment.this, AppConfig.PSGDWSJ2 + "");
                        } else if (msg.arg1 == AppConfig.PSGDWSJ2) {
                            addabListJS(abList);
//                            mFragmentView.mRbf1.setText("污水井");
                            mFragmentView.mNotifyDataSet(abListJS, AppConfig.PSJ);
                        }

                    } else {
                        if (abList != null && abList.size() > 0) {
                            isFristLoaded = true;
                            mFragmentView.mNotifyDataSet(abList, msg.arg1);
                        }
                    }
                    if (msg.arg1 != AppConfig.PSGDJ1 && msg.arg1 != AppConfig.PSGDJ2 && msg.arg1 != AppConfig.PSGDWSJ1) {

                        mTablesum++;
                    }
                    if (mTablesum == showLayerSum) {

                        if (isFristLoaded) {
                            mFragmentView.showData();

                        } else {
                            mFragmentView.setVisibility(View.GONE);
                        }
                        mLoadingWaitView.setVisibility(View.GONE);
                    }

                    break;
                case 2:
                    mLoadingWaitView.setVisibility(View.GONE);

                    break;
                case 25:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    mIvback.setImageBitmap(bitmap);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    boolean isFristLoca = true;

    public static FourthFragment newInstance(Context context) {
        mContext = context;
        Bundle args = new Bundle();

        FourthFragment fragment = new FourthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_fourth;
    }

    @Override
    public void init() {
        MapUtil.ui = 2;
        isRegistered(true, this);
        role=SPUtil.getInstance(getActivity()).getStringValue(SPUtil.APP_ROLE);
        mLoadingWaitView = mainView.findViewById(R.id.loadingWaitView);
        mLoadingWaitView.setRestLoadListener(this);
//        mLoadingWaitView.setVisibility(View.VISIBLE);
        mAdminUIView = mainView.findViewById(R.id.c_adminui);
        mAdminUIView.setfragment(this);
        mAdminUIView.setCallBackMap(this);
        mViewFourthFragmentSeachdetail = mainView.findViewById(R.id.c_ffsd);
        mViewFourthFragmentPeopledetail = mainView.findViewById(R.id.c_ffpd);
        mFragmentView = mainView.findViewById(R.id.c_ffv);
        mFragmentView.setA(this);
        mLegend = mainView.findViewById(R.id.v_legend);
        mLegend.setFragment(this);
        mLegend.setCallBackMap(this);
        mLegend.setMapdownloadListen(this);
        mVfragmentSeachlist = mainView.findViewById(R.id.v_ffseach);
        mVfragmentSeachlist.setmFourthFragment(this);
        mIvback = mainView.findViewById(R.id.iv_back);
        mIvback.setOnClickListener(this);
        mIvspeak = mainView.findViewById(R.id.iv_speak);
        mIvspeak.setOnClickListener(this);
        mIvspeak.setVisibility(View.VISIBLE);
        mIvclean = mainView.findViewById(R.id.iv_clean);
        mIvclean.setOnClickListener(this);
        mTvseach = mainView.findViewById(R.id.tv_seach);
        mTvseach.setOnClickListener(this);
        mEtseach = mainView.findViewById(R.id.et_seach);
        mEtseach.setOnClickListener(this);
        setmEtseachstate(false);

        mMapView = mainView.findViewById(R.id.mapv);
        mMapView.setAttributionTextVisible(false);
        mTouchSymbol = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, 0x667265fb, null);
        mViewFourthFragmentPeopledetail.setGoWebCallBack(this);
         mScrollLayoutView= AppUtils.setScrollLayoutView(getActivity(),mainView,this);
        mContentScrollView = mainView.findViewById(R.id.content_scroll_layout);
        mViewDetailJcsw=mainView.findViewById(R.id.v_jcsw);
        mViewDetailJcsw.setEListen(this);
        mViewDetailJcclc=mainView.findViewById(R.id.v_jcclc);
        mViewDetailJcclc.setEListen(this);
        mViewDetailJcbz=mainView.findViewById(R.id.v_jcbz);
        mViewDetailJcbz.setEListen(this);
        setViewScrollHide();
    }

    @Override
    public void requestData() {
        mEtseach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    mIvspeak.setVisibility(View.GONE);
                    mTvseach.setVisibility(View.VISIBLE);
                    mIvclean.setVisibility(View.VISIBLE);
                    mVfragmentSeachlist.isQuerySeach = tvSeachStr.equals(editable.toString());

                } else {
                    mIvspeak.setVisibility(View.VISIBLE);
                    mTvseach.setVisibility(View.GONE);
                    mIvclean.setVisibility(View.GONE);
                    mVfragmentSeachlist.isQuerySeach = false;
                    mVfragmentSeachlist.clearDataAdapter();
                }
            }
        });
    }

    public void setSearchEdit(String str) {
        mEtseach.setText(str);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Graphicsclear(mTouchgraphicsOverlay);
        Graphicsclear(onClickgraphicsOverlayLine);
        Graphicsclear(onClickgraphicsOverlayPoint);
        Graphicsclear(onClickgraphicsOverlay);
        Graphicsclear(mLocationOverlay);
        Graphicsclear(onClicgraphicsOverlayXJ);
        Graphicsclear(onClicgraphicsOverlayYH);
        Graphicsclear(mTouchgraphicsOverlay);
        mMapView.dispose();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                mLoadingWaitView.setVisibility(View.GONE);
                setmEtseachstate(false);

                break;
            case R.id.et_seach:
                if (mVfragmentSeachlist.getVisibility() == View.GONE) {
                    setmEtseachstate(true);
                    //获取历史搜索列表
                    String user = SPUtil.getInstance(getContext()).getStringValue(SPUtil.USERNO);
                    presenter.requestSearchHis(user);
                }
                break;
            case R.id.iv_speak:
                if (mVfragmentSeachlist.getVisibility() == View.VISIBLE) {
                    if (mIatUtil == null) {
                        mIatUtil = new IatUtil(getActivity(), mEtseach);
                    }
                    mIatUtil.startSpeek(1);
                } else {
                    setmEtseachstate(true);
                }

                break;
            case R.id.iv_clean:
                mEtseach.setText("");
                //获取历史搜索列表
                String user = SPUtil.getInstance(getContext()).getStringValue(SPUtil.USERNO);
                presenter.requestSearchHis(user);
                break;
            case R.id.tv_seach:
                tvSeachStr = mEtseach.getText().toString();
                int mCheckedId = mVfragmentSeachlist.getCheckedItem();
//                envelopeseach = mainMapImageLayer.getFullExtent();
                submitSearchStr(tvSeachStr);
                if (AppTool.isNull(mEtseach.getText().toString())) {
                    return;
                }
                querySeach(mCheckedId, null);
                break;

        }
    }

    @Override
    public void onClick(String mOnClick) {
        switch (mOnClick) {
            case "DOWNLOADSUCCESS":
                mLoadingWaitView.setVisibility(View.GONE);
                if (!MapUtil.getInstance(getActivity()).isExistspath(MapdownloadUtil.strMapUrl))
                    return;

                mArcGISMapVector = MapUtil.getInstance(getActivity()).getArcGISMapVector(MapdownloadUtil.strMapUrl, mMapView);
                initGOverlays();
                mainMapImageLayer = MapUtil.getInstance(getActivity()).getMapImageLayer();
               initImageLayerJc();
                mAddLayerListen = this;
                MapUtil.getInstance(getActivity()).addMapService(mainMapImageLayer, mArcGISMapVector, this, false);
                mMapView.invalidate();
                ArcGISRuntimeEnvironment.setLicense("runtimelite,1000,rud5799562951,none,PM0RJAY3FLLR2B3TR001");
                MapUtil.getInstance(getActivity()).showGpsDialog(getActivity(), 2);
                MapUtil.getInstance(getActivity()).setshanghai(mMapView);

                MapViewTouchListener mMapViewTouchListener = new MapViewTouchListener(getActivity(), mMapView);
                mMapView.setOnTouchListener(mMapViewTouchListener);

                break;
            case "DOWNLOADFIL":
                mLoadingWaitView.failure();
                break;
            case "REMOVEMAPLOC":
//                removeLocaioc();
                break;
            case "TDTVEC":
                ismLegendinit = false;
                ArcGISVectorTiledLayer mainArcGISVectorTiledLayer = new ArcGISVectorTiledLayer(MapdownloadUtil.strMapUrl);
                Basemap mainBasemap = new Basemap(mainArcGISVectorTiledLayer);
                mArcGISMapVector = new ArcGISMap(mainBasemap);
                ArcGISMapImageLayer mainMapImageLayer1 = new ArcGISMapImageLayer(AppConfig.GuanWangUrl);
                mainMapImageLayer = MapUtil.getInstance(getActivity()).addMapService(mainMapImageLayer1, mArcGISMapVector, this, false);
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

                ArcGISMapImageLayer mainMapImageLayer2 = new ArcGISMapImageLayer(AppConfig.GuanWangUrl);

                mainMapImageLayer = MapUtil.getInstance(getActivity()).addMapService(mainMapImageLayer2, mArcGISMapVector, this, false);
                mMapView.setMap(mArcGISMapVector);
                initImageLayerJc();
                mMapView.setViewpointGeometryAsync(QPSWApplication.CenterpointPolygon);
                break;
        }
    }

    @Override
    public void onRestLoad(LoadingWaitView loadWaitView) {
        new MapdownloadUtil(getActivity()).setCallBackMap(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventBusCar(AMapLocation aMapLocation) {
        if (mAdminUIView.mIvgensui.isSelected()) {
            MapUtil.getInstance(getActivity()).setGensui(this.aMapLocation, mMapView);
        }
        if (MapUtil.isLegalLL(aMapLocation,false)) {
            this.aMapLocation = aMapLocation;
            mLocationOverlay = MapUtil.getInstance(getActivity()).setLocicon(aMapLocation, mMapView, mLocationOverlay, "");
            if (mainMapImageLayer != null) {
                if (isFristLoca && QPSWApplication.Locpoint!=null) {
                    isFristLoca = !isFristLoca;
                    mMapView.setViewpointCenterAsync(QPSWApplication.Locpoint, 10000);
                }

            }

        }

    }
    boolean isFistsetMap=true;
    @Override
    public void getImageLayer(ArcGISMapImageLayer mImageLayer) {
        if (mImageLayer.getLoadStatus() == LoadStatus.LOADED) {
            mainMapImageLayer = mImageLayer;
//            if (ismLegendinit) {
            if (isFistsetMap){
                isFistsetMap=false;
                mLegend.mLlguanwangys.setSelected(true);
                mLegend.mIvguanwangys.setSelected(false);
//                mLegend.setmOnClickLl(mLegend.mLlguanwangys);
            }else {
                mLegend.initShowLayerchange();
            }

        }


    }

    @Override
    public void getImageLayerJc(ArcGISMapImageLayer mImageLayer) {
        if (mImageLayer.getLoadStatus() == LoadStatus.LOADED) {
            mLegend.initShowLayerchangejc();

        }
    }

    @Override
    public void getQueryResultMap(final ArrayList<AdapterBean> abList, int mTable) {

        setMessgae(abList, mTable, 1);
    }

    @Override
    public void bA(Graphic g, final ArrayList<AdapterBean> abList, int url, final String urlname, int Symbol) {

        if (url==10000){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLoadingWaitView.success();
                    ToastUtils.showToast(urlname);
                }
            });
            return;
        }
        if (Symbol==0){
            onClickgraphicsOverlayPoint.getGraphics().add(g);
        }else {
            onClickgraphicsOverlayLine.getGraphics().add(g);
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLoadingWaitView.success();
                setViewScrollHide();
                if (mLegend.mLljcsw.isSelected()){
                    presenter.getJCData_id(1,AppConfig.jCurlsw+abList.get(0).mag1);
                }else if(mLegend.mLljcclc.isSelected()){
                    presenter.getJCData_id(2,AppConfig.jCurlclc+abList.get(0).mag1);
                }else if (mLegend.mLljcbz.isSelected()){
                    presenter.getJCData_id(3,AppConfig.jCurlbz+abList.get(0).mag1);
                }

            }
        });

    }
    @Override
    public void cA(String s, ScrollLayoutView.Status currentStatus,boolean isHide) {

    }
    @Override
    public void eA(String time, String id) {
        if (mViewDetailJcsw.getVisibility()==View.VISIBLE){
                presenter.getJCData_his(4,String.format(AppConfig.jCurlswhis,id,time));
        }else if (mViewDetailJcclc.getVisibility()==View.VISIBLE){
            presenter.getJCData_his(5,String.format(AppConfig.jCurlclchis,id,time));

        }else if (mViewDetailJcbz.getVisibility()==View.VISIBLE){
                presenter.getJCData_his(6,String.format(AppConfig.jCurlbzhis,id,time));
    }}
    @Override
    public void getQueryResultMapSeach(ArrayList<AdapterBean> abList, int mTable) {
        setMessgae(abList, mTable, 2);
    }

    public void initStyleSingleTap() {
        isFristLoaded = false;
        mTablesum = 0;
        mFragmentView.initStyle();
        mTouchgraphicsOverlay.getGraphics().clear();
        onClickgraphicsOverlayPoint.getGraphics().clear();
        onClickgraphicsOverlayLine.getGraphics().clear();
        onClickgraphicsOverlay.getGraphics().clear();
        onClickTextgraphicsOverlay.getGraphics().clear();
        onClickMarkergraphicsOverlay.getGraphics().clear();
        mViewFourthFragmentPeopledetail.setVisibility(View.GONE);
        mScrollLayoutView.setToExit();
    }

    public void setViewScrollHide(){
        mViewDetailJcsw.setVisibility(View.GONE);
        mViewDetailJcclc.setVisibility(View.GONE);
        mViewDetailJcbz.setVisibility(View.GONE);
    }
    public void setMessgae(ArrayList<AdapterBean> abList, int mTable, int what) {
        Message message = new Message();
//        Bundle bundle=new Bundle();
//        bundle.putSerializable("2",abList);
//        message.setData(bundle);//bundle传值，耗时，效率低
        message.arg1 = mTable;
        message.obj = abList;
        myHandler.sendMessage(message);//发送message信息
        message.what = what;//
    }

    public void submitSearchStr(String str) {
        if (AppTool.isNull(str)) {
            return;
        }
        String user = SPUtil.getInstance(getContext()).getStringValue(SPUtil.USERNO);
        presenter.addSearchHis(user, str);
    }

    public void querySeach(int mCheckedId, String str) {

        mObjectIdsBean = null;
        mVfragmentSeachlist.clearDataAdapter();
        String des = SPUtil.getInstance(getActivity()).getStringValue(SPUtil.APP_DES);
        presenter.AdminGetObjectIds(String.valueOf(urlarry[mCheckedId]), filterkey[mCheckedId], StringUtils.isEmpty(str) ? tvSeachStr : str, des);
    }

    public void clearHis(final List<SearchHistory.DataBean> hisList, int pos, final SearchHisAdapter mAdapter) {
        DialogUtil dialogUtil = DialogUtil.getSingleton();
        dialogUtil.showTipDialog(getContext(), "立即清空", "清空历史记录？");
        dialogUtil.setOnButtonClickListener(new DialogUtil.OnButtonClickListener() {
            @Override
            public void onOkClick() {
                String user = SPUtil.getInstance(getContext()).getStringValue(SPUtil.USERNO);
                presenter.clearHisList(hisList, user, mAdapter);
            }
        });
    }

    public void setmEtseachstate(boolean isEdit) {
        mLegend.setVisibility(View.GONE);
        mEtseach.setCursorVisible(isEdit);
        mEtseach.setFocusable(isEdit);
        mEtseach.setFocusableInTouchMode(isEdit);
        mVfragmentSeachlist.setVisibility(isEdit ? View.VISIBLE : View.GONE);
        mIvspeak.setVisibility(isEdit ? View.GONE : View.VISIBLE);
        mIvback.setVisibility(isEdit ? View.VISIBLE : View.INVISIBLE);
        mVfragmentSeachlist.showOrHide(isEdit);
        if (isEdit) {
            mEtseach.requestFocus();
            if (mEtseach.getText().toString().length() == 0) {
                mIvspeak.setVisibility(View.VISIBLE);
                mTvseach.setVisibility(View.GONE);
                mIvclean.setVisibility(View.GONE);

            } else {
                mIvspeak.setVisibility(View.GONE);
                mTvseach.setVisibility(View.VISIBLE);
                mIvclean.setVisibility(View.VISIBLE);
            }
        } else {
            mTvseach.setVisibility(View.GONE);
            mIvclean.setVisibility(View.GONE);
        }


    }

    public void addabListJS(ArrayList<AdapterBean> abList) {
        if (abList != null && abList.size() > 0) {
            isFristLoaded = true;
            abListJS.addAll(abList);
        }
    }

    public void setGOverlays(GraphicsOverlay gOverlays) {
        if (!mMapView.getGraphicsOverlays().contains(gOverlays)) {
            mMapView.getGraphicsOverlays().add(gOverlays);
        }
    }
    public  void initImageLayerJc(){
        mainMapImageLayerjc = new ArcGISMapImageLayer(AppConfig.JCUrl);
        mainMapImageLayerjc.loadAsync();
        mainMapImageLayerjc.addDoneLoadingListener(new Runnable() {
            @Override
            public void run() {
                if (mainMapImageLayerjc.getLoadStatus() == LoadStatus.LOADED) {
                    mLegend.isShowlayer(mainMapImageLayerjc,false);
                    mLegend.initShowLayerchangejc();

                }
            }
        });
        if (! mMapView.getMap().getOperationalLayers().contains(mainMapImageLayerjc)){
            mMapView.getMap().getOperationalLayers().add(mainMapImageLayerjc);
        }

    }
public void initGOverlays(){
    mLocationOverlay = new GraphicsOverlay();
    onClicgraphicsOverlayXJ = new GraphicsOverlay();
    onClicgraphicsOverlayYH = new GraphicsOverlay();
    onClickgraphicsOverlay = new GraphicsOverlay();
    mTouchgraphicsOverlay = new GraphicsOverlay();
    onClickgraphicsOverlayPoint = new GraphicsOverlay();
    onClickgraphicsOverlayLine = new GraphicsOverlay();
    onClickTextgraphicsOverlay = new GraphicsOverlay();
    onClickMarkergraphicsOverlay = new GraphicsOverlay();
    setGOverlays(mLocationOverlay);
    setGOverlays(onClickgraphicsOverlay);
    setGOverlays(mTouchgraphicsOverlay);
    setGOverlays(onClickgraphicsOverlayLine);
    setGOverlays(onClickgraphicsOverlayPoint);
    setGOverlays(onClickMarkergraphicsOverlay);
    setGOverlays(onClickTextgraphicsOverlay);
    setGOverlays(onClicgraphicsOverlayXJ);
    setGOverlays(onClicgraphicsOverlayYH);
}
    public void Graphicsclear(GraphicsOverlay gOverlay) {
        if (gOverlay != null && gOverlay.getGraphics() != null) {
            gOverlay.getGraphics().clear();
        }
    }
public  void  seachImageLayer( Envelope envelope){
//    if (mainMapImageLayer.getLoadStatus()!=LoadStatus.LOADED){
//        ToastUtils.showToast("图层加载失败");
//        return;
//    }
    if (mLegend.mLlguanwangys.isSelected()) {//TODO 还差添加污水
        abListJS.clear();
      String role=  SPUtil.getInstance(getActivity()).getStringValue(SPUtil.APP_ROLE);
        if ("区调度员".equals(role)||"区管理员".equals(role)||"街镇管理员".equals(role)){
            MapUtil.getInstance(getActivity()).FeatureQueryResulPSGDYS(query, FourthFragment.this);
            MapUtil.getInstance(getActivity()).FeatureQueryResulPSJ(query, FourthFragment.this, AppConfig.PSGDJ1 + "");
        }else {
            ToastUtils.showToast("无权限查看雨水官网信息");
        }

    }
    if (mLegend.mLlguanwangws.isSelected()) {//TODO 还差添加污水
        abListJS.clear();
        if ("区调度员".equals(role)||"区管理员".equals(role)||"街镇管理员".equals(role)){
            MapUtil.getInstance(getActivity()).FeatureQueryResulPSGDWS(query, FourthFragment.this);
            MapUtil.getInstance(getActivity()).FeatureQueryResulPSJ(query, FourthFragment.this, AppConfig.PSGDWSJ1 + "");
        }else {
            ToastUtils.showToast("无权限查看污水官网信息");
        }
    }
    if (mLegend.mLlpfk.isSelected()) {
        MapUtil.getInstance(getActivity()).FeatureQueryResulPFK(query, FourthFragment.this);
    }
    if (mLegend.mLlbengzhan.isSelected()) {
        MapUtil.getInstance(getActivity()).FeatureQueryResulPSBZ(query, FourthFragment.this);
    }
    if (mLegend.mLlwushuichang.isSelected()) {

        MapUtil.getInstance(getActivity()).FeatureQueryResulWSCLC(query, FourthFragment.this);
    }
    if (mLegend.mLlzhongdianpfk.isSelected()) {
        MapUtil.getInstance(getActivity()).FeatureQueryResulZDPFK(query, FourthFragment.this);
    }
    if (mLegend.mLlpaishuihu.isSelected()) {
        query.setGeometry(envelope);// 设置空间几何对象
        MapUtil.getInstance(getActivity()).FeatureQueryResulPSH(query, FourthFragment.this);
    }
}
public  void  seachImageLayerJc(){
    if (mLegend.mLljcsw.isSelected()) {
        MapUtil.getInstance(getActivity()).FeatureQueryJC(query, FourthFragment.this,AppConfig.JCsw,AppConfig.JCsws);
    }   if (mLegend.mLljcclc.isSelected()) {

        LogUtils.e("mLoadingWaitView2","View.VISIBLE");
        MapUtil.getInstance(getActivity()).FeatureQueryJC(query, FourthFragment.this,AppConfig.JCclc,AppConfig.JCclcs);
    }   if (mLegend.mLljcbz.isSelected()) {
        MapUtil.getInstance(getActivity()).FeatureQueryJC(query, FourthFragment.this,AppConfig.JCbz,AppConfig.JCbzs);
    }
}

    public boolean onClickSingle(android.graphics.Point screenPoint) {

        if (mMapView.getMapScale() > 100000 )
            return true;
        initStyleSingleTap();
        mViewFourthFragmentSeachdetail.setVisibility(View.GONE);
        Point point84 = mMapView.screenToLocation(screenPoint);
        mMapView.setViewpointCenterAsync(point84);//度跟米的换算 0.00001度=1米
        double tolerance = 0.00014;
        double mapTolerance = tolerance;
//        //查询范围
        Envelope envelope = new Envelope(point84.getX() + mapTolerance, point84.getY() - mapTolerance,
                point84.getX() - mapTolerance, point84.getY() + mapTolerance, mArcGISMapVector.getSpatialReference());
        double[] doubles = CoordinateUtil.lonLat2Mercator(point84.getX(), point84.getY());
        double aDouble0 = doubles[0];
        double aDouble1 = doubles[1];
        Point pointMKT = new Point(aDouble0, aDouble1, SpatialReference.create(3857));
//            Polygon polygon = GeometryEngine.buffer(pointMKT, 50);
        Polygon polygon;
        Graphic gra;
        mTouchgraphicsOverlay.getGraphics().clear();
//        mTouchgraphicsOverlay.getGraphics().add(gra1);
        query = new QueryParameters();
        query.setReturnGeometry(true);//指定是否返回几何对象
        String des = SPUtil.getInstance(getActivity()).getStringValue(SPUtil.APP_DES);

        if (!AppTool.isNull(des)) {
//            query.setWhereClause("upper(S_town)='" + townname + "'");

 String s="upper(S_town)" +  MapUtil.getDes(des) + "";
// String s="upper(S_town)" +  MapUtil.getDes("'盈浦街道','徐泾镇'") + "";
            query.setWhereClause(s);
        }
        showLayerSum = mLegend.showLayerSum();
        if (showLayerSum != 0) {
            mLoadingWaitView.setVisibility(View.VISIBLE);
            LogUtils.e("mLoadingWaitView1","View.VISIBLE");
        } else {
            initStyleSingleTap();
            return true;
        }
        if(mLegend.ImageLayer==0){
             polygon = GeometryEngine.buffer(point84, 0.00014);
              gra = new Graphic(polygon, mTouchSymbol);
            mTouchgraphicsOverlay.getGraphics().add(gra);
            query.setGeometry(polygon);// 设置空间几何对象
            mScrollLayoutView.setToExit();
            seachImageLayer(  envelope);
        }else{
             polygon = GeometryEngine.buffer(point84, 0.001);
             query.setGeometry(polygon);// 设置空间几何对象
             gra = new Graphic(polygon, mTouchSymbol);
            mTouchgraphicsOverlay.getGraphics().add(gra);
            seachImageLayerJc();
        }


        return true;
    }


    @Override
    protected FourthFragmentRequestPresenter createPresenter() {
        return new FourthFragmentRequestPresenter();
    }

    @Override
    public void requestSuccess(int resultid, String result) {
        if (!AppTool.isNull(result) && !result.contains("error")) {
            if (resultid == 301 || resultid == 3011 || resultid == 302 || resultid == 3021) {
                if (resultid == 301 || resultid == 3011) {
                    isOnclickPeopleXJ = true;
                    onClicgraphicsOverlayXJ.getGraphics().clear();
                } else {
                    isOnclickPeopleYH = true;
                    onClicgraphicsOverlayYH.getGraphics().clear();
                }
                try {
                    JSONObject jsonObject = new JSONObject(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AdminPeopleBean b = new Gson().fromJson(result, AdminPeopleBean.class);
                /*String companyname=SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_COMPANY);
                for(int i=0; i<b.tPersonCollection.tPerson.size(); i++){
                    if(companyname.equals(b.tPersonCollection.tPerson.get(i).SCOMNAME)){
                        Log.d("qingpu", "SCOMNAME = "+b.tPersonCollection.tPerson.get(i).SCOMNAME);
                    }else {
                        b.tPersonCollection.tPerson.remove(i);
                    }
                }*/
//                MapUtil.getInstance(getActivity()).addGraphicsOverlayPeople(b);
            }else if(resultid == 1008||resultid == 1009||resultid == 10081||resultid == 10091){
                String noResult = "";
                if (resultid == 1008||resultid == 10081) {
                    isOnclickPeopleXJ = true;
                    onClicgraphicsOverlayXJ.getGraphics().clear();
                    noResult = "没有查询到巡查人员数据";
                } else {
                    isOnclickPeopleYH = true;
                    onClicgraphicsOverlayYH.getGraphics().clear();
                    noResult = "没有查询到养护人员数据";
                }
                AdminPersonBean2 b = new Gson().fromJson(result, AdminPersonBean2.class);
                LogUtils.d("qingpu", "b = "+new Gson().toJson(b));
                MapUtil.getInstance(getActivity()).addGraphicsOverlayPeople2(b);
                if (b.getData().size()==0){
                    LogUtils.d("qingpu", "暂无数据");
                    ToastUtils.showToast(noResult);
                }
            } else if (resultid == 1) {
                mObjectIdsBean = new Gson().fromJson(result, ObjectIdsBean.class);
                if (mObjectIdsBean == null || mObjectIdsBean.objectIds == null || mObjectIdsBean.objectIds.size() == 0) {
                    ToastUtils.showToast("没有查询到符合条件的数据");
                    return;
                }
                mVfragmentSeachlist.isQuerySeach = true;
                mVfragmentSeachlist.mSeachPageindex = 1;
                presenter.AdminGetObjectDetails(urlarry[mVfragmentSeachlist.mCheckId], AppTool.getPagingID(mObjectIdsBean, mVfragmentSeachlist.mSeachPageindex), filterkey1[mVfragmentSeachlist.mCheckId]);
            } else if (resultid == AppConfig.PSGDJ1 || resultid == AppConfig.PSGD || resultid == AppConfig.PSGDWSJ1 || resultid == AppConfig.PSGDWS || resultid == AppConfig.PFK || resultid == AppConfig.PSBZ || resultid == AppConfig.WSCLC || resultid == AppConfig.PSH || resultid == AppConfig.ZDPFK) {
                mVfragmentSeachlist.listviewseach.setVisibility(View.VISIBLE);
                mVfragmentSeachlist.mSearchHisView.setVisibility(View.GONE);
                ObjectDetailsBean mBean = new Gson().fromJson(result, ObjectDetailsBean.class);
                List<AdapterBean> b = null;
//                AppConfig.PSGDJ1, AppConfig.PSGD,AppConfig.PSGDWSJ1, AppConfig.PSGDWS,
                if (resultid == AppConfig.PSGDJ1 || resultid == AppConfig.PSGDWSJ1) {
                    b = AdapterBean.getSeachPSJList(mBean, resultid);
                } else if (resultid == AppConfig.PSGD || resultid == AppConfig.PSGDWS) {
                    b = AdapterBean.getSeachPSGDList(mBean, resultid);
                } else if (resultid == AppConfig.PFK) {
                    b = AdapterBean.getSeachPFKList(mBean);
                } else if (resultid == AppConfig.PSBZ) {
                    b = AdapterBean.getSeachPSBZList(mBean);
                } else if (resultid == AppConfig.WSCLC) {
                    b = AdapterBean.getSeachWSCLCList(mBean);
                } else if (resultid == AppConfig.PSH) {
                    b = AdapterBean.getSeachPSHList(mBean);
                } else if (resultid == AppConfig.ZDPFK) {
                    b = AdapterBean.getSeachZDPFKList(mBean);
                }
                if (mVfragmentSeachlist.mSeachPageindex == 1) {
                    mVfragmentSeachlist.mSeachAdapterBean.clear();
                    if (mVfragmentSeachlist.listviewseach.isRefreshing()) {
                        mVfragmentSeachlist.listviewseach.refreshComplete();
                    }
                }
                mVfragmentSeachlist.mSeachAdapterBean.addAll(b);
                if (mVfragmentSeachlist.mSeachAdapterBean.size() < mObjectIdsBean.objectIds.size()) {
                    mVfragmentSeachlist.mSeachPageindex++;
                    if (mVfragmentSeachlist.listviewseach.isLoading()) {
                        mVfragmentSeachlist.listviewseach.loadMoreComplete();
                        //如果没有更多数据就显示没有更多数据提示
                    }
                } else {
                    mVfragmentSeachlist.listviewseach.setNoMoreDate(true);
                }


                mVfragmentSeachlist.mTradapter.notifyDataSetChanged();
            } else if (resultid == 309) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (resultid == 6) {
                SearchHistory history = new Gson().fromJson(result, SearchHistory.class);
                if (history == null) {
                    return;
                }
                if (history.getData().size() == 0) {
                    mVfragmentSeachlist.mSearchHisView.setVisibility(View.GONE);
                    mVfragmentSeachlist.listviewseach.setVisibility(View.VISIBLE);
                } else {
                    mVfragmentSeachlist.mSearchHisView.setVisibility(View.VISIBLE);
                    mVfragmentSeachlist.listviewseach.setVisibility(View.GONE);
                }
                mVfragmentSeachlist.historyList.clear();
                mVfragmentSeachlist.historyList.addAll(history.getData());
                mVfragmentSeachlist.mHisAdapter.notifyDataSetChanged();
            } else if (resultid == 8) {
                mVfragmentSeachlist.mSearchHisView.setVisibility(View.GONE);
                mVfragmentSeachlist.listviewseach.setVisibility(View.VISIBLE);
            }
        } else {
            mLoadingWaitView.setVisibility(View.GONE);
            ToastUtils.showToast(result);
        }
    }

    @Override
    public void requestSuccessJC(int resultid, String result) {
    if (resultid==1||resultid==2||resultid==3){
        RequestDataBean  rdatabean=new Gson().fromJson(result,RequestDataBean.class);
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
public void setScrollState(int time){
    mScrollLayoutView.setToOpen();
//    mContentScrollView.postDelayed(new Runnable() {
//        @Override
//        public void run() {
//            mContentScrollView.fullScroll(ScrollView.FOCUS_UP);
//        }
//    },time);
}
    @Override
    public void show() {
        showDialog();
    }

    @Override
    public void hide() {
        cancelDialog();
    }

    @Override
    public void requestFailure(int resultid, String result) {
        if (resultid == 1008) {
            isOnclickPeopleXJ = true;
            mLegend.mIvpeoplexj.setSelected(false);
            onClicgraphicsOverlayXJ.setVisible(false);
            ToastUtils.showToast("巡查人员图层加载失败");
        } else if (resultid == 1009) {
            isOnclickPeopleYH = true;
            mLegend.mIvpeopleyh.setSelected(false);
            onClicgraphicsOverlayYH.setVisible(false);
            ToastUtils.showToast("养护人员图层加载失败");
        } else if (resultid == 10081) {
            isOnclickPeopleXJ = true;
            ToastUtils.showToast("巡查人员图层刷新失败");
        } else if (resultid == 10091) {
            isOnclickPeopleYH = true;
            ToastUtils.showToast("养护人员图层刷新失败");
        }
    }

    @Override
    public void requestFailureJC(int resultid, String result) {

    }

    @Override
    public void goWebActivity(String url, String flag,String name) {
        if (flag.equals("yanghu")) {
//            String username = SPUtil.getInstance(mContext).getStringValue(SPUtil.USERNO);
            Intent intent = new Intent(mContext, WebView_Activity.class);
            intent.putExtra("webview", url + name);
            startActivity(intent);
        } else {
//            String company = SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_COMPANY);
            Intent intent = new Intent(mContext, WebView_Activity.class);
            intent.putExtra("webview", url );
            startActivity(intent);
        }
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
        public boolean onSingleTapConfirmed(final MotionEvent e) {
            final android.graphics.Point screenPoint = new android.graphics.Point((int) e.getX(), (int) e.getY());

            if (!mLegend.mIvpeopleyh.isSelected() && !mLegend.mIvpeoplexj.isSelected()) {
                onClickSingle(screenPoint);
                return true;
            }
            final ListenableFuture<List<IdentifyGraphicsOverlayResult>> identifyGraphic11 = mMapView.identifyGraphicsOverlaysAsync(screenPoint, 15.0, false, 1);
            identifyGraphic11.addDoneListener(new Runnable() {
                @Override
                public void run() {
                    try {
                        List<IdentifyGraphicsOverlayResult> re = identifyGraphic11.get();
                        int s = re.size();
                        if (s > 0) {
                            IdentifyGraphicsOverlayResult r = re.get(0);
                            List<Graphic> graphic = r.getGraphics();
                            int identifyResultSize = graphic.size();
                            if (identifyResultSize > 0) {
                                String str = (String) graphic.get(0).getAttributes().get("markerpeople");
//                                Log.d("qingpu", "all detail = "+graphic.get(0).getAttributes());
                                if (AppTool.isNull(str)) {
                                    onClickSingle(screenPoint);
                                    return;
                                }
                                if (!graphic.isEmpty() && !AppTool.isNull(str)) {
                                    initStyleSingleTap();
                                    /*AdminPeopleBean.TPersonCollectionBean.TPersonBean b = new Gson().fromJson(str, AdminPeopleBean.TPersonCollectionBean.TPersonBean.class);
                                    final Point p = new Point(Double.parseDouble(b.NX.trim()), Double.parseDouble(b.NY.trim()), SpatialReferences.getWgs84());
                                    mMapView.setViewpointCenterAsync(p);
                                    mViewFourthFragmentPeopledetail.setdata(b);*/
                                    AdminPersonBean2.DataBean b = new Gson().fromJson(str, AdminPersonBean2.DataBean.class);
                                    final Point p = new Point(b.getN_X(), b.getN_Y(), SpatialReferences.getWgs84());
                                    mMapView.setViewpointCenterAsync(p);
                                    mViewFourthFragmentPeopledetail.setdata2(b);
                                }
                            } else {
                                    onClickSingle(screenPoint);
                            }
                        } else {
                            onClickSingle(screenPoint);
                        }
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    } catch (ExecutionException e1) {
                        e1.printStackTrace();
                    }
                }
            });

//            final ListenableFuture<IdentifyGraphicsOverlayResult> identifyGraphic = mMapView.identifyGraphicsOverlayAsync(onClicgraphicsOverlayXJ, screenPoint, 50.0, false, 1);
//
//            identifyGraphic.addDoneListener(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        IdentifyGraphicsOverlayResult grOverlayResult = identifyGraphic.get();
//                        List<Graphic> graphic = grOverlayResult.getGraphics();
//                        int identifyResultSize = graphic.size();
//                        if (identifyResultSize > 0) {
//                            String str = (String) graphic.get(0).getAttributes().get("markerdata");
//                            if (!graphic.isEmpty()) {
////                                TaskBean tb = new Gson().fromJson(str, TaskBean.class);
//ToastUtils.showToast(str);
//                            }
//                        }else {
//                            final ListenableFuture<IdentifyGraphicsOverlayResult> identifyGraphic1 = mMapView.identifyGraphicsOverlayAsync(onClicgraphicsOverlayYH, screenPoint, 50.0, false, 1);
//
//                            identifyGraphic1.addDoneListener(new Runnable() {
//                                @Override
//                                public void run() {
//                                    try {
//                                        IdentifyGraphicsOverlayResult grOverlayResult1 = identifyGraphic1.get();
//                                        List<Graphic> graphic1 = grOverlayResult1.getGraphics();
//                                        int identifyResultSize = graphic1.size();
//                                        if (identifyResultSize > 0) {
//                                            String str = (String) graphic1.get(0).getAttributes().get("markerdata");
//                                            if (!graphic1.isEmpty()) {
////                                TaskBean tb = new Gson().fromJson(str, TaskBean.class);
//                                                ToastUtils.showToast("1111111-----"+str);
//                                            }
//                                        }else {
//                                            onClickSingle(screenPoint);
//                                        }
//
//                                    } catch (Exception ie) {
//                                        ie.printStackTrace();
//                                    }
//
//                                }
//                            });
//                        }
//
//                    } catch (Exception ie) {
//                        ie.printStackTrace();
//                    }
//
//                }
//            });

//            identifyResult(screenPoint);
            return super.onSingleTapConfirmed(e);
        }
    }
    @Override
    public void getbackcall() {
        initStyleSingleTap();
    }

}
