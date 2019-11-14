package com.wavenet.ding.qpps.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.wavenet.ding.qpps.R;


/**
 * Created by zoubeiwen on 2018/6/20.
 */

public class PopWinDownUtil {
    private Context context;
    private View contentView;
    private View relayView;
    private Solve7PopupWindow popupWindow;
    private OnDismissLisener onDismissLisener;

    public PopWinDownUtil(Context context, View contentView, View relayView) {
        this.context = context;
        this.contentView = contentView;
        this.relayView = relayView;
        init();
    }

    @SuppressLint("WrongConstant")
    public void init() {
        //内容，高度，宽度
        popupWindow = new Solve7PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        //动画效果
        popupWindow.setAnimationStyle(R.style.AnimationTopFade);
        //菜单背景色
        ColorDrawable dw = new ColorDrawable(Color.TRANSPARENT);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.setOutsideTouchable(true);
        //关闭事件
        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (onDismissLisener != null) {
                    onDismissLisener.onDismiss();
                }
            }
        });
    }

    public void show() {
        //显示位置
        popupWindow.showAsDropDown(relayView);
    }

    public void hide() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    public void setOnDismissListener(OnDismissLisener onDismissLisener) {
        this.onDismissLisener = onDismissLisener;
    }

    public boolean isShowing() {
        return popupWindow.isShowing();
    }

    public interface OnDismissLisener {
        void onDismiss();
    }
}
