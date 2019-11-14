package com.wavenet.ding.qpps.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dereck.library.utils.ToastUtils;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.activity.AdminSubmitActivity;
import com.wavenet.ding.qpps.api.QPSWApplication;
import com.wavenet.ding.qpps.fragment.FourthFragment;
import com.wavenet.ding.qpps.interf.CallBackMap;
import com.wavenet.ding.qpps.utils.AppUtils;
import com.wavenet.ding.qpps.utils.MapUtil;
import com.wavenet.ding.qpps.utils.SPUtil;


public class ControllerAdminUIView extends LinearLayout implements View.OnClickListener {
    Context mContext;
    LinearLayout mLltrackstart, mLltracking;
    ImageView mIvshanghai, mIvDoing, mIvPhoto, mIvLocation, mIvtuceng, mTvrefresh;
    TextView mTvplus, mTvminus, mTvtaskreport, mTvtaskend;
    int isUn = 0;
    FourthFragment mFourthFragment;
    CallBackMap mCallBackMap;
    public  ImageView  mIvgensui, iv_upload;
    String rolename;
    public ControllerAdminUIView(Context context) {
        super(context);
        initView(context);
    }

    public ControllerAdminUIView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ControllerAdminUIView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.view_adminui, this);

        if (!isInEditMode()) {

            mIvtuceng = findViewById(R.id.iv_tuceng);
            mIvtuceng.setOnClickListener(this);
            mIvshanghai = findViewById(R.id.iv_shanghai);
            mIvshanghai.setOnClickListener(this);
            mIvPhoto = findViewById(R.id.iv_photo);
            mIvPhoto.setOnClickListener(this);
            mIvLocation = findViewById(R.id.iv_location);
            mIvLocation.setOnClickListener(this);
            mTvplus = findViewById(R.id.tv_plus);
            mTvplus.setOnClickListener(this);

            mTvminus = findViewById(R.id.tv_minus);
            mTvminus.setOnClickListener(this);
            mTvrefresh = findViewById(R.id.iv_refresh);
            mTvrefresh.setOnClickListener(this);

            mIvgensui = findViewById(R.id.iv_gensui);
            mIvgensui.setSelected(false);// 默认不跟随你
            mIvgensui.setOnClickListener(this);
            iv_upload = findViewById(R.id.iv_upload);
            iv_upload.setOnClickListener(this);
             rolename= SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_ROLE);
            if(rolename.equals("超级管理员")||rolename.contains("养护单位管理员")||"一般用户".equals(rolename)){
                iv_upload.setVisibility(GONE);
            }
            if ("一般用户".equals(rolename)){
                mTvrefresh.setVisibility(GONE);
            }
            initData();
        }
    }

    protected void initData() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_tuceng:
                if (mFourthFragment.mainMapImageLayer!=null&&mFourthFragment.mainMapImageLayer.getLoadStatus() != LoadStatus.LOADED) {
                    ToastUtils.showToast("业务图层加载失败，重新加载请稍后");
                    mFourthFragment.mainMapImageLayer = MapUtil.getInstance(mContext).addMapService(mFourthFragment.mArcGISMapVector, mFourthFragment.mAddLayerListen, true);
                    return;
                }
                mFourthFragment.mLegend.showOrHide("");
                break;
            case R.id.iv_shanghai:
//        mActivity. mMapView.getMapScale();
//        LogUtils.e("ddddddddddddddd",mActivity. mMapView.getMapScale()+"") ;
                MapUtil.getInstance(mContext).setshanghai(mFourthFragment.mMapView);

                break;
            case R.id.iv_location:
                if (mCallBackMap != null&& QPSWApplication.Locpoint!=null) {
                    mFourthFragment.mMapView.setViewpointCenterAsync(QPSWApplication.Locpoint, 10000);
                }
                break;
            case R.id.tv_plus:

//        ToastUtils.showToast(    mActivity.mMapView.getMapScale()+"");
                mFourthFragment.mMapView.setViewpointScaleAsync(mFourthFragment.mMapView.getMapScale() * 0.5);
                break;
            case R.id.tv_minus:
//        ToastUtils.showToast(    mActivity.mMapView.getMapScale()+"");
                mFourthFragment.mMapView.setViewpointScaleAsync(mFourthFragment.mMapView.getMapScale() / 0.5);
                break;
            case R.id.iv_refresh:
                if (mFourthFragment.mLegend.mIvpeoplexj.isSelected() && mFourthFragment.isOnclickPeopleXJ) {
                    mFourthFragment.isOnclickPeopleXJ = false;
//                    String filterStr= AppUtils.getPopleFilteXJ(mContext);
//                    mFourthFragment.presenter.requestPeople(3011, filterStr);
                    String filterStr = AppUtils.getParamPeople(mContext, mContext.getResources().getString(R.string.xj_people));
                    mFourthFragment.presenter.requestPeople2(10081, filterStr);
                }
                if (mFourthFragment.mLegend.mIvpeopleyh.isSelected() && mFourthFragment.isOnclickPeopleYH) {
                    mFourthFragment.isOnclickPeopleYH = false;
//                    String filterStr= AppUtils.getPopleFilteYH(mContext);
//                    mFourthFragment.presenter.requestPeople(3021, filterStr);
                    String filterStr = AppUtils.getParamPeople(mContext, mContext.getResources().getString(R.string.yh_people));
                    mFourthFragment.presenter.requestPeople2(10091, filterStr);
                }
                break;
            case R.id.iv_gensui:
                mIvgensui.setSelected(!mIvgensui.isSelected());
                break;
            case R.id.iv_upload:

                Intent intent = new Intent(mContext, AdminSubmitActivity.class);
                mContext.startActivity(intent);
                break;
        }
    }

    public void setfragment(FourthFragment mFourthFragment) {
        this.mFourthFragment = mFourthFragment;

    }


    public void setCallBackMap(CallBackMap mCallBackMap) {
        this.mCallBackMap = mCallBackMap;
    }


}
