package com.wavenet.ding.qpps.activity;


import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.trace.TraceLocation;
import com.bll.greendao.FailXJResultDao;
import com.bll.greendao.dbean.FailXJResult;
import com.bll.greendao.dbean.FilePath;
import com.bll.greendao.manager.DBManager;
import com.bumptech.glide.Glide;
import com.dereck.library.base.BaseMvpActivity;
import com.dereck.library.utils.RxHttpUtils;
import com.dereck.library.utils.StringUtils;
import com.dereck.library.utils.ToastUtils;
import com.dereck.library.view.LoadingWaitView;
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.data.QueryParameters;
import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PointCollection;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.esri.arcgisruntime.layers.ArcGISVectorTiledLayer;
import com.esri.arcgisruntime.layers.WebTiledLayer;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.LayerList;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.IdentifyGraphicsOverlayResult;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.google.gson.Gson;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.adapter.SearchHisAdapter;
import com.wavenet.ding.qpps.api.QPSWApplication;
import com.wavenet.ding.qpps.bean.AdapterBean;
import com.wavenet.ding.qpps.bean.BreakOffBean;
import com.wavenet.ding.qpps.bean.ClasBean;
import com.wavenet.ding.qpps.bean.DetailsBean;
import com.wavenet.ding.qpps.bean.Gps;
import com.wavenet.ding.qpps.bean.IsDealBean;
import com.wavenet.ding.qpps.bean.ListBean;
import com.wavenet.ding.qpps.bean.ObjectDetailsBean;
import com.wavenet.ding.qpps.bean.ObjectIdsBean;
import com.wavenet.ding.qpps.bean.PhtotIdBean;
import com.wavenet.ding.qpps.bean.RelevantTaskBean;
import com.wavenet.ding.qpps.bean.SearchHistory;
import com.wavenet.ding.qpps.bean.TaskBean;
import com.wavenet.ding.qpps.bean.TasklistBean;
import com.wavenet.ding.qpps.bean.XYbean;
import com.wavenet.ding.qpps.db.TrackBiz;
import com.wavenet.ding.qpps.db.bean.TrackBean;
import com.wavenet.ding.qpps.interf.AddFeatureQueryResultListen;
import com.wavenet.ding.qpps.interf.AddLayerListen;
import com.wavenet.ding.qpps.interf.CallBackMap;
import com.wavenet.ding.qpps.interf.GoBackInteDef;
import com.wavenet.ding.qpps.mvp.p.MainMapXJRequestPresenter;
import com.wavenet.ding.qpps.mvp.v.XJActivityRequestView;
import com.wavenet.ding.qpps.serverutils.LocService;
import com.wavenet.ding.qpps.utils.AppAttribute;
import com.wavenet.ding.qpps.utils.AppConfig;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.CoordinateUtil;
import com.wavenet.ding.qpps.utils.MapUtil;
import com.wavenet.ding.qpps.utils.MapdownloadUtil;
import com.wavenet.ding.qpps.utils.PositionUtil;
import com.wavenet.ding.qpps.utils.SPUtil;
import com.wavenet.ding.qpps.utils.TianDiTuMethodsClass;
import com.wavenet.ding.qpps.utils.iflytek.voicedemo.IatUtil;
import com.wavenet.ding.qpps.view.CenterPopWindow;
import com.wavenet.ding.qpps.view.ControllerAddPhotoView;
import com.wavenet.ding.qpps.view.ControllerDetailsView;
import com.wavenet.ding.qpps.view.ControllerMainUIView;
import com.wavenet.ding.qpps.view.ControllerTaskDealView;
import com.wavenet.ding.qpps.view.ControllerTaskReasonView;
import com.wavenet.ding.qpps.view.ControllerTaskReportView;
import com.wavenet.ding.qpps.view.ControllerTasklistView;
import com.wavenet.ding.qpps.view.ControllerTasklnewistView;
import com.wavenet.ding.qpps.view.ViewFourthFragmentSeachdetail;
import com.wavenet.ding.qpps.view.ViewLegend;
import com.wavenet.ding.qpps.view.ViewXJSeachlist;
import com.wavenet.ding.qpps.view.ViewonClickMapDetail;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.model.TImage;
import org.devio.takephoto.model.TResult;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.CustomMessage;


