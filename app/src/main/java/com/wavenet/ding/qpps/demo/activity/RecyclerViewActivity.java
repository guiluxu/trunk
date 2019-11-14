package com.wavenet.ding.qpps.demo.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;

import com.dereck.library.base.BaseMvpActivity;
import com.dereck.library.refreshrecyclerview.base.adapter.BaseRecyclerViewAdapter;
import com.dereck.library.refreshrecyclerview.pulltorefresh.PullToRefreshRecyclerView;
import com.dereck.library.utils.GsonUtils;
import com.dereck.library.utils.ToastUtils;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.demo.adapter.TrainingActivityRecyclerViewAdapter;
import com.wavenet.ding.qpps.demo.bean.TainingBean;
import com.wavenet.ding.qpps.demo.presenter.RecyclerViewRequestPresenter;
import com.wavenet.ding.qpps.demo.view.RecyclerViewActivityRequestView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ding on 2018/7/16.
 */

public class RecyclerViewActivity extends BaseMvpActivity<RecyclerViewActivityRequestView, RecyclerViewRequestPresenter> implements RecyclerViewActivityRequestView, PullToRefreshRecyclerView.OnRefreshAndLoadMoreListener {
    @BindView(R.id.wxRecyclerview)
    PullToRefreshRecyclerView wxRecyclerview;
    @BindView(R.id.button2)
    Button button2;
    TrainingActivityRecyclerViewAdapter trainingActivityRecyclerViewAdapter;
    //页码数
    int page = 1;
    List<TainingBean.RECBean> allList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_recyclerview;
    }

    @Override
    public void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        wxRecyclerview.setLayoutManager(linearLayoutManager);
        wxRecyclerview.setPullRefreshEnabled(true);
        wxRecyclerview.setLoadMoreEnabled(true);
        wxRecyclerview.setRefreshAndLoadMoreListener(this);

    }

    @Override
    public void requestData() {
        presenter.clickRequest(page + "");
    }

    @Override
    protected RecyclerViewRequestPresenter createPresenter() {
        return new RecyclerViewRequestPresenter();
    }

    //刷新
    @Override
    public void onRecyclerViewRefresh() {
        page = 1;
        requestData();
    }

    //加载更多
    @Override
    public void onRecyclerViewLoadMore() {
        page++;
        requestData();
    }

    @Override
    public void resultSuccess(String result) {
        TainingBean tainingBean = GsonUtils.getObject(result, TainingBean.class);
        List<TainingBean.RECBean> list = tainingBean.getREC();
        //先做数据拼接
        if (list != null) {
            if (page == 1) {
                allList.clear();
                allList.addAll(list);
            } else {
                allList.addAll(list);
            }
            if (trainingActivityRecyclerViewAdapter == null) {
                trainingActivityRecyclerViewAdapter = new TrainingActivityRecyclerViewAdapter(activity, allList);
                wxRecyclerview.setAdapter(trainingActivityRecyclerViewAdapter);
                trainingActivityRecyclerViewAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        ToastUtils.showToast(position + "");
                    }
                });
            } else {
                //判断该操作是下拉刷新还是上拉加载更多
                if (wxRecyclerview.isLoading()) {
                    wxRecyclerview.loadMoreComplete();
                    //如果没有更多数据就显示没有更多数据提示
                    if (list == null || list.size() == 0) {
                        wxRecyclerview.setNoMoreDate(true);
                    }
                } else if (wxRecyclerview.isRefreshing()) {
                    wxRecyclerview.refreshComplete();
                }
            }
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void resultFailure(String result) {

    }

    @OnClick(R.id.button2)
    public void onClick() {

    }
}
