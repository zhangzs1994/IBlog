package com.tydk.mymvp.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.tydk.uilibrary.view.LoadingView;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author: zzs
 * @date: 2019-07-30 下午 3:20
 * @description: 创建Base抽象类实现Observer
 */
public abstract class BaseObserver<T> implements Observer<BaseResponse<T>> {
    private static final String TAG = "BaseObserver";
    private boolean mShowDialog;
    private LoadingView loading;
    private Context mContext;
    private Disposable d;
    private String mMsg;

    public BaseObserver(Context context, Boolean showDialog, String msg) {
        mContext = context;
        mMsg = msg;
        mShowDialog = showDialog;
    }

    public BaseObserver(Context context, Boolean showDialog) {
        this(context, showDialog, "加载中...");
    }

    public void showLoading() {
        if (loading != null && loading.isShowing()) {
            loading.dismiss();
        }
        loading = new LoadingView(mContext, mMsg);
        loading.show();
    }

    public void dismissLoading() {
        if (loading != null && loading.isShowing()) {
            loading.dismiss();
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
        if (mShowDialog) {
            showLoading();
        }
    }

    @Override
    public void onNext(BaseResponse<T> response) {
        //在这边对 基础数据 进行统一处理
        dismissLoading();
        if (response.getErrorCode() == 0) {
            onSuccess(response.getData());
        } else {
            onFailure(null, response.getErrorMsg());
        }
    }

    @Override
    public void onError(Throwable e) {
        //Log.e(TAG, "Throwable: " + e.getMessage());
        if (d.isDisposed()) {
            d.dispose();
        }
        dismissLoading();
        onFailure(e, RxExceptionUtil.exceptionHandler(e));
    }

    @Override
    public void onComplete() {
        //Log.e(TAG, "onComplete: ");
        if (d.isDisposed()) {
            d.dispose();
        }
        dismissLoading();
    }

    public abstract void onSuccess(T t);

    public abstract void onFailure(Throwable e, String errorMsg);

    /**
     * 是否有网络连接，不管是wifi还是数据流量
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            return false;
        }
        boolean available = info.isAvailable();
        return available;
    }
}