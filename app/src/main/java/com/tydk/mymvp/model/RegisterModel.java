package com.tydk.mymvp.model;

import com.tydk.mymvp.bean.UserInfo;
import com.tydk.mymvp.http.BaseResponse;
import com.tydk.mymvp.http.RetrofitFactory;
import com.tydk.mymvp.contract.RegisterContract;

import io.reactivex.Observable;

/**
 * @author: zzs
 * @date: 2019/7/31 0031 下午 2:25
 * @description:
 */

public class RegisterModel implements RegisterContract.Model {
    @Override
    public Observable<BaseResponse<UserInfo>> register(String name, String pwd, String rePwd) {
        return RetrofitFactory.getApi().register(name, pwd, rePwd);
    }
}
