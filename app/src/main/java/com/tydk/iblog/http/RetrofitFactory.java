package com.tydk.iblog.http;


import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.tydk.iblog.App;
import com.tydk.iblog.common.Constant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author: zzs
 * @date: 2019-07-30 下午 2:37
 * @description: 封装retrofit
 */
public class RetrofitFactory {
    private static ApiService mApiService;

    private RetrofitFactory() {
    }

    /**
     * 初始化retrofit
     * @return Retrofit对象
     */
    private Retrofit initRetrofit() {
        return new Builder()
                .baseUrl(Constant.URL)
                .addConverterFactory(GsonConverterFactory.create())//添加Gson解析
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加RxJava支持
                .client(okHttpClient())//设置自定义OkHttpClient
                .build();
    }

    /**
     * 初始化okHttp
     * @return okHttp对象
     */
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(Constant.HTTP_TIME, TimeUnit.SECONDS)//设置请求超时时间
                .readTimeout(Constant.HTTP_TIME, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(Constant.HTTP_TIME, TimeUnit.SECONDS)//设置写入超时时间
                .addInterceptor(InterceptorUtil.LogInterceptor())//添加日志打印拦截器
                .cookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(App.myApp)))//添加持久化Cookie
                .retryOnConnectionFailure(true)//设置出现错误进行重新连接。
                .build();
    }

    /**
     * retrofit单例
     * @return
     */
    public static ApiService getApi() {
        if (mApiService == null) {
            synchronized (RetrofitFactory.class) {
                if (mApiService == null) {
                    mApiService = new RetrofitFactory().initRetrofit().create(ApiService.class);
                }
            }
        }
        return mApiService;
    }
}
