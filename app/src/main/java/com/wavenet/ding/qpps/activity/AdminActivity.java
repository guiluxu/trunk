package com.wavenet.ding.qpps.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.dereck.library.base.BaseMvpActivity;
import com.dereck.library.base.BaseMvpPersenter;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.adapter.AdminPagerAdapter;
import com.wavenet.ding.qpps.fragment.FifthFragment;
import com.wavenet.ding.qpps.fragment.FourthFragment;
import com.wavenet.ding.qpps.fragment.OneFragment;
import com.wavenet.ding.qpps.fragment.SecondFragment;
import com.wavenet.ding.qpps.fragment.ThirdFragment;
import com.wavenet.ding.qpps.utils.MapUtil;
import com.wavenet.ding.qpps.utils.SPUtil;
import com.wavenet.ding.qpps.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zoubeiwen on 2018/8/29.
 */

public class AdminActivity extends BaseMvpActivity {
    public OneFragment mOneFragment;
    public SecondFragment mSecondFragment;
    public ThirdFragment mThirdFragment;
    public FourthFragment mFourthFragment;
    public FifthFragment mFifthFragment;
    private RadioGroup mRg;
    private RadioButton rb1,rb2,rb4,rb3;
    private CustomViewPager mCustomViewPager;
    AdminPagerAdapter mAdminPagerAdapter;
    private List<Fragment> fragments = new ArrayList<>();
    //记录第一次按返回键的时间
    private long firstTime = 0;
    public int p = 2;
//public int roleid=0;//0管理员，1调度员，2养护单位管理员
    @Override
    public int getLayoutId() {
        return R.layout.activity_admin;
    }

    @Override
    public void init() {
        MapUtil.init(this);
        String  role= SPUtil.getInstance(this).getStringValue(SPUtil.APP_ROLE);

        mRg = findViewById(R.id.rgadmin);
        rb1 = findViewById(R.id.rbadmin1);
        rb2 = findViewById(R.id.rbadmin2);
        rb3 = findViewById(R.id.rbadmin3);
        rb4 = findViewById(R.id.rbadmin4);

        mCustomViewPager = findViewById(R.id.vp);
        mCustomViewPager.setOffscreenPageLimit(3);

            mOneFragment = OneFragment.newInstance();
            fragments.add(mOneFragment);
        mSecondFragment = SecondFragment.newInstance();
        fragments.add(mSecondFragment);


        mFourthFragment = FourthFragment.newInstance(AdminActivity.this);
        fragments.add(mFourthFragment);

          mThirdFragment = ThirdFragment.newInstance();
        fragments.add(mThirdFragment);


        mFifthFragment = FifthFragment.newInstance();
        fragments.add(mFifthFragment);
        mAdminPagerAdapter = new AdminPagerAdapter(getSupportFragmentManager(), fragments);
        mCustomViewPager.setAdapter(mAdminPagerAdapter);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String type = bundle.getString("type");
            if ("1".equals(type)) {//从jpush跳转进来，需要定位到第2个页面
                p = 1;
                rb2.setChecked(true);
                mCustomViewPager.setCurrentItem(1);
            }
        }

        p = 2;
        rb4.setChecked(true);
        mCustomViewPager.setCurrentItem(2);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        rb2.setChecked(true);
        mCustomViewPager.setCurrentItem(1);
    }

    @Override
    public void requestData() {
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int radioButtonId) {
                RadioButton rb = findViewById(radioButtonId);
                String str = rb.getText().toString();
                switch (str) {
                    case "首 页":
                        p = 0;
                        break;
                    case "巡 查":
                        p = 1;
                        break;
                    case "养 护":
                        p = 3;
                        break;
                    case "设 施":
                        p = 2;

//
                        break;
                    case "我 的":
                        p = 4;
//
                        break;
                }
                mCustomViewPager.setCurrentItem(p);
            }
        });
    }

    @Override
    protected BaseMvpPersenter createPresenter() {
        return new BaseMvpPersenter() {
        };
    }

    //防止不小心按到返回键
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (mFourthFragment.mLoadingWaitView.getVisibility() == View.VISIBLE) {
                    mFourthFragment.mLoadingWaitView.setVisibility(View.GONE);
                } else {
                    if (p == 1) {
                        exit();
                    } else if (p == 3) {
                        if (mThirdFragment.onBackPressed()) return true;
                        exit();
                    } else {
                        exit();
                    }

                }

                break;
//                if (mOneFragment.waitingDialog.isShowing()) {
//                    mOneFragment.cancelDialog();
//                } else if (mSecondFragment.waitingDialog.isShowing()) {
//                    mSecondFragment.cancelDialog();
//                } else if (mThirdFragment.waitingDialog.isShowing()) {
//                    mThirdFragment.cancelDialog();
//                } else {

//                }


        }
        return true;
    }

    public void exit() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {                                         //如果两次按键时间间隔大于2秒，则不退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            firstTime = secondTime;//更新firstTime
        } else {                                                    //两次按键小于2秒时，退出应用
            finish();
        }
    }

}
