package com.wavenet.ding.qpps.demo.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dereck.library.base.BaseMvpActivity;
import com.dereck.library.utils.ToastUtils;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.demo.presenter.MainActivityRequestPresenter;
import com.wavenet.ding.qpps.demo.view.MainActivityRequestView;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends BaseMvpActivity<MainActivityRequestView, MainActivityRequestPresenter> implements MainActivityRequestView {


    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.button3)
    Button button3;
    Intent intent;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {

    }

    @Override
    public void requestData() {

    }

    @Override
    public void resultSuccess(String result) {

        ToastUtils.showToast(result + ".....");

    }

    @Override
    public void show() {
        showDialog();
    }

    @Override
    public void hide() {
        cancelDialog();
    }

    @Override
    public void resultFailure(String result) {

    }

    @Override
    protected MainActivityRequestPresenter createPresenter() {
        return new MainActivityRequestPresenter();
    }

    @OnClick({R.id.btn1, R.id.button, R.id.textView, R.id.button3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                presenter.clickRequest("12");
                break;
            case R.id.button:
                intent = new Intent(this, RecyclerViewActivity.class);
                startActivity(intent);
                break;
            case R.id.button3:
                intent = new Intent(this, ConstraintLayoutTestActivity.class);
                startActivity(intent);
                break;
        }
    }


}