package com.tydk.iblog.contract;

import android.content.Context;

import com.tydk.iblog.base.BaseView;
import com.tydk.iblog.bean.BannerInfo;
import com.tydk.iblog.bean.ListDataInfo;
import com.tydk.iblog.http.BaseResponse;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author: zzs
 * @date: 2019/8/1 0001 下午 4:21
 * @description:
 */
public interface IndexContract {
    interface Model {
        Observable<BaseResponse<List<BannerInfo>>> getBanner();

        Observable<BaseResponse<ListDataInfo>> getListData();
    }

    interface View extends BaseView {

        void onDrawerListener();

        void initBanner(List<String> images, List<String> titles);

        void initRecycler();

        String getSearchContext();

        void onBannerSuccess(List<BannerInfo> info);

        void onBannerError(Throwable throwable, String message);

        void onListSuccess(ListDataInfo info);

        void onListError(Throwable throwable, String message);

        void onListItemClickListener();

        void onAbeamItemClickListener();
    }

    interface Presenter {
        void getBanner(Context context);

        void getListData(Context context);
    }
}
