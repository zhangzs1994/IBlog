package com.tydk.mymvp.contract;

import com.tydk.mymvp.base.BaseView;

/**
 * @author: zzs
 * @date: 2019/8/8 0008 上午 11:12
 * @description:
 */
public interface AboutContract {
    interface Model {
    }

    interface View extends BaseView{
        void onBackListener();
    }

    interface Presenter {
    }
}
