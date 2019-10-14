package com.tydk.mymvp.presenter;

import android.content.Context;

import com.tydk.mymvp.base.BasePresenter;
import com.tydk.mymvp.bean.ListDataInfo;
import com.tydk.mymvp.contract.ArticleContract;
import com.tydk.mymvp.http.BaseObserver;
import com.tydk.mymvp.http.RxHelper;
import com.tydk.mymvp.model.ArticleModel;

/**
 * @author: zzs
 * @date: 2019/8/2 0002 下午 2:54
 * @description:
 */
public class ArticlePresenter extends BasePresenter<ArticleContract.View> implements ArticleContract.Presenter {

    private ArticleContract.Model model;

    public ArticlePresenter() {
        this.model = new ArticleModel();
    }

    @Override
    public void getListData(Context context,int pageNum,boolean showDialog) {
        model.getListData(pageNum)
                .compose(RxHelper.observable(context))
                .subscribe(new BaseObserver<ListDataInfo>(context, showDialog) {
                    @Override
                    public void onSuccess(ListDataInfo info) {
                        mView.ouSuccess(info);
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        mView.onError(e, errorMsg);
                    }
                });
    }
}
