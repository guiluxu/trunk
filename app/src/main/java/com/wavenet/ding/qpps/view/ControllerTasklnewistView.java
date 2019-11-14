package com.wavenet.ding.qpps.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.activity.MainMapXJActivity;
import com.wavenet.ding.qpps.adapter.TasklistAdapter;
import com.wavenet.ding.qpps.bean.TasklistBean;
import com.wavenet.ding.qpps.utils.MapUtil;

import java.util.ArrayList;


public class ControllerTasklnewistView extends LinearLayout implements View.OnClickListener {
    Context mContext;
    MainMapXJActivity mActivity;

    //    private LoadingWaitView mLoadingWaitView;
    ListView listview;
    TasklistAdapter mTradapter;
    ArrayList<TasklistBean.ValueBean> mBeanList = new ArrayList<>();

    public ControllerTasklnewistView(Context context) {
        super(context);
        initView(context);
    }

    public ControllerTasklnewistView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ControllerTasklnewistView(Context context, AttributeSet attrs, int defStyle) {
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
            findViewById(R.id.tv_cancel).setOnClickListener(this);
            findViewById(R.id.tv_suer).setOnClickListener(this);
//            mLoadingWaitView =findViewById(R.id.loadingWaitView);
            listview = findViewById(R.id.listview);
            setdata();
            mTradapter = new TasklistAdapter(mContext, mBeanList);
            listview.setAdapter(mTradapter);
            //  initData();
        }
    }

    public void setdata() {
//        for (int i = 0; i < 10; i++) {
//            mBeanList.add(new TasklistBean());
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
//                EventBus.getDefault().post(MapUtil.TLH);
                setVisibility(View.GONE);
                break;
            case R.id.tv_suer:
//                EventBus.getDefault().post(MapUtil.TLH);
                MapUtil.getInstance(mContext).showTasknewDialog(mContext);
                break;
        }
    }
}


