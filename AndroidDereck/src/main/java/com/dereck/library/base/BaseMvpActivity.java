package com.dereck.library.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dereck.library.utils.AppManager;
import com.dereck.library.view.CustomProgressDialog;
import com.dereck.rxhttputilslibrary.R;

import org.devio.takephoto.app.TakePhotoActivity;
import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseMvpActivity<V extends IMvpBaseView, P extends BaseMvpPersenter<V>> extends TakePhotoActivity implements IMvpBaseView {
    public P presenter;
    public Activity activity;
    /**
     * 标题栏右边按钮图标点击监听器
     *
     * @param listener
     */
    public LinearLayout optionsButton;
    CustomProgressDialog waitingDialog;
    /**
     * 设置Activity标题
     */
    TextView mTvtitle;
    /**
     * 设置Activity图片（Drawable left,Drawable top, Drawable right, Drawable bottom）
     */
    ImageView mTvtitledarw;
    RelativeLayout mLltitledarw;
    Object ob = null;
    private Unbinder unbinder;
    public static String TAG = "qingpu CurActivity = ";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        AppManager.getAppManager().addActivity(this);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        if (presenter == null) {
            presenter = createPresenter();
        }
        unbinder = ButterKnife.bind(this);
        if (presenter == null) {
            throw new NullPointerException("presenter 不能为空!");
        }
        //绑定view
        presenter.attachMvpView((V) this);
        init();
        requestData();
        Log.d(TAG, getLocalClassName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //将Activity实例从AppManager的堆栈中移除
        AppManager.getAppManager().finishActivity(this);
        isRegistered(false, ob);
        //解除绑定
        if (presenter != null) {
            presenter.detachMvpView();
        }
        try {
            unbinder.unbind();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示进度条
     *
     * @param
     */
    public void showDialog() {
        try {
            if (null == waitingDialog) {
                waitingDialog = CustomProgressDialog.createDialog(this);
                waitingDialog.setMessage("正在加载..");
                waitingDialog.show();
            } else if (null != waitingDialog) {
                waitingDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏进度条
     */
    public void cancelDialog() {
        try {
            if (null != waitingDialog) {
                waitingDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 布局文件加载
     */
    public abstract int getLayoutId();

    /**
     * 初始化参数
     */
    public abstract void init();

    /**
     * 请求数据
     */
    public abstract void requestData();

    /**
     * 创建Presenter
     *
     * @return 子类自己需要的Presenter
     */
    protected abstract P createPresenter();

    /**
     * 获取Presenter
     *
     * @return 返回子类创建的Presenter
     */
    public P getPresenter() {
        return presenter;
    }

    public void setTitle(String resId) {
        mTvtitle = findViewById(R.id.tv_activity_title);
        mTvtitle.setText(resId);
    }

    public void setTitledraw(int resId, View.OnClickListener listener) {
        mTvtitledarw = findViewById(R.id.iv_titledarw);
        mTvtitledarw.setImageResource(resId);
        mTvtitledarw.setVisibility(View.VISIBLE);
        mLltitledarw = findViewById(R.id.ll_activity_title);
        mLltitledarw.setOnClickListener(listener);
    }

    public void setTitledraw1(int resId) {
        mTvtitledarw = findViewById(R.id.iv_titledarw);
        mTvtitledarw.setImageResource(resId);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        optionsButton = findViewById(R.id.btn_activity_options);
        optionsButton.setOnClickListener(listener);
    }

    /**
     * 设置标题栏右边图标
     *
     * @param resId
     */

    public void setReplacepictures(int resId) {
        ImageView optionsButton = findViewById(R.id.settitle_img);
        optionsButton.setImageResource(resId);
    }

    /**
     * 设置标题栏右边文字按钮
     *
     * @param resId
     */
    public void setReplaceBUT(View.OnClickListener listener, int resId) {
        Button optionsButton = findViewById(R.id.btn_choose_finish);
        optionsButton.setVisibility(View.VISIBLE);
        optionsButton.setText(resId);
        optionsButton.setOnClickListener(listener);
    }

    public void setReplaceBUT(View.OnClickListener listener) {
        Button optionsButton = findViewById(R.id.btn_choose_finish);
        optionsButton.setVisibility(View.VISIBLE);
        optionsButton.setText("删除");
        optionsButton.setOnClickListener(listener);
    }

    /**
     * 设置标题栏坐边图标
     * 设置监听
     *
     * @param resId
     */
    public void setReplacepicturesleft(int resId, View.OnClickListener listener) {
        LinearLayout left_back = findViewById(R.id.btn_activity_back);
        LinearLayout left_ll = findViewById(R.id.btn_activity_lift);
        left_back.setVisibility(View.GONE);
        left_ll.setVisibility(View.VISIBLE);
        ImageView optionsButton = findViewById(R.id.setlift_img);
        optionsButton.setImageResource(resId);
        left_ll.setOnClickListener(listener);
    }

    /**
     * 不显示标题栏右边按钮图标
     */
    public void setOptionsButtonInVisible() {
        optionsButton = findViewById(R.id.btn_activity_options);
        optionsButton.setVisibility(View.INVISIBLE);
    }

    /**
     * 不显示标题栏右边按钮图标
     */
    public void setOptionsButtonVisible() {
        optionsButton = findViewById(R.id.btn_activity_options);
        optionsButton.setVisibility(View.VISIBLE);
    }

    /**
     * 不显示返回按钮
     */
    public void setBackButtonInVisible() {
        LinearLayout optionsButton = findViewById(R.id.btn_activity_back);
        optionsButton.setVisibility(View.INVISIBLE);
    }

    /**
     * 回退事件
     *
     * @param v
     */
    public void onBack(View v) {
        super.onBackPressed();
//        finish();
    }

    public void isRegistered(boolean isRegistered, Object o) {
        this.ob = o;
        if (isRegistered) {
            if (!EventBus.getDefault().isRegistered(o)) {
                EventBus.getDefault().register(o);
            }
        } else {
            if (ob != null) {
                EventBus.getDefault().unregister(ob);
                EventBus.getDefault().post("vieweventunregistere");
            }

        }

    }
}
