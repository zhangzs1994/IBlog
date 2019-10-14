package com.tydk.mymvp.model;

import com.tydk.mymvp.bean.UserInfo;
import com.tydk.mymvp.http.BaseResponse;
import com.tydk.mymvp.http.RetrofitFactory;
import com.tydk.mymvp.contract.LoginContract;

import io.reactivex.Observable;

/**
 * @author: zzs
 * @date: 2019/7/30 0030 上午 9:32
 * @description:
 */

public class LoginModel implements LoginContract.Model {
    @Override
    public Observable<BaseResponse<UserInfo>> login(String name, String pwd) {
        return RetrofitFactory.getApi().login(name, pwd);
    }
}
