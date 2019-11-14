package com.wavenet.ding.qpps.activity;


import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bll.greendao.FailYHResultDao;
import com.bll.greendao.dbean.FailYHResult;
import com.bll.greendao.dbean.FilePath;
import com.bll.greendao.manager.DBManager;
import com.bumptech.glide.Glide;
import com.dereck.library.base.BaseMvpActivity;
import com.dereck.library.utils.GsonUtils;
import com.dereck.library.utils.SPUtils;
import com.dereck.library.utils.StringUtils;
import com.dereck.library.utils.ToastUtils;
import com.dereck.library.view.LoadingWaitView;
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.data.FeatureQueryResult;
import com.esri.arcgisruntime.data.QueryParameters;
import com.esri.arcgisruntime.data.ServiceFeatureTable;
import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.esri.arcgisruntime.layers.ArcGISVectorTiledLayer;
import com.esri.arcgisruntime.layers.FeatureLayer;
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
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleRenderer;
import com.google.gson.Gson;
import com.guoqi.actionsheet.ActionSheet;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.adapter.DeleteImageRecyclerViewAdapter;
import com.wavenet.ding.qpps.adapter.DesImageRecyclerViewAdapter;
import com.wavenet.ding.qpps.adapter.SearchHisAdapter;
import com.wavenet.ding.qpps.adapter.YHReportListViewAdapter;
import com.wavenet.ding.qpps.api.QPSWApplication;
import com.wavenet.ding.qpps.bean.AdapterBean;
import com.wavenet.ding.qpps.bean.ClasBean;
import com.wavenet.ding.qpps.bean.Gps;
import com.wavenet.ding.qpps.bean.ListPicUrlBean;
import com.wavenet.ding.qpps.bean.ObjectDetailsBean;
import com.wavenet.ding.qpps.bean.ObjectIdsBean;
import com.wavenet.ding.qpps.bean.PicBean;
import com.wavenet.ding.qpps.bean.RelyIdBean;
import com.wavenet.ding.qpps.bean.Response;
import com.wavenet.ding.qpps.bean.RoadBean;
import com.wavenet.ding.qpps.bean.SearchHistory;
import com.wavenet.ding.qpps.fragment.PlaybackFragment;
import com.wavenet.ding.qpps.interf.AddFeatureQueryResultListen;
import com.wavenet.ding.qpps.interf.AddLayerListen;
import com.wavenet.ding.qpps.interf.CallBackMap;
import com.wavenet.ding.qpps.interf.GoBackInteDef;
import com.wavenet.ding.qpps.mvp.p.MainMapYHRequestPresenter;
import com.wavenet.ding.qpps.mvp.v.MainMapYHActivityRequestView;
import com.wavenet.ding.qpps.utils.AppAttribute;
import com.wavenet.ding.qpps.utils.AppConfig;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.AppUtils;
import com.wavenet.ding.qpps.utils.CoordinateUtil;
import com.wavenet.ding.qpps.utils.LogUtils;
import com.wavenet.ding.qpps.utils.MapUtil;
import com.wavenet.ding.qpps.utils.MapdownloadUtil;
import com.wavenet.ding.qpps.utils.MaxTextLengthFilter;
import com.wavenet.ding.qpps.utils.PositionUtil;
import com.wavenet.ding.qpps.utils.SPUtil;
import com.wavenet.ding.qpps.utils.iflytek.voicedemo.IatUtil;
import com.wavenet.ding.qpps.view.ActionSheetSuper;
import com.wavenet.ding.qpps.view.DialogSound;
import com.wavenet.ding.qpps.view.DialogUtil;
import com.wavenet.ding.qpps.view.ViewFourthFragmentSeachdetail;
import com.wavenet.ding.qpps.view.ViewGalleryPhoto;
import com.wavenet.ding.qpps.view.ViewLegend;
import com.wavenet.ding.qpps.view.ViewYHSeachlist;
import com.wavenet.ding.qpps.view.ViewonClickMapDetail;
import com.wavenet.ding.qpps.view.WrapContentListView;
import com.wavenet.ding.qpps.xy.MyServiceYH;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.TImage;
import org.devio.takephoto.model.TResult;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;


public class MainMapYHActivity extends BaseMvpActivity<MainMapYHActivityRequestView, MainMapYHRequestPresenter> implements MainMapYHActivityRequestView, ActionSheet.OnActionSheetSelected, CallBackMap, View.OnLongClickListener, AddLayerListen, ViewGalleryPhoto.NotifyDataSetChangedListen, GoBackInteDef.GoBack, AddFeatureQueryResultListen {
    public final static int requestCountinuTask = 100;
    public static int resultCountinuTask = 100;
    public ArcGISMap mArcGISMapVector;
    public GraphicsOverlay mLocationOverlay;
    public ArcGISMapImageLayer mainMapImageLayer;
    @BindView(R.id.mapv)
    public MapView mapv;
    @BindView(R.id.iv_shanghai)
    ImageView ivShanghai;
    @BindView(R.id.tv_plus)
    TextView tvPlus;
    @BindView(R.id.tv_minus)
    TextView tvMinus;
    @BindView(R.id.iv_location)
    ImageView ivLocation;
    @BindView(R.id.iv_trackstart)
    ImageView ivTrackstart;
    @BindView(R.id.iv_trackcamera)
    ImageView ivTrackcamera;
    @BindView(R.id.iv_doing)
    ImageView ivDoing;
    @BindView(R.id.loadingWaitView)
    LoadingWaitView mLoadingWaitView;
    @BindView(R.id.llReport)
    LinearLayout llReport;
    @BindView(R.id.listview)
    WrapContentListView listview;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mRecyclerViewPhoto)
    RecyclerView mRecyclerViewPhoto;
    @BindView(R.id.llPhoto)
    LinearLayout llPhoto;
    @BindView(R.id.mRecyclerViewDetails)
    RecyclerView mRecyclerViewDetails;
    @BindView(R.id.llDetails)
    LinearLayout llDetails;
    @BindView(R.id.llBtn)
    LinearLayout llBtn;
    @BindView(R.id.tvPicNumReport)
    TextView tvPicNumReport;
    @BindView(R.id.iv_stop)
    ImageView mIvstop;
    public LinearLayout mLltop;
    public EditText mEtseach;
    TextView mTvseach;
    ImageView mIvback, mIvspeak, mIvclean;
    public ImageView mIvsheshi, mIvgensui;

    DeleteImageRecyclerViewAdapter imageRecyclerViewAdapter, photoRecyclerViewAdapter;
    String strMapUrl;
    //上报图片的集合
    ArrayList<TImage> allimages = new ArrayList<>();
    //补录图片的集合
    ArrayList<TImage> allPhotoimages = new ArrayList<>();
    //判断是上报的图片 还是补录的照片  1是上报  2 是补录
    String tag;
    BitmapDrawable endDrawable;
    GraphicsOverlay markerOverlay;
    Map<String, Object> map;
    //地图下方按钮是隐藏还是显示
    boolean checkBtn = true;
    //判断是补录的拍照还是直接拍照,因为类型不同添加mark的方法不同,now 现场,old为补录
    // String markType = "now";
    //每次补录  上传图片等等  需要的唯一标示
    public static String relyid = "";
    public static AMapLocation aMapLocation;
    Point point;
    String msg = "";
    String id = "";
    //判断是不是暂停
    boolean doing = true;
    public boolean isTasking = false;//是否养护中
    long startTime = 0;
    FeatureLayer mFeaturelayer;
    ServiceFeatureTable mServiceFeatureTable;
    ArrayList<RoadBean> allRoad = new ArrayList<>();
    RoadBean rBean = new RoadBean();
    public static Geometry geometry;
    Polygon roadbuffer;//道路缓冲区大小，画轨迹用
    RoadBean roadBean;
    @BindView(R.id.llRoad)
    LinearLayout llRoad;
    //底图
    ArcGISMap arcGISMap;
    //是否是结束
    boolean finishTag;
    boolean collectionTag = true;
    LayerList operationalLayers;
    AlertDialog dialog;
    String S_ROAD_ID = "";
    @BindView(R.id.ck1)
    CheckBox ck1;
    @BindView(R.id.ck2)
    CheckBox ck2;
    @BindView(R.id.ck3)
    CheckBox ck3;
    @BindView(R.id.ck4)
    CheckBox ck4;
    @BindView(R.id.ck5)
    CheckBox ck5;
    @BindView(R.id.ck6)
    CheckBox ck6;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.llAll)
    LinearLayout llAll;
    @BindView(R.id.v_legendyh)
    public ViewLegend mLegend;
    EditText et_contentdeal;
    TextView mTvclaphoto;
    EditText et_contentdealstart;
    TextView mTvclaphotostart;
    TextView mTvclaphotodetail;
    TextView mTvcontentdetail;
    public ViewonClickMapDetail mViewonCMD;
    public ViewFourthFragmentSeachdetail mViewFourthFragmentSeachdetail;//搜索查询view
    public ViewYHSeachlist mVXJSeachlist;
    ImageView mIvhistory;
    String S_CURING_MODE, S_TARKS_TYPE;
    long timel;  //录音时间
    int mSoundindex = 0;//录音下标时间
    TImage mTImage;//文件生成返回对象
    ArrayList<TImage> imagesbig = new ArrayList<>();//放大图片集合
    int ps = 0;//浏览和总文件的总差
    boolean isContinue = false;
    //养护距离
    double distance = 0.00;
    public double Dis = 0;
    //养护所有点的集合
    ArrayList<AMapLocation> allAmapLocation = new ArrayList<>();
    DialogSound mDialogSound;
    public AppAttribute mAAtt;
    QueryParameters query;
    int showLayerSum = 0;
    boolean isLoaded = false;
    ArrayList<AdapterBean> abListJS = new ArrayList<>();
    boolean isFristLoaded = false;//判断是否查询到数据
    int mTablesum = 0;
    public ObjectIdsBean mObjectIdsBean;
    String tvSeachStr = "";
    //持续不断的往后台发送当前位置
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (doing) {

                switch (msg.what) {
//上传坐标
                    case 1:
                        break;
//更新时间
                    case 2:
                        if (isTasking) {
                            tvTime.setText(AppTool.getHMSTime(System.currentTimeMillis() - startTime));
                            handler.sendEmptyMessageDelayed(2, 1000);
                        }

                        break;


                }


                LogUtils.e("发送", msg.what + "...............");
            }


        }
    };

    //是不是结束任务的那一次拍照,如果是 把mark去掉
    //  @BindView(R.id.c_details)
    // ControllerDetailsView cDetails;
    private TakePhoto takePhoto;
    private CompressConfig compressConfig;  //压缩参数
    private Uri imageUri;  //图片保存路径
    //记录第一次结束键的时间
    private long finishFirstTime = 0;
    //记录第一次按返回键的时间
    private long firstTime = 0;
    private Button btnUpLoad;
    private int flag;//区分上报和结束养护类型
    private ImageView mIvUpload;
    private boolean isHasData;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mainyhmap;
    }

    int mGalleryPhoto = 0;

    Intent intent;//养护服务

    @Override
    public void init() {
        MapUtil.init(this);
        MapUtil.ui = 1;
        mapv.getGraphicsOverlays().clear();
        intent = new Intent(activity, MyServiceYH.class);

        mLegend.setCallBackMap(this);
        mLegend.setMapdownloadListen(this);
        ImageView mIvtuceng = findViewById(R.id.iv_tuceng);

        mIvtuceng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mLegend.showOrHide("");
            }
        });
        mIvtuceng.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MapUtil.getInstance(MainMapYHActivity.this).showTaskingDialog(MainMapYHActivity.this,"YH");
                return false;
            }
        });
        setReplacepicturesleft(R.mipmap.nav_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isExit();
//                long secondTime = System.currentTimeMillis();
//                if (secondTime - finishFirstTime > 2000) {                                         //如果两次按键时间间隔大于2秒，则不退出
//                    Toast.makeText(activity, "再按一次返回登录界面", Toast.LENGTH_SHORT).show();
//                    finishFirstTime = secondTime;
//                } else {
//                    showMyDialog("请确认是否要退出该界面?");
//                }

            }
        });

        mLoadingWaitView.setVisibility(View.GONE);
        if (!EventBus.getDefault().isRegistered(activity)) {
            EventBus.getDefault().register(activity);
        }
        //mark图标初始化
        endDrawable = (BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.sheshi_mapjs_active);
        setTitle("排水设施移动养护");
        mIvsheshi = findViewById(R.id.iv_sheshi);//设施
        mIvsheshi.setSelected(false);

        mIvgensui = findViewById(R.id.iv_gensui);
        mIvgensui.setSelected(false);// 默认不跟随你
        et_contentdeal = findViewById(R.id.et_contentdeal);//上报照片描述
        et_contentdeal.setFilters(new InputFilter[]{new MaxTextLengthFilter(66)});

        mTvclaphoto = findViewById(R.id.tv_claphoto);
        mTvclaphoto.setText("检查井");

        et_contentdealstart = findViewById(R.id.et_contentdealstart);//开始养护照片描述
        et_contentdealstart.setFilters(new InputFilter[]{new MaxTextLengthFilter(66)});

        mTvclaphotodetail = findViewById(R.id.tv_claphotodetail);
        mTvcontentdetail = findViewById(R.id.tv_contentdetail);
        mTvclaphotostart = findViewById(R.id.tv_claphotostart);
        mTvclaphotostart.setText("检查井");
        mViewonCMD = findViewById(R.id.v_oncmd);
        mViewonCMD.setaListen(new ViewonClickMapDetail.A() {
            @Override
            public void callBack() {
                initStyleSingleTap();
            }
        });
        mViewFourthFragmentSeachdetail = findViewById(R.id.c_xjsd);
        mVXJSeachlist = findViewById(R.id.v_yhseach);
        initVLlTop();
        mIvhistory = findViewById(R.id.iv_history);
        mAAtt = new AppAttribute(MainMapYHActivity.this);
        //获得下载路径
        strMapUrl = MapUtil.getInstance(this).getvtppath();
        //地图下载
