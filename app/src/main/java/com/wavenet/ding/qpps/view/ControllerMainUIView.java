package com.wavenet.ding.qpps.view;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dereck.library.utils.ToastUtils;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.activity.MainMapXJActivity;
import com.wavenet.ding.qpps.activity.WebViewHistoryXJActivity;
import com.wavenet.ding.qpps.api.QPSWApplication;
import com.wavenet.ding.qpps.bean.BreakOffBean;
import com.wavenet.ding.qpps.bean.Gps;
import com.wavenet.ding.qpps.interf.CallBackMap;
import com.wavenet.ding.qpps.serverutils.LocService;
import com.wavenet.ding.qpps.utils.AppAttribute;
import com.wavenet.ding.qpps.utils.AppConfig;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.MapUtil;
import com.wavenet.ding.qpps.utils.PositionUtil;
import com.wavenet.ding.qpps.utils.SPUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;


public class ControllerMainUIView extends LinearLayout implements View.OnClickListener {
    public static int timeint = 0;
    public ImageView mIvpause;
    public TextView mTvtaskpai;
    public LinearLayout mLltrackstart;
   public  LinearLayout mLltracking;
    public ImageView mIvsheshi;
   public TextView mTvplus, mTvminus, mTvdistance, mTvtime;
    public boolean isStartde = false;//是否已经开始巡检，0未巡检   1 正在巡讲当中
    Context mContext;
    LinearLayout mLltaskreport, mLltaskcancel, mLltasktuidan;
    ImageView mIvtuceng, mIvshanghai, mIvDoing, mIvPhoto, mIvLocation, mIvtaskreport, mIvtaskend, mTvtaskcancel, mIvtasktuidan;
   public  ImageView  mIvgensui;
    CallBackMap mCallBackMap;
    int isUn = 0;
    DecimalFormat fnum;
    String disstr;
    String timepause = "";
    private MainMapXJActivity mActivity;

    public static int REPORT = 1001;


    public ControllerMainUIView(Context context) {
        super(context);
        initView(context);
    }

