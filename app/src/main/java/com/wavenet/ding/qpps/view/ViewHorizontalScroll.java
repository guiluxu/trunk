package com.wavenet.ding.qpps.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;


/**
 * @version V_1.0.0
 */
public class ViewHorizontalScroll extends HorizontalScrollView {
    public ViewHorizontalScroll(Context context) {
        super(context);
    }

    public ViewHorizontalScroll(Context context,  AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewHorizontalScroll(Context context,  AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private float lastX, lastY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {

        boolean intercept = super.onInterceptTouchEvent(e);

        switch (e.getAction()) {

            case MotionEvent.ACTION_DOWN:
                lastX = e.getX();
                lastY = e.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                // 只要横向大于竖向，就拦截掉事件。
                // 部分机型点击事件（slopx==slopy==0），会触发MOVE事件。
                // 所以要加判断(slopX > 0 || sloy > 0)
                float slopX = Math.abs(e.getX() - lastX);
                float slopY = Math.abs(e.getY() - lastY);
                //  Log.log("slopX=" + slopX + ", slopY="  + slopY);
                if((slopX > 0 || slopY > 0) && slopX >= slopY){
                    requestDisallowInterceptTouchEvent(true);
                    intercept = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
        }
        // Log.log("intercept"+e.getAction()+"=" + intercept);
        return intercept;
    }
}
