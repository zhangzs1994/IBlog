package com.tydk.mymvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.tydk.mymvp.base.BasePresenter;
import com.tydk.mymvp.bean.UserInfo;
import com.tydk.mymvp.http.BaseObserver;
import com.tydk.mymvp.http.RxHelper;
import com.tydk.mymvp.contract.RegisterContract;
import com.tydk.mymvp.model.RegisterModel;

/**
 * @author: zzs
 * @date: 2019/7/31 0031 下午 2:25
 * @description:
 */

public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {

    private RegisterContract.Model model;

    public RegisterPresenter() {
        model = new RegisterModel();
    }

    @Override
    public void register(Context context, String name, String pwd, String rePwd) {
        if (TextUtils.isEmpty(name)) {
            mView.toastMsg("请输入用户名");
        } else if (TextUtils.isEmpty(pwd)) {
            mView.toastMsg("请输入密码");
        } else if (TextUtils.isEmpty(rePwd)) {
            mView.toastMsg("请确认密码");
        } else if (!pwd.equals(rePwd)) {
            mView.toastMsg("两次输入密码不一致");
        } else {
            model.register(name, pwd, rePwd)
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
