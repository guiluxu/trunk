package com.wavenet.ding.qpps.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.dereck.library.base.BaseMvpActivity;
import com.dereck.library.base.BaseMvpPersenter;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.adapter.AdminPagerAdapter;
import com.wavenet.ding.qpps.fragment.FourthFragment;
import com.wavenet.ding.qpps.utils.MapUtil;
import com.wavenet.ding.qpps.utils.SPUtil;
import com.wavenet.ding.qpps.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zoubeiwen on 2018/8/29.
 */

public class AdminNormalActivity extends BaseMvpActivity {
    public FourthFragment mFourthFragment;
    private RadioGroup mRg;
    private CustomViewPager mCustomViewPager;
    AdminPagerAdapter mAdminPagerAdapter;
    private List<Fragment> fragments = new ArrayList<>();
    //记录第一次按返回键的时间
    private long firstTime = 0;
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
        mRg.setVisibility(View.GONE);

        mCustomViewPager = findViewById(R.id.vp);
        mCustomViewPager.setOffscreenPageLimit(2);

        mFourthFragment = FourthFragment.newInstance(AdminNormalActivity.this);
        fragments.add(mFourthFragment);

        mAdminPagerAdapter = new AdminPagerAdapter(getSupportFragmentManager(), fragments);
        mCustomViewPager.setAdapter(mAdminPagerAdapter);

        mCustomViewPager.setCurrentItem(0);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void requestData() {
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
                    exit();
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
