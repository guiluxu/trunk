package com.wavenet.ding.qpps.view;

import android.content.Context;
import android.content.Intent;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.dereck.library.utils.NetUtils;
import com.dereck.library.utils.ToastUtils;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.activity.MainMapXJActivity;
import com.wavenet.ding.qpps.bean.ClasBean;
import com.wavenet.ding.qpps.bean.TaskBean;
import com.wavenet.ding.qpps.utils.AppAttribute;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.MapUtil;
import com.wavenet.ding.qpps.utils.MaxTextLengthFilter;
import com.wavenet.ding.qpps.utils.PositionUtil;
import com.wavenet.ding.qpps.utils.SPUtil;
import com.wavenet.ding.qpps.view.viewutils.CustomClickListener;

import org.devio.takephoto.model.TResult;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class ControllerTaskReportView extends LinearLayout implements View.OnClickListener,
        ControllerCameraView.ShowGalleryPhotoListen, ViewGalleryPhoto.NotifyDataSetChangedListen, ControllerCameraView.ShowExitViewListener{
    public TaskBean mTaskBean;
    Context mContext;
    MainMapXJActivity mActivity;
    TextView mTvclasbig, mTvclasmall, mTvuser, mTvtime;
    ControllerCameraView mCameraView;
    int isUn = 0;
//    ClasBean mClasBean;
    String clasbig;
    String classmall;
    int selectposition = 1000;
//    int options;
    String ReportTime = "";
    EditText et_contentdeal, mEtaddr;
    public ViewGalleryPhoto mViewGalleryPhoto;

    public ControllerTaskReportView(Context context) {
        super(context);
        initView(context);
    }

    public ControllerTaskReportView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ControllerTaskReportView(Context context, AttributeSet attrs, int defStyle) {
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
        LayoutInflater.from(mContext).inflate(R.layout.view_taskingreport, this);
        if (!isInEditMode()) {
            setOnClickListener(this);
            isUn = AppTool.isRegistered(true, this, isUn);
            findViewById(R.id.ll_back).setOnClickListener(this);
            mCameraView = findViewById(R.id.c_camer);
            mCameraView.setShowGalleryPhotoListen(this);
            mCameraView.setExitViewListener(this);
            mTvclasbig = findViewById(R.id.tv_clasbig);
            et_contentdeal = findViewById(R.id.et_contentdeal);
            et_contentdeal.setFilters(new InputFilter[]{new MaxTextLengthFilter(66)});
            mTvclasbig.setOnClickListener(this);
            mTvclasmall = findViewById(R.id.tv_clasmall);
            mTvclasmall.setOnClickListener(this);
            findViewById(R.id.tv_cancel).setOnClickListener(this);
//            findViewById(R.id.tv_suer).setOnClickListener(this);
           TextView tv_suer= findViewById(R.id.tv_suer);
            tv_suer.setOnClickListener( onClickL(tv_suer, 3000));
            mTvuser = findViewById(R.id.tv_user);
            mTvuser.setText(SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_MYNAME));
            mEtaddr = findViewById(R.id.et_addr);
            mEtaddr.setFilters(new InputFilter[]{new MaxTextLengthFilter(16)});
            mTvtime = findViewById(R.id.tv_time);
            mTvtime.setText(AppTool.getCurrentDate(AppTool.FORMAT_YMDHM));
            ReportTime = AppTool.getCurrentDate(AppTool.FORMAT_YMDHMS);
            mViewGalleryPhoto = findViewById(R.id.v_galleryphoto);
            mViewGalleryPhoto.setNotifyDataSetChangedListen(this);
            initData();
        }
    }

    private void initData() {
//        clasbiglist=AppTool.getReportclasbPopData();
//        classmalllist=AppTool.getReportclassPopData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                MapUtil.getInstance(mContext).showTaskreportDialog(mContext, this);
                break;
            case R.id.tv_cancel:
                MapUtil.getInstance(mContext).showTaskreportDialog(mContext, this);
                break;
            case R.id.tv_suer:
                break;
            case R.id.tv_clasbig:
                mActivity.presenter.clickRequestClasbig();

                break;
            case R.id.tv_clasmall:
                if (selectposition == 1000) {
                    ToastUtils.showToast("请选择大类");
                    return;
                }
                mActivity.presenter.clickRequestClassmall(clasbig);
//                EventBus.getDefault().post(MapUtil.TLH);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventBusCar(String state) {
        if (MapUtil.VEU.equals(state)) {
            AppTool.isRegistered(false, this, isUn);
        }
    }

    public void setPopClas(ClasBean mClasBean) {
        if (mClasBean.ui == 1) {
//            this.options = mClasBean.options;
//            this.mClasBean = mClasBean;
            setPopSelect(mClasBean);
        }
    }
    OptionsPickerView pvOptions;
    public void setPopSelect(final ClasBean mClasBean) {
        //                //   条件选择器
         pvOptions = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                if (options1>mClasBean.value.size()||options1==mClasBean.value.size()){
                    pvOptions.dismiss();
                    return;
                }

                if (mClasBean.options == 1) {
                    if (selectposition == options1) {
                        return;
                    }
                    selectposition = options1;
                    mTvclasbig.setText(mClasBean.value.get(options1).SVALUE);
                    clasbig = mClasBean.value.get(options1).SCORRESPOND;
                    mTvclasmall.setText("");
                } else if (mClasBean.options == 2) {

                    mTvclasmall.setText(mClasBean.value.get(options1).SVALUE);
//                    mTvclasmall.setText(mClasBean.value.get(100).SVALUE);//测试数组下标越界bug
                    classmall = mClasBean.value.get(options1).SCORRESPOND;

                }


            }
        }).build();
        pvOptions.setPicker(mClasBean.value);
        pvOptions.show();

    }

    public void cleanData() {
        mTvclasbig.setText("");
        mTvclasmall.setText("");
        mEtaddr.setText("");
        et_contentdeal.setText("");
        selectposition = 1000;
        mCameraView.ImaDatasclear();
        mTvtime.setText(AppTool.getCurrentDate(AppTool.FORMAT_YMDHM));
        ReportTime = AppTool.getCurrentDate(AppTool.FORMAT_YMDHMS);
    }

    public void showView(int type) {
        setVisibility(VISIBLE);
        mCameraView.setType(type);
        cleanData();
        if (MapUtil.isLegalLL(mActivity.aMapLocation)){
            mEtaddr.setText(mActivity.aMapLocation.getStreet()+mActivity.aMapLocation.getStreetNum());
        }else {
//            ToastUtils.showToast("");
        }
    }

    public void FileRequest(String f) {
        mCameraView.FileRequest(f);
    }

    public void notifyData(TResult result) {
        mCameraView.notifyData(result);
    }

    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        mCameraView.onActivityResult(requestCode, resultCode, data);
    }
    private CustomClickListener onClickL(View v, long timeInterval) {
        CustomClickListener mCustomClickListener = new CustomClickListener(timeInterval, v) {
            @Override
            protected void onSingleClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_suer:
                       if (!isSubmit()){
                           return;
                       }
                        //把高德经纬度转换为84
                        MainMapXJActivity.g = PositionUtil.gcj_To_Gps84(mActivity.aMapLocation.getLatitude(), mActivity.aMapLocation.getLongitude());
                        String user = SPUtil.getInstance(mContext).getStringValue(SPUtil.USERNO);
                        MainMapXJActivity.S_MANGE_ID = System.currentTimeMillis() + user;
                        mTaskBean = new TaskBean(MainMapXJActivity.S_MANGE_ID, mTvclasbig.getText().toString(), mTvclasmall.getText().toString(), user, ReportTime, MainMapXJActivity.g.getWgLat(), MainMapXJActivity.g.getWgLon());
                        mTaskBean.addr = mEtaddr.getText().toString();
                        mTaskBean.beizhu = et_contentdeal.getText().toString().trim();
//        事件编号  记录编号  问题类别  问题类型   上报人  上报时间
                        mActivity.presenter.clickRequestUPTask(mContext ,et_contentdeal.getText().toString().trim(), MainMapXJActivity.S_MANGE_ID, AppAttribute.F.getXJID(mContext), clasbig, classmall, user, ReportTime, MainMapXJActivity.g.getWgLon(), MainMapXJActivity.g.getWgLat(), mEtaddr.getText().toString());
                        break;
                }
            }

            @Override
            protected void onFastClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_suer:
                        if (!isSubmit()){
                            return;
                        }
                        ToastUtils.showToast( "正在提交,请稍后重试");
                        break;
                }
            }

        };
        return mCustomClickListener;
    }
    public boolean isSubmit(){
        if (!NetUtils.isNetworkConnected()) {
            ToastUtils.showToast("网络未连接，请检查网络！");
            return false;
        }
        if (AppTool.isNull(mTvclasbig.getText().toString()) || AppTool.isNull(mTvclasmall.getText().toString())) {
            ToastUtils.showToast("请选择问题类型");
            return false;
        }
        if (AppTool.isNull(mEtaddr.getText().toString())) {
            ToastUtils.showToast("请输入地址");
            return false;
        }
        if (!mCameraView.getishavefile()) {
            ToastUtils.showToast("请选择上报的图片");
            return false;
        }
        return true;
    }
            @Override
    public void ShowGalleryPhotoListen(boolean isShow, int position) {
        mViewGalleryPhoto.setData(position, mCameraView.imaDatas);
    }

    @Override
    public void notifyDataSetChangedListen(int p) {
        mCameraView.notifyData(p);
    }

    @Override
    public void showExit() {
        setVisibility(GONE);
        cleanData();
    }
}
