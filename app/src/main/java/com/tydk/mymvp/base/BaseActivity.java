package com.tydk.mymvp.base;

import android.os.Bundle;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.tydk.mymvp.annotations.ContentView;
import com.tydk.uilibrary.utils.AppManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: zzs
 * @date: 2019-07-30 上午 9:48
 * @description: Activity基类
 */
public abstract class BaseActivity<T extends BasePresenter> extends RxAppCompatActivity implements BaseView {

    protected T mPresenter;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContentView annotation = this.getClass().getAnnotation(ContentView.class);
        if(annotation!=null) {
            int layoutId = annotation.value();
            if (layoutId > 0) {
                setContentView(layoutId);
                ImmersionBar.with(this).init();
                mPresenter = createPresenter();
                if (mPresenter != null) {
                    mPresenter.attachView(this);
                }
                unbinder = ButterKnife.bind(this);
                initView();
                AppManager.getAppManager().addActivity(this);
            } else {
                throw new RuntimeException("layoutId为空！");
            }
        }else{
            throw new RuntimeException("annotation为空！");
        }
    }

    /**
     * 创建Presenter
     */
    protected abstract T createPresenter();

    /**
     * 初始化视图
     */
    public abstract void initView();

    @Override
    public void toastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        if (unbinder != null) {
            unbinder.unbind();
        }
        AppManager.getAppManager().finishActivity(this);
        super.onDestroy();
    }
}
