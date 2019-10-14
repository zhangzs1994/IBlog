package com.tydk.mymvp.contract;

import android.view.View;

import com.tydk.mymvp.base.BaseView;

/**
 * @author: zzs
 * @date: 2019/8/5 0005 下午 3:14
 * @description:
 */
public interface MineContract {
    interface Model {
    }

    interface View extends BaseView{
        void setTitle(String title);

        void initAnimation(android.view.View front, android.view.View back);
    }

    interface Presenter {
    }
}
