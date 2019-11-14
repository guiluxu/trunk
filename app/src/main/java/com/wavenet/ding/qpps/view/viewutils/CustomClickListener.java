package com.wavenet.ding.qpps.view.viewutils;

import android.view.View;

/**
 * Created by zoubeiwen on 2019/3/28.
 */

public abstract class CustomClickListener implements View.OnClickListener{
    private long mLastClickTime;
    private long timeInterval = 1000L;
View v;
    public CustomClickListener() {

    }

    public CustomClickListener(long timeInterval, View v) {
        this.timeInterval = timeInterval;
        this.v = v;
    }

    public CustomClickListener(long interval) {
        this.timeInterval = interval;
    }

    @Override
    public void onClick(View v) {
        long nowTime = System.currentTimeMillis();
        if (nowTime - mLastClickTime > timeInterval) {
            // 单次点击事件
            onSingleClick(v);
            mLastClickTime = nowTime;
        } else {
            // 快速点击事件
            onFastClick(v);
        }
    }

    protected abstract void onSingleClick(View v);
    protected abstract void onFastClick(View v);
}
