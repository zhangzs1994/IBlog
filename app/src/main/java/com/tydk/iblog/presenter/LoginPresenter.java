package com.tydk.iblog.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.tydk.iblog.base.BasePresenter;
import com.tydk.iblog.bean.UserInfo;
import com.tydk.iblog.http.BaseObserver;
import com.tydk.iblog.http.RxHelper;
import com.tydk.iblog.contract.LoginContract;
import com.tydk.iblog.model.LoginModel;

/**
 * @author: zzs
 * @date: 2019/7/30 0030 上午 9:32
 * @description:
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    private LoginContract.Model model;

    public LoginPresenter() {
        model = new LoginModel();
    }

    @Override
    public void login(Context context, String name, String pwd) {
        if (TextUtils.isEmpty(name)) {
            mView.toastMsg("请输入用户名");
        } else if (TextUtils.isEmpty(pwd)) {
            mView.toastMsg("请输入密码");
        } else {
            model.login(name, pwd)
                    .compose(RxHelper.observable(context))
                    .subscribe(new BaseObserver<UserInfo>(context, true) {
                        @Override
                        public void onSuccess(UserInfo t) {
                            mView.onSuccess(t);
                        }

                        @Override
                        public void onFailure(Throwable e, String errorMsg) {
                            mView.onError(e, errorMsg);
                        }
                    });
        }
    }
}