//        new MapdownloadUtil(this).setCallBackMap(this).set();
//        new MapdownloadUtil(this).setCallBackMap(this).requestMapbase(mLoadingWaitView);
        markerOverlay = new GraphicsOverlay();
        mAAtt.initB().graphicsOverlayInit();

//        for (int i = 0; i < 10; i++) {
//            mBeanList.add(new TasklistBean());
//        }
//

        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        //设置Adapter
        imageRecyclerViewAdapter = new DeleteImageRecyclerViewAdapter(activity, allimages);
        mRecyclerView.setAdapter(imageRecyclerViewAdapter);


        imageRecyclerViewAdapter.setOnItemClickListener(new DeleteImageRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                if (allimages.get(position).mType == 1) {
                    Intent intent = new Intent(MainMapYHActivity.this, PlayVideoActivity.class);
//               String f= Environment.getExternalStorageDirectory().getAbsolutePath()+"/IMG_0114.MOV";
                    intent.putExtra("file", allimages.get(position).getOriginalPath());
                    startActivity(intent);
                } else if (allimages.get(position).mType == 2) {
                    try {
                        PlaybackFragment playbackFragment =
                                new PlaybackFragment().newInstance(allimages.get(position).getOriginalPath(), timel);

                        FragmentTransaction transaction = MainMapYHActivity.this
                                .getSupportFragmentManager()
                                .beginTransaction();

                        playbackFragment.show(transaction, "dialog_playback");

                    } catch (Exception e) {
//                        Log.e(LOG_TAG, "exception", e);
                    }
                } else {
                    mGalleryPhoto = 0;
                    imagesbig.clear();
                    for (int i = 0; i < allimages.size(); i++) {
                        if (allimages.get(i).mType == 1 || allimages.get(i).mType == 2) {
                            position--;
                            ps++;
                        } else {
                            imagesbig.add(allimages.get(i));
                        }
                    }
                    mViewGalleryPhoto.setData(position, imagesbig);
                }
            }
        });
        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(activity);
        // 设置布局管理器
        mRecyclerViewPhoto.setLayoutManager(layoutManager1);
        layoutManager1.setOrientation(OrientationHelper.HORIZONTAL);
        photoRecyclerViewAdapter = new DeleteImageRecyclerViewAdapter(activity, allPhotoimages);
        mRecyclerViewPhoto.setAdapter(photoRecyclerViewAdapter);
        //补录
        photoRecyclerViewAdapter.setOnItemClickListener(new DeleteImageRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (allPhotoimages.get(position).mType == 1) {
                    Intent intent = new Intent(MainMapYHActivity.this, PlayVideoActivity.class);
//               String f= Environment.getExternalStorageDirectory().getAbsolutePath()+"/IMG_0114.MOV";
                    intent.putExtra("file", allPhotoimages.get(position).getOriginalPath());
                    startActivity(intent);
                } else if (allPhotoimages.get(position).mType == 2) {
                    try {
                        PlaybackFragment playbackFragment =
                                new PlaybackFragment().newInstance(allPhotoimages.get(position).getOriginalPath(), timel);

                        FragmentTransaction transaction = MainMapYHActivity.this
                                .getSupportFragmentManager()
                                .beginTransaction();

                        playbackFragment.show(transaction, "dialog_playback");

                    } catch (Exception e) {
//                        Log.e(LOG_TAG, "exception", e);
                    }
                } else {

                    mGalleryPhoto = 1;
                    imagesbig.clear();
                    for (int i = 0; i < allPhotoimages.size(); i++) {
                        if (allPhotoimages.get(i).mType == 1 || allPhotoimages.get(i).mType == 2) {
                            position--;
                            ps++;
                        } else {
                            imagesbig.add(allPhotoimages.get(i));
                        }
                    }
                    mViewGalleryPhoto.setData(position, imagesbig);
                }
            }
        });
        ivTrackcamera.setOnLongClickListener(this);

        mIvUpload = findViewById(R.id.settitle_img);
        mIvUpload.setVisibility(View.VISIBLE);
        mIvUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHasData) {
                    showUploadDialog();
                    presenter.uploadFile(MainMapYHActivity.this);
                } else {
                    ToastUtils.showToast("本地暂无数据！");
                }
