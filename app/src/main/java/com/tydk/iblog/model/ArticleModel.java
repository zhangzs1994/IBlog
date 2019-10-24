package com.tydk.iblog.model;

import com.tydk.iblog.bean.ListDataInfo;
import com.tydk.iblog.contract.ArticleContract;
import com.tydk.iblog.http.BaseResponse;
import com.tydk.iblog.http.RetrofitFactory;

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
