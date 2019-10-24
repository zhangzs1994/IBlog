package com.tydk.iblog.contract;

import android.content.Context;

import com.tydk.iblog.base.BaseView;
import com.tydk.iblog.bean.ListDataInfo;
import com.tydk.iblog.http.BaseResponse;

import io.reactivex.Observable;

/**
 * @author: zzs
 * @date: 2019/8/2 0002 下午 2:54
 * @description:
 */
public interface ArticleContract {
    interface Model {
        Observable<BaseResponse<ListDataInfo>> getListData(int pageNum);
    }

    interface View extends BaseView {
        void setTitle(String title);

        void hideBackIcon();

        void initRecycler();

        void onRefresh();

        void onLoadMore();

        void onItemClick();

        void ouSuccess(ListDataInfo info);

        void onError(Throwable throwable, String message);
    }

    interface Presenter {
        void getListData(Context context, int pageNum, boolean showDialog);
    }
}
