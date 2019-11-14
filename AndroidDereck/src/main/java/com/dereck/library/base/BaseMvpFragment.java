package com.dereck.library.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dereck.library.view.CustomProgressDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseMvpFragment<V extends IMvpBaseView, P extends BaseMvpPersenter<V>> extends Fragment implements IMvpBaseView {


    public View mainView;
    public P presenter;
    public Context context;
    Object ob = null;
    CustomProgressDialog waitingDialog;
    private Unbinder unbinder;
    private Toast toast = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建Presenter
        if (presenter == null) {
            presenter = createPresenter();
        }
        if (presenter == null) {
            throw new NullPointerException("presenter 不能为空!");
        }
        //绑定view
        presenter.attachMvpView((V) this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(getLayoutId(), container, false);

        unbinder = ButterKnife.bind(this, mainView);
        context = getActivity();
        return mainView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
        requestData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
     * 创建Presenter
     *
     * @return 子类自己需要的Presenter
     */
    protected abstract P createPresenter();

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
     * 显示进度条
     *
     * @param
     */
    public void showDialog() {
        try {
            if (null == waitingDialog) {
                waitingDialog = CustomProgressDialog.createDialog(context);
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
     * 显示一个Toast类型的消息
     *
     * @param msg 显示的消息
     */
    public void showToast(final String msg) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (toast == null) {
                        toast = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
                    } else {
                        toast.setText(msg);
                        toast.setDuration(Toast.LENGTH_SHORT);
                    }
                    toast.show();
                }
            });
        }
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
