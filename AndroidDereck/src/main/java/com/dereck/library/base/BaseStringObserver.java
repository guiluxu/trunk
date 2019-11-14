package com.dereck.library.base;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 结果不做处理直接返回string
 */

public abstract class BaseStringObserver implements Observer<String>, com.dereck.library.interfaces.IStringSubscriber {

    /**
     * 是否隐藏toast
     *
     * @return
     */
    protected boolean isHideToast() {
        return false;
    }

    @Override
    public void onSubscribe(Disposable d) {
        doOnSubscribe(d);
    }

    @Override
    public void onNext(String string) {
        doOnNext(string);
    }

    @Override
    public void onError(Throwable e) {
        String error = com.dereck.library.exception.ApiException.handleException(e).getMessage();
        doOnError(error);
    }

    @Override
    public void onComplete() {
        doOnCompleted();
    }

}
