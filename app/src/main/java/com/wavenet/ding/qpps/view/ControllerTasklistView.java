package com.wavenet.ding.qpps.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.maps2d.model.LatLng;
import com.dereck.library.utils.ToastUtils;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.activity.MainMapXJActivity;
import com.wavenet.ding.qpps.adapter.TasklistAdapter;
import com.wavenet.ding.qpps.bean.Gps;
import com.wavenet.ding.qpps.bean.TasklistBean;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.MapUtil;
import com.wavenet.ding.qpps.utils.PositionUtil;
import com.wavenet.ding.qpps.utils.navi.PopWindowNavi;

import java.util.ArrayList;


public class ControllerTasklistView extends LinearLayout implements View.OnClickListener,TasklistAdapter.A {
    Context mContext;
    MainMapXJActivity mActivity;
    TextView mTvtasksum, mTvrefuse, mTvtitle;
    //    private LoadingWaitView mLoadingWaitView;
    ListView listview;
    TasklistAdapter mTradapter;
    ArrayList<TasklistBean.ValueBean> mBeanList = new ArrayList<>();
    PopWindowNavi mPopNaviWindow;
    //    int pageIndexlist = 1;
//    int pageSize = 20;
//    Map<String, String> paramMap = new HashMap<>();
//    OkHttpPost mOkHttpPost;
    public ControllerTasklistView(Context context) {
        super(context);
        initView(context);
    }

    public ControllerTasklistView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ControllerTasklistView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        mActivity = (MainMapXJActivity) context;
        setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT));
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater.from(mContext).inflate(R.layout.view_taskinglist, this);
        if (!isInEditMode()) {
            setOnClickListener(this);
            findViewById(R.id.iv_close).setOnClickListener(this);
            findViewById(R.id.tv_suer).setOnClickListener(this);
            findViewById(R.id.tv_refuse).setOnClickListener(this);
            mTvtasksum = findViewById(R.id.tv_tasksum);
            listview = findViewById(R.id.listview);
//            mXlv_rr.setPullLoadEnable(false);//上拉刷新
//            mXlv_rr.setPullRefreshEnable(false);//下拉刷新
////            mXlv_rr.setXListViewListener(this);
////            mXlv_rr.setOnItemClickListener(this);
            mTradapter = new TasklistAdapter(mContext, mBeanList);
            mTradapter.setAListener(this);
            listview.setAdapter(mTradapter);

            //  initData();
        }
    }

    public void setdata(TasklistBean tasklistBean) {
        AppTool.getDictionaries(mContext);
        mTvtasksum.setText(tasklistBean.value.size() + "");
        mBeanList.clear();
        mBeanList.addAll(tasklistBean.value);
        mTradapter.setDefSelect(0);
        mTradapter.notifyDataSetChanged();
        new android.os.Handler().postDelayed(new Runnable() {
            public void run() {
                mTradapter.notifyDataSetChanged();
            }
        }, 2000);
    }
    public void setdata() {
        AppTool.getDictionaries(mContext);
        mTvtasksum.setText("0");
        mBeanList.clear();
        mTradapter.notifyDataSetChanged();
        new android.os.Handler().postDelayed(new Runnable() {
            public void run() {
                mTradapter.notifyDataSetChanged();
            }
        }, 2000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                setVisibility(GONE);
//                MapUtil.getInstance(mContext).setstardailyloc(mActivity.aMapLocation, mActivity, 0);// TODO
                break;
            case R.id.tv_refuse:
                if (AppTool.isNull(taskbean.S_MANGE_ID)) {
                    ToastUtils.showToast("请选择任务");
                    return;
                }
                mActivity.mCtaskreason.isShowLlreason(true, 0,taskbean);
                break;
            case R.id.tv_suer:
                if (taskbean == null) {
                    ToastUtils.showToast("请结束当前任务");
                    return;
                }
                if (AppTool.isNull(taskbean.S_MANGE_ID)) {
                    ToastUtils.showToast("请选择执行任务");
                    return;
                }
                if (MainMapXJActivity.isStartde) {
//                    MapUtil.getInstance(mContext).showTasknewDialog(mContext);
                    ToastUtils.showToast("请结束当前任务");
                } else {
                    MapUtil.getInstance(mContext).setstardailyloc(MainMapXJActivity.aMapLocation, mActivity, 1);
                }


                break;
        }
    }
    TasklistBean.ValueBean b;
    LatLng startLL,endLL;
    @Override
    public void getAData(TasklistBean.ValueBean b) {
        if (!MapUtil.isLegalLL(MainMapXJActivity.aMapLocation)){
            ToastUtils.showToast("定位失败，稍后请重是导航");
            return;
        }
        startLL= new LatLng(MainMapXJActivity.aMapLocation.getLatitude(),MainMapXJActivity.aMapLocation.getLongitude());
        if (AppTool.isNull(b.N_Y)||AppTool.isNull(b.N_X)){
            ToastUtils.showToast("终点坐标不正确");
            return;
        }
        this.b=b;
        Gps g= PositionUtil.gps84_To_Gcj02(Double.parseDouble(b.N_Y),Double.parseDouble(b.N_X));
        endLL=  new LatLng(g.getWgLat(),g.getWgLon());
        ShowPopNavi(mPopNaviWindow);

    }//
    public  TasklistBean.ValueBean taskbean = null;

    @Override
    public void selectData(TasklistBean.ValueBean b) {
        taskbean = null;
        taskbean= b;
        paiID();
    }
public void paiID(){
    MainMapXJActivity.mtvBean = taskbean;
    MainMapXJActivity.S_SJSB_ID = taskbean.S_SJSB_ID;
    MainMapXJActivity.STASKID = taskbean.S_MANGE_ID;
    MainMapXJActivity.S_MANGE_ID = taskbean.S_MANGE_ID;
}

    public PopWindowNavi ShowPopNavi( PopWindowNavi mPopNaviWindow){
        if (mPopNaviWindow == null) {
            mPopNaviWindow = new PopWindowNavi(mActivity);
            mPopNaviWindow.setLocinfo(startLL,endLL,null);
            mPopNaviWindow.showPop();
            return mPopNaviWindow;
        }
        mPopNaviWindow.setLocinfo(startLL,endLL,null);
        mPopNaviWindow.showPop();
        return mPopNaviWindow;
    }

}


