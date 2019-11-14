package com.wavenet.ding.qpps.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.adapter.TaskRefuselistAdapter;
import com.wavenet.ding.qpps.bean.TasklistBean;

import java.util.ArrayList;


public class ControllerTaskRefuselistView extends LinearLayout implements View.OnClickListener {
    Context mContext;
    TaskRefuselistAdapter mTradapter;
    ListView listview;
    ArrayList<TasklistBean.ValueBean> mBeanList = new ArrayList<>();

    public ControllerTaskRefuselistView(Context context) {
        super(context);
        initView(context);
    }

    public ControllerTaskRefuselistView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ControllerTaskRefuselistView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT));
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater.from(mContext).inflate(R.layout.view_taskrefuselist, this);
        if (!isInEditMode()) {
            setOnClickListener(this);
            listview = findViewById(R.id.wctv_refuselist);
            mTradapter = new TaskRefuselistAdapter(mContext, mBeanList);
            listview.setAdapter(mTradapter);
        }
    }

    public void setdata(TasklistBean tasklistBean) {
        mBeanList.clear();
        mBeanList.addAll(tasklistBean.value);
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

        }
    }

}