    public ControllerMainUIView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ControllerMainUIView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }



    private void initView(Context context) {
        mContext = context;
        mActivity = (MainMapXJActivity) context;
        LayoutInflater.from(mContext).inflate(R.layout.view_mainui, this);

        if (!isInEditMode()) {
            isUn = AppTool.isRegistered(true, this, isUn);
            mIvtuceng = findViewById(R.id.iv_tuceng);
            mIvtuceng.setSelected(false);
            mIvtuceng.setOnClickListener(this);
            mIvsheshi = findViewById(R.id.iv_sheshi);
            mIvsheshi.setSelected(false);
            mIvsheshi.setOnClickListener(this);
            mIvgensui = findViewById(R.id.iv_gensui);
            mIvgensui.setSelected(false);// 默认不跟随你
            mIvgensui.setOnClickListener(this);
            mTvtaskpai = findViewById(R.id.tv_taskpai);
            mTvtaskpai.setOnClickListener(this);
            mTvtaskpai.setVisibility(GONE);
            mIvshanghai = findViewById(R.id.iv_shanghai);
            mIvshanghai.setOnClickListener(this);
            mIvDoing = findViewById(R.id.iv_doing);
            mIvDoing.setOnClickListener(this);
            mIvPhoto = findViewById(R.id.iv_photo);
            mIvPhoto.setOnClickListener(this);
            mIvLocation = findViewById(R.id.iv_location);
            mIvLocation.setOnClickListener(this);
            mLltrackstart = findViewById(R.id.ll_trackstart);
            mLltrackstart.setOnClickListener(this);
//            isShowstart(false);
            mLltracking = findViewById(R.id.ll_tracking);
            mLltracking.setVisibility(GONE);
            mLltracking.setOnClickListener(this);
            mTvtime = findViewById(R.id.tv_time);
            mTvdistance = findViewById(R.id.tv_distance);
            mIvtaskreport = findViewById(R.id.iv_taskreport);
            mIvtaskreport.setOnClickListener(this);
            mIvpause = findViewById(R.id.iv_pause);
            mIvpause.setOnClickListener(this);
            mIvtaskend = findViewById(R.id.iv_taskend);
            mIvtaskend.setOnClickListener(this);
            mIvtasktuidan = findViewById(R.id.iv_tasktuidan);
            mIvtasktuidan.setOnClickListener(this);
            mTvplus = findViewById(R.id.tv_plus);
            mTvplus.setOnClickListener(this);
            mTvminus = findViewById(R.id.tv_minus);
            mTvminus.setOnClickListener(this);
            mTvtaskcancel = findViewById(R.id.iv_taskcancel);
            mTvtaskcancel.setOnClickListener(this);
            mLltaskreport = findViewById(R.id.ll_taskreport);
            mLltaskcancel = findViewById(R.id.ll_taskcancel);
            mLltasktuidan = findViewById(R.id.ll_tasktuidan);
            findViewById(R.id.iv_history).setOnClickListener(this);
            initData();
        }
    }

    protected void initData() {
        fnum = new DecimalFormat("##0.00");
        mIvtuceng.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MapUtil.getInstance(mContext).showTaskingDialog(mContext,"XJ");
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_tuceng:
                if (mActivity.mainMapImageLayer!=null&&mActivity.mainMapImageLayer.getLoadStatus() != LoadStatus.LOADED) {
                    ToastUtils.showToast("业务图层加载失败，重新加载请稍后");
                    mActivity.mainMapImageLayer = MapUtil.getInstance(mContext).addMapService(mActivity.mArcGISMapVector, mActivity, true);
                    return;
                }
                mActivity.mLegend.showOrHide("");
                break;
            case R.id.iv_sheshi:
                mIvsheshi.setSelected(!mIvsheshi.isSelected());
                if (mIvsheshi.isSelected()){
                    mActivity.mLltop.setVisibility(VISIBLE);
                    mTvtaskpai.setVisibility(GONE);
                    mLltrackstart.setVisibility(GONE);
                    mLltracking.setVisibility(GONE);
                }else {
                    mActivity.mLltop.setVisibility(GONE);
                    mTvtaskpai.setVisibility(VISIBLE);
                    mActivity.mViewFourthFragmentSeachdetail.setVisibility(GONE);
                    mActivity.mViewonCMD.setVisibility(GONE);
                    mActivity.initStyleSingleTap();
                    if (isStartde) {
                        mLltracking.setVisibility(VISIBLE);
                    } else {
                        mLltrackstart.setVisibility(VISIBLE);
                    }

                }

                break;
                case R.id.tv_taskpai:
                mActivity.presenter.clickRequestTasklist(SPUtil.getInstance(mContext).getStringValue(SPUtil.USERNO), "W1006500002");
                break;
            case R.id.iv_shanghai:
//        mActivity. mMapView.getMapScale();
//        LogUtils.e("ddddddddddddddd",mActivity. mMapView.getMapScale()+"") ;
                MapUtil.getInstance(mContext).setshanghai(mActivity.mMapView);
                break;
            case R.id.iv_doing:
                break;
            case R.id.iv_photo:
                mActivity.mIvshou.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_location:
                if (mCallBackMap != null && QPSWApplication.Locpoint!=null) {
                    mActivity.mMapView.setViewpointCenterAsync(QPSWApplication.Locpoint, 10000);
                }else {
                    ToastUtils.showToast("定位失败");
                }
                break;
            case R.id.ll_trackstart:
                if (mActivity.mainMapImageLayer==null){
                    ToastUtils.showToast("底图加载失败");
                    return;
                }
                MapUtil.getInstance(mContext).setstardailyloc(MainMapXJActivity.aMapLocation, mActivity, 0);

                //   MapUtil.getInstance(mContext).setstarico(mActivity.aMapLocation, mActivity.mMapView);
//                mActivity.presenter.clickRequestTasklist(SPUtil.getInstance(mContext).getStringValue(SPUtil.USERNO), "W1006500002");// TODO
                break;
            case R.id.tv_plus:
//        ToastUtils.showToast(    mActivity.mMapView.getMapScale()+"");
                mActivity.mMapView.setViewpointScaleAsync(mActivity.mMapView.getMapScale() * 0.5);
                break;
            case R.id.tv_minus:
//        ToastUtils.showToast(    mActivity.mMapView.getMapScale()+"");
                mActivity.mMapView.setViewpointScaleAsync(mActivity.mMapView.getMapScale() / 0.5);
                break;
                case R.id.iv_gensui:
                    mIvgensui.setSelected(!mIvgensui.isSelected());
                break;
            case R.id.iv_taskreport:
                if (mIvpause.isSelected()) {
                    ToastUtils.showToast("巡检暂停中，不能上报");
                    return;
                }
                if (MainMapXJActivity.Task == 0) {
                    mActivity.mTaskReportView.showView(REPORT);
                }

                break;
            case R.id.iv_taskend:
                if (mIvpause.isSelected()) {
                    ToastUtils.showToast("巡检暂停中，不能结束");
                    return;
                }
                if (MainMapXJActivity.Task == 0) {
                    MapUtil.getInstance(mContext).showTaskendDialog(mContext);

                } else if (MainMapXJActivity.Task == 1) {
                    MapUtil.getInstance(mContext).showTaskendPaiDialog(mContext);
//                    mActivity.mTaskDealView.initData(mActivity.mtvBean);
                }

                break;
            case R.id.iv_taskcancel:
                if (mIvpause.isSelected()) {
                    ToastUtils.showToast("巡检暂停中，不能取消");
                    return;
                }
                if (MainMapXJActivity.Task==1) {
                    MapUtil.getInstance(mContext).showTaskcancelDialog2(mContext, this);

                } else {
                    MapUtil.getInstance(mContext).showTaskcancelDialog(mContext, this);
                }


                break;
            case R.id.iv_tasktuidan:
                if (mIvpause.isSelected()) {
                    ToastUtils.showToast("巡检暂停中，不能退单");
                    return;
                }
                mActivity.mCtaskreason.isShowLlreason(true, 1);
                break;
            case R.id.iv_pause:
                mIvpause.setSelected(!mIvpause.isSelected());
                if (mIvpause.isSelected()) {
                    timepause=AppTool.getCurrentDate(AppTool.FORMAT_YMDHMS);
                    mActivity.stopService(mActivity.itS);
//                    LocService.mHandler.removeCallbacks(LocService.r);
                    mActivity.mHandlertime.removeCallbacks(mActivity.rTime);
                } else {
                    mActivity.startService(mActivity.itS);
                    int timepau= (int) AppTool.getTimeperiod(timepause,AppTool.getCurrentDate(AppTool.FORMAT_YMDHMS));
                    int tp=  SPUtil.getInstance(mContext).getIntValue(SPUtil.TIMEPAUSE);
                    SPUtil.getInstance(mContext).setIntValue(SPUtil.TIMEPAUSE,timepau+tp);
//                    LocService.mHandler.post(LocService.r);
                    mActivity.mHandlertime.post(mActivity.rTime);
                }

                break;
            case R.id.iv_history:
//                Intent iHistory = new Intent(mActivity, MapTrackActivity.class);
//                iHistory.putExtra("title", "巡检轨迹");
                Intent iHistory = new Intent(mActivity, WebViewHistoryXJActivity.class);
                iHistory.putExtra("isStartde", isStartde);
                iHistory.putExtra("url", String.format(AppConfig.XJHistory, SPUtil.getInstance(mContext).getStringValue(SPUtil.USERNO)));
                mActivity.startActivity  (iHistory);
                //ToastUtils.showToast("距离1");
                break;

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventBusCar(String state) {
        if (MapUtil.VEU.equals(state)) {
            AppTool.isRegistered(false, this, isUn);
        }
    }

    //Task  0 是日常 1是派单
    public void setDoingstyle(boolean isShow) {
        isStartde = isShow;
        mActivity.isStartde=isShow;
        BreakOffBean.isStartde=isStartde;
//        mIvDoing.setVisibility(isShow ? View.INVISIBLE : View.GONE);
//        mIvPhoto.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mLltrackstart.setVisibility(isShow ? View.GONE : View.VISIBLE);
        mLltracking.setVisibility(isShow ? View.VISIBLE : View.GONE);
        AppAttribute.G.initState();
        if (isShow){ //日常、派单开始巡检为ture
            mLltaskreport.setVisibility(MainMapXJActivity.Task == 0 ? View.VISIBLE : View.GONE);
            mLltaskcancel.setVisibility(VISIBLE);
            mLltasktuidan.setVisibility(MainMapXJActivity.Task == 0 ? View.GONE : View.VISIBLE);
            MainMapXJActivity.Distance = 0;
            mTvdistance.setText("0.00 Km");
            mActivity.firstTime=System.currentTimeMillis();
            timeint = 0;
            mActivity.mHandlertime.postDelayed(mActivity.rTime,0);//时间计数
            Gps gpsEp = PositionUtil.gcj_To_Gps84(mActivity.aMapLocation.getLatitude(), mActivity.aMapLocation.getLongitude());
            mActivity. mAAtt.initA().drawR(mActivity.mMapView, gpsEp.getWgLat(), gpsEp.getWgLon(), true);
SPUtil.getInstance(mContext).setStringValue(SPUtil.STARTIME,AppTool.getCurrentDate(AppTool.FORMAT_YMDHMS));
SPUtil.getInstance(mContext).setIntValue(SPUtil.TIMEPAUSE,0);
            // Android 8.0使用startForegroundService在前台启动新服务
            if (!AppTool.isServiceRunning(mContext, LocService.class.getName()));{
                if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
                {
                    mActivity.startForegroundService(mActivity.itS);
                }
                else{
                    mActivity.startService(mActivity.itS);
                }
            }

        } else {
            mActivity.initTask();
            SPUtil.getInstance(mContext).remove(SPUtil.BREAKOFFBEAN);
            BreakOffBean.getInitSingle().init();
            SPUtil.getInstance(mContext).remove(SPUtil.STARTIME);
            MapUtil.graphicsClean(mActivity.mMapView, mActivity.markerOverlay);
            MapUtil.graphicsClean(mActivity.mMapView, mActivity.mStarOverlay);
            MapUtil.graphicsClean(mActivity.mMapView, mActivity.graphicsOverlaysld);
            mActivity. mAAtt.getASingle().cleanR(mActivity.mMapView);
            mActivity. stopService(mActivity.itS);
            mActivity.mHandlertime.removeCallbacks(mActivity.rTime);//时间计数
            BreakOffBean.isContinue=false;
//            MainMapXJActivity.mHandlertime.removeCallbacks(mActivity.rTime);
//            LocService.mHandler.removeCallbacks(LocService.r);
        }
    }

    public void setmTvtaskpai(int sum) {
        mTvtaskpai.setText(sum + "");
        if (sum < 0 || sum == 0) {
            mTvtaskpai.setVisibility(GONE);
        } else {
            mTvtaskpai.setVisibility(VISIBLE);
        }

    }

    public void setmTvtaskpai() {
        mTvtaskpai.setVisibility(VISIBLE);
//        int sum = Integer.parseInt(mTvtaskpai.getText().toString().trim());
//        mTvtaskpai.setText(sum + 1 + "");
    }

    public boolean isTaskDoing() {
        return mLltracking.getVisibility() == VISIBLE;
    }

    public void setDis(double disflo) {
        BreakOffBean .getInitSingle().distance  =disflo ; //记录当前时间，当程序崩溃重新打开用到这个时间
        disstr = fnum.format(disflo);
        mTvdistance.setText(disstr + " Km");
    }

    public void setTime() {
        if (mIvpause.isSelected())
            return;
        timeint++;
        mTvtime.setText(AppTool.StringFormTime(timeint));
    }
    int a;
    public void setTime(boolean isAgain) {

        if (mIvpause.isSelected())
            return;

        if (isAgain){
            timeint=0;
            String timestart=SPUtil.getInstance(mContext).getStringValue(SPUtil.STARTIME);
            String timecurrent=AppTool.getCurrentDate(AppTool.FORMAT_YMDHMS);
            timeint= (int) AppTool.getTimeperiod(timestart,timecurrent);
            if(BreakOffBean.isContinue){
                timeint=timeint+BreakOffBean .getInitSingle().timeend  ;
            }
            int tp=  SPUtil.getInstance(mContext).getIntValue(SPUtil.TIMEPAUSE);
            timeint=timeint-tp;
        }else {
            timeint++;
        }
        BreakOffBean .getInitSingle().time=timeint;//记录当前时间，当程序崩溃重新打开用到这个时间
        mTvtime.setText(AppTool.StringFormTime(timeint));
//        if (mTvtime.getText().toString().contains("10") || mTvtime.getText().toString().contains("20")){
//            mTvtime.setText(a);//测试崩溃巡检是否继续进行的空指针bug
//        }
    }

    public void continueTask(){
        timeint= BreakOffBean.getInitSingle().time;
        BreakOffBean .getInitSingle().timeend=timeint;
        setTime(false);
        setDis(BreakOffBean.getInitSingle().distance);
    }

    public void setListener(String onclickStr) {
        if (mCallBackMap != null) {
            mCallBackMap.onClick(onclickStr);
        }
    }

    public void setCallBackMap(CallBackMap mCallBackMap) {
        this.mCallBackMap = mCallBackMap;
    }


}