public class MainMapXJActivity extends BaseMvpActivity<XJActivityRequestView, MainMapXJRequestPresenter>
        implements CallBackMap, View.OnClickListener
        , LoadingWaitView.OnRestLoadListener, TakePhoto.TakeResultListener, XJActivityRequestView, AddLayerListen, AddFeatureQueryResultListen {
    public static String S_RECODE_ID = "";//记录编号
    public static String S_MANGE_ID = "";//事件编号
    public static String STASKID = "";//派单任务id
    public static String S_SJSB_ID = "";//派单任务的事件上报id
    public static String STASKIDcurrent = "";//当前派单任务id
    public static String STASKIDTem = "";//临时派单任务id
    public static TasklistBean.ValueBean mtvBean;//派单任务bean
    public static double Distance = 0;//巡检距离
    public static int Task = 0;//0 日常，1派单
    public static boolean isStartde = false;//0 日常，1派单
    public static Gps g;
    public static AMapLocation aMapLocation;
    public static GraphicsOverlay onClickgraphicsOverlay;//高亮图层
    public static GraphicsOverlay onClickgraphicsOverlayPoint;//高亮图层点
    public static GraphicsOverlay onClickgraphicsOverlayLine;//高亮图层线
    public static GraphicsOverlay onClickTextgraphicsOverlay;//文字图层
    public static GraphicsOverlay onClickMarkergraphicsOverlay;//marker指定图层
    public  ControllerMainUIView mMainUIView;
    public ViewXJSeachlist mVXJSeachlist;
    public ObjectIdsBean mObjectIdsBean;
    public MapView mMapView;
    public ArcGISMap mArcGISMapVector;
    public LinearLayout mLltop;
    public TakePhoto takePhoto;
    public ImageView mIvshou;
    public ControllerTaskReportView mTaskReportView;
    public ControllerTaskDealView mTaskDealView;
    public ViewLegend mLegend;
    public GraphicsOverlay mLocationOverlay;
    public ControllerTasklistView mTasklistView;
    public ControllerTaskReasonView mCtaskreason;
    public ArcGISMapImageLayer mainMapImageLayer;
    public Handler mHandlertime = new Handler();
    public Runnable rTime = new Runnable() {

        @Override
        public void run() {
            mMainUIView.setTime(false);
            //每隔1s循环执行run方法
            mHandlertime.postDelayed(this, 1000);//延时1000毫秒

        }
    };
    public Intent itS;
    public AppAttribute mAAtt;
    public GraphicsOverlay mTouchgraphicsOverlay; // 画圆图层
    public ViewonClickMapDetail mViewonCMD;
    public ViewFourthFragmentSeachdetail mViewFourthFragmentSeachdetail;//搜索查询view
    public EditText mEtseach;
    TextView mTvseach;
    ImageView mIvback, mIvspeak, mIvclean, mIvUpload;
    String tvSeachStr = "";
    LoadingWaitView mLoadingWaitView;
    ControllerAddPhotoView mAddPhotoView;
    ControllerDetailsView mDetailsView;
    ControllerTasklnewistView mTasklnewistView;
    String strMapUrl;
    CenterPopWindow mPopWindow;
    boolean isnavi = false;
    boolean ismLegendinit = true;
    public GraphicsOverlay markerOverlay;
    public GraphicsOverlay mStarOverlay;
    Point point;
    LatLng llold;
    public Runnable r = new Runnable() {

        @Override
        public void run() {

            if (!AppTool.isNull(AppAttribute.F.getXJID(MainMapXJActivity.this))) {
//                setLatlonupload();
                //每隔1s循环执行run方法
//                mHandler.postDelayed(this, 5 * 1000);
            }
        }
    };
    int mTablesum = 0;
    boolean isFristLoaded = false;//判断是否查询到数据
    QueryParameters query;
    int showLayerSum = 0;
    ArrayList<AdapterBean> abListJS = new ArrayList<>();
    boolean isLoaded = false;
    LayerList operationalLayers;
    ArcGISMapImageLayer mMapImageLayer;
    boolean isAgainStart = false;
    int Tasklistsize = 0;
    //派单mark
    public GraphicsOverlay graphicsOverlaysld;
    IatUtil mIatUtil;
    boolean is = true;
    boolean isFristLoca = true;
    public Runnable runisLocaSuccess = new Runnable() {

        @Override
        public void run() {
            if (!MapUtil.isLegalLL(aMapLocation)) {
                mHandlertime.postDelayed(this, 3000);//延时1000毫秒
                return;
            }
            BreakOffBean.isContinue = true;
            if (BreakOffBean.getInitSingle().Task == 0) {
                taskDaily(false);
            } else {
                taskPai(false);

            }
            SPUtil.getInstance(MainMapXJActivity.this).setBooleanValue(SPUtil.ISBREAKOFF, false);

        }
    };
    public Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    ArrayList<AdapterBean> abList = (ArrayList<AdapterBean>) msg.obj;

                    if (msg.arg1 == AppConfig.PSGDJ1 || msg.arg1 == AppConfig.PSGDJ2 || msg.arg1 == AppConfig.PSGDJ3 || msg.arg1 == AppConfig.PSGDWSJ1 || msg.arg1 == AppConfig.PSGDWSJ2) {
                        if (msg.arg1 == AppConfig.PSGDJ1) {
                            abListJS.clear();
                            addabListJS(abList);
                            MapUtil.getInstance(MainMapXJActivity.this).FeatureQueryResulPSJ(query, MainMapXJActivity.this, AppConfig.PSGDJ2 + "");
                        } else if (msg.arg1 == AppConfig.PSGDJ2) {
                            addabListJS(abList);
                            MapUtil.getInstance(MainMapXJActivity.this).FeatureQueryResulPSJ(query, MainMapXJActivity.this, AppConfig.PSGDJ3 + "");
                        } else if (msg.arg1 == AppConfig.PSGDJ3) {
                            addabListJS(abList);
//                            mFragmentView.mRbf1.setText("雨水井");
                            mViewonCMD.mNotifyDataSet(abListJS, AppConfig.PSJ);
                        } else if (msg.arg1 == AppConfig.PSGDWSJ1) {
                            abListJS.clear();
                            addabListJS(abList);
                            MapUtil.getInstance(MainMapXJActivity.this).FeatureQueryResulPSJ(query, MainMapXJActivity.this, AppConfig.PSGDWSJ2 + "");
                        } else if (msg.arg1 == AppConfig.PSGDWSJ2) {
                            addabListJS(abList);
//                            mFragmentView.mRbf1.setText("污水井");
                            mViewonCMD.mNotifyDataSet(abListJS, AppConfig.PSJ);
                        }

                    } else {
                        if (abList != null && abList.size() > 0) {
                            isFristLoaded = true;
                            mViewonCMD.mNotifyDataSet(abList, msg.arg1);
                        }
                    }
                    if (msg.arg1 != AppConfig.PSGDJ1 && msg.arg1 != AppConfig.PSGDJ2 && msg.arg1 != AppConfig.PSGDWSJ1) {

                        mTablesum++;
                    }
                    if (mTablesum == showLayerSum) {

                        if (!isFristLoaded) {
                            mViewonCMD.setVisibility(View.GONE);
                        } else {
                            mViewonCMD.showData();
                        }
                        mLoadingWaitView.setVisibility(View.GONE);
                    }

                    break;
                case 2:
                    mLoadingWaitView.setVisibility(View.GONE);
                    ArrayList<AdapterBean> abList1 = (ArrayList<AdapterBean>) msg.obj;
//                        mVfragmentSeachlist.mNotifyDataSet(abList1 ,msg.arg1);

                    break;
                case 3:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private BroadcastReceiver broadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String acString = intent.getAction();
            if (acString.equals(LocService.LOCATION_ACTION)) {
                AppAttribute.G.TrackUPTimer++;
                if (!AppTool.isNull(S_RECODE_ID) && !mMainUIView.mIvpause.isSelected()) {
                    double distance = intent.getDoubleExtra("distance", 0);
                    setLatlonupload(distance);//坐标上传
                }

            } else if (acString.equals(LocService.TIME_ACTION)) {
//                mMainUIView.setTime();

            } else if (acString.equals(Intent.ACTION_SCREEN_ON)) { // 屏幕亮

//                Toast.makeText(MainActivity.this, "由于屏幕锁定熄屏，期间的巡查轨迹没有被记录", Toast.LENGTH_LONG).show();

            }
        }
    };

    private boolean isHasData;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mainxjmap;
    }

    //
    @Override
    public void init() {
        MapUtil.init(this);
        MapUtil.ui = 0;
        onResume = false;
        MapUtil.getInstance(this).setActivity(this);
        initFilter();
        itS = new Intent(this, LocService.class);
        takePhoto = getTakePhoto();
        setTitle("排水设施移动巡查");
//        setReplacepictures(R.mipmap.btn_xuncha_xuncjlu);
//        setOnClickListener(this);
        setReplacepicturesleft(R.mipmap.nav_back, this);
        isRegistered(true, this);
        mLoadingWaitView = findViewById(R.id.loadingWaitView);
        mLoadingWaitView.setRestLoadListener(this);
//        mLoadingWaitView.setVisibility(View.VISIBLE);

        mIvshou = findViewById(R.id.iv_shou);
        mMainUIView = findViewById(R.id.c_mainui);// TODO 暂时注释
        mMainUIView.setCallBackMap(this);
        mMainUIView.mIvpause.setSelected(false);
        mTasklistView = findViewById(R.id.c_tasklist);
        mTaskReportView = findViewById(R.id.c_taskreport);
        mTaskDealView = findViewById(R.id.c_taskdeal);
        mAddPhotoView = findViewById(R.id.c_addphoto);
        mDetailsView = findViewById(R.id.c_details);
        mCtaskreason = findViewById(R.id.c_taskreason);
        mCtaskreason.isShowLlreason(false, 100);
        mLegend = findViewById(R.id.v_legend);
        mLegend.setMapdownloadListen(this);
        mLegend.setCallBackMap(this);
        mViewonCMD = findViewById(R.id.v_oncmd);
        mViewonCMD.setaListen(new ViewonClickMapDetail.A() {
            @Override
            public void callBack() {
                initStyleSingleTap();
            }
        });
        mVXJSeachlist = findViewById(R.id.v_xjseach);
        mViewFourthFragmentSeachdetail = findViewById(R.id.c_xjsd);
        mMapView = findViewById(R.id.mapv);
        mMapView.setAttributionTextVisible(false);
        mTrackBiz = new TrackBiz(this);
        mAAtt = new AppAttribute(this);
        strMapUrl = MapUtil.getInstance(this).getvtppath();
//        new MapdownloadUtil(this).setCallBackMap(this).set();
//        new MapdownloadUtil(this).setCallBackMap(this).requestMapbase(mLoadingWaitView);
        initVLlTop();//搜索头部
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
                    mVXJSeachlist.isQuerySeach = tvSeachStr.equals(editable.toString());

                } else {
                    mIvspeak.setVisibility(View.VISIBLE);
                    mTvseach.setVisibility(View.GONE);
                    mIvclean.setVisibility(View.GONE);
                    mVXJSeachlist.isQuerySeach = false;
                    mVXJSeachlist.clearDataAdapter();
                }
            }
        });
        presenter.getRequestDictionaries();
        presenter.clickRequestTasklist(SPUtil.getInstance(this).getStringValue(SPUtil.USERNO), "W1006500002");

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        presenter.clickRequestTasklist(SPUtil.getInstance(this).getStringValue(SPUtil.USERNO), "W1006500002");
    }

    boolean onResume = false;

    @Override
    public void onResume() {
        super.onResume();
        if (!mMainUIView.mIvpause.isSelected() && mMainUIView.isStartde && onResume) {
            if (mAAtt != null && mAAtt.a != null) {
                mAAtt.a.isOnstop = false;
            }
            mMainUIView.setTime(true);
            mHandlertime.postDelayed(rTime, 1000);
        }
        onResume = true;
        mMapView.resume();
        }

    @Override
    protected void onStop() {
        super.onStop();
        if (mMainUIView.isStartde) {
            if (mAAtt != null && mAAtt.a != null) {
                mAAtt.a.isOnstop = true;
            }
            mHandlertime.removeCallbacks(rTime);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isStartde=false;
        MapdownloadUtil.isStop = true;
        try {
            unregisterReceiver(broadcast);
        } catch (Exception e) {
        }
        mMapView.dispose();
        mMainUIView.setDoingstyle(false);
        Graphicsclear(mLocationOverlay);
        Graphicsclear(mTouchgraphicsOverlay);
        Graphicsclear(onClickgraphicsOverlayLine);
        Graphicsclear(onClickgraphicsOverlayPoint);
        Graphicsclear(onClickgraphicsOverlay);
        Graphicsclear(mLocationOverlay);
//        mTouchgraphicsOverlay=null;
//        mTouchgraphicsOverlay=null;
        mMapView.dispose();
    }

    public void Graphicsclear(GraphicsOverlay gOverlay) {
        if (gOverlay != null && gOverlay.getGraphics() != null) {
            gOverlay.getGraphics().clear();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_activity_options:
                Intent i = new Intent(MainMapXJActivity.this, XJRecordActivity.class);
                startActivityForResult(i, 1);
                break;
            case R.id.btn_activity_lift:
                isExit();
                break;
            case R.id.iv_topback:
                mLoadingWaitView.setVisibility(View.GONE);
                setmEtseachstate(false);
                break;
            case R.id.et_topseach:
                if (mVXJSeachlist.getVisibility() == View.GONE) {
                    setmEtseachstate(true);
                    //获取历史搜索列表
                    String user = SPUtil.getInstance(this).getStringValue(SPUtil.USERNO);
                    presenter.requestSearchHis(user);
                }
                break;
            case R.id.iv_topspeak:
                if (mVXJSeachlist.getVisibility() == View.VISIBLE) {
                    if (mIatUtil == null) {
                        mIatUtil = new IatUtil(this, mEtseach);
                    }
                    mIatUtil.startSpeek(1);
                } else {
                    setmEtseachstate(true);
                }

                break;
            case R.id.iv_topclean:
                mEtseach.setText("");
                //获取历史搜索列表
                String user = SPUtil.getInstance(this).getStringValue(SPUtil.USERNO);
                presenter.requestSearchHis(user);
                break;
            case R.id.tv_topseach:
                tvSeachStr = mEtseach.getText().toString();
                int mCheckedId = mVXJSeachlist.getCheckedItem();
                submitSearchStr(tvSeachStr);
                if (AppTool.isNull(mEtseach.getText().toString())) {
                    return;
                }
                querySeach(mCheckedId, null);
                break;
            case R.id.settitle_img://上传附件
//                DBManager.getFailXJDao(this).deleteAll();
                if (isHasData) {
                    showUploadDialog();
                } else {
                    ToastUtils.showToast("本地暂无数据！");
                }
                break;
        }
    }

    private void showUploadDialog() {
        List<FailXJResult> xjResults = DBManager.getFailXJDao(this).queryBuilder().where(FailXJResultDao.Properties.IsSave.eq(false)).list();

        View view = View.inflate(activity, R.layout.dialog_tips, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                .setTitle("提示").setView(view).setCancelable(false);
        final AlertDialog dialog = builder.create();
        TextView tvSure = view.findViewById(R.id.tv_ok);
        TextView tv_no = view.findViewById(R.id.tv_no);
        TextView tv_content = view.findViewById(R.id.tv_content);
        tv_content.setText("共有" + xjResults.size() + "条本地数据待上传，是否现在上传？");
        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                presenter.uploadFile(MainMapXJActivity.this);
            }
        });
        dialog.setCancelable(true);
        dialog.show();
    }

    public void setSearchEdit(String str) {
        mEtseach.setText(str);
    }


    public void isContinueTask() {
        if (!SPUtil.getInstance(MainMapXJActivity.this).getBooleanValue(SPUtil.ISBREAKOFF, false))
            return;

        String breakoffstr = SPUtil.getInstance(MainMapXJActivity.this).getStringValue(SPUtil.BREAKOFFBEAN);//获取崩溃前巡检数据信息缓存
        BreakOffBean boBean = new Gson().fromJson(breakoffstr, BreakOffBean.class);
        BreakOffBean.getInitSingle().setBean(boBean);
        MapUtil.getInstance(this).showIsContinueTaskDialog(this, new GoBackInteDef.A() {
            @Override
            public void goBack(int falg) {
                myHandler.postDelayed(runisLocaSuccess, 0);
            }
        });
    }
