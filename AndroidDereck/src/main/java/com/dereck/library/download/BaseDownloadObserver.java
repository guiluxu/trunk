package com.dereck.library.download;


import com.dereck.library.exception.ApiException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import okhttp3.ResponseBody;

import static com.dereck.library.utils.ToastUtils.showToast;


public abstract class BaseDownloadObserver implements Observer<ResponseBody> {

    /**
     * 失败回调
     *
     * @param errorMsg 错误信息
     */
    protected abstract void doOnError(String errorMsg);


    @Override
    public void onError(@NonNull Throwable e) {
        String error = ApiException.handleException(e).getMessage();
        setError(error);
    }

    private void setError(String errorMsg) {
        showToast(errorMsg);
        doOnError(errorMsg);
    }

}
