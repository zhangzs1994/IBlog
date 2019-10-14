package com.tydk.mymvp.model;

import com.tydk.mymvp.bean.ListDataInfo;
import com.tydk.mymvp.contract.ArticleContract;
import com.tydk.mymvp.http.BaseResponse;
import com.tydk.mymvp.http.RetrofitFactory;

import io.reactivex.Observable;

/**
 * @author: zzs
 * @date: 2019/8/2 0002 下午 2:54
 * @description:
 */
public class ArticleModel implements ArticleContract.Model {
    @Override
    public Observable<BaseResponse<ListDataInfo>> getListData(int pageNum) {
        return RetrofitFactory.getApi().getListData(pageNum);
    }
}
