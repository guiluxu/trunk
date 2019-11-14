package com.dereck.library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

public class AnimRotationView extends android.support.v7.widget.AppCompatImageView {

    public AnimRotationView(Context context) {
        super(context);
        this.init(context);
    }

    public AnimRotationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    public AnimRotationView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        this.init(context);
    }

    private void init(Context context) {
        startRefresh();
    }

    public void startRefresh() {
        if (this.getAnimation() != null && !this.getAnimation().hasEnded()) {
            return;
        }
        /*
         * 按中心点旋转90度 效果和RotateAnimation(0,90,50,50);则以View的中心点为旋转点，旋转90度 。效果一样
		 */
        RotateAnimation ra = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);//
        ra.setDuration(700);
        ra.setRepeatCount(-1);
        ra.setInterpolator(new LinearInterpolator());
        this.startAnimation(ra);
    }

    public void endRefresh() {
        this.clearAnimation();
    }

    public boolean isRefreshing() {
        Animation a = this.getAnimation();
        return (a != null) && !a.hasEnded();
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility == VISIBLE) {
            startRefresh();
        } else {
            endRefresh();
        }
        super.setVisibility(visibility);
    }

}
