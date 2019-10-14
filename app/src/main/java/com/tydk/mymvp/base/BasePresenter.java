package com.tydk.mymvp.base;

/**
 * @author: zzs
 * @date: 2019/7/30 0030 上午 9:49
 * @description: presenter基类
 */
public class BasePresenter<V extends BaseView> {
    protected V mView;

    /**
     * 绑定view
     */
    public void attachView(V view) {
        mView = view;
    }

    /**
     * 解绑view
     */
    public void detachView() {
        mView = null;
    }

    /**
     * view是否绑定
     */
    public boolean isViewAttach() {
        return mView != null;
    }
}
