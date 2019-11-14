package com.dereck.library.observer;


import android.app.Dialog;
import android.text.TextUtils;

import com.dereck.library.base.BaseObserver;
import com.dereck.library.utils.RxHttpUtils;

import io.reactivex.disposables.Disposable;


public abstract class CommonObserver<T> extends BaseObserver<T> {
    public  boolean isToast = true;

    private Dialog mProgressDialog;

    public CommonObserver() {
    }

    public CommonObserver(boolean isToast) {
        this.isToast=isToast;
    }

    public CommonObserver(Dialog progressDialog) {
        mProgressDialog = progressDialog;
    }

    /**
     * 失败回调
     *
     * @param errorMsg
     */
    protected abstract void onError(String errorMsg);

    /**
     * 成功回调
     *
     * @param t
     */
    protected abstract void onSuccess(T t);


    @Override
    public void doOnSubscribe(Disposable d) {
        //RxHttpUtils.addDisposable(d);
        RxHttpUtils.addToCompositeDisposable(d);
    }

    @Override
    public void doOnError(String errorMsg) {
        dismissLoading();
        if ( !TextUtils.isEmpty(errorMsg)) {
            if (!"空指针异常".equals(errorMsg) || !isToast) {
                com.dereck.library.utils.ToastUtils.showToast(errorMsg);
            }
        }
        onError(errorMsg);
    }

    @Override
    public void doOnNext(T t) {
        onSuccess(t);
    }

    @Override
    public void doOnCompleted() {
        dismissLoading();
    }

    /**
     * 隐藏loading对话框
     */
    private void dismissLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
