package com.wavenet.ding.qpps.utils;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wavenet.ding.qpps.R;

public class CommonDialog extends android.app.Dialog {

    public Button buttonAccept;
    public Button buttonCancel;
    public Button buttonOther;
    Context context;
    FrameLayout dialogView;
    LinearLayout dialogContentView, messageContentView;
    View backView;
    CharSequence message;
    TextView messageTextView;
    CharSequence title;
    TextView titleTextView;
    CharSequence buttonCancelText, confirmButtonText, otherButtonText;

    View.OnClickListener onConfirmButtonClickListener;
    View.OnClickListener onCancelButtonClickListener;
    View.OnClickListener onOtherButtonClickListener;

    View cutomeDialogView, cutomeMessageView;
    // 点击确定，自动关闭dialog
    boolean isClickCloseDialog = true;

    public CommonDialog(Context context) {
        super(context, R.style.commDialogTheme);
        this.context = context;// init Context
    }

    public CommonDialog(Context context, CharSequence title,
                        CharSequence message) {
        this(context);
        this.message = message;
        this.title = title;
    }

    public CommonDialog(Context context, CharSequence title) {
        this(context, title, null);
    }

    /**
     * 设置取消按钮文字
     */
    public void setCancelBntText(CharSequence text) {
        this.buttonCancelText = text;
    }

    /**
     * 设置确定按钮文字
     */
    public void setConfirmBntText(CharSequence text) {
        this.confirmButtonText = text;
    }

    /**
     * 设置其他按钮文字
     */
    public void setOtherBntText(CharSequence text) {
        this.otherButtonText = text;
    }

    /**
     * 设置取消按钮单击事件
     */
    public void setOnCancelListener(View.OnClickListener click) {
        onCancelButtonClickListener = click;
    }

    /**
     * 设置确认按钮单击事件
     */
    public void setOnConfirmListener(View.OnClickListener click) {
        setOnConfirmListener(click, true);
    }

    /**
     * 设置确认按钮单击事件,isClickCloseDialog:true ->点击确定，自动关闭dialog
     *
     * @param click
     * @param isClickCloseDialog 点击确定，自动关闭dialog
     */
    public void setOnConfirmListener(View.OnClickListener click,
                                     boolean isClickCloseDialog) {
        this.onConfirmButtonClickListener = click;
        this.isClickCloseDialog = isClickCloseDialog;
    }

    /**
     * 设置其他按钮按钮单击事件
     */
    public void setOnOtherBntListener(View.OnClickListener click) {
        onOtherButtonClickListener = click;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_dialog);

        dialogView = findViewById(R.id.contentDialog);
        dialogContentView = findViewById(R.id.dialogContentView);
        messageContentView = findViewById(R.id.messageContentView);

        backView = findViewById(R.id.dialog_rootView);
        // backView.setOnTouchListener(new OnTouchListener() {
        //
        // @Override
        // public boolean onTouch(View v, MotionEvent event) {
        // if (event.getX() < dialogView.getLeft()
        // || event.getX() > dialogView.getRight()
        // || event.getY() > dialogView.getBottom()
        // || event.getY() < dialogView.getTop()) {
        // close(true);
        // }
        // return false;
        // }
        // });

        this.titleTextView = findViewById(R.id.title);
        setTitle(title);

        this.messageTextView = findViewById(R.id.message);
        setMessage(message);

        this.buttonAccept = findViewById(R.id.button_accept);

        if (confirmButtonText == null) {
            isClickCloseDialog = true;
            this.buttonAccept.setText("确定");
        } else {
            this.buttonAccept.setText(confirmButtonText);
        }
        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClickCloseDialog)
                    cancel();

                if (onConfirmButtonClickListener != null)
                    onConfirmButtonClickListener.onClick(v);

            }

        });

        this.buttonCancel = findViewById(R.id.button_cancel);
        this.buttonCancel.setVisibility(buttonCancelText == null ? View.GONE
                : View.VISIBLE);
        if (buttonCancelText != null) {
            this.buttonCancel.setText(buttonCancelText);

            buttonCancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    cancel();

                    if (onCancelButtonClickListener != null)
                        onCancelButtonClickListener.onClick(v);

                }
            });
        }

        this.buttonOther = findViewById(R.id.otherBnt);
        findViewById(R.id.otherBntPanel).setVisibility(
                otherButtonText == null ? View.GONE : View.VISIBLE);
        if (otherButtonText != null) {
            this.buttonOther.setText(otherButtonText);

            buttonOther.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (onOtherButtonClickListener != null)
                        onOtherButtonClickListener.onClick(v);

                }
            });
        }

        if (cutomeDialogView != null) {
            dialogContentView.removeViewAt(1);
            dialogContentView.addView(cutomeDialogView);
        }
        if (cutomeMessageView != null) {
            messageContentView.removeAllViews();
            messageContentView.addView(cutomeMessageView);
        }

        setNewStyles();
    }

    public void setNewStyles() {

    }

    @Override
    public void show() {
        // TODO 自动生成的方法存根
        super.show();
        // set dialog enter animations
        dialogView.startAnimation(AnimationUtils.loadAnimation(context,
                R.anim.comm_dialog_main_show_amin));
        backView.startAnimation(AnimationUtils.loadAnimation(context,
                R.anim.comm_dialog_root_show_amin));
    }

    /**
     * 添加自定义view 到 title和消息区域，保留确定和取消按钮
     *
     * @param view
     */
    public void setDialogContentView(View view) {
        cutomeDialogView = view;
    }

    public View getDialogContentView(View view) {
        return cutomeDialogView;
    }

    public View getMessageContentView() {
        return cutomeMessageView;
    }

    /**
     * 添加自定义view 到显示消息区域，保留title部分
     *
     * @param view
     */
    public void setMessageContentView(View view) {
        cutomeMessageView = view;
    }

    public void setMessage(CharSequence message) {
        this.message = message;
        if (messageTextView != null)
            messageTextView.setText(message);
    }

    public void setTitle(CharSequence title) {
        this.title = title;
        if (title == null)
            titleTextView.setVisibility(View.GONE);
        else {
            titleTextView.setVisibility(View.VISIBLE);
            titleTextView.setText(title);
        }
    }

}
