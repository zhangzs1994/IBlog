package com.tydk.iblog.contract;

import android.content.Context;

import com.tydk.iblog.base.BaseView;
import com.tydk.iblog.bean.UserInfo;
import com.tydk.iblog.http.BaseResponse;

import io.reactivex.Observable;

/**
 * @author: zzs
 * @date: 2019/7/30 0030 上午 9:32
 * @description:
 */
public interface LoginContract {
    interface Model {
        Observable<BaseResponse<UserInfo>> login(String name, String pwd);
    }

    interface View extends BaseView {
        String getName();

        String getPwd();

        void initPermissions();

        void onSuccess(UserInfo info);

        void onError(Throwable throwable, String message);

        void jumpToMain();

        void jumpToRegister();

    }

    interface Presenter {
        void login(Context context, String name, String pwd);
    }
}