boolean isFistsetMap=true;
    @Override
    public void onClick(String mOnClick) {
        switch (mOnClick) {
            case "DOWNLOADSUCCESS"://地图加载成功后初始化处理其他数据
                mLoadingWaitView.setVisibility(View.GONE);
                if (!MapUtil.getInstance(this).isExistspath(MapdownloadUtil.strMapUrl) || mMapView == null)
                    return;

                ArcGISRuntimeEnvironment.setLicense("runtimelite,1000,rud5799562951,none,PM0RJAY3FLLR2B3TR001");
                mArcGISMapVector = MapUtil.getInstance(this).getArcGISMapVector(MapdownloadUtil.strMapUrl, mMapView);
 initGOverlays();
                isLoaded = false;
                mainMapImageLayer = MapUtil.getInstance(this).getMapImageLayer();

                MapUtil.getInstance(this).addMapService(mainMapImageLayer, mArcGISMapVector, this, false);
                mMapView.invalidate();
                isnavi = true;
                MapUtil.getInstance(this).showGpsDialog(this, 0);
                MapUtil.getInstance(this).setshanghai(mMapView);
                MapViewTouchListener mMapViewTouchListener = new MapViewTouchListener(this, mMapView);
                mMapView.setOnTouchListener(mMapViewTouchListener);

//                isContinueTask();
                break;
            case "DOWNLOADFIL":
//                mLoadingWaitView.failure();
                break;
            case "REMOVEMAPLOC":
//                removeLocaioc();
                break;
            case "TDTVEC":
                ismLegendinit = false;
                ArcGISVectorTiledLayer mainArcGISVectorTiledLayer = new ArcGISVectorTiledLayer(MapdownloadUtil.strMapUrl);
                Basemap mainBasemap = new Basemap(mainArcGISVectorTiledLayer);
                ArcGISMap mArcGISMapVector1 = new ArcGISMap(mainBasemap);
                ArcGISMapImageLayer mainMapImageLayer1 = new ArcGISMapImageLayer(AppConfig.GuanWangUrl);
                mainMapImageLayer = MapUtil.getInstance(this).addMapService(mainMapImageLayer1, mArcGISMapVector1, this, false);
                mMapView.setMap(mArcGISMapVector1);

                MapUtil.getInstance(this).setshanghai(mMapView);
                break;
            case "TDTIMG":
                ismLegendinit = false;
                WebTiledLayer webTiledLayer = TianDiTuMethodsClass.CreateTianDiTuTiledLayer(TianDiTuMethodsClass.LayerType.TIANDITU_IMAGE_2000);
                WebTiledLayer webTiledLayer1 = TianDiTuMethodsClass.CreateTianDiTuTiledLayer(TianDiTuMethodsClass.LayerType.TIANDITU_IMAGE_ANNOTATION_CHINESE_2000);
                Basemap mainBasemap1 = new Basemap(webTiledLayer);
                mainBasemap1.getBaseLayers().add(webTiledLayer1);
                ArcGISMap mArcGISMapVector2 = new ArcGISMap(mainBasemap1);
                ArcGISMapImageLayer mainMapImageLayer2 = new ArcGISMapImageLayer(AppConfig.GuanWangUrl);
                mainMapImageLayer = MapUtil.getInstance(this).addMapService(mainMapImageLayer2, mArcGISMapVector2, this, false);
                mMapView.setMap(mArcGISMapVector2);
                MapUtil.getInstance(this).setshanghai(mMapView);
                break;
        }
    }

    @Override
    public void getImageLayer(ArcGISMapImageLayer mImageLayer) {
        if (mImageLayer.getLoadStatus() == LoadStatus.LOADED) {
            mainMapImageLayer = mImageLayer;
            isLoaded = true;

            mLegend.setMainMapImageLayer(mainMapImageLayer);
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

    }

    @Override
    public void onRestLoad(LoadingWaitView loadWaitView) {
        new MapdownloadUtil(this).setCallBackMap(this);
    }

    @Override
    public void getQueryResultMap(ArrayList<AdapterBean> abList, int mTable) {
        setMessgae(abList, mTable, 1);
    }

    @Override
    public void getQueryResultMapSeach(ArrayList<AdapterBean> abList, int mTable) {
        setMessgae(abList, mTable, 2);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventBusCar(CustomMessage customMessage ) {

        presenter.clickRequestTasklistsum(SPUtil.getInstance(this).getStringValue(SPUtil.USERNO), "W1006500002");
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventBusCar(String sate) {
        if (MapUtil.L.equals(sate)) {

        } else if (MapUtil.TCI.equals(sate)) {//没有用的方法  废弃
            if (Task == 0) {
                presenter.RequestEndTask(MainMapXJActivity.S_RECODE_ID, AppTool.getCurrentDate(AppTool.FORMAT_YMDHMS));
            } else if (Task == 1) {
                isAgainStart = true;
                STASKIDTem = STASKID;
                presenter.clickTaskPaiReason1(STASKIDcurrent, "W1006500002");
            }

        } else if (MapUtil.TD.equals(sate)) {
            presenter.clickRequestIsDeal(MainMapXJActivity.this, S_MANGE_ID);


        } else if (MapUtil.CANCLE_TD.equals(sate)) {
            if (mTaskReportView != null && mTaskReportView.mTaskBean != null) {
                List<String> nameList = (List<String>) SPUtil.getInstance(this).getObjectValue(SPUtil.APP_PUSH);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < nameList.size(); i++) {
                    if (i == nameList.size() - 1) {
                        sb.append(nameList.get(i));
                    } else {
                        sb.append(nameList.get(i) + ",");
                    }
                }
                presenter.clickRequestPushMessage(sb.toString(), mTaskReportView.mTaskBean.addr, mTaskReportView.mTaskBean.clabig, mTaskReportView.mTaskBean.clasmall, mTaskReportView.mTaskBean.time);
            }
        } else if (MapUtil.czdw.equals(sate)) {
            if (MapUtil.isLegalLL(aMapLocation)) {
                g = PositionUtil.gcj_To_Gps84(aMapLocation.getLatitude(), aMapLocation.getLongitude());
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventBusCar(AMapLocation aMapLocation) {
        if (!MapUtil.isLegalLL(aMapLocation)) {
            return;
        }
        setLocicon();
        if (AppTool.isServiceRunning(MainMapXJActivity.this, LocService.class.getName())) {
            return;
        }
        MainMapXJActivity.aMapLocation = aMapLocation;
        if (mainMapImageLayer == null) {
            return;
        }
        if (isFristLoca && QPSWApplication.Locpoint!=null) {
            isFristLoca = !isFristLoca;
            mMapView.setViewpointCenterAsync(QPSWApplication.Locpoint, 10000);
        }
    }

    @Override
    public void takeSuccess(TResult result) {
        if (mTaskReportView.getVisibility() == View.VISIBLE) {
            //被调用
            mTaskReportView.notifyData(result);
        } else if (mTaskDealView.getVisibility() == View.VISIBLE) {
            mTaskDealView.notifyData(result);
        } else if (mAddPhotoView.getVisibility() == View.VISIBLE) {
            mAddPhotoView.notifyData(result);
        }
    }

    @Override
    public void takeFail(TResult result, String msg) {
    }

    @Override
    public void takeCancel() {
    }

    TrackBiz mTrackBiz;

    @Override
    public void requestSuccess(int resultid, String result) {
try {
//    Log.e("requestSuccess111",result);

        if (!AppTool.isNull(result) && !result.contains("error")) {
            //暂注释
           /* JSONObject JB=new JSONObject(result);
            if (resultid!=6&&resultid!=61&&resultid!=62&&resultid!=63&&resultid!=16&&resultid!=17&&resultid!=171&&JB.getInt("Code")!=200){
                ToastUtils.showToast(JB.getString("Msg"));
                return;
            }*/
            switch (resultid) {
                case 1:
//                    Log.e("requestSuccess111",result);
                    try {
                        JSONObject jsonObject=new JSONObject(result);
                        if (jsonObject.getInt("code")!=200){
                            AppAttribute.G.TrackUPError++;
                            return;
                        }
                        AppAttribute.G.TrackUPSuccess++;
//                        String s=jsonObject.getString("Data");
                        XYbean xYbean = new Gson().fromJson(result, XYbean.class);
                        Log.e("requestSuccess111",xYbean.toString());
                        if (!mMainUIView.isStartde)
                            return;
                        mTrackBiz.inserttasktrack(new TrackBean(xYbean));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//后台接口改动后打开注释
// Distance = Distance + xYbean.NMILEAGE;
//            mMainUIView.setDis(Distance / 1000);//坐标上传成功的里程数计算显示
//                    mAAtt.a.drawR(mMapView, xYbean.N_Y + dr, xYbean.N_X + dr);
//                    mAAtt.getASingle().drawR(mMapView, xYbean.N_Y, xYbean.N_X);

                    break;
                case 2:
                    mTaskReportView.FileRequest(MapUtil.FR);
                    break;
                case 3:
                    Log.e(TAG, "requestSuccess3: "+result);
                    ClasBean cb = new Gson().fromJson(result, ClasBean.class);

                    if (!"200".equals(cb.Code)){
                        ToastUtils.showToast(cb.Msg);
                        return;
                    }
                    cb.options = 1;
                    if (mAddPhotoView.getVisibility() == View.VISIBLE) {
                        cb.ui = 2;
                    } else {
                        cb.ui = 1;
                    }
                    mTaskReportView.setPopClas(cb);
                    break;
                case 4:
                    ClasBean cs = new Gson().fromJson(result, ClasBean.class);
                    if (!"200".equals(cs.Code)){
                        ToastUtils.showToast(cs.Msg);
                        return;
                    }
                    cs.options = 2;
                    if (mAddPhotoView.getVisibility() == View.VISIBLE) {
                        cs.ui = 2;
                    } else {
                        cs.ui = 1;
                    }
                    mTaskReportView.setPopClas(cs);
                    break;
                case 5:
//                    if (!mMainUIView.isStartde){//正在巡检中不弹出派单列表
//                        mMainUIView.mTvtaskpai.setVisibility(View.VISIBLE);
//                    }
                    TasklistBean tasklistBean = new Gson().fromJson(result, TasklistBean.class);
                    if (!"200".equals(tasklistBean.Code)){
                        ToastUtils.showToast(tasklistBean.Msg);
                        return;
                    }
                    Tasklistsize = tasklistBean.value.size();

                    mMainUIView.setmTvtaskpai(Tasklistsize);


                    if (Tasklistsize > 0) {
                        mTasklistView.setVisibility(View.VISIBLE);
                        mTasklistView.setdata(tasklistBean);
                        LatLng lls;
                        if (MapUtil.isLegalLL(aMapLocation)) {
                            lls = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                            if (Tasklistsize > 0) {
                                for (int i = 0; i < Tasklistsize; i++) {
                                    TasklistBean.ValueBean b = tasklistBean.value.get(i);
                                    if (!AppTool.isNull(b.N_X) && !AppTool.isNull(b.N_Y)) {
                                        tasklistBean.value.get(i).setDis(lls);
                                    }
                                }
                            }
                        } else {
                            ToastUtils.showToast("当前定位失败，不显示距离数据");
                        }
                    } else {
                        mTasklistView.setVisibility(View.GONE);
                        mTasklistView.setdata();
                    }
                    break;
                case 51:
                    TasklistBean tasklistBean1 = new Gson().fromJson(result, TasklistBean.class);
                    if (!"200".equals(tasklistBean1.Code)){
                        ToastUtils.showToast(tasklistBean1.Msg);
                        return;
                    }
                    Tasklistsize = tasklistBean1.value.size();
                    mMainUIView.setmTvtaskpai(Tasklistsize);
                    break;
                case 6://日常上报的文件上传成功
                    PhtotIdBean mPhtotIdBean = new Gson().fromJson(result, PhtotIdBean.class);
                    mTaskReportView.mTaskBean.setFileUrls2(mPhtotIdBean);
                    mTaskReportView.setVisibility(View.GONE);
                    addGraphicsOverlay(mTaskReportView.mTaskBean);
                    mTaskReportView.cleanData();
                    MapUtil.getInstance(this).showTaskdealDialog(this);
                    break;
                case 61://日常处置的文件上传成功
//                    if (Task==1){
//presenter.RequestEndTaskpai1(this,STASKIDcurrent);
//                    }else {
                    cancelDialog();
                    mTaskDealView.cleanData();
                    mTaskDealView.setVisibility(View.GONE);
                    presenter.clickRequestTasklistsum(SPUtil.getInstance(this).getStringValue(SPUtil.USERNO), "W1006500002");
//                    }
                    break;
                case 62://暂时没有用
                    cancelDialog();
                    mAddPhotoView.setVisibility(View.GONE);
                    addGraphicsOverlay(mAddPhotoView.mTaskBean);
                    mAddPhotoView.cleanData();
                    break;
                case 63://结束派单的文件上传成功
                    cancelDialog();
                    S_RECODE_ID = "";
                    mTaskDealView.cleanData();
                    mTaskDealView.setVisibility(View.GONE);
                    mMainUIView.setDoingstyle(false);
                    presenter.clickRequestTasklistsum(SPUtil.getInstance(this).getStringValue(SPUtil.USERNO), "W1006500002");
                    break;
                case 7://clickTaskStart   /日常開始成功的回调
                    taskDaily(true);
                    break;
                case 8://日常上报处置
                    mTaskDealView.FileRequest(MapUtil.FTD);
                    break;
                case 81:
                    presenter.RequestEndTaskpai1(this, STASKIDcurrent);
                    break;
                case 82://结束派单处置
                    g = PositionUtil.gcj_To_Gps84(aMapLocation.getLatitude(), aMapLocation.getLongitude());//图片上传参数用到这个坐标
                    mTaskDealView.FileRequest(MapUtil.FTDP);
                    break;
                case 9:
                    mAddPhotoView.FileRequest(MapUtil.FTA);
                    break;
                //派单执行成功的回调
                case 10://clickTaskPaiStart
                    taskPai(true);
                    break;
                case 102://废弃
                    if (isAgainStart) {
                        mMainUIView.setDoingstyle(false);
                        presenter.clickTaskPaiState(STASKIDTem);
                    } else {
                        mCtaskreason.clickTaskPaiReason2();
                    }
                    break;
                case 1021:
                    presenter.clickRequestTasklist(SPUtil.getInstance(this).getStringValue(SPUtil.USERNO), "W1006500002");
//  派单列表中拒绝
                    cancelDialog();
                    mCtaskreason.isShowLlreason(false, 100);
                    break;
                case 1022://派单执行中去退单
                    mCtaskreason.isShowLlreason(false, 100);
                    mMainUIView.setDoingstyle(false);
                    presenter.clickRequestTasklist(SPUtil.getInstance(this).getStringValue(SPUtil.USERNO), "W1006500002");
                    cancelDialog();
                    break;
                case 11:
                    ClasBean dictionaries = new Gson().fromJson(result, ClasBean.class);
                    if (!"200".equals(dictionaries.Code)){
                        ToastUtils.showToast(dictionaries.Msg);
                        return;
                    }
                    dictionaries.setDictionaries(this);
                    break;
                case 12:
                    mMainUIView.setDoingstyle(false);
                    break;
                case 177:
                    mMainUIView.setDoingstyle(false);
                    presenter.clickRequestTasklistsum(SPUtil.getInstance(this).getStringValue(SPUtil.USERNO), "W1006500002");
                    break;
                case 121:
                    mAAtt.getASingle().cleanR(mMapView);
                    mMainUIView.setDoingstyle(false);
                    break;
                case 122:
                    presenter.RequestEndTaskpai2(STASKIDcurrent);
                    break;
                case 123:
                    mTaskReportView.FileRequest(MapUtil.FTDP);//TODO 最后一步文件上传，不知道文件上传接口通不
//                        if ("W1007500004".equals(mtvBean.SSOURCE)){
//                            presenter.RequestEndTaskpai3(this,mTaskDealView.UTCTime,mtvBean.SMANGEIDREL);
//                        }else {
//                            presenter.RequestEndTaskpai4(this,mTaskDealView.UTCTime);
//                        }
                    break;
                case 124:
                    cancelDialog();
                    mTaskDealView.cleanData();
                    mTaskDealView.setVisibility(View.GONE);
                    mMainUIView.setDoingstyle(false);
                    break;
                case 125:
                    cancelDialog();
                    mTaskDealView.cleanData();
                    mTaskDealView.setVisibility(View.GONE);
                    mMainUIView.setDoingstyle(false);
                    break;
                case 13:
                    IsDealBean mIsDealBean = new Gson().fromJson(result, IsDealBean.class);
                    if (!"200".equals(mIsDealBean.Code)){
                        ToastUtils.showToast(mIsDealBean.Msg);
                        return;
                    }
                    mDetailsView.IsShowDeal(mIsDealBean.SISMANGE);
                    break;
                case 14://  废弃
                    RelevantTaskBean mRelevantTaskBean = new Gson().fromJson(result, RelevantTaskBean.class);
                    AppTool.getDictionaries(this);
                    List<TaskBean> tblist = TaskBean.mTaskBean(mRelevantTaskBean);
                    if (tblist.size() == 0)
                        return;
                    for (int j = 0; j < tblist.size(); j++) {
                        addGraphicsOverlay(tblist.get(j));
                    }
                    break;
                case 15://废弃
                    DetailsBean mDetailsBean = new Gson().fromJson(result, DetailsBean.class);
                    DetailsBean.ValueBean b = mDetailsBean.value.get(0);
                    presenter.RequestReportDetailsPhoto(b.SSJCZID);
                    break;
                case 16://废弃
                    PhtotIdBean mPhtotIdBean1 = new Gson().fromJson(result, PhtotIdBean.class);
                    mDetailsView.setPhotonotifyData(TaskBean.Builder().setFileUrls1(mPhtotIdBean1).fileUrls);
                    break;
                case 17:
                    PhtotIdBean mPhtotIdBean3 = new Gson().fromJson(result, PhtotIdBean.class);
                    if (mPhtotIdBean3.app.size() > 0) {
                        mTaskDealView.setDate(mPhtotIdBean3);
                    }
                    break;
                case 171:
                    PhtotIdBean mPhtotIdBean4 = new Gson().fromJson(result, PhtotIdBean.class);
                    mDetailsView.setPhotonotifyData(TaskBean.Builder().setFileUrls(mPhtotIdBean4).fileUrls);
                    break;
                case 18:
                    TasklistBean tlb = new Gson().fromJson(result, TasklistBean.class);
                    mTaskDealView.setTaskRefuselistView(tlb);

                    break;
                case 19://上报完成后主动领取任务处置
                    presenter.clickRequestIsDealDetail(S_MANGE_ID);
                    presenter.clickRequestTasklistsum(SPUtil.getInstance(this).getStringValue(SPUtil.USERNO), "W1006500002");
                    break;
                case 20://上报完成后主动领取任务处置确定后查询任务详情接口回调
                    ListBean taskldetailBean = new Gson().fromJson(result, ListBean.class);
                    if (!"200".equals(taskldetailBean.Code)){
                        ToastUtils.showToast(taskldetailBean.Msg);
                        return;
                    }
                    mTaskDealView.showView(true);
                    mTaskDealView.initDatadaily(taskldetailBean);
                    break;
                case 21:
                    ListBean taskldetailBean1 = new Gson().fromJson(result, ListBean.class);
                    if (!"200".equals(taskldetailBean1.Code)){
                        ToastUtils.showToast(taskldetailBean1.Msg);
                        return;
                    }
//                        mTaskDealView.showView(true);
//                        mTaskDealView.initDatadaily(taskldetailBean1);
                    mDetailsView.setVisibility(View.VISIBLE);
                    mDetailsView.setData(taskldetailBean1);
                    break;
                case 22:
                    ListBean taskldetailBean2 = new Gson().fromJson(result, ListBean.class);
                    mTaskDealView.showView(false);
                    mTaskDealView.initDatadaily(taskldetailBean2);

                    break;
                case 10000://补报数据成功
                    mIvUpload.setImageResource(R.mipmap.btn_update_no);
                    isHasData = false;
                    break;
            }
        } else {
            if (resultid!=1){
                ToastUtils.showToast(result);
            }
        }

}catch (Exception e){

}
    }

    @Override
    public void requestSuccess(int resultid, String result, boolean boo) {
        if (!AppTool.isNull(result) && !result.contains("error")) {
            if (resultid == 1) {
                mObjectIdsBean = new Gson().fromJson(result, ObjectIdsBean.class);
                if (mObjectIdsBean == null || mObjectIdsBean.objectIds == null || mObjectIdsBean.objectIds.size() == 0) {
                    ToastUtils.showToast("没有查询到符合条件的数据");
                    return;
                }
                mVXJSeachlist.isQuerySeach = true;
                mVXJSeachlist.mSeachPageindex = 1;
                presenter.AdminGetObjectDetails(mAAtt.initB().urlarry[mVXJSeachlist.mCheckId], AppTool.getPagingID(mObjectIdsBean, mVXJSeachlist.mSeachPageindex), mAAtt.initB().filterkey1[mVXJSeachlist.mCheckId]);
            } else if (resultid == AppConfig.PSGDJ1 || resultid == AppConfig.PSGD || resultid == AppConfig.PSGDWSJ1 || resultid == AppConfig.PSGDWS || resultid == AppConfig.PFK || resultid == AppConfig.PSBZ || resultid == AppConfig.WSCLC || resultid == AppConfig.PSH || resultid == AppConfig.ZDPFK) {
                mVXJSeachlist.listviewseach.setVisibility(View.VISIBLE);
                mVXJSeachlist.mSearchHisView.setVisibility(View.GONE);
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
                if (mVXJSeachlist.mSeachPageindex == 1) {
                    mVXJSeachlist.mSeachAdapterBean.clear();
                    if (mVXJSeachlist.listviewseach.isRefreshing()) {
                        mVXJSeachlist.listviewseach.refreshComplete();
                    }
                }
                mVXJSeachlist.mSeachAdapterBean.addAll(b);
                if (mVXJSeachlist.mSeachAdapterBean.size() < mObjectIdsBean.objectIds.size()) {
                    mVXJSeachlist.mSeachPageindex++;
                    if (mVXJSeachlist.listviewseach.isLoading()) {
                        mVXJSeachlist.listviewseach.loadMoreComplete();
                        //如果没有更多数据就显示没有更多数据提示
                    }
                } else {
                    mVXJSeachlist.listviewseach.setNoMoreDate(true);
                }
                mVXJSeachlist.mTradapter.notifyDataSetChanged();
            } else if (resultid == 3) {//获取历史记录列表
                SearchHistory history = new Gson().fromJson(result, SearchHistory.class);
                if (history == null) {
                    return;
                }
                if (history.getData().size() == 0) {
                    mVXJSeachlist.mSearchHisView.setVisibility(View.GONE);
                    mVXJSeachlist.listviewseach.setVisibility(View.VISIBLE);
                } else {
                    mVXJSeachlist.mSearchHisView.setVisibility(View.VISIBLE);
                    mVXJSeachlist.listviewseach.setVisibility(View.GONE);
                }
                mVXJSeachlist.historyList.clear();
                mVXJSeachlist.historyList.addAll(history.getData());
                mVXJSeachlist.mHisAdapter.notifyDataSetChanged();
            } else if (resultid == 4) {//清空历史记录
                mVXJSeachlist.mSearchHisView.setVisibility(View.GONE);
                mVXJSeachlist.listviewseach.setVisibility(View.VISIBLE);
            }

        } else {
            mLoadingWaitView.setVisibility(View.GONE);
            ToastUtils.showToast(result);
        }
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
    public void requestFailure(int resultid, String result, boolean b) {

    }

    @Override
    public void requestFailure(int resultid, String result) {

//        switch (resultid) {
//            case 5:
////                mMainUIView.mTvtaskpai.setVisibility(View.GONE);
//                break;
//            case 6://日常上报的文件上传失败
//                showTipDialog(6);
//                break;
//            case 61://日常处置的文件上传失败
//                showTipDialog(61);
//                break;
//            case 62://暂时没有用
//                showTipDialog(62);
//                break;
//            case 63://结束派单的文件上传失败
//                showTipDialog(63);
//                break;
//        }
    }

    @Override
    public void requestFailure(int resultid, String result, Map<String, Object> map, ArrayList<TImage> images, String videoPath, String audioPath) {
        double x = (double) map.get("x");
        double y = (double) map.get("y");
        String relyid = (String) map.get("relyid");

        switch (resultid) {
            case 6://日常上报的文件上传失败
                showTipDialog(6);
                saveParams(x, y, relyid, images, videoPath, audioPath, 6);
                break;
            case 61://日常处置的文件上传失败
                showTipDialog(61);
                saveParams(x, y, relyid, images, videoPath, audioPath, 61);
                break;
            case 62://暂时没有用
                showTipDialog(62);
                break;
            case 63://结束派单的文件上传失败
                showTipDialog(63);
                saveParams(x, y, relyid, images, videoPath, audioPath, 63);
                break;
        }
    }

    //保存参数到本地数据库
    public void saveParams(double x, double y, String relyid, ArrayList<TImage> images, String videoPath, String audioPath, int type) {
        FailXJResult result = new FailXJResult();
        result.setX(x + "");
        result.setY(y + "");
        result.setAudioUrl(audioPath);
        result.setRelyid(relyid);
        result.setXjId(type + "");
        result.setVideoUrl(videoPath);
        result.setXjType(type);
        DBManager.getFailXJDao(this).insert(result);

        for (TImage tImage : images) {
            FilePath filePath = new FilePath(null, type + "", tImage.getCompressPath());
            DBManager.getFilePathDao(this).insert(filePath);
        }

        //本地数据保存成功后，上传图标变为gif
        Glide.with(this).asGif().load(R.drawable.btn_update_no).into(mIvUpload);
        isHasData = true;
    }

    /**
     * 附件上传失败的提示框
     */
    public void showTipDialog(final int type) {

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 有白色背
        window.setContentView(R.layout.dialog_net_tips);
        window.findViewById(R.id.version_describe).setVisibility(View.VISIBLE);
        TextView tv_content = window.findViewById(R.id.tv_content);
        tv_content.setText("当前网络状态不稳定，附件上传失败，请稍后手动上传！");

        window.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                if (type == 6) {
                    mTaskReportView.setVisibility(View.GONE);
                    addGraphicsOverlay(mTaskReportView.mTaskBean);
                    mTaskReportView.cleanData();
                    MapUtil.getInstance(MainMapXJActivity.this).showTaskdealDialog(MainMapXJActivity.this);
                    //保存没有上传成功的附件及需要上传的一些参数，需要保存一个map和一个list


                } else if (type == 61) {
                    cancelDialog();
                    mTaskDealView.cleanData();
                    mTaskDealView.setVisibility(View.GONE);
                    presenter.clickRequestTasklistsum(SPUtil.getInstance(MainMapXJActivity.this).getStringValue(SPUtil.USERNO), "W1006500002");
                } else if (type == 62) {
                    cancelDialog();
                    mAddPhotoView.setVisibility(View.GONE);
                    addGraphicsOverlay(mAddPhotoView.mTaskBean);
                    mAddPhotoView.cleanData();
                } else if (type == 63) {
                    cancelDialog();
                    S_RECODE_ID = "";
                    mTaskDealView.cleanData();
                    mTaskDealView.setVisibility(View.GONE);
                    mMainUIView.setDoingstyle(false);
                    presenter.clickRequestTasklistsum(SPUtil.getInstance(MainMapXJActivity.this).getStringValue(SPUtil.USERNO), "W1006500002");
                }
            }
        });
    }

    @Override
    protected MainMapXJRequestPresenter createPresenter() {
        return new MainMapXJRequestPresenter() {
        };
    }

    public void initVLlTop() {
        mLltop = findViewById(R.id.ll_top);
        mIvback = findViewById(R.id.iv_topback);
        mIvback.setOnClickListener(this);
        mIvspeak = findViewById(R.id.iv_topspeak);
        mIvspeak.setOnClickListener(this);
        mIvspeak.setVisibility(View.VISIBLE);
        mIvclean = findViewById(R.id.iv_topclean);
        mIvclean.setOnClickListener(this);
        mTvseach = findViewById(R.id.tv_topseach);
        mTvseach.setOnClickListener(this);
        mEtseach = findViewById(R.id.et_topseach);
        mEtseach.setOnClickListener(this);

        mIvUpload = findViewById(R.id.settitle_img);
        mIvUpload.setVisibility(View.VISIBLE);
        mIvUpload.setOnClickListener(this);
        setmEtseachstate(false);

        //判断是否有本地数据缓存
        List<FailXJResult> xjResults = DBManager.getFailXJDao(this).queryBuilder().where(FailXJResultDao.Properties.IsSave.eq(false)).list();

        if (xjResults != null && !xjResults.isEmpty()) {
            Glide.with(this).asGif().load(R.drawable.btn_update_no).into(mIvUpload);
            isHasData = true;
        } else {
            mIvUpload.setImageResource(R.mipmap.btn_update_no);
            isHasData = false;

        }
    }

    public void setmEtseachstate(boolean isEdit) {
        mLegend.setVisibility(View.GONE);
        mEtseach.setCursorVisible(isEdit);
        mEtseach.setFocusable(isEdit);
        mEtseach.setFocusableInTouchMode(isEdit);
        mVXJSeachlist.setVisibility(isEdit ? View.VISIBLE : View.GONE);
        mIvspeak.setVisibility(isEdit ? View.GONE : View.VISIBLE);
        mIvback.setVisibility(isEdit ? View.VISIBLE : View.INVISIBLE);
        mVXJSeachlist.showOrHide(isEdit);
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

    public void submitSearchStr(String str) {
        if (AppTool.isNull(str)) {
            return;
        }
        String user = SPUtil.getInstance(this).getStringValue(SPUtil.USERNO);
        presenter.addSearchHis(user, str);
    }

    public void querySeach(int mCheckedId, String str) {

        mObjectIdsBean = null;
        mVXJSeachlist.clearDataAdapter();
        String townname = SPUtil.getInstance(this).getStringValue(SPUtil.APP_TOWNNAME);
        presenter.AdminGetObjectIds(String.valueOf(mAAtt.initB().urlarry[mCheckedId]), mAAtt.initB().filterkey[mCheckedId], StringUtils.isEmpty(str) ? tvSeachStr : str, townname);
    }

    public void clearHis(List<SearchHistory.DataBean> hisList, int pos, SearchHisAdapter mAdapter) {
        showClearDialog(hisList, mAdapter);
    }

    private void showClearDialog(final List<SearchHistory.DataBean> hisList, final SearchHisAdapter mAdapter) {
        View view = View.inflate(activity, R.layout.dialog_tips, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                .setTitle("提示").setView(view).setCancelable(false);
        final AlertDialog dialog = builder.create();
        TextView tvSure = view.findViewById(R.id.tv_ok);
        tvSure.setText("立即清空");
        TextView tv_no = view.findViewById(R.id.tv_no);
        TextView tv_content = view.findViewById(R.id.tv_content);
        tv_content.setText("清空历史记录？");
        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                String user = SPUtil.getInstance(MainMapXJActivity.this).getStringValue(SPUtil.USERNO);
                presenter.clearHisList(hisList, user, mAdapter);
            }
        });
        dialog.setCancelable(true);
        dialog.show();
    }

    private void addGraphicsOverlay(final TaskBean tb) {
        if (tb == null)
            return;
        final PictureMarkerSymbol pinDestinationSymbol;
        try {
            {
                pinDestinationSymbol = PictureMarkerSymbol.createAsync(mAAtt.initB().endDrawable).get();
                pinDestinationSymbol.loadAsync();
                pinDestinationSymbol.addDoneLoadingListener(new Runnable() {
                    @Override
                    public void run() {
                        Map<String, Object> m = new HashMap<>();
                        m.put("markerdata", new Gson().toJson(tb));
                        point = new Point(tb.y, tb.x, SpatialReferences.getWgs84());
                        Graphic graphic = new Graphic(point, m, pinDestinationSymbol);
                        markerOverlay.getGraphics().add(graphic);
                    }
                });
                pinDestinationSymbol.setOffsetY(20);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        if (!mMapView.getGraphicsOverlays().contains(markerOverlay)) {
            mMapView.getGraphicsOverlays().add(markerOverlay);
        }
    }

    public void initTask() {
        RxHttpUtils.cancelAllRequest();
        if (markerOverlay != null && markerOverlay.getGraphics() != null) {
            markerOverlay.getGraphics().clear();
        }
        S_RECODE_ID = "";//记录编号
        S_MANGE_ID = "";//事件编号
        STASKID = "";//派单任务id
        Distance = 0;//巡检距离
        stopService(itS);
//       LocService. mHandler.removeCallbacks(r);
    }
    public void initStyleSingleTap() {
        isFristLoaded = false;
        mTablesum = 0;
        mViewonCMD.initStyle();
        mTouchgraphicsOverlay.getGraphics().clear();
        onClickgraphicsOverlayPoint.getGraphics().clear();
        onClickgraphicsOverlayLine.getGraphics().clear();
        onClickgraphicsOverlay.getGraphics().clear();
        onClickTextgraphicsOverlay.getGraphics().clear();
        onClickMarkergraphicsOverlay.getGraphics().clear();
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

    public void addabListJS(ArrayList<AdapterBean> abList) {
        if (abList != null && abList.size() > 0) {
            isFristLoaded = true;
            abListJS.addAll(abList);
        }
    }

    private void initFilter() {
        // 注册路径信息广播接收器 -接收经纬度广播
        IntentFilter filter = new IntentFilter();
        filter.addCategory(getPackageName());
        filter.setPriority(100);
        filter.addAction(LocService.LOCATION_ACTION);
        filter.addAction(LocService.TIME_ACTION);
        // 屏幕关闭，和开启
        filter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(broadcast, filter);
    }

    public boolean onClickSingle(android.graphics.Point screenPoint) {
        if (mMapView.getMapScale() > 100000 || !isLoaded)
            return true;
        initStyleSingleTap();
        mViewFourthFragmentSeachdetail.setVisibility(View.GONE);
        Point point84 = mMapView.screenToLocation(screenPoint);
        mMapView.setViewpointCenterAsync(point84);
        int tolerance = 50;
        double mapTolerance = tolerance * mMapView.getUnitsPerDensityIndependentPixel();

        Envelope envelope = new Envelope(point84.getX() - 0.0001, point84.getY() - 0.0001, point84.getX() + 0.0001, point84.getY() + 0.0001, SpatialReferences.getWgs84());
        double[] doubles = CoordinateUtil.lonLat2Mercator(point84.getX(), point84.getY());
        double aDouble0 = doubles[0];
        double aDouble1 = doubles[1];
        Polygon polygon = GeometryEngine.buffer(point84, 0.00014);

        Graphic gra = new Graphic(polygon, mAAtt.initB().mTouchSymbol);
        mTouchgraphicsOverlay.getGraphics().add(gra);
        query = new QueryParameters();
        query.setReturnGeometry(true);//指定是否返回几何对象
        query.setGeometry(polygon);// 设置空间几何对象
        String townname = SPUtil.getInstance(this).getStringValue(SPUtil.APP_TOWNNAME);

        if (!AppTool.isNull(townname)) {


            query.setWhereClause( "upper(S_town)='" + townname + "'");
        }
        showLayerSum = mLegend.showLayerSum();
        if (showLayerSum != 0) {
            mLoadingWaitView.setVisibility(View.VISIBLE);
        } else {
            ToastUtils.showToast(" 请选择要查询的图层");
            initStyleSingleTap();
            return true;
        }
        if (mLegend.mLlguanwangys.isSelected()) {//TODO 还差添加污水
            abListJS.clear();
            MapUtil.getInstance(this).FeatureQueryResulPSGDYS(query, this);
            MapUtil.getInstance(this).FeatureQueryResulPSJ(query, this, AppConfig.PSGDJ1 + "");
        }
        if (mLegend.mLlguanwangws.isSelected()) {//TODO 还差添加污水
            abListJS.clear();
            MapUtil.getInstance(this).FeatureQueryResulPSGDWS(query, this);
            MapUtil.getInstance(this).FeatureQueryResulPSJ(query, this, AppConfig.PSGDWSJ1 + "");
        }
        if (mLegend.mLlpfk.isSelected()) {
            MapUtil.getInstance(this).FeatureQueryResulPFK(query, this);
        }
        if (mLegend.mLlbengzhan.isSelected()) {
            MapUtil.getInstance(this).FeatureQueryResulPSBZ(query, this);
        }
        if (mLegend.mLlwushuichang.isSelected()) {

            MapUtil.getInstance(this).FeatureQueryResulWSCLC(query, this);
        }
        if (mLegend.mLlzhongdianpfk.isSelected()) {
            MapUtil.getInstance(this).FeatureQueryResulZDPFK(query, this);
        }
        if (mLegend.mLlpaishuihu.isSelected()) {
            query.setGeometry(envelope);// 设置空间几何对象
            MapUtil.getInstance(this).FeatureQueryResulPSH(query, this);
        }
        return true;
    }

    public void setLocicon() {
        if (mMainUIView.mIvgensui.isSelected()) {//跟随
            MapUtil.getInstance(this).setGensui(aMapLocation, mMapView);
        }
        if (mLocationOverlay == null) {
            mLocationOverlay = new GraphicsOverlay();

        }
        MapUtil.getInstance(this).setLocicon(MainMapXJActivity.aMapLocation, mMapView, mLocationOverlay, "");//定位图标
    }

    public void taskDaily(boolean isFrist) {
        Task = 0;
        if (isFrist) {
            BreakOffBean.getInitSingle().init();//重新开始巡检，业务信息缓存初始化
            BreakOffBean.isContinue = false;
            BreakOffBean.getInitSingle().Task = Task;//缓存保存当前任务类型
            BreakOffBean.getInitSingle().S_RECODE_ID = MainMapXJActivity.S_RECODE_ID;
            mMainUIView.setDoingstyle(true);
        } else {
            S_RECODE_ID = BreakOffBean.getInitSingle().S_RECODE_ID;
            mMainUIView.setDoingstyle(true);
            mMainUIView.continueTask();
        }
    }
    public void initGOverlays(){
        if (mLocationOverlay == null) {
            mLocationOverlay = new GraphicsOverlay();
        }
        onClickgraphicsOverlay = new GraphicsOverlay();
        mTouchgraphicsOverlay = new GraphicsOverlay();
        onClickgraphicsOverlayPoint = new GraphicsOverlay();
        onClickgraphicsOverlayLine = new GraphicsOverlay();
        onClickTextgraphicsOverlay = new GraphicsOverlay();
        onClickMarkergraphicsOverlay = new GraphicsOverlay();
        markerOverlay = new GraphicsOverlay();
        mStarOverlay = new GraphicsOverlay();
        if (!mMapView.getGraphicsOverlays().contains(mLocationOverlay)) {
            mMapView.getGraphicsOverlays().add(mLocationOverlay);
        }
        if (!mMapView.getGraphicsOverlays().contains(onClickgraphicsOverlay)) {
            mMapView.getGraphicsOverlays().add(onClickgraphicsOverlay);
        }
        if (!mMapView.getGraphicsOverlays().contains(mTouchgraphicsOverlay)) {
            mMapView.getGraphicsOverlays().add(mTouchgraphicsOverlay);
        }
        if (!mMapView.getGraphicsOverlays().contains(onClickgraphicsOverlayLine)) {
            mMapView.getGraphicsOverlays().add(onClickgraphicsOverlayLine);
        }
        if (!mMapView.getGraphicsOverlays().contains(onClickgraphicsOverlayPoint)) {
            mMapView.getGraphicsOverlays().add(onClickgraphicsOverlayPoint);
        }
        if (!mMapView.getGraphicsOverlays().contains(onClickMarkergraphicsOverlay)) {
            mMapView.getGraphicsOverlays().add(onClickMarkergraphicsOverlay);
        }
        if (!mMapView.getGraphicsOverlays().contains(onClickTextgraphicsOverlay)) {
            mMapView.getGraphicsOverlays().add(onClickTextgraphicsOverlay);
        }

    }
    public void taskPai(boolean isFrist) {
        Task = 1;
        if (isFrist) {
            BreakOffBean.getInitSingle().init();//重新开始巡检，业务信息缓存初始化
            BreakOffBean.isContinue = false;
//            mTasklistView.paiID();
            STASKIDcurrent = STASKID;
            BreakOffBean.getInitSingle().Task = Task;//缓存保存当前任务类型
            BreakOffBean.getInitSingle().STASKIDcurrent = STASKIDcurrent;//记录当前派单id，当程序崩溃重新打开用到这个id
            BreakOffBean.getInitSingle().mtvBean = mtvBean;//记录当前派单数据信息，当程序崩溃重新打开用到这个数据
            cancelDialog();
            mMainUIView.setmTvtaskpai(Tasklistsize - 1);//也可以换成请求服务器刷新派单数
            mMainUIView.setDoingstyle(true);

        } else {
            mtvBean = BreakOffBean.getInitSingle().mtvBean;
            STASKIDcurrent = BreakOffBean.getInitSingle().STASKIDcurrent;
            mMainUIView.setDoingstyle(true);
            mMainUIView.continueTask();
        }
        mTasklistView.setVisibility(View.GONE);
        if(!AppTool.isNull(mtvBean.N_Y)&&!AppTool.isNull(mtvBean.N_X)){
        graphicsOverlaysld = MapUtil.getInstance(this).setstarico1(Double.parseDouble(mtvBean.N_Y), Double.parseDouble(mtvBean.N_X), mMapView);}
    }

    int lineIndex = 1;
    public long firstTime = 0;
    List<TraceLocation> mTracePList = new ArrayList<>();
    final List<TraceLocation> mTraceCList = new ArrayList<>();
    PointCollection pc = new PointCollection(SpatialReference.create(4326));

    public void setLatlonupload(double distance) {

        Gps g = PositionUtil.gcj_To_Gps84(aMapLocation.getLatitude(), aMapLocation.getLongitude());

        String user = SPUtil.getInstance(MainMapXJActivity.this).getStringValue(SPUtil.USERNO);
        LatLng llnew = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
        long secondTime = System.currentTimeMillis();//上次传送的时间点和这次的时间间隔
        if (llold != null) {
            double d = com.amap.api.maps2d.AMapUtils.calculateLineDistance(llnew, llold);
            if (!MapUtil.isLegalSpeed(d, secondTime - firstTime)) {//15 秒内不可能走300米
                return;
            }
//            if (d<10) {//上一次跟这次两点之间距离小于10米不处理坐标
//                return;
//            }
//            if (!MapUtil.isLegalTime(secondTime - firstTime, d)) {//15 秒内不可能走300米
//                return;
//            }
            Distance = Distance + d;
            mMainUIView.setDis(Distance / 1000);//坐标上传成功的里程数计算显示

        }

        llold = llnew;
        firstTime = secondTime;//作为上次传送的时间点
//ToastUtils.showToast(Distance+"------"+distance);
        mAAtt.getASingle().drawR(mMapView, g.getWgLat(), g.getWgLon());
        String S_TASK_TYPE = "";
        if (Task == 0) {
            S_TASK_TYPE = "W1007000001";
        } else if (Task == 1) {
            S_TASK_TYPE = "W1007000003";
        }
        AppAttribute.G.TrackUPPre++;
        presenter.clickRequestUPloca(System.currentTimeMillis() + user + AppTool.getDeviceIMEI(this), user, AppTool.getCurrentDate(AppTool.FORMAT_YMDHMS), g.getWgLon(), g.getWgLat(), AppTool.getDoubleAccurate(aMapLocation.getSpeed()), S_TASK_TYPE, S_RECODE_ID, AppTool.getTwoDecimal(Distance));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == 1) {
                    mMainUIView.setDoingstyle(false);//当时记录里面有上报功能，该功能已经屏蔽，此方法废弃，
                    MapUtil.getInstance(this).setstardailyloc(aMapLocation, this, 2);
                }
                break;
            case 2:
                if (resultCode == 2) {
                    if (mTaskReportView.getVisibility() == View.VISIBLE) {

                        mTaskReportView.onActivityResult(requestCode, resultCode, data);
                    } else if (mTaskDealView.getVisibility() == View.VISIBLE) {

                        mTaskDealView.onActivityResult(requestCode, resultCode, data);
                    }
                }
                break;
        }
    }

    //防止不小心按到返回键
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:

                if (mTasklistView.getVisibility() == View.VISIBLE) {
                    mTasklistView.setVisibility(View.GONE);
                } else if (mTaskReportView.getVisibility() == View.VISIBLE) {
                    if (mTaskReportView.mViewGalleryPhoto.getVisibility() == View.VISIBLE) {
                        mTaskReportView.mViewGalleryPhoto.setVisibility(View.GONE);
                    } else {
                        MapUtil.getInstance(this).showTaskreportDialog(this, mTaskReportView);
                    }

                } else if (mTaskDealView.getVisibility() == View.VISIBLE) {
                    if (mTaskDealView.mViewGalleryPhoto.getVisibility() == View.VISIBLE) {
                        mTaskDealView.mViewGalleryPhoto.setVisibility(View.GONE);
                    } else {
                        MapUtil.getInstance(this).showTaskreportDialog(this, mTaskDealView);
                    }
                } else if (mAddPhotoView.getVisibility() == View.VISIBLE) {
                    MapUtil.getInstance(this).showTaskreportDialog(this, mAddPhotoView);
                } else if (mDetailsView.getVisibility() == View.VISIBLE) {
                    if (mDetailsView.mViewGalleryPhoto.getVisibility() == View.VISIBLE) {
                        mDetailsView.mViewGalleryPhoto.setVisibility(View.GONE);
                    } else {
                        mDetailsView.setVisibility(View.GONE);
                    }
                } else if (mVXJSeachlist.getVisibility() == View.VISIBLE) {
                    mLoadingWaitView.setVisibility(View.GONE);
                    setmEtseachstate(false);
                } else {
                    return isExit();

                }

                break;
        }
        return true;
    }

    //记录第一次按返回键的时间
    private long firstTime1 = 0;

    public boolean isExit() {
        if (mMainUIView.isStartde) {
//            MapUtil.getInstance(this).showTaskendDialog(this);
            ToastUtils.showToast("正在巡检，无法退出");
        } else {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime1 > 2000) {                                         //如果两次按键时间间隔大于2秒，则不退出
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime1 = secondTime;//firstTime1
                return true;
            } else {                                                    //两次按键小于2秒时，退出应用
                finish();
                return false;
            }
        }
        return true;
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
            if (mIvshou.getVisibility() == View.VISIBLE) {
                point = mMapView.screenToLocation(new android.graphics.Point((int) e.getX(), (int) e.getY()));
            }

            final ListenableFuture<IdentifyGraphicsOverlayResult> identifyGraphic = mMapView.identifyGraphicsOverlayAsync(markerOverlay, screenPoint, 50.0, false, 1);

            identifyGraphic.addDoneListener(new Runnable() {
                @Override
                public void run() {
                    try {
                        IdentifyGraphicsOverlayResult grOverlayResult = identifyGraphic.get();
                        List<Graphic> graphic = grOverlayResult.getGraphics();
                        int identifyResultSize = graphic.size();
                        if (identifyResultSize > 0) {
                            String str = (String) graphic.get(0).getAttributes().get("markerdata");
                            if (!graphic.isEmpty()) {

                                TaskBean tb = new Gson().fromJson(str, TaskBean.class);

                                presenter.RequestISDeal(tb.S_MANGE_ID);
                                presenter.clickRequestIsDealDetail1(tb.S_MANGE_ID);

                            }
                        } else if (identifyResultSize == 0) {
                            if (mMainUIView.mIvsheshi.isSelected()) {
//                                onClickSingle(screenPoint);
                            } else {
                                //                            if (mIvshou.getVisibility() == View.VISIBLE) {
////                                mAddPhotoView.setVisibility(View.VISIBLE);//TODO 补漏功能
////                                mIvshou.setVisibility(View.GONE);
//                            }
                            }

                        }

                    } catch (Exception ie) {
                        ie.printStackTrace();
                    }

                }
            });

            return super.onSingleTapConfirmed(e);
        }

    }
}
