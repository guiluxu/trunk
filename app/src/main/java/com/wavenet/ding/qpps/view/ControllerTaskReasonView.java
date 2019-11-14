package com.wavenet.ding.qpps.view;

import android.content.Context;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.activity.MainMapXJActivity;
import com.wavenet.ding.qpps.bean.TasklistBean;
import com.wavenet.ding.qpps.utils.MaxTextLengthFilter;


public class ControllerTaskReasonView extends LinearLayout implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    public String W10082 = "W1008200001";
    public String S_STATUS = "W1006500005";
    Context mContext;
    MainMapXJActivity mActivity;
    TextView mTvtitle;
    LinearLayout mLlreason;
    EditText et_contentdeal;
    RadioGroup radioGroup;

    //    int pageIndexlist = 1;
//    int pageSize = 20;
//    Map<String, String> paramMap = new HashMap<>();
//    OkHttpPost mOkHttpPost;
    public ControllerTaskReasonView(Context context) {
        super(context);
        initView(context);
    }

    public ControllerTaskReasonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ControllerTaskReasonView(Context context, AttributeSet attrs, int defStyle) {
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
        LayoutInflater.from(mContext).inflate(R.layout.view_taskpaireason, this);
        if (!isInEditMode()) {
            setOnClickListener(this);
            findViewById(R.id.tv_cancelr).setOnClickListener(this);
            findViewById(R.id.tv_suerr).setOnClickListener(this);
            mLlreason = findViewById(R.id.ll_reason);
            mTvtitle = findViewById(R.id.version_describe);
            et_contentdeal = findViewById(R.id.et_contentdeal);
            et_contentdeal.setFilters(new InputFilter[]{new MaxTextLengthFilter(66)});

            radioGroup = findViewById(R.id.rg_tr);
            radioGroup.setOnCheckedChangeListener(this);
            isShowLlreason(false, 100);
            //  initData();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancelr:
                isShowLlreason(false, 100);
//                MapUtil.getInstance(mContext).setstardailyloc(mActivity.aMapLocation, mActivity, 0);// TODO
                break;

            case R.id.tv_suerr:
                if ("W1006500005".equals(S_STATUS)) {//派单任务列表的任务拒绝
                    mActivity.presenter.clickTaskPaiReason2(mContext, W10082, et_contentdeal.getText().toString(), b.S_MANGE_ID, S_STATUS);
//                    mActivity.presenter.clickTaskPaiReason1(MainMapXJActivity.STASKID, S_STATUS);
                } else {
//执行中的任务退单
                    mActivity.presenter.clickTaskPaiReason3(mContext, W10082, et_contentdeal.getText().toString(), MainMapXJActivity.S_MANGE_ID, S_STATUS);
//                    mActivity.presenter.clickTaskPaiReason1(MainMapXJActivity.STASKIDcurrent, S_STATUS);
                }


                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        RadioButton rb = findViewById(i);
        String str = rb.getText().toString();
        switch (str) {
            case "有其他紧急任务":
                W10082 = "W1008200001";
                break;
            case "设备不足":
                W10082 = "W1008200002";
                break;
            case "人手不足":
                W10082 = "W1008200003";
                break;
        }
    }

    public void isShowLlreason(boolean isShow, int state) {
        setVisibility(isShow ? VISIBLE : GONE);
        if (isShow) {
            initData(state);
        }
    }
    TasklistBean.ValueBean b;
    public void isShowLlreason(boolean isShow, int state,TasklistBean.ValueBean b) {
        this.b=b;
        setVisibility(isShow ? VISIBLE : GONE);
        if (isShow) {
            initData(state);
        }
    }

    public void initData(int state) {//state 0拒绝，1退单 100无效
//        W10082 拒绝退单 W1008200001 有其他紧急任务
//        W10082 拒绝退单 W1008200002 设备不足
//        W10082 拒绝退单 W1008200003 人手不足
        W10082 = "W1008200001";
        et_contentdeal.setText("");
        if (state == 0) {
            mTvtitle.setText(" 拒绝理由");
            S_STATUS = "W1006500005";
        } else if (state == 1) {
            mTvtitle.setText(" 退单理由");
            S_STATUS = "W1006500006";
        }

    }

    public void clickTaskPaiReason2() {//废弃
        if (mTvtitle.getText().toString().contains("拒绝")) {
            mActivity.presenter.clickTaskPaiReason2(mContext, W10082, et_contentdeal.getText().toString(), MainMapXJActivity.S_MANGE_ID, S_STATUS);

        } else {
            mActivity.presenter.clickTaskPaiReason3(mContext, W10082, et_contentdeal.getText().toString(), MainMapXJActivity.S_MANGE_ID, S_STATUS);


        }


    }

    public boolean isS_STATUS005() {
        return "W1006500005".equals(S_STATUS);
    }
}


