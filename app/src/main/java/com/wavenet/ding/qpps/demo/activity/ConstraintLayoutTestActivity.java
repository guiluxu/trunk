package com.wavenet.ding.qpps.demo.activity;

import com.dereck.library.base.BaseMvpActivity;
import com.dereck.library.base.BaseMvpPersenter;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.demo.presenter.RecyclerViewRequestPresenter;

/**
 * Created by ding on 2018/7/20.
 */
//给子控件添加父类约束   然后百分比设置   主要靠拖拽
public class ConstraintLayoutTestActivity extends BaseMvpActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_constraintayout_demo;
    }

    @Override
    public void init() {

    }

    @Override
    public void requestData() {

    }

    @Override
    protected BaseMvpPersenter createPresenter() {
        return new RecyclerViewRequestPresenter();
    }
}
