package com.dereck.library.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dereck.rxhttputilslibrary.R;


/**
 * 加载数据中.. 提示view
 */
public class LoadingWaitView extends FrameLayout {

    OnRestLoadListener ls;
    Context _context;
    TextView lodingText;
    ImageView loadimage;
    private View loadingWaitView;
    private View failureView;
    private boolean isLoadFailure, isRemove;

    public LoadingWaitView(Context context) {
        super(context);
        initView(context);
    }

    public LoadingWaitView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        _context = context;
        setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));

        LayoutInflater listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下
        View view = listContainer.inflate(R.layout.view_loaddatawait, this);
        // 自定义代码使用 !isInEditMode() 判断起来，在layout 设计界面可以预览
        if (!isInEditMode()) {
            view.findViewById(R.id.loading_view_id).setOnClickListener(
                    new OnClickListener() {// 禁止后面点击事件

                        @Override
                        public void onClick(View v) {
                            if (isLoadFailure) {
                                loadingWaitView.setVisibility(View.VISIBLE);
                                failureView.setVisibility(View.GONE);

                                if (ls != null) {
                                    ls.onRestLoad(LoadingWaitView.this);
                                }
                                isLoadFailure = false;
                            }
                        }
                    });

            loadingWaitView = view.findViewById(R.id.loading_view);
            lodingText = view.findViewById(R.id.loading_text);
            failureView = view.findViewById(R.id.load_failure_view);
            loadimage = findViewById(R.id.loadimage);
        }
    }

    public void setLoadingText(String text) {
        if (text != null) {
            lodingText.setText(text);
        }
    }

    /**
     * 加载数据失败，显示失败提示view
     */
    public void failure() {

        this.setVisibility(View.VISIBLE);
        loadingWaitView.setVisibility(View.GONE);
        failureView.setVisibility(View.VISIBLE);
        loadimage.setImageResource(R.mipmap.comm_loading_failure);
        isLoadFailure = true;

    }

    /**
     * 无数据显示
     */
    public void notdata() {
        this.setVisibility(View.VISIBLE);
        loadingWaitView.setVisibility(View.GONE);
        failureView.setVisibility(View.VISIBLE);
        loadimage.setImageResource(R.mipmap.comm_loading_not_data);
        isLoadFailure = true;
    }

    /**
     * 加载数据成功 调用
     */
    public void success() {
        lodingText.setText("加载中,请稍候");
        this.setVisibility(View.GONE);
        isLoadFailure = false;
    }

    public void reload() {
        this.setVisibility(View.VISIBLE);
        loadingWaitView.setVisibility(View.VISIBLE);
        failureView.setVisibility(View.GONE);
    }

    public void reload(String text) {
        lodingText.setText(text);
        reload();
    }

    public void successByRemove() {
        if (!isRemove) {
            ((ViewGroup) this.getParent()).removeView(this);
            isRemove = true;
        }
        isLoadFailure = false;
    }

    /**
     * 显示 加载数据中 视图
     */
    public void loadView() {
        if (!isRemove) {
            this.setVisibility(View.VISIBLE);
            loadingWaitView.setVisibility(View.VISIBLE);
            failureView.setVisibility(View.GONE);
        } else {
            Toast.makeText(_context, "loadView已被移除，无法重新加载", Toast.LENGTH_SHORT)
                    .show();
        }
    }
    public String getLodingText(){

        return   lodingText.getText().toString();
    }

    public void setRestLoadListener(OnRestLoadListener l) {
        ls = l;
    }

    public interface OnRestLoadListener {
        void onRestLoad(LoadingWaitView loadWaitView);
    }

}
