package com.tydk.iblog.http;

import com.tydk.iblog.bean.BannerInfo;
import com.tydk.iblog.bean.ListDataInfo;
import com.tydk.iblog.bean.UserInfo;
import com.tydk.iblog.common.Constant;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @author: zzs
 * @date: 2019-07-30 下午 4:51
 * @description: API服务接口类
 */
public interface ApiService {

    //登录
    @FormUrlEncoded
    @POST(Constant.LOGIN)
    Observable<BaseResponse<UserInfo>> login(@Field("username") String username,
                                             @Field("password") String password);

    //注册
    @FormUrlEncoded
    @POST(Constant.REGISTER)
    Observable<BaseResponse<UserInfo>> register(@Field("username") String username,
                                                @Field("password") String password,
                                                @Field("repassword") String repassword);

    //获取banner图
    @GET(Constant.BANNER)
    Observable<BaseResponse<List<BannerInfo>>> getBanner();

    //获取文章列表数据
    @GET(Constant.LIST_DATA)
    Observable<BaseResponse<ListDataInfo>> getListData();

    //分页获取文章列表数据
    @GET(Constant.LIST_DATA_PAGE)
    Observable<BaseResponse<ListDataInfo>> getListData(@Path("pageNum") int pageNum);

}
