package com.tydk.mymvp.contract;

import android.content.Context;

import com.tydk.mymvp.base.BaseView;

import java.util.List;
import java.util.Map;

/**
 * @author: zzs
 * @date: 2019/8/5 0005 上午 9:35
 * @description:
 */
public interface ContactsContract {
    interface Model {
        List<Map<String,String>> getData(Context context);
    }

    interface View extends BaseView{

        void setTitle(String title);

        void hideBackIcon();

        void initData();

        void onSelectIndexItemListener();
    }

    interface Presenter {
        List<Map<String,String>> getData(Context context);
    }
}
