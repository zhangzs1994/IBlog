package com.tydk.mymvp.model;

import com.tydk.mymvp.bean.BannerInfo;
import com.tydk.mymvp.bean.ListDataInfo;
import com.tydk.mymvp.contract.IndexContract;
import com.tydk.mymvp.http.BaseResponse;
import com.tydk.mymvp.http.RetrofitFactory;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author: zzs
 * @date: 2019/8/1 0001 下午 4:21
 * @description:
 */
public class IndexModel implements IndexContract.Model {
    @Override
    public Observable<BaseResponse<List<BannerInfo>>> getBanner() {
        return RetrofitFactory.getApi().getBanner();
    }

    @Override
    public Observable<BaseResponse<ListDataInfo>> getListData() {
        return RetrofitFactory.getApi().getListData();
    }
}
