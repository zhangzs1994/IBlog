package com.tydk.mymvp.presenter;

import android.content.Context;

import com.tydk.mymvp.base.BasePresenter;
import com.tydk.mymvp.bean.BannerInfo;
import com.tydk.mymvp.bean.ListDataInfo;
import com.tydk.mymvp.bean.UserInfo;
import com.tydk.mymvp.contract.IndexContract;
import com.tydk.mymvp.http.BaseObserver;
import com.tydk.mymvp.http.RxHelper;
import com.tydk.mymvp.model.IndexModel;

import java.util.List;

/**
 * @author: zzs
 * @date: 2019/8/1 0001 下午 4:21
 * @description:
 */
public class IndexPresenter extends BasePresenter<IndexContract.View> implements IndexContract.Presenter {
    private IndexContract.Model model;

    public IndexPresenter() {
        model = new IndexModel();
    }

    @Override
    public void getBanner(Context context) {
        model.getBanner()
                .compose(RxHelper.observable(context))
                .subscribe(new BaseObserver<List<BannerInfo>>(context, true) {
                    @Override
                    public void onSuccess(List<BannerInfo> t) {
                        mView.onBannerSuccess(t);
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        mView.onBannerError(e, errorMsg);
                    }
                });
    }

    @Override
    public void getListData(Context context) {
        model.getListData()
                .compose(RxHelper.observable(context))
                .subscribe(new BaseObserver<ListDataInfo>(context, true) {
                    @Override
                    public void onSuccess(ListDataInfo t) {
                        mView.onListSuccess(t);
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        mView.onListError(e, errorMsg);
                    }
                });
    }
}
