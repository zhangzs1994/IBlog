package com.tydk.iblog.contract;

import android.content.Context;

import com.tydk.iblog.base.BaseView;
import com.tydk.iblog.bean.UserInfo;
import com.tydk.iblog.http.BaseResponse;

import io.reactivex.Observable;

/**
 * @author: zzs
 * @date: 2019/7/31 0031 下午 2:25
 * @description:
 */

public interface RegisterContract {
    interface Model {
        Observable<BaseResponse<UserInfo>> register(String name, String pwd, String rePwd);
    }

    interface View extends BaseView {
        void setTitle(String title);

        String getName();

        String getPwd();

        String getRePwd();

        void onSuccess(UserInfo info);

        void onError(Throwable throwable, String message);

    }

    interface Presenter {
        void register(Context context, String name, String pwd, String rePwd);
    }
}
