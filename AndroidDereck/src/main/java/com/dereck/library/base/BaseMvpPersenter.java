package com.dereck.library.base;


import java.lang.ref.Reference;

public abstract class BaseMvpPersenter<V extends IMvpBaseView> {

    private V mMvpView;
    protected Reference<V> mViewRef;  //View接口类型的弱引用

    /**
     * 绑定V层
     *
     * @param view
     */
    public void attachMvpView(V view) {
        this.mMvpView = view;
    }

    /**
     * 解除绑定V层
     */
    public void detachMvpView() {
        mMvpView = null;
    }

    /**
     * 获取V层
     *
     * @return
     */
    public V getmMvpView() {

        return mMvpView;
    }
}
