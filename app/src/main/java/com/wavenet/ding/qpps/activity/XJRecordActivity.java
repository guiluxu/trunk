package com.wavenet.ding.qpps.activity;


import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.dereck.library.base.BaseMvpActivity;
import com.dereck.library.refreshrecyclerview.pulltorefresh.PullToRefreshRecyclerView;
import com.dereck.library.utils.SPUtils;
import com.dereck.library.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.adapter.TasklistRecordAdapter;
import com.wavenet.ding.qpps.adapter.TasklistRecordAdapter1;
import com.wavenet.ding.qpps.bean.ListBean;
import com.wavenet.ding.qpps.interf.CallBackMap;
import com.wavenet.ding.qpps.mvp.p.XJRecordRequestPresenter;
import com.wavenet.ding.qpps.mvp.v.XJActivityRequestView;
import com.wavenet.ding.qpps.utils.AppAttribute;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.LogUtils;
import com.wavenet.ding.qpps.view.ControllerXJRecordSelecetTop;
import com.wavenet.ding.qpps.view.DropdownButton;

import org.devio.takephoto.model.TImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class XJRecordActivity extends BaseMvpActivity<XJActivityRequestView, XJRecordRequestPresenter>
        implements CallBackMap, View.OnClickListener, XJActivityRequestView, PullToRefreshRecyclerView.OnRefreshAndLoadMoreListener, DropdownButton.OnDropItemSelectListener, TasklistRecordAdapter1.OnItemClickListener {
    PullToRefreshRecyclerView wxRecyclerview;
    //    ListView mLvrecord;
    TasklistRecordAdapter1 mTradapter;
    List<ListBean.PATROLMANAGEMENTBean> mBeanList = new ArrayList<>();

    ControllerXJRecordSelecetTop SelecetTop;
    Map<String, Object> filterMap = new HashMap<>();
    //时间
    String tabkey1 = "T_IN_DATE";
    //级别
    String tabkey2 = "S_EMERGENCY";
    //来源
    String tabkey3 = "S_SOURCE";
    //状态
    String tabkey4 = "S_STATUS";

    @Override
    public int getLayoutId() {
        return R.layout.activity_xjrecord;
    }

    @Override
    public void init() {
        setTitle("历史应急处置记录");
        SelecetTop = findViewById(R.id.c_mstop);
        SelecetTop.setOnDropItemSelectListener(this);
//        mLvrecord = findViewById(R.id.lv_record);
        wxRecyclerview = findViewById(R.id.listview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        wxRecyclerview.setLayoutManager(linearLayoutManager);
        wxRecyclerview.setPullRefreshEnabled(false);
        wxRecyclerview.setLoadMoreEnabled(false);
        wxRecyclerview.setRefreshAndLoadMoreListener(this);
        mTradapter = new TasklistRecordAdapter1(this, mBeanList);
        mTradapter.setOnItemClickListener(this);
        wxRecyclerview.setAdapter(mTradapter);
//        mLvrecord.setAdapter(mTradapter);
        findViewById(R.id.tv_suer).setOnClickListener(this);
        findViewById(R.id.tv_cancel).setOnClickListener(this);
    }

    @Override
    public void requestData() {
//        fifterStr = " and T_CREATE ge " + AppTool.getBeforeData(0, 7, AppTool.FORMAT_YMD) + "T00:00:00+08:00";
        clickRequestTasklist();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_suer:
                if (AppTool.isNull(TasklistRecordAdapter.id)) {
                    ToastUtils.showToast("请选择执行任务");
                    return;
                }
                if (AppTool.isNull(AppAttribute.F.getXJID(this))) {
                    setResult(1);
                    finish();
                } else {
                    final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setCancelable(false);
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                    Window window = alertDialog.getWindow();
                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 有白色背
                    window.setContentView(R.layout.dialog_tips);
                    TextView tv_content = window.findViewById(R.id.tv_content);
                    tv_content.setText("您确定结束当前巡检，去新的任务点吗?");
                    window.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                            presenter.RequestEndTask(AppAttribute.F.getXJID(XJRecordActivity.this), AppTool.getCurrentDate(AppTool.FORMAT_YMDHMS));
//                    setResult(1);
//                     finish();
                        }
                    });
                    window.findViewById(R.id.tv_no).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                }


                break;
        }
    }

    @Override
    public void onClick(String mOnClick) {

    }

    @Override
    public void onItemClick(View view, int position) {
        if (position >= 0) {
            Intent intent = new Intent(this, XJRecordDetailsActivity.class);
            intent.putExtra("mBeanList", mBeanList.get(position));
            startActivity(intent);
        }
    }

    @Override
    public void onRecyclerViewRefresh() {

        clickRequestTasklist();
    }

    @Override
    public void onRecyclerViewLoadMore() {
        clickRequestTasklist();
    }

    @Override
    public void onDropItemSelect(int Postion, int tab) {
        String valuestr = SelecetTop.getItmestr(Postion, tab);
        StringBuilder sb = new StringBuilder();

        if (tab == 1) {
            if (AppTool.isNull(valuestr)) {
                filterMap.remove(tabkey1);
            } else {
                filterMap.put(tabkey1, valuestr);
            }
        } else if (tab == 2) {
            if (AppTool.isNull(valuestr)) {
                filterMap.remove(tabkey3);
            } else {
                filterMap.put(tabkey3, valuestr + "");
            }
        } else if (tab == 3) {
            if (AppTool.isNull(valuestr)) {
                filterMap.remove(tabkey4);
            } else {
                filterMap.put(tabkey4, valuestr + "");
            }
        } else if (tab == 4) {
            if (AppTool.isNull(valuestr)) {
                filterMap.remove(tabkey2);
            } else {
                filterMap.put(tabkey2, valuestr + "");
            }
        }
        filterMap.put("S_DELETE", "1");
        filterMap.put("PAGE", 1);
        filterMap.put("PAGESIZE", 100);
        filterMap.put("S_MANGE_MAN", SPUtils.get("user", ""));

        presenter.clickRequestTasklist(filterMap);
    }

    public void clickRequestTasklist() {
        filterMap.put("S_DELETE", "1");
        filterMap.put("PAGE", 1);
        filterMap.put("PAGESIZE", 100);
        filterMap.put("S_MANGE_MAN", SPUtils.get("user", ""));

        filterMap.put(tabkey1, AppTool.getBeforeData(0, 7, AppTool.FORMAT_YMDHMS));
        presenter.clickRequestTasklist(filterMap);
    }

    @Override
    public void requestSuccess(int resultid, String result) {
        LogUtils.e("ddddddddddd", result);

        if (!AppTool.isNull(result) && !result.contains("error")) {
            switch (resultid) {
                case 1:
                    try {
                        ListBean tasklistBean = new Gson().fromJson(result, ListBean.class);

                        if (tasklistBean.PATROL_MANAGEMENT != null) {
                            wxRecyclerview.setVisibility(View.VISIBLE);
                            mBeanList.clear();
                            mBeanList.addAll(tasklistBean.PATROL_MANAGEMENT);
                            mTradapter.notifyDataSetChanged();
                        } else {

                            wxRecyclerview.setVisibility(View.GONE);

                        }

//                    if (tasklistBean == 10) {
//                        pageIndexlist = pageIndexlist + 10;
//                        if (wxRecyclerview.isLoading()) {
//                            wxRecyclerview.loadMoreComplete();
//                            //如果没有更多数据就显示没有更多数据提示
//                        }
//                        wxRecyclerview.setNoMoreDate(false);
//                    } else {
//                        wxRecyclerview.setNoMoreDate(true);
//                    }

                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();

                        wxRecyclerview.setVisibility(View.GONE);
                    }
                    break;
//        if (pageIndexlist == 1) {
////            if (size==0){
////                mNView.setShow(0);
//////                                mNView.setDataResult(isFirstNo);
////            }else {
////                mNView.setShow(2);
////            }
//            mBeanList.clear();
//            wxRecyclerview.stopRefresh();
//        }
//        mBeanList.addAll(beanList);
//        if (mBeanList.size() < size) {
//            pageIndexlist++;
//            wxRecyclerview.setPullLoadEnable(true);
//        } else {
//            wxRecyclerview.setPullLoadEnable(false);
//        }
//        //判断该操作是下拉刷新还是上拉加载更多
//        if (wxRecyclerview.isLoading()) {
//            wxRecyclerview.loadMoreComplete();
//            //如果没有更多数据就显示没有更多数据提示
//            if (list == null || list.size() == 0) {
//                wxRecyclerview.setNoMoreDate(true);
//            }
//        } else if (wxRecyclerview.isRefreshing()) {
//            wxRecyclerview.refreshComplete();
//        }
                case 2:
                    setResult(1);
                    finish();
                    break;
            }
        } else {
            ToastUtils.showToast(result);
        }

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void requestFailure(int resultid, String result) {
        switch (resultid) {
            case 2:
                ToastUtils.showToast("新任务执行失败，请重新开始执行");
                break;
        }
    }

    @Override
    public void requestSuccess(int resultid, String result, boolean b) {

    }

    @Override
    public void requestFailure(int resultid, String result, boolean b) {

    }

    @Override
    public void requestFailure(int resultid, String result, Map<String, Object> map, ArrayList<TImage> images, String videoPath, String audioPath) {

    }

    @Override
    protected XJRecordRequestPresenter createPresenter() {
        return new XJRecordRequestPresenter();
    }

}