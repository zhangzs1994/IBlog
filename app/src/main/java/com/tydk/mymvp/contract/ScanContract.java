package com.tydk.mymvp.contract;

import com.tydk.mymvp.base.BaseView;
import com.uuzuche.lib_zxing.activity.CodeUtils;

/**
 * @author: zzs
 * @date: 2019/8/6 0006 上午 10:36
 * @description:
 */
public interface ScanContract {
    interface Model {
    }

    interface View extends BaseView{
        //初始化二维码界面
        void initCaptureFragment();

        //打开系统相册
        void openAlbum();

        //扫描成功回调
        CodeUtils.AnalyzeCallback analyzeCallback();
    }

    interface Presenter {
    }
}