//                DBManager.getFailYHDao(MainMapYHActivity.this).deleteAll();
            }
        });

        //判断是否有本地数据缓存
        List<FailYHResult> xjResults = DBManager.getFailYHDao(this).queryBuilder().where(FailYHResultDao.Properties.IsSave.eq(false)).list();

        if (xjResults != null && !xjResults.isEmpty()) {
            Glide.with(this).asGif().load(R.drawable.btn_update_no).into(mIvUpload);
            isHasData = true;
        } else {
            mIvUpload.setImageResource(R.mipmap.btn_update_no);
            isHasData = false;

        }
    }

    private void showUploadDialog() {
        List<FailYHResult> xjResults = DBManager.getFailYHDao(this).queryBuilder().where(FailYHResultDao.Properties.IsSave.eq(false)).list();

        View view = View.inflate(activity, R.layout.dialog_tips, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                .setTitle("提示").setView(view).setCancelable(false);
        final AlertDialog dialog = builder.create();
        TextView tvSure = view.findViewById(R.id.tv_ok);
        TextView tv_no = view.findViewById(R.id.tv_no);
        TextView tv_content = view.findViewById(R.id.tv_content);
        tv_content.setText("共有：" + xjResults.size() + "条本地数据待上传，网络良好请及时上传！");
        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.uploadFile(MainMapYHActivity.this);
            }
        });
        dialog.setCancelable(true);
        dialog.show();

    }

    public void setSearchEdit(String str) {
        mEtseach.setText(str);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除绑定s
        EventBus.getDefault().unregister(this);
        try {
            unregisterReceiver(broadcast);
        } catch (Exception e) {

        }
        Graphicsclear(mLocationOverlay);
        handler.removeMessages(2);
        handler.removeMessages(1);

    }


    boolean isFristLoca = true;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventBusCar(AMapLocation aMapLocation) {
        if (!MapUtil.isLegalLL(aMapLocation)) {
            return;
        }
        setLocicon();
        if (AppTool.isServiceRunning(MainMapYHActivity.this, MyServiceYH.class.getName())) {
            return;
        }
        this.aMapLocation = aMapLocation;
        if (mainMapImageLayer == null) {
            return;
        }
        if (isFristLoca) {
            isFristLoca = !isFristLoca;
            mapv.setViewpointCenterAsync(QPSWApplication.Locpoint, 10000);
        }
    }

    public void setLocicon() {
        if (mIvgensui.isSelected()) {//跟随
            MapUtil.getInstance(this).setGensui(aMapLocation, mapv);
        }
        if (mLocationOverlay == null) {
            mLocationOverlay = new GraphicsOverlay();

        }
        MapUtil.getInstance(this).setLocicon(aMapLocation, mapv, mLocationOverlay, "");//定位图标
    }

    public ViewGalleryPhoto mViewGalleryPhoto;

    //进界面的第一次数据的初始化
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
        mViewGalleryPhoto = findViewById(R.id.v_galleryphoto);
        mViewGalleryPhoto.setNotifyDataSetChangedListen(this);

    }

    @Override
    protected MainMapYHRequestPresenter createPresenter() {
        return new MainMapYHRequestPresenter() {
        };
    }

    //获得照片的输出保存Uri
    private Uri getImageCropUri() {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        return Uri.fromFile(file);
    }

    IatUtil mIatUtil;
    public List<ClasBean.ValueBean> mClasPhotoList = new ArrayList<>();

    //全局普通点击事件的
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.iv_shanghai, R.id.tv_suerDetails, R.id.iv_gensui, R.id.tv_distance, R.id.tv_time, R.id.ll_stop, R.id.iv_finish, R.id.ll_taskcancel, R.id.ck1, R.id.ck2, R.id.ck3, R.id.ck4, R.id.ck5, R.id.ck6, R.id.btnChange, R.id.tv_cancel_photo, R.id.tv_suer_photo, R.id.tv_plus, R.id.tv_cancel, R.id.tv_suer, R.id.llReport, R.id.tv_minus, R.id.iv_location, R.id.iv_trackstart, R.id.iv_trackcamera, R.id.iv_doing, R.id.iv_video, R.id.iv_speech, R.id.iv_photo, R.id.iv_video1, R.id.iv_speech1, R.id.iv_photo1, R.id.tv_claphoto, R.id.tv_claphotostart, R.id.iv_sheshi, R.id.iv_topback, R.id.iv_topspeak, R.id.iv_topclean, R.id.tv_topseach, R.id.et_topseach, R.id.iv_history, R.id.btn_activity_lift})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_activity_lift:
                isExit();
                break;
            case R.id.iv_sheshi:
                mIvsheshi.setSelected(!mIvsheshi.isSelected());
                if (mIvsheshi.isSelected()) {
                    mLltop.setVisibility(View.VISIBLE);
                    ivTrackstart.setVisibility(View.GONE);
                    llAll.setVisibility(View.GONE);
                } else {
                    mLltop.setVisibility(View.GONE);
                    mViewFourthFragmentSeachdetail.setVisibility(View.GONE);
                    mViewonCMD.setVisibility(View.GONE);
                    initStyleSingleTap();
                    if (isTasking) {
                        llAll.setVisibility(View.VISIBLE);
                    } else {
                        ivTrackstart.setVisibility(View.VISIBLE);
                    }

                }

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
            case R.id.tv_claphoto:
            case R.id.tv_claphotostart:
                if (mClasPhotoList.size() == 0) {
                    mClasPhotoList = AppUtils.getClaphotolist();
                }
                setPopSelect(mClasPhotoList);
                //ToastUtils.showToast("距离1");
                break;
            case R.id.iv_history:
                Intent iHistory = new Intent(MainMapYHActivity.this, WebViewHistoryYHActivity.class);
                iHistory.putExtra("url", String.format(AppConfig.YHHistory, SPUtil.getInstance(this).getStringValue(SPUtil.USERNO)));
                iHistory.putExtra("isTasking", isTasking);
                startActivityForResult(iHistory, requestCountinuTask);
                //ToastUtils.showToast("距离1");
                break;
            case R.id.tv_distance:

                //ToastUtils.showToast("距离1");
                break;
            case R.id.tv_time:
                //ToastUtils.showToast("距离2");
                break;
            case R.id.ll_stop:
                //ToastUtils.showToast("距离3");
                mIvstop.setSelected(!mIvstop.isSelected());
                if (!doing) {
                    handler.sendEmptyMessage(1);

                    handler.sendEmptyMessage(2);

                }
                doing = !doing;
                break;
            case R.id.iv_finish://结束养护
                flag = 3;
                showMyDialog("确认结束本次养护吗?");
                break;
            case R.id.ll_taskcancel:
                showMyDialog("确认取消本次养护吗?");
                break;
            case R.id.iv_shanghai:
                MapUtil.getInstance(this).setshanghai(mapv);
                break;
            case R.id.tv_plus:
                mapv.setViewpointScaleAsync(mapv.getMapScale() * 0.5);
                break;
            case R.id.tv_minus:

                mapv.setViewpointScaleAsync(mapv.getMapScale() / 0.5);
                break;

            case R.id.iv_gensui:
                mIvgensui.setSelected(!mIvgensui.isSelected());
                break;
            case R.id.iv_location:
                if (QPSWApplication.Locpoint == null) {
                    ToastUtils.showToast("定位失败");
                    return;
                }
                mapv.setViewpointCenterAsync(QPSWApplication.Locpoint, 10000);
                break;

            case R.id.iv_trackstart://开始养护
                flag = 1;
                if (mainMapImageLayer == null) {
                    ToastUtils.showToast("底图加载失败");
                    return;
                }

                //获取养护路段的数据
                searchForState();
                break;
            case R.id.iv_trackcamera://上报
                flag = 2;
                llPhoto.setVisibility(View.VISIBLE);
                break;

            case R.id.ck1:

                ck2.setChecked(false);
                break;
            case R.id.ck2:
                ck1.setChecked(false);

                break;
            case R.id.ck3:

                ck4.setChecked(false);
                ck5.setChecked(false);
                ck6.setChecked(false);

                break;
            case R.id.ck4:

                ck3.setChecked(false);
                ck5.setChecked(false);
                ck6.setChecked(false);

                break;
            case R.id.ck5:

                ck4.setChecked(false);
                ck3.setChecked(false);
                ck6.setChecked(false);

                break;
            case R.id.ck6:

                ck4.setChecked(false);
                ck5.setChecked(false);
                ck3.setChecked(false);

                break;
            case R.id.iv_doing:

                if (collectionTag) {
                    llRoad.setVisibility(View.VISIBLE);
                    operationalLayers.get(1).setVisible(true);
                } else {
                    operationalLayers.get(1).setVisible(false);
                    llRoad.setVisibility(View.GONE);
                }
                collectionTag = !collectionTag;
                break;

            //开始养护布局上的取消
            case R.id.tv_cancel:
                llReport.setVisibility(View.GONE);
                break;
            //开始养护布局上的确定
            case R.id.tv_suer:

                if (AppTool.isNull(S_ROAD_ID) || ck1.isChecked() == ck2.isChecked() || ck3.isChecked() == ck4.isChecked() == ck5.isChecked() == ck6.isChecked() || allimages.size() == 0) {
                    ToastUtils.showToast("请资料补充完整");
                } else {

                    if (!hasImage(1)){
                        ToastUtils.showToast("请至少添加一张图片！");
                        return;
                    }

                    if (ck1.isChecked()) {

                        S_TARKS_TYPE = "W1007100001";

                    }
                    if (ck2.isChecked()) {

                        S_TARKS_TYPE = "W1007100002";

                    }


                    if (ck3.isChecked()) {

                        S_CURING_MODE = "W1008300001";

                    }
                    if (ck4.isChecked()) {

                        S_CURING_MODE = "W1008300002";

                    }
                    if (ck5.isChecked()) {

                        S_CURING_MODE = "W1008300003";

                    }
                    if (ck6.isChecked()) {

                        S_CURING_MODE = "W1008300004";

                    }


                    map = new HashMap<>();


                    map.put("S_CURING_MODE", S_CURING_MODE);
                    map.put("S_TARKS_TYPE", S_TARKS_TYPE);


                    //路段选择获取的任务编号  先写死
                    map.put("S_TASK_ID", "123");
                    //时间+养护人员
                    map.put("S_RECODE_ID", System.currentTimeMillis() + "_" + "YH" + "_" + SPUtils.get("user", ""));
                    //路段ID
                    map.put("S_ROAD_ID", S_ROAD_ID);
                    //养护人员
                    map.put("T_CURING_MAN", SPUtils.get("user", ""));
                    //养护类型
                    //  map.put("S_TARKS_TYPE", "W1007100001");
                    //任务开始时间
                    map.put("T_STARTM", AppTool.getCurrentDate(AppTool.FORMAT_YMDHMS));
                    LogUtils.e("数据zzzzzzz", map.toString());
                    presenter.getRelyid(4, map);
                }

                break;
            case R.id.llReport:

                break;
            //补录照片的布局的取消
            case R.id.tv_cancel_photo:
                llPhoto.setVisibility(View.GONE);
                break;
            //补录照片的布局的确定


            case R.id.tv_suer_photo:
                if (AppTool.isNull(SPUtil.getInstance(this).getStringValue(SPUtil.YHID))) {
                    ToastUtils.showToast("养护记录id为空");
                    return;
                }
                if (aMapLocation == null) {
                    ToastUtils.showToast("aMapLocation=" + aMapLocation);
                    return;
                }
                if (allPhotoimages.size() > 0) {
                    //把高德经纬度转换为84
                    Gps   g = com.wavenet.ding.qpps.utils.PositionUtil.gcj_To_Gps84(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                    Point p=new Point(g.getWgLon(),g.getWgLat(), SpatialReferences.getWgs84());
                    if (!GeometryEngine.intersects(roadbuffer,p)&&!GeometryEngine.crosses(roadbuffer,p)&&!GeometryEngine.touches(roadbuffer,p)){
                        MapUtil.getInstance(this).showTipsDialog(this,"当前坐标位置数据异常，不在养护区域内，请稍后重试");
                        return;
                    }
                    Gps g1 = PositionUtil.gcj_To_Gps84(MyServiceYH.aMapLocation.getLatitude(), aMapLocation.getLongitude());

                    map.put("x", g1.getWgLat());
                    map.put("y", g1.getWgLon());
                    map.put("relyid", SPUtil.getInstance(this).getStringValue(SPUtil.YHID));
                    ArrayList<String> arrayList1 = new ArrayList<>();
                    ArrayList<String> imgList = new ArrayList<>();
                    String audioPath = null;
                    String videoPath = null;

                    if (!hasImage(2)){
                        ToastUtils.showToast("请至少添加一张图片！");
                        return;
                    }

                    for (int i = 0; i < allPhotoimages.size(); i++) {

                        if (allPhotoimages.get(i).mType == 2) {//语音
                            File f = new File(allPhotoimages.get(i).getCompressPath());
                            File mm = AppTool.getAudioFolder(this, "/MyRecording" + System.currentTimeMillis() + "@" + timel + ".mp4");
                            if (f.renameTo(mm)) {
                                arrayList1.add(mm.getAbsolutePath());
                                audioPath = mm.getAbsolutePath();
                            }
//            arrayList.add(mSoundrul);
                        } else if (allPhotoimages.get(i).mType == 1) {//视频
                            videoPath = allPhotoimages.get(i).getCompressPath();
                            arrayList1.add(allPhotoimages.get(i).getCompressPath());
                        } else {
                            arrayList1.add(allPhotoimages.get(i).getCompressPath());
                            imgList.add(allPhotoimages.get(i).getCompressPath());
                        }
                    }

                    map.put("yxfa", System.currentTimeMillis());
                    map.put("S_TYPE", mTvclaphoto.getText().toString());
                    map.put("S_DESC", et_contentdeal.getText().toString());
                    if (flag == 2) {//上报
                        presenter.reporFile(2, map, arrayList1, audioPath, videoPath, imgList);
                    } else if (flag == 3) {//结束
                        presenter.reporFile(3, map, arrayList1, audioPath, videoPath, imgList);

                    }

                } else {
                    ToastUtils.showToast("请选择图片");
                }

                //控制地图下面背景大小
            case R.id.btnChange:

                break;

            case R.id.tv_suerDetails:
                llDetails.setVisibility(View.GONE);
                break;
            case R.id.iv_video:
                if (allimages.size() == 9) {
                    ToastUtils.showToast("只能上传9个文件");
                    return;
                }
                Intent i = new Intent(MainMapYHActivity.this, com.wavenet.ding.qpps.activity.TakePhotoActivity.class);
                startActivityForResult(i, 2);
                break;
            case R.id.iv_photo:
                if (allimages.size() == 9) {
                    ToastUtils.showToast("只能上传9个文件");
                    return;
                }
                tag = "1";
                ActionSheet.showSheet(MainMapYHActivity.this, MainMapYHActivity.this, null);
                break;
            case R.id.iv_speech:
                if (allimages.size() == 9) {
                    ToastUtils.showToast("只能上传9个文件");
                    return;
                }
                if (mDialogSound == null) {
                    mDialogSound = new DialogSound(this);
                }
                mDialogSound.setSListener(new DialogSound.SListener() {
                    @Override
                    public void sListener(String soundurl, long time) {
                        timel = time;
                        mTImage = new TImage(soundurl, 2);
                        boolean isSound = false;
                        boolean isvideo = false;
                        mSoundindex = 0;
                        if (allimages.size() == 0) {
                            allimages.add(0, mTImage);
                            imageRecyclerViewAdapter.notifyDataSetChanged();
                            return;
                        }
                        for (int j = 0; j < allimages.size(); j++) {
                            if (allimages.get(j).mType == 2) {
                                isSound = true;
                                mSoundindex = j;
                            }
                            if (allimages.get(j).mType == 1) {
                                isvideo = true;
                            }
                        }
                        if (isSound) {
                            MapUtil.getInstance(MainMapYHActivity.this).showIsReplaceFileDialog(MainMapYHActivity.this, MainMapYHActivity.this, GoBackInteDef.setBack(GoBackInteDef.REPLACESOUND), "只能上传一个语音文件，是否要替换已有的语音?");
                        } else {
                            if (isvideo) {
                                allimages.add(1, mTImage);
                            } else {
                                allimages.add(0, mTImage);
                            }
                            imageRecyclerViewAdapter.notifyDataSetChanged();
                        }

                    }
                });
                mDialogSound.show();
                break;
            case R.id.iv_video1:
                if (allPhotoimages.size() == 9) {
                    ToastUtils.showToast("只能上传9个文件");
                    return;
                }
                Intent i1 = new Intent(MainMapYHActivity.this, com.wavenet.ding.qpps.activity.TakePhotoActivity.class);
                startActivityForResult(i1, 2);
                break;
            case R.id.iv_photo1:
                if (allPhotoimages.size() == 9) {
                    ToastUtils.showToast("只能上传9个文件");
                    return;
                }
                tag = "2";
                ActionSheetSuper.showSheet(MainMapYHActivity.this, flag == 2, MainMapYHActivity.this, null);
                break;
            case R.id.iv_speech1:
                if (allPhotoimages.size() == 9) {
                    ToastUtils.showToast("只能上传9个文件");
                    return;
                }
                if (mDialogSound == null) {
                    mDialogSound = new DialogSound(this);
                }
                mDialogSound.setSListener(new DialogSound.SListener() {
                    @Override
                    public void sListener(String soundurl, long time) {
                        timel = time;
                        mTImage = new TImage(soundurl, 2);
                        boolean isSound = false;
                        boolean isvideo = false;
                        mSoundindex = 0;
                        if (allPhotoimages.size() == 0) {
                            allPhotoimages.add(0, mTImage);
                            photoRecyclerViewAdapter.notifyDataSetChanged();
                            return;
                        }
                        for (int j = 0; j < allPhotoimages.size(); j++) {
                            if (allPhotoimages.get(j).mType == 2) {
                                isSound = true;
                                mSoundindex = j;
                            }
                            if (allPhotoimages.get(j).mType == 1) {
                                isvideo = true;
                            }
                        }
                        if (isSound) {
                            MapUtil.getInstance(MainMapYHActivity.this).showIsReplaceFileDialog(MainMapYHActivity.this, MainMapYHActivity.this, GoBackInteDef.setBack(GoBackInteDef.REPLACESOUND), "只能上传一个语音文件，是否要替换已有的语音?");
                        } else {
                            if (isvideo) {
                                allPhotoimages.add(1, mTImage);
                            } else {
                                allPhotoimages.add(0, mTImage);
                            }
                            photoRecyclerViewAdapter.notifyDataSetChanged();
                        }

                    }
                });
                mDialogSound.show();
                break;

        }
    }

    //判断是否添加图片，type:1 开始上报 2上报和结束上报
    private boolean hasImage(int type){
        if (type == 1) {
            for (TImage image : allimages) {
                if (image.mType == 0) {
                    return true;
                }
            }
        } else if (type == 2){
            for (TImage image : allPhotoimages) {
                if (image.mType == 0){
                    return true;
                }
            }
        }
        return false;
    }

    public void Graphicsclear(GraphicsOverlay gOverlay) {
        if (gOverlay != null && gOverlay.getGraphics() != null) {
            gOverlay.getGraphics().clear();
        }
    }

    private void addMapService() {


        String mainArcGISMapImageLayerURL =
                AppConfig.BeasUrl+"2084/arcgis/rest/services/QPRoadPipe0818/MapServer";
//        ServiceFeatureTable mServiceFeatureTable1 = new ServiceFeatureTable(mainArcGISMapImageLayerURL);
//        FeatureLayer mFeaturelayer1 = new FeatureLayer(mServiceFeatureTable1);
//        mFeaturelayer1.setOpacity(0.8f);
//        mArcGISMapVector.getOperationalLayers().add(mFeaturelayer1);
        ArcGISMapImageLayer mainMapImageLayer = new ArcGISMapImageLayer(mainArcGISMapImageLayerURL);
        mainMapImageLayer.setOpacity(0.5f);
        mArcGISMapVector.getOperationalLayers().add(mainMapImageLayer);
//        operationalLayers = mArcGISMapVector.getOperationalLayers();

//                SublayerList sublayers = mainMapImageLayer.getSublayers();

//        mapv.setMap(mArcGISMapVector);
//        operationalLayers.get(0).setVisible(false);
//        operationalLayers.get(1).setVisible(false);

    }

    //自定义仿IOS布局的点击回调
    @Override
    public void onClick(int whichButton) {
        switch (whichButton) {
            case ActionSheet.CHOOSE_PICTURE:
                //相册
                ////获取TakePhoto实例
                takePhoto = getTakePhoto();
                //设置裁剪参数
                // cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(false).create();
                //设置压缩参数
                compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(800).create();
                takePhoto.onEnableCompress(compressConfig, true);  //设置为需要压缩
                takePhoto.onPickMultiple(9);
                break;
            case ActionSheet.TAKE_PICTURE:
                ////获取TakePhoto实例
                takePhoto = getTakePhoto();
                //设置裁剪参数
                // cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(false).create();
                //设置压缩参数
                compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(800).create();
                takePhoto.onEnableCompress(compressConfig, true);  //设置为需要压缩
                imageUri = getImageCropUri();
                takePhoto.onPickFromCapture(imageUri);
                break;
            case ActionSheet.CANCEL:

                break;

        }
    }

    //拍照成功回调
    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        //上报的拍照回调
        if (tag.equals("1")) {
            if (allimages.size() + result.getImages().size() < 10) {
                allimages.addAll(result.getImages());
                imageRecyclerViewAdapter.notifyDataSetChanged();
                tvPicNumReport.setText(allimages.size() + "/" + "9图片");
            } else {
                ToastUtils.showToast("附件选择不能超过9个");
            }

        }
        //补录的上报回调
        if (tag.equals("2")) {
            if (allPhotoimages.size() + result.getImages().size() < 10) {
                allPhotoimages.addAll(result.getImages());
                photoRecyclerViewAdapter.notifyDataSetChanged();
            } else {
                ToastUtils.showToast("附件选择不能超过9个");
            }
        }
    }

    //拍照失败的回调
    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
        ToastUtils.showToast("照片获取失败");
    }

    //拍照取消的回调
    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    //养护上报的长按事件
    @Override
    public boolean onLongClick(View v) {


        return true;
    }

    public void showMyDialog(final String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //     builder.setTitle("添加故障描述！");
        View view = View.inflate(activity, R.layout.dialog_tips, null);
        builder.setView(view);
        builder.setCancelable(false);
        dialog = builder.create();
        TextView tvSure = view.findViewById(R.id.tv_ok);
        TextView tv_no = view.findViewById(R.id.tv_no);
        TextView tv_content = view.findViewById(R.id.tv_content);
        tv_content.setText(content);
        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (content.contains("退出")) {
                    finish();
                }
                if (content.contains("结束")) {

                    finishTag = true;
                    llPhoto.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                }

                if (content.contains("取消")) {
                    Map<String, Object> map = new HashMap<>();

                    map.put("T_ENDTM", AppTool.getCurrentDate(AppTool.FORMAT_YMDHMS));
                    map.put("S_DEL", "0");
                    presenter.userCancelTask(888, SPUtil.getInstance(MainMapYHActivity.this).getStringValue(SPUtil.YHID), map);
                }

            }
        });
        dialog.setCancelable(true);
        dialog.show();
    }

    //给地图添加mark
    private void addGraphicsOverlay() {
        final PictureMarkerSymbol pinDestinationSymbol;
        try {
            {
                pinDestinationSymbol = PictureMarkerSymbol.createAsync(endDrawable).get();
                pinDestinationSymbol.loadAsync();
                pinDestinationSymbol.addDoneLoadingListener(new Runnable() {
                    @Override
                    public void run() {
                        //add a new graphic as end point
                        Map<String, Object> m = new HashMap<>();
                        m.put("id", id);
//                        Point p=    new Point(121.08344,31.144881,mapv.getSpatialReference());
//                        Graphic graphic = new Graphic(p, m, pinDestinationSymbol);
                        Graphic graphic = new Graphic(point, m, pinDestinationSymbol);
                        markerOverlay.getGraphics().add(graphic);

                    }
                });
                pinDestinationSymbol.setOffsetY(20);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        if (!mapv.getGraphicsOverlays().contains(markerOverlay)) {
            mapv.getGraphicsOverlays().add(markerOverlay);
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
    public void resultFailure(int what, String result) {


    }

    @Override
    public void resultFailure(int what, String result, Map<String, Object> map, String audioPath,
                              String videoPath, ArrayList<String> imgPaths) {
        double x = (double) map.get("x");
        double y = (double) map.get("y");
        String relyid = (String) map.get("relyid");
        long yxfa = (long) map.get("yxfa");
        String sType = (String) map.get("S_TYPE");
        String sDesc = (String) map.get("S_DESC");


        if (what == 1) {//开始养护时上传附件失败处理
            showTipDialog(1);
            saveParams(1 + "", x, y, relyid, yxfa + "", sType, sDesc, imgPaths, videoPath, audioPath);
        } else if (what == 2) {//上报或者结束养护上传附件失败处理
            showTipDialog(2);
            saveParams(2 + "", x, y, relyid, yxfa + "", sType, sDesc, imgPaths, videoPath, audioPath);
        } else if (what == 3) {
            showTipDialog(3);
            saveParams(3 + "", x, y, relyid, yxfa + "", sType, sDesc, imgPaths, videoPath, audioPath);
        }
    }

    //保存参数到本地数据库
    public void saveParams(String type, double x, double y, String relyid, String yxfa, String sType, String sDesc,
                           ArrayList<String> images, String videoPath, String audioPath) {
        FailYHResult result = new FailYHResult();
        result.setX(x + "");
        result.setY(y + "");
        result.setYxfa(yxfa);
        result.setSType(sType);
        result.setSDesc(sDesc);
        result.setAudioUrl(audioPath);
        result.setRelyid(relyid);
        result.setYhId(type);
        result.setVideoUrl(videoPath);
        DBManager.getFailYHDao(this).insert(result);

        for (String url : images) {
            FilePath filePath = new FilePath(null, type, url);
            DBManager.getFilePathDao(this).insert(filePath);
        }

        //本地数据保存成功后，上传图标变为gif
        Glide.with(this).asGif().load(R.drawable.btn_update_no).into(mIvUpload);
        isHasData = true;
    }

    @Override
    public void resultSuccess(int what, ResponseBody result) {

        switch (what) {
            case 1://开始养护上传附件

                try {
                    msg = result.string();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                if (msg.contains("failed")) {
//                    ToastUtils.showToast("提交失败,请重试");
                    showTipDialog(1);
                } else {
                AppAttribute.G.initState();
                    id = GsonUtils.getObject(msg, PicBean.class).app.get(0).metadata.yxfa;
                    handler.sendEmptyMessage(2);
                    ToastUtils.showToast("提交成功");
                    llReport.setVisibility(View.GONE);
                    allimages.clear();
                    imageRecyclerViewAdapter.notifyDataSetChanged();
                    ivTrackstart.setVisibility(View.GONE);
                    isTasking = true;
                    llAll.setVisibility(View.VISIBLE);
                    startTime = System.currentTimeMillis();
                    start();
                    llPhoto.setVisibility(View.GONE);
                    mTvclaphotostart.setText("检查井");
                    et_contentdealstart.setText("");
                    allPhotoimages.clear();
                    photoRecyclerViewAdapter.notifyDataSetChanged();
//                    Gps gpsE = PositionUtil.gcj_To_Gps84(MyServiceYH.aMapLocation.getLatitude(), aMapLocation.getLongitude());
                    Gps gpsE = PositionUtil.gcj_To_Gps84(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                    double lon = gpsE.getWgLon();
                    double lat = gpsE.getWgLat();
                    point = new Point(lon, lat, SpatialReference.create(4326));
                    addGraphicsOverlay();
//                    mAAtt.initB().roadgraphicsOverlay.getGraphics().add(mAAtt.initB().getGraphicroad(rBean.geometry));

                }
                break;
            case 2://上报养护上传附件
            case 3://结束养护上传附件
                try {
                    msg = result.string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (msg.contains("failed")) {
//                    ToastUtils.showToast("提交失败,请重试");
                    showTipDialog(2);
                } else {
                    id = GsonUtils.getObject(msg, PicBean.class).app.get(0).metadata.yxfa;
                    mTvclaphoto.setText("检查井");
                    et_contentdeal.setText("");
                    allPhotoimages.clear();
                    photoRecyclerViewAdapter.notifyDataSetChanged();

//
                    Gps gpsE = PositionUtil.gcj_To_Gps84(MyServiceYH.aMapLocation.getLatitude(), aMapLocation.getLongitude());
                    GraphicsOverlay mStarOverlay = new GraphicsOverlay();
//                        Drawable drawable = activity.getResources().getDrawable(R.mipmap.btn_location);
//                        PictureMarkerSymbol pictureMarkerSymbol = new PictureMarkerSymbol((BitmapDrawable) drawable);
                    double lon = gpsE.getWgLon();
                    double lat = gpsE.getWgLat();
                    point = new Point(lon, lat, SpatialReference.create(4326));
                    addGraphicsOverlay();


                    //   MapUtil.getInstance(this).setLocico(aMapLocation,mapv);


                    if (finishTag) {//如果是结束按钮
                        mLoadingWaitView.loadView();
                        Map<String, Object> map = new HashMap<>();

                        map.put("T_ENDTM",AppTool.getCurrentDate(AppTool.FORMAT_YMDHMS));
                        map.put("S_TASK_STATUS", "W1007800001");


                        if (tvDistance.getText().toString().contains("Km")
                                ) {
                            double km = Double.parseDouble(tvDistance.getText().toString().replace("Km", ""));

                            map.put("N_MILEAGE", km * 1000);


                        } else {

                            map.put("N_MILEAGE", Double.parseDouble(tvDistance.getText().toString().replace("m", "")));

                        }
//                        map.put("N_MILEAGE", 10682.46);33
                        show();
                        presenter.userFinishTask(66, SPUtil.getInstance(MainMapYHActivity.this).getStringValue(SPUtil.YHID), map);

                    } else {
                        llPhoto.setVisibility(View.GONE);
                        ToastUtils.showToast("提交成功");
                    }
                }
                break;
            case 10000://补报数据成功
                mIvUpload.setImageResource(R.mipmap.btn_update_no);
                isHasData = false;
                break;
        }
    }
    public void endYH(){

        relyid = "";
        ToastUtils.showToast("提交成功");
        mLoadingWaitView.success();
        dialog.dismiss();
        ivTrackstart.setVisibility(View.VISIBLE);
        llPhoto.setVisibility(View.GONE);
        llAll.setVisibility(View.GONE);
        isTasking = false;
        S_ROAD_ID = "";
        mAAtt.initB().graphicsOverlayClean(mAAtt.initB().roadgraphicsOverlay);
        if (markerOverlay != null && markerOverlay.getGraphics() != null) {
            markerOverlay.getGraphics().clear();
        }
        doing = false;
        allAmapLocation.clear();
        tvDistance.setText("00.00m");
        distance = 0.0;
        finishTag = false;
        mAAtt.getASingle().cleanR(mapv);
        stopService(intent);
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
                PicBean picBean = GsonUtils.getObject(msg, PicBean.class);
                if (msg != null && picBean != null) {
                    id = GsonUtils.getObject(msg, PicBean.class).app.get(0).metadata.yxfa;
                }
                switch (type) {
                    case 1:
                        handler.sendEmptyMessage(2);
                        llReport.setVisibility(View.GONE);
                        allimages.clear();
                        imageRecyclerViewAdapter.notifyDataSetChanged();
                        ivTrackstart.setVisibility(View.GONE);
                        isTasking = true;
                        llAll.setVisibility(View.VISIBLE);
                        startTime = System.currentTimeMillis();
                        start();
                        llPhoto.setVisibility(View.GONE);
                        mTvclaphotostart.setText("检查井");
                        et_contentdealstart.setText("");
                        allPhotoimages.clear();
                        photoRecyclerViewAdapter.notifyDataSetChanged();
                        Gps gpsE = PositionUtil.gcj_To_Gps84(MyServiceYH.aMapLocation.getLatitude(), aMapLocation.getLongitude());
                        double lon = gpsE.getWgLon();
                        double lat = gpsE.getWgLat();
                        point = new Point(lon, lat, SpatialReference.create(4326));
                        addGraphicsOverlay();
                        break;
                    case 2:
                    case 3:
                        mTvclaphoto.setText("检查井");
                        et_contentdeal.setText("");
                        allPhotoimages.clear();
                        photoRecyclerViewAdapter.notifyDataSetChanged();

                        Gps gpsE1 = PositionUtil.gcj_To_Gps84(MyServiceYH.aMapLocation.getLatitude(), aMapLocation.getLongitude());
                        GraphicsOverlay mStarOverlay = new GraphicsOverlay();
//                        Drawable drawable = activity.getResources().getDrawable(R.mipmap.btn_location);
//                        PictureMarkerSymbol pictureMarkerSymbol = new PictureMarkerSymbol((BitmapDrawable) drawable);
                        double lon1 = gpsE1.getWgLon();
                        double lat1 = gpsE1.getWgLat();
                        point = new Point(lon1, lat1, SpatialReference.create(4326));
                        addGraphicsOverlay();

                        if (finishTag) {//如果是结束按钮
                            mLoadingWaitView.loadView();
                            Map<String, Object> map = new HashMap<>();

                            map.put("T_ENDTM", AppTool.getCurrentDate(AppTool.FORMAT_YMDHMS));
                            map.put("S_TASK_STATUS", "W1007800001");


                            if (tvDistance.getText().toString().contains("Km")
                                    ) {
                                double km = Double.parseDouble(tvDistance.getText().toString().replace("Km", ""));

                                map.put("N_MILEAGE", km * 1000);


                            } else {

                                map.put("N_MILEAGE", Double.parseDouble(tvDistance.getText().toString().replace("m", "")));

                            }
//                        map.put("N_MILEAGE", 10682.46);33
                            show();
                            presenter.userFinishTask(66, SPUtil.getInstance(MainMapYHActivity.this).getStringValue(SPUtil.YHID), map);

                        } else {
                            llPhoto.setVisibility(View.GONE);
                        }
                        break;
                }
            }
        });
    }

    void start() {
        isContinue = false;
        distance = 0.0;
        Gps mGps = PositionUtil.gcj_To_Gps84(aMapLocation.getLatitude(), aMapLocation.getLongitude());
        intent.putExtra("S_MAN_ID", SPUtils.get("user", ""));
        intent.putExtra("N_Y", mGps.getWgLat());
        intent.putExtra("N_X", mGps.getWgLon());
        intent.putExtra("Dis", 0.00);
        geometry = rBean.geometry;
        roadbuffer= GeometryEngine.buffer(MainMapYHActivity.geometry, MapUtil.roadbuffer);
        mAAtt.initB().roadgraphicsOverlay.getGraphics().add(mAAtt.initB().getGraphicroad(geometry));
        if (AppTool.isServiceRunning(this, MyServiceYH.class.getName())) ;
        {
            mAAtt.getASingle().cleanR(mapv);
            stopService(intent);
        }
        startService(intent);
        allAmapLocation.clear();
    }

    private void upXY(double NMILEAGE) {
        distance = NMILEAGE;
        if (distance > 100) {
            tvDistance.setText(
                    AppTool.getTwoDecimal(distance / 1000) + "Km");
        } else {
            tvDistance.setText(AppTool.getTwoDecimal(distance) + "m");
        }
    }

    @Override
    public void resultStringSuccess(int what, String result) {

        switch (what) {
            case 3:
//                handler.sendEmptyMessageDelayed(1, 10000);
                break;
            case 5:
                List<ListPicUrlBean.AppBean> app = GsonUtils.getObject(result, ListPicUrlBean.class).app;
                if (app == null || app.size() == 0)
                    return;
                mTvclaphotodetail.setText(app.get(0).metadata.S_TYPE);
                mTvcontentdetail.setText(app.get(0).metadata.S_DESC);
                final ArrayList<String> imaDatas = new ArrayList<>();
                final ArrayList<String> imaDatasphoto = new ArrayList<>();
                imaDatas.addAll(ListPicUrlBean.AppBean.setFileUrls(app));
                for (int i = 0; i < imaDatas.size(); i++) {
                    if (!imaDatas.get(i).endsWith("视频") && !imaDatas.get(i).endsWith("语音")) {
                        imaDatasphoto.add(imaDatas.get(i));
                    }
                }
//                for (int i = 0; i < app.size(); i++) {
//
//                    imaDatas.add(SPUtils.get("url2", "http://222.66.154.70:2083") + "/file/download/SSYH?id=" + app.get(i)._id + "&" + "yxfa" + app.get(i).metadata.yxfa);
//
//
//                }
                // 创建一个线性布局管理器
                LinearLayoutManager layoutManager1 = new LinearLayoutManager(activity);
                // 设置布局管理器
                mRecyclerViewDetails.setLayoutManager(layoutManager1);
                layoutManager1.setOrientation(OrientationHelper.HORIZONTAL);
                DesImageRecyclerViewAdapter desImageRecyclerViewAdapter = new DesImageRecyclerViewAdapter(activity, imaDatas);
                mRecyclerViewDetails.setAdapter(desImageRecyclerViewAdapter);
                desImageRecyclerViewAdapter.setOnItemClickListener(new DesImageRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        if (AppTool.openFile(MainMapYHActivity.this, position, imaDatas)) {
                            return;
                        }
                        int p = imaDatas.size() - imaDatasphoto.size();
                        mViewGalleryPhoto.setData1(position - p, imaDatasphoto);
                    }
                });
                break;
            case 4:

                if (result != null && !result.contains("error")) {
                    JSONObject jsonObject= null;
                    try {
                        jsonObject = new JSONObject(result);

                    if (jsonObject.getInt("Code")!=200){
                        ToastUtils.showToast(jsonObject.getString("Msg"));
                        return;
                    }

                    doing = true;
//                    RelyIdBean relyIdBean = GsonUtils.getObject(result, RelyIdBean.class);
                    RelyIdBean relyIdBean = GsonUtils.getObject(jsonObject.getJSONObject("Data").toString(), RelyIdBean.class);
                    relyid = relyIdBean.S_RECODE_ID;
                    SPUtil.getInstance(MainMapYHActivity.this).setStringValue(SPUtil.YHID, relyid);
                    if (AppTool.isNull(SPUtil.getInstance(MainMapYHActivity.this).getStringValue(SPUtil.YHID))) {
                        ToastUtils.showToast("relyid为空请,请重重试");
                        return;
                    }
                    if (aMapLocation != null) {
                        if (allimages.size() > 0) {
                            map = new HashMap<>();
                            //把高德经纬度转换为84
                            Gps g = PositionUtil.gcj_To_Gps84(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                            map.put("x", g.getWgLat());
                            map.put("y", g.getWgLon());
                            map.put("relyid", SPUtil.getInstance(MainMapYHActivity.this).getStringValue(SPUtil.YHID));
                            ArrayList<String> arrayList = new ArrayList<>();
                            ArrayList<String> imgList = new ArrayList<>();
                            String audioPath = null;
                            String videoPath = null;
                            for (int i = 0; i < allimages.size(); i++) {

                                if (allimages.get(i).mType == 2) {//语音
                                    File f = new File(allimages.get(i).getCompressPath());
                                    File mm = AppTool.getAudioFolder(this, "/MyRecording" + System.currentTimeMillis() + "@" + timel + ".mp4");
                                    if (f.renameTo(mm)) {
                                        arrayList.add(mm.getAbsolutePath());
                                        audioPath = mm.getAbsolutePath();
                                    }
                                } else if (allimages.get(i).mType == 1) {//视频
                                    videoPath = allimages.get(i).getCompressPath();
                                    arrayList.add(allimages.get(i).getCompressPath());
                                } else {
                                    imgList.add(allimages.get(i).getCompressPath());
                                    arrayList.add(allimages.get(i).getCompressPath());
                                }
                            }
                            map.put("yxfa", System.currentTimeMillis());
                            map.put("S_TYPE", mTvclaphotostart.getText().toString());
                            map.put("S_DESC", et_contentdealstart.getText().toString());
                            show();
                            presenter.reporFile(1, map, arrayList, audioPath, videoPath, imgList);
                        } else {
                            ToastUtils.showToast("请选择图片");
                        }
                    } else {
                        ToastUtils.showToast("定位失败");
                    }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    ToastUtils.showToast("服务器异常");
                }

                break;

            case 6:
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
                break;

            case 66://结束养护
            case 888://取消养护
                JSONObject  jsonObject = null;
                try {
                    jsonObject = new JSONObject(result);

                    if (jsonObject.getInt("Code")!=200){
                        ToastUtils.showToast(jsonObject.getString("Msg"));
                        return;
                    }
                    endYH();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                break;

        }


    }
    boolean isFistsetMap=true;

    //地图下载结束的回调
    @Override
    public void onClick(String mOnClick) {
        switch (mOnClick) {
            case "DOWNLOADSUCCESS":
                if (mLoadingWaitView == null) {
                    mLoadingWaitView = findViewById(R.id.loadingWaitView);
                }
                mLoadingWaitView.setVisibility(View.GONE);
                if (!MapUtil.getInstance(this).isExistspath(MapdownloadUtil.strMapUrl))
                    return;
                if (mLocationOverlay == null) {
                    mLocationOverlay = new GraphicsOverlay();
                }
                if (!mapv.getGraphicsOverlays().contains(mLocationOverlay)) {
                    mapv.getGraphicsOverlays().add(mLocationOverlay);
                }

                mAAtt.initB().graphicsOverlayAdd(mapv);
                mAAtt.initC().initFilter(MainMapYHActivity.this, broadcast);


                mArcGISMapVector = MapUtil.getInstance(this).getArcGISMapVector(MapdownloadUtil.strMapUrl, mapv);
                ArcGISRuntimeEnvironment.setLicense("runtimelite,1000,rud5799562951,none,PM0RJAY3FLLR2B3TR001");
                mapv.setAttributionTextVisible(false);
                MapUtil.getInstance(this).showGpsDialog(this, 1);
//                MapUtil.getInstance(this).setLocicosld(aMapLocation, mapv);
                MapViewTouchListener mMapViewTouchListener = new MapViewTouchListener(this, mapv);
                mapv.setOnTouchListener(mMapViewTouchListener);

                MapUtil.getInstance(this).setshanghai(mapv);

//                mServiceFeatureTable = new ServiceFeatureTable(SPUtils.get("url3", "http://222.66.154.70:2084") + "/arcgis/rest/services/DLZXXService/MapServer/0");
                mServiceFeatureTable = new ServiceFeatureTable(AppConfig.YHRoadurl);
//                http://222.66.154.70:2084/arcgis/rest/services/DLZXXService/MapServer/0
                mFeaturelayer = new FeatureLayer(mServiceFeatureTable);
                mFeaturelayer.setOpacity(0.8f);

                SimpleLineSymbol lineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLACK, 1);
                SimpleFillSymbol fillSymbol = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, Color.YELLOW, lineSymbol);
                mFeaturelayer.setRenderer(new SimpleRenderer(fillSymbol));
                ArcGISVectorTiledLayer mainArcGISVectorTiledLayer = new ArcGISVectorTiledLayer(MapdownloadUtil.strMapUrl);
                Basemap mainBasemap = new Basemap(mainArcGISVectorTiledLayer);
//                mArcGISMapVector = new ArcGISMap(mainBasemap);
                mArcGISMapVector.getOperationalLayers().add(mFeaturelayer);

                mFeaturelayer.setVisible(false);
                ivShanghai.setVisibility(View.VISIBLE);
                tvPlus.setVisibility(View.VISIBLE);
                ivDoing.setVisibility(View.GONE);
                tvMinus.setVisibility(View.VISIBLE);
                ivLocation.setVisibility(View.VISIBLE);

                mainMapImageLayer = MapUtil.getInstance(this).getMapImageLayer();
                MapUtil.getInstance(this).addMapService(mainMapImageLayer, mArcGISMapVector, this, false);
//                operationalLayers.get(0).setVisible(false);
//                addMapService();
                break;
            case "DOWNLOADFIL":
                mLoadingWaitView.failure();
                break;
        }
    }

    public void searchForState() {
        mFeaturelayer.setVisible(false);
//        mFeaturelayer.setDefinitionExpression(" NAME in ('赵江路','蔡家路','慈母路','鹤祥路','浦屯路','程鹤路')");
        allRoad.clear();
        //把高德转换成84
        if (MapUtil.isLegalLL(aMapLocation)) {
            Gps g1 = PositionUtil.gcj_To_Gps84(aMapLocation.getLatitude(), aMapLocation.getLongitude());
//            Gps g1 = PositionUtil.bd09_To_Gps84(31.152865,121.093546);
//            Gps g1 = PositionUtil.gcj_To_Gps84( 31.152736,121.142764);
//            if (SPUtils.get("标记", "").equals("1")
//                    ) {
//            } else {
//                g1.setWgLon(121.134);
//                g1.setWgLat(31.155);
//            }
//            g1.setWgLon(121.08344);
//            g1.setWgLat(31.144881);
//            mFeaturelayer.clearSelection();
            double tolerance = 0.001;
            double mapTolerance = tolerance;
//                    * mapv.getUnitsPerDensityIndependentPixel();
            //查询范围
            Envelope envelope = new Envelope(g1.getWgLon() + mapTolerance, g1.getWgLat() - mapTolerance,
                    g1.getWgLon() - mapTolerance, g1.getWgLat() + mapTolerance, mArcGISMapVector.getSpatialReference());
//            //显示的范围
//            PointCollection coloradoCorners = new PointCollection(SpatialReferences.getWgs84());
//            coloradoCorners.add(g1.getWgLon() - mapTolerance, g1.getWgLat() - mapTolerance);
//            coloradoCorners.add(g1.getWgLon() + mapTolerance, g1.getWgLat() - mapTolerance);
//            coloradoCorners.add(g1.getWgLon() + mapTolerance, g1.getWgLat() + mapTolerance);
//            coloradoCorners.add(g1.getWgLon() - mapTolerance, g1.getWgLat() + mapTolerance);
//            Polygon polygon = new Polygon(coloradoCorners);
//            SimpleFillSymbol fillSymbol = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, 0x667265fb, null);
//            final GraphicsOverlay overlay = new GraphicsOverlay();
//            Graphic gra = new Graphic(envelope, fillSymbol);
//            overlay.getGraphics().clear();
//            overlay.getGraphics().add(gra);
//            if (!mapv.getGraphicsOverlays().contains(overlay)) {
//                mapv.getGraphicsOverlays().add(overlay);
//            }
            final QueryParameters query = new QueryParameters();
            query.setReturnGeometry(true);//指定是否返回几何对象
            showDialog();
            query.setGeometry(envelope);// 设置空间几何对象
            final ListenableFuture<FeatureQueryResult> future = mServiceFeatureTable.queryFeaturesAsync(query, ServiceFeatureTable.QueryFeatureFields.LOAD_ALL);
            future.addDoneListener(new Runnable() {
                @Override
                public void run() {
                    try {
                        FeatureQueryResult result = future.get();
                        List<QueryParameters.OrderBy> orderByFields = query.getOrderByFields();
                        for (Object element : result) {
                            if (element instanceof Feature) {
                                Feature mFeatureGrafic = (Feature) element;

                                Map<String, Object> mQuerryString = mFeatureGrafic.getAttributes();

                                LogUtils.e("附近暂无道路信息", mQuerryString.toString());
                                roadBean = new RoadBean();
                                roadBean.NAME = (String) mQuerryString.get("NAME");
                                roadBean.QD_ROAD = (String) mQuerryString.get("QD_ROAD");
                                roadBean.ZD_ROAD = (String) mQuerryString.get("ZD_ROAD");
                                roadBean.S_ROAD_ID = (String) mQuerryString.get("S_ROAD_ID");
                                roadBean.RANK = (String) mQuerryString.get("RANK");
                                roadBean.geometry = mFeatureGrafic.getGeometry();
                                allRoad.add(roadBean);
                            }
                        }
                        hide();
                        if (allRoad.size() < 1) {
                            ToastUtils.showToast("附近暂无道路信息");
                        } else {
                            final YHReportListViewAdapter yhReportListViewAdapter = new YHReportListViewAdapter(activity, allRoad);
                            listview.setAdapter(yhReportListViewAdapter);
                            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    rBean = allRoad.get(position);
                                    S_ROAD_ID = allRoad.get(position).S_ROAD_ID;
                                    for (int i = 0; i < allRoad.size(); i++) {
                                        allRoad.get(i).isSelect = false;

                                    }
                                    allRoad.get(position).isSelect = true;

                                    yhReportListViewAdapter.notifyDataSetChanged();


                                }
                            });
                            llReport.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {

                    }
                }
            });

        } else {


            ToastUtils.showToast("定位失败");
        }

    }

    public void setPopSelect(final List<ClasBean.ValueBean> popall) {
        //                //   条件选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                if (llReport.getVisibility() == View.VISIBLE) {
                    mTvclaphotostart.setText(popall.get(options1).SVALUE);
                } else {
                    mTvclaphoto.setText(popall.get(options1).SVALUE);
                }

            }
        }).build();
        pvOptions.setPicker(popall);
        pvOptions.show();

    }

    //防止不小心按到返回键
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                isExit();
                break;
        }
        return true;
    }

    //记录第一次按返回键的时间
    private long firstTime1 = 0;

    public boolean isExit() {
        if (isTasking) {
//            MapUtil.getInstance(this).showTaskendDialog(this);
            ToastUtils.showToast("正在养护，无法退出");
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

    @Override
    public void getImageLayer(ArcGISMapImageLayer mImageLayer) {
        if (mImageLayer.getLoadStatus() == LoadStatus.LOADED) {
            LogUtils.e("zoubeiwen", LoadStatus.LOADED);
            isLoaded = true;
            mainMapImageLayer.setVisible(true);
            mainMapImageLayer = mImageLayer;
            mLegend.setMainMapImageLayer(mainMapImageLayer);

            if (isFistsetMap){
                isFistsetMap=false;
//                mLegend.mLlguanwangys.setSelected(true);
//                mLegend.mIvguanwangys.setSelected(false);
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
    public void notifyDataSetChangedListen(int p) {

        if (mGalleryPhoto == 0) {
            allimages.remove(p + ps);
//        mTvphotosum.setText(allimages.size() + "/9图片");
            imageRecyclerViewAdapter.notifyDataSetChanged();
        } else {
            allPhotoimages.remove(p + ps);
//        mTvphotosum.setText(allimages.size() + "/9图片");
            photoRecyclerViewAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void goBack(int mGoBack) {
        switch (mGoBack) {
            case GoBackInteDef.DELEVIDEO:
                break;
            case GoBackInteDef.PALYVIDEO:

                break;
            case GoBackInteDef.REPLACEVIDEO:
                mTImage = new TImage(mfilevideoTem, 1);
                if (llPhoto.getVisibility() == View.VISIBLE) {

                    allPhotoimages.remove(0);
                    allPhotoimages.add(0, mTImage);

                    photoRecyclerViewAdapter.notifyDataSetChanged();
                } else {

                    allimages.remove(0);
                    allimages.add(0, mTImage);

                    imageRecyclerViewAdapter.notifyDataSetChanged();
                }
                break;
            case GoBackInteDef.REPLACESOUND:
                if (llPhoto.getVisibility() == View.VISIBLE) {

                    allPhotoimages.remove(mSoundindex);
                    allPhotoimages.add(mSoundindex, mTImage);
                    photoRecyclerViewAdapter.notifyDataSetChanged();
                } else {
                    allimages.remove(mSoundindex);
                    allimages.add(mSoundindex, mTImage);
                    imageRecyclerViewAdapter.notifyDataSetChanged();
                }
                break;

        }
    }

    String mfilevideoTem;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 2:
                if (resultCode == 2) {

                    mfilevideoTem = data.getStringExtra("bitmap");//视频的路径
                    String mvideoBitmapTem = data.getStringExtra("suolvetu");//视频图片路径
                    if (AppTool.isNull(mvideoBitmapTem) || AppTool.isNull(mfilevideoTem)) {
                        ToastUtils.showToast("视频保存失败，请重新录制");
                        return;
                    }
                    mTImage = new TImage(mfilevideoTem, 1);
                    if (llPhoto.getVisibility() == View.VISIBLE) {
                        if (allPhotoimages.size() == 0) {
                            allPhotoimages.add(0, mTImage);
                            photoRecyclerViewAdapter.notifyDataSetChanged();
                        } else {
                            if (allPhotoimages.get(0).mType == 1) {
                                MapUtil.getInstance(this).showIsReplaceFileDialog(this, this, GoBackInteDef.setBack(GoBackInteDef.REPLACEVIDEO), "只能上传一个视频，是否要替换已有的视频?");
                            } else {
                                mTImage = new TImage(mfilevideoTem, 1);
                                allPhotoimages.add(0, mTImage);
                                photoRecyclerViewAdapter.notifyDataSetChanged();
                            }

                        }
                    } else {
                        if (allimages.size() == 0) {
                            allimages.add(0, mTImage);
                            imageRecyclerViewAdapter.notifyDataSetChanged();
                        } else {
                            if (allimages.get(0).mType == 1) {
                                MapUtil.getInstance(this).showIsReplaceFileDialog(this, this, GoBackInteDef.setBack(GoBackInteDef.REPLACEVIDEO), "只能上传一个视频，是否要替换已有的视频?");
                            } else {
                                mTImage = new TImage(mfilevideoTem, 1);
                                allimages.add(0, mTImage);
                                imageRecyclerViewAdapter.notifyDataSetChanged();
                            }

                        }
                    }

                }
                break;
            case requestCountinuTask://继续养护
                if (resultCode == resultCountinuTask) {
                    Response.DataBean mDataBean = (Response.DataBean) data.getSerializableExtra("DataBean");
                    continueTask(mDataBean);
                }
                break;
        }
    }


    //地图的点击时间,奇葩API  地图所有的点击都是走这个回调
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
            point = mapv.screenToLocation(new android.graphics.Point((int) e.getX(), (int) e.getY()));
            final ListenableFuture<IdentifyGraphicsOverlayResult> identifyGraphic = mMapView.identifyGraphicsOverlayAsync(markerOverlay, screenPoint, 50.0, false, 1);

            identifyGraphic.addDoneListener(new Runnable() {
                @Override
                public void run() {
                    try {
                        IdentifyGraphicsOverlayResult grOverlayResult = identifyGraphic.get();
                        List<Graphic> graphic = grOverlayResult.getGraphics();
                        int identifyResultSize = graphic.size();
                        if (identifyResultSize > 0) {
                            String str = (String) graphic.get(0).getAttributes().get("id");
                            if (!graphic.isEmpty()) {

                                llDetails.setVisibility(View.VISIBLE);

                            }

                            Map<String, Object> map = new HashMap<>();
                            map.put("yxfa", str);
                            map.put("relyid", SPUtil.getInstance(MainMapYHActivity.this).getStringValue(SPUtil.YHID));

                            presenter.getPicUrl(5, map);

                        } else if (identifyResultSize == 0) {
                            if (mIvsheshi.isSelected()) {
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

    @Override
    public void getQueryResultMap(ArrayList<AdapterBean> abList, int mTable) {
        mAAtt.initC().setMessgae(myHandler, abList, mTable, 1);
    }

    @Override
    public void getQueryResultMapSeach(ArrayList<AdapterBean> abList, int mTable) {
        mAAtt.initC().setMessgae(myHandler, abList, mTable, 2);
    }

    @Override
    public void resultFailureMAP(int what, String result) {

    }

    @Override
    public void resultSuccessMAP(int resultid, String result) {
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
            } else if (resultid == 3) {
                if (mVXJSeachlist.historyList.size() == 0) {
                    mVXJSeachlist.mSearchHisView.setVisibility(View.GONE);
                    mVXJSeachlist.listviewseach.setVisibility(View.VISIBLE);
                } else {
                    mVXJSeachlist.mSearchHisView.setVisibility(View.VISIBLE);
                    mVXJSeachlist.listviewseach.setVisibility(View.GONE);
                }
                SearchHistory history = new Gson().fromJson(result, SearchHistory.class);
                mVXJSeachlist.historyList.clear();
                mVXJSeachlist.historyList.addAll(history.getData());
                mVXJSeachlist.mHisAdapter.notifyDataSetChanged();
            } else if (resultid == 4) {
                mVXJSeachlist.mSearchHisView.setVisibility(View.GONE);
                mVXJSeachlist.listviewseach.setVisibility(View.VISIBLE);
            }

        } else {
            mLoadingWaitView.setVisibility(View.GONE);
            ToastUtils.showToast(result);
        }
    }

    public boolean onClickSingle(android.graphics.Point screenPoint) {
        if (mapv.getMapScale() > 100000 || !isLoaded)
            return true;
        initStyleSingleTap();
        mViewFourthFragmentSeachdetail.setVisibility(View.GONE);
        Point point84 = mapv.screenToLocation(screenPoint);
        mapv.setViewpointCenterAsync(point84);
        int tolerance = 50;
//        double mapTolerance = tolerance * mapv.getUnitsPerDensityIndependentPixel();
        Envelope envelope = new Envelope(point84.getX() - 0.0001, point84.getY() - 0.0001, point84.getX() + 0.0001, point84.getY() + 0.0001, SpatialReferences.getWgs84());
        double[] doubles = CoordinateUtil.lonLat2Mercator(point84.getX(), point84.getY());
        double aDouble0 = doubles[0];
        double aDouble1 = doubles[1];
        Polygon polygon = GeometryEngine.buffer(point84, 0.00014);
        Graphic gra = new Graphic(polygon, mAAtt.initB().mTouchSymbol);
        mAAtt.initB().mTouchgraphicsOverlay.getGraphics().add(gra);
        query = new QueryParameters();
        query.setReturnGeometry(true);//指定是否返回几何对象
        query.setGeometry(polygon);// 设置空间几何对象
        String townname = SPUtil.getInstance(this).getStringValue(SPUtil.APP_TOWNNAME);

        if (!AppTool.isNull(townname)) {
            query.setWhereClause("upper(S_town)='" + townname + "'");
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

    public void initVLlTop() {
        mLltop = findViewById(R.id.ll_top);
        mIvback = findViewById(R.id.iv_topback);
        mIvspeak = findViewById(R.id.iv_topspeak);
        mIvspeak.setVisibility(View.VISIBLE);
        mIvclean = findViewById(R.id.iv_topclean);
        mTvseach = findViewById(R.id.tv_topseach);
        mEtseach = findViewById(R.id.et_topseach);
        setmEtseachstate(false);
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

    private BroadcastReceiver broadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String acString = intent.getAction();

            if (acString.equals(MyServiceYH.MAPTRACKINIT)) {
                mAAtt.getASingle().cleanR(mapv);
            } else if (acString.equals(MyServiceYH.MAPTRACKING)) { //画轨迹
                if (isTasking) {
                    double lat = intent.getDoubleExtra("lat84", 0);
                    double lon = intent.getDoubleExtra("lon84", 0);
                    if (lat != 0 && lon != 0) {
                        mAAtt.getASingle().drawR(mapv, lat, lon);
                    }
                }
            } else if (acString.equals(MyServiceYH.MAPTRACKCLEAN)) { //清除轨迹
                mAAtt.getASingle().cleanR(mapv);
            } else if (acString.equals(MyServiceYH.NMILEAGE)) { //里程数计算
                double NMILEAGE = intent.getDoubleExtra("NMILEAGE", 0);
                if (!AppTool.isNull(SPUtil.getInstance(MainMapYHActivity.this).getStringValue(SPUtil.YHID)) && NMILEAGE != 0 && isTasking) {
                    upXY(NMILEAGE);
                }
            } else if (acString.equals(Intent.ACTION_SCREEN_ON)) { // 屏幕亮

//                Toast.makeText(MainActivity.this, "由于屏幕锁定熄屏，期间的巡查轨迹没有被记录", Toast.LENGTH_LONG).show();

            }
        }

    };

    public void initStyleSingleTap() {
        isFristLoaded = false;
        mTablesum = 0;
        mViewonCMD.initStyle();
        mAAtt.initB().graphicsOverlayClean();
    }

    public Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    ArrayList<AdapterBean> abList = (ArrayList<AdapterBean>) msg.obj;

                    if (msg.arg1 == AppConfig.PSGDJ1 || msg.arg1 == AppConfig.PSGDJ2 || msg.arg1 == AppConfig.PSGDJ3 || msg.arg1 == AppConfig.PSGDWSJ1 || msg.arg1 == AppConfig.PSGDWSJ2) {
                        if (msg.arg1 == AppConfig.PSGDJ1) {
                            abListJS.clear();
                            addabListJS(abList);
                            MapUtil.getInstance(MainMapYHActivity.this).FeatureQueryResulPSJ(query, MainMapYHActivity.this, AppConfig.PSGDJ2 + "");
                        } else if (msg.arg1 == AppConfig.PSGDJ2) {
                            addabListJS(abList);
                            MapUtil.getInstance(MainMapYHActivity.this).FeatureQueryResulPSJ(query, MainMapYHActivity.this, AppConfig.PSGDJ3 + "");
                        } else if (msg.arg1 == AppConfig.PSGDJ3) {
                            addabListJS(abList);
//                            mFragmentView.mRbf1.setText("雨水井");
                            mViewonCMD.mNotifyDataSet(abListJS, AppConfig.PSJ);

                        } else if (msg.arg1 == AppConfig.PSGDWSJ1) {
                            abListJS.clear();
                            addabListJS(abList);
                            MapUtil.getInstance(MainMapYHActivity.this).FeatureQueryResulPSJ(query, MainMapYHActivity.this, AppConfig.PSGDWSJ2 + "");
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

    public void addabListJS(ArrayList<AdapterBean> abList) {
        if (abList != null && abList.size() > 0) {
            isFristLoaded = true;
            abListJS.addAll(abList);
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

    public void clearHis(final List<SearchHistory.DataBean> hisList, int pos, final SearchHisAdapter mAdapter) {
        DialogUtil dialogUtil = DialogUtil.getSingleton();
        dialogUtil.showTipDialog(this, "立即清空", "清空历史记录？");
        dialogUtil.setOnButtonClickListener(new DialogUtil.OnButtonClickListener() {
            @Override
            public void onOkClick() {
                String user = SPUtil.getInstance(MainMapYHActivity.this).getStringValue(SPUtil.USERNO);
                presenter.clearHisList(hisList, user, mAdapter);
            }
        });
    }

    public void continueTask(Response.DataBean dataBean) {
        AppAttribute.G. initState();
        map = new HashMap<>();
        isContinue = true;
        doing = true;//宋写的这个表示  不是很清楚具体干嘛的这里不着设置时间计算器会不执行
        isTasking = true;
        relyid = dataBean.S_RECORD_ID;
        SPUtil.getInstance(this).setStringValue(SPUtil.YHID, relyid);
        mAAtt.initB().roadgraphicsOverlay.getGraphics().add(mAAtt.initB().getGraphicroad(geometry));
        point = new Point(dataBean.NY, dataBean.NX, SpatialReference.create(4326));
        startTime = 0;
        if (AppTool.isNull(dataBean.TSTARTM) || AppTool.isNull(dataBean.TUPLOAD)) {
            startTime = System.currentTimeMillis();
        } else {
            startTime = System.currentTimeMillis() - (AppTool.getTimeperiod(dataBean.TSTARTM,dataBean.TUPLOAD) *1000);
        }

 String t=AppTool.getHMSTime(startTime);
//        tvTime.setText(t);
        if (startTime < 0) {
            startTime = System.currentTimeMillis();
        }
        LogUtils.e("zzzzzdCollections4", dataBean.NMILEAGE + "----" + dataBean.orderDis);
        if (dataBean.NMILEAGE > 100) {
            tvDistance.setText(
                    AppTool.getTwoDecimal(dataBean.NMILEAGE / 1000) + "Km");
        } else {
            tvDistance.setText(AppTool.getTwoDecimal(dataBean.NMILEAGE) + "m");
        }
        llAll.setVisibility(View.VISIBLE);
        llReport.setVisibility(View.GONE);
        llPhoto.setVisibility(View.GONE);
        ivTrackstart.setVisibility(View.GONE);
        mTvclaphotostart.setText("检查井");
        et_contentdealstart.setText("");
        allAmapLocation.clear();
//    handler.sendEmptyMessageDelayed(1, 1);
        handler.sendEmptyMessage(2);
        intent.putExtra("S_MAN_ID", SPUtils.get("user", ""));
        intent.putExtra("N_Y", dataBean.NX);
        intent.putExtra("N_X", dataBean.NY);
        intent.putExtra("Dis", dataBean.NMILEAGE);
        if (!AppTool.isServiceRunning(this, MyServiceYH.class.getName())) ;
        {
            mAAtt.getASingle().cleanR(mapv);
            stopService(intent);
        }
        startService(intent);
        allimages.clear();
        imageRecyclerViewAdapter.notifyDataSetChanged();
        allPhotoimages.clear();
        photoRecyclerViewAdapter.notifyDataSetChanged();
    }

}
