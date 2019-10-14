package com.tydk.mymvp.http;

import com.tydk.mymvp.bean.BannerInfo;
import com.tydk.mymvp.bean.ListDataInfo;
import com.tydk.mymvp.bean.UserInfo;
import com.tydk.mymvp.common.Constant;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
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
