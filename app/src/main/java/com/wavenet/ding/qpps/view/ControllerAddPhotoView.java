package com.wavenet.ding.qpps.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.dereck.library.utils.ToastUtils;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.activity.MainMapXJActivity;
import com.wavenet.ding.qpps.bean.ClasBean;
import com.wavenet.ding.qpps.bean.TaskBean;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.MapUtil;
import com.wavenet.ding.qpps.utils.SPUtil;

import org.devio.takephoto.model.TResult;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ControllerAddPhotoView extends LinearLayout implements View.OnClickListener {
    public TaskBean mTaskBean;
    Context mContext;
    MainMapXJActivity mActivity;
    Map<String, String> paramMap = new HashMap<>();
    TextView mTvclasbig, mTvclasmall, mTvuser, mTvtime;
    ControllerCameraView mCameraView;
    ArrayList<String> clasbiglist = new ArrayList<>();
    ArrayList<ArrayList<ClasBean>> classmalllist = new ArrayList<>();
    //    OkHttpPost mOkHttpPost;
    int isUn = 0;
    ClasBean mClasBean;
    String clasbig;
    String classmall;
    int selectposition = 100;
    int options;
    String UTCTime = "T";

    //    OkHttpPost mOkHttpPost;
    public ControllerAddPhotoView(Context context) {
        super(context);
        initView(context);
    }

    public ControllerAddPhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ControllerAddPhotoView(Context context, AttributeSet attrs, int defStyle) {
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
        LayoutInflater.from(mContext).inflate(R.layout.view_addphoto, this);
        if (!isInEditMode()) {
            isUn = AppTool.isRegistered(true, this, isUn);
            mCameraView = findViewById(R.id.c_camer);
            mTvclasbig = findViewById(R.id.tv_clasbig);
            mTvclasbig.setOnClickListener(this);
            mTvclasmall = findViewById(R.id.tv_clasmall);
            mTvclasmall.setOnClickListener(this);
            findViewById(R.id.tv_cancel).setOnClickListener(this);
            findViewById(R.id.tv_suer).setOnClickListener(this);
            mTvuser = findViewById(R.id.tv_user);
            mTvuser.setText(SPUtil.getInstance(mContext).getStringValue(SPUtil.USERNO));
            mTvtime = findViewById(R.id.tv_time);
            mTvtime.setText(AppTool.getCurrentDate(AppTool.FORMAT_YMDHMS));
            UTCTime = AppTool.getCurrenttimeT();
            initData();
        }
    }

    private void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                cleanData();
                setVisibility(View.GONE);
//                EventBus.getDefault().post(MapUtil.TLH);
                break;

            case R.id.tv_clasbig:
                mActivity.presenter.clickRequestClasbig();

                break;
            case R.id.tv_clasmall:
                if (selectposition == 100) {
                    ToastUtils.showToast("请选择大类");
                    return;
                }
                mActivity.presenter.clickRequestClassmall(clasbig);
//                EventBus.getDefault().post(MapUtil.TLH);
                break;
            case R.id.tv_suer:
                if (AppTool.isNull(mTvclasbig.getText().toString()) || AppTool.isNull(mTvclasmall.getText().toString())) {
                    ToastUtils.showToast("请选择问题类型");
                    return;
                }
                if (!mCameraView.getishavefile()) {
                    ToastUtils.showToast("请选择上报的图片");
                    return;
                }
                String user = SPUtil.getInstance(mContext).getStringValue(SPUtil.USERNO);
                MainMapXJActivity.S_MANGE_ID = System.currentTimeMillis() + user;
//        事件编号  记录编号  问题类别  问题类型   上报人  上报时间
                mActivity.presenter.clickRequestTaskAdd(MainMapXJActivity.S_MANGE_ID, MainMapXJActivity.S_RECODE_ID, clasbig, classmall, user, UTCTime);
//                EventBus.getDefault().post(MapUtil.AMI);//TODO
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventBusCar(String state) {
        if (MapUtil.VEU.equals(state)) {
            AppTool.isRegistered(false, this, isUn);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventBusCar(ClasBean mClasBean) {
        if (mClasBean.ui == 2) {
            this.options = mClasBean.options;
            this.mClasBean = mClasBean;
            setPopSelect(mClasBean.value);
        }
    }

    public void setPopSelect(final ArrayList<ClasBean.ValueBean> popall) {
        //                //   条件选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {

                if (options == 1) {
                    if (selectposition == options1) {
                        return;
                    }
                    selectposition = options1;
                    mTvclasbig.setText(mClasBean.value.get(options1).SVALUE);
                    clasbig = mClasBean.value.get(options1).SCORRESPOND;
                    mTvclasmall.setText("");
                } else if (options == 2) {
                    mTvclasmall.setText(mClasBean.value.get(options1).SVALUE);
                    classmall = mClasBean.value.get(options1).SCORRESPOND;
                }


            }
        }).build();
        pvOptions.setPicker(popall);
        pvOptions.show();

    }

    public void cleanData() {
        mTvclasbig.setText("");
        mTvclasmall.setText("");
        selectposition = 1000;
        mCameraView.ImaDatasclear();
    }

    public void FileRequest(String f) {
        mCameraView.FileRequest(f);
    }

    public void notifyData(TResult result) {
        mCameraView.notifyData(result);
    }
}
