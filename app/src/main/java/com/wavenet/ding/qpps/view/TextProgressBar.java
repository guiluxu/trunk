package com.wavenet.ding.qpps.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * Created by zoubeiwen on 2019/5/7.
 */

public class TextProgressBar extends ProgressBar {

    private Paint mPaint;
    private String text;
    private float rate;

    public TextProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        initView();
    }

    public TextProgressBar(Context context) {
        super(context);
        initView();
    }

    private void initView() {

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
    }

    @Override
    public synchronized void setProgress(int progress) {
        setText(progress);
        super.setProgress(progress);
    }

    private void setText(int progress) {
        rate = progress * 1.0f / this.getMax();
        int i = (int) (rate * 100);
        this.text = String.valueOf(i) + "%";
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        Rect rect = new Rect();
        mPaint.getTextBounds(text, 0, this.text.length(), rect);
        // int x = (getWidth()/2) - rect.centerX();
        // int y = (getHeight()/2) - rect.centerY();

//        if (x == getWidth()) {
//            // 如果为百分之百则在左边绘制。
//            x = getWidth() - rect.right;
//        }
        int x;
        if (rate<0.1){
     x = (int) (getWidth() * rate);
}else {
    x = (int) (getWidth() * (rate-0.1));
}
//        int y = (getHeight() / 2) - rect.top;
        int y = (getHeight() / 2)- rect.top/2 ;
        mPaint.setTextSize(35);
        canvas.drawText(text, x, y, mPaint);
    }

}