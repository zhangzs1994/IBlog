package com.tydk.mymvp.common;

import android.os.Environment;

/**
 * @author: zzs
 * @date: 2019/7/30 0030 上午 11:49
 * @description: 通用常量类
 */

public class Constant {

    public static final int REQUEST_IMAGE = 2;
    public static final int HTTP_TIME = 60;
    public static final String SD = (Environment.getExternalStorageDirectory() + "/com.tydic.mymvp/");
    public static final String URL = "https://www.wanandroid.com/";
    public static final String LOGIN = URL + "user/login";
    public static final String REGISTER = URL + "user/register";
    public static final String BANNER = URL + "banner/json";
    public static final String LIST_DATA = URL + "article/listproject/0/json";
    public static final String LIST_DATA_PAGE = URL + "article/listproject/{pageNum}/json";

}
