package com.tydk.iblog.contract;

import com.tydk.iblog.base.BaseView;

/**
 * @author: zzs
 * @date: 2019/7/31 0031 下午 3:57
 * @description:
 */
public interface MainContract {
    interface Model {
    }

    interface View extends BaseView {
        void jumpToScan();
    }

    interface Presenter {
    }
}
