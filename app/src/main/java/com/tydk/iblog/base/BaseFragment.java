package com.tydk.iblog.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.trello.rxlifecycle2.components.support.RxFragment;
import com.tydk.iblog.annotations.ContentView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: zzs
 * @date: 2019-07-30 上午 11:37
 * @description: Fragment基类
 */
public abstract class BaseFragment<T extends BasePresenter> extends RxFragment implements BaseView {

    protected T mPresenter;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View mView = null;
        ContentView annotation = this.getClass().getAnnotation(ContentView.class);
        if (annotation != null) {
            int layoutId = annotation.value();
            if (layoutId > 0) {
                mView = inflater.inflate(layoutId, container, false);
                mPresenter = createPresenter();
                if (mPresenter != null) {
                    mPresenter.attachView(this);
                }
                unbinder = ButterKnife.bind(this, mView);
                initView(mView);
            } else {
                throw new RuntimeException("layoutId为空！");
            }
        } else {
            throw new RuntimeException("annotation为空！");
        }
        return mView;
    }

    /**
     * 创建Presenter
     */
    protected abstract T createPresenter();

    /**
     * 初始化视图
     */
    public abstract void initView(View view);

    @Override
    public void toastMsg(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroyView();
    }
}