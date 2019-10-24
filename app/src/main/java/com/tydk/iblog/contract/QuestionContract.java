package com.tydk.iblog.contract;

import com.tydk.iblog.base.BaseView;

/**
 * @author: zzs
 * @date: 2019/8/9 0009 上午 8:47
 * @description:
 */
public interface QuestionContract {
    interface Model {
    }

    interface View extends BaseView{
        void setTitle(String title);
    }

    interface Presenter {
    }
}
