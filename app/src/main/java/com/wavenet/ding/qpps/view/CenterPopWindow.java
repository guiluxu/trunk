package com.wavenet.ding.qpps.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 自定义泡泡窗口(居中显示窗口)
 */
public class CenterPopWindow extends PopupWindow {
    private SparseArray<View> mViews;
    private int layoutId;
    private ViewGroup mConvertView;
    private int mWidth;
    private int mHeight;

    public CenterPopWindow(Activity activity, int layoutId, int animationStyle) {
        this.layoutId = layoutId;
        this.mViews = new SparseArray<View>();
        //计算宽度和高度
        calWidthAndHeight(activity);
        setWidth(mWidth);
        setHeight(mHeight);
        LayoutInflater mLayoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mConvertView = (ViewGroup) mLayoutInflater.inflate(
                layoutId, null, true);
        this.setContentView(mConvertView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        mConvertView.setTag(this);
        // 设置动画效果
        this.setAnimationStyle(animationStyle);
        //设置如下四条信息，当点击其他区域使其隐藏，要在show之前配置
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
        this.setBackgroundDrawable(new BitmapDrawable());
        this.showAtLocation(mConvertView, Gravity.CENTER, 0, 0);
        // 设置动画效果
//        popHead.setAnimationStyle(R.style.popwin_slide_style);
//        this.setFocusable(true);
//        this.setOutsideTouchable(true);
//        mConvertView.setFocusable(true);
//        mConvertView.setFocusableInTouchMode(true);
//        mConvertView.setOnKeyListener(new View.OnKeyListener(){
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_BACK){
//                    if (this != null) {
//                        CenterPopWindow.this.dismiss();
//                    }
//                }
//                return false;
//            }
//        });

    }

    public CenterPopWindow(Context context, int layoutId) {
        this.layoutId = layoutId;
        this.mViews = new SparseArray<View>();
        LayoutInflater mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mConvertView = (ViewGroup) mLayoutInflater.inflate(
                layoutId, null, true);
        this.setContentView(mConvertView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        mConvertView.setTag(this);
        //设置如下四条信息，当点击其他区域使其隐藏，要在show之前配置
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
        this.setBackgroundDrawable(new BitmapDrawable());
        this.showAtLocation(mConvertView, Gravity.CENTER, 0, 0);
        // 设置动画效果
//        popHead.setAnimationStyle(R.style.popwin_slide_style);
//        this.setFocusable(true);
//        this.setOutsideTouchable(true);
//        mConvertView.setFocusable(true);
//        mConvertView.setFocusableInTouchMode(true);
//        mConvertView.setOnKeyListener(new View.OnKeyListener(){
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_BACK){
//                    if (this != null) {
//                        CenterPopWindow.this.dismiss();
//                    }
//                }
//                return false;
//            }
//        });

    }

    //关闭窗口
    public void dismissPopupWindow() {
        CenterPopWindow.this.dismiss();
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public CenterPopWindow setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * TextView的事件监听
     */
    public void setOnClickListener(int viewId, View.OnClickListener li) {
        getView(viewId).setOnClickListener(li);
    }

    /**
     * TextView
     */
    public void setTextOnClickListener(int viewId, View.OnClickListener li) {
        getView(viewId).setOnClickListener(li);
    }

    /**
     * ListView设置数据
     */
    public CenterPopWindow setListAdapter(int viewId, BaseAdapter adapter) {
        ListView listView = getView(viewId);
        listView.setAdapter(adapter);
        return this;
    }

    /**
     * ListView的事件监听
     */
    public void setItemListener(int viewId, AdapterView.OnItemClickListener li) {
        ListView listView = getView(viewId);
        listView.setOnItemClickListener(li);
    }

    //判断是否关闭窗口
    private void isClosePop() {
        if (this.isShowing()) {
            this.dismiss();
        }
    }

    /**
     * 设置PopupWindow的大小
     *
     * @param context
     */
    private void calWidthAndHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);

        mWidth = metrics.widthPixels;
        //设置高度为全屏高度的70%
        mHeight = metrics.heightPixels * 1;
    }
}
